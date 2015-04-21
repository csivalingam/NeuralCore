/**
 * 
 */
package net.zfp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.zfp.dao.EntityDao;
import net.zfp.entity.Domain;
import net.zfp.entity.Feedback;
import net.zfp.entity.FootprintType;
import net.zfp.entity.Image;
import net.zfp.entity.PaymentTransaction;
import net.zfp.entity.PortalType;
import net.zfp.entity.Source;
import net.zfp.entity.SourceType;
import net.zfp.entity.Status;
import net.zfp.entity.Translation;
import net.zfp.entity.User;
import net.zfp.entity.VehicleType;
import net.zfp.entity.account.AccountGroup;
import net.zfp.entity.account.AccountGroupCommunicationStatus;
import net.zfp.entity.account.AccountCampaign;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.carbon.CarbonFootprint;
import net.zfp.entity.carbon.CarbonTransaction;
import net.zfp.entity.community.Community;
import net.zfp.entity.community.CommunityFeature;
import net.zfp.entity.community.CommunityPortal;
import net.zfp.entity.community.CommunityPortalTheme;
import net.zfp.entity.feature.Feature;
import net.zfp.entity.feature.FeatureTravelType;
import net.zfp.entity.group.Groups;
import net.zfp.entity.portal.PortalLayout;
import net.zfp.entity.salesorder.SalesOrder;
import net.zfp.form.FootprintForm;
import net.zfp.form.TranslationForm;
import net.zfp.notify.NotificationService;
import net.zfp.notify.impl.NotificationServiceImpl;
import net.zfp.service.UtilityService;
import net.zfp.util.AppConstants;
import net.zfp.util.ImageUtil;
import net.zfp.util.SystemProperties;
import net.zfp.util.TextUtil;
import net.zfp.view.DeviceView;
import net.zfp.view.FeatureView;
import net.zfp.view.GaugeView;
import net.zfp.view.PortalLayoutView;
import net.zfp.view.ResultView;
import net.zfp.view.TrackingView;
import net.zfp.view.TranslationView;
import net.zfp.view.TravelTypeView;
import net.zfp.view.UserView;
import net.zfp.view.VehicleView;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;


/**
 * @author henry
 *
 */
public class UtilityServiceImpl implements UtilityService {

	@Resource
	private EntityDao<Domain> domainDao;

	@Resource
	private EntityDao<Translation> translationDao;

	@Resource
	private EntityDao<Feedback> feedbackDao;

	@Resource
	private EntityDao<Image> imageDao;
	
