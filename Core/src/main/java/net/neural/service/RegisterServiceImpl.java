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
import net.zfp.entity.Source;
import net.zfp.entity.SourceHierarchy;
import net.zfp.entity.SourceType;
import net.zfp.entity.Status;
import net.zfp.entity.User;
import net.zfp.entity.account.AccountCampaign;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.campaign.CampaignTarget;
import net.zfp.entity.carbon.CarbonFootprint;
import net.zfp.entity.category.Category;
import net.zfp.entity.community.Community;
import net.zfp.entity.device.DeviceSourceType;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.provider.Provider;
import net.zfp.entity.provider.ProviderAccount;
import net.zfp.entity.provider.ProviderSource;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.segment.SegmentType;
import net.zfp.entity.source.SourceMode;
import net.zfp.entity.tips.Tips;
import net.zfp.form.DeviceForm;
import net.zfp.notify.NotificationService;
import net.zfp.notify.impl.NotificationServiceImpl;
import net.zfp.service.RegisterService;
import net.zfp.util.AppConstants;
import net.zfp.view.DeviceView;
import net.zfp.view.ResultView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class RegisterServiceImpl implements RegisterService {
	@Resource
    private SegmentService segmentService;
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Source> sourceDao;
	@Resource
	private EntityDao<Segment> segmentDao;
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<Offer> offerDao;
	@Resource
	private EntityDao<Campaign> campaignDao;
	@Resource
	private EntityDao<AccountCampaign> accountCampaignDao;
	@Resource
	private EntityDao<SourceHierarchy> sourceHierarchyDao;
	@Resource
	private EntityDao<AccountSource> accountSourceDao;
	@Resource
	private EntityDao<ProviderSource> providerSourceDao;
	@Resource
	private EntityDao<Category> categoryDao;
	@Resource
	private EntityDao<Status> statusDao;
	@Resource
	private EntityDao<Provider> providerDao;
	@Inject
    private NotificationService notificationService;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<DeviceView> getRegisteredDevice(String domainName, Long accountId, String sourceType, String categoryCode ){
		List<DeviceView> dvs = new ArrayList<DeviceView>();
		//get communityId based on domain Name
		Long communityId = getCommunityId(domainName);
		//In AccountSource table, check if there are any sources associated with account
		List<Source> sources = userDao.findAllSourceByAccountAndType(accountId, sourceType);
		int counter = 1;
		for (Source source : sources){
			if (source.getCategory().getCode().equals(categoryCode) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				
				DeviceView dv = new DeviceView();
				dv.setCode(source.getCode());
				dv.setName(source.getName());
				
				AccountSource as = accountSourceDao.findAccountSource(accountId, source.getId());
				if (as.getStatus() != null){
					dv.setStatus(as.getStatus().getCode());
				}else{
					dv.setStatus(AppConstants.STATUS_CODE_ACTIVE);
				}
				
				dv.setDefaultDevice(as.isDefaultSource());
				
				dvs.add(dv);
				System.out.println("added");
				counter++;
			}
		}
		
		return dvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView addDeviceMobile(DeviceForm deviceForm){
		System.out.println("Adding Devices!");
		ResultView rv = new ResultView();
		//get communityId based on domain Name
		//Long communityId = getCommunityId(deviceForm.getDomain());
		//Get AccountId
		User user = userDao.findById(deviceForm.getAccountId());
		
		//Check if you have household source if not create 
		List<Source> sources = userDao.findSourceByAccount(user.getId());
		Source householdSource = null;
		for (Source source : sources){
			System.out.println(source.getId());
			
			if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_HOUSEHOLD) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				householdSource = source;
				break;
			}
		}
		
		if ( householdSource == null){
			//Create a source and store this at source hierarchy
			String householdSourceCode = user.getFirstName().toUpperCase() + "_" + user.getLastName().toUpperCase()+"_"+ AppConstants.CATEGORY_CODE_HOUSEHOLD;
			householdSource = new Source();
			householdSource.setName(user.getFirstName() + " " + user.getLastName() +"'s Household");
			householdSource.setCode(householdSourceCode);
			Category category = categoryDao.getCategoryByCode(AppConstants.CATEGORY_CODE_HOUSEHOLD, AppConstants.CATEGORYTYPE_SOURCE);
			SourceType sourceType = sourceDao.getSourceTypeByCode(AppConstants.SOURCETYPE_CODE_NONE);
			SourceMode sourceMode = sourceDao.getSourceModeByCode(AppConstants.SOURCEMODE_CODE_CLUSTER);
			householdSource.setSourceMode(sourceMode);
			householdSource.setSourceType(sourceType);
			householdSource.setCategory(category);
			householdSource.setSupressName(false);
			householdSource.setSupressDisplay(false);
			householdSource.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
			
			sourceDao.save(householdSource);
			householdSource = sourceDao.findSourceBySourceCode(householdSourceCode);
			//Source Hierarchy
			SourceHierarchy sourceHierarchy = new SourceHierarchy();
			sourceHierarchy.setDepth(0); //this is parent
			sourceHierarchy.setCommunity_id(user.getDefaultCommunity());
			sourceHierarchy.setSource(householdSource);
			
			sourceHierarchyDao.save(sourceHierarchy);
			
		}
		
		//Based on a serial number check if this source exists
		Source source = sourceDao.findSourceBySourceCodeAndProvider(deviceForm.getField1(), deviceForm.getProvider());
		
		//SourceMode virtualSourceMode = sourceDao.getSourceModeByCode(AppConstants.SOURCEMODE_CODE_VIRTUAL);
		SourceMode meterSourceMode = sourceDao.getSourceModeByCode(AppConstants.SOURCEMODE_CODE_METER);
		
		//Get source type from provider device...
		List<DeviceSourceType> deviceSourceTypes = sourceDao.getSourceTypeByProviderDevice(deviceForm.getProviderDeviceId());
		
		if (deviceSourceTypes.isEmpty()){
			rv.fill(AppConstants.FAILURE, "Invalid provider device");
			return rv;
		}
		
		//Create a source if there is no source in database and update flag is null or false
		if (source == null && (deviceForm.getUpdate() == null || !deviceForm.getUpdate())){
			
			for (DeviceSourceType deviceType : deviceSourceTypes){
				Source newSource = new Source();
				newSource.setName(deviceForm.getName());
				if (deviceSourceTypes.size() == 1) newSource.setCode(deviceForm.getField1());
				else newSource.setCode(deviceType.getSourceType().getCode().toUpperCase()+"_" + deviceForm.getField1());
				
				newSource.setSourceType(deviceType.getSourceType());
				newSource.setCategory(deviceType.getCategory());
				newSource.setSupressName(false);
				newSource.setSupressDisplay(false);
				newSource.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
				
				sourceDao.save(newSource);
				
				source = sourceDao.findSourceBySourceCode(deviceForm.getField1());
				
				String providerCode = AppConstants.PROVIDER_EYEDRO;
				if (deviceForm.getProvider() != null) providerCode = deviceForm.getProvider();
				Provider provider = providerSourceDao.getProvider(providerCode);
				
				ProviderSource ps = new ProviderSource();
				ps.setProvider(provider);
				ps.setSource(source);
				
				providerSourceDao.save(ps);
				
			}
			
		}else{
			
			if (source == null){
				//we want to update the source
				//Get Provider
				String providerCode = AppConstants.PROVIDER_EYEDRO;
				if (deviceForm.getProvider() != null) providerCode = deviceForm.getProvider();
				Provider provider = providerSourceDao.getProvider(providerCode);
				
				//Get AccountSource -Provider
				List<Source> accountSource = providerSourceDao.getProviderSources(deviceForm.getAccountId(), provider.getId());
				//Update this source with information from form
				if (!accountSource.isEmpty()){
					source = accountSource.get(0);
					source.setCode(deviceForm.getField1());
				}
			}
			source.setName(deviceForm.getName());
			
			for (DeviceSourceType deviceType : deviceSourceTypes){
				if (deviceType.getSourceType().equals(AppConstants.SOURCETYPE_CODE_DRIVING)){
					source.setCode(deviceForm.getField1());
				}
			}
			
			//Possibly the source might have been deactivated. We should make it active
			source.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
		}
		
		/*
		if (deviceForm.getProvider() != null && deviceForm.getProvider().equals(AppConstants.PROVIDER_NEWMARKET_HYDRO)){
			source.setSourceMode(virtualSourceMode);
		}else{
			source.setSourceMode(meterSourceMode);
		}
		*/
		
		source.setSourceMode(meterSourceMode);
		source.setLastModified(new Date());
		sourceDao.save(source, true);
		
		for (DeviceSourceType deviceType : deviceSourceTypes){
			joinAutoCampaign(user, deviceForm.getDomain(), deviceType.getSourceType().getCode());
		}
		
		if (!deviceForm.getUpdate()){
		
			SourceHierarchy sourceHierarchy = new SourceHierarchy();
			sourceHierarchy.setDepth(1); // first child
			sourceHierarchy.setCommunity_id(user.getDefaultCommunity());
			sourceHierarchy.setParent_id(householdSource.getId());
			sourceHierarchy.setSource(source);
			
			sourceHierarchyDao.save(sourceHierarchy);
		}
		//Check if this is already connected
		AccountSource accountSource = userDao.findAccountSource(user.getId(), householdSource.getId());
		if (accountSource == null){
			accountSource = new AccountSource();
			accountSource.setUser(user);
			accountSource.setSource(householdSource);
			accountSource.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
			try{
				accountSourceDao.save(accountSource);
				rv.fill(AppConstants.SUCCESS, "Success!");
			}catch (Exception e){
				rv.fill(AppConstants.FAILURE, "Error while saving to account source");
			}
		}
		
		Status active = statusDao.getStatusByCode(AppConstants.STATUS_CODE_ACTIVE);
		
		accountSource = userDao.findAccountSource(user.getId(), source.getId());
		if (accountSource == null){
			accountSource = new AccountSource();
			accountSource.setUser(user);
			accountSource.setSource(source);
			accountSource.setStatus(active);
			try{
				accountSourceDao.save(accountSource);
				rv.fill(AppConstants.SUCCESS, "Success!");
			}catch (Exception e){
				rv.fill(AppConstants.FAILURE, "Error while saving to account source");
			}
		}else{
			accountSource.setLastModified(new Date());
			accountSource.setStatus(active);
			try{
				accountSourceDao.save(accountSource, true);
				rv.fill(AppConstants.SUCCESS, "Success!");
			}catch (Exception e){
				rv.fill(AppConstants.FAILURE, "Error while saving to account source");
			}
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView addDevice(DeviceForm deviceForm){
		System.out.println("Adding Devices!");
		ResultView rv = new ResultView();
		//get communityId based on domain Name
		//Long communityId = getCommunityId(deviceForm.getDomain());
		//Get AccountId
		User user = userDao.findById(deviceForm.getAccountId());
		
		//Check if you have household source if not create 
		List<Source> sources = userDao.findSourceByAccount(user.getId());
		Source householdSource = null;
		for (Source source : sources){
			System.out.println(source.getId());
			
			if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_HOUSEHOLD) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				householdSource = source;
				break;
			}
		}
		
		if ( householdSource == null){
			//Create a source and store this at source hierarchy
			String householdSourceCode = user.getFirstName().toUpperCase() + "_" + user.getLastName().toUpperCase()+"_"+ AppConstants.CATEGORY_CODE_HOUSEHOLD;
			householdSource = new Source();
			householdSource.setName(user.getFirstName() + " " + user.getLastName() +"'s Household");
			householdSource.setCode(householdSourceCode);
			Category category = categoryDao.getCategoryByCode(AppConstants.CATEGORY_CODE_HOUSEHOLD, AppConstants.CATEGORYTYPE_SOURCE);
			SourceType sourceType = sourceDao.getSourceTypeByCode(AppConstants.SOURCETYPE_CODE_NONE);
			SourceMode sourceMode = sourceDao.getSourceModeByCode(AppConstants.SOURCEMODE_CODE_CLUSTER);
			householdSource.setSourceMode(sourceMode);
			householdSource.setSourceType(sourceType);
			householdSource.setCategory(category);
			householdSource.setSupressName(false);
			householdSource.setSupressDisplay(false);
			householdSource.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
			
			sourceDao.save(householdSource);
			householdSource = sourceDao.findSourceBySourceCode(householdSourceCode);
			//Source Hierarchy
			SourceHierarchy sourceHierarchy = new SourceHierarchy();
			sourceHierarchy.setDepth(0); //this is parent
			sourceHierarchy.setCommunity_id(user.getDefaultCommunity());
			sourceHierarchy.setSource(householdSource);
			
			sourceHierarchyDao.save(sourceHierarchy);
			
		}
		
		
		//Based on a serial number check if this source exists
		
		Source source = null;
		//If getUpdate means we already know the source exists, and if flag is off then we want to create a new source
		if (deviceForm.getType().equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY)){
			System.out.println("ELECTRICITY SOURCE!!!");
			if (deviceForm.getSerialNumber() != null) source = sourceDao.findSourceBySourceCodeAndType(deviceForm.getSerialNumber(), AppConstants.SOURCETYPE_CODE_ELECTRICITY);
		}else if (deviceForm.getType().equals(AppConstants.SOURCETYPE_CODE_DRIVING)){
			System.out.println("DRIVING SOURCE!!! " + deviceForm.getUserName());
			if (deviceForm.getUserName() != null) source = sourceDao.findSourceBySourceCodeAndType(deviceForm.getUserName(), AppConstants.SOURCETYPE_CODE_DRIVING);
			if (source == null) System.out.println("Source is empty");
			else System.out.println("Source is not empty :: " + source.getId());
			//System.out.println("SOURCE NAME AND CODE : " + source.getName() + "  : " + source.getCode() + " : " + source.getSourceType().getCode());
		}else{
			System.out.println("ELSE SOURCE!!!");
			if (deviceForm.getSerialNumber() != null) source = sourceDao.findSourceBySourceCode(deviceForm.getSerialNumber());
		}
		
		//SourceMode virtualSourceMode = sourceDao.getSourceModeByCode(AppConstants.SOURCEMODE_CODE_VIRTUAL);
		SourceMode meterSourceMode = sourceDao.getSourceModeByCode(AppConstants.SOURCEMODE_CODE_METER);
		
		System.out.println(meterSourceMode.getCode());
		
		//Create a source if there is no source in database and update flag is null or false
		if (source == null && (deviceForm.getUpdate() == null || !deviceForm.getUpdate())){
			
			//Based on set source 
			if (deviceForm.getType().equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY)){
				Source newSource = new Source();
				newSource.setName(deviceForm.getName());
				newSource.setCode(deviceForm.getSerialNumber());
				//For now save this as home
				//Category category = categoryDao.getCategoryByCode(AppConstants.CATEGORY_CODE_ELECTRICITY);
				Category category = categoryDao.getCategoryByCode(AppConstants.CATEGORY_CODE_HOME, AppConstants.CATEGORYTYPE_SOURCE);
				SourceType sourceType = sourceDao.getSourceTypeByCode(deviceForm.getType());
				System.out.println("Source Type... ! " + sourceType.getCode());
				newSource.setSourceType(sourceType);
				newSource.setCategory(category);
				newSource.setSupressName(false);
				newSource.setSupressDisplay(false);
				newSource.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
				
				sourceDao.save(newSource);
				
				source = sourceDao.findSourceBySourceCode(deviceForm.getSerialNumber().toUpperCase());
				
				String providerCode = AppConstants.PROVIDER_EYEDRO;
				if (deviceForm.getProvider() != null) providerCode = deviceForm.getProvider();
				Provider provider = providerSourceDao.getProvider(providerCode);
				
				ProviderSource ps = new ProviderSource();
				ps.setProvider(provider);
				ps.setSource(source);
				
				providerSourceDao.save(ps);
			}else{
				
				Date created = new Date();
				Source newSource = new Source();
				newSource.setName(deviceForm.getName());
				if (deviceForm.getType().equals(AppConstants.SOURCETYPE_CODE_WALKING)){
					newSource.setCode("NIKETEMPCODE" + created.getTime());
				}else{
					newSource.setCode(deviceForm.getUserName());
				}
				
				newSource.setSupressName(false);
				newSource.setSupressDisplay(false);
				newSource.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
				
				SourceType sourceType = sourceDao.getSourceTypeByCode(deviceForm.getType());
				newSource.setSourceType(sourceType);
				System.out.println(deviceForm.getType() + " ~~~~~~~~~");
				if (deviceForm.getType().equals(AppConstants.SOURCETYPE_CODE_DRIVING)){
					Category category = categoryDao.getCategoryByCode(AppConstants.CATEGORY_CODE_CAR, AppConstants.CATEGORYTYPE_SOURCE);
					System.out.println(category.getId());
					newSource.setCategory(category);
				}else if (deviceForm.getType().equals(AppConstants.SOURCETYPE_CODE_WALKING)){
					Category category = categoryDao.getCategoryByCode(AppConstants.CATEGORY_CODE_PERSONAL, AppConstants.CATEGORYTYPE_SOURCE);
					System.out.println(category.getId());
					newSource.setCategory(category);
				}
				
				sourceDao.save(newSource);
				
				if (deviceForm.getType().equals(AppConstants.SOURCETYPE_CODE_WALKING)){
					source = sourceDao.findSourceBySourceCode("NIKETEMPCODE" + created.getTime());
				}else{
					source = sourceDao.findSourceBySourceCode(deviceForm.getUserName());
				}
				
				
				
				Provider provider = null;
				if (deviceForm.getType().equals(AppConstants.SOURCETYPE_CODE_DRIVING)){
					provider = providerSourceDao.getProvider(AppConstants.PROVIDER_MOJIO);
				}else if (deviceForm.getType().equals(AppConstants.SOURCETYPE_CODE_WALKING)){
					provider = providerSourceDao.getProvider(AppConstants.PROVIDER_NIKEBAND);
				}
				
				ProviderSource ps = new ProviderSource();
				ps.setProvider(provider);
				ps.setSource(source);
				ps.setUsername(deviceForm.getUserName());
				ps.setPassword(deviceForm.getPassword());
				providerSourceDao.save(ps);
			}
		}else{
			
			if (source == null){
				//we want to update the source
				//Get Provider
				String providerCode = AppConstants.PROVIDER_EYEDRO;
				if (deviceForm.getProvider() != null) providerCode = deviceForm.getProvider();
				Provider provider = providerSourceDao.getProvider(providerCode);
				
				//Get AccountSource -Provider
				List<Source> accountSource = providerSourceDao.getProviderSources(deviceForm.getAccountId(), provider.getId());
				//Update this source with information from form
				if (!accountSource.isEmpty()){
					source = accountSource.get(0);
					source.setCode(deviceForm.getSerialNumber());
				}
			}
			source.setName(deviceForm.getName());
			
			if (deviceForm.getType().equals(AppConstants.SOURCETYPE_CODE_DRIVING)){
				source.setCode(deviceForm.getUserName());
			}
			//Possibly the source might have been deactivated. We should make it active
			source.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
		}
		
		/*
		if (deviceForm.getProvider() != null && deviceForm.getProvider().equals(AppConstants.PROVIDER_NEWMARKET_HYDRO)){
			source.setSourceMode(virtualSourceMode);
		}else{
			source.setSourceMode(meterSourceMode);
		}
		*/
		
		source.setSourceMode(meterSourceMode);
		source.setLastModified(new Date());
		sourceDao.save(source, true);
		
		joinAutoCampaign(user, deviceForm.getDomain(), deviceForm.getType());
		
		if (!deviceForm.getUpdate()){
		
			SourceHierarchy sourceHierarchy = new SourceHierarchy();
			sourceHierarchy.setDepth(1); // first child
			sourceHierarchy.setCommunity_id(user.getDefaultCommunity());
			sourceHierarchy.setParent_id(householdSource.getId());
			sourceHierarchy.setSource(source);
			
			sourceHierarchyDao.save(sourceHierarchy);
		}
		//Check if this is already connected
		AccountSource accountSource = userDao.findAccountSource(user.getId(), householdSource.getId());
		if (accountSource == null){
			accountSource = new AccountSource();
			accountSource.setUser(user);
			accountSource.setSource(householdSource);
			accountSource.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
			try{
				accountSourceDao.save(accountSource);
				rv.fill(AppConstants.SUCCESS, "Success!");
			}catch (Exception e){
				rv.fill(AppConstants.FAILURE, "Error while saving to account source");
			}
		}
		
		Status active = statusDao.getStatusByCode(AppConstants.STATUS_CODE_ACTIVE);
		
		accountSource = userDao.findAccountSource(user.getId(), source.getId());
		if (accountSource == null){
			accountSource = new AccountSource();
			accountSource.setUser(user);
			accountSource.setSource(source);
			accountSource.setStatus(active);
			try{
				accountSourceDao.save(accountSource);
				rv.fill(AppConstants.SUCCESS, "Success!");
			}catch (Exception e){
				rv.fill(AppConstants.FAILURE, "Error while saving to account source");
			}
		}else{
			accountSource.setLastModified(new Date());
			accountSource.setStatus(active);
			try{
				accountSourceDao.save(accountSource, true);
				rv.fill(AppConstants.SUCCESS, "Success!");
			}catch (Exception e){
				rv.fill(AppConstants.FAILURE, "Error while saving to account source");
			}
		}
		
		return rv;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView removeDevice(String domainName, Long accountId, String sourceCode){
		System.out.println("Entered Removing Devices...!!");
		ResultView rv = new ResultView();
		//get communityId based on domain Name
		Long communityId = getCommunityId(domainName);
		//get account based on communityId
		//Get AccountId
		User user = userDao.findById(accountId);
		
		Source source = sourceDao.findSourceBySourceCode(sourceCode);
		
		Status status = statusDao.getStatusByCode(AppConstants.STATUS_CODE_DEACTIVATED);
		
		//source is moves provider.. then get all sources
		Provider movesProvider = providerDao.getProvider(AppConstants.PROVIDER_MOVES);
		List<ProviderAccount> MovesProviderAccounts = providerDao.getProviderAccountsByProviderAndUserCode(movesProvider.getId(), user.getId(), sourceCode);
		
		Provider zfpProvider = providerDao.getProvider(AppConstants.PROVIDER_ZFP);
		List<ProviderSource> ZFPProviderSources = providerDao.getProviderSourceByProviderAndUserCode(zfpProvider.getId(), user.getId(), sourceCode);
		
		boolean isMovesSource = false;
		boolean isZFPSource = false;
		
		if (!MovesProviderAccounts.isEmpty()){
			isMovesSource = true;
		}else if (!ZFPProviderSources.isEmpty()){
			isZFPSource = true;
		}
		
		if (isMovesSource){
			
			List<ProviderAccount> pas = providerDao.getProviderAccountsByProviderAndUser(movesProvider.getId(), user.getId());
			
			if (!pas.isEmpty()){
				for (ProviderAccount pa : pas){
					Source movesSource = sourceDao.findSourceBySourceCode(pa.getCode());
					AccountSource accountSource = userDao.findAccountSource(user.getId(), movesSource.getId());
					
					if (accountSource != null){
						try{
							accountSource.setStatus(status);
							accountSource.setLastModified(new Date());
							accountSource.setDefaultSource(false);
							accountSourceDao.save(accountSource, true);
							System.out.println("Successfully deactivated!!");
							
							movesSource.setLastModified(new Date());
							movesSource.setStatus(status);
							sourceDao.save(movesSource, true);
							
							rv.fill(AppConstants.SUCCESS, "Source has been deactivated");
						}catch(Exception e){
							e.printStackTrace();
							rv.fill(AppConstants.FAILURE, "Error while removing account source");
						}
					}
					
				}
			}
		}else if (isZFPSource){
			List<Source> pas = providerDao.getProviderSources(user.getId(), zfpProvider.getId());
			
			if (!pas.isEmpty()){
				System.out.println("Size :: " + pas.size());
				for (Source zfpSource : pas){
					System.out.println("ZFP Source..." + zfpSource.getId());
					
					AccountSource accountSource = userDao.findAccountSource(user.getId(), zfpSource.getId());
					
					if (accountSource != null){
						try{
							accountSource.setStatus(status);
							accountSource.setLastModified(new Date());
							accountSource.setDefaultSource(false);
							accountSourceDao.save(accountSource, true);
							System.out.println("Successfully deactivated!!");
							
							zfpSource.setLastModified(new Date());
							zfpSource.setStatus(status);
							sourceDao.save(zfpSource, true);
							
							rv.fill(AppConstants.SUCCESS, "Source has been deactivated");
						}catch(Exception e){
							e.printStackTrace();
							rv.fill(AppConstants.FAILURE, "Error while removing account source");
						}
					}
					
				}
			}
		}else{
			AccountSource accountSource = userDao.findAccountSource(user.getId(), source.getId());
			
			System.out.println("Account Source !! " + accountSource.getId());
			try{
				accountSource.setStatus(status);
				accountSource.setLastModified(new Date());
				accountSource.setDefaultSource(false);
				accountSourceDao.save(accountSource, true);
				System.out.println("Successfully deactivated!!");
				
				source.setLastModified(new Date());
				source.setStatus(status);
				sourceDao.save(source, true);
				
				rv.fill(AppConstants.SUCCESS, "Source has been deactivated");
			}catch(Exception e){
				e.printStackTrace();
				rv.fill(AppConstants.FAILURE, "Error while removing account source");
			}
		}
		
		
		
		
		return rv;
		
	}
	
	private Long getCommunityId(String serverName){
		Long communityId = null;
		try {
			communityId = domainDao.getCommunitId(serverName);
		}catch (NoResultException ne){
			System.out.println("************ Community no found.");
		}catch (Exception e) {
			System.out.println("************ Get error in MediaStreamServiceImpl for Community. Error: " + e);
		}
		
		return communityId;
	}
	
	private void joinAutoCampaign(User user, String domain, String sourceTypeCode){
		System.out.println("Entered auto campaign : " + new Date());
		Long communityId = communityDao.getCommunitId(domain);
		
		//Get user segments
		List<Segment> segments =segmentService.getMemberSegment(user);
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
			AccountCampaign ac = new AccountCampaign();
			ac.setUser(user);
			ac.setCampaign(campaign);
			CampaignTarget campaignTarget = campaignDao.getCampaignTarget(campaign.getId());
			/*
			if (campaignTarget != null) ac.setCampaignTarget(campaignTarget.getTarget());
			else ac.setCampaignTarget(100);
			*/
			//ac.setFundraiseTarget(100);
			
			
			accountCampaignDao.save(ac);
			//good life is good send an auto joined email
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
