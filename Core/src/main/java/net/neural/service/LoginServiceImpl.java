package net.zfp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Account_Authority;
import net.zfp.entity.Status;
import net.zfp.entity.User;
import net.zfp.entity.UserRole;
import net.zfp.entity.account.AccountCampaign;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.campaign.CampaignTarget;
import net.zfp.entity.category.Category;
import net.zfp.entity.community.Community;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.segment.Segment;
import net.zfp.notify.NotificationService;
import net.zfp.notify.impl.NotificationServiceImpl;
import net.zfp.service.LoginService;
import net.zfp.service.UtilityService;
import net.zfp.util.AppConstants;
import net.zfp.util.LocaleUtil;
import net.zfp.util.PasswordEncoderUtil;
import net.zfp.view.ResultView;
import net.zfp.view.UserView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Resource
public class LoginServiceImpl implements LoginService {
	@Resource
    private SegmentService segmentService;
	@Resource
    private MemberActivityService memberActivityService;
	@Resource
    private AccountService accountService;
	@Resource
	private EntityDao<Segment> segmentDao;
	@Resource
	private EntityDao<AccountCampaign> accountCampaignDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Status> statusDao;
	@Resource
	private EntityDao<Account_Authority> accountAuthorityDao;
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<Campaign> campaignDao;
	@Resource
	private EntityDao<Category> categoryDao;
	@Resource
	private EntityDao<Offer> offerDao;
	@Resource
    private UtilityService utilityService;
    @Inject
    private NotificationService notificationService;
    
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public UserView authenticate(String username, String password, String locale){
		UserView userView;
		ResultView result = new ResultView();
		User user;
		String email = username;
		

		try{
			// TODO: for community
			user = userDao.findByNameCommunityForLogin(email);
		}catch(NoResultException e){
			result.fill(AppConstants.NO_USER_FOUND, "Account not found.");
			userView = new UserView();
			userView.setResult(result);
			return userView;
			
		}catch(Exception e){
			e.printStackTrace();
			result.fill(AppConstants.UNKNOWN_ERROR, "Unknown error.");
			userView = new UserView();
			userView.setResult(result);
			return userView;
		}

		if(user.getMode() == User.ACCOUNT_ZEROFOORPRINT){

			String encodePasswd = "";
			if(password!=null){
				encodePasswd = PasswordEncoderUtil.encodePassword(password, user.getPasswordSalt());
			}

			if(password == null || !encodePasswd.equals(user.getPassword())){
				System.out.println("******WRONG_PASSWORD******");
				result.fill(AppConstants.WRONG_PASSWORD, "wrong password");
				
			}else {
				//Collection<UserRole> cs = user.getRoles();
				//System.out.println("******SUCCESS****** Role size: "+cs.size());
				result.fill(AppConstants.SUCCESS, "success");
			}
			
		}else if(user.getMode() == User.ACCOUNT_FACEBOOK){
			System.out.println("******SUCCESS sign by Facebook******");
			result.fill(AppConstants.SUCCESS, "success");
		}else{
			
		}

		userView = new UserView(user);
		
		System.out.println("AUTHENTICATE :: " + user.getId() +  " :: " + user.getDefaultCommunity());
		Community community = communityDao.findById(user.getDefaultCommunity());
		userView.setCommunityCode(community.getCode());
		userView.setResult(result);
		
		return userView;
	}
	
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public
	UserView forgotPassword(
			User user, 
			String domain, 
			String url, 
			String portalUrl, 
			String locale) {
		
		UserView userView;
		ResultView result = new ResultView();

		String email = user.getEmail();
		System.out.println("******** In login service impl, Email: " + email + " ; Sign mode: " + user.getMode());
		
		//Community type based on community
		//If it's coporate then mode is 3
		Integer mode = User.ACCOUNT_ZEROFOORPRINT;
		Community community = communityDao.getCommunity(domain);
		if (community.getCommunityType().getType().equals(AppConstants.COMMUNITY_TYPE_CORPORATE)){
			mode = User.ACCOUNT_CORPORATE;
		}
		
		try{
			user = userDao.findUserByEmailAndModeWithException(email, mode);
		}catch(NoResultException e){
			result.fill(AppConstants.NO_USER_FOUND, "Account not found.");
			userView = new UserView();
			userView.setResult(result);
			return userView;
			
		}catch(Exception e){
			e.printStackTrace();
			result.fill(AppConstants.UNKNOWN_ERROR, "Unknown error.");
			userView = new UserView();
			userView.setResult(result);
			return userView;
		}

		if(user.getMode() == User.ACCOUNT_ZEROFOORPRINT){

			user.setPasswordResetCode(PasswordEncoderUtil.generatePasswordResetCode(user.getEmail(), user.getPasswordSalt()));

			GregorianCalendar cal = new GregorianCalendar();
			cal.add(Calendar.DATE, 3);
			Date newdate = cal.getTime();
			user.setPasswordResetCodeExpiresOn(newdate);
			
			userDao.save(user);
			
			NotificationServiceImpl notificationService = new NotificationServiceImpl();
			notificationService.notifyPasswordResetRequest(user, url, portalUrl, locale, domain);
			
			result.fill(AppConstants.SUCCESS, "success");
			userView = new UserView();
			userView.setResult(result);

		}else if(user.getMode() == User.ACCOUNT_FACEBOOK){
			System.out.println("******Facebook account can not change password in VELO******");
			result.setResultCode(AppConstants.FACEBOOK_ACCOUNT);
			result.setResultMessage("Facebook account can not change password in VELO");
		}else{
			
		}

		userView = new UserView(user);
		userView.setResult(result);
		
		return userView;

	
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public UserView resetPassword(User user, String community, String code, String locale) {

		UserView userView = new UserView();
		ResultView result = new ResultView();

		String email = user.getEmail();
		String password = user.getPassword();
		
		try{
			//Fetch based on email and password reset code
			user = userDao.findByPasswordResetCode(email, code);
		}catch(NoResultException e){
			result.fill(AppConstants.NO_USER_FOUND, "Account not found");
			userView.setResult(result);
			return userView;
			
		}catch(Exception e){
			e.printStackTrace();
			result.fill(AppConstants.UNKNOWN_ERROR, "Unknown error.");
			userView.setResult(result);
			return userView;
		}
		
		if(user.getMode() == User.ACCOUNT_ZEROFOORPRINT){

            if (user.getPasswordResetCode().equals(code)) {
                if (new Date().compareTo(user.getPasswordResetCodeExpiresOn()) <= 0) {
                	user.setPassword(PasswordEncoderUtil.encodePassword(password, user.getPasswordSalt()));
            		user.setPasswordResetCode("");
            		user.setPasswordResetCodeExpiresOn(null);
        			userDao.save(user);
            		
        			try{
	        			NotificationServiceImpl notificationService = new NotificationServiceImpl();
	        			notificationService.notifyPasswordResetSuccess(user, locale, community);
        			}catch(Exception ex){
        				ex.printStackTrace();
        				result.fill(AppConstants.SEND_NOTIFY_EMAIL_FAILURE, "Password reset success, but send notify email wrong.");
        				userView.setResult(result);
        				return userView;
        			}

                	result.fill(AppConstants.SUCCESS, "success");
        			userView.setResult(result);
                    return userView;
                } else {
                	result.fill(AppConstants.RESET_PASSWORD_EXPIRE, "exprie");
        			userView.setResult(result);
                    return userView;
                }
            } else {
            	result.fill(AppConstants.RESET_PASSWORD_WRONG_CODE, "wrong code");
    			userView.setResult(result);
                return userView;
            }

		}else if(user.getMode() == User.ACCOUNT_FACEBOOK){
			System.out.println("******Facebook account can not change password in VELO******");
			result.setResultCode(AppConstants.FACEBOOK_ACCOUNT);
			result.setResultMessage("Facebook account can not change password in VELO");
		}else{
			
		}
		
		return userView;
		
	}

	@Override
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public
	UserView checkExpires(User user,String community, String code) {
		
		UserView userView = new UserView();
		ResultView result = new ResultView();

		String email = user.getEmail();
		System.out.println("******** In login service resetPassword, Email: " + user.getEmail() + " ; Code: " + code);
		
		try{
			user = userDao.findByName(email, utilityService.getCommuniyId(community), user.getMode());
		}catch(NoResultException e){
			result.fill(AppConstants.NO_USER_FOUND, "Account not found.");
			userView.setResult(result);
			return userView;
			
		}catch(Exception e){
			e.printStackTrace();
			result.fill(AppConstants.UNKNOWN_ERROR, "Unknown error.");
			userView.setResult(result);
			return userView;
		}

		if(user.getMode() == User.ACCOUNT_ZEROFOORPRINT){

            if (user.getPasswordResetCode().equals(code)) {
                if (new Date().compareTo(user.getPasswordResetCodeExpiresOn()) <= 0) {
                	result.fill(AppConstants.SUCCESS, "No expire");
        			userView.setResult(result);
                    return userView;
                } else {
                	result.fill(AppConstants.RESET_PASSWORD_EXPIRE, "link expire");
        			userView.setResult(result);
                    return userView;
                }
            } else {
            	result.fill(AppConstants.RESET_PASSWORD_WRONG_CODE, "Wrong code");
    			userView.setResult(result);
                return userView;
            }

		}else if(user.getMode() == User.ACCOUNT_FACEBOOK){
			System.out.println("******Facebook account can not change password in VELO******");
			result.fill(AppConstants.FACEBOOK_ACCOUNT, "Facebook account can not change password in VELO");
		}else{
			
		}
		
		return userView;
		
	}
	
	private void joinAutoCampaign(User user, String domain){
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
							if (!tempCampaign.getCampaignTemplate().getMeterRequired() && tempCampaign.getAutoJoin()){
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
				
				Integer offerPoints = 0;
				List<Offer> offers = getCampaignOffer(campaign.getId(), campaign.getCommunityId(), campaign.getEndDate(), user.getId());
				if (offers != null){
					if (offers != null && offers.size() > 0){
							for (Offer offer : offers){
								offerPoints += offer.getValue().intValue();
							}
					}
				}
				
				//good send an auto joined email
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
