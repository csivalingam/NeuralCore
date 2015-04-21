package net.zfp.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.ws.rs.QueryParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Domain;
import net.zfp.entity.User;
import net.zfp.entity.account.MemberExtendedProfile;
import net.zfp.entity.category.Category;
import net.zfp.entity.community.Community;
import net.zfp.entity.community.CorporatePreference;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.survey.Survey;
import net.zfp.entity.survey.SurveyAnswerChoices;
import net.zfp.entity.survey.SurveyAnswerRanges;
import net.zfp.entity.survey.SurveyAnswerValueType;
import net.zfp.entity.survey.SurveyAnswers;
import net.zfp.entity.survey.SurveyCategory;
import net.zfp.entity.survey.SurveyFactor;
import net.zfp.entity.survey.SurveyQuestions;
import net.zfp.entity.survey.SurveySummary;
import net.zfp.entity.survey.SurveyTips;
import net.zfp.entity.tips.Tips;
import net.zfp.form.MobileSurveyForm;
import net.zfp.form.SurveyForm;
import net.zfp.service.SurveyService;
import net.zfp.util.AppConstants;
import net.zfp.util.DateUtil;
import net.zfp.util.ImageConstants;
import net.zfp.util.ImageUtil;
import net.zfp.util.TextUtil;
import net.zfp.view.GaugeView;
import net.zfp.view.LeaderboardView;
import net.zfp.view.ResultView;
import net.zfp.view.survey.BenchmarkView;
import net.zfp.view.survey.CategoryView;
import net.zfp.view.survey.ChoiceView;
import net.zfp.view.survey.HistoryView;
import net.zfp.view.survey.HistoryViews;
import net.zfp.view.survey.QuestionView;
import net.zfp.view.survey.RangeView;
import net.zfp.view.survey.SurveySummaryView;
import net.zfp.view.survey.SurveyView;
import net.zfp.view.survey.SurveyViews;
import net.zfp.view.survey.TipsView;
import net.zfp.view.survey.TipsViews;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class SurveyServiceImpl implements SurveyService {
	@Resource
    private SegmentService segmentService;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Survey> surveyDao;
	@Resource
	private EntityDao<SurveyAnswers> surveyAnswersDao;
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<Offer> offerDao;
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<Category> categoryDao;
	@Resource
	private EntityDao<Segment> segmentDao;
	@Resource
	private EntityDao<SurveySummary> surveySummaryDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView storeSurveys(MobileSurveyForm mobileSurveyForm){
		
		ResultView rv = new ResultView();
		
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView storeSurveyAnswers(SurveyForm surveyForm){
		
		System.out.println("Store Survey Answers..." + surveyForm.getAccountId() + " : " +surveyForm.getSurveyAnswerParserString());
		
		ResultView rv = new ResultView();
		User user = userDao.findById(surveyForm.getAccountId());
		String parserString = surveyForm.getSurveyAnswerParserString();
		
		Date date = new Date();
		
		String[] answers = parserString.split("-;");
		
		try{
			for (String answer: answers){
				String[] answerInfos = answer.split("::");
				
				if (answerInfos.length > 0){
					Long surveyId = Long.parseLong(answerInfos[0]);
					Long questionId = Long.parseLong(answerInfos[2]);
					String answerValue = answerInfos[3];
					
					Survey survey = surveyDao.findById(surveyId);
					SurveyQuestions surveyQuestion = surveyDao.getSurveyQuestion(questionId);
					SurveyAnswerValueType savt = surveyDao.getSurveyAnswerValueType(surveyQuestion.getAnswerValueType().getType());
					
					SurveyAnswers sa = new SurveyAnswers();
					sa.setSurvey(survey);
					sa.setUser(user);
					sa.setCompletedDate(date);
					sa.setSurveyQuestion(surveyQuestion);
					sa.setAnswerValue(answerValue);
					sa.setSurveyAnswerValueType(savt);
					
					surveyAnswersDao.save(sa);
					
				}
			}
			rv.fill(AppConstants.SUCCESS, "Survey is saved");
		}catch(Exception e){
			e.printStackTrace();
			rv.fill(AppConstants.FAILURE, e.getMessage());
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public SurveySummaryView getSurveySummary(Long surveyId, Long memberId){
		SurveySummaryView surveySummaryView = new SurveySummaryView();
		
		if (surveyId == null || memberId == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			surveySummaryView.setResult(rv);
			return surveySummaryView;
		}
		
		Survey survey = surveyDao.findById(surveyId);
		if (survey == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid parameter(s).");
			surveySummaryView.setResult(rv);
			return surveySummaryView;
		}
		
		User user = userDao.findById(memberId);
		if (user == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid parameter(s).");
			surveySummaryView.setResult(rv);
			return surveySummaryView;
		}
		
		//Get Latest iteration for this memberId
		List<SurveySummary> surveySummaries = surveyDao.getLatestSurveySummary(survey.getId(), user.getId());
		if (!surveySummaries.isEmpty()){
			List<CategoryView> categoryViews = new ArrayList<CategoryView>();
			for (SurveySummary surveySummary : surveySummaries){
				//for each survey SummaryView get category...!
				CategoryView categoryView = new CategoryView();
				categoryView.setCategoryId(surveySummary.getCategory().getId());
				categoryView.setCategoryName(surveySummary.getCategory().getName());
				categoryView.setImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(surveySummary.getCategory().getImageUrl()));
				categoryView.setResult(surveySummary.getResult());
				
				categoryViews.add(categoryView);
				
			}
			
			surveySummaryView.setCategoryViews(categoryViews);
		}else{
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "The member did not complete the survey.");
			surveySummaryView.setResult(rv);
			return surveySummaryView;
		}
		
		return surveySummaryView;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public HistoryViews getSurveyHistory(Long surveyId, Long memberId, Integer limit){
		HistoryViews historyViews = new HistoryViews();
		
		if (memberId == null || surveyId == null || limit == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			historyViews.setResult(rv);
			return historyViews;
		}
		
		Survey survey = surveyDao.findById(surveyId);
		if (survey == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid parameter(s).");
			historyViews.setResult(rv);
			return historyViews;
		}
		
		User user = userDao.findById(memberId);
		if (user == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid parameter(s).");
			historyViews.setResult(rv);
			return historyViews;
		}
		
		List<SurveySummary> surveyHistories = surveyDao.getSurveySummaries(survey.getId(), user.getId(), limit);
		
		if (!surveyHistories.isEmpty()){
			List<HistoryView> hvs = new ArrayList<HistoryView>();
			
			for (SurveySummary surveyHistory : surveyHistories){
				HistoryView hv = new HistoryView();
				DateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
				
				hv.setDate(formatter.format(surveyHistory.getCreated()));
				Double result = surveyDao.getSurveySummaryResult(survey.getId(), user.getId(), surveyHistory.getIteration());
				hv.setResult(result);
				hv.setSurveyId(surveyHistory.getSurvey().getId());
				
				hvs.add(hv);
			}
			
			historyViews.setHistoryViews(hvs);
		}
		return historyViews;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public SurveyViews getCompletedSurveyList(Long memberId){
		SurveyViews surveyViews = new SurveyViews();
		if (memberId ==null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			surveyViews.setResult(rv);
			return surveyViews;
		}
		
		User member = userDao.findById(memberId);
		if (member == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
			surveyViews.setResult(rv);
			return surveyViews;
		}
		
		//Check to see all answered surveys...
		List<Survey> surveys = surveyDao.getCompletedSurvey(memberId);
		
		if (!surveys.isEmpty()){
			
			List<SurveyView> svs = new ArrayList<SurveyView>();
			for (Survey survey : surveys){
				svs.add(surveyHelper(survey, memberId));
			}
			
			surveyViews.setSurveyViews(svs);
		}
		
		
		return surveyViews;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public SurveyViews getSurveyList(Long memberId, String domain){
		
		SurveyViews surveyViews = new SurveyViews();
		if (memberId ==null && domain == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			surveyViews.setResult(rv);
			return surveyViews;
		}
		
		//Get user segments
		List<Segment> segments = null;
		if (memberId != null) segments = segmentService.getMemberSegment(memberId);
		else{
			Long communityId = getCommunityId(domain);
			if (communityId != null) segments = segmentService.getDefaultSegment(communityId);
		}
		
		if (segments == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid parameter(s).");
			surveyViews.setResult(rv);
			return surveyViews;
		}
		
		Date currentDate = new Date();
		List<Survey> surveys = surveyDao.getSurveyList(segments, currentDate);
		
		
		if (surveys != null && surveys.size() > 0){
			
			List<SurveyView> svs = new ArrayList<SurveyView>();
			
			for (Survey survey : surveys){
				svs.add(surveyHelper(survey, memberId));
			}
			
			surveyViews.setSurveyViews(svs);
		}
		
		
		return surveyViews;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public BenchmarkView getSurveyBenchmarkDetail(Long surveyId, Long memberId, Integer limit, Integer compareTo){
		BenchmarkView benchmarkView = new BenchmarkView();
		
		if (surveyId == null || memberId == null || limit == null || compareTo == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			benchmarkView.setResult(rv);
			return benchmarkView;
		}
		
		Survey survey = surveyDao.findById(surveyId);
		if (survey == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid survey");
			benchmarkView.setResult(rv);
			return benchmarkView;
		}
		
		User user = userDao.findById(memberId);
		if (user == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
			benchmarkView.setResult(rv);
			return benchmarkView;
		}
		
		
		GaugeView gaugeView = getGaugeViewForBenchmark(surveyId, user, compareTo);
		benchmarkView.setGaugeView(gaugeView);
		
		
		List<LeaderboardView> leaderboardViews = getLeaderboardViewForBenchmark(surveyId, user, limit, compareTo);
		benchmarkView.setLeaderboardViews(leaderboardViews);
		
		return benchmarkView;
	}
	
	/**
	 * Set leaderboard settings for a user
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-11
	 * @param surveyId
	 * @param user
	 * @param limit
	 * @param compareTo
	 * @return a leaderboard
	 */
	private List<LeaderboardView> getLeaderboardViewForBenchmark(Long surveyId, User user, Integer limit, Integer compareTo){
		List<LeaderboardView> leaderboardViews = new ArrayList<LeaderboardView>();
		
		if (compareTo == 1 || compareTo == 3){
			//Me compared to my community
			//See who's participated 
			List<SurveySummary> communityPeersSummary = new ArrayList<SurveySummary>();
			if (compareTo == 1)
				communityPeersSummary.addAll(surveyDao.getSurveySummariesForCommunity(surveyId, user.getDefaultCommunity()));
			else if (compareTo == 3)
				communityPeersSummary.addAll(surveyDao.getSurveySummaries(surveyId));
			
			if (!communityPeersSummary.isEmpty()){
				
				Integer greenZone =0, yellowzone =0;
				
				if (communityPeersSummary.size() == 1){
					greenZone = 0;
				}else if (communityPeersSummary.size() == 2){
					greenZone = 0;
					yellowzone = 1;
				}else{
					greenZone = communityPeersSummary.size()/3;
					yellowzone = 2 * communityPeersSummary.size()/3;
				}
				
				int memberPositionInPeers = 0;
				
				if (communityPeersSummary.size() < limit){
					limit = communityPeersSummary.size();
					memberPositionInPeers = limit-1;
					
				}else{
					
					//Check to see if community summary has user
					for (SurveySummary communityPeerSummary : communityPeersSummary){
						if (communityPeerSummary.getUser().getId().equals(user.getId())){
							break;
						}
						memberPositionInPeers++;
					}
				}
								
				boolean memberFound = false;
				for (int i = 0; i < limit-1; i++){
					SurveySummary peerSummary = communityPeersSummary.get(i);
					
					LeaderboardView leaderboardView = setLeaderboardView(peerSummary, surveyId, i, greenZone, yellowzone);
					//Check to see if a member founded or not
					if (peerSummary.getUser().getId().equals(user.getId())){
						memberFound = true;
						leaderboardView.setSelected(true);
					}
					leaderboardViews.add(leaderboardView);
					
				}
				
				//Check to see if a member should be the last one to show or not
				int index = limit-1;
				if (!memberFound){
					index = memberPositionInPeers;
				}
				
				SurveySummary peerSummary = communityPeersSummary.get(index);
				
				LeaderboardView leaderboardView = setLeaderboardView(peerSummary, surveyId, index, greenZone, yellowzone);
				if (peerSummary.getUser().getId().equals(user.getId())){
					leaderboardView.setSelected(true);
				}
				leaderboardViews.add(leaderboardView);
			
				
			}
			
		}else if (compareTo == 2){
			//My community to similar community
			//Get my corporate preference
			CorporatePreference memberPreference = communityDao.getCorporatePreference(user.getDefaultCommunity());
			if (memberPreference != null && memberPreference.getCorporateCategory() != null){
				//Get my category
				Long corporateCategoryId = memberPreference.getCorporateCategory().getId();
				if (corporateCategoryId != null){
					//Get all community with same category
					List<Community> peerCommunities = communityDao.getCorporateCommunitiesByCategory(corporateCategoryId);
					
					Integer greenZone =0, yellowzone =0;
					
					if (peerCommunities.size() == 1){
						greenZone = 0;
					}else if (peerCommunities.size() == 2){
						greenZone = 0;
						yellowzone = 1;
					}else{
						greenZone = peerCommunities.size()/3;
						yellowzone = 2 * peerCommunities.size()/3;
					}
					
					//Timestamp when the system finds member community
					Long memberTimestamp = null;
					for (Community peerCommunity : peerCommunities){
						Double communityValue = surveyDao.getCommunitySurveyResultSum(surveyId, peerCommunity.getId());
						if (communityValue == null) communityValue = 0.0;
						
						LeaderboardView leaderboardView = new LeaderboardView();
						
						Long timestamp = new Date().getTime();
						if (peerCommunity.getId().equals(user.getDefaultCommunity())){
							memberTimestamp = timestamp;
							leaderboardView.setSelected(true);
						}

						leaderboardView.setAccountId(timestamp);
						leaderboardView.setSourceId(timestamp);
						leaderboardView.setSourceFirstName(peerCommunity.getName());
						leaderboardView.setSourceLastName(" ");
						leaderboardView.setSortingValue(communityValue);
						
						if (communityValue > 50){
							leaderboardView.setValue((double)Math.round(communityValue));
						}else{
							leaderboardView.setValue((double)Math.round(communityValue* 10.0)/10.0);
						}
						
						/* Only add community value when the value is greater than 0 */
						if (communityValue > 0)
							leaderboardViews.add(leaderboardView);
					}
					
					//Sort by less
					//sort leaderboard view to lowest value
					Comparator<LeaderboardView> SortLowestComparator = new Comparator<LeaderboardView>(){
						public int compare(LeaderboardView lv1, LeaderboardView lv2){
							if (lv1.getSortingValue() < lv2.getSortingValue()) return -1;
							else if (lv1.getSortingValue() > lv2.getSortingValue()) return 1;
							else return 0;
						}
					};
					
					Collections.sort(leaderboardViews, SortLowestComparator);
					
					List<LeaderboardView> orderedLvs = new ArrayList<LeaderboardView>(); 
					boolean isUserIncluded= false;
					for (int i = 0; i<leaderboardViews.size(); i++){
						LeaderboardView leaderboardView = leaderboardViews.get(i);
						//Pass the 10th element if a user is not found yet
						if (!isUserIncluded && !leaderboardView.getSourceId().equals(memberTimestamp) && i >limit-1){
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
						
						if (leaderboardView.getSourceId().equals(memberTimestamp)){
							isUserIncluded = true;
						}
						
						if (isUserIncluded){
							if (i >= limit){
								break;
							}
						}
						
					}
					
					leaderboardViews = orderedLvs;
					
					
				}
			}
		}
		
		return leaderboardViews;
	}
	
	/**
	 * Set leaderboard view based on survey summary
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-11
	 * @param peerSummary
	 * @param surveyId
	 * @param i
	 * @param greenZone
	 * @param yellowZone
	 * @return leaderboard view
	 */
	private LeaderboardView setLeaderboardView(SurveySummary peerSummary, Long surveyId, Integer i, Integer greenZone, Integer yellowZone){
		LeaderboardView leaderboardView = new LeaderboardView();
		
		Long timestamp = new Date().getTime();
		leaderboardView.setAccountId(timestamp);
		leaderboardView.setSourceId(timestamp);
		leaderboardView.setSourceFirstName(peerSummary.getUser().getFirstName());
		leaderboardView.setSourceLastName(peerSummary.getUser().getLastName().substring(0,1));
		
		if (i <= greenZone){
			leaderboardView.setRank(1);
		}else if (i <= yellowZone){
			leaderboardView.setRank(2);
		}else{
			leaderboardView.setRank(3);
		}
		
		leaderboardView.setRanking(i+1);
		
		//Get Value
		Double result = surveyDao.getMemberSurveyResult(surveyId, peerSummary.getUser().getId());
		
		leaderboardView.setSortingValue(result);
		
		if (result > 50){
			leaderboardView.setValue((double)Math.round(result));
		}else{
			leaderboardView.setValue((double)Math.round(result* 10.0)/10.0);
		}
		
		return leaderboardView;
	}
	/**
	 * Getting gauge settings for a user
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-11
	 * @param surveyId
	 * @param user
	 * @param compareTo
	 * @return gauge view
	 */
	private GaugeView getGaugeViewForBenchmark(Long surveyId, User user, Integer compareTo){
		GaugeView gaugeView = new GaugeView();
		
		if (compareTo == 1 || compareTo == 3){
			//Me compare to my community
			
			Double minValue = 0.0;
			Double maxValue = 0.0;
			if (compareTo == 1){
				//Get minimum in my community
				minValue = surveyDao.getMinimumSurveyResult(surveyId, user.getDefaultCommunity());
				//Get maximum in my community
				maxValue = surveyDao.getMaximumSurveyResult(surveyId, user.getDefaultCommunity());
			}else if (compareTo == 3){
				//Get minimum in my community
				minValue = surveyDao.getMinimumSurveyResult(surveyId);
				//Get maximum in my community
				maxValue = surveyDao.getMaximumSurveyResult(surveyId);
			}
			
			Double[] zoneData = new Double[4];
			
			if (minValue == maxValue){
				//No users in community
				zoneData[0] = minValue;
				zoneData[1] = minValue;
				zoneData[2] = minValue;
				zoneData[3] = minValue;
			}else{
				zoneData[0] = Math.floor(minValue);
				zoneData[3] = Math.ceil(maxValue);
				Double interval = (zoneData[3] - zoneData[0])/3;
				zoneData[1] = zoneData[0] + interval;
				zoneData[2] = zoneData[3] - interval;
			}
			//Get my value
			Double myValue = surveyDao.getMemberSurveyResult(surveyId, user.getId());
			
			gaugeView.setZoneData(zoneData);
			gaugeView.setValue(myValue);
			
		}else if (compareTo == 2){
			//My community to similar my community
			//Get my corporate preference
			CorporatePreference memberPreference = communityDao.getCorporatePreference(user.getDefaultCommunity());
			if (memberPreference != null && memberPreference.getCorporateCategory() != null){
				//Get my category
				Long corporateCategoryId = memberPreference.getCorporateCategory().getId();
				
				if (corporateCategoryId != null){
					//Get all community with same category
					List<Community> peerCommunities = communityDao.getCorporateCommunitiesByCategory(corporateCategoryId);
					
					//For each community get survey results...
					Double maxValue = Double.MIN_VALUE;
					Double minValue = Double.MAX_VALUE;
					
					for (Community peerCommunity : peerCommunities){
						//Check all total consumption from this community
						Double communityValue = surveyDao.getCommunitySurveyResultSum(surveyId, peerCommunity.getId());
						if (communityValue == null) communityValue = 0.0;
						
						if (maxValue < communityValue) maxValue = communityValue;
						if (minValue > communityValue) minValue = communityValue;
						
					}
					
					Double memberCommunityValue = surveyDao.getCommunitySurveyResultSum(surveyId, user.getDefaultCommunity());
					if (memberCommunityValue == null) memberCommunityValue = 0.0;
					Double[] zoneData = new Double[4];
					
					if (minValue == maxValue){
						//No users in community
						zoneData[0] = minValue;
						zoneData[1] = minValue;
						zoneData[2] = minValue;
						zoneData[3] = minValue;
					}else{
						zoneData[0] = Math.floor(minValue);
						zoneData[3] = Math.ceil(maxValue);
						Double interval = (zoneData[3] - zoneData[0])/3;
						zoneData[1] = zoneData[0] + interval;
						zoneData[2] = zoneData[3] - interval;
					}
					
					gaugeView.setZoneData(zoneData);
					gaugeView.setValue(memberCommunityValue);
					
				}
				
			}
			
		}
		
		return gaugeView;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public TipsViews getAllSurveyTips(){
		TipsViews tipsViews = new TipsViews();
		
		List<Tips> tips = surveyDao.getAllTipsByCategories();
		List<TipsView> tvs = new ArrayList<TipsView>();
		
		if (!tips.isEmpty()){
			for (Tips tip : tips){
				TipsView tipsView = new TipsView();
				tipsView.setTips(TextUtil.parseHTMLString(tip.getDescription()));
				tvs.add(tipsView);
			}
			tipsViews.setTipsViews(tvs);
		}else{
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "No Tips available.");
			tipsViews.setResult(rv);
		}
		
		return tipsViews;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public TipsViews getSurveyTips(Long surveyId, Long memberId, Integer limit){
		TipsViews tipsViews = new TipsViews();
		
		if (surveyId == null || memberId == null || limit == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			tipsViews.setResult(rv);
			return tipsViews;
		}
		
		Survey survey = surveyDao.findById(surveyId);
		if (survey == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid survey");
			tipsViews.setResult(rv);
			return tipsViews;
		}
		
		User user = userDao.findById(memberId);
		if (user == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
			tipsViews.setResult(rv);
			return tipsViews;
		}
		
		if (survey.getSurveyType().getType().equals(AppConstants.SURVEY_TYPE_RESULTS_BASED)){
			//Get member results...
			List<SurveySummary> surveySummaries = surveySummaryDao.getLatestSurveySummary(survey.getId(), user.getId());
			
			//Member has to complete the survey in order to see tips
			if (!surveySummaries.isEmpty()){
				Double result = 0.0;
				for (SurveySummary surveySummary : surveySummaries){
					result += surveySummary.getResult();
				}
				//Get survey tips on based on member's results
				//Get relevance
				Integer relevance = surveyDao.getSurveyTips(result);
				List<Tips> surveyTips = surveyDao.getSurveyTips(relevance, limit);
				
				if (!surveyTips.isEmpty()){
					List<TipsView> tvs = new ArrayList<TipsView>();
					for (Tips surveyTip : surveyTips){
						TipsView tv = new TipsView();
						tv.setTips(TextUtil.parseHTMLString(surveyTip.getDescription()));
						
						tvs.add(tv);
					}
					
					tipsViews.setTipsViews(tvs);
				}else{
					ResultView rv = new ResultView();
					rv.fill(AppConstants.FAILURE, "Survey tips are empty.");
					tipsViews.setResult(rv);
					return tipsViews;
				}
			}else{
				ResultView rv = new ResultView();
				rv.fill(AppConstants.FAILURE, "A member needs to complete the survey in order to see tips.");
				tipsViews.setResult(rv);
				return tipsViews;
			}
			
		}else{
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid survey type to get tips.");
			tipsViews.setResult(rv);
			return tipsViews;
		}
		
		
		return tipsViews;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setSurveySummary(Long surveyId, Long memberId, String result){
		ResultView rv = new ResultView();
		
		if (surveyId == null || memberId == null || result == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		
		Survey survey = surveyDao.findById(surveyId);
		if (survey == null){
			rv.fill(AppConstants.FAILURE, "Invalid survey");
			return rv;
		}
		
		User user = userDao.findById(memberId);
		if (user == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
			return rv;
		}
		
		//Parse result
		String[] resultInArray = result.split("-;");
		
		System.out.println(resultInArray.length);
		
		//Check to see the iteration...
		Integer iteration = surveyDao.getSurveySummaryIteration(survey.getId(), user.getId());
		if (iteration == null) iteration = 0;
		iteration++;
		
		for (String categoryResult : resultInArray){
			//Get Category Id
			String[] categoryResultPair = categoryResult.split("::");
			
			if (categoryResultPair.length == 2){
				Category category = categoryDao.findById(Long.parseLong(categoryResultPair[0]));
				
				//store survey summary
				SurveySummary surveySummary = new SurveySummary();
				surveySummary.setCreated(new Date());
				surveySummary.setUser(user);
				surveySummary.setSurvey(survey);
				surveySummary.setCategory(category);
				surveySummary.setResult(Double.parseDouble(categoryResultPair[1]));
				surveySummary.setIteration(iteration);
				try{
					surveySummaryDao.save(surveySummary);
					rv.fill(AppConstants.SUCCESS, "Successfully saved the survey results");
				}catch(Exception e){
					rv.fill(AppConstants.FAILURE, "Errors in web services");
					return rv;
				}
			}
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public SurveyView getSurvey(Long surveyId, Long memberId){
		SurveyView sv = new SurveyView();
		
		if (surveyId == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			sv.setResult(rv);
			return sv;
		}
		
		//GetSurvey
		Date currentDate = new Date();
		Survey survey = surveyDao.getSurveyName(surveyId, currentDate);
		
		if (survey == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid survey.");
			sv.setResult(rv);
			return sv;
		}
		sv = surveyHelper(survey, memberId);
		
		return sv;
		
	}
	
	/**
	 * Get survey detail information
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-05
	 * @param survey
	 * @param memberId
	 * @return survey detail information
	 */
	private SurveyView surveyHelper(Survey survey, Long memberId){
		
		SurveyView sv = new SurveyView();
		
		sv.setSurveyId(survey.getId());
		sv.setSurveyName(TextUtil.parseString(survey.getName()));
		sv.setSurveyDescription(TextUtil.parseString(survey.getDescription()));
		sv.setSurveyLongDescription(TextUtil.parseString(survey.getLongDescription()));
		sv.setOutline(TextUtil.parseString(survey.getOutline()));
		
		sv.setType(survey.getSurveyType().getType());
		
		if (memberId != null){
			//Check if a member exists 
			SurveyAnswers sa = surveyAnswersDao.getSurveyAnswers(survey.getId(), memberId);
			
			if (sa != null) sv.setStatus(AppConstants.STATUS_COMPLETED);
			else sv.setStatus(AppConstants.STATUS_ACTIVE);
		}
		
		sv.setStartDate(DateUtil.printCalendar4(survey.getStartDate()));
		sv.setEndDate(DateUtil.printCalendar4(survey.getEndDate()));
		
		//Find offer...! (Image URL, Sponsor Image URL)
		List<Offer> surveyOffer = getSurveyOffer(survey.getId(), survey.getCommunity().getId(), survey.getEndDate(), memberId);
		if (surveyOffer == null || surveyOffer.size() == 0){
			sv.setImageURL(AppConstants.APACHE_IMAGE_LINK + "/images/survey/survey_default.jpg");
			sv.setSponsorImageURL(AppConstants.APACHE_IMAGE_LINK + "/images/partner/zero_logo.png");
			sv.setOfferCoins(0);
		}else{
			sv.setImageURL(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(surveyOffer.get(0).getSmallImageUrl()));
			sv.setSponsorImageURL(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(surveyOffer.get(0).getBusinessPartner().getBannerSmallImageUrl()));
			sv.setOfferCoins((int)(long)surveyOffer.get(0).getValue());
		}
		
		//SurveyCategoryType By Name
		List<SurveyCategory> surveyCategories = surveyDao.getSurveyCategory(survey.getId());
		
		if (surveyCategories != null && surveyCategories.size() > 0){
			for (SurveyCategory surveyCategory : surveyCategories){
				CategoryView cv = new CategoryView();
				Category c =surveyCategory.getCategory();
				cv.setCategoryId(c.getId());
				cv.setCategoryName(TextUtil.parseString(c.getName()));
				cv.setImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(c.getImageUrl()));
				
				//Get Question View from survey Category
				List<SurveyQuestions> surveyQuestions = surveyDao.getSurveyQuestions(surveyCategory.getId());
				
				if (surveyQuestions != null && surveyQuestions.size() > 0){
					for (SurveyQuestions sq : surveyQuestions){
						QuestionView qv = new QuestionView();
						
						qv.setIsMandatory(sq.getIsMandatory());
						qv.setQuestionId(sq.getId());
						qv.setQuestionName(TextUtil.parseString(sq.getQuestion()));
						qv.setQuestionType(sq.getQuestionType().getId());
						
						//Check to see if this question has more info images
						if (sq.getMoreInfoImage() != null){
							qv.setMoreInfoImage(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(sq.getMoreInfoImage()));
						}
						
						if (sq.getAnswerValueType() != null)
							qv.setAnswerValueType(sq.getAnswerValueType().getId());
						
						if (survey.getSurveyType().getType().equals(AppConstants.SURVEY_TYPE_RESULTS_BASED) &&
								sq.getQuestionType().getType().equals(AppConstants.SURVEY_QUESTION_TYPE_FORMULA) &&
									memberId != null){
							
							String factorKey = sq.getSurveyFactor().getKey();
							if (factorKey != null){
								//Get factor and operator
								qv.setOperator(sq.getOperator().getOperator());
								
								//Member community
								User member = userDao.findById(memberId);
								//Get community corporate preferences
								CorporatePreference cp = communityDao.getCorporatePreference(member.getDefaultCommunity());
								//Get country
								if (cp != null && cp.getCountry() != null){
									SurveyFactor surveyFactor = surveyDao.getSurveyFactor(factorKey, cp.getCountry().getId());
									
									if (surveyFactor != null){
										qv.setFactor(surveyFactor.getValue());
										
										//If there is factor reference you need to apply in the calculation
										if (sq.getSurveyFactorReference() != null){
											if (sq.getReferenceOperator() != null){
												qv.setReferenceOperator(sq.getReferenceOperator().getOperator());
												String reference = sq.getSurveyFactorReference().getReference();
												
												if (reference != null){
													//Get member extended profile
													MemberExtendedProfile mep = userDao.getMemberExtendedProfile(memberId);
													
													if (mep != null){
														if (reference.equals(AppConstants.MEMBER_PROFILE_OCCUPANTS)){
															qv.setReference(mep.getOccupants().doubleValue());
														}else if (reference.equals(AppConstants.MEMBER_PROFILE_ROOMS)){
															qv.setReference(mep.getRooms().doubleValue());
														}
													}
													
												}
											}
										}
									}
								}
							}
							
						}
						
						if (sq.getAnswerValueType().getType().equals(AppConstants.SURVEY_ANSWER_VALUE_TYPE_CHOICE)){
							List<SurveyAnswerChoices> answerChoices = surveyDao.getSurveyAnswerChoices(sq.getId());
							if (answerChoices != null && answerChoices.size() > 0){
								for (SurveyAnswerChoices sac : answerChoices){
									ChoiceView choiceView = new ChoiceView();
									choiceView.setName(TextUtil.parseString(sac.getChoice()));
									choiceView.setChoiceCode(sac.getChoiceCode());
									choiceView.setValue(sac.getValue());
									qv.getChoiceViews().add(choiceView);
								}
							}
						}else if (sq.getAnswerValueType().getType().equals(AppConstants.SURVEY_ANSWER_VALUE_TYPE_RANGE)){
							SurveyAnswerRanges surveyAnswerRanges = surveyDao.getSurveyRanges(sq.getId());
							
							if (surveyAnswerRanges != null){
								RangeView rangeView = new RangeView();
								rangeView.setIncrementer(surveyAnswerRanges.getIncrementer());
								rangeView.setMaximum(surveyAnswerRanges.getMaximum());
								rangeView.setMinimum(surveyAnswerRanges.getMinimum());
								
								qv.setRangeViews(rangeView);
							}
							
						}
						cv.getQuestionViews().add(qv);
					}
					sv.getCategoryViews().add(cv);
				}
				
				
			}
			
		}
		return sv;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<SurveyView> getMobileSurveys(String domainName, Long surveyId){
		
		List<SurveyView> svs = new ArrayList<SurveyView>();
		//Get communityId
		Long communityId = getCommunityId(domainName);
		
		
		
		return svs;
	}
	
	private List<Offer> getSurveyOffer(Long surveyId, Long communityId, Date endDate, Long memberId){
		
		List<Segment> segments = null;
		
		if (memberId != null) segments = segmentService.getMemberSegment(memberId);
		else segments = segmentService.getDefaultSegment(communityId);
		
		
		List<Offer> offers = offerDao.getOfferByBehaviorActions(AppConstants.BEHAVIOR_TRACKERTYPE_SURVEY, segments, surveyId+"", endDate);
		
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
}
