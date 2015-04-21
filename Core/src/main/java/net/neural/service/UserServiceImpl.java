package net.zfp.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import net.zfp.dao.EntityDao;
import net.zfp.entity.AccountSegment;
import net.zfp.entity.Account_Authority;
import net.zfp.entity.Domain;
import net.zfp.entity.Location;
import net.zfp.entity.PointsAccount;
import net.zfp.entity.Status;
import net.zfp.entity.User;
import net.zfp.entity.UserRole;
import net.zfp.entity.account.AccountCampaign;
import net.zfp.entity.account.AccountGroup;
import net.zfp.entity.account.MemberExtendedProfile;
import net.zfp.entity.account.MemberHouseholdProfile;
import net.zfp.entity.account.MemberProfileValue;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.campaign.CampaignTarget;
import net.zfp.entity.category.Category;
import net.zfp.entity.communication.CommunicationPreference;
import net.zfp.entity.community.Community;
import net.zfp.entity.group.Groups;
import net.zfp.entity.group.GroupsType;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.segment.SegmentType;
import net.zfp.entity.wallet.PointsType;
import net.zfp.form.HouseholdForm;
import net.zfp.notify.NotificationService;
import net.zfp.notify.impl.NotificationServiceImpl;
import net.zfp.view.*;
import net.zfp.service.UserService;
import net.zfp.service.UtilityService;
import net.zfp.util.AppConstants;
import net.zfp.util.CommonConstants;
import net.zfp.util.GoogleMapAPI;
import net.zfp.util.KeyGenerationUtil;
import net.zfp.util.LocaleUtil;
import net.zfp.util.PasswordEncoderUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.http.client.ClientProtocolException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class UserServiceImpl implements UserService {

	private static Log							LOG	= LogFactory.getLog(UserServiceImpl.class.getName());
	@Resource
    private SegmentService segmentService;
	@Resource
	private EntityDao<Domain> domainDao;

	@Resource
	private EntityDao<Community> communityDao;
	
	@Resource
	private EntityDao<Account_Authority> accountAuthorityDao;
	
	@Resource
	private EntityDao<AccountSegment> accountSegmentDao;
	
	@Resource
	private EntityDao<Segment> segmentDao;
	
	@Resource
	private EntityDao<Offer> offerDao;
	
	@Resource
	private EntityDao<Category> categoryDao;
	
	@Resource
	private EntityDao<AccountGroup> accountGroupDao;
	
	@Resource
	private EntityDao<AccountCampaign> accountCampaignDao;
	
	@Resource
	private EntityDao<Campaign> campaignDao;
	
	@Resource
	private EntityDao<Location> locationDao;
	
	@Resource
	private EntityDao<MemberExtendedProfile> memberExtendedProfileDao;
	
	@Resource
	private EntityDao<MemberHouseholdProfile> memberHouseholdProfileDao;
	
	@Resource
	private EntityDao<CommunicationPreference> communicationPreferenceDao;
	
	@Resource
	private EntityDao<User> userDao;

	@Resource
	private EntityDao<Status> statusDao;
	
	@Resource
	private EntityDao<UserRole> roleDao;

	@Resource
	private EntityDao<Groups> groupsDao;
	
	@Context
	private MessageContext context;

    @Inject
    private UtilityService utilityService;
    @Inject
    private NotificationService notificationService;
	
    @Resource
    private MemberActivityService memberActivityService;
	@Resource
    private AccountService accountService;
	
	@Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView resetPassword(Long accountId, String oldPassword, String newPassword){
		ResultView rv = new ResultView();
		
		if (accountId == null || oldPassword == null || newPassword == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
		}
		
		User user = userDao.findById(accountId);
		if (user != null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
		}
		
		/*
		 * Validate a user with old password
		 */
		String encodePasswd = "";
		if(oldPassword!=null){
			encodePasswd = PasswordEncoderUtil.encodePassword(oldPassword, user.getPasswordSalt());
		}

		if(oldPassword == null || !encodePasswd.equals(user.getPassword())){
			rv.fill(AppConstants.FAILURE, "Invalid parameter");
			
		}else {
			user.setPassword(PasswordEncoderUtil.encodePassword(newPassword, user.getPasswordSalt()));
			userDao.save(user, true);
			rv.fill(AppConstants.SUCCESS, "Password has been reset successfully");
		}
		
		return rv;
	}
	
	@Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public UserView authenticateWithoutPassword(Long memberId, String locale){
		UserView userView;
		ResultView result = new ResultView();
		User user;
		
		try{
			// TODO: for community
			user = userDao.findById(memberId);
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

		userView = new UserView(user);
		
		Community community = communityDao.findById(user.getDefaultCommunity());
		userView.setCommunityCode(community.getCode());
		userView.setResult(result);
		
		return userView;
	}
	
	@Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public TextView getMemberDomain(Long memberId){
		TextView tv = new TextView();
		tv.setHeader("dd");
		tv.setMasterHeader("333");
		User member = userDao.findById(memberId);
		if (member != null){
			//Get CommunityId get Domain
			Domain domain = domainDao.getDomainByCommunityId(member.getDefaultCommunity());
			if (domain != null){
				tv.setText(domain.getName());
			}
		}
		return tv;
	}
	
	@Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setMemberHouseholdProfile(HouseholdForm householdForm){
		ResultView rv = new ResultView();
		
		try{
			MemberHouseholdProfile mhp = null;
			User user = userDao.findById(householdForm.getMemberId());
			
			if (user != null){
				mhp = memberHouseholdProfileDao.getMemberHouseholdProfileByMember(user.getId());
				
				if (mhp == null){
					mhp = new MemberHouseholdProfile();
					mhp.setUser(user);
				}
				
				if (householdForm.getHouseType() != null){
					MemberProfileValue houseType = memberHouseholdProfileDao.getMemberProfileValue(householdForm.getHouseType().toUpperCase());
					mhp.setHouseTypePV(houseType);
				}
				if (householdForm.getHouseArea() != null){
					MemberProfileValue houseArea = memberHouseholdProfileDao.getMemberProfileValue(householdForm.getHouseArea().toUpperCase());
					mhp.setHouseAreaPV(houseArea);
				}
				if (householdForm.getOccupants() != null){
					MemberProfileValue occupants = memberHouseholdProfileDao.getMemberProfileValue(householdForm.getOccupants().toUpperCase());
					mhp.setOccupantsPV(occupants);
				}
				if (householdForm.getHouseHeating() != null){
					MemberProfileValue houseHeating = memberHouseholdProfileDao.getMemberProfileValue(householdForm.getHouseHeating().toUpperCase());
					mhp.setHouseHeatingPV(houseHeating);
				}
				if (householdForm.getWaterHeating() != null){
					MemberProfileValue waterHeating = memberHouseholdProfileDao.getMemberProfileValue(householdForm.getWaterHeating().toUpperCase());
					mhp.setWaterHeatingPV(waterHeating);
				}
				if (householdForm.getStove() != null){
					MemberProfileValue stove = memberHouseholdProfileDao.getMemberProfileValue(householdForm.getStove().toUpperCase());
					mhp.setStovePV(stove);
				}
				if (householdForm.getFireplace() != null){
					Boolean fireplace = Boolean.parseBoolean(householdForm.getFireplace().toLowerCase());
					mhp.setFireplace(fireplace);
				}
				
				if (householdForm.getBBQGas() != null){
					Boolean BBQGas = Boolean.parseBoolean(householdForm.getBBQGas().toLowerCase());
					mhp.setBBQGas(BBQGas);
				}
				
				if (mhp.getId() == null){
					memberHouseholdProfileDao.save(mhp);
				}else{
					memberHouseholdProfileDao.save(mhp, true);
				}

				rv.fill(AppConstants.SUCCESS, "Successfuly updated.");
			}
			
		}catch(Exception e){
			rv.fill(AppConstants.FAILURE, e.getMessage());
		}
		
		return rv;
	}
	
	@Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setMemberProfileImage(Long memberId, String profileImageUrl){
		ResultView rv = new ResultView();
		
		try{
			//Get Member Extended Profile
			MemberExtendedProfile mep = memberExtendedProfileDao.getMemberExtendedProfile(memberId);
			if (mep == null){
				User member = userDao.findById(memberId);
				mep.setUser(member);
				mep.setProfileImageUrl(profileImageUrl);
				memberExtendedProfileDao.save(mep);
			}else{
				mep.setProfileImageUrl(profileImageUrl);
				mep.setLastModified(new Date());
				memberExtendedProfileDao.save(mep, true);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			rv.fill(AppConstants.FAILURE, "Error while saving member Extended Profile");
		}
		return rv;
	}
	
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public ResultView addTempProfile(String domainName, String firstName, String lastName, String email, Integer mode){
    	ResultView rv = new ResultView();
    	Long communityId = domainDao.getCommunitId(domainName);
    	//Add guest account
    	
    	//Check if this is already a user account
    	User newUser = null;
    	try{
    		newUser = userDao.findByName(email, communityId, mode);
    		rv.fill(AppConstants.FAILURE, "User is already existed in the system");
    	}catch(Exception e){
    		newUser = new User();
    		newUser.setFirstName(firstName);
    		newUser.setLastName(lastName);
    		newUser.setEmail(email);
    		newUser.setDefaultCommunity(communityId);
    		newUser.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
    		Account_Authority aa = new Account_Authority();
    		UserRole authority = userDao.getUserRole(AppConstants.ROLE_GROUP_GUEST);
			aa.setUser(newUser);
			aa.setUserRole(authority);
			accountAuthorityDao.save(aa, true);
			
			rv.fill(AppConstants.SUCCESS, "Successfully added the guest to the system");
    	}
    	
    	return rv;
    }
    
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public ResultView changePassword(Long accountId, String oldPassword, String newPassword){
    	ResultView result = new ResultView();
    	//Check if old Password valids
    	User user = userDao.findById(accountId);
    	
    	if(user.getMode() == User.ACCOUNT_ZEROFOORPRINT){

			String encodePasswd = "";
			if(oldPassword!=null){
				encodePasswd = PasswordEncoderUtil.encodePassword(oldPassword, user.getPasswordSalt());
			}

			if(oldPassword == null || !encodePasswd.equals(user.getPassword())){
				System.out.println("******WRONG_PASSWORD******");
				result.fill(AppConstants.WRONG_PASSWORD, "wrong password");
				
			}else {
				//if Password matches... then put new password;
				
				user.setPassword(PasswordEncoderUtil.encodePassword(newPassword, user.getPasswordSalt()));
				userDao.save(user, true);
				result.fill(AppConstants.SUCCESS, "success");
			}
			
		}
    	
    	
    	
    	return result;
    }
    
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public ResultView setCorporateProfile(Long memberId, Double rooms, Integer occupants){
    	ResultView rv = new ResultView();
    	
    	if (memberId == null){
    		rv.fill(AppConstants.FAILURE,  AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
    	}
    	User member = userDao.findById(memberId);
    	
    	if (member == null){
    		rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
    	}else{
    		try{
        		
        		if (rooms != null || occupants != null){
        			MemberExtendedProfile mep = userDao.getMemberExtendedProfile(memberId);
        			
        			/*
        			 * If member extended profile exists it should update the profile.
        			 * Otherwise create a new corporate profile.
        			 */
        			if (mep != null){
        				if (rooms != null){
            				mep.setRooms(rooms);
            			}
            			if (occupants != null){
            				mep.setOccupants(occupants);
            			}
            			
            			memberExtendedProfileDao.save(mep, true);
        			}else{
        				mep = new MemberExtendedProfile();
        				mep.setUser(member);
        				
        				if (rooms != null){
        					mep.setRooms(rooms);
            			}
            			if (occupants != null){
            				mep.setOccupants(occupants);
            			}
            			
            			memberExtendedProfileDao.save(mep);
        			}
        			
        			
        		}
        		
        	}catch(Exception e){
        		rv.fill(AppConstants.FAILURE, e.getMessage());
        	}
    	}
    	
    	
    	return rv;
    }
	
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
   	public ResultView updateProfile(Long memberId, String firstName, String lastName, String address1, String address2, String city, String country, String province, String postalCode){
       	ResultView rv = new ResultView();
       	User existsUser;
       	
       	if (memberId == null){
       		rv.fill(AppConstants.FAILURE, "Invalid parameter");
       		return rv;
       	}
       		
       	try{
       		existsUser = userDao.findById(memberId);
       		
       		if (existsUser.getPostalCode() == null || !existsUser.getPostalCode().equals(postalCode)){
       			//change weather
       			existsUser.setPostalCode(postalCode);
       			asyncLocationProfile(existsUser, true);
       		}
       		if (firstName != null) 
       			existsUser.setFirstName(firstName);
       		if (lastName != null)
       			existsUser.setLastName(lastName);
       		if (address1 != null)
       			existsUser.setAddress1(address1);
       		if (address2 != null)
       			existsUser.setAddress2(address2);
       		if (city != null)
       			existsUser.setCity(city);
       		if (country != null)
       			existsUser.setCountry(country);
       		if (province != null)
       			existsUser.setProvince(province);
       		
       		userDao.save(existsUser, true);
       		
       		rv.fill(AppConstants.SUCCESS, "Successfully updated member profile");
       	}catch(Exception e){
       		//e.printStackTrace();
       		rv.fill(AppConstants.FAILURE, "Invalid user");
       	}
       	
       	return rv;
       }
    
    @Override
  	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
      public ResultView checkAndUpdateUser(User userParam, String communityCode){
      	System.out.println("*************** Check user ***********");
      	ResultView result = new ResultView();
      	
      	Community community = domainDao.getCommunityByCode(communityCode);
      	if (community != null){
      		User user = userDao.findUser(community.getId(), userParam.getEmail());
          	//If not add this email to account table
          	if (user == null){
          		User tempUser = new User();
          		tempUser.setEmail(userParam.getEmail());
          		tempUser.setFirstName(userParam.getFirstName());
          		tempUser.setLastName(userParam.getLastName());
          		tempUser.setEnabled(true);
          		tempUser.setDefaultCommunity(userParam.getDefaultCommunity());
          		tempUser.setAuthenticateId(userParam.getAuthenticateId());
          		tempUser.setMode(User.ACCOUNT_ZEROFOORPRINT);
          		try {

          			tempUser = userDao.save(tempUser);
          			userDao.flush();
          			result.fill(AppConstants.SUCCESS, "success");
          		} catch (Exception e) {
          			result.fill(AppConstants.FAILURE, "Error while saving a user");
          			return result;
          		}
          	}else{
          		result.fill(AppConstants.USER_ALREADY_EXISTS, "Account exists.");
          	}
          	
          	return result;
      	}else{
      		result.fill(AppConstants.FAILURE, "No Community found error");
      		return result;
      	}
      	
      }
    
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public UserView getUser(String domainName, String accountEmail){
    	System.out.println("Entered get User :: " + new Date());
    	System.out.println("****************** GET USER ::: " + domainName + " :: " + accountEmail);
    	Long communityId = null;
		try {
			communityId = domainDao.getCommunitId(domainName);
			System.out.println("************ Community ID : " + communityId);
		}catch (NoResultException ne){
			System.out.println("************ Community no found.");
		}catch (Exception e) {
			System.out.println("************ Get error in UtilityService for Community. Error: " + e);
		}
		
    	User user = userDao.findUser(communityId, accountEmail);
    	UserView uv = new UserView();
    	if (user != null){
    		uv = new UserView(user);
    		System.out.println("Communication Move :: " + user.getCommunicationMode());
    		if (user.getCommunicationMode() == 1 || user.getCommunicationMode() == 3){
    			CommunicationPreference cp = communicationPreferenceDao.getCommunicationPreference(user.getId());
    			if (cp != null){
    				System.out.println("ALERRRT !! : " + cp.getReceiveEmailAlert() + " :: " + cp.getReceiveEmailNewsletter() + " :: " + cp.getReceiveEmailOffer());
    				uv.setEmailAlert(cp.getReceiveEmailAlert());
    				uv.setEmailNewsletter(cp.getReceiveEmailNewsletter());
    				uv.setEmailOffer(cp.getReceiveEmailOffer());
    			}
    		}
    	}
    	
    	return uv;
    	
    }
    
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<UserView> getAllUsersInCommunity(String domainName, Long accountId, Boolean excludeFriends){
    	List<UserView> uvs = new ArrayList<UserView>();
    	
    	Long communityId = domainDao.getCommunitId(domainName);
    	List<User> users = userDao.getUsersInCommunity(communityId);
    	User currentUser = userDao.findById(accountId);
    	
    	users.remove(currentUser);
    	
    	if (excludeFriends){
    		Groups group = groupsDao.getGroupByAccountIdType(accountId, AppConstants.GROUPSTYPE_DEFAULT);
    		List<AccountGroup> ags = accountGroupDao.getNumberOfMembers(group.getId());
    		for (AccountGroup ag: ags){
    			if (users.contains(ag.getUser())) users.remove(ag.getUser());
    		}
    	}
    	
    	if (users != null && users.size() > 0){
    		for (User user : users){
    			UserView uv = new UserView();
    			
    			String identifier = "";
    			
    			//Bit mask the people's id
    			if (user.getUid() == null || user.getUid().equals("")){
    				identifier = PasswordEncoderUtil.encodePassword(user.getId()+"", AppConstants.MASK_SALT);
    				
    				user.setUid(identifier);
    				userDao.save(user, true);
    				
    				
    			}else{
    				identifier = user.getUid();
    			}
    			
    			uv.setIdentifier(identifier);
    			uv.setFirstName(user.getFirstName());
    			uv.setLastName(user.getLastName());
    			
    			uvs.add(uv);
    		}
    	}
    	
    	return uvs;
    }
    
    
    
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public UserView createUpdateUser(Long userId, User user, String operation) {
		UserView view;
		ResultView result = new ResultView();
		try {
			if (operation.equalsIgnoreCase("CREATE") && userDao.checkName(user.getEmail()) == false) {
				LOG.info("Cannot create user:" + user.getEmail() + ". A user with this name already exists.");
				result.fill(AppConstants.USER_ALREADY_EXISTS, "Account exists.");
				view = new UserView();
				view.setResult(result);
				return view;
			}

			user = userDao.save(user);
			userDao.flush();
			view = new UserView(user);
			result.fill(AppConstants.SUCCESS, "success");
			view.setResult(result);
			LOG.info("User:" + user.getEmail() + " successfully added.");
		} catch (Exception e) {
			LOG.error("Exception occurred in createUpdateUser", e);
			result.fill(AppConstants.UNKNOWN_ERROR, "Unknown error.");
			view = new UserView();
			view.setResult(result);
			return view;
		}
		return view;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public UserView deleteUser(Long userId, User user) {

		UserView response = new UserView();
		ResultView result = new ResultView();
		result.fill(AppConstants.SUCCESS, "success");

		response.setResult(result);
		return response;

	}
	
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public UserView mobileSignup(User user, String communityCode, String locale){
		UserView userView = new UserView();
		ResultView result = new ResultView();
		
		Community community = communityDao.getCommunityByCode(communityCode);
		
		Domain domain = communityDao.getDomainByCommunityId(community.getId());
		
		Long communityId = community.getId();
		User existsUser;
		User newUser = new User();
		try{
			existsUser = userDao.findByNameCommunity(user.getEmail());
			//check if a user with group guest authority
			Account_Authority aa = accountAuthorityDao.getAccountAuthority(existsUser.getId());
			if (aa != null && aa.getUserRole().getAuthority().equals("GROUP_GUEST")){
				existsUser.setPasswordSalt(KeyGenerationUtil.generate64CharacterKey());
				existsUser.setPassword(PasswordEncoderUtil.encodePassword(user.getPassword(), existsUser.getPasswordSalt()));
				
				existsUser.setMode(user.getMode());
				existsUser.setFirstName(user.getFirstName());
				existsUser.setLastName(user.getLastName());
				existsUser.setBirthYear(user.getBirthYear());
				existsUser.setGender(user.getGender());
				existsUser.setPostalCode(user.getPostalCode());
				
				asyncLocationProfile(existsUser, false);
				asyncAutoCampaignJoin(existsUser, communityCode);
				
				UserRole authority = userDao.getUserRole(AppConstants.ROLE_USER);
				aa.setUser(existsUser);
				aa.setUserRole(authority);
				accountAuthorityDao.save(aa, true);
				
				result.fill(AppConstants.SUCCESS_ADD_NEW_USER, "Signup success.");
				userView = new UserView(existsUser);
				userView.setResult(result);
				userView.setCommunityCode(community.getCode());
				try{
					createDefaultGroup(existsUser.getId());
				}catch(Exception exception){
					exception.printStackTrace();
				}
				
				try{
					NotificationServiceImpl notificationService = new NotificationServiceImpl();
					
					
		    		notificationService.notifySignup(user, LocaleUtil.getLocale(locale), domain.getName());
						
				}catch(Exception ex){
					ex.printStackTrace();
					result.fill(AppConstants.SEND_NOTIFY_EMAIL_FAILURE, "Account success create, but send notify email wrong.");
					userView.setResult(result);
					return userView;
				}
				
				
				return userView;
				
			}
		}catch(NoResultException e){

			if(user.getMode() == User.ACCOUNT_ZEROFOORPRINT){
				newUser.setPasswordSalt(KeyGenerationUtil.generate64CharacterKey());
				newUser.setPassword(PasswordEncoderUtil.encodePassword(user.getPassword(), newUser.getPasswordSalt()));
			}else if(user.getMode() == User.ACCOUNT_FACEBOOK){
				newUser.setPasswordSalt(null);
				newUser.setPassword(null);
			}else{
				newUser.setPasswordSalt(null);
				newUser.setPassword(null);
			}
			
			//System.out.println("----------Signup in : " + utilityService.getCommuniyId(community) );
			
			
			newUser.setDefaultCommunity(communityId);
			newUser.setEnabled(true);
			newUser.setEmail(user.getEmail());
			newUser.setMode(user.getMode());
			newUser.setAuthenticateId(user.getAuthenticateId());
			
			newUser.setFirstName(user.getFirstName());
			newUser.setLastName(user.getLastName());
			Status activeStatus = statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE);
			newUser.setStatus(activeStatus);
			
			newUser.setBirthYear(user.getBirthYear());
			newUser.setGender(user.getGender());
			newUser.setPostalCode(user.getPostalCode());
			
			//newUser set role
			UserRole authority = userDao.getUserRole(AppConstants.ROLE_USER);
			
			//userDao.save(newUser, true);
			Account_Authority aa = new Account_Authority();
			aa.setUser(newUser);
			aa.setUserRole(authority);
			accountAuthorityDao.save(aa, true);
			
			newUser = userDao.findByName(newUser.getEmail(), newUser.getDefaultCommunity(), newUser.getMode());
				
			try {
				saveLocationProfile(newUser, false);
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			joinAutoCampaign(newUser, communityCode);
			
			try{
				createDefaultGroup(newUser.getId());
			}catch(Exception exception){
				exception.printStackTrace();
			}
			result.fill(AppConstants.SUCCESS_ADD_NEW_USER, "Signup success.");
			userView = new UserView(newUser);
			userView.setResult(result);
			userView.setCommunityCode(community.getCode());
			
			System.out.println("*******************" + newUser.getId());
			
				try{
					
					NotificationServiceImpl notificationService = new NotificationServiceImpl();
	    			notificationService.notifySignup(user, LocaleUtil.getLocale(locale), domain.getName());
					
				}catch(Exception ex){
					ex.printStackTrace();
					result.fill(AppConstants.SEND_NOTIFY_EMAIL_FAILURE, "Account success create, but send notify email wrong.");
					userView.setResult(result);
					return userView;
				}
			
			
			
			return userView;
			
		}catch(Exception e){
			e.printStackTrace();
			result.fill(AppConstants.UNKNOWN_ERROR, "Unknown error.");
			userView.setResult(result);
			return userView;
		}
		
		result.fill(AppConstants.USER_ALREADY_EXISTS, "Account exists.");
		LOG.debug("User already exists, email " + existsUser.getEmail());
		userView.setResult(result);
		return userView;

	}
	
	/**
	 * Create another separate thread for saving a member location
	 * @param user
	 * @param update
	 */
	private void asyncLocationProfile( final User user, final Boolean update){
		Runnable task = new Runnable(){
			@Override
			public void run(){
				try{
					saveLocationProfile(user, update);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		};
		new Thread(task, "LocationServiceThread").start();
		
	}
	
	/**
	 * Create another separate thread for checking if there is any auto campaign that member can join
	 * @param user
	 * @param domain
	 */
	private void asyncAutoCampaignJoin( final User user, final String domain){
		Runnable task = new Runnable(){
			@Override
			public void run(){
				try{
					joinAutoCampaign(user, domain);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		};
		new Thread(task, "AutoCampaignThread").start();
		
	}
	
	/**
	 * Join a member to campaign if there are any available auto campaigns
	 * @param user
	 * @param domain
	 */
	private void joinAutoCampaign(User user, String domain){
		System.out.println("Entered auto campaign : " + new Date());
		
		//Get user segments
		List<Segment> segments = segmentService.getMemberSegment(user);
		
		List<Category> categories = categoryDao.getCategories(AppConstants.CATEGORYTYPE_CAMPAIGN);
		
		List<Campaign> campaigns = new ArrayList<Campaign>();
		
		//Get available auto join campaign
		if (categories != null && categories.size() > 0){
			for (Category category : categories){
				if (segments != null && segments.size() > 0){
					for (Segment segment : segments){
						List<Campaign> tempCampaigns = campaignDao.getCampaignsBySegmentWithCategory(segment.getId(), category.getId());
						for (Campaign tempCampaign : tempCampaigns){
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
				
				//Send an auto joined email to a member
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
	
	/**
	 * Based on member's postal code, get latitude and longitude, and store it to member profile table
	 * @param user
	 * @param update
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private void saveLocationProfile(User user, Boolean update) throws ClientProtocolException, IOException{
		Double[] coordinates;
		
		//Gets a coordinates based on postal code
		coordinates = GoogleMapAPI.getGPS(user.getPostalCode());
		
		if (coordinates[0] != null && coordinates[1] != null){
			//0 == latitude
			//1 == longitude
			//get location Id based on the coordinates
			Long locationId = userDao.getLocationId(coordinates[0], coordinates[1]);
			if (locationId != null){
				Location location = locationDao.findById(locationId);
				if (location != null){
					if (update){
						MemberExtendedProfile mep = memberExtendedProfileDao.getMemberExtendedProfile(user.getId());
						if (mep != null){
							mep.setLastModified(new Date());
							mep.setLocation(location);
							memberExtendedProfileDao.save(mep, true);
						}else{
							mep = new MemberExtendedProfile();
							mep.setUser(user);
							mep.setLocation(location);
							memberExtendedProfileDao.save(mep);
						}
						
					}else{
						MemberExtendedProfile mep = new MemberExtendedProfile();
						mep.setUser(user);
						mep.setLocation(location);
						memberExtendedProfileDao.save(mep);
					}
					
				}
			}
		}
	}
	
	/**
	 * Creates a member default group if does not exist
	 * @param memberId
	 */
	private void createDefaultGroup(Long memberId){
		Groups group = groupsDao.getGroupByAccountIdType(memberId, AppConstants.GROUPSTYPE_DEFAULT);
		if (group ==null){
			//If this account doesn't have default group then add to Group
			group = new Groups();
			group.setAccountId(memberId);
			GroupsType gt = groupsDao.getGroupsTypeByName(AppConstants.GROUPSTYPE_DEFAULT);
			group.setGroupsType(gt);
			User user = userDao.findById(memberId);
			group.setCommunityId(user.getDefaultCommunity());
			group.setImageUrl("/images/rewards/groupsampleimage.png");
			group.setName("My group");
			
			groupsDao.save(group);
		}
	}
	
	@Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public ResultView deactivateAccount(Long memberId){
    	ResultView rv = new ResultView();
    	
    	if (memberId == null){
    		rv.fill(AppConstants.FAILURE, "Invalid parameter");
    		return rv;
    	}
    	
    	User userInfo = userDao.findById(memberId);
    	
    	if (userInfo == null){
    		rv.fill(AppConstants.FAILURE, "Member does not exist");
    		return rv;
    	}
    	
    	Status inactiveStatus = statusDao.getStatusByCode(AppConstants.STATUS_INACTIVE);
    	userInfo.setStatus(inactiveStatus);
    	userDao.save(userInfo, true);
    	
    	rv.fill(AppConstants.SUCCESS, "Deactivated the member");
    	return rv;
    }
	
	@Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public ResultView updatePreferences(Long memberId, Boolean emailAlert, Boolean emailNewsletter, Boolean emailOffer,String language,
    		Integer communicationMode, String communicationEmail, String alternativeEmail, String mobilePhone, String houseArea, Integer occupants,
    		String houseHeating, String waterHeating){
    	ResultView rv = new ResultView();
    	
    	if (memberId == null){
    		rv.fill(AppConstants.FAILURE, "Invalid parameter");
    		return rv;
    	}
    	
    	User userInfo = userDao.findById(memberId);
    	
    	if (userInfo == null){
    		rv.fill(AppConstants.FAILURE, "Member does not exist");
    		return rv;
    	}
    	
    	if (language != null) userInfo.setLanguage(language);
    	if (communicationMode != null) userInfo.setCommunicationMode(communicationMode);
    	if (communicationEmail != null) userInfo.setCommunicationEmail(communicationEmail);
    	if (alternativeEmail != null) userInfo.setAlternativeEmail(alternativeEmail);
    	if (mobilePhone != null) userInfo.setMobilePhone(mobilePhone);
    	
    	userDao.save(userInfo, true);
    	
    	if (communicationMode != null && (communicationMode == 1 || communicationMode == 3)){
    		//Get communication preference if exists
    		CommunicationPreference userCP = communicationPreferenceDao.getCommunicationPreference(memberId);
    		if (userCP == null){
    			userCP = new CommunicationPreference();
    			userCP.setUser(userInfo);
    			if (emailAlert != null) userCP.setReceiveEmailAlert(emailAlert);
    			if (emailNewsletter != null) userCP.setReceiveEmailNewsletter(emailNewsletter);
    			if (emailOffer != null) userCP.setReceiveEmailOffer(emailOffer);
    			communicationPreferenceDao.save(userCP);
    		}else{
    			userCP.setLastModified(new Date());
    			if (emailAlert != null) userCP.setReceiveEmailAlert(emailAlert);
    			if (emailNewsletter != null) userCP.setReceiveEmailNewsletter(emailNewsletter);
    			if (emailOffer != null) userCP.setReceiveEmailOffer(emailOffer);
    			communicationPreferenceDao.save(userCP, true);
    		}
    	}
    	
    	if (houseArea != null && occupants != null && houseHeating != null && waterHeating != null){
    		MemberHouseholdProfile mhp = memberHouseholdProfileDao.getMemberHouseholdProfileByMember(memberId);
    		if (mhp == null){
    			mhp = new MemberHouseholdProfile();
    			mhp.setUser(userInfo);
    			if (houseArea != null){
    				MemberProfileValue houseAreaPV = memberHouseholdProfileDao.getMemberProfileValue(houseArea.toUpperCase());
    				if (houseAreaPV != null) mhp.setHouseAreaPV(houseAreaPV);
    			}
    			if (occupants != null){
    				MemberProfileValue occupantsPV = memberHouseholdProfileDao.getMemberProfileValue(occupants + "" );
    				if( occupantsPV != null) mhp.setOccupantsPV(occupantsPV);
    			}
    			if (houseHeating != null){
    				MemberProfileValue houseHeatingPV = memberHouseholdProfileDao.getMemberProfileValue(houseHeating.toUpperCase());
    				if (houseHeatingPV != null) mhp.setHouseHeatingPV(houseHeatingPV);
    			}
    			if (waterHeating != null){
    				MemberProfileValue waterHeatingPV = memberHouseholdProfileDao.getMemberProfileValue(waterHeating.toUpperCase());
    				if (waterHeatingPV != null) mhp.setWaterHeatingPV(waterHeatingPV);
    			}
    			
    			memberHouseholdProfileDao.save(mhp);
    		}else{
    			if (houseArea != null){
    				MemberProfileValue houseAreaPV = memberHouseholdProfileDao.getMemberProfileValue(houseArea.toUpperCase());
    				if (houseAreaPV != null) mhp.setHouseAreaPV(houseAreaPV);
    			}
    			if (occupants != null){
    				MemberProfileValue occupantsPV = memberHouseholdProfileDao.getMemberProfileValue(occupants + "" );
    				if( occupantsPV != null) mhp.setOccupantsPV(occupantsPV);
    			}
    			if (houseHeating != null){
    				MemberProfileValue houseHeatingPV = memberHouseholdProfileDao.getMemberProfileValue(houseHeating.toUpperCase());
    				if (houseHeatingPV != null) mhp.setHouseHeatingPV(houseHeatingPV);
    			}
    			if (waterHeating != null){
    				MemberProfileValue waterHeatingPV = memberHouseholdProfileDao.getMemberProfileValue(waterHeating.toUpperCase());
    				if (waterHeatingPV != null) mhp.setWaterHeatingPV(waterHeatingPV);
    			}
    			
    			memberHouseholdProfileDao.save(mhp, true);
    		}
    	}
    	
		
    	rv.fill(AppConstants.SUCCESS, "Successfully updated member setting");
    	return rv;
    }
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public UserView getUser(Long memberId) {
		UserView uv = new UserView();
    	ResultView rv = new ResultView();
    	
    	if (memberId != null){
    		User user = userDao.findById(memberId);
    		
    		if (user != null){
    			Community community = communityDao.findById(user.getDefaultCommunity());
    			uv.setCommunityCode(community.getCode());
    			
    			if (user.getCommunicationMode() != null && (user.getCommunicationMode() == 1 || user.getCommunicationMode() == 3)){
        			CommunicationPreference cp = communicationPreferenceDao.getCommunicationPreference(user.getId());
        			if (cp != null){
        				uv.setEmailAlert(cp.getReceiveEmailAlert());
        				uv.setEmailNewsletter(cp.getReceiveEmailNewsletter());
        				uv.setEmailOffer(cp.getReceiveEmailOffer());
        			}else{
        				cp = new CommunicationPreference();
        				cp.setUser(user);
        				cp.setReceiveEmailAlert(true);
        				cp.setReceiveEmailNewsletter(true);
        				cp.setReceiveEmailOffer(true);
        				
        				communicationPreferenceDao.save(cp);
        				
        				uv.setEmailAlert(true);
        				uv.setEmailNewsletter(true);
        				uv.setEmailOffer(true);
        				
        			}
        		}
    			
    			MemberHouseholdProfile mhp = memberHouseholdProfileDao.getMemberHouseholdProfileByMember(memberId);
        		if (mhp != null){
        			uv.setHouseArea(mhp.getHouseAreaPV().getValue());
        			uv.setOccupants(mhp.getOccupantsPV().getValue());
        			uv.setHouseHeating(mhp.getHouseHeatingPV().getValue());
        			uv.setWaterHeating(mhp.getWaterHeatingPV().getValue());
        		}
        		
    		}else{
    			rv.fill(AppConstants.FAILURE, "User does not exists");
    			uv.setResult(rv);
    		}
			
    		
    	}else{
    		rv.fill(AppConstants.FAILURE, "Invalid parameter");
    		uv.setResult(rv);
    	}
		return uv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public UserView getMemberProfile(Long memberId){
		System.out.println("Get Member Profile :: " + new Date() + " : " + memberId);
		
    	UserView uv = new UserView();
    	ResultView rv = new ResultView();
    	if (memberId != null){
    		User user = userDao.findById(memberId);
    		
    		if (user == null){
    			rv.fill(AppConstants.FAILURE, "User does not exists");
    			uv.setResult(rv);
    		}else{
    			uv = new UserView(user);
    			Community community = communityDao.findById(user.getDefaultCommunity());
    			uv.setCommunityCode(community.getCode());
    		}
    	}else{
    		rv.fill(AppConstants.FAILURE, "Invalid parameter");
    		uv.setResult(rv);
    	}
    	
    	
		return uv;
    }
	
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public UserView signin(String domain, String email, String password, Integer mode, String firstName, String lastName, Integer gender) {
		
		UserView userView = new UserView();
		ResultView result = new ResultView();
		
		//System.out.println("******** ccccc ***** Community ID : " + utilityService.getCommuniyId(communityName));
		
		if (email == null || (password == null && mode != null && mode == User.ACCOUNT_ZEROFOORPRINT)|| mode == null){
			result.fill(AppConstants.NO_USER_FOUND, "Missing required parameters");
			userView.setResult(result);
			return userView;
		}
		
		User user = null;
		try{
			user = userDao.findUserForLogin(email,mode);
		}catch(NoResultException e){
			
			if (mode == User.ACCOUNT_FACEBOOK){
				try{
					user = userDao.findUserForLogin(email, User.ACCOUNT_FACEBOOK);
				}catch(NoResultException exception){
					//Here sign up with facebook
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setEmail(email);
					user.setGender(gender);
					Status activeStatus = statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE);
					user.setStatus(activeStatus);
					Community community =userDao.getCommunity(domain);
					user.setDefaultCommunity(community.getId());
					//newUser set role
					UserRole authority = userDao.getUserRole(AppConstants.ROLE_USER);
					
					//userDao.save(newUser, true);
					Account_Authority aa = new Account_Authority();
					aa.setUser(user);
					aa.setUserRole(authority);
					accountAuthorityDao.save(aa, true);
					
					user = userDao.findUserForLogin(email, mode);
					
					joinAutoCampaign(user, domain);
					
					try{
						NotificationServiceImpl notificationService = new NotificationServiceImpl();
		    			notificationService.notifySignup(user, LocaleUtil.getLocale("en"), domain);
						
					}catch(Exception ex){
						ex.printStackTrace();
					}
					
					//Set Member ACtivity 
					memberActivityService.setMemberActivity(user.getId(), AppConstants.REFERENCECODE_SIGN_UP, user.getId()+"", null, null);
					//Point Issuance
					accountService.processIssuanceByAccount(user.getId());
				}
				
				userView = new UserView(user);
				
				return userView;
			}else{
				result.fill(AppConstants.NO_USER_FOUND, "Account not found.");
				System.out.println("Not found in the system :" + result.getResultCode());
				userView.setResult(result);
				return userView;
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			result.fill(AppConstants.UNKNOWN_ERROR, "Unknown error.");
			userView = new UserView();
			userView.setResult(result);
			return userView;
		}

		if(mode == User.ACCOUNT_ZEROFOORPRINT || mode == User.ACCOUNT_CORPORATE){

			String encodePasswd = "";
			if(password!=null){
				encodePasswd = PasswordEncoderUtil.encodePassword(password, user.getPasswordSalt());
			}

			if(password == null || !encodePasswd.equals(user.getPassword())){
				result.fill(AppConstants.WRONG_PASSWORD, "wrong password");
				userView = new UserView();
				userView.setResult(result);
				return userView;
			}else {
				System.out.println("SuccesS!");
				//result.fill(AppConstants.SUCCESS, "success");
			}
			
		}else if(mode == User.ACCOUNT_FACEBOOK){
			//result.fill(AppConstants.SUCCESS, "success");
		}else{
			
		}

		userView = new UserView(user);
		
		//userView.setResult(result);
		return userView;
	}
	
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public UserView corporateSignup(String firstName, String lastName, String email, String password,
			String communityCode, Double rooms, Integer occupants){
		UserView userView = new UserView();
		ResultView result = new ResultView();
		if (firstName == null || lastName == null || email == null || password == null || communityCode == null || rooms == null || occupants == null){
			
			result.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			userView.setResult(result);
			return userView;
		}
		
		//Check to see community code is valid or not
		Community community = domainDao.getCommunityByCode(communityCode);
		if (community == null){
			result.fill(AppConstants.FAILURE, "Invalid community code");
			userView.setResult(result);
			return userView;
		}
		
		userView.setEmail(email);
		Integer mode = User.ACCOUNT_CORPORATE;
		
		User user = new User();
		try{
			user = userDao.findUserByEmailAndModeWithException(email, mode);
			//check if a user with group guest authority
			Account_Authority aa = accountAuthorityDao.getAccountAuthority(user.getId());
			if (aa != null && aa.getUserRole().getAuthority().equals("GROUP_GUEST")){
				if(mode == User.ACCOUNT_CORPORATE){
					user.setPasswordSalt(KeyGenerationUtil.generate64CharacterKey());
					user.setPassword(PasswordEncoderUtil.encodePassword(password, user.getPasswordSalt()));
				}
				
				user.setMode(mode);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				 								
				UserRole authority = userDao.getUserRole(AppConstants.ROLE_USER);
				aa.setUser(user);
				aa.setUserRole(authority);
				accountAuthorityDao.save(aa, true);
				
				userView = new UserView(user);
				
				user = userDao.findActiveUserByEmail(user.getEmail());
				
				//Add this user to member extended Profile..
				MemberExtendedProfile mep = new MemberExtendedProfile();
				mep.setUser(user);
				mep.setOccupants(occupants);
				mep.setRooms(rooms);
				
				memberExtendedProfileDao.save(mep);
				
				return userView;
				
			}
		}catch(NoResultException e){
			
			if(mode == User.ACCOUNT_CORPORATE){
				user.setPasswordSalt(KeyGenerationUtil.generate64CharacterKey());
				user.setPassword(PasswordEncoderUtil.encodePassword(password, user.getPasswordSalt()));
			}
			
			user.setDefaultCommunity(community.getId());
			user.setEnabled(true);
			user.setEmail(email);
			user.setMode(mode);
			
			
			user.setFirstName(firstName);
			user.setLastName(lastName);
			Status activeStatus = statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE);
			user.setStatus(activeStatus);
			
			//newUser set role
			UserRole authority = userDao.getUserRole(AppConstants.ROLE_USER);
			
			Account_Authority aa = new Account_Authority();
			aa.setUser(user);
			aa.setUserRole(authority);
			accountAuthorityDao.save(aa, true);
			
			user = userDao.findActiveUserByEmail(user.getEmail());
			
			//Add this user to member extended Profile..
			MemberExtendedProfile mep = new MemberExtendedProfile();
			mep.setUser(user);
			mep.setOccupants(occupants);
			mep.setRooms(rooms);
			
			memberExtendedProfileDao.save(mep);
			
			userView = new UserView(user);
			
			return userView;
			
		}catch(Exception e){
			e.printStackTrace();
			result.fill(AppConstants.UNKNOWN_ERROR, "Unknown");
			userView = new UserView();
			userView.setResult(result);
			return userView;
		}
		
		result.fill(AppConstants.USER_ALREADY_EXISTS, "Account exists");
		
		userView = new UserView();
		userView.setResult(result);
		
		return userView;
	}
	/*
	 * (non-Javadoc)
	 * @see net.zfp.service.UserService#signup(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public UserView signup(String domain, String firstName, String lastName, String email, String password, String postalCode, Integer birthYear, Integer gender, Integer mode, String locale, String portalName) {
		//Hiberante user object will be used to store to database
		User user = new User();
		
		//UserView will store member information which will be returned 
		UserView userView = new UserView();
		
		//ResultView will store status of sign up process
		ResultView result = new ResultView();
		
		if (email == null || (password == null && mode != null && mode == User.ACCOUNT_ZEROFOORPRINT)|| mode == null
				||firstName == null || lastName == null || domain == null || postalCode == null || birthYear == null || gender == null){
			result.fill(AppConstants.FAILURE, "Missing required parameters");
			userView = new UserView();
			userView.setResult(result);
		}
		
		userView.setEmail(email);
		
		//Using the domain given get associated community
		Community community = domainDao.getCommunity(domain);
		try{
			user = userDao.findUserByEmailAndModeWithException(email, mode);
			//check if a user with group guest authority
			Account_Authority aa = accountAuthorityDao.getAccountAuthority(user.getId());
			if (aa != null && aa.getUserRole().getAuthority().equals("GROUP_GUEST")){
				if(mode == User.ACCOUNT_ZEROFOORPRINT){
					user.setPasswordSalt(KeyGenerationUtil.generate64CharacterKey());
					user.setPassword(PasswordEncoderUtil.encodePassword(password, user.getPasswordSalt()));
				}else if(mode == User.ACCOUNT_FACEBOOK){
					user.setPasswordSalt(null);
					user.setPassword(null);
				}else{
					user.setPasswordSalt(null);
					user.setPassword(null);
				}
				user.setMode(mode);
				user.setFirstName(firstName);
				user.setLastName(lastName);

				user.setBirthYear(birthYear);
				user.setGender(gender);
				user.setPostalCode(postalCode);
				 
				//Async method: Set a member's location to database 
				asyncLocationProfile(user, false);
				//Async method: Check if any auto join campaign available and if there is then auto join the campaign.
				asyncAutoCampaignJoin(user, domain);
				
				try{
					//Create a friend list of the member
					createDefaultGroup(user.getId());
				}catch(Exception exception){
					exception.printStackTrace();
				}
				
				UserRole authority = userDao.getUserRole(AppConstants.ROLE_USER);
				aa.setUser(user);
				aa.setUserRole(authority);
				accountAuthorityDao.save(aa, true);
				
				userView = new UserView(user);
				
				//If locale is not defined then set it to Canadian English
				if (locale == null) locale = "en_CA";
				
				//If sign up process was called from Carbon portal then it is going to send Carbon format of welcome email
				if (portalName != null && portalName.equals("CARBON")){
					try{
						NotificationServiceImpl notificationService = new NotificationServiceImpl();
		    			notificationService.notifyCarbonSignup(user, locale);
						
					}catch(Exception ex){
						ex.printStackTrace();
						result.fill(AppConstants.SEND_NOTIFY_EMAIL_FAILURE, "Account success create, but send notify email wrong.");
						userView.setResult(result);
						return userView;
					}
				}else{
					try{
						NotificationServiceImpl notificationService = new NotificationServiceImpl();
		    			notificationService.notifySignup(user, LocaleUtil.getLocale(locale), domain);
						
					}catch(Exception ex){
						ex.printStackTrace();
						result.fill(AppConstants.SEND_NOTIFY_EMAIL_FAILURE, "Account success create, but send notify email wrong.");
						userView.setResult(result);
						return userView;
					}
				}
				
				return userView;
				
			}
		}catch(NoResultException e){
			
			if(mode == User.ACCOUNT_ZEROFOORPRINT){
				user.setPasswordSalt(KeyGenerationUtil.generate64CharacterKey());
				user.setPassword(PasswordEncoderUtil.encodePassword(password, user.getPasswordSalt()));
			}else if(mode == User.ACCOUNT_FACEBOOK){
				user.setPasswordSalt(null);
				user.setPassword(null);
			}else{
				user.setPasswordSalt(null);
				user.setPassword(null);
			}
			
			user.setDefaultCommunity(community.getId());
			user.setEnabled(true);
			user.setEmail(email);
			user.setMode(mode);
			
			user.setBirthYear(birthYear);
			user.setGender(gender);
			user.setPostalCode(postalCode);
			
			user.setFirstName(firstName);
			user.setLastName(lastName);
			Status activeStatus = statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE);
			user.setStatus(activeStatus);
			
			//newUser set role
			UserRole authority = userDao.getUserRole(AppConstants.ROLE_USER);
			
			Account_Authority aa = new Account_Authority();
			aa.setUser(user);
			aa.setUserRole(authority);
			accountAuthorityDao.save(aa, true);
			
			user = userDao.findActiveUserByEmail(user.getEmail());
				
			try {
				//Async method: Set a member's location to database 
				saveLocationProfile(user, false);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			
			//Async method: Check if any auto join campaign available and if there is then auto join the campaign.
			joinAutoCampaign(user, domain);
			
			try{
				//Create a friend list of the member
				createDefaultGroup(user.getId());
			}catch(Exception exception){
				exception.printStackTrace();
			}
			
			userView = new UserView(user);
			
			//If locale is not defined then set it to Canadian English
			if (locale == null) locale = "en_CA";
			
			//If sign up process was called from Carbon portal then it is going to send Carbon format of welcome email
			if (portalName != null && portalName.toUpperCase().equals("CARBON")){
				try{
					
					NotificationServiceImpl notificationService = new NotificationServiceImpl();
	    			notificationService.notifyCarbonSignup(user, locale);
					
				}catch(Exception ex){
					ex.printStackTrace();
					result.fill(AppConstants.SEND_NOTIFY_EMAIL_FAILURE, "Account success create, but send notify email wrong.");
					userView.setResult(result);
					return userView;
				}
			}else{
				try{
					
					NotificationServiceImpl notificationService = new NotificationServiceImpl();
	    			notificationService.notifySignup(user, LocaleUtil.getLocale(locale), domain);
					
				}catch(Exception ex){
					ex.printStackTrace();
					result.fill(AppConstants.SEND_NOTIFY_EMAIL_FAILURE, "Account success create, but send notify email wrong.");
					userView.setResult(result);
					return userView;
				}
			}
			
			
			return userView;
			
		}catch(Exception e){
			e.printStackTrace();
			result.fill(AppConstants.UNKNOWN_ERROR, "Unknown");
			userView = new UserView();
			userView.setResult(result);
			return userView;
		}
		
		result.fill(AppConstants.USER_ALREADY_EXISTS, "Account exists");
		
		userView = new UserView();
		userView.setResult(result);
		return userView;

	}

}
