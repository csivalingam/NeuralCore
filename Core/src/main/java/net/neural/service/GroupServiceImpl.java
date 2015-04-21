package net.zfp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Account_Authority;
import net.zfp.entity.Domain;
import net.zfp.entity.EmailTemplate;
import net.zfp.entity.Source;
import net.zfp.entity.Status;
import net.zfp.entity.User;
import net.zfp.entity.UserRole;
import net.zfp.entity.account.AccountGroup;
import net.zfp.entity.account.AccountCampaign;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.campaign.CampaignGroupProgress;
import net.zfp.entity.campaign.CampaignProgress;
import net.zfp.entity.community.Community;
import net.zfp.entity.driving.DrivingHour;
import net.zfp.entity.electricity.ElectricityDataMinute;
import net.zfp.entity.electricity.ElectricityDataMonth;
import net.zfp.entity.group.Groups;
import net.zfp.entity.group.GroupsType;
import net.zfp.entity.personalmotion.PersonalMotion;
import net.zfp.notify.NotificationService;
import net.zfp.notify.impl.NotificationServiceImpl;
import net.zfp.service.GroupService;
import net.zfp.util.AppConstants;
import net.zfp.util.DateParserUtil;
import net.zfp.util.ImageUtil;
import net.zfp.util.PasswordEncoderUtil;
import net.zfp.util.TextUtil;
import net.zfp.view.ContactView;
import net.zfp.view.GroupDetailView;
import net.zfp.view.GroupView;
import net.zfp.view.GroupViews;
import net.zfp.view.LeaderboardView;
import net.zfp.view.RankView;
import net.zfp.view.ResultView;
import net.zfp.view.UserView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class GroupServiceImpl implements GroupService {

	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<Groups> groupsDao;
	@Resource
	private EntityDao<DrivingHour> drivingDao;
	@Resource
	private EntityDao<Campaign> campaignDao;
	@Resource
	private EntityDao<Account_Authority> accountAuthorityDao;
	@Resource
	private EntityDao<AccountGroup> accountGroupDao;
	@Resource
	private EntityDao<ElectricityDataMinute> electricityDao;
	@Resource
	private EntityDao<Status> statusDao;
	@Resource
	private EntityDao<AccountCampaign> accountCampaignDao;
	@Resource
	private EntityDao<PersonalMotion> personalMotionDao;
	@Inject
    private NotificationService notificationService;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setAccountGroupStatus(Long accountGroupId, String statusCode){
		ResultView rv = new ResultView();
		AccountGroup ag = accountGroupDao.findById(accountGroupId);
		Status toThisStatus = statusDao.getStatusByCode(statusCode);
		
		if (ag != null){
			//Get inviter's all accountGroup 
			List<AccountGroup> inviterAGs = accountGroupDao.getAccountGroups(ag.getGroups().getAccountId(), ag.getUser().getId());
			
			if (inviterAGs != null && inviterAGs.size() > 0){
				for (AccountGroup inviterAG : inviterAGs){
					inviterAG.setStatus(toThisStatus);
					accountGroupDao.save(inviterAG, true);
				}
			}
			
			//If statuscode is denied
			if (!statusCode.equals(AppConstants.STATUS_CODE_FRIENDS_DECLINED)){
				//Get invitee's all accountGroup 
				List<AccountGroup> inviteeAGs = accountGroupDao.getAccountGroups(ag.getUser().getId(), ag.getGroups().getAccountId());
				
				if (inviteeAGs != null && inviteeAGs.size() > 0){
					for (AccountGroup inviteeAG : inviteeAGs){
						inviteeAG.setStatus(toThisStatus);
						accountGroupDao.save(inviteeAG, true);
					}
				}
			}
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<ContactView> getMembersInGroup(Long memberId, Long groupId){

		List<ContactView> cvs = new ArrayList<ContactView>();
		//Get member in the group
		List<AccountGroup> groupUsers = groupsDao.getNumberOfMembers(groupId);
		if (groupUsers != null && groupUsers.size() > 0){
			for (AccountGroup ag : groupUsers){
				ContactView cv = new ContactView();
				if (ag.getUser().getFirstName() != null && ag.getUser().getLastName() != null){
					cv.setFirstName(TextUtil.parseString(ag.getUser().getFirstName()));
					cv.setLastName(TextUtil.parseString(ag.getUser().getLastName()));
				}else{
					cv.setFirstName(TextUtil.parseString(ag.getUser().getEmail()));
					cv.setLastName("");
				}
				
				if (ag.getStatus() != null){
					cv.setStatusCode(ag.getStatus().getCode());
					cv.setStatus(ag.getStatus().getName());
				}
				else cv.setStatus("");
					
				//Images...?
				cv.setImageUrl(AppConstants.APACHE_IMAGE_LINK + "/images/rewards/upload.png");
				
				String identifier = "";
    			
    			//Bit mask the people's id
    			if (ag.getUser().getUid() == null || ag.getUser().getUid().equals("")){
    				identifier = PasswordEncoderUtil.encodePassword(ag.getUser().getId()+"", AppConstants.MASK_SALT);
    				
    				ag.getUser().setUid(identifier);
    				userDao.save(ag.getUser(), true);
    				
    				
    			}else{
    				identifier = ag.getUser().getUid();
    			}
    			
				cv.setUid(identifier);
				cv.setEmail(TextUtil.parseString(ag.getUser().getEmail()));
				cv.setAccountGroupId(ag.getId());
				
				cvs.add(cv);
				
			}
		}
		return cvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<UserView> getMyGroupUsers(String domainName, Long accountId){
		List<UserView> uvs = new ArrayList<UserView>();
		
		System.out.println("Get My Group Users");
		//Groups based on accountId
		List<Groups> groups = groupsDao.getGroupsByAccount(accountId);
		List<User> users = new ArrayList<User>();
		
		if (groups != null && groups.size() > 0){
			for (Groups group : groups){
				//Get Users for each groups
				List<AccountGroup> groupUsers = groupsDao.getNumberOfMembers(group.getId());
				if (groupUsers != null && groupUsers.size() > 0){
					for (AccountGroup groupUser : groupUsers){
						if (!users.contains(groupUser.getUser())){
							users.add(groupUser.getUser());
						}
					}
				}
			}
		}
		
		System.out.println("Users Size :: " + users.size());
		if (users.size() > 0){
			//Sort by last name ASC
			Comparator<User> valueComparator = new Comparator<User>(){
				public int compare(User user1, User user2){
					return user1.getLastName().compareTo(user2.getLastName());
				}
			};
			
			Collections.sort(users, valueComparator);
			
			for (User user : users){
				UserView uv = new UserView(user);
				
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
    			
				uvs.add(uv);
			}
		}
		
		
		return uvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<RankView> getCampaignGroupFriends(Long memberId, Long campaignId, Integer iteration){
		List<RankView> rvs = new ArrayList<RankView>();
		Groups group = groupsDao.getGroupByAccountIdType(memberId, AppConstants.GROUPSTYPE_DEFAULT);
		
		Campaign campaign = campaignDao.findById(campaignId);
		
		User user = userDao.findById(memberId);
		RankView myRank = new RankView();
		
		if (campaign.getCampaignType().getType().equals(AppConstants.CAMPAIGN_TYPE_GROUP)){
			
			/*
			 * Get My current group
			 */
			List<Groups> myGroups = groupsDao.getGroups(memberId);
			if (!myGroups.isEmpty()){
				List<Groups> myCampaignGroups = groupsDao.getGroupsWithCampaign(myGroups, campaign.getId());
				if (!myCampaignGroups.isEmpty()){
					Groups targettedCampaignGroup = myCampaignGroups.get(0);
					myRank.setFirstName(targettedCampaignGroup.getName());
					myRank.setImageUrl("/portal-core/images/rewards/upload.png");
					
					CampaignGroupProgress result = campaignDao.getCampaignGroupProgress(campaign.getId(), targettedCampaignGroup.getId(), iteration);
					
					if (result == null || result.getProgressValue() == 0.0){
						myRank.setValue(0.0);
						myRank.setStatusCode(3);
						myRank.setStatus("RANK n/a");
					}else{
						myRank.setValue(result.getProgressValue());
						myRank.setStatusCode(1);
						myRank.setStatus("RANK");
					}
					
					/*
					 * Get my rank to first
					 */
					rvs.add(myRank);
					
					/*
					 * Compared with my other group...
					 */
					for (Groups myGroup : myGroups){
						/*
						 * Skip if it finds a targetted campaign group
						 */
						if (myGroup.equals(targettedCampaignGroup)) continue;
						
						RankView peerRank = new RankView();
						peerRank.setFirstName(myGroup.getName());
						peerRank.setImageUrl("/portal-core/images/rewards/upload.png");
						
						peerRank.setStatusCode(2);
						peerRank.setValue(-1.0);
						peerRank.setStatus("RANK n/a");
						
						CampaignGroupProgress friendProgress = userDao.getCampaignGroupProgress(campaignId, myGroup.getId(), iteration);
						
						if (friendProgress != null){
							if (friendProgress.getProgressValue() == 0){
								peerRank.setValue(friendProgress.getProgressValue());
								peerRank.setStatusCode(3);
								peerRank.setStatus("RANK n/a");
							}else{
								peerRank.setValue(friendProgress.getProgressValue());
								peerRank.setStatusCode(1);
								peerRank.setStatus("RANK");
							}
						}
						
						System.out.println("Rank adding..." + peerRank.getFirstName());
						rvs.add(peerRank);
					}
				}
			}
		}else{
			
			myRank.setFirstName(user.getFirstName());
			myRank.setLastName(user.getLastName());
			myRank.setImageUrl("/portal-core/images/rewards/upload.png");
			
			CampaignProgress result = userDao.getCampaignProgress(campaignId, memberId, iteration);
			
			if (result == null || result.getProgressValue() == 0.0){
				myRank.setValue(0.0);
				myRank.setStatusCode(3);
				myRank.setStatus("RANK n/a");
			}else{
				myRank.setValue(result.getProgressValue());
				myRank.setStatusCode(1);
				myRank.setStatus("RANK");
			}
			
			
			rvs.add(myRank);
			
			if (group != null){
				List<AccountGroup> ags = groupsDao.getNumberOfMembers(group.getId());
				if (ags != null && ags.size() > 0){
					RankView peerRank = null;
					for(AccountGroup ag : ags){
						//Each user gets ranks
						peerRank = new RankView();
						peerRank.setFirstName(ag.getUser().getFirstName());
						peerRank.setLastName(ag.getUser().getLastName());
						peerRank.setImageUrl("/portal-core/images/rewards/upload.png");
						
						peerRank.setStatusCode(2);
						peerRank.setValue(-1.0);
						
						if (ag.getStatus() != null && ag.getStatus().equals(AppConstants.STATUS_CODE_PENDING_APPROVAL)){
							//Hasn't approved me yet.
							Account_Authority aa = accountAuthorityDao.getAccountAuthority(ag.getUser().getId());
							if (aa != null){
								if (aa.getUserRole().getAuthority().equals(AppConstants.ROLE_GROUP_GUEST)){
									peerRank.setStatus("Awaiting sign up");
								}else {
									peerRank.setStatus("Not participated");
								}
							}
						}else{
							CampaignProgress friendProgress = userDao.getCampaignProgress(campaignId, ag.getUser().getId(), iteration);
							
							if (friendProgress != null){
								if (friendProgress.getProgressValue() == 0){
									peerRank.setValue(friendProgress.getProgressValue());
									peerRank.setStatusCode(3);
									peerRank.setStatus("RANK n/a");
								}else{
									peerRank.setValue(friendProgress.getProgressValue());
									peerRank.setStatusCode(1);
									peerRank.setStatus("RANK");
								}
							}else{
							
								Account_Authority aa = accountAuthorityDao.getAccountAuthority(ag.getUser().getId());
								if (aa != null){
									if (aa.getUserRole().getAuthority().equals(AppConstants.ROLE_GROUP_GUEST)){
										peerRank.setStatus("Awaiting sign up");
									}else {
										peerRank.setStatus("Not participated");
									}
								}
								
							}
						}
						
						rvs.add(peerRank);
						
					}
				}
			}
		}
		
		//ASC
		Comparator<RankView> valueComparator = new Comparator<RankView>(){
			public int compare(RankView rank1, RankView rank2){
				if (rank1.getValue() < rank2.getValue()) return 1;
				if (rank1.getValue() > rank2.getValue()) return -1;
				return 0;
			}
		};
		
		Collections.sort(rvs, valueComparator);
		
		double prev = Double.MAX_VALUE;
		int rank = 0;
		int count = 1;
		int totalCount = 0;
		
		for (int i=0; i < rvs.size(); i++){
			System.out.println("NAME : " + rvs.get(i).getFirstName() + rvs.get(i).getLastName());
			
			if (rvs.get(i).getStatusCode() == 1){
				if (prev == rvs.get(i).getValue()){
					count++;
				}else{
					prev = rvs.get(i).getValue();
					rank += count;
					count = 1;
				}
				
				totalCount++;
				rvs.get(i).setRank(rank);
			}
		}
		
		for (int i = 0; i < rvs.size(); i++){
			RankView rv = rvs.get(i);
			if (rv.getStatusCode() == 1){
				Double division = (double)(int)rv.getRank() / totalCount;
				if (totalCount >= 3){
					
					if (division > 2.0/3.0){
						rv.setDivision(3);
					}else if (division > 1.0/3.0){
						rv.setDivision(2);
					}else{
						rv.setDivision(1);
					}
				}else if (totalCount >= 2){
					if ( division > 1.0 / 2.0){
						rv.setDivision(2);
					}else{
						rv.setDivision(1);
					}
				}else {
					rv.setDivision(1);
				}
			}else if (rv.getStatusCode() == 3){
				rv.setDivision(3);
				
			}else{
				rv.setDivision(-1);
			}
		}
		
		try{
			RankView temp = rvs.get(rvs.indexOf(myRank));
			 myRank.setRank(temp.getRank());
			 myRank.setDivision(temp.getDivision());
			 rvs.remove(myRank);
		}catch(Exception e){
			
		}
		 rvs.add(myRank);
		 
		 System.out.println("Size " + rvs.size());
		return rvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<RankView> getGroupFriends(Long accountId, String modeInString, Integer period){
		List<RankView> rvs = new ArrayList<RankView>();
		Integer mode = 1;
		if (modeInString.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_WALKING)) mode = 2;
		else if (modeInString.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY)) mode = 1;
		else if (modeInString.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_RUNNING)) mode = 3;
		else if (modeInString.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_CYCLING)) mode = 4;
		else if (modeInString.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_DRIVING)) mode = 5;
		
		//Check default group for this account
		Groups group = groupsDao.getGroupByAccountIdType(accountId, AppConstants.GROUPSTYPE_DEFAULT);
		if (group != null){
			
		}else{
			//If this account doesn't have default group then add to Group
			group = new Groups();
			group.setAccountId(accountId);
			GroupsType gt = groupsDao.getGroupsTypeByName(AppConstants.GROUPSTYPE_DEFAULT);
			group.setGroupsType(gt);
			User user = userDao.findById(accountId);
			group.setCommunityId(user.getDefaultCommunity());
			group.setImageUrl("/portal-core/images/rewards/groupsampleimage.png");
			group.setName(user.getFirstName() + " " + user.getLastName() + "'s default group");
			
			groupsDao.save(group);
		}
			
		//rvs = rankingFriends(mode, accountId, group.getId(), period, modeInString);
		
		
		return rvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<RankView> getFriendsRanked(Long accountId, String sourceTypeCode, String periodType, Integer year, Integer month, Integer day){
		List<RankView> rvs = new ArrayList<RankView>();
		
		Integer mode = 1;
		if (sourceTypeCode.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_WALKING)) mode = 2;
		else if (sourceTypeCode.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY)) mode = 1;
		else if (sourceTypeCode.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_RUNNING)) mode = 3;
		else if (sourceTypeCode.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_CYCLING)) mode = 4;
		else if (sourceTypeCode.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_DRIVING)) mode = 5;
		
		//Check default group for this account
		Groups group = groupsDao.getGroupByAccountIdType(accountId, AppConstants.GROUPSTYPE_DEFAULT);
		if (group != null){
			
		}else{
			//If this account doesn't have default group then add to Group
			group = new Groups();
			group.setAccountId(accountId);
			GroupsType gt = groupsDao.getGroupsTypeByName(AppConstants.GROUPSTYPE_DEFAULT);
			group.setGroupsType(gt);
			User user = userDao.findById(accountId);
			group.setCommunityId(user.getDefaultCommunity());
			group.setImageUrl("/portal-core/images/rewards/groupsampleimage.png");
			group.setName(user.getFirstName() + " " + user.getLastName() + "'s default group");
			
			groupsDao.save(group);
		}
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		if (day != null) calendar.set(Calendar.DAY_OF_MONTH, day);
		
		Date requestDate = calendar.getTime();
		rvs = rankingFriends(mode, accountId, group.getId(), periodType, sourceTypeCode, requestDate);
		
		return rvs;
	}
	
	private Double getRankValue(Integer filter, String period, Long sourceId, String sourceTypeCode, Date requestDate){
		Double totalValue = null;
		Date endDate = new Date();
		Date startDate = null;
		
		/*
		 * filter = 1 electricity consumption
		 * filter = 2 distance
		 * filter = 3 fuel efficiency
		 */
		
		if (filter == 1){
			if (period.equals(AppConstants.PERIOD_TYPE_DAILY)){
				startDate = DateParserUtil.getBeginningOfDay(requestDate);
				endDate = DateParserUtil.getEndOfDay(startDate);
				
				totalValue = electricityDao.findSumElectricity(AppConstants.TABLE_ELECTRICITY_DATA_DAY, sourceId, startDate, endDate);
				
			}else if (period.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
				startDate = DateParserUtil.getBeginningOfMonth(requestDate);
				endDate = DateParserUtil.getEndOfMonth(startDate);
				
				totalValue = electricityDao.findSumElectricity(AppConstants.TABLE_ELECTRICITY_DATA_MONTH, sourceId, startDate, endDate);
				
			}
		}else if (filter == 2){
			String database = "";
			if (period.equals(AppConstants.PERIOD_TYPE_DAILY)){
				if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_WALKING)) database = AppConstants.TABLE_WALKING_HOUR;
				else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_RUNNING)) database = AppConstants.TABLE_RUNNING_HOUR;
				else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)) database= AppConstants.TABLE_CYCLING_HOUR;
				
				startDate = DateParserUtil.getBeginningOfDay(requestDate);
				endDate = DateParserUtil.getEndOfDay(startDate);
				
				totalValue = personalMotionDao.getWalkingDistanceByInterval(database, sourceId, startDate, endDate);
				
			}else if (period.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
				if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_WALKING)) database = AppConstants.TABLE_WALKING_DAY;
				else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_RUNNING)) database = AppConstants.TABLE_RUNNING_DAY;
				else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)) database= AppConstants.TABLE_CYCLING_DAY;
				
				startDate = DateParserUtil.getBeginningOfMonth(requestDate);
				endDate = DateParserUtil.getEndOfMonth(startDate);
				
				totalValue = personalMotionDao.getWalkingDistanceByInterval(database, sourceId, startDate, endDate);
			}
			
		}else if (filter == 3){
			if (period.equals(AppConstants.PERIOD_TYPE_DAILY)){
				startDate = DateParserUtil.getBeginningOfDay(requestDate);
				endDate = DateParserUtil.getEndOfDay(startDate);
				
				totalValue = drivingDao.getDrivingFuelEfficiencyByInterval(AppConstants.TABLE_DRIVING_HOUR, sourceId, startDate, endDate);
				
			}else if (period.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
				startDate = DateParserUtil.getBeginningOfMonth(requestDate);
				endDate = DateParserUtil.getEndOfMonth(startDate);
				
				totalValue = drivingDao.getDrivingFuelEfficiencyByInterval(AppConstants.TABLE_DRIVING_DAY, sourceId, startDate, endDate);
				
			}	
			
		}
		
		return totalValue;
		
	}
	private List<RankView> rankingFriends(Integer mode, Long accountId, Long groupId, String period, String sourceTypeCode, Date requestDate){
		//for now... let's ignore mode just for walking
		List<RankView> rvs = new ArrayList<RankView>();
		RankView myRank = new RankView();
		
		if (mode == 1){
			List<Source> sources = userDao.findSourceByAccountAndType(accountId, sourceTypeCode);
			Long sourceId = null;
			for (Source source : sources){
				if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_HOME) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
					sourceId = source.getId();
					break;
				}
			}
			
			System.out.println("SourceID :: " + sourceId);
			if (sourceId != null){
				//get my data using accountId
				
				myRank.setFirstName("ME");
				myRank.setLastName("");
				myRank.setImageUrl("/portal-core/images/rewards/upload.png");
				
				Double totalConsumption = getRankValue(1, period, sourceId, sourceTypeCode, requestDate);
				
				if (totalConsumption == null || totalConsumption == 0){
					myRank.setValue(0.0);
					myRank.setStatusCode(3);
					myRank.setStatus("RANK n/a");
				}else{
					myRank.setValue(totalConsumption);
					myRank.setStatusCode(1);
					myRank.setStatus("RANK");
				}
				
				
				rvs.add(myRank);
				
				List<AccountGroup> ags = groupsDao.getNumberOfMembers(groupId);
				if (ags != null && ags.size() > 0){
					RankView peerRank = null;
					for(AccountGroup ag : ags){
						//Each user gets ranks
						peerRank = new RankView();
						peerRank.setFirstName(ag.getUser().getFirstName());
						peerRank.setLastName(ag.getUser().getLastName());
						peerRank.setImageUrl("/portal-core/images/rewards/upload.png");
						
						peerRank.setStatusCode(2);
						peerRank.setValue(-1.0);
						
						if (ag.getStatus() == null || ag.getStatus().getCode().equals(AppConstants.STATUS_CODE_APPROVED)){
							sources = userDao.findSourceByAccountAndType(ag.getUser().getId(), sourceTypeCode);
							sourceId = null;
							for (Source source : sources){
								if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_HOME) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
									sourceId = source.getId();
									break;
								}
							}
							
							if (sourceId != null){
								totalConsumption = getRankValue(1, period, sourceId, sourceTypeCode, requestDate);
								
								if (totalConsumption == null || totalConsumption == 0){
									peerRank.setValue(0.0);
									peerRank.setStatusCode(3);
									peerRank.setStatus("RANK n/a");
								}else{
									peerRank.setValue(totalConsumption);
									peerRank.setStatusCode(1);
									peerRank.setStatus("RANK");
								}
								
							}else{
							
								
								Account_Authority aa = accountAuthorityDao.getAccountAuthority(ag.getUser().getId());
								if (aa != null){
									if (aa.getUserRole().getAuthority().equals(AppConstants.ROLE_GROUP_GUEST)){
										peerRank.setStatus("Awaiting sign up");
									}else {
										peerRank.setStatus("Not connected");
									}
								}
								
							}
						}else{
							//Hasn't approved me yet.
							Account_Authority aa = accountAuthorityDao.getAccountAuthority(ag.getUser().getId());
							if (aa != null){
								if (aa.getUserRole().getAuthority().equals(AppConstants.ROLE_GROUP_GUEST)){
									peerRank.setStatus("Awaiting sign up");
								}else {
									peerRank.setStatus("Not connected");
								}
							}
						}
						
						
						
						rvs.add(peerRank);
						
					}
				}
			}
			
		}else if (mode == 2 || mode == 3 || mode == 4){
			
			List<Source> sources = userDao.findSourceByAccountAndType(accountId, sourceTypeCode);
			Long sourceId = null;
			for (Source source : sources){
				
				if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_PERSONAL) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
					sourceId = source.getId();
					break;
				}
			}
			
			if (sourceId != null){
				//get my data using accountId
				System.out.println("ME !!");
				myRank.setFirstName("ME");
				myRank.setLastName("");
				myRank.setImageUrl("/portal-core/images/rewards/upload.png");
				
				
				Double totalDistanceCover = getRankValue(2, period, sourceId, sourceTypeCode, requestDate);
				if (totalDistanceCover == null || totalDistanceCover == 0){
					myRank.setValue(0.0);
					myRank.setStatusCode(3);
					myRank.setStatus("RANK n/a");
				}else{
					myRank.setValue(totalDistanceCover);
					myRank.setStatusCode(1);
					myRank.setStatus("RANK");
				}
				
				
				/*
				myRank.setStatusCode(2);
				myRank.setValue(-1.0);
				myRank.setStatus("Activity Status");
				*/
				rvs.add(myRank);
				//get friends data using group account
				//get group members
				List<AccountGroup> ags = groupsDao.getNumberOfMembers(groupId);
				if (ags != null && ags.size() > 0){
					RankView peerRank = null;
					for(AccountGroup ag : ags){
						//Each user gets ranks
						peerRank = new RankView();
						peerRank.setFirstName(ag.getUser().getFirstName());
						peerRank.setLastName(ag.getUser().getLastName());
						peerRank.setImageUrl("/portal-core/images/rewards/upload.png");
						peerRank.setStatusCode(2);
						peerRank.setValue(-1.0);
						
						if (ag.getStatus() == null || ag.getStatus().getCode().equals(AppConstants.STATUS_CODE_APPROVED)){
							sources = userDao.findSourceByAccountAndType(ag.getUser().getId(), sourceTypeCode);
							sourceId = null;
							for (Source source : sources){
								
								if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_PERSONAL) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
									sourceId = source.getId();
									break;
								}
							}
							
							if (sourceId != null){
								
								totalDistanceCover = getRankValue(2, period, sourceId, sourceTypeCode, requestDate);
								if (totalDistanceCover == null || totalDistanceCover == 0){
									peerRank.setValue(0.0);
									peerRank.setStatusCode(3);
									peerRank.setStatus("RANK n/a");
								}else{
									peerRank.setValue(totalDistanceCover);
									peerRank.setStatusCode(1);
									peerRank.setStatus("RANK");
								}
								
							}else{
							
								Account_Authority aa = accountAuthorityDao.getAccountAuthority(ag.getUser().getId());
								if (aa != null){
									if (aa.getUserRole().getAuthority().equals(AppConstants.ROLE_GROUP_GUEST)){
										peerRank.setStatus("Awaiting sign up");
									}else {
										peerRank.setStatus("Not connected");
									}
								}
							}
						}else{
							//Hasn't approved me yet.
							Account_Authority aa = accountAuthorityDao.getAccountAuthority(ag.getUser().getId());
							if (aa != null){
								if (aa.getUserRole().getAuthority().equals(AppConstants.ROLE_GROUP_GUEST)){
									peerRank.setStatus("Awaiting sign up");
								}else {
									peerRank.setStatus("Not connected");
								}
							}
						}
						
						
						/*
						peerRank.setStatusCode(2);
						peerRank.setValue(-1.0);
						peerRank.setStatus("Activity Status");
						*/
						
						rvs.add(peerRank);
						
					}
				}
				
				
			}
			
			
		}else if (mode ==5){
			List<Source> sources = userDao.findSourceByAccountAndType(accountId, sourceTypeCode);
			Long sourceId = null;
			for (Source source : sources){
				
				if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_CAR) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
					sourceId = source.getId();
					break;
				}
			}
			
			if (sourceId != null){
				//get my data using accountId
				myRank.setFirstName("ME");
				myRank.setLastName("");
				myRank.setImageUrl("/portal-core/images/rewards/upload.png");
				
				
				Double totalDistanceCover = getRankValue(3, period, sourceId, sourceTypeCode, requestDate);
				if (totalDistanceCover == null || totalDistanceCover == 0){
					myRank.setValue(Double.MAX_VALUE);
					myRank.setStatusCode(3);
					myRank.setStatus("RANK n/a");
				}else{
					myRank.setValue(totalDistanceCover);
					myRank.setStatusCode(1);
					myRank.setStatus("RANK");
				}
				
				
				/*
				myRank.setStatusCode(2);
				myRank.setValue(-1.0);
				myRank.setStatus("Activity Status");
				*/
				rvs.add(myRank);
				//get friends data using group account
				//get group members
				List<AccountGroup> ags = groupsDao.getNumberOfMembers(groupId);
				if (ags != null && ags.size() > 0){
					RankView peerRank = null;
					for(AccountGroup ag : ags){
						//Each user gets ranks
						peerRank = new RankView();
						peerRank.setFirstName(ag.getUser().getFirstName());
						peerRank.setLastName(ag.getUser().getLastName());
						peerRank.setImageUrl("/portal-core/images/rewards/upload.png");
						peerRank.setStatusCode(2);
						peerRank.setValue(Double.MAX_VALUE);
						
						if (ag.getStatus() == null || ag.getStatus().getCode().equals(AppConstants.STATUS_CODE_APPROVED)){
							sources = userDao.findSourceByAccountAndType(ag.getUser().getId(), sourceTypeCode);
							sourceId = null;
							for (Source source : sources){
								
								if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_CAR) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
									sourceId = source.getId();
									break;
								}
							}
							
							if (sourceId != null){
								
								totalDistanceCover = getRankValue(3, period, sourceId, sourceTypeCode, requestDate);
								if (totalDistanceCover == null || totalDistanceCover == 0){
									peerRank.setValue((100000000.0));
									peerRank.setStatusCode(3);
									peerRank.setStatus("RANK n/a");
								}else{
									peerRank.setValue(totalDistanceCover);
									peerRank.setStatusCode(1);
									peerRank.setStatus("RANK");
								}
								
							}else{
							
								Account_Authority aa = accountAuthorityDao.getAccountAuthority(ag.getUser().getId());
								if (aa != null){
									if (aa.getUserRole().getAuthority().equals(AppConstants.ROLE_GROUP_GUEST)){
										peerRank.setStatus("Awaiting sign up");
									}else {
										peerRank.setStatus("Not connected");
									}
								}
							}
							
							
						}else{
							//Hasn't approved me yet.
							Account_Authority aa = accountAuthorityDao.getAccountAuthority(ag.getUser().getId());
							if (aa != null){
								if (aa.getUserRole().getAuthority().equals(AppConstants.ROLE_GROUP_GUEST)){
									peerRank.setStatus("Awaiting sign up");
								}else {
									peerRank.setStatus("Not connected");
								}
							}
							
						}
						
						
						/*
						peerRank.setStatusCode(2);
						peerRank.setValue(-1.0);
						peerRank.setStatus("Activity Status");
						*/
						
						rvs.add(peerRank);
						
					}
				}
				
				
			}
		}
		
		System.out.println("Size :: " + rvs.size());
		
		if (mode == 5){
			//ASC
			Comparator<RankView> valueComparator = new Comparator<RankView>(){
				public int compare(RankView rank1, RankView rank2){
					if (rank1.getValue() > rank2.getValue()) return 1;
					if (rank1.getValue() < rank2.getValue()) return -1;
					return 0;
				}
			};
			
			Collections.sort(rvs, valueComparator);
			
		}else{
			//ASC
			Comparator<RankView> valueComparator = new Comparator<RankView>(){
				public int compare(RankView rank1, RankView rank2){
					if (rank1.getValue() < rank2.getValue()) return 1;
					if (rank1.getValue() > rank2.getValue()) return -1;
					return 0;
				}
			};
			
			Collections.sort(rvs, valueComparator);
		}
		
		
		double prev = Double.MAX_VALUE;
		int rank = 0;
		int count = 1;
		int totalCount = 0;
		
		for (int i=0; i < rvs.size(); i++){
			if (rvs.get(i).getStatusCode() == 1){
				if (prev == rvs.get(i).getValue()){
					count++;
				}else{
					prev = rvs.get(i).getValue();
					rank += count;
					count = 1;
				}
				
				totalCount++;
				rvs.get(i).setRank(rank);
			}
		}
		
		for (int i = 0; i < rvs.size(); i++){
			RankView rv = rvs.get(i);
			if (rv.getStatusCode() == 1){
				Double division = (double)(int)rv.getRank() / totalCount ;
				if (totalCount >= 3){
					
					if (division > 2.0/3.0){
						rv.setDivision(3);
					}else if (division > 1.0/3.0){
						rv.setDivision(2);
					}else{
						rv.setDivision(1);
					}
				}else if (totalCount >= 2){
					if ( division > 1.0 / 2.0){
						rv.setDivision(2);
					}else{
						rv.setDivision(1);
					}
				}else {
					rv.setDivision(1);
				}
			}else if (rv.getStatusCode() == 3){
				rv.setDivision(3);
				
			}else{
				rv.setDivision(-1);
			}
		}
		
		try{
			RankView temp = rvs.get(rvs.indexOf(myRank));
			 myRank.setRank(temp.getRank());
			 myRank.setDivision(temp.getDivision());
			 rvs.remove(myRank);
		}catch(Exception e){
			
		}
		 rvs.add(myRank);
		 
		return rvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView removeGroupMember(Long groupId, String memberIds){
		
		//System.out.println("Group Member :: " + new Date());
		ResultView rv = new ResultView();
		
		//Parse memberIds;
		String[] members = memberIds.split(";");
		
		if (members.length > 0){
			for (String member : members){
				User user = userDao.getUserByUID(member);
				
				Long memberId = user.getId();
				
				//System.out.println("Member Id :: " + memberId + " :: Group Id :: " + groupId);
				
				//If Group is Default... then check all people inside 
				Groups groups = groupsDao.findById(groupId);
				
				if ( groups.getGroupsType().getName().equals(AppConstants.GROUPSTYPE_DEFAULT)){
					//Here needs to delete this member to every inviter's group
					//Getll All AccountGroup by inviter
					List<AccountGroup> ags= accountGroupDao.getAccountGroups(groups.getAccountId(), memberId);
					
					if (ags != null && ags.size() > 0){
						for (AccountGroup ag : ags){
							accountGroupDao.deleteAccountGroup(ag.getId());
						}
					}
				}else{
					//Get Account Group
					AccountGroup ag = accountGroupDao.getAccountGroup(memberId, groupId);
					if (ag != null){
						accountGroupDao.deleteAccountGroup(ag.getId());
					}
				}
				
			}
		}
		
		return rv;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView removeGroup(Long groupId){
		ResultView rv = new ResultView();
		//First Check if this account exists
		
		try{
			//Check in account program
			List<AccountCampaign> aps = accountCampaignDao.getAccountCampaignsByGroupId(groupId);
			if (aps != null && aps.size() > 0){
				for (AccountCampaign ap : aps){
					
					ap.setGroups(null);
					accountCampaignDao.save(ap, true);
				}
			}
			
			accountGroupDao.deleteAccountGroupByGroupId(groupId);
			
			//finally delete groups 
			groupsDao.deleteGroup(groupId);
			
			rv.setResultCode(AppConstants.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			rv.setResultCode(AppConstants.FAILURE);
		}
		return rv;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView createGroupAndLinkCampaign(String domainName, String accountEmail, Long campaignId, String emails){
		ResultView rv = new ResultView();
		//Get Community Id
		Community community = communityDao.getCommunity(domainName);
		Long communityId = community.getId();
		
		//First Check if this account exists
		User user = userDao.findActiveUserByEmail(accountEmail);
		
		//Check if account program exists
		AccountCampaign ap = accountCampaignDao.getAccountCampaign(user.getId(), campaignId);
		Groups group = null;
		if (ap != null){
			if (ap.getGroups() != null && ap.getGroups().getGroupsType().getName().equals(AppConstants.GROUPSTYPE_CAMPAIGN)){
				group = ap.getGroups();
			}else{
				//Create a group
				group = new Groups();
				GroupsType gt = groupsDao.getGroupsTypeByName(AppConstants.GROUPSTYPE_CAMPAIGN);
				group.setGroupsType(gt);
				group.setAccountId(user.getId());
				group.setCommunityId(communityId);
				group.setCreated(new Date());
				group.setLastModified(new Date());
				Campaign campaign = campaignDao.getCampaign(campaignId);
				group.setName(campaign.getName() + " Sponsors");
				group.setImageUrl("/portal-core/images/rewards/groupsampleimage.png");
				
				groupsDao.save(group);
				
				group = groupsDao.getGroupByName(user.getId(), communityId, campaign.getName() + " Sponsors");
				
				//System.out.println("!!!! NEW GROUP ID :: " + group.getId());
				ap.setGroups(group);
				
				//update account program
				accountCampaignDao.save(ap, true);
				
			}
			
			//Parse emails separated by ;
			String[] emailAccounts = emails.split(";");
					
			savingUserToGroup(emailAccounts, communityId, group.getId());
		}
		
		
		
		
		return rv;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView saveGroup(Long groupId, String groupName, String groupDescription, String imageUrl, String domainName, Long memberId){
		ResultView rv = new ResultView();
		if (groupId == -1){
			//create a group here
			//Get Community Id
			Community community = communityDao.getCommunity(domainName);
			Long communityId = community.getId();
			//First Check if this account exists
			User user = userDao.findById(memberId);
			
			Groups checkingGroup = groupsDao.getGroup(memberId, null, groupName);
			if ( checkingGroup == null){
				Groups group = new Groups();
				group.setName(groupName);
				group.setDescription(groupDescription);
				group.setImageUrl(ImageUtil.parseApcheImageUrl(imageUrl));
				group.setAccountId(user.getId());
				group.setCommunityId(communityId);
				GroupsType gt = groupsDao.getGroupsTypeByName("GENERIC");
				group.setGroupsType(gt);
				
				groupsDao.save(group);
				
				Groups groups = groupsDao.getGroupByName(user.getId(), communityId, groupName);
				rv.setResultCode((int)(long)groups.getId());
				
				/*
				 * Add this member to this account group
				 */
				AccountGroup ag = new AccountGroup();
				ag.setGroups(groups);
				ag.setUser(user);
				ag.setStatus(statusDao.getStatusByCode(AppConstants.STATUS_CODE_APPROVED));
				accountGroupDao.save(ag);
			}else{
				rv.fill(AppConstants.FAILURE, "Group name already exists.");
			}
			
		}else{
			//fetch group
			Groups checkingGroup = groupsDao.getGroup(memberId, groupId, groupName);
			if (checkingGroup == null){
				Groups group = groupsDao.getGroup(groupId);
				group.setName(groupName);
				group.setDescription(groupDescription);
				group.setImageUrl(ImageUtil.parseApcheImageUrl(imageUrl));
				
				groupsDao.save(group, true);
				rv.setResultCode((int)(long)group.getId());
			}else{
				rv.fill(AppConstants.FAILURE, "Group name already exists.");
			}
		}
		
		return rv;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView addGroupMember(String domainName, Long groupId, String memberIds){
		ResultView rv = new ResultView();
		//Get Community Id
		Community community = communityDao.getCommunity(domainName);
		Long communityId = community.getId();
		
		//Parse emails separated by ;
		String[] ids = memberIds.split(";");
		if (!(ids.length == 1 && ids[0].equals(""))){
			savingUserToGroup(ids, communityId, groupId);
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView saveGroupMember(String domainName, Long groupId, String memberIds){
		ResultView rv = new ResultView();
		//Get Community Id
		Community community = communityDao.getCommunity(domainName);
		
		//Parse emails separated by ;
		String[] ids = memberIds.split(";");
		
		//For each id get check
		List<AccountGroup> ags = accountGroupDao.getNumberOfMembers(groupId);
				
		if (ids.length == 1 && ids[0].equals("")){
			//Deleted everyone...
			for (AccountGroup ag : ags){
				accountGroupDao.deleteAccountGroup(ag.getId());
			}
		}else{
			List<Long> newMembers = new ArrayList<Long>();
			for (String invitee : ids){
				User member = userDao.getUserByUID(invitee);
				newMembers.add(member.getId());
			}
			
			
					
			List<Long> originals = new ArrayList<Long>();
			for (AccountGroup ag : ags){
				originals.add(ag.getUser().getId());
			}
			
			
			
			//ags has it but invitee doesn't have it then remove this ag
			for (AccountGroup ag : ags){
				if (!newMembers.contains(ag.getUser().getId())){
					accountGroupDao.deleteAccountGroup(ag.getId());
				}
			}
			
			//new has it but original doesn't ahve it then add this
			for (Long invitee : newMembers){
				if (!originals.contains(invitee)){
					//Add this user
					User user = userDao.findById(invitee);
					Status toThisStatus = null;
					
					Groups group = groupsDao.getGroup(groupId);
					
					Groups defaultGroup = groupsDao.getGroupByAccountIdType(group.getAccountId(), AppConstants.GROUPSTYPE_DEFAULT);
					AccountGroup defaultAG = accountGroupDao.getAccountGroup(user.getId(), defaultGroup.getId());
					
					//save to account groups
					AccountGroup ag = new AccountGroup();
					ag.setGroups(group);
					ag.setUser(user);
					
					if (defaultAG != null) toThisStatus = defaultAG.getStatus();
					else toThisStatus = statusDao.getStatusByCode(AppConstants.STATUS_CODE_PENDING_APPROVAL);
					
					ag.setStatus(toThisStatus);
					
					accountGroupDao.save(ag);
				}
			}
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<UserView> validateGuest(String domainName, Long accountId, String emails){
		System.out.println("Entered validate guest :: " +  new Date());
		List<UserView> memberIds = null;
		Community community = communityDao.getCommunity(domainName);
		Long communityId = community.getId();
		//Parse emails separated by ;
		String[] emailAccounts = emails.split(";");
		//get GroupId 
		Groups group = groupsDao.getGroupByAccountIdType(accountId, AppConstants.GROUPSTYPE_DEFAULT);
		if ( group != null){
			
			memberIds = savingUserToDefaultGroup(domainName, emailAccounts, null, communityId, group.getId(), false);
		}
		
		return memberIds;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<UserView> saveDefaultGroup(String domainName, Long accountId, String guestemail, String members){
		System.out.println("Entered Default Group :: " +  new Date());
		List<UserView> memberIds = null;
		Community community = communityDao.getCommunity(domainName);
		Long communityId = community.getId();
		
		String[] guestAccounts = null;
		String[] memberAccounts = null;
		if (guestemail != null && !guestemail.equals("")){
			guestAccounts = guestemail.split(";");
		}
		if (members != null && !members.equals("")){
			memberAccounts = members.split(";");
		}
		
		//get GroupId 
		Groups group = groupsDao.getGroupByAccountIdType(accountId, AppConstants.GROUPSTYPE_DEFAULT);
		if ( group != null){
			
			memberIds = savingUserToDefaultGroup(domainName, guestAccounts, memberAccounts, communityId, group.getId(), true);
		}
		
		return memberIds;
	}
	
	private void savingUserToGroup(String[] ids, Long communityId, Long groupId){
		//For each emails
		for (String id : ids){
					
			Long inviteeId = Long.parseLong(id);
					
			//First Check if this account exists
			User user = userDao.findById(inviteeId);
					
			Status toThisStatus = null;
					
			Groups group = groupsDao.getGroup(groupId);
					
			Groups defaultGroup = groupsDao.getGroupByAccountIdType(group.getAccountId(), AppConstants.GROUPSTYPE_DEFAULT);
			AccountGroup defaultAG = accountGroupDao.getAccountGroup(user.getId(), defaultGroup.getId());
					
			//save to account groups
			AccountGroup ag = new AccountGroup();
			ag.setGroups(group);
			ag.setUser(user);
					
			if (defaultAG != null) toThisStatus = defaultAG.getStatus();
			else toThisStatus = statusDao.getStatusByCode(AppConstants.STATUS_CODE_PENDING_APPROVAL);
					
			ag.setStatus(toThisStatus);
					
			accountGroupDao.save(ag);
					
		}
	}
	
	private List<UserView> savingUserToDefaultGroup(String domainName, String[] guestInfo, String[] memberInfo, Long communityId, Long groupId, Boolean inviteAll){
		//For each emails
		List<UserView> memberIds = new ArrayList<UserView>();
		
		List<User> users = new ArrayList<User>();
		Boolean isGuest = false;
		
		//For guest user, create a guest account
		if (guestInfo != null){
			for (String userInfo : guestInfo){
				
				String[] tokens = userInfo.split(",");
						
				String firstName = tokens[0];
				String lastName = tokens[1];
				String email = tokens[2];
						
						
				//First Check if this account exists
				User user = userDao.findAllUserByEmail(email);
						
				
						
				Status toThisStatus = null;
				if (user == null){
					isGuest = true;
							
					//Else create in account table, account authority with (GUEST)
					User newUser = new User();
					newUser.setEmail(email);
					newUser.setFirstName(firstName);
					newUser.setLastName(lastName);
					newUser.setDefaultCommunity(communityId);
					newUser.setEnabled(true);
					newUser.setMode(User.ACCOUNT_ZEROFOORPRINT);
					Status status = statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE);
					newUser.setStatus(status);
					userDao.save(newUser);
							
					user = userDao.findUser( communityId, email);
					
					UserView uv = new UserView();
					uv.setId(user.getId());
					memberIds.add(uv);
					
					UserRole userRole = userDao.getUserRole(AppConstants.AUTHORITY_GROUP_GUEST);
							
					Account_Authority aa = new Account_Authority();
					aa.setUser(user);
					aa.setUserRole(userRole);
							
					accountAuthorityDao.save(aa);
							
					//Need to created default group for this account
					try{
						createDefaultGroup(user.getId());
					}catch(Exception exception){
						exception.printStackTrace();
					}
							
				}else{
					//Check if this user already exited in the account group
					AccountGroup ag = accountGroupDao.getAccountGroup(user.getId(), groupId);
					if (ag != null) continue;
					if (user.getFirstName() == null) user.setFirstName(firstName);
					if (user.getLastName() == null) user.setLastName(lastName);
							
					//Check if this user is guest
					Account_Authority aa = accountAuthorityDao.getAccountAuthority(user.getId());
					
					if (aa == null || aa.getUserRole().getAuthority().equals(AppConstants.AUTHORITY_GROUP_GUEST)){
						
						//If account authority is not exists then add...
						if (aa == null){
							UserRole userRole = userDao.getUserRole(AppConstants.AUTHORITY_GROUP_GUEST);
							
							aa = new Account_Authority();
							aa.setUser(user);
							aa.setUserRole(userRole);
									
							accountAuthorityDao.save(aa);
							
						}
						UserView uv = new UserView();
						uv.setId(user.getId());
						memberIds.add(uv);
						
						isGuest= true;
					}
						
					
					//Check if it has default group if not create one
					try{
						createDefaultGroup(user.getId());
					}catch(Exception exception){
						exception.printStackTrace();
					}
				}
				
				users.add(user);
			}
		}
		
		if (memberInfo != null){
			
			System.out.println("memberINFO...");
			for (String member : memberInfo){
				System.out.println(member);
				User user = null;
				try{
					user = userDao.getUserByUID(member);
					System.out.println(user.getId());
				}catch(Exception e){
					e.printStackTrace();
					
				}
				
				if (user != null) users.add(user);
			}
		}
		
		System.out.println("How many users... " + users.size());
		
		for (User user : users){
			
			Groups group = groupsDao.getGroup(groupId);
					
			Groups defaultGroup = groupsDao.getGroupByAccountIdType(group.getAccountId(), AppConstants.GROUPSTYPE_DEFAULT);
			AccountGroup defaultAG = accountGroupDao.getAccountGroup(user.getId(), defaultGroup.getId());
			
			Status toThisStatus;
			
			//save to account groups
			AccountGroup ag = new AccountGroup();
			ag.setGroups(group);
			ag.setUser(user);
					
			if (defaultAG != null) toThisStatus = defaultAG.getStatus();
			else toThisStatus = statusDao.getStatusByCode(AppConstants.STATUS_CODE_PENDING_APPROVAL);
					
			ag.setStatus(toThisStatus);
					
			accountGroupDao.save(ag);
					
			ag = accountGroupDao.getAccountGroup(user.getId(), groupId);
					
			User inviter = userDao.findUser(group.getAccountId());
			
			System.out.println("Getting inviter...");
			
			//When you add people to generic Also add to default group
			if (!group.getGroupsType().getName().equals(AppConstants.GROUPSTYPE_DEFAULT)){
				//Get default group id
						
				//Case: Inviter added invitee in their generic group. Thus we need to add them to default group
				if (defaultAG == null){
					defaultAG = new AccountGroup();
					defaultAG.setGroups(defaultGroup);
					defaultAG.setUser(user);
					defaultAG.setStatus(toThisStatus);
							
					accountGroupDao.save(defaultAG);
								
							
							
					//If a user is in inviter's default group then don't send an email to user
					try{
						if(inviteAll || isGuest){
							notificationService = new NotificationServiceImpl();
							
							if (inviteAll) notificationService.notifyGroupJoin(user, inviter, ag.getId(), isGuest, domainName);
							else notificationService.notifyReferJoin(user, inviter, ag.getId(), domainName);
						}
						
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}else{
						
				//Case: Inviter added invitee in their default group
				try{
					if(inviteAll || isGuest){
						notificationService = new NotificationServiceImpl();
						if (inviteAll) notificationService.notifyGroupJoin(user, inviter, ag.getId(), isGuest, domainName);
						else notificationService.notifyReferJoin(user, inviter, ag.getId(), domainName);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
				
			//Invitee becomes an inviter
			Groups inviteeGroup = groupsDao.getGroupByAccountIdType(user.getId(), AppConstants.GROUPSTYPE_DEFAULT);
			AccountGroup inviteeAG = accountGroupDao.getAccountGroup(inviter.getId(), inviteeGroup.getId());
			
			if (inviteeAG == null){
				inviteeAG = new AccountGroup();
				inviteeAG.setUser(inviter);
				inviteeAG.setGroups(inviteeGroup);
				
				toThisStatus = statusDao.getStatusByCode(AppConstants.STATUS_CODE_FRIENDS_REQUESTED);
				inviteeAG.setStatus(toThisStatus);
				
				accountGroupDao.save(inviteeAG);
			}
					
		}
	
		return memberIds;
	}
	
	private void createDefaultGroup(Long accountId){
		Groups group = groupsDao.getGroupByAccountIdType(accountId, AppConstants.GROUPSTYPE_DEFAULT);
		if (group ==null){
			//If this account doesn't have default group then add to Group
			group = new Groups();
			group.setAccountId(accountId);
			GroupsType gt = groupsDao.getGroupsTypeByName(AppConstants.GROUPSTYPE_DEFAULT);
			group.setGroupsType(gt);
			User user = userDao.findById(accountId);
			group.setCommunityId(user.getDefaultCommunity());
			group.setImageUrl("/images/rewards/groupsampleimage.png");
			group.setName("My group");
			
			groupsDao.save(group);
		}
	}
	
	private void savingEmailsToGroup(String[] emailAccounts, Long communityId, Long groupId){
		//For each emails
				for (String email : emailAccounts){
					
					System.out.println(email);
					
					//First Check if this account exists
					User user = userDao.findAllUserByEmail(email);
					
					if (user == null){
						//Else create in account table, account authority with (GUEST)
						User newUser = new User();
						newUser.setEmail(email);
						newUser.setDefaultCommunity(communityId);
						newUser.setEnabled(true);
						newUser.setMode(User.ACCOUNT_ZEROFOORPRINT);
						userDao.save(newUser);
						
						user = userDao.findUser( communityId, email);
						System.out.println("Just added him! hooray :: " + user.getId());
						
						UserRole userRole = userDao.getUserRole(AppConstants.AUTHORITY_GROUP_GUEST);
						
						Account_Authority aa = new Account_Authority();
						aa.setUser(user);
						aa.setUserRole(userRole);
						
						accountAuthorityDao.save(aa);
					}else{
						//Check if this user already exited in the account gourp
						AccountGroup ag = accountGroupDao.getAccountGroup(user.getId(), groupId);
						if (ag != null) continue;
					}
					
					//save to account groups
					AccountGroup ag = new AccountGroup();
					ag.setGroups(groupsDao.getGroup(groupId));
					ag.setUser(user);
					
					accountGroupDao.save(ag);
				}
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public GroupViews getGroupList(Long memberId){
		
		GroupViews groupViews = new GroupViews();
		
		if (memberId == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			groupViews.setResult(rv);
			return groupViews;
		}
		
		User member = userDao.findById(memberId);
		if (member == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
			groupViews.setResult(rv);
			return groupViews;
		}
		
		//Get AccountId with communityId
		List<Groups> groups = groupsDao.getGroups(memberId);
		if (groups.isEmpty()){
			try{
				createDefaultGroup(memberId);
			}catch(Exception exception){
				exception.printStackTrace();
			}
			groups = groupsDao.getGroups(memberId);
		}
		
		if (!groups.isEmpty()){
			List<GroupView> gvs = new ArrayList<GroupView>();
			for (Groups group : groups){
				GroupView gv = new GroupView();
				gv.setId(group.getId());
				gv.setName(TextUtil.parseHTMLString((group.getName())));
				gv.setDescription(TextUtil.parseHTMLString(group.getDescription()));
				if (group.getGroupsType() != null && group.getGroupsType().getType() == 3) gv.setIsDefault(true);
				else gv.setIsDefault(false);
				
				/*
				 * To check if a group is created by the member
				 */
				if (group.getAccountId().equals(memberId)){
					gv.setIsAdmin(true);
				}else{
					gv.setIsAdmin(false);
				}
				
				if (group.getImageUrl() != null) gv.setImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(group.getImageUrl()));
				else gv.setImageUrl(AppConstants.APACHE_IMAGE_LINK + "/images/rewards/groupsampleimage.png");
				//Based on group Id get fetch accountGroupId
				List<AccountGroup> numMembers = groupsDao.getNumberOfMembers(group.getId());
				if (!numMembers.isEmpty()) gv.setMembers(numMembers.size());
				else gv.setMembers(0);
				
				User admin = userDao.findById(group.getAccountId());
				if (admin != null){
					UserView uv = new UserView();
					uv.setFirstName(admin.getFirstName());
					uv.setLastName(admin.getLastName());
					uv.setEmail(admin.getEmail());
					String identifier = "";
	    			
	    			//Bit mask the people's id
	    			if (admin.getUid() == null || admin.getUid().equals("")){
	    				identifier = PasswordEncoderUtil.encodePassword(admin.getId()+"", AppConstants.MASK_SALT);
	    				
	    				admin.setUid(identifier);
	    				userDao.save(admin, true);
	    				
	    				
	    			}else{
	    				identifier = admin.getUid();
	    			}
	    			
	    			uv.setIdentifier(identifier);
	    			
	    			gv.setAdminInfo(uv);
				}
				gvs.add(gv);
			}
			
			groupViews.setGroup(gvs);
		}
		
		return groupViews;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<EmailTemplate> getEmailTemplate(Long emailTemplateTypeId, Long memberId){
		List<EmailTemplate> ets = null;
		
		User inviter = userDao.findById(memberId);
		
		String username ="";
		if (inviter.getFirstName() != null && inviter.getLastName() != null){
			username = inviter.getFirstName() + " " + inviter.getLastName();
		}else{
			username = "membership";
		}
		
		if (emailTemplateTypeId.equals(0l)){
			//Get All Email Templates
			ets = groupsDao.getAllEmailTemplate();
			
		}else{
			ets = groupsDao.getEmailTemplateByType(emailTemplateTypeId);
			
		}
		
		if (ets != null){
			for (EmailTemplate et : ets){
				
				et.setContent(et.getContent().replaceAll("<user name>", username));
        		et.setContent(TextUtil.parseString(et.getContent()));
        	}
		}
		return ets;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public GroupDetailView getGroupInfo(Long groupId){
		GroupDetailView gv = new GroupDetailView();
		
		Groups group = groupsDao.getGroup(groupId);
		
		User user = userDao.findUser(group.getAccountId());
		
		if ( group != null){
			gv.setId(group.getId());
			
			String username ="";
			if (user.getFirstName() != null && user.getLastName() != null){
				username = user.getFirstName() + " " + user.getLastName();
			}else{
				username = "membership";
			}
			gv.setHeadName(username);
			gv.setName(group.getName());
			if (group.getImageUrl() != null) gv.setImageUrl(ImageUtil.parseImageUrl(group.getImageUrl()));
			else gv.setImageUrl("/portal-core/images/rewards/groupsampleimage.png");
			
			//GET Email Templates
			List<EmailTemplate> ets = groupsDao.getEmailTemplateByType(5l);
			if (ets != null){
				for (EmailTemplate et : ets){
					
					et.setContent(et.getContent().replaceAll("<user name>", username));
            		et.setContent(TextUtil.parseString(et.getContent()));
            	}
				gv.setEmailTemplate(ets);
			}
			
			//GET Account List
			List<AccountGroup> ags = groupsDao.getNumberOfMembers(group.getId());
			if (ets != null){
				List<ContactView> cvs = new ArrayList<ContactView>();
				for (AccountGroup ag : ags){
					ContactView cv = new ContactView();
					if (ag.getUser().getFirstName() != null && ag.getUser().getLastName() != null){
						cv.setFirstName(ag.getUser().getFirstName());
						cv.setLastName(ag.getUser().getLastName());
					}else{
						cv.setFirstName(ag.getUser().getEmail());
						cv.setLastName("");
					}
					cv.setStatus("");
					//Images...?
					cv.setImageUrl("/portal-core/images/rewards/upload.png");
					cv.setId(ag.getUser().getId());
					cv.setEmail(ag.getUser().getEmail());
					cvs.add(cv);
					
				}
				
				gv.setContactViews(cvs);
			}
		}
		
		return gv;
	}
}
