package net.zfp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.AccountSource;
import net.zfp.entity.Domain;
import net.zfp.entity.News;
import net.zfp.entity.PortalType;
import net.zfp.entity.Source;
import net.zfp.entity.SourceType;
import net.zfp.entity.Status;
import net.zfp.entity.User;
import net.zfp.entity.account.AccountCampaign;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.campaign.CampaignTarget;
import net.zfp.entity.carbon.CarbonFootprint;
import net.zfp.entity.category.Category;
import net.zfp.entity.community.Community;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.product.Product;
import net.zfp.entity.provider.Provider;
import net.zfp.entity.provider.ProviderAccount;
import net.zfp.entity.provider.ProviderSource;
import net.zfp.entity.provider.ProviderType;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.segment.SegmentType;
import net.zfp.entity.source.SourceMode;
import net.zfp.entity.tips.Tips;
import net.zfp.entity.utility.UtilityConnectionRequest;
import net.zfp.entity.utility.UtilityType;
import net.zfp.form.UtilityForm;
import net.zfp.notify.NotificationService;
import net.zfp.notify.impl.NotificationServiceImpl;
import net.zfp.service.ConnectorService;
import net.zfp.util.AppConstants;
import net.zfp.view.NewsView;
import net.zfp.view.ResultView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class ConnectorServiceImpl implements ConnectorService {

	@Resource
    private SegmentService segmentService;
	@Resource
    private AccountService accountService;
	@Resource
	private EntityDao<Tips> tipDao;
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<News> newsDao;
	@Resource
	private EntityDao<CarbonFootprint> carbonFootprintDao;
	@Resource
	private EntityDao<Category> categoryDao;
	@Resource
	private EntityDao<PortalType> portalTypeDao;
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<Segment> segmentDao;
	@Resource
	private EntityDao<Offer> offerDao;
	@Resource
	private EntityDao<Provider> providerDao;
	@Resource
	private EntityDao<Campaign> campaignDao;
	@Resource
	private EntityDao<AccountCampaign> accountCampaignDao;
	@Resource
	private EntityDao<Source> sourceDao;
	@Resource
	private EntityDao<Status> statusDao;
	@Resource
	private EntityDao<AccountSource> accountSourceDao;
	@Resource
	private EntityDao<ProviderAccount> providerAccountDao;
	@Resource
	private EntityDao<ProviderSource> providerSourceDao;
	@Resource
	private EntityDao<UtilityConnectionRequest> utilityConnectionRequestDao;
	@Inject
    private NotificationService notificationService;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView storeWalkingConnector(Long memberId, String deviceName, String authorizationCode, String providerCode){
		ResultView rv = new ResultView();
		
		if (memberId == null || deviceName == null || authorizationCode == null || providerCode == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		
		User user = userDao.findById(memberId);
		if (user == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
			return rv;
		}
		
		Provider provider =providerDao.getProvider(providerCode);
		if (provider == null){
			rv.fill(AppConstants.FAILURE, "Invalid provider.");
			return rv;
		}
		
		
		Source source = null;
		/*
		 * Check if it exists...
		 */
		source = sourceDao.findSourceBySourceCode(authorizationCode);
		
		if (source == null){
			//Create source first
			try{
				source = new Source();
				source.setName(deviceName);
				source.setCode(authorizationCode);
				source.setSupressName(false);
				source.setSupressDisplay(false);
				source.setSourceType(sourceDao.getSourceTypeByCode(AppConstants.SOURCETYPE_CODE_WALKING));
				source.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
				source.setCategory(categoryDao.getCategoryByCode(AppConstants.CATEGORY_CODE_PERSONAL, AppConstants.CATEGORYTYPE_SOURCE));
				
				SourceMode meterMode = sourceDao.getSourceModeByCode(AppConstants.SOURCEMODE_CODE_METER);
				source.setSourceMode(meterMode);
				
				sourceDao.save(source);
				source = sourceDao.findSourceBySourceCode(authorizationCode);
				
				Status activeStatus = statusDao.getStatusByCode(AppConstants.STATUS_CODE_ACTIVE);
				
				AccountSource accountSource = new AccountSource();
				accountSource.setUser(user);
				accountSource.setSource(source);
				accountSource.setStatus(activeStatus);
				accountSourceDao.save(accountSource);
				
				/*
				 * If provider is ZFP then we do want to make this source to be default source
				 */
				if (providerCode.equals(AppConstants.PROVIDER_ZFP)){
					accountService.setMemberDefaultSource(user.getId(), source.getCode());
				}
			}catch(Exception e){
				System.out.println("Source save failed");
			}
			
			/*
			 * Add to provider source table.
			 */
			ProviderSource ps = new ProviderSource();
			if (provider != null){
				
				ps.setSource(source);
				ps.setProvider(provider);
				try{
					providerSourceDao.save(ps);
					rv.fill(AppConstants.SUCCESS, "Success");
				}catch(Exception e){
					e.printStackTrace();
					rv.fill(AppConstants.FAILURE, e.getMessage());
				}
				
			}
		}else{
			source.setLastModified(new Date());
			source.setName(deviceName);
			source.setCode(authorizationCode);
			Status activeStatus = statusDao.getStatusByCode(AppConstants.STATUS_CODE_ACTIVE);
			source.setStatus(activeStatus);
			
			sourceDao.save(source, true);
			
			AccountSource accountSource =accountSourceDao.findAccountSource(memberId, source.getId());
			if (accountSource == null){
				accountSource = new AccountSource();
				accountSource.setUser(user);
				accountSource.setSource(source);
				accountSource.setStatus(activeStatus);
				accountSourceDao.save(accountSource);
				
			}else{
				accountSource.setStatus(activeStatus);
				accountSourceDao.save(accountSource, true);
			}
		}
		
		try{
			joinAutoCampaign(user, null, AppConstants.SOURCETYPE_CODE_WALKING);
		}catch(Exception e){
		}
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView storeRunningConnector(Long memberId, String deviceName, String authorizationCode, String providerCode){
		System.out.println("Store Running connector : "+ new Date() + " : " + authorizationCode + " providerCode : " + providerCode);
		
		ResultView rv = new ResultView();
		
		if (memberId == null || deviceName == null || authorizationCode == null || providerCode == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		
		User user = userDao.findById(memberId);
		if (user == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
			return rv;
		}
		
		Provider provider =providerDao.getProvider(providerCode);
		if (provider == null){
			rv.fill(AppConstants.FAILURE, "Invalid provider.");
			return rv;
		}
		
		
		Source source = null;
		/*
		 * Check if it exists...
		 */
		source = sourceDao.findSourceBySourceCode(authorizationCode);
		
		if (source == null){
			System.out.println("Source does not exists");
			//Create source first
			try{
				source = new Source();
				source.setName(deviceName);
				source.setCode(authorizationCode);
				source.setSupressName(false);
				source.setSupressDisplay(false);
				source.setSourceType(sourceDao.getSourceTypeByCode(AppConstants.SOURCETYPE_CODE_RUNNING));
				source.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
				source.setCategory(categoryDao.getCategoryByCode(AppConstants.CATEGORY_CODE_PERSONAL, AppConstants.CATEGORYTYPE_SOURCE));
				
				SourceMode meterMode = sourceDao.getSourceModeByCode(AppConstants.SOURCEMODE_CODE_METER);
				source.setSourceMode(meterMode);
				
				sourceDao.save(source);
				
				System.out.println("Creating sources...");
				source = sourceDao.findSourceBySourceCode(authorizationCode);
				
				Status activeStatus = statusDao.getStatusByCode(AppConstants.STATUS_CODE_ACTIVE);
				
				AccountSource accountSource = new AccountSource();
				accountSource.setUser(user);
				accountSource.setSource(source);
				accountSource.setStatus(activeStatus);
				accountSourceDao.save(accountSource);
				
				/*
				 * If provider is ZFP then we do want to make this source to be default source
				 */
				if (providerCode.equals(AppConstants.PROVIDER_ZFP)){
					System.out.println("Source ID :: " + source.getId() + " source Code " + source.getCode());
					accountService.setMemberDefaultSource(user.getId(), source.getCode());
				}
				System.out.println("Creating default source...");
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Source save failed");
			}
			
			/*
			 * Add to provider source table.
			 */
			ProviderSource ps = new ProviderSource();
			
			System.out.println("Provider...");
			if (provider != null){
				
				ps.setSource(source);
				ps.setProvider(provider);
				try{
					providerSourceDao.save(ps);
					rv.fill(AppConstants.SUCCESS, "Success");
					System.out.println("Success...");
				}catch(Exception e){
					e.printStackTrace();
					rv.fill(AppConstants.FAILURE, e.getMessage());
				}
				
				System.out.println("...");
			}
		}else{
			System.out.println("Source exits...");
			source.setLastModified(new Date());
			source.setName(deviceName);
			source.setCode(authorizationCode);
			Status activeStatus = statusDao.getStatusByCode(AppConstants.STATUS_CODE_ACTIVE);
			source.setStatus(activeStatus);
			
			sourceDao.save(source, true);
			
			AccountSource accountSource =accountSourceDao.findAccountSource(memberId, source.getId());
			if (accountSource == null){
				accountSource = new AccountSource();
				accountSource.setUser(user);
				accountSource.setSource(source);
				accountSource.setStatus(activeStatus);
				accountSourceDao.save(accountSource);
				
			}else{
				accountSource.setStatus(activeStatus);
				accountSourceDao.save(accountSource, true);
			}
		}
		
		try{
			joinAutoCampaign(user, null, AppConstants.SOURCETYPE_CODE_RUNNING);
		}catch(Exception e){
		}

		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView isUtilityConnectorRequested(Long accountId, String type){
		ResultView rv = new ResultView();
		UtilityConnectionRequest urc = utilityConnectionRequestDao.getUtilityConnectionRequest(accountId, type);
		if (urc != null){
			rv.fill(AppConstants.SUCCESS, "Requested");
		}else{
			rv.fill(AppConstants.FAILURE, "Not Requested");
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView isSourceConnected(Long accountId, String sourceTypeCode, String categoryCode){
		ResultView rv =new ResultView();
		
		List<Source> sources = userDao.findSourceByAccountAndType(accountId, sourceTypeCode);
		Long sourceId = null;
		for (Source source : sources){
			System.out.println(source.getId());
			
			if (source.getCategory().getCode().equals(categoryCode) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE && source.getStatus().getCode().equals(AppConstants.STATUS_CODE_ACTIVE)){
				sourceId = source.getId();
				break;
			}
		}
		
		if (sourceId != null){
			rv.fill(AppConstants.SUCCESS, "Connected");
		}else{
			rv.fill(AppConstants.FAILURE, "Not Connected");
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView storeElectricityConnector(String domainName, Long memberId, String deviceName, String authorizationCode, String providerCode){
		ResultView rv = new ResultView();
		
		User user = userDao.findById(memberId);
		Source source = new Source();
		//Create source first
		try{
			
			source.setName(deviceName);
			source.setCode(authorizationCode);
			source.setSupressName(false);
			source.setSupressDisplay(false);
			source.setSourceType(sourceDao.getSourceTypeByCode(AppConstants.SOURCETYPE_CODE_ELECTRICITY));
			source.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
			source.setCategory(categoryDao.getCategoryByCode(AppConstants.CATEGORY_CODE_HOME, AppConstants.CATEGORYTYPE_SOURCE));
			
			SourceMode meterMode = sourceDao.getSourceModeByCode(AppConstants.SOURCEMODE_CODE_METER);
			source.setSourceMode(meterMode);
			
			sourceDao.save(source);
			source = sourceDao.findSourceBySourceCode(authorizationCode);
			
			Status activeStatus = statusDao.getStatusByCode(AppConstants.STATUS_CODE_ACTIVE);
			
			AccountSource accountSource = new AccountSource();
			accountSource.setUser(user);
			accountSource.setSource(source);
			accountSource.setStatus(activeStatus);
			accountSourceDao.save(accountSource);
			
		}catch(Exception e){
			System.out.println("Source save failed");
		}
		//store authorization code to
		ProviderAccount pa = new ProviderAccount();
		
		ProviderSource ps = new ProviderSource();
		Provider provider = providerDao.getProvider(AppConstants.PROVIDER_GREENBUTTON);
		if (provider != null){
			
			
			pa.setUser(user);
			pa.setProvider(provider);
			pa.setCode(authorizationCode);
			pa.setDeviceName(deviceName);
			try{
				providerAccountDao.save(pa);
				rv.fill(AppConstants.SUCCESS, "Successfully saved Electricity account");
			}catch(Exception e){
				e.printStackTrace();
				rv.fill(AppConstants.FAILURE, "Error while saving Electricity account");
			}
			
		}else{
			rv.fill(AppConstants.FAILURE, "No Electricity Provider");
		}
		
		if (provider != null){
			
			ps.setSource(source);
			ps.setProvider(provider);
			try{
				providerSourceDao.save(ps);
				rv.fill(AppConstants.SUCCESS, "Successfully saved Electricity account");
			}catch(Exception e){
				e.printStackTrace();
				rv.fill(AppConstants.FAILURE, "Error while saving Electricity account");
			}
			
		}else{
			rv.fill(AppConstants.FAILURE, "No Electricity Provider");
		}
		
		return rv;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView storeMovesCode(String domainName, Long accountId, String deviceName, String authorizationCode){
		ResultView rv = new ResultView();
		//Get communityId
		//User user = userDao.findByName(accountEmail, communityId, mode);
		User user = userDao.findById(accountId);
		
		//check if a user has Provider Acccount if it is then delete
		/*
		try{
			checkMovesAccount(user);
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		rv = storeMovesHelper(domainName, user, "Moves Walking Device", authorizationCode, AppConstants.SOURCETYPE_CODE_WALKING);
		rv = storeMovesHelper(domainName, user, "Moves Running Device", "RUNNING_" + authorizationCode, AppConstants.SOURCETYPE_CODE_RUNNING);
		rv = storeMovesHelper(domainName, user, "Moves Cycling Device", "CYCLING_" + authorizationCode, AppConstants.SOURCETYPE_CODE_CYCLING);
		return rv;
	}
	/*
	private void checkMovesAccount(User user){
		Provider provider = providerDao.getProvider(AppConstants.PROVIDER_MOVES);
		List<ProviderAccount> movesAccounts = providerAccountDao.getProviderAccountsByProviderAndUser(provider.getId(), user.getId());
		
		if (!movesAccounts.isEmpty()){
			for (ProviderAccount movesAccount : movesAccounts){
				//Delete this movesAccount
				providerAccountDao.deleteProviderAccount(movesAccount.getId());
			}
		}
	}
	*/
	/**
	 * Insert or update Moves devices for a given user
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-02
	 * @param domainName
	 * @param user
	 * @param deviceName
	 * @param authorizationCode
	 * @param sourceTypeCode
	 */
	private ResultView storeMovesHelper(String domainName, User user, String deviceName, String authorizationCode, String sourceTypeCode){
		ResultView rv = new ResultView();
		Source source = new Source();
		//Create source first
		boolean isUpdate = false;
		try{
			
			//Check if account has source
			Provider provider = providerDao.getProvider(AppConstants.PROVIDER_MOVES);
			List<Source> sources = providerDao.getProviderSourcesBySourceType(user.getId(), provider.getId(), sourceTypeCode);
			Status activeStatus = statusDao.getStatusByCode(AppConstants.STATUS_CODE_ACTIVE);
			
			if (sources.isEmpty()){
				source.setName(deviceName);
				source.setCode(authorizationCode);
				source.setSupressName(false);
				source.setSupressDisplay(false);
				source.setSourceType(sourceDao.getSourceTypeByCode(sourceTypeCode));
				source.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
				source.setCategory(categoryDao.getCategoryByCode(AppConstants.CATEGORY_CODE_PERSONAL, AppConstants.CATEGORYTYPE_SOURCE));
				
				SourceMode meterMode = sourceDao.getSourceModeByCode(AppConstants.SOURCEMODE_CODE_METER);
				source.setSourceMode(meterMode);
				
				sourceDao.save(source);
				source = sourceDao.findSourceBySourceCode(authorizationCode);
				
				AccountSource accountSource = new AccountSource();
				accountSource.setUser(user);
				accountSource.setSource(source);
				accountSource.setStatus(activeStatus);
				accountSourceDao.save(accountSource);
			}else{
				//There is source attach to this provider. So we wouldn't create a new entry but update.
				isUpdate = true;
				source = sources.get(0);
				source.setName(deviceName);
				source.setCode(authorizationCode);
				source.setSupressName(false);
				source.setSupressDisplay(false);
				source.setStatus(activeStatus);
				sourceDao.save(source, true);
				
				AccountSource accountSource = accountSourceDao.findAccountSource(user.getId(), source.getId());
				accountSource.setStatus(activeStatus);
				accountSourceDao.save(accountSource, true);
			}
			
			
			try{
				joinAutoCampaign(user, domainName, sourceTypeCode);
			}catch(Exception e){
			}
			
			
		}catch(Exception e){
		}
		
		//Insert provider account and provider source if source has been created but not update.
		Provider provider = providerDao.getProvider(AppConstants.PROVIDER_MOVES);
		if (provider != null){
			if (!isUpdate){
				//store authorization code to
				ProviderAccount pa = new ProviderAccount();
				pa.setUser(user);
				pa.setProvider(provider);
				pa.setCode(authorizationCode);
				pa.setDeviceName(deviceName);
				providerAccountDao.save(pa);
				
			}else{
				//Update provider account with newest code
				List<ProviderAccount> pas = providerAccountDao.getProviderAccountsByProviderAndUser(provider.getId(), user.getId());
				
				//A user should have 3 Moves sources
				if (!pas.isEmpty() && pas.size() == 3){
					ProviderAccount pa = null;
					if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_WALKING)){
						pa = pas.get(0);
					}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_RUNNING)){
						pa = pas.get(1);
					}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)){
						pa = pas.get(2);
					}
					
					if (pa != null){
						pa.setCode(authorizationCode);
						pa.setDeviceName(deviceName);
						providerAccountDao.save(pa, true);
						
						System.out.println("Provider Account updated... " + pa.getId());
					}
				}
			}
		}else{
			rv.fill(AppConstants.FAILURE, "No Moves Provider");
		}
		
		ProviderSource ps = new ProviderSource();
		if (provider != null){
			ps.setSource(source);
			ps.setProvider(provider);
			if (!isUpdate){
				try{
					providerSourceDao.save(ps);
					rv.fill(AppConstants.SUCCESS, "Successfully saved Moves account");
				}catch(Exception e){
					e.printStackTrace();
					rv.fill(AppConstants.FAILURE, "Error while saving Moves account");
				}
			}
		}else{
			rv.fill(AppConstants.FAILURE, "No Moves Provider");
		}
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Provider> findUtilitiyProvider(String providerType){
		List<Provider> ps = new ArrayList<Provider>();
		List<Provider> providers = providerDao.getProvidersByType(providerType);
		
		if (providers != null) ps.addAll(providers);
		
		//Add Other
		Provider provider = new Provider();
		provider.setId(-1l);
		ProviderType pt = providerDao.getProviderType(providerType);
		provider.setProviderType(pt);
		provider.setName("Other");
		provider.setCode("OTHER");
		
		ps.add(provider);
		return ps;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView storeUtilityConnector(UtilityForm utilityForm){
		ResultView rv = new ResultView();
		
		UtilityConnectionRequest ucr = new UtilityConnectionRequest();
		
		UtilityType ut = utilityConnectionRequestDao.getUtilityType(utilityForm.getUtilityType());
		User user = userDao.findById(utilityForm.getAccountId());
		ucr.setUtilityType(ut);
		ucr.setProvider(utilityForm.getProvider());
		ucr.setUser(user);
		ucr.setCity(utilityForm.getCity());
		ucr.setPostalCode(utilityForm.getPostalCode());
		ucr.setAccountNumber(utilityForm.getAccountNumber());
		ucr.setPassword(utilityForm.getPassword());
		
		try{
			utilityConnectionRequestDao.save(ucr);
			rv.fill(AppConstants.SUCCESS, "Successfully recorded a page");
		}catch(Exception e){
			rv.fill(AppConstants.FAILURE, "Error happend during the save utility connection");
		}
		return rv;
	}
	
	private void joinAutoCampaign(User user, String domain, String sourceTypeCode){
		System.out.println("Entered auto campaign : " + new Date());
		Long communityId = communityDao.getCommunitId(domain);
		
		//Get user segments
		List<Segment> segments = segmentService.getMemberSegment(user);
		//Get available auto join campaign
		List<Category> categories = categoryDao.getCategories(AppConstants.CATEGORYTYPE_CAMPAIGN);
		
		List<Campaign> campaigns = new ArrayList<Campaign>();
		
		if (categories != null && categories.size() > 0){
			for (Category category : categories){
				if (segments != null && segments.size() > 0){
					for (Segment segment : segments){
						System.out.println("segment ID :: " + segment.getId() + " : " + campaigns.size());
						List<Campaign> tempCampaigns = campaignDao.getCampaignsBySegmentWithCategory(segment.getId(), category.getId());
						for (Campaign tempCampaign : tempCampaigns){
							System.out.println("Temp Campaign : : " + tempCampaign.getName() + " : " + tempCampaign.getCampaignTemplate().getMeterRequired() + " : " + tempCampaign.getAutoJoin());
							if (tempCampaign.getCampaignTemplate().getMeterRequired() && tempCampaign.getAutoJoin() && tempCampaign.getCampaignTemplate().getSourceType().getCode().equals(sourceTypeCode)){
								campaigns.add(tempCampaign);
							}
						}
					}
				}
			}
		}
		
		//create account campaign
		for (Campaign campaign: campaigns){
			System.out.println("CAMPAGIN :::: " + campaign.getName());
			
			AccountCampaign ac = campaignDao.getAccountCampaign(user.getId(), campaign.getId());
			
			if (ac == null){
				ac = new AccountCampaign();
				ac.setUser(user);
				ac.setCampaign(campaign);
				CampaignTarget campaignTarget = campaignDao.getCampaignTarget(campaign.getId());
				
				/*
				if (campaignTarget != null) ac.setCampaignTarget(campaignTarget.getTarget());
				else ac.setCampaignTarget(100);
				*/
				//ac.setFundraiseTarget(100);
				
				accountCampaignDao.save(ac);
				
				//send an auto joined email
				
				Integer offerPoints = 0;
				List<Offer> offers = getCampaignOffer(campaign.getId(), campaign.getCommunityId(), campaign.getEndDate(), user.getId());
				if (offers != null){
					
					
					if (offers != null && offers.size() > 0){
						for (Offer offer : offers){
							offerPoints += offer.getValue().intValue();
						}
					}
				}
				
				try{
					notificationService = new NotificationServiceImpl();
					notificationService.notifyAutoCampaignJoin(user, campaign, domain, offerPoints);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			
		}
		
	}
	
	private List<Offer> getCampaignOffer(Long campaignId, Long communityId, Date endDate, Long memberId){
		
		List<Segment> segments = null;
		
		if (memberId != null) segments = segmentService.getMemberSegment(memberId);
		else segments = segmentService.getDefaultSegment(communityId);
		
		
		List<Offer> offers = offerDao.getOfferByBehaviorActions(AppConstants.BEHAVIOR_TRACKERTYPE_CAMPAIGN, segments, campaignId+"", endDate);
		
		if (offers != null && offers.size() > 0){
			List<Offer> temp = new ArrayList<Offer>();
			for (Offer o : offers){
				if (o.getSegment() == null && o.getCommunity() != null && o.getCommunity().getId() != communityId){
					
				}else{
					temp.add(o);
				}
			}
			
			offers = temp;
		}
		
		return offers;
	}

	
}
