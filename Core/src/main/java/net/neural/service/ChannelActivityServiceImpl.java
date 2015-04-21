package net.zfp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Account_Authority;
import net.zfp.entity.Domain;
import net.zfp.entity.Status;
import net.zfp.entity.User;
import net.zfp.entity.UserRole;
import net.zfp.entity.channel.ChannelActivity;
import net.zfp.entity.channel.ChannelType;
import net.zfp.entity.memberactivity.MemberActivity;
import net.zfp.entity.memberactivity.MemberActivityType;
import net.zfp.entity.mobile.MobileDevice;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.upload.UploadImage;
import net.zfp.form.MobileDeviceForm;
import net.zfp.notify.NotificationService;
import net.zfp.notify.impl.NotificationServiceImpl;
import net.zfp.util.AppConstants;
import net.zfp.view.ResultView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class ChannelActivityServiceImpl implements ChannelActivityService {

	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Status> statusDao;
	@Resource
	private EntityDao<ChannelActivity> channelActivityDao;
	@Resource
	private EntityDao<MemberActivity> memberActivityDao;
	@Resource
	private EntityDao<Account_Authority> accountAuthorityDao;
	@Resource
	private EntityDao<Offer> offerDao;
	@Inject
    private NotificationService notificationService;
	@Resource
	private EntityDao<UploadImage> uploadImageDao;
	@Resource
	private EntityDao<MobileDevice> mobileDeviceDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setMobileDeviceInfo(Long memberId, String OS, String hardwareModel, String phoneName, String carrier, String identifier, String appversion){
		ResultView rv = new ResultView();
		
		if (memberId == null || OS == null || hardwareModel == null || phoneName == null || carrier == null || identifier == null || appversion == null){
			rv.fill(AppConstants.FAILURE, "Missing parameter(s)");
			return rv;
		}
		try{
			MobileDevice md = mobileDeviceDao.getMobileDeviceByIdentifier(identifier);
			
			if (md == null){
				md = new MobileDevice();
				User user = userDao.findById(memberId);
				if (user == null){
					rv.fill(AppConstants.SUCCESS, "User does not exists");
					return rv;
				}
				
				md.setUser(user);
				md.setCreated(new Date());
				md.setOS(OS);
				md.setHardwareModel(hardwareModel);
				md.setPhoneName(phoneName);
				md.setCarrier(carrier);
				md.setIdentifier(identifier);
				md.setAppversion(appversion);
				mobileDeviceDao.save(md,true);
				
			}else{
				User user = userDao.findById(memberId);
				if (user == null){
					rv.fill(AppConstants.SUCCESS, "User does not exists");
					return rv;
				}
					
				md.setUser(user);
				md.setCreated(new Date());
				md.setOS(OS);
				md.setHardwareModel(hardwareModel);
				md.setPhoneName(phoneName);
				md.setCarrier(carrier);
				md.setIdentifier(identifier);
				md.setAppversion(appversion);
				mobileDeviceDao.save(md);
				
			}
			
			rv.fill(AppConstants.SUCCESS, "Successfully saved mobile device information");
		}catch(Exception e){
			rv.fill(AppConstants.FAILURE, e.getMessage());
		}
			
		
		return rv;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setUploadImage(String imageUrl, Long offerCode){
		
		ResultView rv = new ResultView();
		if (imageUrl == null || offerCode == null){
			rv.fill(AppConstants.FAILURE, "Missing parameter(s)");
			return rv;
		}
		//Get AccountId
		Long code = System.currentTimeMillis();
		UploadImage ui = new UploadImage();
		ui.setCode(code);
		ui.setImageUrl(imageUrl);
		ui.setDate(new Date());
		Offer offer = offerDao.getOfferByCode(offerCode);
		ui.setOffer(offer);
		try{
			uploadImageDao.save(ui);
			rv.fill(AppConstants.SUCCESS, code+"");
		}catch(Exception e){
			rv.fill(AppConstants.FAILURE, "Failed while saving to the system");
		}
		
		return rv;
	}
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setChannelActivity(String channelType, Long memberId, String activityType){
		
		ResultView rv = new ResultView();
		
		if (channelType == null || memberId == null || activityType == null){
			rv.fill(AppConstants.FAILURE, "Missing parameter(s)");
			return rv;
		}
		Date now = new Date();
		//Get Member ActivityType using activityType
		MemberActivityType mat = memberActivityDao.getMemberActivityType(activityType);
		
		if (mat != null){
			ChannelType ct = channelActivityDao.getChannelType(channelType);
			if (ct != null){
				User member = userDao.findById(memberId);
				ChannelActivity ca = new ChannelActivity();
				ca.setChannelType(ct);
				ca.setDate(now);
				ca.setMember(member);
				ca.setMemberActivityType(mat);
				
				try{
					channelActivityDao.save(ca);
					rv.fill(AppConstants.SUCCESS, "Channel Activity Saved");
					System.out.println("Channel Activity Saved");
				}catch(Exception e){
					e.printStackTrace();
					rv.fill(AppConstants.FAILURE, "Channel Activity Saved failed");
				}
				
			}else{
				rv.fill(AppConstants.FAILURE, "No such channel : " + channelType);
				System.out.println("No such channel : " + channelType);
			}
		}else{
			rv.fill(AppConstants.FAILURE, "No such member activity type : " + activityType);
			System.out.println("No such member activity type : " + activityType);
		}
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView shareLinkByEmail(Long accountId, String guestemails, String members, String url, String domainName){
		
		ResultView rv = new ResultView();
		
		if (accountId == null || (guestemails == null && members == null) || url == null || domainName == null){
			rv.fill(AppConstants.FAILURE, "Missing parameter(s)");
			return rv;
		}
		
		if (accountId != null){
			
			List<User> users = new ArrayList<User>();
			
			User user = userDao.findById(accountId);
			
			String[] guestAccounts = null;
			String[] memberAccounts = null;
			if (guestemails != null && !guestemails.equals("")){
				guestAccounts = guestemails.split(";");
			}
			if (members != null && !members.equals("")){
				memberAccounts = members.split(";");
			}
			
			String firstName ="" ;
			String lastName ="";
			String email ="";
			
			if (guestAccounts != null){
				for (int i = 0; i < guestAccounts.length; i++ ){
					String userInfo = guestAccounts[i];
					String[] tokens = userInfo.split("::");
					
					if (tokens.length != 3){
						rv.fill(AppConstants.FAILURE, "Invalid parameter format... should be ( First Name :: last Name :: email )");
						return rv;
					}
					firstName = tokens[0];
					lastName = tokens[1];
					email = tokens[2];
					
					//Email to LowerCase
					email = email.toLowerCase();
					
					//First Check if this account exists
					User newUser = userDao.findActiveUserByEmail(email);
					
					if (newUser == null){
						//Else create in account table, account authority with (GUEST)
						newUser = new User();
						newUser.setEmail(email);
						newUser.setFirstName(firstName);
						newUser.setLastName(lastName);
						newUser.setDefaultCommunity(user.getDefaultCommunity());
						newUser.setEnabled(true);
						newUser.setMode(User.ACCOUNT_ZEROFOORPRINT);
						Status status = statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE);
						newUser.setStatus(status);
						
						Account_Authority aa = new Account_Authority();
			    		UserRole authority = userDao.getUserRole(AppConstants.ROLE_GROUP_GUEST);
						aa.setUser(newUser);
						aa.setUserRole(authority);
						accountAuthorityDao.save(aa, true);
						
					}
					
					User tempInvitee = new User();
					tempInvitee.setEmail(email);
					tempInvitee.setFirstName(firstName);
					tempInvitee.setLastName(lastName);
					
					//Notification service kicks in here
					try{
						notificationService = new NotificationServiceImpl();
						notificationService.notifyShareLink(user, tempInvitee, url, domainName);
					}catch(Exception e){
					}
					
					rv.fill(AppConstants.SUCCESS, "Successfully shared the link");
				}
			}
			
			if (memberAccounts != null){
				for (int i = 0; i < memberAccounts.length; i++ ){
					String identifier = memberAccounts[i];
					
					User tempInvitee = userDao.getUserByUID(identifier);
					
					//Notification service kicks in here
					try{
						notificationService = new NotificationServiceImpl();
						notificationService.notifyShareLink(user, tempInvitee, url, domainName);
					}catch(Exception e){
					}
					
					rv.fill(AppConstants.SUCCESS, "Successfully shared the link");
					
				}
			}
			
		}
		
		return rv;
	}
}