	@Resource
	private EntityDao<CarbonFootprint> carbonFootprintDao;
	
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<SalesOrder> salesOrderDao;
	@Resource
	private EntityDao<PortalType> portalTypeDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Groups> groupsDao;
	@Resource
	private EntityDao<Campaign> campaignDao;
	@Resource
	private EntityDao<AccountGroupCommunicationStatus> accountGroupCommunicationStatusDao;
	@Resource
	private EntityDao<AccountCampaign> accountCampaignDao;
	@Resource
	private EntityDao<AccountGroup> accountGroupDao;
	@Resource
    private EntityDao<CarbonTransaction> carbonTransactionDao;
	@Inject
    private NotificationService notificationService;
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView updateStatus(String emails, String uid, Long groupId, Long campaignId, Long statusId){
		ResultView rv = new ResultView();
		try{
			String[] emailArray = emails.split(";");
			
			for (String email : emailArray){
				User user = userDao.findActiveUserByEmail(email);
				if (user != null){
					System.out.println("Email Array !! : " + emailArray.length);
					if (uid.equals("null")){
						AccountGroupCommunicationStatus ag = null;
						ag = accountGroupCommunicationStatusDao.getAccountGroupCommunicationStatus(user.getId(), groupId, campaignId);
						Status status = accountGroupCommunicationStatusDao.getStatus(statusId);
						if (ag != null){
							ag.setStatus(status);
							accountGroupCommunicationStatusDao.save(ag, true);
							rv.setResultCode(AppConstants.SUCCESS);
						}else{
							ag = new AccountGroupCommunicationStatus();
							ag.setUser(user);
							Campaign campaign = campaignDao.getCampaign(campaignId);
							Groups group = groupsDao.getGroup(groupId);
							if ( group != null){
								System.out.println("Groups does exists!");
								ag.setGroups(group);
								ag.setStatus(status);
								ag.setCampaign(campaign);
								accountGroupCommunicationStatusDao.save(ag);
								rv.setResultCode(AppConstants.SUCCESS);
							}
							else System.out.println("Group does not exist");
							
						}
					}else{
						//Check if uid matches...
						if (user.getUid() != null && user.getUid().equals(uid)){
						
							AccountGroupCommunicationStatus ag = accountGroupCommunicationStatusDao.getAccountGroupCommunicationStatus(user.getId(), groupId,campaignId);
							Status status = accountGroupCommunicationStatusDao.getStatus(statusId);
							if (ag != null){
								ag.setStatus(status);
								accountGroupCommunicationStatusDao.save(ag, true);
								rv.setResultCode(AppConstants.SUCCESS);
							}else{
								ag = new AccountGroupCommunicationStatus();
								ag.setUser(user);
								Groups group = groupsDao.getGroup(groupId);
								Campaign campaign = campaignDao.getCampaign(campaignId);
								if ( group != null) System.out.println("Groups does exists!");
								else System.out.println("Group does not exist");
								ag.setGroups(group);
								ag.setCampaign(campaign);
								ag.setStatus(status);
								accountGroupCommunicationStatusDao.save(ag);
								rv.setResultCode(AppConstants.SUCCESS);
							}
						}else{
							rv.setResultCode(AppConstants.FAILURE);
						}
					}
				}else{
					rv.setResultCode(AppConstants.FAILURE);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			rv.setResultCode(AppConstants.FAILURE);
		}
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public PortalLayoutView getPortalLayout(String serverName, String portalName){
		
		System.out.println("servername " + serverName + " : " + portalName);
		
		PortalLayoutView plv = new PortalLayoutView();
		ResultView rv = new ResultView();
		
		if (serverName == null || portalName == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			plv.setResult(rv);
			return plv;
		}
		
		//get community portal layout theme
		Long communityId = getCommunityId(serverName);
		if (communityId == null){
			rv.fill(AppConstants.FAILURE,"Invalid domain.");
			plv.setResult(rv);
			return plv;
		}
		
		PortalLayout pl = null;
		Long portalTypeId = portalTypeDao.findPortalTypeListId(portalName);
		if (portalTypeId == null){
			rv.fill(AppConstants.FAILURE, "Invalid portal type.");
			plv.setResult(rv);
			return plv;
		}
		
		try{
			
			System.out.println(communityId + " : " + portalTypeId);
			
			CommunityPortal cp = communityDao.getCommunityPortal(communityId, portalTypeId);
			
			if (cp != null){
				System.out.println(cp.getId());
				CommunityPortalTheme cpt = communityDao.getCommunityPortalTheme(cp.getId());
				if (cpt != null){
					System.out.println(cpt.getId());
					pl = portalTypeDao.findPortalLayoutByCommunityPortalThemeId(cpt.getId());
				}
			}
			
			if (pl != null){
				plv = new PortalLayoutView(pl);
				System.out.println("PortalLayout ID : " + pl.getId());
			}else{
				plv = new PortalLayoutView(1);
				System.out.println("Portal Layout not exists");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			rv.fill(AppConstants.FAILURE, e.getMessage());
			plv.setResult(rv);
			return plv;
			
		}
		
		return plv;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView sendGroupInvitationEmail(String emails, Long groupId, Long memberId){
		ResultView rv = new ResultView();
		
		try{
			String[] emailArray = emails.split(";");
			notificationService = new NotificationServiceImpl();
			
			for (String email : emailArray){
				
				//From email get user name...
				User invitee = userDao.findActiveUserByEmail(email);
				
				AccountGroup ag = accountGroupDao.getAccountGroup(invitee.getId(), groupId);
				if (ag != null){
					
					User inviter = userDao.findById(memberId);
					
					boolean isEmailSuccess = false;
					try{
						//notificationService.notifyGroupJoin(invitee, inviter, ag.getId());
						//Beyond this, no error invitation success change the status
						isEmailSuccess = true;
						rv.setResultCode(AppConstants.SUCCESS);
					}catch(Exception e){
						isEmailSuccess = false;
						
					}
					
					Status status = null;
					if (isEmailSuccess){
						status = accountCampaignDao.getStatusByCode(AppConstants.STATUS_CODE_PENDING_APPROVAL);
						ag.setStatus(status);
					}else{
						//status = accountCampaignDao.getStatusByCode(AppConstants.STATUS_CODE_SPONSORSHIP_REQUESTED_FAILED);
						//ag.setStatus(status);
					}
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			rv.setResultCode(AppConstants.FAILURE);
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView sendSponsorEmail(String emails, Long groupId, Long campaignId, String fromEmail, String fromName, String subject, String content, String domainName){
		System.out.println("Sponsor list!");
		ResultView rv = new ResultView();
		try{
			String[] emailArray = emails.split(";");
			List<String> to = new ArrayList<String>();
			notificationService = new NotificationServiceImpl();
			
			for (String email : emailArray){
				
				//From email get user name...
				User user = userDao.findActiveUserByEmail(email);
				String username = "";
				if (user.getFirstName() != null){
					username = user.getFirstName();
				}else{
					username = user.getEmail();
				}
				
				System.out.println(user.getId() + " :: " + campaignId);
				AccountCampaign ap = accountCampaignDao.getAccountCampaignByGroupAndCampaign(groupId, campaignId);
				if (ap != null){
					ResourceBundle rb =  ResourceBundle.getBundle("net/zerofootprint/velo/velo-application");
					String server = rb.getString("donation.redirect.server");
					String path = rb.getString("donation.redirect.path");
					System.out.println(server);
					System.out.println(path);
					String donateUrl = "<a href=\"" + server + path + "?accountId="+ user.getId()+"&accountProgramId="+ap.getId() +"\">Please click here to donate and support my fundraising efforts.</a>";
					content = content.replaceAll("<sponsor's first name>", username);
					content = content.replaceAll("Please click here to donate and support my fundraising efforts.", donateUrl);
					
					AccountGroup accountGroup = accountGroupDao.getAccountGroup(user.getId(), groupId);
					Campaign campaign = campaignDao.getCampaign(campaignId);
					
					if (accountGroup != null){
						AccountGroupCommunicationStatus ag = accountGroupCommunicationStatusDao.getAccountGroupCommunicationStatus(user.getId(), groupId, campaignId);
						
						boolean updateStatus = false;
						if (ag == null){
							updateStatus = false;
							ag = new AccountGroupCommunicationStatus();
							ag.setGroups(accountGroup.getGroups());
							ag.setCampaign(campaign);
							ag.setUser(accountGroup.getUser());
						}else{
							updateStatus = true;
						}
						
						boolean isEmailSuccess = false;
						try{
							notificationService.sendBasicEmail(email, fromEmail, fromName, subject, content, domainName);
							//Beyond this, no error invitation success change the status
							isEmailSuccess = true;
							rv.setResultCode(AppConstants.SUCCESS);
						}catch(Exception e){
							isEmailSuccess = false;
							
						}
						
						Status status = null;
						if (isEmailSuccess){
							status = accountCampaignDao.getStatusByCode(AppConstants.STATUS_CODE_SPONSORSHIP_REQUESTED);
							ag.setStatus(status);
						}else{
							status = accountCampaignDao.getStatusByCode(AppConstants.STATUS_CODE_SPONSORSHIP_REQUESTED_FAILED);
							ag.setStatus(status);
						}
						if (updateStatus){
							System.out.println("Updating the status");
							accountGroupCommunicationStatusDao.save(ag, true);
						}
						else{
							System.out.println("Creating the status");
							accountGroupCommunicationStatusDao.save(ag);
						}
						
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			rv.setResultCode(AppConstants.FAILURE);
		}
		
		return rv;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView sendBasicEmail(String emails, String fromEmail, String fromName, String subject, String content, String domainName){
		//parse emails by ;
		System.out.println("UTIlitily Service send Basic email" + new Date() + " :: " + emails);
		ResultView rv = new ResultView();
		notificationService = new NotificationServiceImpl();
		try{
			String[] emailArray = emails.split(";");
			
			System.out.println(emailArray.length);
			for (int i = 0; i < (emailArray.length); i++){
				String member = emailArray[i];
				
				System.out.println(member);
				
				//From email get user name...
				
				User user = userDao.getUserByUID(member);
				String username = "";
				if (user.getFirstName() != null){
					username = user.getFirstName();
				}else{
					username = user.getEmail();
				}
				
				content = content.replaceAll("<member's first name>", username);
				notificationService.sendBasicEmail(user.getEmail(), fromEmail, fromName, subject, content, domainName);
			}
			
			rv.setResultCode(AppConstants.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			rv.setResultCode(AppConstants.FAILURE);
		}
		return rv;
		
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public TrackingView getTrackingId(String domainName){
		System.out.println("We are in getTrackingID ---- ");
		
		String trackingId = null;
		try {
			trackingId = domainDao.getTrackingId(domainName);
		}catch (NoResultException ne){
			System.out.println("************ Domain no found.");
		}catch (Exception e) {
			System.out.println("************ Get error in UtilityService for Domain. Error: " + e);
		}
		TrackingView tv = new TrackingView();
		tv.setTrackingId(trackingId);
		return tv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView isSourceExists(String domainName, String accountEmail, String category){
		ResultView rv = new ResultView();
		rv.setResultCode(AppConstants.FAILURE);
		
		Long communityId = getCommunityId(domainName);
		User user = userDao.findByNameCommunity(accountEmail);
		Long accountId = user.getId();
		//In AccountSource table, check if there are any sources associated with account
		List<Source> sources = userDao.findSourceByAccount(accountId);
		boolean isSource = false;
		for (Source source : sources){
			System.out.println("Source :: " + source.getName() + " From Source :: Category :: " + category);
			if (source.getCategory().getCode().equals(category) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				isSource = true;
				break;
			}
		}
		
		System.out.println("Is Source!!! +++++ " + isSource);
		if (isSource) rv.setResultCode(AppConstants.SUCCESS);
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Long getCommuniyId(String communityName) {
		
		System.out.println("************ CommunityNamr name : " + communityName);
		Long id = null;
		try {
			id = domainDao.getCommunitId(communityName);
			System.out.println("************ Community ID : " + id);
		}catch (NoResultException ne){
			System.out.println("************ Community no found.");
		}catch (Exception e) {
			System.out.println("************ Get error in UtilityService for Community. Error: " + e);
		}
		return id;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<VehicleView> getVehicleTypes(String domainName, String portalName, String locale){
		List<TravelTypeView> ttvs = new ArrayList<TravelTypeView>();
		Long communityId = getCommunityId(domainName);
		//Get PortalId
		Long portalTypeId = portalTypeDao.findPortalTypeListId(portalName);
		
		List<VehicleView> vvs = new ArrayList<VehicleView>();
		List<VehicleType> vts = communityDao.findAllVehicleTypes();
		for (VehicleType vt : vts){
			VehicleView vv = new VehicleView(vt);
			vv.setName(translationDao.findTranslation(vv.getName(), communityId, portalTypeId, locale, 3l).getTranslation());
			vvs.add(vv);
		}
		
		return vvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<TravelTypeView> getTravelTypes(String domainName, String portalName, String locale){
		System.out.println("************ GET TravelTypes ******************* : " + domainName);
		List<TravelTypeView> ttvs = new ArrayList<TravelTypeView>();
		Long communityId = getCommunityId(domainName);
		//Get PortalId
		Long portalTypeId = portalTypeDao.findPortalTypeListId(portalName);
		//Get CommunityPortal ID
		CommunityPortal communityPortal= communityDao.getCommunityPortal(communityId, portalTypeId);
		Long communityPortalId;
		if (communityPortal != null) communityPortalId = communityPortal.getId();
		else return ttvs;
		
		//Get Community Feature Id
		String featureName = "CARBON_TRAVEL";
		Long communityFeatureId = communityDao.getCommunityFeatureId(communityPortalId, featureName);
		
		List<FeatureTravelType> ftts = communityDao.getFeatureTravelType(communityFeatureId);
		
		for (FeatureTravelType ftt : ftts){
			TravelTypeView ttv = new TravelTypeView(ftt);
			ttv.setName(translationDao.findTranslation(ttv.getName(), communityId, portalTypeId, locale, 3l).getTranslation());
			ttvs.add(ttv);
		}
		
		return ttvs;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<FeatureView> getFeatures( String domainName, String portalName){
		System.out.println("************ GET FEATURES ******************* : " + domainName);
		
		List<FeatureView> fvs = new ArrayList<FeatureView>();
		Long communityId = getCommunityId(domainName);
		//Get PortalId
		Long portalTypeId = portalTypeDao.findPortalTypeListId(portalName);
		//Get CommunityPortal ID
		
		System.out.println("Portal Type ID :: " + portalTypeId + " :: communityId :: " + communityId);
		CommunityPortal communityPortal= communityDao.getCommunityPortal(communityId, portalTypeId);
		Long communityPortalId;
		if (communityPortal != null) communityPortalId = communityPortal.getId();
		else return fvs;
		
		List<CommunityFeature> cfs = communityDao.getCommunityFeature(communityPortalId);
		
		for (CommunityFeature cf : cfs){
			Feature feature = cf.getFeature();
			FeatureView fv = new FeatureView(feature);
			fvs.add(fv);
		}
		
		return fvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public TranslationView getTranslation(TranslationForm translationForm) {

		Long communityId = translationForm.getCommunityId();
		if ( communityId == null) communityId =  getCommunityId(translationForm.getCommunityName());
		
		List<Translation> tt = translationDao.findTranslation(translationForm);
		System.out.println("************ Found translations count: " + tt.size());
		if( tt==null || tt.size()<1 ) return null;
		return new TranslationView(tt.get(0));
		
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<TranslationView> getTranslations(TranslationForm translationForm) {
		ArrayList<TranslationView> tvs = new ArrayList<TranslationView>();

		Long communityId = translationForm.getCommunityId();
		if ( communityId == null) communityId =  getCommunityId(translationForm.getCommunityName());
		
		
		Long portalTypeId = portalTypeDao.findPortalTypeListId(translationForm.getPortalTypeName().toUpperCase());
		
		
		List<Translation> tt= new ArrayList<Translation>();
		if (translationForm.getComponentId() != 3l){
			tt = translationDao.findTranslationsList(translationForm, communityId, portalTypeId);
		}else{
			tt = translationDao.findBodyTranslationsList(translationForm, communityId, portalTypeId);
		}
		
		
		for (int i = 0; i < tt.size(); i++) {
			tvs.add(new TranslationView(tt.get(i)));
		}
		
		return tvs;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public final Long getCommunityId(String comm){

		Long commId = null;
		if(comm != null){
			try{
				commId = domainDao.getCommunitId(comm);
			}catch (Exception e) {
				System.out.println("************ Community not found.");
			}
		}else{
			commId = null;
		}
		
		return commId;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView saveFootprint(FootprintForm footprintForm) {
		
		ResultView result = new ResultView();
		
		//Check if footprint exists uniqueness (source ID, source Type, communityId, and FootprintTypeID)
		Long communityId = getCommunityId(footprintForm.getDomainName());
		
			//Get Community
			Community community = communityDao.getCommunityById(communityId);
			//Get AccountId
			User user = userDao.findById(footprintForm.getAccountId()); 
			Long accountId = user.getId();
			
			//In AccountSource table, check if there are any sources associated with account
			List<Source> sources = userDao.findSourceByAccountAndType(accountId, AppConstants.SOURCETYPE_CODE_PERSONAL_FOOTPRINT);
			Source source = null;
			for (Source s : sources){
				//Each sources check if category is 1 PERSOANL_FOOTPRINT SURVEY SAVE source id for further use
				if (s.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_PERSONAL) && s.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
					source = s;
					break;
				}
			}
			if (source == null){
				result.setResultCode(AppConstants.NO_SOURCE_FOUND);
				return result;
			}
			//Get FootprintTYpe
			FootprintType footprintType = carbonFootprintDao.getFootprintTypeById(footprintForm.getFootprintType());
			
			Double value = footprintForm.getElec();
			Long sourceTypeId = 1l;
			//Get sourceType
			SourceType sourceType = communityDao.getSourceTypeById(sourceTypeId);
				
			CarbonFootprint cf = new CarbonFootprint(community, source, sourceType, footprintType);
			cf.setValue(value);
			cf.setUnits(footprintForm.getUnit());
			carbonFootprintDao.save(cf);
			
			value = footprintForm.getNg();
			sourceTypeId = 2l;
			//Get sourceType
			sourceType = communityDao.getSourceTypeById(sourceTypeId);
				
			cf = new CarbonFootprint(community, source, sourceType, footprintType);
			cf.setValue(value);
			cf.setUnits(footprintForm.getUnit());
			carbonFootprintDao.save(cf);
			
			value = footprintForm.getWater();
			sourceTypeId = 9l;
			//Get sourceType
			sourceType = communityDao.getSourceTypeById(sourceTypeId);
				
			cf = new CarbonFootprint(community, source, sourceType, footprintType);
			cf.setValue(value);
			cf.setUnits(footprintForm.getUnit());
			carbonFootprintDao.save(cf);
			
			value = footprintForm.getWaste();
			sourceTypeId = 6l;
			//Get sourceType
			sourceType = communityDao.getSourceTypeById(sourceTypeId);
				
			cf = new CarbonFootprint(community, source, sourceType, footprintType);
			cf.setValue(value);
			cf.setUnits(footprintForm.getUnit());
			carbonFootprintDao.save(cf);
			
			value = footprintForm.getVehicle();
			sourceTypeId = 4l;
			//Get sourceType
			sourceType = communityDao.getSourceTypeById(sourceTypeId);
				
			cf = new CarbonFootprint(community, source, sourceType, footprintType);
			cf.setValue(value);
			cf.setUnits(footprintForm.getUnit());
			carbonFootprintDao.save(cf);
			
			value = footprintForm.getFlights();
			sourceTypeId = 10l;
			//Get sourceType
			sourceType = communityDao.getSourceTypeById(sourceTypeId);
				
			cf = new CarbonFootprint(community, source, sourceType, footprintType);
			cf.setValue(value);
			cf.setUnits(footprintForm.getUnit());
			carbonFootprintDao.save(cf);
			System.out.println("************ Success save footprint info to DB");

		
		result.fill(AppConstants.SUCCESS, "Add footprint success");
		return result;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView addFeedback(Feedback feedback) {
		
		ResultView result = new ResultView();
		
		try {
			
			feedbackDao.save(feedback);
			System.out.println("************ Success save feedback info to DB");
			
		}catch (Exception e) {
			System.out.println("************ Save feedback failure");
			e.printStackTrace();
			result.fill(AppConstants.FAILURE, "Add feedback failure");
			return result;
		}
		
		result.fill(AppConstants.SUCCESS, "Add feedback success");
		return result;
		
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Image getImage(String imageName, String serverName, String locale, Long portalId) {
		// 
		Long communityId = getCommunityId(serverName);
		
		List<Image> tt = imageDao.fingImages(imageName, communityId, locale, portalId);
		System.out.println("************ Found images count: " + tt.size());
		if( tt==null || tt.size()<1 ) return null;
		
		return tt.get(0);
		
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Image> getImages(String serverName, String locale, String portalName) {
		Long portalTypeId = portalTypeDao.findPortalTypeListId(portalName.toUpperCase());

		Long communityId = getCommunityId(serverName);
		List<Image> tt = imageDao.fingImages(communityId, locale, portalTypeId);
		System.out.println("************ Found images count: " + tt.size());
		return tt;
	}

	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResultView isValidEmail(String email){
		ResultView rv = new ResultView();
		rv.fill(AppConstants.FAILURE, "Email is invalid.");
		HttpClient client = new DefaultHttpClient();
		
		String contentURL = null;
		try{
			
			HttpPost post = new HttpPost(AppConstants.EXPERIAN_EMAIL_CHECK_URL);
		 
			// add header
			post.setHeader("Auth-Token", AppConstants.EXPERIAL_TOKEN);
			post.setHeader("Content-Type", "application/json");
			
			post.setEntity(new StringEntity("{\"Email\":\"" + email + "\"}"));
			
			HttpResponse response = client.execute(post);
			System.out.println("Response Code : " 
		                + response.getStatusLine().getStatusCode());
			Header[] h = response.getAllHeaders();
			
			
			for (int i =0; i < h.length; i++){
				System.out.println("Name : " + h[i].getName() + " : " + h[i].getValue());
				if (h[i].getName().equals("Content-Location")){
					contentURL = h[i].getValue();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(contentURL);
		
		if (contentURL != null){
			
			try {
				//sleep for a sec because Experian needs to wait
				Thread.sleep(1500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try{
				HttpGet get = new HttpGet(contentURL);
				get.setHeader("Auth-Token", "919c39a4-8050-40e8-3028-9ee3ef12359e");
				get.setHeader("Content-Type", "application/json");
				
				HttpResponse response = client.execute(get);
				
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
					   + response.getStatusLine().getStatusCode());
				}
		 
				BufferedReader br = new BufferedReader(
		                         new InputStreamReader((response.getEntity().getContent())));
		 
				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = br.readLine()) != null) {
					result.append(line);
				}
				
				String jsonText = result.toString();
				Gson gson = new Gson();
				JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonText);
				boolean isValid = false;
				if (json.getString("Certainty").toUpperCase().equals("VERIFIED") || json.getString("Certainty").toUpperCase().equals("UNKNOWN")){
					isValid = true;
				}else if (json.getString("Certainty").toUpperCase().equals("UNDELIVERABLE") && json.getString("Message").toUpperCase().equals("MAILBOX FULL")){
					isValid = true;
				}
				
				if (isValid) rv.fill(AppConstants.SUCCESS,"Email is valid");
				else rv.fill(AppConstants.FAILURE, "Email is invalid");
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		return rv;
	}
	
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String generateCarbonCertificate(Long id, String name, String value, String date) {
            // 
            SalesOrder ct = salesOrderDao.findById(id);
            String cfile = null;
            notificationService = new NotificationServiceImpl();
            
            if (ct.getReceiptUrl() == null){
                    cfile = notificationService.createCertificate(name, value, date, "en_CA");
            }else{
                    File cf = new File (ct.getReceiptUrl());
                    if (!cf.exists()){
                            cfile = notificationService.createCertificate(name, value, date, "en_CA");
                    }else{
                            ct.getReceiptUrl();
                    }
            }
            
            String url = SystemProperties.getProp("webServerUrl") + ":" + SystemProperties.getProp("webPort") 
                            + SystemProperties.SERVER_CONTEXT_NAME + "/certificate/";
            System.out.println("11++++++++++ PDF path: " + url + " ++++++++++");
            ct.setReceiptUrl(url+cfile);
            
            salesOrderDao.save(ct, true);
            
            //return cfile;
            return url+cfile;

    }
	public static String hmacDigest(String msg, String keyString, String algo) {
	    String digest = null;
	    try {
	      SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
	      Mac mac = Mac.getInstance(algo);
	      mac.init(key);

	      byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

	      StringBuffer hash = new StringBuffer();
	      for (int i = 0; i < bytes.length; i++) {
	        String hex = Integer.toHexString(0xFF & bytes[i]);
	        if (hex.length() == 1) {
	          hash.append('0');
	        }
	        hash.append(hex);
	      }
	      digest = hash.toString();
	    } catch (UnsupportedEncodingException e) {
	    } catch (InvalidKeyException e) {
	    } catch (NoSuchAlgorithmException e) {
	    }
	    return digest;
	  
	}
	
	
}
