package net.zfp.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.AccountSegment;
import net.zfp.entity.Account_Authority;
import net.zfp.entity.Domain;
import net.zfp.entity.EmailTemplate;
import net.zfp.entity.Fundraise;
import net.zfp.entity.Source;
import net.zfp.entity.User;
import net.zfp.entity.account.AccountGroup;
import net.zfp.entity.account.AccountGroupCommunicationStatus;
import net.zfp.entity.account.AccountCampaign;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.campaign.CampaignAction;
import net.zfp.entity.campaign.CampaignActionList;
import net.zfp.entity.campaign.CampaignFact;
import net.zfp.entity.campaign.CampaignFactList;
import net.zfp.entity.campaign.CampaignGroupProgress;
import net.zfp.entity.campaign.CampaignGroupProgressActivity;
import net.zfp.entity.campaign.CampaignProgress;
import net.zfp.entity.campaign.CampaignProgressActivity;
import net.zfp.entity.campaign.CampaignTarget;
import net.zfp.entity.category.Category;
import net.zfp.entity.community.Community;
import net.zfp.entity.electricity.ElectricityDataMonth;
import net.zfp.entity.group.GroupCampaign;
import net.zfp.entity.group.Groups;
import net.zfp.entity.memberactivity.MemberActivity;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.offer.OfferBehavior;
import net.zfp.entity.personalmotion.CyclingDataDayAverage;
import net.zfp.entity.personalmotion.PersonalMotion;
import net.zfp.entity.personalmotion.PersonalMotionInterval;
import net.zfp.entity.personalmotion.RunningDataDayAverage;
import net.zfp.entity.personalmotion.WalkingDayAverage;
import net.zfp.entity.personalmotion.WalkingHour;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.segment.SegmentType;
import net.zfp.entity.source.SourceUnit;
import net.zfp.notify.NotificationService;
import net.zfp.notify.impl.NotificationServiceImpl;
import net.zfp.service.CampaignService;
import net.zfp.service.UtilityService;
import net.zfp.util.AppConstants;
import net.zfp.util.CoreFunction;
import net.zfp.util.DateParserUtil;
import net.zfp.util.DateUtil;
import net.zfp.util.ImageUtil;
import net.zfp.util.TextUtil;
import net.zfp.view.BaselineView;
import net.zfp.view.CampaignSummaryView;
import net.zfp.view.CampaignSummaryViews;
import net.zfp.view.CampaignViews;
import net.zfp.view.ContactView;
import net.zfp.view.FundraiseView;
import net.zfp.view.GroupDetailView;
import net.zfp.view.GroupView;
import net.zfp.view.LeaderboardView;
import net.zfp.view.LeaderboardViews;
import net.zfp.view.OfferView;
import net.zfp.view.PersonalMotionDailyView;
import net.zfp.view.PersonalMotionView;
import net.zfp.view.CampaignDailyView;
import net.zfp.view.CampaignDetailView;
import net.zfp.view.CampaignView;
import net.zfp.view.ResultView;
import net.zfp.view.SponsorView;
import net.zfp.view.mobile.MobileLeaderboardView;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Resource
public class CampaignServiceImpl implements CampaignService {
	@Resource
    private UtilityService utilityService;
	@Resource
    private SegmentService segmentService;
	@Resource
    private ActivityService activityService;
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<Campaign> campaignDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Offer> offerDao;
	@Resource
	private EntityDao<Source> sourceDao;
	@Resource
	private EntityDao<Groups> groupsDao;
	@Resource
	private EntityDao<MemberActivity> memberActivityDao;
	@Resource
	private EntityDao<Account_Authority> accountAuthorityDao;
	@Resource
	private EntityDao<AccountGroup> accountGroupDao;
	@Resource
	private EntityDao<AccountSegment> accountSegmentDao;
	@Resource
	private EntityDao<Segment> segmentDao;
	@Resource
	private EntityDao<Fundraise> fundraiseDao;
	@Resource
	private EntityDao<PersonalMotion> personalMotionDao;
	@Resource
	private EntityDao<AccountCampaign> accountCampaignDao;
	@Resource
	private EntityDao<GroupCampaign> groupCampaignDao;
	@Resource
	private EntityDao<Category> categoryDao;
	@Inject
    private NotificationService notificationService;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView saveTarget(String accountEmail, Long campaignId, Integer target, Integer mode){
		ResultView rv = new ResultView();
		rv.setResultCode(AppConstants.FAILURE);
		try{
			User user = userDao.findActiveUserByEmail(accountEmail);
			Long userId = user.getId();
			AccountCampaign ap = accountCampaignDao.getAccountCampaign(userId, campaignId);
			if (ap != null){
				if (mode == 1){
					//ap.setFundraiseTarget(target);
				}
				/*
				else if (mode == 2){
					ap.setCampaignTarget(target);
				}
				*/
				accountCampaignDao.save(ap, true);
				rv.setResultCode(AppConstants.SUCCESS);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView checkGroupExists(String accountEmail, Long campaignId){
		ResultView rv = new ResultView();
		rv.setResultCode(AppConstants.FAILURE);
		try{
			User user = userDao.findActiveUserByEmail(accountEmail);
			Long userId = user.getId();
			AccountCampaign ap = accountCampaignDao.getAccountCampaign(userId, campaignId);
			if (ap != null && ap.getGroups() != null){
				rv.setResultCode(AppConstants.SUCCESS);
				rv.setResultMessage(ap.getGroups().getId() + "");
			}
		}catch (Exception e){
			
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView addSponsorGroup(String accountEmail, Long campaignId, Long groupId){
		ResultView rv = new ResultView();
		
		User user = userDao.findActiveUserByEmail(accountEmail);
		Long userId = user.getId();
		
		AccountCampaign ap = accountCampaignDao.getAccountCampaign(userId, campaignId);
		if (ap != null){
			Groups groups = groupsDao.getGroup(groupId);
			ap.setGroups(groups);
			accountCampaignDao.save(ap, true);
			rv.setResultCode(AppConstants.SUCCESS);
		}
		
		return rv;
		
	}
	
	/**
	 * Get Campaign Goal Summary
	 * 
	 * Get campaign progress for a specified iteration and get trends
	 * 
	 * @param memberId
	 * @param campaignId
	 * @param currentStage
	 * @param maxStage
	 * @param frequency
	 * @return
	 */
	private CampaignSummaryView campaignSourceSummaryHelper(Long memberId, Long campaignId, Integer currentStage, Integer maxStage, Integer frequency){
		CampaignSummaryView csv = new CampaignSummaryView();
		
		Campaign campaign = campaignDao.findById(campaignId);
		
		csv.setPeriodType(campaign.getPeriodType());
		csv.setStage(currentStage);
		if (currentStage <= maxStage){
			User user = userDao.findById(memberId);
			
			//Get source type which is your unit label
			CampaignTarget campaignTarget = campaignDao.getCampaignTarget(campaignId);
			if (campaignTarget.getUnit() != null){
				if (campaignTarget.getUnit().getDescription() != null) csv.setUnit(campaignTarget.getUnit().getDescription());
				csv.setUnit("");
				csv.setUnitLabel(campaignTarget.getUnit().getName().toUpperCase());
			}
			
			Double myValue = 0.0;
			boolean participated = false;
			
			if (campaign.getCampaignType().getType().equals(AppConstants.CAMPAIGN_TYPE_GROUP)){
				List<Groups> myGroups = groupsDao.getGroups(memberId);
				if (!myGroups.isEmpty()){
					List<Groups> myCampaignGroups = groupsDao.getGroupsWithCampaign(myGroups, campaign.getId());
					if (!myCampaignGroups.isEmpty()){
						/*
						 * Since we only going to display one group... pick first one.
						 */
						Groups targettedCampaignGroup = myCampaignGroups.get(0);
						CampaignGroupProgress cgp = campaignDao.getCampaignGroupProgress(campaign.getId(), targettedCampaignGroup.getId(), currentStage);
						if (cgp != null){
							myValue = cgp.getProgressValue();
							csv.setValue(myValue);
							/*
							 * If we have progress greater than 0 then we flag this group to be participated
							 */
							if (cgp.getProgressValue() > 0) participated = true;
						}else{
							csv.setValue(myValue);
						}
						
						//Now Compare with last one
						if (currentStage > 1){
							int previousStage = currentStage- 1;
							CampaignGroupProgress previousApp = campaignDao.getCampaignGroupProgress(campaign.getId(), targettedCampaignGroup.getId(), previousStage);
							if (previousApp != null){
								if (previousApp.getProgressValue() > myValue){
									csv.setTrend(1);
								}else if(previousApp.getProgressValue() < myValue){
									csv.setTrend(2);
								}else{
									csv.setTrend(-1);
								}
							}else{
								csv.setTrend(-1);
							}
						}else{
							//This is first iteration so... no value!
							csv.setTrend(-1);
						}
						
						if (participated){
							
							List<Long> groupIds = campaignDao.getGroupsInCampaign(campaignId);
							if (groupIds != null && groupIds.size() >= 2){
								int peerSize = 0;
								int counter = 0;
								
								for (Long m : groupIds){
									CampaignGroupProgress friendsProgress = campaignDao.getCampaignGroupProgress(campaignId, m, currentStage);
									if (friendsProgress != null){
										peerSize++;
										if (friendsProgress.getProgressValue() > myValue) counter++;
									}
								}
								
								if (peerSize >= 2){
									if ((double)counter / peerSize > 2.0/3.0){
										csv.setRank(3);
									}else if ((double) counter /peerSize > 1.0/3.0){
										csv.setRank(2);
									}else{
										csv.setRank(1);
									}
								}else{
									csv.setRank(1);
								}
								
							}else{
								csv.setRank(1);
							}
						}
					}
				}
				
			}else{
				
				//Get Campaign Progress Value
				CampaignProgress app = campaignDao.getCampaignProgress(campaignId, memberId, currentStage);
				if (app != null){
					myValue = app.getProgressValue();
					csv.setValue(myValue);
					if (app.getProgressValue() > 0) participated = true;
					
				}else{
					csv.setValue(myValue);
				}
				
				
				//Now Compare with last one
				if (currentStage > 1){
					int previousStage = currentStage- 1;
					CampaignProgress previousApp = campaignDao.getCampaignProgress(campaignId, memberId, previousStage);
					if (previousApp != null){
						if (previousApp.getProgressValue() > myValue){
							csv.setTrend(1);
						}else if(previousApp.getProgressValue() < myValue){
							csv.setTrend(2);
						}else{
							csv.setTrend(-1);
						}
					}else{
						csv.setTrend(-1);
						
					}
				}else{
					//This is first iteration so... no value!
					csv.setTrend(-1);
				}
				
				if (participated){
					//Compare with friends
					//Get friends from campaign
					List<Long> memberIds = campaignDao.getPeersInCampaign(campaignId);
					if (memberIds != null && memberIds.size() >= 2){
						int peerSize = 0;
						int counter = 0;
						
						for (Long m : memberIds){
							System.out.println("ID ::: " + m);
							CampaignProgress friendsProgress = campaignDao.getCampaignProgress(campaignId, m, currentStage);
							if (friendsProgress != null){
								peerSize++;
								if (friendsProgress.getProgressValue() > myValue) counter++;
							}
						}
						
						System.out.println("RANK :: " + (double)counter / peerSize);
						
						if (peerSize >= 2){
							if ((double)counter / peerSize > 2.0/3.0){
								csv.setRank(3);
							}else if ((double) counter /peerSize > 1.0/3.0){
								csv.setRank(2);
							}else{
								csv.setRank(1);
							}
						}else{
							csv.setRank(1);
						}
						
					}else{
						csv.setRank(1);
					}
				}
			}
			
		}
		return csv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CampaignSummaryViews getCampaignSourceSummary(Long memberId, Long campaignId){
		CampaignSummaryViews campaignSummaryViews = new CampaignSummaryViews();
		List<CampaignSummaryView> campaignSummaryList = new ArrayList<CampaignSummaryView>();
		CampaignSummaryView campaignSummaryView = new CampaignSummaryView();
		ResultView rv = new ResultView();		
		
		if (campaignId == null || memberId == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			campaignSummaryViews.setResult(rv);
			return campaignSummaryViews;
		}
		
		User user = userDao.findById(memberId);
		if (user == null){
			rv.fill(AppConstants.FAILURE, "Invalid member");
			campaignSummaryViews.setResult(rv);
			return campaignSummaryViews;
		}
		
		Campaign campaign = campaignDao.getCampaign(campaignId);
		if (campaign == null){
			rv.fill(AppConstants.FAILURE, "Invalid campaign");
			campaignSummaryViews.setResult(rv);
			return campaignSummaryViews;
		}
		
		//get frequency
		Integer frequency = campaign.getFrequency();
		Integer currentStage = campaign.getCurrentFrequencyStage();
		
		for (int i = 1; i <= frequency; i++){
			campaignSummaryList.add(campaignSourceSummaryHelper(memberId, campaignId, i, currentStage, frequency));
		}
		
		campaignSummaryViews.setCampaignSummary(campaignSummaryList);
		
		return campaignSummaryViews;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CampaignDetailView getCampaignSummary(Long campaignId, Long memberId, Integer iteration){
		CampaignDetailView cdv = new CampaignDetailView();
		
		Campaign campaign = campaignDao.getCampaign(campaignId);
		
		SimpleDateFormat df3 = new SimpleDateFormat("dd MMM yyyy");
		Date campaignStartDate = null;
		Date campaignEndDate = null;
		
		//Start Date..
		//End Date..	
		 if (campaign.getFrequency() != null && campaign.getFrequency()  > 1){
			campaignStartDate = DateUtil.getContinousStartDate(campaign.getStartDate(), campaign.getPeriodType(), iteration, campaign.getPeriod());
			campaignEndDate = DateUtil.getContinousEndDate(campaign.getStartDate(), campaign.getPeriodType(), iteration, campaign.getPeriod());
			cdv.setStartDate(df3.format(campaignStartDate).toUpperCase());
            cdv.setEndDate(df3.format(campaignEndDate).toUpperCase());
	     }else{
             campaignStartDate = campaign.getStartDate();
             campaignEndDate = campaign.getEndDate();
             cdv.setStartDate(df3.format(campaignStartDate).toUpperCase());
             cdv.setEndDate(df3.format(campaignEndDate).toUpperCase());
	     }
		 
		//Overall Progress
         List<CampaignProgress> paps = campaignDao.getCampaignAccountProgress(campaign.getId(), memberId, iteration);
         if (paps != null && paps.size() > 0){
             CampaignProgress pap = new CampaignProgress();
             if (paps.size()>0){
                     pap = paps.get(0);
                     Double accountProgress = pap.getProgressValue();
                     cdv.setProgress((int)Math.round(accountProgress));
             }else{
            	 cdv.setProgress(0);
             }
                 
         }
         else{
        	 cdv.setProgress(0);
         }
         
		//Coins earned in the campaign iteration...
       //Get Offer from offerId
		try{
			List<Offer> offers = getCampaignOffer(campaign.getId(), null, campaignEndDate, memberId);
			if (offers != null){
				Integer offerPoints = 0;
				if (offers != null && offers.size() > 0){
					for(Offer offer : offers){
						
						if (iteration == 1){
							if (isMemberActivityProcessedByDate(memberId, campaign.getId(), offer.getCode(), campaign.getLaunchDate(), campaignEndDate)){
								offerPoints += offer.getValue().intValue();
							}
						}else{
							if (isMemberActivityProcessedByDate(memberId, campaign.getId(), offer.getCode(), campaignStartDate, campaignEndDate)){
								offerPoints += offer.getValue().intValue();
							}
						}
						
					}
				}
				
				cdv.setOfferValue(offerPoints.toString());
			}else{
				cdv.setOfferValue("0");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			cdv.setOfferValue("0");
		}
		
		
		return cdv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public LeaderboardViews getLeaderboardData(Long campaignId, Long accountId, Integer iteration){
		
		//We are in Campaign Leaderboard Web Services
		LeaderboardViews leaderboardViews = new LeaderboardViews();
		List<LeaderboardView> lvs = new ArrayList<LeaderboardView>();
		LeaderboardView leaderboardView = new LeaderboardView();
		ResultView rv = new ResultView();		
		
		if (campaignId == null || accountId == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			leaderboardViews.setResult(rv);
			return leaderboardViews;
		}
		
		User user = userDao.findById(accountId);
		if (user == null){
			rv.fill(AppConstants.FAILURE, "Invalid member");
			leaderboardViews.setResult(rv);
			return leaderboardViews;
		}
		
		Campaign campaign = campaignDao.getCampaign(campaignId);
		if (campaign == null){
			rv.fill(AppConstants.FAILURE, "Invalid campaign");
			leaderboardViews.setResult(rv);
			return leaderboardViews;
		}
		
		
		if (iteration == null) iteration = 0;
		
		boolean sortByLower = false;
		//Check if campaign target condition...
		CampaignTarget ct = campaignDao.getCampaignTarget(campaignId);
		
		if (ct != null){
			if (ct.getRuleCondition() != null && (ct.getRuleCondition().getCondition().equals("<") || ct.getRuleCondition().getCondition().equals("<="))){
				sortByLower = true;
			}
		}
		
		if (campaign.getCampaignType().getType().equals(AppConstants.CAMPAIGN_TYPE_GROUP)){
			List<Groups> groups = campaignDao.getGroupsInCampagin(campaignId);
			if (groups != null){
				Integer greenZone =0, yellowzone =0;
				
				if (groups.size() == 1){
					greenZone = 0;
				}else if (groups.size() == 2){
					greenZone = 0;
					yellowzone = 1;
				}else{
					greenZone = groups.size()/3;
					yellowzone = 2 * groups.size()/3;
				}
				
				
				//Sort user with account campaign progress
				for (Groups group : groups){
					leaderboardView = new LeaderboardView();
					leaderboardView.setAccountId(group.getId());
					leaderboardView.setSourceId(group.getId());
					
					leaderboardView.setSourceFirstName(group.getName());
					leaderboardView.setSourceLastName("");
					
					/*
					 * For each group get target for everyone
					 */
					if (ct.getCampaignTargetMode().getType().equals(AppConstants.CAMPAIGN_TARGET_MODE_DYNAMIC)){
						int dynamicTarget = 0;
						List<User> membersInGroup = accountGroupDao.getAccountFromGroups(group.getId());
						/*
						 * Get individual campaign target and sum it up to get group campaign target
						 */
						if (!membersInGroup.isEmpty()){
							for (User member : membersInGroup){
								dynamicTarget += individualCampaignTarget(member, campaign, ct);
							}
						}
						leaderboardView.setTarget(dynamicTarget);
					}else{
						leaderboardView.setTarget(ct.getTarget());
					}
					List<CampaignGroupProgress> app = userDao.getCampaignGroupProgresses(campaignId, group.getId(), iteration);
					if (app != null && app.size() > 0 && app.get(0).getProgressValue() > 0){
						leaderboardView.setSortingValue(app.get(0).getProgressValue());
						if (app.get(0).getProgressValue() > 50){
							leaderboardView.setValue((double)Math.round(app.get(0).getProgressValue()));
						}else{
							leaderboardView.setValue((double)Math.round(app.get(0).getProgressValue()* 10.0)/10.0);
						}
						
					}else{
						if (!app.isEmpty()) leaderboardView.setValue(app.get(0).getProgressValue());
						else leaderboardView.setValue(0.0);
						
						if (sortByLower){
							leaderboardView.setSortingValue(Double.MAX_VALUE);
						}else{
							leaderboardView.setSortingValue(Double.MIN_VALUE);
						}
						
					}
					//System.out.println("Source NAME : " + leaderboardView.getSourceFirstName() + " : value : " + leaderboardView.getValue());
					lvs.add(leaderboardView);
				}
				
				//sort leaderboard view to lowest value
				Comparator<LeaderboardView> SortLowestComparator = new Comparator<LeaderboardView>(){
					public int compare(LeaderboardView lv1, LeaderboardView lv2){
						if (lv1.getSortingValue() < lv2.getSortingValue()) return -1;
						else if (lv1.getSortingValue() > lv2.getSortingValue()) return 1;
						else return 0;
					}
				};
				
				//sort leaderboard view to highest value
				Comparator<LeaderboardView> SortHighestComparator = new Comparator<LeaderboardView>(){
					public int compare(LeaderboardView lv1, LeaderboardView lv2){
						if (lv1.getSortingValue() < lv2.getSortingValue()) return 1;
						else if (lv1.getSortingValue() > lv2.getSortingValue()) return -1;
						else return 0;
					}
				};
				
				if (sortByLower){
					Collections.sort(lvs, SortLowestComparator);
				
				}else{
					Collections.sort(lvs, SortHighestComparator);
					
				}
				
				List<LeaderboardView> orderedLvs = new ArrayList<LeaderboardView>(); 
				
				/*
				 * Get my groups in campaign
				 */
				List<Groups> myGroups = groupsDao.getGroups(accountId);
				if (!myGroups.isEmpty()){
					List<Groups> myCampaignGroups = groupsDao.getGroupsWithCampaign(myGroups, campaign.getId());
					
					int remaining = 9 - myCampaignGroups.size();
					
					for (int i = 0; i<lvs.size(); i++){
						leaderboardView = lvs.get(i);
						//Pass the 10th element if a user is not found yet
						if (!validateGroupId(leaderboardView.getSourceId(), myCampaignGroups) && i > remaining){
							continue;
						}
						
						//Each source get Id and name and value
						if (i <= greenZone){
							leaderboardView.setRank(1);
						}else if (i <= yellowzone){
							leaderboardView.setRank(2);
						}else{
							leaderboardView.setRank(3);
						}
						
						leaderboardView.setRanking(i+1);
						
						orderedLvs.add(leaderboardView);
						
						if (myCampaignGroups.size() == 0){
							if (i >= 9){
								break;
							}
						}
						
					}
				}
				
				
				lvs = orderedLvs;
				
			}
		}else{
			
			List<User> users = campaignDao.getUsersInCampagin(campaignId);
			if ( users != null){
				Integer greenZone =0, yellowzone =0;
				
				if (users.size() == 1){
					greenZone = 0;
				}else if (users.size() == 2){
					greenZone = 0;
					yellowzone = 1;
				}else{
					greenZone = users.size()/3;
					yellowzone = 2 * users.size()/3;
				}
				
				
				//Sort user with account campaign progress
				for (User u : users){
					leaderboardView = new LeaderboardView();
					leaderboardView.setAccountId(u.getId());
					leaderboardView.setSourceId(u.getId());
					if (u.getFirstName() != null && u.getLastName() != null){
						leaderboardView.setSourceFirstName(u.getFirstName());
						leaderboardView.setSourceLastName(u.getLastName().substring(0, 1));
					}else{
						leaderboardView.setSourceFirstName(u.getEmail());
						leaderboardView.setSourceLastName("");
					}
					
					/*
					 * For each user... Get Target
					 */
					if (ct.getCampaignTargetMode() != null && ct.getCampaignTargetMode().getType().equals(AppConstants.CAMPAIGN_TARGET_MODE_DYNAMIC)){
						
						/*
						 * Individual dynamic campaign
						 */
						leaderboardView.setTarget(individualCampaignTarget(u, campaign, ct));
						
					}else{
						leaderboardView.setTarget(ct.getTarget());
					}
					List<CampaignProgress> app = userDao.getCampaignAccountProgress(campaignId, u.getId(), iteration);
					if (app != null && app.size() > 0 && app.get(0).getProgressValue() > 0){
						leaderboardView.setSortingValue(app.get(0).getProgressValue());
						if (app.get(0).getProgressValue() > 50){
							leaderboardView.setValue((double)Math.round(app.get(0).getProgressValue()));
						}else{
							leaderboardView.setValue((double)Math.round(app.get(0).getProgressValue()* 10.0)/10.0);
						}
						
					}else{
						if (!app.isEmpty()) leaderboardView.setValue(app.get(0).getProgressValue());
						else leaderboardView.setValue(0.0);
						
						if (sortByLower){
							leaderboardView.setSortingValue(Double.MAX_VALUE);
						}else{
							leaderboardView.setSortingValue(Double.MIN_VALUE);
						}
						
					}
					//System.out.println("Source NAME : " + leaderboardView.getSourceFirstName() + " : value : " + leaderboardView.getValue());
					lvs.add(leaderboardView);
				}
				
				//sort leaderboard view to lowest value
				Comparator<LeaderboardView> SortLowestComparator = new Comparator<LeaderboardView>(){
					public int compare(LeaderboardView lv1, LeaderboardView lv2){
						if (lv1.getSortingValue() < lv2.getSortingValue()) return -1;
						else if (lv1.getSortingValue() > lv2.getSortingValue()) return 1;
						else return 0;
					}
				};
				
				//sort leaderboard view to highest value
				Comparator<LeaderboardView> SortHighestComparator = new Comparator<LeaderboardView>(){
					public int compare(LeaderboardView lv1, LeaderboardView lv2){
						if (lv1.getSortingValue() < lv2.getSortingValue()) return 1;
						else if (lv1.getSortingValue() > lv2.getSortingValue()) return -1;
						else return 0;
					}
				};
				
				if (sortByLower){
					Collections.sort(lvs, SortLowestComparator);
				
				}else{
					Collections.sort(lvs, SortHighestComparator);
					
				}
				
				List<LeaderboardView> orderedLvs = new ArrayList<LeaderboardView>(); 
				boolean isUserIncluded= false;
				for (int i = 0; i<lvs.size(); i++){
					leaderboardView = lvs.get(i);
					//Pass the 10th element if a user is not found yet
					if (!isUserIncluded && !leaderboardView.getSourceId().equals(accountId) && i >8){
						continue;
					}
					
					//Each source get Id and name and value
					if (i <= greenZone){
						leaderboardView.setRank(1);
					}else if (i <= yellowzone){
						leaderboardView.setRank(2);
					}else{
						leaderboardView.setRank(3);
					}
					
					leaderboardView.setRanking(i+1);
					
					orderedLvs.add(leaderboardView);
					
					if (leaderboardView.getSourceId().equals(accountId)){
						isUserIncluded = true;
						System.out.println("Found the user !!!!!!");
					}
					
					if (isUserIncluded){
						if (i >= 9){
							break;
						}
					}
					
				}
				
				lvs = orderedLvs;
				
				
			}
		}
		
		
		leaderboardViews.setLeaderboardView(lvs);
		
		
		return leaderboardViews;
	}
	
	private boolean validateGroupId(Long groupId, List<Groups> campaignGroups){

		if (!campaignGroups.isEmpty()){
			for (Groups group : campaignGroups){
				if (groupId.equals(group.getId())){
					campaignGroups.remove(group);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public FundraiseView getFundraiseCampaign(Long campaignId, Long accountId){
		FundraiseView fv = new FundraiseView();
		Long userId = accountId;
		
		Campaign campaign = campaignDao.getCampaign(campaignId);
		
		if (campaign != null){
			//get account camapgin fundraise target
			System.out.println("Campaign SERVICESS !!! USER ID :: " + userId + " campaign Id :: " + campaign.getId());
			AccountCampaign ap = accountCampaignDao.getAccountCampaign(userId, campaign.getId());
			int target = 1;
			if (ap != null) {
				System.out.println("Account campaign ");
				//target = ap.getFundraiseTarget();
			}
			
			int actual = 0;
			Double amount = userDao.getFundraisedValue(ap.getId());
			if (amount != null){
				System.out.println("AMOUNT VALUE :: " + amount);
				actual = amount.intValue();
			}
			
			fv.setTarget(target);
			fv.setActual(actual);
			double progress = 0.0;
			if (target > 0.0) progress = (double)actual/ target;
			if (progress > 1.0) progress = 1.0;
			fv.setProgress( (int)(progress * 100));
			
			List<Offer> offers = getCampaignOffer(campaign.getId(), campaign.getCommunityId(), campaign.getEndDate(), accountId);
			if (offers != null){
				
				Integer offerPoints = 0;
				if (offers != null && offers.size() > 0){
					if (campaign.getStatus().getCode().equals(AppConstants.STATUS_COMPLETED)){
						for(Offer offer : offers){
							System.out.println(offer.getName());
							if (isMemberActivityProcessed(accountId, campaign.getId(), offer.getCode())){
								offerPoints += offer.getValue().intValue();
							}
						}
						
					}else{
						for (Offer offer : offers){
							offerPoints += offer.getValue().intValue();
						}
						
						
					}
				}
				fv.setOfferPoints(offerPoints);
				fv.setIsOffer(true);
			}else{
				fv.setIsOffer(false);
			}
			
		}
		
		return fv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public SponsorView getGroupDetail(String serverName, Long campaignId, String accountEmail){
		SponsorView sv = new SponsorView();
		
		List<GroupDetailView> gdvs = new ArrayList<GroupDetailView>();
		//Get Community Id
		Community community = communityDao.getCommunity(serverName);
		User user = userDao.findActiveUserByEmail(accountEmail);
		Long userId = user.getId();
		AccountCampaign ap = accountCampaignDao.getAccountCampaign(userId, campaignId);
		
		if (ap != null && ap.getGroups() != null){
			sv.setGroupId(ap.getGroups().getId());
			if (user.getFirstName() != null && user.getLastName() != null){
				sv.setHeadName(user.getFirstName() + " " + user.getLastName());
			}else{
				sv.setHeadName("membership");
			}
		}
		List<Groups> groups = groupsDao.getGroups(userId);

		if (groups != null && groups.size() > 0 ){
			for (Groups group : groups){
				GroupDetailView gv = new GroupDetailView();
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
				List<EmailTemplate> ets = groupsDao.getEmailTemplateByType(4l);
				if (ets != null){
					for (EmailTemplate et : ets){
						//Here we have to replace bunch
						//campaign name
						et.setContent(et.getContent().replaceAll("<campaign name>", ap.getCampaign().getName()));
						//start date
						et.setContent(et.getContent().replaceAll("<start date>", DateUtil.printCalendar2(ap.getCampaign().getStartDate())));
						//end date
						et.setContent(et.getContent().replaceAll("<end date>", DateUtil.printCalendar2(ap.getCampaign().getEndDate())));
						
						et.setContent(et.getContent().replaceAll("<user name>", username));
						
						et.setContent(TextUtil.parseString(et.getContent()));
					}
					gv.setEmailTemplate(ets);
				}

				//GET ACCount List
				List<AccountGroup> ags = groupsDao.getNumberOfMembers(group.getId());
				if (ets != null){
					List<ContactView> cvs = new ArrayList<ContactView>();
					for (AccountGroup ag : ags){
						ContactView cv = new ContactView();
						if (ag.getUser().getFirstName() != null && ag.getUser().getLastName() != null){
							cv.setFirstName(ag.getUser().getFirstName());
							cv.setLastName(ag.getUser().getLastName());
						}
						
						Double totalAmount = fundraiseDao.getIndividualFundraisedValue(ag.getUser().getId(), ap.getId());
						DecimalFormat df = new DecimalFormat("#.00");
						
						if (totalAmount != null) cv.setAmount(df.format(totalAmount));
						AccountGroupCommunicationStatus agcs = groupsDao.getAccountCampaignStatus(ag.getUser().getId(), ag.getGroups().getId(), campaignId);

						if (agcs != null) cv.setStatus(agcs.getStatus().getName());
						else cv.setStatus("");

						//Images...?
						cv.setImageUrl("/portal-core/images/rewards/upload.png");
						cv.setId(ag.getUser().getId());
						cv.setEmail(ag.getUser().getEmail());
						cvs.add(cv);

					}

					gv.setContactViews(cvs);
				}

				gdvs.add(gv);
			}
		}else{
			GroupDetailView gv = new GroupDetailView();
			gv.setId(-1l);
			if (user.getFirstName() != null && user.getLastName() != null){
				gv.setHeadName(user.getFirstName() + " " + user.getLastName());
			}else{
				gv.setHeadName("membership");
			}
			gv.setName("No groups associated");

			List<EmailTemplate> ets = groupsDao.getEmailTemplateByType(4l);
			if (ets != null){
				for (EmailTemplate et : ets){
					et.setContent(et.getContent().replaceAll("[\n\r]", "<br/>"));
				}
				gv.setEmailTemplate(ets);
			}
			gv.setContactViews(new ArrayList<ContactView>());

			gdvs.add(gv);
		}



		sv.setGroupDetailView(gdvs);
		return sv;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CampaignDailyView getCampaignDaily(Long campaignId,Long accountId){
		CampaignDailyView cdv = new CampaignDailyView();
		
		ResultView rv = new ResultView();		
		
		if (campaignId == null || accountId == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			cdv.setResult(rv);
			return cdv;
		}
		
		User user = userDao.findById(accountId);
		if (user == null){
			rv.fill(AppConstants.FAILURE, "Invalid member");
			cdv.setResult(rv);
			return cdv;
		}
		
		Campaign campaign = campaignDao.getCampaign(campaignId);
		if (campaign == null){
			rv.fill(AppConstants.FAILURE, "Invalid campaign");
			cdv.setResult(rv);
			return cdv;
		}
		
		if (campaign != null){
			CampaignTarget campaignTarget = campaignDao.getCampaignTarget(campaignId);
			
			String campaignTargetType = AppConstants.CAMPAIGN_TARGET_TYPE_GOAL;
			
			if (campaignTarget.getCampaignTargetType() != null) campaignTargetType = campaignTarget.getCampaignTargetType().getType();
			//campaign Progress 
			int iteration = 1;
			if (campaign.getCurrentFrequencyStage() != null && campaign.getCurrentFrequencyStage() != 0 ) iteration = campaign.getCurrentFrequencyStage();
			
			/*
			 * Total value for daily progress
			 */
			Double totalValue = 0.0;
			
			if (campaign.getCampaignType().getType().equals(AppConstants.CAMPAIGN_TYPE_GROUP)){
				List<Groups> myGroups = groupsDao.getGroups(accountId);
				if (!myGroups.isEmpty()){
					List<Groups> myCampaignGroups = groupsDao.getGroupsWithCampaign(myGroups, campaign.getId());
					
					if (!myCampaignGroups.isEmpty()){
						/*
						 * Get Group Campaign to get daily target
						 */
						List<GroupCampaign> groupCampaign = groupCampaignDao.getGroupCampaigns(myCampaignGroups, campaign.getId());
						if (!groupCampaign.isEmpty()){
							if (groupCampaign.get(0).getCampaignDailyTarget() == 0){
								if (campaignTargetType.equals(AppConstants.CAMPAIGN_TARGET_TYPE_LIMIT)){
									/*
									 * If target type is limit... Then daily target is same as campaign target 
									 */
									cdv.setTarget(campaignTarget.getTarget());
								}else{
									/*
									 * Target has not set up... So we calculate target by on fly
									 */
									Integer days = 0;
									
									Date endDate = null;
									if (campaign.getFrequency() != null && campaign.getFrequency()  > 1){
										endDate = DateUtil.getContinousEndDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
										
									}else{
										endDate = campaign.getEndDate();
									}
									
									days = Days.daysBetween(new DateTime(new Date()), new DateTime(endDate)).getDays();
									days++;
									
									int target = (int)Math.ceil(campaignTarget.getTarget()/days);
									if (campaignTarget != null && target >= 0) cdv.setTarget(target);
									else cdv.setTarget(0);
								}
							}else{
								cdv.setTarget(groupCampaign.get(0).getCampaignDailyTarget());
							}
							
							//Get Today's time
							Date today = getBeginningOfDay(new Date());
							CampaignGroupProgress cgp = groupCampaignDao.getCampaignGroupProgress(campaign.getId(), groupCampaign.get(0).getGroups().getId(), iteration);
							if ( cgp != null){
								if (campaign.getCampaignTemplate().getSourceType().getCode().equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY_IN_PERCENTAGE) || 
										campaign.getCampaignTemplate().getSourceType().getCode().equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY_PERCENTAGE_USAGE_DURING_OFF_PEAK)){
									CampaignGroupProgressActivity cpa = campaignDao.getLatestCampaignGroupProgressActivity(cgp.getId(), today);
									if (cpa != null){
										totalValue += cpa.getProgressValue();
									}
								}else{
									
									List<CampaignGroupProgressActivity> cpas = campaignDao.getCampaignGroupProgressActivities(cgp.getId(), today);
									
									if (cpas != null && cpas.size() > 0){
										for (CampaignGroupProgressActivity cpa : cpas){
											totalValue += cpa.getProgressValue();
										}
									}
								}
							}
							
						}else{
							/*
							 * Set Target is equal to zero since member group doesn't belong to a campaign
							 */
							cdv.setTarget(0);
						}
						
						
					}else{
						/*
						 * Set Target is equal to zero since member group doesn't belong to a campaign
						 */
						cdv.setTarget(0);
					}
					
					
				}
				AccountCampaign accountCampaign = accountCampaignDao.getAccountCampaign(accountId, campaignId);
				
				if (accountCampaign != null){
					if (accountCampaign.getCampaignDailyTarget() ==null){
						if (campaignTargetType.equals(AppConstants.CAMPAIGN_TARGET_TYPE_LIMIT)){
							cdv.setTarget(campaignTarget.getTarget());
						}else{
							
							Integer days = 0;
							
							Date endDate = null;
							if (campaign.getFrequency() != null && campaign.getFrequency()  > 1){
								endDate = DateUtil.getContinousEndDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
								
							}else{
								endDate = campaign.getEndDate();
							}
							
							days = Days.daysBetween(new DateTime(new Date()), new DateTime(endDate)).getDays();
							days++;
							
							int target = (int)Math.ceil(campaignTarget.getTarget()/days);
							if (campaignTarget != null && target >= 0) cdv.setTarget(target);
							else cdv.setTarget(0);
						}
					}else{
						cdv.setTarget(accountCampaign.getCampaignDailyTarget());
					}
				}
			}else{
				
				
				AccountCampaign accountCampaign = accountCampaignDao.getAccountCampaign(accountId, campaignId);
				
				if (accountCampaign != null){
					if (accountCampaign.getCampaignDailyTarget() ==null){
						if (campaignTargetType.equals(AppConstants.CAMPAIGN_TARGET_TYPE_LIMIT)){
							cdv.setTarget(campaignTarget.getTarget());
						}else{
							
							Integer days = 0;
							
							Date endDate = null;
							if (campaign.getFrequency() != null && campaign.getFrequency()  > 1){
								endDate = DateUtil.getContinousEndDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
								
							}else{
								endDate = campaign.getEndDate();
							}
							
							days = Days.daysBetween(new DateTime(new Date()), new DateTime(endDate)).getDays();
							days++;
							
							int target = (int)Math.ceil(campaignTarget.getTarget()/days);
							if (campaignTarget != null && target >= 0) cdv.setTarget(target);
							else cdv.setTarget(0);
						}
					}else{
						cdv.setTarget(accountCampaign.getCampaignDailyTarget());
					}
				}
				
				//Get Today's time
				Date today = getBeginningOfDay(new Date());
				CampaignProgress cp = campaignDao.getCampaignProgress(campaignId, accountId, iteration);
				
				if ( cp != null){
					if (campaign.getCampaignTemplate().getSourceType().getCode().equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY_IN_PERCENTAGE) || 
							campaign.getCampaignTemplate().getSourceType().getCode().equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY_PERCENTAGE_USAGE_DURING_OFF_PEAK)){
						CampaignProgressActivity cpa = campaignDao.getLatestCampaignProgressActivity(cp.getId(), today);
						if (cpa != null){
							totalValue += cpa.getProgressValue();
						}
					}else{
						List<CampaignProgressActivity> cpas = campaignDao.getCampaignProgressActivities(cp.getId(), today);
						
						if (cpas != null && cpas.size() > 0){
							for (CampaignProgressActivity cpa : cpas){
								totalValue += cpa.getProgressValue();
							}
						}
					}
				}
				
			}
			
			cdv.setActual((int)Math.round(totalValue));
			
			if (cdv.getTarget() != null && cdv.getTarget() > 0) cdv.setProgressbarValue((int)(((double)(int) cdv.getActual() / (double)(int)cdv.getTarget()) * 100));
			else cdv.setProgressbarValue(0);
		}
		return cdv;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView connectUserCampaign(Long accountId, Long campaignId){
		ResultView rv = new ResultView();
		
		if (campaignId == null || accountId == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);

			return rv;
		}
		
		User user = userDao.findById(accountId);
		if (user == null){
			rv.fill(AppConstants.FAILURE, "Invalid member");

			return rv;
		}
		
		Campaign campaign = campaignDao.getCampaign(campaignId);
		if (campaign == null){
			rv.fill(AppConstants.FAILURE, "Invalid campaign");

			return rv;
		}
		
		try{
			Boolean canConnect = true;
			
			if (campaign.getCampaignTemplate().getMeterRequired()){
				String codes = "";
				if (campaign.getCampaignTemplate().getSourceType().getCode().contains(AppConstants.SOURCETYPE_CODE_WALKING)) codes = AppConstants.SOURCETYPE_CODE_WALKING;
				else if (campaign.getCampaignTemplate().getSourceType().getCode().contains(AppConstants.SOURCETYPE_CODE_ELECTRICITY)) codes = AppConstants.SOURCETYPE_CODE_ELECTRICITY;
				else if (campaign.getCampaignTemplate().getSourceType().getCode().contains(AppConstants.SOURCETYPE_CODE_TIMESPENT)) codes = AppConstants.SOURCETYPE_CODE_WALKING;
				else codes = campaign.getCampaignTemplate().getSourceType().getCode();
				
				List<Source> sources = userDao.findSourceByAccountAndType(accountId, codes);
				if (sources == null || sources.size() == 0) canConnect = false;
			}
			
			if (canConnect){
				
				CampaignTarget campaignTarget = campaignDao.getCampaignTarget(campaignId);
				int target = 0;
				if (campaignTarget != null){
					/*
					 * get campaign target
					 */
					target = campaignTarget.getTarget();
					
					if (campaignTarget.getCampaignTargetMode() != null &&
							campaignTarget.getCampaignTargetMode().getType().equals(AppConstants.CAMPAIGN_TARGET_MODE_DYNAMIC)){
						/*
						 * Since campaign target is dynamic we want to update sources...
						 */
						target = dynamicCampaignTargetHelper(user, campaign, campaignTarget);
						
					}
				}
				/*
				 * Check campaign type whether group or individual(default)
				 */
				if (campaign.getCampaignType().getType().equals(AppConstants.CAMPAIGN_TYPE_GROUP)){
					System.out.println("Campaign type is group...");
					List<Groups> groups = groupsDao.getGroupsBySegment(user.getId(), campaign.getSegment().getId());
					List<GroupCampaign> groupCampaign = groupCampaignDao.getGroupCampaigns(groups, campaign.getId());
					if (groupCampaign.isEmpty()){
						
						Date campaignStartDate = null;
						Date campaignEndDate = null;
						
						if (campaign.getFrequency() != null && campaign.getFrequency()  > 1){
							campaignStartDate = DateUtil.getContinousStartDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
							campaignEndDate = DateUtil.getContinousEndDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
							
						}else{
							campaignStartDate = campaign.getStartDate();
							campaignEndDate = campaign.getEndDate();
						}
						
						/*
						 * Get all groups needs to be added...
						 */
						for(Groups group : groups){
							/*
							 * Add to GroupCampaign table
							 */
							GroupCampaign gc = new GroupCampaign();
							gc.setGroups(group);
							gc.setCampaign(campaign);
							gc.setCampaignTarget(target);
							if (campaignStartDate != null && campaignEndDate != null){
								
								//If unit is percentage or campaign target type is limit then we want to match the daily target to campaign target
								if ((campaignTarget.getUnit() != null && campaignTarget.getUnit().getCode().equals(AppConstants.UNIT_PERCENTAGE)) ||
										(campaignTarget.getCampaignTargetType() != null && campaignTarget.getCampaignTargetType().equals(AppConstants.CAMPAIGN_TARGET_TYPE_LIMIT))){
									gc.setCampaignDailyTarget(target);
								}else{
									Integer days = Days.daysBetween(new DateTime(campaignStartDate), new DateTime(campaignEndDate)).getDays();
									if (days < 0) days = 0;
									days++;
									
									Double dailyTarget = ((double)target) / days;
									
									gc.setCampaignDailyTarget(dailyTarget.intValue());
								}
								
							}else{
								gc.setCampaignDailyTarget(target);
							}
							
							groupCampaignDao.save(gc);
							rv.setResultCode(AppConstants.SUCCESS);
							
						}
						
						System.out.println("After save...");
						try{
							Domain domain = communityDao.getDomainByCommunityId(user.getDefaultCommunity());
							
							notificationService = new NotificationServiceImpl();
							notificationService.notifyCampaignJoin(user, campaign, domain.getName());
						}catch(Exception e){
							e.printStackTrace();
							rv.setResultCode(AppConstants.FAILURE);
						}
						
						rv.fill(AppConstants.SUCCESS, "Successfully added the group to the campaign");
					}else{
						rv.fill(AppConstants.FAILURE, "Member already joined the campaign");
					}
					
				}else{
					AccountCampaign apExisted = accountCampaignDao.getAccountCampaign(user.getId(), campaign.getId());
					if (apExisted == null){
						
						Date campaignStartDate = null;
						Date campaignEndDate = null;
						
						if (campaign.getFrequency() != null && campaign.getFrequency()  > 1){
							campaignStartDate = DateUtil.getContinousStartDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
							campaignEndDate = DateUtil.getContinousEndDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
							
						}else{
							campaignStartDate = campaign.getStartDate();
							campaignEndDate = campaign.getEndDate();
						}
						
						AccountCampaign ap = new AccountCampaign();
						ap.setUser(user);
						ap.setCampaign(campaign);
						ap.setCampaignTarget(target);
						//calculate daily target
						if (campaignStartDate != null && campaignEndDate != null){
							
							//If unit is percentage or campaign target type is limit then we want to match the daily target to campaign target
							if ((campaignTarget.getUnit() != null && campaignTarget.getUnit().getCode().equals(AppConstants.UNIT_PERCENTAGE)) ||
									(campaignTarget.getCampaignTargetType() != null && campaignTarget.getCampaignTargetType().equals(AppConstants.CAMPAIGN_TARGET_TYPE_LIMIT))){
								ap.setCampaignDailyTarget(target);
							}else{
								Integer days = Days.daysBetween(new DateTime(campaignStartDate), new DateTime(campaignEndDate)).getDays();
								if (days < 0) days = 0;
								days++;
								
								Double dailyTarget = ((double)(int)target) / days;
								
								ap.setCampaignDailyTarget(dailyTarget.intValue());
							}
							
						}else{
							ap.setCampaignDailyTarget(target);
						}
						/*
						if (campaignTarget != null) ap.setCampaignTarget(campaignTarget.getTarget());
						else ap.setCampaignTarget(100);
						*/
						accountCampaignDao.save(ap);
						rv.setResultCode(AppConstants.SUCCESS);
						
						try{
							Domain domain = communityDao.getDomainByCommunityId(user.getDefaultCommunity());
							
							notificationService = new NotificationServiceImpl();
							notificationService.notifyCampaignJoin(user, campaign, domain.getName());
						}catch(Exception e){
							rv.setResultCode(AppConstants.FAILURE);
						}
						
						rv.fill(AppConstants.SUCCESS, "Successfully added the member to the campaign");
					}else{
						rv.fill(AppConstants.FAILURE, "Member already joined the campaign");
					}
				}
				
				
			}else{
				rv.fill(AppConstants.FAILURE, "Meter is required in order to connect to the campaign.");
			}
			
		}catch(Exception e){
			
		}
		/*
		List<String> sourceCodes = new ArrayList<String>(Arrays.asList("SPEND", "ACTION", "WASTE"));
		List<String> electricityCodes = new ArrayList<String>(Arrays.asList("ELECTRICITY", "ELECTRICITY_IN_PERCENTAGE", "ELECTRICITY_PERCENTAGE_USAGE_DURING_OFF_PEAK"));
		List<String> walkingCodes = new ArrayList<String>(Arrays.asList("WALKING", "WALKING_IN_DISTANCE", "CALORIES"));
		
		//Get AccountId
		User user = null;
		try{
			user = userDao.findById(accountId);
			Campaign campaign = campaignDao.getCampaign(campaignId);
			
			String codes = "";
			if (walkingCodes.contains(campaign.getCampaignTemplate().getSourceType().getCode())) codes = AppConstants.SOURCETYPE_CODE_WALKING; 
			else if (electricityCodes.contains(campaign.getCampaignTemplate().getSourceType().getCode())) codes= AppConstants.SOURCETYPE_CODE_ELECTRICITY;
			else codes = campaign.getCampaignTemplate().getSourceType().getCode();
			
			//Check if user has this campaign
			List<Source> sources = userDao.findSourceByAccountAndType(accountId, codes);
			if ((sources != null && sources.size() > 0) || sourceCodes.contains(campaign.getCampaignTemplate().getSourceType().getCode())){
				AccountCampaign apExisted = accountCampaignDao.getAccountCampaign(user.getId(), campaign.getId());
				System.out.println(user.getId() + " ++ " + campaign.getId());
				if (apExisted == null){
					System.out.println("NEW account and campaign");
					CampaignTarget campaignTarget = campaignDao.getCampaignTarget(campaignId);
					AccountCampaign ap = new AccountCampaign();
					ap.setUser(user);
					ap.setCampaign(campaign);
					ap.setFundraiseTarget(100);
					if (campaignTarget != null) ap.setCampaignTarget(campaignTarget.getTarget());
					else ap.setCampaignTarget(100);
					accountCampaignDao.save(ap);
					rv.setResultCode(AppConstants.SUCCESS);
					
					try{
						
						notificationService = new NotificationServiceImpl();
						notificationService.notifyCampaignJoin(user, campaign);
					}catch(Exception e){
						rv.setResultCode(AppConstants.FAILURE);
					}
				}else{
					
					System.out.println("already existed...!");
				}
				rv.fill(AppConstants.SUCCESS, "SUCCESS");
			}else{
				
				rv.fill(AppConstants.FAILURE, codes);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		
		return rv;
	}
	
	/**
	 * Get dynamic campaign target
	 * 
	 * If campaign target unit is percentage, it is already form of dynamic
	 * 
	 * @param user
	 * @param campaign
	 * @param campaignTarget
	 * @return dynamic campaign target
	 */
	private int dynamicCampaignTargetHelper(User user, Campaign campaign, CampaignTarget campaignTarget){
		int dynamicTarget = 0;
		
		/*
		 * If campaign target unit is percentage... then it is already dynamic campaign target... send original campaign target
		 */
		if (campaignTarget.getUnit().getCode().equals(AppConstants.UNIT_PERCENTAGE)){
			return campaignTarget.getTarget();
		}
		
		if (campaign.getCampaignType().getType().equals(AppConstants.CAMPAIGN_TYPE_GROUP)){
			/*
			 * Group Dynamic Campaign Target Section
			 */
			/*
			 * Get campaign Group...
			 */
			List<Groups> groups = groupsDao.getGroupsBySegment(user.getId(), campaign.getSegment().getId());
			for (Groups group : groups){
				/*
				 * For each group get all members
				 */
				List<User> membersInGroup = accountGroupDao.getAccountFromGroups(group.getId());
				if (!membersInGroup.isEmpty()){
					for (User member : membersInGroup){
						dynamicTarget += individualCampaignTarget(member, campaign, campaignTarget);
						System.out.println(dynamicTarget + " : ");
					}
				}
			}
			
		}else{
			dynamicTarget = individualCampaignTarget(user, campaign, campaignTarget);
			
		}
		
		return dynamicTarget;
	}
	
	/**
	 * Get individual dynamic campaign target
	 * 
	 * @param user
	 * @param campaign
	 * @param campaignTarget
	 * @return the target with campaign target plus delta
	 */
	private int individualCampaignTarget(User user, Campaign campaign, CampaignTarget campaignTarget){
		int dynamicTarget = 0;
		/*
		 * Indiviudal Dynamic Campaign Target Section
		 */
		Long sourceId = getMemberSource(user.getId(), campaign.getCampaignTemplate().getSourceType().getCode());
		//Get baseline for source type
		BaselineView baseline = getMemberBaseline(sourceId, campaign.getCampaignTemplate().getSourceType().getCode());
		
		if (campaignTarget.getUnit().getCode().equals(AppConstants.UNIT_METER)){
			double distance = baseline.getDistance();
			if (distance == 0){
				/*
				 * If there is not baseline... Camapign Target is the default baseline
				 */
				distance = campaignTarget.getBaseline();
			}
			dynamicTarget = (int)(double)((1 + campaignTarget.getTarget()/100.0) * distance);
			
		}else if (campaignTarget.getUnit().getCode().equals(AppConstants.UNIT_KILOMETER)){
			double distance = baseline.getDistance()/1000.0;
			if (distance == 0){
				distance = campaignTarget.getBaseline();
			}
			
			dynamicTarget = (int)(double)((1 + campaignTarget.getTarget()/100.0) * distance);
			
		}else if (campaignTarget.getUnit().getCode().equals(AppConstants.UNIT_MINUTE)){
			
			double duration = baseline.getDuration() / 60.0;
			/*
			 * Baseline duration is in seconds so we need to convert it to minute
			 */
			if (duration == 0){
				duration = campaignTarget.getBaseline();
			}
			dynamicTarget = (int)Math.round((1 + campaignTarget.getTarget()/100.0) * duration);
			
		}else if (campaignTarget.getUnit().getCode().equals(AppConstants.UNIT_STEPS)){
			int steps = baseline.getSteps();
			
			if (steps == 0){
				steps = campaignTarget.getBaseline();
			}
			dynamicTarget = (int)(double)((1 + campaignTarget.getTarget()/100.0) * steps);
			
		}else if (campaignTarget.getUnit().getCode().equals(AppConstants.UNIT_CALORIES)){
			int calories = baseline.getCalories();
			if (calories == 0){
				calories = campaignTarget.getBaseline();
			}
			dynamicTarget = (int)(double)((1 + campaignTarget.getTarget()/100.0) * calories);
			
		}
		
		return dynamicTarget;
				
	}
	private Long getMemberSource(Long memberId, String sourceTypeCode){
		Long sourceId = null;
		List<Source> sources = null;
		sources = userDao.findDefaultSourceByAccountAndType(memberId, sourceTypeCode);
		
		/*
		 * If there is no default source... fetch all non default source
		 */
		if (sources.isEmpty()){
			sources = userDao.findSourceByAccountAndType(memberId, sourceTypeCode);
		}
		for (Source source : sources){
			
			if (source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				sourceId = source.getId();
				break;
			}
		}
		
		return sourceId;
	}
	
	/**
	 * Get member's baseline for a given source type
	 * 
	 * @param sourceId
	 * @param sourceTypeCode
	 * @return baseline view
	 */
	private BaselineView getMemberBaseline(Long sourceId, String sourceTypeCode){
		Source source = sourceDao.findById(sourceId);
		BaselineView baselineView = new BaselineView();
		
		if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_WALKING)){
			WalkingDayAverage walkingBaseline = personalMotionDao.getWalkingDayAverage(source.getId());
			if (walkingBaseline != null){
				baselineView.setCalories(walkingBaseline.getCalories());
				baselineView.setDistance(walkingBaseline.getDistance());
				baselineView.setDuration(walkingBaseline.getDuration());
				baselineView.setSteps(walkingBaseline.getSteps());
			}else{
				/*
				 * If there is no baseline...set it to 0
				 */
				baselineView.setCalories(0);
				baselineView.setDistance(0.0);
				baselineView.setDuration(0.0);
				baselineView.setSteps(0);
			}
		}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_RUNNING)){
			RunningDataDayAverage runningBaseline = personalMotionDao.getRunningDayAverage(source.getId());
			if (runningBaseline != null){
				baselineView.setCalories(runningBaseline.getCalories());
				baselineView.setDistance(runningBaseline.getDistance());
				baselineView.setDuration(runningBaseline.getDuration());
				baselineView.setSteps(runningBaseline.getSteps());
			}else{
				/*
				 * If there is no baseline...set it to 0
				 */
				baselineView.setCalories(0);
				baselineView.setDistance(0.0);
				baselineView.setDuration(0.0);
				baselineView.setSteps(0);
			}
		}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)){
			CyclingDataDayAverage cyclingBaseline = personalMotionDao.getCyclingDayAverage(source.getId());
			if (cyclingBaseline != null){
				baselineView.setCalories(cyclingBaseline.getCalories());
				baselineView.setDistance(cyclingBaseline.getDistance());
				baselineView.setDuration(cyclingBaseline.getDuration());
			}else{
				/*
				 * If there is no baseline...set it to 0
				 */
				baselineView.setCalories(0);
				baselineView.setDistance(0.0);
				baselineView.setDuration(0.0);
			}
		}else{
			/*
			 * If there is no baseline...set it to 0
			 */
			baselineView.setCalories(0);
			baselineView.setDistance(0.0);
			baselineView.setDuration(0.0);
			baselineView.setSteps(0);
		}
	
		return baselineView;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CampaignDetailView getFundraisedCampaignDetail(Long accountCampaignId){
		CampaignDetailView pdv = new CampaignDetailView();
		
		AccountCampaign ap = accountCampaignDao.getAccountCampaign(accountCampaignId);
		
		if (ap != null){
			User user = ap.getUser();
			if (user != null){
				if (user.getFirstName() != null && user.getLastName() != null) pdv.setOwnerName(user.getFirstName() + " " + user.getLastName());
				else pdv.setOwnerName(user.getEmail());
			}
			Campaign campaign = ap.getCampaign();
			if ( campaign != null){
				pdv.setTitle(TextUtil.parseString(campaign.getName()));
				pdv.setDescription(TextUtil.parseString(campaign.getDescription()));
				//image
				pdv.setImageURL(ImageUtil.parseImageUrl(campaign.getCampaignTemplate().getImageURL()));
				//type
				if (campaign.getCampaignTemplate().getCampaignTemplateType().getMode() != null){
					pdv.setType(campaign.getCampaignTemplate().getCampaignTemplateType().getMode());
				}else{
					pdv.setType("");
				}
				
				//progressbar value
				Date currentDate = new Date();
				
				if (campaign.getEndDate().getTime() > currentDate.getTime()){
					Integer days = 0;
					//campaign hasn't start yet
					if (campaign.getStartDate().getTime() > currentDate.getTime()){
						days = Days.daysBetween(new DateTime(campaign.getStartDate()), new DateTime(campaign.getEndDate())).getDays();
					}else{
						days = Days.daysBetween(new DateTime(currentDate), new DateTime(campaign.getEndDate())).getDays();
					}
					days++;
					if (days > 0){
						pdv.setTimeRemaining(days);
						pdv.setTimeRemainingUnit("days");
					}else{
						Integer hours = Hours.hoursBetween(new DateTime(currentDate), new DateTime(campaign.getEndDate())).getHours();
						if (hours > 0){
							pdv.setTimeRemaining(hours);
							pdv.setTimeRemainingUnit("hours");
						}else{
							Integer minutes = Minutes.minutesBetween(new DateTime(currentDate), new DateTime(campaign.getEndDate())).getMinutes();
							pdv.setTimeRemaining(minutes);
							pdv.setTimeRemainingUnit("minutes");
						}
						
					}
				}else{
					pdv.setTimeRemaining(0);
					pdv.setTimeRemainingUnit("days");
				}
				
				//status
				pdv.setStatus(campaign.getStatus().getName());
				
				//time
				int target = 0;
				int fundraisedInt = 0;
				Double fundraised = fundraiseDao.getFundraisedValue(accountCampaignId);
				if (fundraised != null) fundraisedInt = fundraised.intValue();
				else fundraisedInt = 0;
				
				
				pdv.setTarget(target);
				pdv.setProgress(fundraisedInt);
				
				//Calculate Progress
				Double progress = progressDate(campaign.getStartDate(), campaign.getEndDate());
				pdv.setTimeProgress(progress);
				
				Integer progressValue = (int)Math.round((double)fundraisedInt / (double)target * 100);
				if (progressValue > 100) progressValue =100;
				pdv.setProgressbarValue(progressValue);
			}
			
			
		}
		
		
		
		return pdv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public MobileLeaderboardView getLeaderboardDataMobile(Long campaignId, Long accountId, Integer topNumber) {
		
		MobileLeaderboardView mlv = new MobileLeaderboardView();
		//We are in Campaign Leaderboard Web Services
		List<LeaderboardView> lvs = new ArrayList<LeaderboardView>();
		//Fetch all users with campaign id == campaignId
		Long userId = accountId;
		
		boolean sortByLower = false;
		//Check if campaign target condition...
		CampaignTarget ct = campaignDao.getCampaignTarget(campaignId);
		
		if (ct != null){
			if (ct.getRuleCondition() != null && (ct.getRuleCondition().getCondition().equals("<") || ct.getRuleCondition().getCondition().equals("<="))){
				sortByLower = true;
			}
		}
		
		Campaign campaign = campaignDao.findById(campaignId);
		Integer iteration = 1;
		if (campaign.getCurrentFrequencyStage() != null) iteration = campaign.getCurrentFrequencyStage();
		
		if (campaign.getCampaignType().getType().equals(AppConstants.CAMPAIGN_TYPE_GROUP)){
			List<Groups> groups = campaignDao.getGroupsInCampagin(campaignId);
			if (groups != null){
				Integer greenZone =0, yellowzone =0;
				
				if (groups.size() == 1){
					greenZone = 0;
				}else if (groups.size() == 2){
					greenZone = 0;
					yellowzone = 1;
				}else{
					greenZone = groups.size()/3;
					yellowzone = 2 * groups.size()/3;
				}
				
				LeaderboardView leaderboardView = null;
				//Sort user with account campaign progress
				for (Groups group : groups){
					leaderboardView = new LeaderboardView();
					leaderboardView.setAccountId(group.getId());
					leaderboardView.setSourceId(group.getId());
					
					leaderboardView.setSourceFirstName(group.getName());
					leaderboardView.setSourceLastName("");
					
					/*
					 * For each group get target for everyone
					 */
					if (ct.getCampaignTargetMode().getType().equals(AppConstants.CAMPAIGN_TARGET_MODE_DYNAMIC)){
						int dynamicTarget = 0;
						List<User> membersInGroup = accountGroupDao.getAccountFromGroups(group.getId());
						/*
						 * Get individual campaign target and sum it up to get group campaign target
						 */
						if (!membersInGroup.isEmpty()){
							for (User member : membersInGroup){
								dynamicTarget += individualCampaignTarget(member, campaign, ct);
							}
						}
						leaderboardView.setTarget(dynamicTarget);
					}else{
						leaderboardView.setTarget(ct.getTarget());
					}
					
					List<CampaignGroupProgress> app = userDao.getCampaignGroupProgresses(campaignId, group.getId(), iteration);
					if (app != null && app.size() > 0 && app.get(0).getProgressValue() > 0){
						leaderboardView.setSortingValue(app.get(0).getProgressValue());
						if (app.get(0).getProgressValue() > 50){
							leaderboardView.setValue((double)Math.round(app.get(0).getProgressValue()));
						}else{
							leaderboardView.setValue((double)Math.round(app.get(0).getProgressValue()* 10.0)/10.0);
						}
						
					}else{
						if (!app.isEmpty()) leaderboardView.setValue(app.get(0).getProgressValue());
						else leaderboardView.setValue(0.0);
						
						if (sortByLower){
							leaderboardView.setSortingValue(Double.MAX_VALUE);
						}else{
							leaderboardView.setSortingValue(Double.MIN_VALUE);
						}
						
					}
					//System.out.println("Source NAME : " + leaderboardView.getSourceFirstName() + " : value : " + leaderboardView.getValue());
					lvs.add(leaderboardView);
				}
				
				//sort leaderboard view to lowest value
				Comparator<LeaderboardView> SortLowestComparator = new Comparator<LeaderboardView>(){
					public int compare(LeaderboardView lv1, LeaderboardView lv2){
						if (lv1.getSortingValue() < lv2.getSortingValue()) return -1;
						else if (lv1.getSortingValue() > lv2.getSortingValue()) return 1;
						else return 0;
					}
				};
				
				//sort leaderboard view to highest value
				Comparator<LeaderboardView> SortHighestComparator = new Comparator<LeaderboardView>(){
					public int compare(LeaderboardView lv1, LeaderboardView lv2){
						if (lv1.getSortingValue() < lv2.getSortingValue()) return 1;
						else if (lv1.getSortingValue() > lv2.getSortingValue()) return -1;
						else return 0;
					}
				};
				
				if (sortByLower){
					Collections.sort(lvs, SortLowestComparator);
				
				}else{
					Collections.sort(lvs, SortHighestComparator);
					
				}
				
				List<LeaderboardView> orderedLvs = new ArrayList<LeaderboardView>(); 
				
				/*
				 * XXX:Get my groups in campaign
				 */
				List<Groups> myGroups = groupsDao.getGroups(accountId);
				if (!myGroups.isEmpty()){
					List<Groups> myCampaignGroups = groupsDao.getGroupsWithCampaign(myGroups, campaign.getId());
					
					int remaining = topNumber -1 - myCampaignGroups.size();
					
					for (int i = 0; i<lvs.size(); i++){
						leaderboardView = lvs.get(i);
						//Pass the 10th element if a user is not found yet
						if (!validateGroupId(leaderboardView.getSourceId(), myCampaignGroups) && i > remaining){
							continue;
						}
						
						//Each source get Id and name and value
						if (i <= greenZone){
							leaderboardView.setRank(1);
						}else if (i <= yellowzone){
							leaderboardView.setRank(2);
						}else{
							leaderboardView.setRank(3);
						}
						
						leaderboardView.setRanking(i+1);
						
						orderedLvs.add(leaderboardView);
						
						if (myCampaignGroups.size() == 0){
							if (i >= topNumber-1){
								break;
							}
						}
						
					}
				}
				
				
				lvs = orderedLvs;
				
			}
		}else{
			List<User> users = campaignDao.getUsersInCampagin(campaignId);
			if ( users != null){
				Integer greenZone =0, yellowzone =0;
				if (users.size() == 1){
					greenZone = 0;
				}else if (users.size() == 2){
					greenZone = 0;
					yellowzone = 1;
				}else{
					greenZone = users.size()/3;
					yellowzone = 2 * users.size()/3;
				}
				
				
				//Sort user with account campaign progress
				
				for (User u : users){
					LeaderboardView leaderboardView = new LeaderboardView();
					leaderboardView.setAccountId(u.getId());
					leaderboardView.setSourceId(u.getId());
					if (u.getFirstName() != null && u.getLastName() != null){
						leaderboardView.setSourceFirstName(u.getFirstName());
						leaderboardView.setSourceLastName(u.getLastName());
					}else{
						leaderboardView.setSourceFirstName(u.getEmail());
						leaderboardView.setSourceLastName("");
					}
					
					/*
					 * For each user... Get Target
					 */
					if (ct.getCampaignTargetMode().getType().equals(AppConstants.CAMPAIGN_TARGET_MODE_DYNAMIC)){
						
						/*
						 * Individual dynamic campaign
						 */
						leaderboardView.setTarget(individualCampaignTarget(u, campaign, ct));
						
					}else{
						leaderboardView.setTarget(ct.getTarget());
					}
					
					List<CampaignProgress> app = userDao.getCampaignAccountProgress(campaignId, u.getId(), iteration);
					if (app != null && app.size() > 0 && app.get(0).getProgressValue() > 0 ){
						leaderboardView.setSortingValue(app.get(0).getProgressValue());
						if (app.get(0).getProgressValue() > 50){
							leaderboardView.setValue((double)Math.round(app.get(0).getProgressValue()));
						}else{
							leaderboardView.setValue((double)Math.round(app.get(0).getProgressValue()* 10.0)/10.0);
						}
						
					}else{
						if (!app.isEmpty()) leaderboardView.setValue(app.get(0).getProgressValue());
						else leaderboardView.setValue(0.0);
						
						if (sortByLower){
							leaderboardView.setSortingValue(Double.MAX_VALUE);
						}else{
							leaderboardView.setSortingValue(Double.MIN_VALUE);
						}
					}
					lvs.add(leaderboardView);
				}
				
				//sort leaderboard view to lowest value
				Comparator<LeaderboardView> SortLowestComparator = new Comparator<LeaderboardView>(){
					public int compare(LeaderboardView lv1, LeaderboardView lv2){
						if (lv1.getSortingValue() < lv2.getSortingValue()) return -1;
						else if (lv1.getSortingValue() > lv2.getSortingValue()) return 1;
						else return 0;
					}
				};
				
				//sort leaderboard view to highest value
				Comparator<LeaderboardView> SortHighestComparator = new Comparator<LeaderboardView>(){
					public int compare(LeaderboardView lv1, LeaderboardView lv2){
						if (lv1.getSortingValue() < lv2.getSortingValue()) return 1;
						else if (lv1.getSortingValue() > lv2.getSortingValue()) return -1;
						else return 0;
					}
				};
				
				if (sortByLower){
					Collections.sort(lvs, SortLowestComparator);
				
				}else{
					Collections.sort(lvs, SortHighestComparator);
					
				}
				
				List<LeaderboardView> orderedLvs = new ArrayList<LeaderboardView>(); 
				boolean isUserIncluded= false;
				for (int i = 0; i<lvs.size(); i++){
					LeaderboardView leaderboardView = lvs.get(i);
					//Pass the 10th element if a user is not found yet
					if (!isUserIncluded && !leaderboardView.getSourceId().equals(userId) && i >(topNumber-2)){
						continue;
					}
					
					//Each source get Id and name and value
					if (i <= greenZone){
						leaderboardView.setRank(1);
					}else if (i <= yellowzone){
						leaderboardView.setRank(2);
					}else{
						leaderboardView.setRank(3);
					}
					
					leaderboardView.setRanking(i+1);
					
					orderedLvs.add(leaderboardView);
					
					if (leaderboardView.getSourceId().equals(userId)){
						isUserIncluded = true;
						System.out.println("Found the user !!!!!!");
					}
					
					if (isUserIncluded){
						if (i >= (topNumber-1)){
							break;
						}
					}
					
				}
				
				lvs = orderedLvs;
			}
		}
		//Target
		CampaignTarget campaignTarget = campaignDao.getCampaignTarget(campaignId);
		if (campaignTarget != null) mlv.setTarget(campaignTarget.getTarget());
		else mlv.setTarget(0);
		
		String unit = campaign.getCampaignTemplate().getSourceType().getStandardUnit();
		
		if (ct != null){
			if (ct.getUnit() != null){
				unit = ct.getUnit().getDescription();
			}
		}
		mlv.setTargetUnit(unit);
		
		mlv.setLbvs(lvs);
		
		return mlv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CampaignDetailView getCampaignProgressMobile(Long campaignId, String communityCode, Long accountId){
		Long communityId = communityDao.getCommunityId(communityCode);
		CampaignDetailView pdv = new CampaignDetailView();
		Campaign campaign = campaignDao.getCampaign(campaignId);
		
		if (campaign != null){
			CampaignTarget campaignTarget = campaignDao.getCampaignTarget(campaignId);
			if (campaign.getSmallImageUrl() == null || campaign.getSmallImageUrl().equals("")) pdv.setImageURL(ImageUtil.parseImageUrl(campaign.getCampaignTemplate().getImageURL()));
			else pdv.setImageURL(ImageUtil.parseImageUrl(campaign.getSmallImageUrl()));
			
			pdv.setTitle(TextUtil.parseHTMLString(campaign.getName()));
			pdv.setDescription(TextUtil.parseHTMLString(campaign.getDescription()));
			
			String unit = campaign.getCampaignTemplate().getSourceType().getStandardUnit();
			
			if (campaignTarget != null){
				pdv.setTarget(campaignTarget.getTarget());
				if (campaignTarget.getUnit() != null) unit = campaignTarget.getUnit().getDescription();
				if (campaignTarget.getCampaignTargetType() != null) pdv.setTemplateType(campaignTarget.getCampaignTargetType().getName());
				else pdv.setTemplateType(AppConstants.CAMPAIGN_TEMPLATE_TYPE_DEFAULT);
				
			}else{
				pdv.setTarget(0);
				pdv.setTemplateType(AppConstants.CAMPAIGN_TEMPLATE_TYPE_DEFAULT);
			}
			
			pdv.setTargetUnit(unit);
			
			if (campaign.getCampaignTemplate().getCampaignTemplateType().getMode().equals(AppConstants.CAMPAIGN_TEMPLATE_TYPE_COMPETITION)){
				pdv.setIsCompetition(true);
			}else{
				pdv.setIsCompetition(false);
			}
			
			//Check if 
			if (campaign.getCampaignTemplate().getCampaignTemplateType().getType().equals(AppConstants.CAMPAIGN_TEMPLATE_TYPE_SPONSORED)){
				pdv.setIsFundraising(true);
			}else{
				pdv.setIsFundraising(false);
			}
			
			if (campaign.getCampaignTemplate().getSourceType() != null){
				pdv.setSourceTypeName(campaign.getCampaignTemplate().getSourceType().getName());
				pdv.setSourceTypeImage(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getCampaignTemplate().getSourceType().getImageURL()));
				
				//Get Source Type Unit
				SourceUnit sourceUnit = sourceDao.findSourceUnitByType(campaign.getCampaignTemplate().getSourceType().getCode());
				
				String targetUnit = sourceUnit.getUnit().getName();
				
				if (campaignTarget.getUnit() != null){
					targetUnit = campaignTarget.getUnit().getName();
				}
				
				pdv.setSourceTypeUnit(targetUnit);
			}
			
			
			SimpleDateFormat df3 = new SimpleDateFormat("MMM dd");
			Date campaignStartDate = null;
			Date campaignEndDate = null;
			
			if (campaign.getFrequency() != null && campaign.getFrequency()  > 1){
				campaignStartDate = DateUtil.getContinousStartDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
				campaignEndDate = DateUtil.getContinousEndDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
				pdv.setStartDate(df3.format(campaignStartDate).toUpperCase());
				pdv.setEndDate(df3.format(campaignEndDate).toUpperCase());
			}else{
				campaignStartDate = campaign.getStartDate();
				campaignEndDate = campaign.getEndDate();
				pdv.setStartDate(df3.format(campaignStartDate).toUpperCase());
				pdv.setEndDate(df3.format(campaignEndDate).toUpperCase());
			}
			
			//Calculate Remaining in number and unit
			Date currentDate = new Date();
			//First check if we can calculate by days
			
			//Calculate progress bar value
			Integer iteration = 1;
			if (campaign.getCurrentFrequencyStage() != null) iteration = campaign.getCurrentFrequencyStage();
			
				List<CampaignProgress> paps = campaignDao.getCampaignAccountProgress(campaign.getId(), accountId, iteration);
				if (paps != null && paps.size() > 0){
					CampaignProgress pap = new CampaignProgress();
					if (paps.size()>0){
						pap = paps.get(0);
						Double accountProgress = pap.getProgressValue();
						pdv.setProgress((int)Math.round(accountProgress));
					}else{
						pdv.setProgress(0);
					}
					
				}
				else{
					pdv.setProgress(0);
				}
			
				if (campaignEndDate.getTime() > currentDate.getTime()){
					
					Integer days = 0;
					//Campaign hasn't start yet
					if (campaignStartDate.getTime() > currentDate.getTime()){
						days = Days.daysBetween(new DateTime(campaignStartDate), new DateTime(campaignEndDate)).getDays();
					}else{
						days = Days.daysBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getDays();
					}
					days++;	
					if (days > 0){
						pdv.setTimeRemaining(days);
						pdv.setTimeRemainingUnit("days");
					}else{
						Integer hours = Hours.hoursBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getHours();
						if (hours > 0){
							pdv.setTimeRemaining(hours);
							pdv.setTimeRemainingUnit("hours");
						}else{
							Integer minutes = Minutes.minutesBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getMinutes();
							pdv.setTimeRemaining(minutes);
							pdv.setTimeRemainingUnit("minutes");
						}
						
					}
				}else{
					pdv.setTimeRemaining(0);
					pdv.setTimeRemainingUnit("days");
				}
				
				//Calculate Progress
				Double progress = progressDate(campaignStartDate, campaignEndDate);
				System.out.println("Time Progress Bar " + progress);
				pdv.setTimeProgress(progress);
			
			//Get Offer from offerId
			try{
				List<Offer> offers = getCampaignOffer(campaign.getId(), communityId, campaign.getEndDate(), accountId);
				if (offers != null){
					
					Integer offerPoints = 0;
					if (offers != null && offers.size() > 0){
						if (campaign.getStatus().getCode().equals(AppConstants.STATUS_COMPLETED)){
							for(Offer offer : offers){
								System.out.println(offer.getName());
								if (isMemberActivityProcessed(accountId, campaign.getId(), offer.getCode())){
									offerPoints += offer.getValue().intValue();
								}
							}
							
						}else{
							for (Offer offer : offers){
								offerPoints += offer.getValue().intValue();
							}
						}
					}
					pdv.setOfferValue(offerPoints.toString());
				}else{
					pdv.setOfferValue("0");
				}
				
			}catch(Exception e){
				pdv.setOfferValue("0");
			}
			
			//pdv.setActions(getActions(campaign));
			//pdv.setFacts(getFacts(campaign));
			
			pdv.setStatus(campaign.getStatus().getCode());
		}
		
		return pdv;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CampaignDetailView getCampaignDetail(Long campaignId, Long accountId, Integer iteration) {
		
		CampaignDetailView pdv = new CampaignDetailView();
		
		if (accountId == null || campaignId == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			pdv.setResult(rv);
			return pdv;
		}
		
		User user = userDao.findById(accountId);
		if (user == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid member");
			pdv.setResult(rv);
			return pdv;
		}
		
		Campaign campaign = campaignDao.getCampaign(campaignId);
		if (campaign == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid campaign");
			pdv.setResult(rv);
			return pdv;
		}
		
		List<String> electricityCodes = new ArrayList<String>(Arrays.asList("ELECTRICITY", "ELECTRICITY_IN_PERCENTAGE", "ELECTRICITY_PERCENTAGE_USAGE_DURING_OFF_PEAK"));
		List<String> walkingCodes = new ArrayList<String>(Arrays.asList("WALKING", "WALKING_IN_DISTANCE", "CALORIES"));
		
		if (campaign != null){
			
			pdv.setCampaignId(campaignId);
			
			String sourceType = "";
			if (electricityCodes.contains(campaign.getCampaignTemplate().getSourceType().getCode())) sourceType = "ELECTRICITY";
			else if (walkingCodes.contains(campaign.getCampaignTemplate().getSourceType().getCode())) sourceType = "WALKING";
			else if (campaign.getCampaignTemplate().getSourceType().getCode().contains(AppConstants.SOURCETYPE_CODE_RUNNING)) sourceType = AppConstants.SOURCETYPE_CODE_RUNNING;
			else if (campaign.getCampaignTemplate().getSourceType().getCode().contains(AppConstants.SOURCETYPE_CODE_CYCLING)) sourceType = AppConstants.SOURCETYPE_CODE_CYCLING;
			else sourceType = campaign.getCampaignTemplate().getSourceType().getCode();
			
			pdv.setSourceType(sourceType);
			
			CampaignTarget campaignTarget = campaignDao.getCampaignTarget(campaignId);
			if (campaign.getSmallImageUrl() == null || campaign.getSmallImageUrl().equals("")) pdv.setImageURL(ImageUtil.parseImageUrl(campaign.getCampaignTemplate().getImageURL()));
			else pdv.setImageURL(ImageUtil.parseImageUrl(campaign.getSmallImageUrl()));
			
			pdv.setTitle(TextUtil.parseHTMLString(campaign.getName()));
			pdv.setDescription(TextUtil.parseHTMLString(campaign.getDescription()));
			pdv.setLongDescription(TextUtil.parseHTMLString(campaign.getLongDescription()));
			
			if (campaign.getFrequency() != null && campaign.getFrequency() > 1) pdv.setIsContinuous(true);
			else pdv.setIsContinuous(false);
			
			if (campaignTarget != null){
				pdv.setTarget(campaignTarget.getTarget());
				if (campaignTarget.getCampaignTargetType() != null) pdv.setTemplateType(campaignTarget.getCampaignTargetType().getName());
				else pdv.setTemplateType(AppConstants.CAMPAIGN_TEMPLATE_TYPE_DEFAULT);
			}else{
				pdv.setTarget(0);
				pdv.setTemplateType(AppConstants.CAMPAIGN_TEMPLATE_TYPE_DEFAULT);
			}
			
			//Get Source Type Unit
			SourceUnit sourceUnit = sourceDao.findSourceUnitByType(sourceType);
			
			String targetUnit = sourceUnit.getUnit().getName();
			
			if (campaignTarget.getUnit() != null){
				targetUnit = campaignTarget.getUnit().getName();
			}
			pdv.setTargetUnit(targetUnit);
			
			if (campaign.getCampaignTemplate().getCampaignTemplateType().getMode().toUpperCase().equals(AppConstants.CAMPAIGN_TEMPLATE_TYPE_COMPETITION)){
				pdv.setIsCompetition(true);
			}else{
				pdv.setIsCompetition(false);
			}
			
			//Check if 
			if (campaign.getCampaignTemplate().getCampaignTemplateType().getType().equals(AppConstants.CAMPAIGN_TEMPLATE_TYPE_SPONSORED)){
				pdv.setIsFundraising(true);
			}else{
				pdv.setIsFundraising(false);
			}
			
			if (campaign.getCampaignTemplate().getSourceType() != null){
				pdv.setSourceTypeName(campaign.getCampaignTemplate().getSourceType().getName());
				pdv.setSourceTypeImage(ImageUtil.parseImageUrl(campaign.getCampaignTemplate().getSourceType().getImageURL()));
				
				pdv.setSourceTypeUnit(targetUnit);
				
			}
				
			
			SimpleDateFormat df3 = new SimpleDateFormat("dd MMM yyyy");
			Date campaignStartDate = null;
			Date campaignEndDate = null;
			
			if (campaign.getFrequency() != null && campaign.getFrequency()  > 1){
				campaignStartDate = DateUtil.getContinousStartDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
				campaignEndDate = DateUtil.getContinousEndDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
				pdv.setStartDate(df3.format(campaignStartDate).toUpperCase());
				pdv.setEndDate(df3.format(campaignEndDate).toUpperCase());
				pdv.setFrequency(campaign.getFrequency());
			}else{
				campaignStartDate = campaign.getStartDate();
				campaignEndDate = campaign.getEndDate();
				pdv.setStartDate(df3.format(campaignStartDate).toUpperCase());
				pdv.setEndDate(df3.format(campaignEndDate).toUpperCase());
				pdv.setFrequency(1);
			}
			
			if (campaign.getCurrentFrequencyStage() != null && campaign.getCurrentFrequencyStage() > 1) {
				pdv.setCurrentIteration(campaign.getCurrentFrequencyStage());
			}else{
				pdv.setCurrentIteration(1);
			}
			
			//Calculate Remaining in number and unit
			Date currentDate = new Date();
			//First check if we can calculate by days
			
			//Calculate progress bar value
			if (iteration == null) iteration = 1;
			if (campaign.getCurrentFrequencyStage() != null) iteration = campaign.getCurrentFrequencyStage();
			
			if (campaign.getCampaignType().getType().equals(AppConstants.CAMPAIGN_TYPE_GROUP)){
				List<Groups> myGroups = groupsDao.getGroups(accountId);
				if (!myGroups.isEmpty()){
					List<Groups> myCampaignGroups = groupsDao.getGroupsWithCampaign(myGroups, campaign.getId());
					
					if (!myCampaignGroups.isEmpty()){
						
						pdv.setGroupId(myCampaignGroups.get(0).getId());
						List<CampaignGroupProgress> paps = campaignDao.getCampaignGroupProgresses(campaignId, myCampaignGroups.get(0).getId(), iteration);
						CampaignGroupProgress pap = new CampaignGroupProgress();
						if (!paps.isEmpty()){
							if (paps.size()>0){
								pap = paps.get(0);
								Double accountProgress = pap.getProgressValue();
								pdv.setProgress((int)Math.round(accountProgress));
							}else{
								pdv.setProgress(0);
							}
							
						}
					}else{
						pdv.setProgress(0);
					}
					
				}
				
				
			}else{
				List<CampaignProgress> paps = campaignDao.getCampaignAccountProgress(campaign.getId(), accountId, iteration);
				if (paps != null && paps.size() > 0){
					CampaignProgress pap = new CampaignProgress();
					if (paps.size()>0){
						pap = paps.get(0);
						Double accountProgress = pap.getProgressValue();
						pdv.setProgress((int)Math.round(accountProgress));
					}else{
						pdv.setProgress(0);
					}
					
				}
				else{
					pdv.setProgress(0);
				}
			}
			
			
			if (campaignEndDate.getTime() > currentDate.getTime()){
				
				Integer days = 0;
				//Campaign hasn't start yet
				if (campaignStartDate.getTime() > currentDate.getTime()){
					days = Days.daysBetween(new DateTime(campaignStartDate), new DateTime(campaignEndDate)).getDays();
				}else{
					days = Days.daysBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getDays();
				}
				days++;	
				if (days > 0){
					pdv.setTimeRemaining(days);
					pdv.setTimeRemainingUnit("days");
				}else{
					Integer hours = Hours.hoursBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getHours();
					if (hours > 0){
						pdv.setTimeRemaining(hours);
						pdv.setTimeRemainingUnit("hours");
					}else{
						Integer minutes = Minutes.minutesBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getMinutes();
						pdv.setTimeRemaining(minutes);
						pdv.setTimeRemainingUnit("minutes");
					}
					
				}
			}else{
				pdv.setTimeRemaining(0);
				pdv.setTimeRemainingUnit("days");
			}
			
			//Calculate Progress
			Double progress = progressDate(campaignStartDate, campaignEndDate);
			System.out.println("Time Progress Bar " + progress);
			pdv.setTimeProgress(progress);
			
			//Get Offer from offerId
			try{
				List<Offer> offers = getCampaignOffer(campaign.getId(), user.getDefaultCommunity(), campaign.getEndDate(), accountId);
				if (offers != null){
					
					List<OfferView> offerViews = new ArrayList<OfferView>();
					Integer offerPoints = 0;
					if (offers != null && offers.size() > 0){
						if (campaign.getStatus().getCode().equals(AppConstants.STATUS_COMPLETED)){
							for(Offer offer : offers){
								System.out.println(offer.getName());
								if (isMemberActivityProcessed(accountId, campaign.getId(), offer.getCode())){
									offerPoints += offer.getValue().intValue();
								}
							}
							
						}else{
							for (Offer offer : offers){
								offerPoints += offer.getValue().intValue();
								OfferView ov = new OfferView(offer);
								offerViews.add(ov);
							}
							
							
						}
					}
					
					pdv.setOfferviews(offerViews);
					pdv.setOfferValue(offerPoints.toString());
					pdv.setOfferImageUrl("/portal-core/images/common/gift-icon.png");
				}else{
					pdv.setOfferValue("0");
					pdv.setOfferImageUrl("/portal-core/images/common/gift-icon.png");
				}
				
			}catch(Exception e){
				e.printStackTrace();
				pdv.setOfferValue("0");
				pdv.setOfferImageUrl("/portal-core/images/common/gift-icon.png");
			}
			
			//pdv.setActions(getActions(campaign));
			//pdv.setFacts(getFacts(campaign));
			
			pdv.setStatus(campaign.getStatus().getCode());
		}
		
		return pdv;
	}
	
	private List<CampaignFact> getFacts(Campaign campaign){
		List<CampaignFact> pfs = new ArrayList<CampaignFact>();
		
		try{
			List<CampaignFactList> pfls = campaignDao.getCampaignFactList(campaign.getId());
		
		
			for (CampaignFactList pfl : pfls){
				pfl.getCampaignFact().setDescription(TextUtil.parseString(pfl.getCampaignFact().getDescription()));
				pfs.add(pfl.getCampaignFact());
			}
			
			return pfs;
		}catch (Exception e){
			return pfs;
		}
	}
	private List<CampaignAction> getActions(Campaign campaign){
		List<CampaignAction> pas = new ArrayList<CampaignAction>();
		try{
			
			List<CampaignActionList> pals = campaignDao.getCampaignActionList(campaign.getId());
			
			
			for (CampaignActionList pal : pals){
				pal.getCampaignAction().setDescription(TextUtil.parseString(pal.getCampaignAction().getDescription()));
				pas.add(pal.getCampaignAction());
			}
			return pas;
		}catch (Exception e){
			e.printStackTrace();
			return pas;
		}
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<CampaignView> getNonUserCampaignsMobile(String communitycode, Long memberId, Long campaignTypeMode){
		Long communityId = communityDao.getCommunityId(communitycode);
		System.out.println("community ID :: " + communityId);
		
		List<CampaignView> campaignViews = new ArrayList<CampaignView>();
		
		List<Category> categories = new ArrayList<Category>();
		if (campaignTypeMode == 0){
			categories = categoryDao.getCategories(AppConstants.CATEGORYTYPE_CAMPAIGN);
		}else{
			categories.add(categoryDao.getCategoryByType(campaignTypeMode));
		}
		
		try{
			List<Campaign> userCampaigns = new ArrayList<Campaign>();
			List<Campaign> campaigns = new ArrayList<Campaign>();
			//Get segments of accounts
			List<Segment> segments = segmentService.getMemberSegment(memberId);
			
			/*
			 * Get member group segment...
			 */
			if (categories != null && categories.size() > 0){
				long categoryId;
				for (Category category : categories){
					categoryId = category.getId();
					userCampaigns.addAll(userDao.getUserActiveCampaigns(memberId, categoryId));
					/*
					 * Add group campaigns
					 */
					List<Groups> groups = groupsDao.getGroups(memberId);
					if (!groups.isEmpty()){
						for (Groups group : groups){
							userCampaigns.addAll(userDao.getGroupActiveCampaigns(group.getId(), categoryId));
						}
					}
					
					if (segments != null && segments.size() > 0){
						for (Segment segment : segments){
							campaigns.addAll(campaignDao.getCampaignsBySegmentWithCategory(segment.getId(), categoryId));
						}
					}
				}
			}
			if (campaigns != null){
				
				if (!userCampaigns.isEmpty()){
					for (Campaign userCampaign : userCampaigns){
						if (campaigns.contains(userCampaign)) campaigns.remove(userCampaign);
					}
				}
				
				nonMemberCampaignHelper(memberId, campaigns, campaignViews, true);
			}
			
		}catch (Exception e){
			e.printStackTrace();
			return campaignViews;
		}
			
		return campaignViews;	
			
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CampaignViews getNonUserCampaigns(Long memberId, Long campaignTypeMode) {
		
		CampaignViews campaignViews = new CampaignViews();
		List<CampaignView> campaignList = new ArrayList<CampaignView>();
		
		CampaignView campaignView = new CampaignView();
		
		if (memberId == null || campaignTypeMode == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			campaignViews.setResult(rv);
			return campaignViews;
		}
		
		User user = userDao.findById(memberId);
		if (user == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid member");
			campaignViews.setResult(rv);
			return campaignViews;
		}
		
		List<Category> categories = new ArrayList<Category>();
		if (campaignTypeMode == 0){
			categories = categoryDao.getCategories(AppConstants.CATEGORYTYPE_CAMPAIGN);
		}else{
			categories.add(categoryDao.getCategoryByType(campaignTypeMode));
		}
		
		try{
			List<Campaign> userCampaigns = new ArrayList<Campaign>();
			List<Campaign> campaigns = new ArrayList<Campaign>();
			//Get segments of accounts
			
			List<Segment> segments = segmentService.getMemberSegment(memberId);
			
			if (categories != null && categories.size() > 0){
				long categoryId;
				for (Category category : categories){
					categoryId = category.getId();
					userCampaigns.addAll(userDao.getUserActiveCampaigns(memberId, categoryId));
					/*
					 * Add group campaigns
					 */
					List<Groups> groups = groupsDao.getGroups(memberId);
					if (!groups.isEmpty()){
						for (Groups group : groups){
							userCampaigns.addAll(userDao.getGroupActiveCampaigns(group.getId(), categoryId));
						}
					}
					
					if (segments != null && segments.size() > 0){
						for (Segment segment : segments){
							campaigns.addAll(campaignDao.getCampaignsBySegmentWithCategory(segment.getId(), categoryId));
						}
					}
				}
			}
			
			if (campaigns != null){
				
				if (!userCampaigns.isEmpty()){
					for (Campaign userCampaign : userCampaigns){
						
						if (campaigns.contains(userCampaign)) campaigns.remove(userCampaign);
					}
				}
				
				nonMemberCampaignHelper(memberId, campaigns, campaignList, false);
				
				campaignViews.setCampaign(campaignList);
			}
		}catch (Exception e){
			e.printStackTrace();
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, e.getMessage());
			campaignViews.setResult(rv);
			return campaignViews;
		}
			
		return campaignViews;	
			
	}
	
	/**
	 * Get non member campaigns 
	 * 
	 * @param memberId
	 * @param campaigns
	 * @param campaignViews
	 */
	private void nonMemberCampaignHelper(Long memberId, List<Campaign> campaigns, List<CampaignView> campaignViews, boolean isMobile){
		User member = userDao.findById(memberId);
		for (Campaign campaign : campaigns){
			CampaignView pv = new CampaignView();
			pv.setCampaignId(campaign.getId());
			pv.setName(TextUtil.parseString(campaign.getName()));
			pv.setDescription(TextUtil.parseString(campaign.getDescription()));
			pv.setLongDescription(TextUtil.parseString(campaign.getLongDescription()));
			pv.setStatusText(campaign.getStatus().getName());
			pv.setStatusId(campaign.getStatus().getId());
			pv.setCampaignType(campaign.getCampaignType().getType());
			
			if (campaign.getLongDescriptionRefImageUrl() != null)
				pv.setLongDescriptionRefImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getLongDescriptionRefImageUrl()));
			
			SimpleDateFormat df3 = new SimpleDateFormat("MMM dd");
			Date campaignStartDate = null;
			Date campaignEndDate = null;
			if (campaign.getFrequency() != null && campaign.getFrequency()  > 1){
				campaignStartDate = DateUtil.getContinousStartDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
				campaignEndDate = DateUtil.getContinousEndDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
				pv.setStartDate(df3.format(campaignStartDate).toUpperCase());
				pv.setEndDate(df3.format(campaignEndDate).toUpperCase());
			}else{
				campaignStartDate = campaign.getStartDate();
				campaignEndDate = campaign.getEndDate();
				pv.setStartDate(df3.format(campaignStartDate).toUpperCase());
				pv.setEndDate(df3.format(campaignEndDate).toUpperCase());
			}
			
			//Campaign end date always wins***
			if (campaign.getEndDate().before(campaignEndDate)) campaignEndDate = campaign.getEndDate();
			pv.setEndDate(df3.format(campaignEndDate).toUpperCase());
			
			pv.setStartTime(campaignStartDate);
			pv.setEndTime(campaignEndDate);
			
			pv.setSourceTypeImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getCampaignTemplate().getSourceType().getImageURL()));
			
			if (isMobile){
				if (campaign.getMobileSmallImageUrl() == null || campaign.getMobileSmallImageUrl().equals("")) pv.setImageURL(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getCampaignTemplate().getImageURL()));
				else pv.setImageURL(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getMobileSmallImageUrl()));
			}else{
				if (campaign.getSmallImageUrl() == null || campaign.getSmallImageUrl().equals("")) pv.setImageURL(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getCampaignTemplate().getImageURL()));
				else pv.setImageURL(AppConstants.APACHE_IMAGE_LINK  + ImageUtil.parseApcheImageUrl(campaign.getSmallImageUrl()));
			}
			
			if (campaign.getCampaignTemplate().getCampaignTemplateType().getMode() != null){
				pv.setMode(campaign.getCampaignTemplate().getCampaignTemplateType().getMode());
			}else{
				pv.setMode("");
			}
			if (campaign.getCampaignTemplate().getCampaignTemplateType().getName() != null){
				pv.setType(campaign.getCampaignTemplate().getCampaignTemplateType().getName());
			}else{
				pv.setType("");
			}
			
			List<Offer> offers = getCampaignOffer(campaign.getId(), member.getDefaultCommunity(), campaign.getEndDate(), memberId);
			Integer offerPoints = 0;
			
			System.out.println("Campaign Name :: " + campaign.getName() + " : " + campaign.getId());
			System.out.println("OFFER SIZE :: " + offers.size() + " :: " + campaign.getStatus().getCode());
			
			if (offers != null && offers.size() > 0){
				if (campaign.getStatus().getCode().equals(AppConstants.STATUS_COMPLETED)){
					int earnedPoints = 0;
					for(Offer offer : offers){
						System.out.println(offer.getName());
						if (isMemberActivityProcessed(memberId, campaign.getId(), offer.getCode())){
							earnedPoints += offer.getValue().intValue();
						}
						
					}
					
					pv.setEarnedPoints(earnedPoints);
				}
				
				for (Offer offer : offers){
					offerPoints += offer.getValue().intValue();

					if (offer.getBusinessPartner() != null) pv.setSponsorImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(offer.getBusinessPartner().getBannerSmallImageUrl()));
				}
					
			}
			pv.setOfferPoints(offerPoints);
			
			//Calculate progress bar value
			
			pv.setProgressbarValue(0);
			
				
			//Calculate Remaining in number and unit
			Date currentDate = new Date();
			//First check if we can calculate by days
			
			if (campaignEndDate.getTime() > currentDate.getTime()){
				Integer days = 0;
				//campaign hasn't start yet
				if (campaignStartDate.getTime() > currentDate.getTime()){
					days = Days.daysBetween(new DateTime(campaignStartDate), new DateTime(campaignEndDate)).getDays();
				}else{
					days = Days.daysBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getDays();
				}
				days++;
				if (days > 0){
					pv.setRemainingInNumber(days);
					pv.setRemainingInUnit("days");
				}
				else{
					Integer hours = Hours.hoursBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getHours();
					if (hours > 0){
						pv.setRemainingInNumber(hours);
						pv.setRemainingInUnit("hours");
					}else{
						Integer minutes = Minutes.minutesBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getMinutes();
						pv.setRemainingInNumber(minutes);
						pv.setRemainingInUnit("minutes");
					}
					
				}
				
			}else{
				pv.setRemainingInNumber(0);
				pv.setRemainingInUnit("days");
			}
			
			
			//Calculate Progress
			Double progress = progressDate(campaignStartDate, campaignEndDate);
			System.out.println("---- Progress :: " + progress);
			pv.setProgress(progress);
			
			campaignViews.add(pv);
		}

		Comparator<CampaignView> comparator = new Comparator<CampaignView>(){
			public int compare(CampaignView campaign1, CampaignView campaign2){
				if (campaign1.getStatusId() > campaign2.getStatusId()) return 1;
				else if (campaign1.getStatusId() < campaign2.getStatusId()) return -1;
				else return 0;
			}
		};
		
		Comparator<CampaignView> dateComparator = new Comparator<CampaignView>(){
			public int compare(CampaignView campaign1, CampaignView campaign2){
				//return campaign1.getName().compareTo(campaign2.getName());
				return (campaign1.getStartTime().compareTo(campaign2.getEndTime())) * -1;
			}
		};
		
		Collections.sort(campaignViews, dateComparator);
		Collections.sort(campaignViews, comparator);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CampaignViews getTopCampaigns(String domain, Long memberId, Long campaignTypeMode, Integer topNumber){
		
		CampaignViews campaignViews = new CampaignViews();
		List<CampaignView> campaignList = new ArrayList<CampaignView>();
		
		System.out.println("GET TOP Campaigns ");
		System.out.println("Domain : " + domain + " : memberId " + memberId + " : campaignTypeMode : " + campaignTypeMode + " : top Number "+ topNumber);
		if (campaignTypeMode == null || topNumber == null || (domain == null && memberId == null)){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			campaignViews.setResult(rv);
			return campaignViews;
		}
		
		Long communityId = null;
		
		List<Segment> segments = new ArrayList<Segment>();
		//Get member segments first if does not exist then get domain segments
		if (memberId != null){
			User user = userDao.findById(memberId);
			if (user != null){
				communityId = user.getDefaultCommunity();
				segments = segmentService.getMemberSegment(user.getId());
			}
		}
		
		if (segments.isEmpty()){
			Community community = communityDao.getCommunity(domain);
			if (community != null){
				communityId = community.getId();
				segments = segmentService.getDefaultSegment(community.getId());
			}
		}
		
		if (segments.isEmpty()){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid domain or member ID");
			campaignViews.setResult(rv);
			return campaignViews;
		}
		
		List<Category> categories = new ArrayList<Category>();
		if (campaignTypeMode == 0){
			categories = categoryDao.getCategories(AppConstants.CATEGORYTYPE_CAMPAIGN);
		}else{
			categories.add(categoryDao.getCategoryByType(campaignTypeMode));
		}
		
		if (!categories.isEmpty()){
			List<Campaign> campaigns = campaignDao.getTopCampaignsBySegmentsAndCategory(segments, categories, topNumber);
			
			System.out.println("Campaign Size :: " + campaigns.size());
			if (campaigns.isEmpty()){
				ResultView rv = new ResultView();
				rv.fill(AppConstants.FAILURE, "There are no campaigns.");
				campaignViews.setResult(rv);
			}else{
				for (Campaign campaign : campaigns){
					
					CampaignView pv = new CampaignView();
					pv.setCampaignId(campaign.getId());
					pv.setName(TextUtil.parseString(campaign.getName()));
					pv.setDescription(TextUtil.parseString(campaign.getDescription()));
					pv.setLongDescription(TextUtil.parseString(campaign.getLongDescription()));
					pv.setStatusText(campaign.getStatus().getName());
					pv.setStatusId(campaign.getStatus().getId());
					pv.setCampaignType(campaign.getCampaignType().getType());
					
					if (campaign.getLongDescriptionRefImageUrl() != null)
						pv.setLongDescriptionRefImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getLongDescriptionRefImageUrl()));
					
					SimpleDateFormat df3 = new SimpleDateFormat("MMM dd");
					
					Date campaignStartDate = null;
					Date campaignEndDate = null;
					if (campaign.getFrequency() != null && campaign.getFrequency()  > 1){
						campaignStartDate = DateUtil.getContinousStartDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
						campaignEndDate = DateUtil.getContinousEndDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
						pv.setStartDate(df3.format(campaignStartDate).toUpperCase());
						pv.setEndDate(df3.format(campaignEndDate).toUpperCase());
					}else{
						campaignStartDate = campaign.getStartDate();
						campaignEndDate = campaign.getEndDate();
						pv.setStartDate(df3.format(campaignStartDate).toUpperCase());
						pv.setEndDate(df3.format(campaignEndDate).toUpperCase());
					}
					
					//Campaign end date always wins***
					if (campaign.getEndDate().before(campaignEndDate)) campaignEndDate = campaign.getEndDate();
					pv.setEndDate(df3.format(campaignEndDate).toUpperCase());
					
					pv.setStartTime(campaignStartDate);
					pv.setEndTime(campaignEndDate);
					
					List<Offer> offers = getCampaignOffer(campaign.getId(), communityId, campaign.getEndDate(), null);
										
					Integer offerPoints = 0;
					if (offers != null && offers.size() > 0){
						for (Offer offer : offers){
							offerPoints += offer.getValue().intValue();
							
							System.out.println("Offer Found ! " + offer.getName() + " : " );
							System.out.println(offer.getBusinessPartner().getName());
							if (offer.getBusinessPartner() != null) pv.setSponsorImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(offer.getBusinessPartner().getBannerSmallImageUrl()));
						}
					}
					
					pv.setOfferPoints(offerPoints);
					
					if (campaign.getSmallImageUrl() == null || campaign.getSmallImageUrl().equals("")) pv.setImageURL(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getCampaignTemplate().getImageURL()));
					else pv.setImageURL(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getSmallImageUrl()));
					if (campaign.getCampaignTemplate().getCampaignTemplateType().getMode() != null){
						pv.setMode(campaign.getCampaignTemplate().getCampaignTemplateType().getMode());
					}else{
						pv.setMode("");
					}
					
					if (campaign.getCampaignTemplate().getCampaignTemplateType().getName() != null){
						pv.setType(campaign.getCampaignTemplate().getCampaignTemplateType().getName());
					}else{
						pv.setType("");
					}
					
					//Calculate progress bar value
					if (campaign.getCampaignTemplate().getCampaignTemplateType().getId() != 1l){
						
						CampaignTarget campaignTarget = campaignDao.getCampaignTarget(campaign.getId());
						Integer target = 0;
						if (campaignTarget != null) target = campaignTarget.getTarget();
						pv.setProgressbarValue(0);
						
						
					}
					//Calculate Remaining in number and unit
					Date currentDate = new Date();
					//First check if we can calculate by days
					
					if (campaignEndDate.getTime() > currentDate.getTime()){
						Integer days = 0;
						//campaignTarget hasn't start yet
						if (campaignStartDate.getTime() > currentDate.getTime()){
							days = Days.daysBetween(new DateTime(campaignStartDate), new DateTime(campaignEndDate)).getDays();
						}else{
							days = Days.daysBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getDays();
						}
						days++;
						if (days > 0){
							pv.setRemainingInNumber(days);
							pv.setRemainingInUnit("days");
						}
						else{
							Integer hours = Hours.hoursBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getHours();
							if (hours > 0){
								pv.setRemainingInNumber(hours);
								pv.setRemainingInUnit("hours");
							}else{
								Integer minutes = Minutes.minutesBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getMinutes();
								pv.setRemainingInNumber(minutes);
								pv.setRemainingInUnit("minutes");
							}
							
						}
						
					}else{
						pv.setRemainingInNumber(0);
						pv.setRemainingInUnit("days");
					}
					
					
					//Calculate Progress
					Double progress = progressDate(campaignStartDate, campaignEndDate);
					pv.setProgress(progress);
					
					campaignList.add(pv);
				}
				
				campaignViews.setCampaign(campaignList);
			}
		}
		
		return campaignViews;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CampaignViews getAllCampaigns(String serverName, Long campaignTypeMode){
		
		CampaignViews campaignViews = new CampaignViews();
		List<CampaignView> campaignList = new ArrayList<CampaignView>();
		
		if (serverName == null || campaignTypeMode == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			campaignViews.setResult(rv);
			return campaignViews;
		}
		
		Long communityId = utilityService.getCommuniyId(serverName);
		if (communityId == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid domain");
			campaignViews.setResult(rv);
			return campaignViews;
		}
		
		List<Category> categories = new ArrayList<Category>();
		if (campaignTypeMode == 0){
			categories = categoryDao.getCategories(AppConstants.CATEGORYTYPE_CAMPAIGN);
		}else{
			categories.add(categoryDao.getCategoryByType(campaignTypeMode));
		}
		
		List<Segment> segments = segmentService.getDefaultSegment(communityId);
		
		try{
			if (segments != null && segments.size() > 0  && categories != null && categories.size() > 0){
				List<Campaign> campaigns = new ArrayList<Campaign>();
				for (Category category : categories){
					if (segments != null && segments.size() > 0){
						for (Segment segment : segments){
							System.out.println("segment ID :: " + segment.getId());
							campaigns.addAll(campaignDao.getCampaignsBySegmentWithCategory(segment.getId(), category.getId()));
						}
					}
				}	
				
				if (campaigns != null){
					for (Campaign campaign : campaigns){
						CampaignView pv = new CampaignView();
						pv.setCampaignId(campaign.getId());
						pv.setName(TextUtil.parseString(campaign.getName()));
						pv.setDescription(TextUtil.parseString(campaign.getDescription()));
						pv.setLongDescription(TextUtil.parseString(campaign.getLongDescription()));
						pv.setStatusText(campaign.getStatus().getName());
						pv.setStatusId(campaign.getStatus().getId());
						pv.setCampaignType(campaign.getCampaignType().getType());
						
						if (campaign.getLongDescriptionRefImageUrl() != null)
							pv.setLongDescriptionRefImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getLongDescriptionRefImageUrl()));
						
						SimpleDateFormat df3 = new SimpleDateFormat("MMM dd");
						
						Date campaignStartDate = null;
						Date campaignEndDate = null;
						if (campaign.getFrequency() != null && campaign.getFrequency()  > 1){
							campaignStartDate = DateUtil.getContinousStartDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
							campaignEndDate = DateUtil.getContinousEndDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
							pv.setStartDate(df3.format(campaignStartDate).toUpperCase());
							pv.setEndDate(df3.format(campaignEndDate).toUpperCase());
						}else{
							campaignStartDate = campaign.getStartDate();
							campaignEndDate = campaign.getEndDate();
							pv.setStartDate(df3.format(campaignStartDate).toUpperCase());
							pv.setEndDate(df3.format(campaignEndDate).toUpperCase());
						}
						
						//Campaign end date always wins***
						if (campaign.getEndDate().before(campaignEndDate)) campaignEndDate = campaign.getEndDate();
						pv.setEndDate(df3.format(campaignEndDate).toUpperCase());
						
						pv.setStartTime(campaignStartDate);
						pv.setEndTime(campaignEndDate);
						
						// ----------------------
						System.out.println(campaign.getEndDate());
						List<Offer> offers = getCampaignOffer(campaign.getId(), communityId, campaign.getEndDate(), null);
						
						System.out.println("OFFER SIZE :: " + offers.size() + " :: " + campaign.getStatus().getCode());
						
						Integer offerPoints = 0;
						if (offers != null && offers.size() > 0){
							for (Offer offer : offers){
								offerPoints += offer.getValue().intValue();
								
								System.out.println("Offer Found ! " + offer.getName() + " : " );
								System.out.println(offer.getBusinessPartner().getName());
								if (offer.getBusinessPartner() != null) pv.setSponsorImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(offer.getBusinessPartner().getBannerSmallImageUrl()));
							}
						}
						
						pv.setOfferPoints(offerPoints);
						
						if (campaign.getSmallImageUrl() == null || campaign.getSmallImageUrl().equals("")) pv.setImageURL(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getCampaignTemplate().getImageURL()));
						else pv.setImageURL(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getSmallImageUrl()));
						
						if (campaign.getCampaignTemplate().getCampaignTemplateType().getMode() != null){
							pv.setMode(campaign.getCampaignTemplate().getCampaignTemplateType().getMode());
						}else{
							pv.setMode("");
						}
						
						if (campaign.getCampaignTemplate().getCampaignTemplateType().getName() != null){
							pv.setType(campaign.getCampaignTemplate().getCampaignTemplateType().getName());
						}else{
							pv.setType("");
						}
						
						//Calculate progress bar value
						if (campaign.getCampaignTemplate().getCampaignTemplateType().getId() != 1l){
							
							CampaignTarget campaignTarget = campaignDao.getCampaignTarget(campaign.getId());
							Integer target = 0;
							if (campaignTarget != null) target = campaignTarget.getTarget();
							pv.setProgressbarValue(0);
							
							
						}
						//Calculate Remaining in number and unit
						Date currentDate = new Date();
						//First check if we can calculate by days
						
						if (campaignEndDate.getTime() > currentDate.getTime()){
							Integer days = 0;
							//campaignTarget hasn't start yet
							if (campaignStartDate.getTime() > currentDate.getTime()){
								days = Days.daysBetween(new DateTime(campaignStartDate), new DateTime(campaignEndDate)).getDays();
							}else{
								days = Days.daysBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getDays();
							}
							days++;
							if (days > 0){
								pv.setRemainingInNumber(days);
								pv.setRemainingInUnit("days");
							}
							else{
								Integer hours = Hours.hoursBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getHours();
								if (hours > 0){
									pv.setRemainingInNumber(hours);
									pv.setRemainingInUnit("hours");
								}else{
									Integer minutes = Minutes.minutesBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getMinutes();
									pv.setRemainingInNumber(minutes);
									pv.setRemainingInUnit("minutes");
								}
								
							}
							
						}else{
							pv.setRemainingInNumber(0);
							pv.setRemainingInUnit("days");
						}
						
						
						//Calculate Progress
						Double progress = progressDate(campaignStartDate, campaignEndDate);
						pv.setProgress(progress);
						
						campaignList.add(pv);
					}
				}
				
			}
				
			Comparator<CampaignView> comparator = new Comparator<CampaignView>(){
				public int compare(CampaignView campaign1, CampaignView campaign2){
					if (campaign1.getStatusId() > campaign2.getStatusId()) return 1;
					else if (campaign1.getStatusId() < campaign2.getStatusId()) return -1;
					else return 0;
				}
			};
			
			Comparator<CampaignView> dateComparator = new Comparator<CampaignView>(){
				public int compare(CampaignView campaign1, CampaignView campaign2){
					//return campaign1.getName().compareTo(campaign2.getName());
					return (campaign1.getStartTime().compareTo(campaign2.getEndTime())) * -1;
				}
			};
			
			Collections.sort(campaignList, dateComparator);
			Collections.sort(campaignList, comparator);
			campaignViews.setCampaign(campaignList);
			
		}catch (Exception e){
			e.printStackTrace();
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, e.getMessage());
			campaignViews.setResult(rv);
			return campaignViews;
		}
		return campaignViews;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<CampaignView> getUserCampaignsMobile( String communitycode, Long accountId, Long campaignTypeMode){

		CampaignViews campaignViews = new CampaignViews();
		
		List<Campaign> campaigns = null;
		
		if (campaignTypeMode == 0){
			campaigns = userDao.getUserCampaigns(accountId);
		}else{
			List<Category> categories = new ArrayList<Category>();
			
			categories.add(categoryDao.getCategoryByType(campaignTypeMode));
			if (!categories.isEmpty()){
				campaigns = userDao.getUserCampaignsWithCategories(accountId, categories);
				
			}
			/*
			 * Add group campaigns
			 */
			List<Groups> groups = groupsDao.getGroups(accountId);
			if (!groups.isEmpty()){
				campaigns.addAll(userDao.getGroupCampaignsWithCategories(groups, categories));
			}
		}
		
		try{
			if (!campaigns.isEmpty()){
				memberCampaignHelper(accountId, campaignTypeMode, campaignViews, campaigns, true);
			}
			
		}catch (Exception e){
			e.printStackTrace();
			return campaignViews.getCampaign();
		}
			
		return campaignViews.getCampaign();	
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<CampaignView> getUserActiveCampaigns(String serverName, Long accountId, Long campaignTypeMode) {

		Long communityId = utilityService.getCommuniyId(serverName);
		System.out.println("community ID :: " + communityId);
		
		CampaignViews campaignViews = new CampaignViews();
		
		List<Category> categories = new ArrayList<Category>();
		if (campaignTypeMode == 0){
			categories = categoryDao.getCategories(AppConstants.CATEGORYTYPE_CAMPAIGN);
		}else{
			categories.add(categoryDao.getCategoryByType(campaignTypeMode));
		}
		
		try{
			if (!categories.isEmpty()){
				List<Campaign> campaigns = new ArrayList<Campaign>();
				campaigns.addAll(userDao.getUserActiveCampaignsWithCategories(accountId, categories));
				
				if (!campaigns.isEmpty()){
					memberCampaignHelper(accountId, campaignTypeMode, campaignViews, campaigns, false);
				}
				/*
				 * Add group campaigns
				 */
				List<Groups> groups = groupsDao.getGroups(accountId);
				if (!groups.isEmpty()){
					campaigns.addAll(userDao.getGroupActiveCampaignsWithCategories(groups, categories));
				}
			}
			
		
		}catch (Exception e){
			e.printStackTrace();
			return campaignViews.getCampaign();
		}
			
		return campaignViews.getCampaign();	
	}
	
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CampaignViews getUserCampaigns(Long accountId, Long campaignTypeMode) {
		
		CampaignViews campaignViews = new CampaignViews();
		
		if (accountId == null || campaignTypeMode == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			campaignViews.setResult(rv);
			return campaignViews;
		}
		
		User user = userDao.findById(accountId);
		if (user == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid member");
			campaignViews.setResult(rv);
			return campaignViews;
		}
		
		List<Category> categories = new ArrayList<Category>();
		if (campaignTypeMode == 0){
			categories = categoryDao.getCategories(AppConstants.CATEGORYTYPE_CAMPAIGN);
		}else{
			categories.add(categoryDao.getCategoryByType(campaignTypeMode));
		}
		
		try{
			if (!categories.isEmpty()){
				List<Campaign> campaigns = userDao.getUserCampaignsWithCategories(accountId, categories);
				/*
				 * Add group campaigns
				 */
				List<Groups> groups = groupsDao.getGroups(accountId);
				if (!groups.isEmpty()){
					campaigns.addAll(userDao.getGroupCampaignsWithCategories(groups, categories));
				}
				
				if (!campaigns.isEmpty()){
					memberCampaignHelper(accountId, campaignTypeMode, campaignViews, campaigns, false);
				}
				
			}
		}catch (Exception e){
			e.printStackTrace();
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, e.getMessage());
			campaignViews.setResult(rv);
		}
		
			
		return campaignViews;	
			
	}
	
	/**
	 * Store all list of member campaigns
	 * 
	 * @param accountId
	 * @param campaignTypeMode
	 * @param campaignViews
	 */
	private void memberCampaignHelper(long accountId, long campaignTypeMode, CampaignViews campaignViews, List<Campaign> campaigns, boolean isMobile){
		
		List<CampaignView> campaignList = new ArrayList<CampaignView>();
		
		User user = userDao.findById(accountId);
		
		
		if (campaigns != null){
			for (Campaign campaign : campaigns){
				CampaignView pv = new CampaignView();
				pv.setCampaignId(campaign.getId());
				pv.setName(TextUtil.parseString(campaign.getName()));
				pv.setDescription(TextUtil.parseString(campaign.getDescription()));
				pv.setLongDescription(TextUtil.parseString(campaign.getLongDescription()));
				pv.setStatusText(campaign.getStatus().getName());
				pv.setStatusId(campaign.getStatus().getId());
				pv.setCampaignType(campaign.getCampaignType().getType());
				
				if (campaign.getLongDescriptionRefImageUrl() != null)
					pv.setLongDescriptionRefImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getLongDescriptionRefImageUrl()));
				
				SimpleDateFormat df3 = new SimpleDateFormat("MMM dd");
				
				//Check if continuous and get current status
				Date campaignStartDate = null;
				Date campaignEndDate = null;
				if (campaign.getFrequency() != null && campaign.getFrequency()  > 1){
					campaignStartDate = DateUtil.getContinousStartDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
					campaignEndDate = DateUtil.getContinousEndDate(campaign.getStartDate(), campaign.getPeriodType(), campaign.getCurrentFrequencyStage(), campaign.getPeriod());
					pv.setStartDate(df3.format(campaignStartDate).toUpperCase());
					pv.setEndDate(df3.format(campaignEndDate).toUpperCase());
				
				}else{
					campaignStartDate = campaign.getStartDate();
					campaignEndDate = campaign.getEndDate();
					pv.setStartDate(df3.format(campaignStartDate).toUpperCase());
					pv.setEndDate(df3.format(campaignEndDate).toUpperCase());
				}
				
				//Campaign end date always wins***
				if (campaign.getEndDate().before(campaignEndDate)) campaignEndDate = campaign.getEndDate();
				pv.setEndDate(df3.format(campaignEndDate).toUpperCase());
				
				pv.setStartTime(campaignStartDate);
				pv.setEndTime(campaignEndDate);
				
				List<Offer> offers = getCampaignOffer(campaign.getId(), user.getDefaultCommunity(), campaign.getEndDate(), accountId);
				Integer offerPoints = 0;
				int earnedPoints = 0;
				if (offers != null && offers.size() > 0){
					for (Offer offer : offers){
						if (offer.getValue() != null){
							if (isMemberActivityProcessed(accountId, campaign.getId(), offer.getCode())){
								earnedPoints += offer.getValue().intValue();
							}
							offerPoints += offer.getValue().intValue();
						}
						if (offer.getBusinessPartner() != null) pv.setSponsorImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(offer.getBusinessPartner().getBannerSmallImageUrl()));
						
					}
						
				}
				pv.setOfferPoints(offerPoints);
				pv.setEarnedPoints(earnedPoints);
				
				pv.setSourceTypeImageUrl(AppConstants.APACHE_IMAGE_LINK  + ImageUtil.parseApcheImageUrl(campaign.getCampaignTemplate().getSourceType().getImageURL()));
				
				if (isMobile){
					if (campaign.getMobileSmallImageUrl() == null || campaign.getMobileSmallImageUrl().equals("")) pv.setImageURL(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getCampaignTemplate().getImageURL()));
					else pv.setImageURL(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getMobileSmallImageUrl()));
				}else{
					if (campaign.getSmallImageUrl() == null || campaign.getSmallImageUrl().equals("")) pv.setImageURL(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(campaign.getCampaignTemplate().getImageURL()));
					else pv.setImageURL(AppConstants.APACHE_IMAGE_LINK  + ImageUtil.parseApcheImageUrl(campaign.getSmallImageUrl()));
				}
				
				
				if (campaign.getCampaignTemplate().getCampaignTemplateType().getMode() != null){
					pv.setType(campaign.getCampaignTemplate().getCampaignTemplateType().getMode());
				}else{
					pv.setType("");
				}
				//Calculate progress bar value
					
					CampaignTarget campaignTarget = campaignDao.getCampaignTarget(campaign.getId());
					Integer target = 0;
					if (campaignTarget != null) target = campaignTarget.getTarget();
					
					Integer iteration = 1;
					if (campaign.getCurrentFrequencyStage() != null) iteration = campaign.getCurrentFrequencyStage();
					
					List<CampaignProgress> paps = campaignDao.getCampaignAccountProgress(campaign.getId(), accountId, iteration);
					if (paps !=null & paps.size() > 0){
						CampaignProgress pap = new CampaignProgress();
						pap = paps.get(0);
						Double accountProgress = pap.getProgressValue();
						Integer progressValue = (int)Math.round(accountProgress / (double)target * 100);
						if (progressValue > 100) progressValue =100;
						pv.setProgressbarValue(progressValue);
					}else{
						pv.setProgressbarValue(0);
					}
					
				//Calculate Remaining in number and unit
				Date currentDate = new Date();
				//First check if we can calculate by days
				
				if (campaignEndDate.getTime() > currentDate.getTime()){
					Integer days = 0;
					//campaign hasn't start yet
					if (campaignStartDate.getTime() > currentDate.getTime()){
						days = Days.daysBetween(new DateTime(campaignStartDate), new DateTime(campaignEndDate)).getDays();
					}else{
						days = Days.daysBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getDays();
					}
					days++;
					if (days > 0){
						pv.setRemainingInNumber(days);
						pv.setRemainingInUnit("days");
					}
					else{
						Integer hours = Hours.hoursBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getHours();
						if (hours > 0){
							pv.setRemainingInNumber(hours);
							pv.setRemainingInUnit("hours");
						}else{
							Integer minutes = Minutes.minutesBetween(new DateTime(currentDate), new DateTime(campaignEndDate)).getMinutes();
							pv.setRemainingInNumber(minutes);
							pv.setRemainingInUnit("minutes");
						}
						
					}
					
				}else{
					pv.setRemainingInNumber(0);
					pv.setRemainingInUnit("days");
				}
				
				
				//Calculate Progress
				Double progress = progressDate(campaignStartDate, campaignEndDate);
				pv.setProgress(progress);
				
				campaignList.add(pv);
			}
		}
		
		Comparator<CampaignView> comparator = new Comparator<CampaignView>(){
			public int compare(CampaignView campaign1, CampaignView campaign2){
				if (campaign1.getStatusId() > campaign2.getStatusId()) return 1;
				else if (campaign1.getStatusId() < campaign2.getStatusId()) return -1;
				else return 0;
			}
		};
		
		Comparator<CampaignView> dateComparator = new Comparator<CampaignView>(){
			public int compare(CampaignView campaign1, CampaignView campaign2){
				//return campaign1.getName().compareTo(campaign2.getName());
				return (campaign1.getStartTime().compareTo(campaign2.getEndTime())) * -1;
			}
		};
		
		Collections.sort(campaignList, dateComparator);
		Collections.sort(campaignList, comparator);
		
		campaignViews.setCampaign(campaignList);
			
		
	}
	
	/**
	 * Check to see if given member activity information has awarded points.
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-04
	 * @param accountId
	 * @param campaignId
	 * @param offerCode
	 * @return
	 */
	private boolean isMemberActivityProcessed(Long accountId, Long campaignId, Long offerCode){
		MemberActivity ma = memberActivityDao.getMemberActivityByReferenceAsCampaign(accountId, campaignId, offerCode);
		if (ma != null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Check to see if given member activity information has awarded points.
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-04
	 * @param accountId
	 * @param campaignId
	 * @param offerCode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private boolean isMemberActivityProcessedByDate(Long accountId, Long campaignId, Long offerCode, Date startDate, Date endDate){
		MemberActivity ma = memberActivityDao.getMemberActivityByReferenceAsCampaignAndDate(accountId, campaignId, offerCode, startDate, endDate);
		if (ma != null){
			return true;
		}else{
			return false;
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
	
	private Double progressDate(Date startDate, Date endDate){
		Date currentDate = new Date();
		//Check if endDate is before currentDate
		if (endDate.getTime() < currentDate.getTime()) return 1.0;
		//Check if startDate is future
		else if (startDate.getTime() > currentDate.getTime()) return 0.0;
		else{
			Double progress = (double) ((double)(currentDate.getTime() - startDate.getTime()) / (double)(endDate.getTime() - startDate.getTime()));
			return progress;
		}
		
		
	}
	
	public static Date getBeginningOfDay(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMinimum(Calendar.MILLISECOND));

		return new Date(calendar.getTimeInMillis());
	}
}


