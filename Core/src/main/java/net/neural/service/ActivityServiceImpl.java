package net.zfp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.QueryParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Domain;
import net.zfp.entity.Source;
import net.zfp.entity.Unit;
import net.zfp.entity.User;
import net.zfp.entity.driving.DrivingDay;
import net.zfp.entity.driving.DrivingHour;
import net.zfp.entity.driving.DrivingMonth;
import net.zfp.entity.electricity.ElectricityDataDay;
import net.zfp.entity.electricity.ElectricityDataHour;
import net.zfp.entity.electricity.ElectricityDataMinute;
import net.zfp.entity.electricity.ElectricityDataMonth;
import net.zfp.entity.personalmotion.CyclingDataDay;
import net.zfp.entity.personalmotion.CyclingDataDayAverage;
import net.zfp.entity.personalmotion.CyclingDataHour;
import net.zfp.entity.personalmotion.CyclingDataMonth;
import net.zfp.entity.personalmotion.PersonalMotion;
import net.zfp.entity.personalmotion.RunningDataDay;
import net.zfp.entity.personalmotion.RunningDataDayAverage;
import net.zfp.entity.personalmotion.RunningDataHour;
import net.zfp.entity.personalmotion.RunningDataMonth;
import net.zfp.entity.personalmotion.WalkingBoundary;
import net.zfp.entity.personalmotion.WalkingDay;
import net.zfp.entity.personalmotion.WalkingDayAverage;
import net.zfp.entity.personalmotion.WalkingHour;
import net.zfp.entity.personalmotion.WalkingMonth;
import net.zfp.util.AppConstants;
import net.zfp.util.DateParserUtil;
import net.zfp.util.ImageUtil;
import net.zfp.view.BaselineView;
import net.zfp.view.DrivingSummaryView;
import net.zfp.view.DrivingView;
import net.zfp.view.ElectricitySummaryView;
import net.zfp.view.ElectricityView;
import net.zfp.view.GaugeView;
import net.zfp.view.PersonalMotionDailyView;
import net.zfp.view.PersonalMotionView;
import net.zfp.view.ResultView;
import net.zfp.view.SourceSummaryView;
import net.zfp.view.SourceSummaryViews;
import net.zfp.view.activity.ActivitySubView;
import net.zfp.view.activity.ActivityView;
import net.zfp.view.activity.ActivityViews;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class ActivityServiceImpl implements ActivityService {
	
	@Resource
	private SourceService sourceService;
	@Resource
	private DrivingService drivingService;
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<DrivingHour> drivingDao;
	@Resource
	private EntityDao<Source> sourceDao;
	@Resource
	private EntityDao<Unit> unitDao;
	@Resource
	private EntityDao<ElectricityDataMinute> electricityDao;
	@Resource
	private EntityDao<PersonalMotion> personalMotionDao;
	
	public static final String ACTIVITYTYPE_UTILITY = "UTILITY";
	public static final String ACTIVITYTYPE_EXERCISE = "EXERCISE";
	public static final String ACTIVITYTYPE_TRANSPORTAION = "TRANSPORTATION";
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public SourceSummaryViews getActivitySummary(Long memberId, String activityType, String periodType, Integer year, Integer month, Integer day){
		
		SourceSummaryViews ssvs = new SourceSummaryViews();
		
		if (memberId == null || activityType == null || periodType == null || year == null || month == null || (periodType.equals(AppConstants.PERIOD_TYPE_DAILY) && day == null)){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			ssvs.setResult(rv);
			return ssvs;
		}
		
		List<String> sourceTypes = new ArrayList<String>();
		
		//Because sql starts with 1... increment month by 1
		month++;
		
		//Get member community
		User user = userDao.findById(memberId);
		
		//For each activity type add source types
		if (activityType.toUpperCase().equals(ACTIVITYTYPE_UTILITY)){
			sourceTypes.add(AppConstants.SOURCETYPE_CODE_ELECTRICITY);
			sourceTypes.add(AppConstants.SOURCETYPE_CODE_NATURAL_GAS);
			sourceTypes.add(AppConstants.SOURCETYPE_CODE_WATER);
		}else if (activityType.toUpperCase().equals(ACTIVITYTYPE_EXERCISE)){
			sourceTypes.add(AppConstants.SOURCETYPE_CODE_WALKING);
			sourceTypes.add(AppConstants.SOURCETYPE_CODE_RUNNING);
			sourceTypes.add(AppConstants.SOURCETYPE_CODE_CYCLING);
		}else if (activityType.toUpperCase().equals(ACTIVITYTYPE_TRANSPORTAION)){
			sourceTypes.add(AppConstants.SOURCETYPE_CODE_DRIVING);
			sourceTypes.add(AppConstants.SOURCETYPE_CODE_BUS);
			sourceTypes.add(AppConstants.SOURCETYPE_CODE_TRAIN);
		}
		
		if (!sourceTypes.isEmpty()){
			
			List<SourceSummaryView> ssv = new ArrayList<SourceSummaryView>();
			
			for (String sourceType : sourceTypes){
				SourceSummaryView sourceSummaryView = new SourceSummaryView();
				sourceSummaryView.setSourceType(sourceType);
				ResultView rv = sourceService.getMasterSourceId(memberId, sourceType);
				
				if (rv.getResultCode() == AppConstants.SUCCESS){
					
					Long sourceId = Long.parseLong(rv.getResultMessage());
					Source activeSource = sourceDao.findById(sourceId);
					
					if (activeSource != null){
						
						if (activityType.toUpperCase().equals(ACTIVITYTYPE_UTILITY)){
							
							getUtilitySummary(sourceSummaryView, activeSource, sourceType, periodType.toUpperCase(), year, month, day);
							
													
						}else if (activityType.toUpperCase().equals(ACTIVITYTYPE_EXERCISE)){
							
							getExerciseSummary(sourceSummaryView, activeSource, sourceType, periodType.toUpperCase(), year, month, day);
							
						}else if (activityType.toUpperCase().equals(ACTIVITYTYPE_TRANSPORTAION)){
							
							getTransportationSummary(sourceSummaryView, activeSource, sourceType, periodType.toUpperCase(), year, month, day);
						}
					}
					
					getRankPeers(sourceSummaryView, user.getId(), user.getDefaultCommunity(), sourceType, periodType.toUpperCase(), year, month, day);
					
					ssv.add(sourceSummaryView);
				}
			}
			
			ssvs.setSourceSummaryViews(ssv);
		}
		
		return ssvs;
	}
	
	/**
	 * GET source summary for particular date and period type
	 */
	private void getTransportationSummary(SourceSummaryView ssv, Source activeSource, String sourceTypeCode, String periodType, Integer year, Integer month, Integer day){
		
		if (periodType.equals(AppConstants.PERIOD_TYPE_DAILY)){
			if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_DRIVING)){
				DrivingDay drivingDay = drivingDao.getDrivingDayByDate(activeSource.getId(), year, month, day);
				if (drivingDay != null){
					
					ssv.setEfficiency(Math.round(drivingDay.getFuelEfficiency() * 100.0) / 100.0);
					ssv.setConsumption(drivingDay.getFuel());
					ssv.setDistance(drivingDay.getDistance());
					ssv.setDurationInSeconds(drivingDay.getDuration());
					
					DrivingDay previousDay = drivingDao.getDrivingDayByDate(activeSource.getId(), year, month, --day);
					if (previousDay != null){
						if (previousDay.getFuelEfficiency() > drivingDay.getFuelEfficiency()){
							ssv.setTrend(1);
						}else if (previousDay.getFuelEfficiency() < drivingDay.getFuelEfficiency()){
							ssv.setTrend(2);
						}else{
							ssv.setTrend(-1);
						}
					}else{
						//Since there was no previous value it should be incremented
						ssv.setTrend(2);
					}
				}else{
					ssv.setEfficiency(0.0);
					ssv.setConsumption(0.0);
					ssv.setDistance(0.0);
					ssv.setDurationInSeconds(0.0);
					ssv.setTrend(-1);
				}
			}else{
				//XXX: For Utility Summary, we only have electricity built in. For now water and natural gas returns nothing
				
			}
		}else if (periodType.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
			if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_DRIVING)){
				DrivingMonth drivingMonth = drivingDao.getDrivingMonthByDate(activeSource.getId(), year, month);
				if (drivingMonth != null){
					
					ssv.setEfficiency(Math.round(drivingMonth.getFuelEfficiency() * 100.0) / 100.0);
					ssv.setConsumption(drivingMonth.getFuel());
					ssv.setDistance(drivingMonth.getDistance());
					ssv.setDurationInSeconds(drivingMonth.getDuration());
					
					DrivingMonth previousMonth = drivingDao.getDrivingMonthByDate(activeSource.getId(), year, --month);
					if (previousMonth != null){
						if (previousMonth.getFuelEfficiency() > drivingMonth.getFuelEfficiency()){
							ssv.setTrend(1);
						}else if (previousMonth.getFuelEfficiency() < drivingMonth.getFuelEfficiency()){
							ssv.setTrend(2);
						}else{
							ssv.setTrend(-1);
						}
					}else{
						//Since there was no previous value it should be incremented
						ssv.setTrend(2);
					}
				}else{
					ssv.setEfficiency(0.0);
					ssv.setConsumption(0.0);
					ssv.setDistance(0.0);
					ssv.setDurationInSeconds(0.0);
					ssv.setTrend(-1);
				}
			}else{
				//XXX: For Utility Summary, we only have electricity built in. For now water and natural gas returns nothing
				
			}
		}
	}
	
	/**
	 * GET source summary for particular date and period type
	 */
	private void getUtilitySummary(SourceSummaryView ssv, Source activeSource, String sourceTypeCode, String periodType, Integer year, Integer month, Integer day){
		
		if (periodType.equals(AppConstants.PERIOD_TYPE_DAILY)){
			if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY)){
				ElectricityDataDay electrictyDataDay = electricityDao.getElectricityDayByDate(activeSource.getId(), year, month, day);
				if (electrictyDataDay != null){
					
					Double spending = electrictyDataDay.getOnPeakSpend() + electrictyDataDay.getMidPeakSpend() + electrictyDataDay.getOffPeakSpend();
					ssv.setSpend(Math.round(spending * 100.0)/100.0);
					ssv.setSavings(Math.round((spending *0.12 *12) * 100.0)/100.0);
					Double consumption = electrictyDataDay.getOnPeakConsumption() + electrictyDataDay.getMidPeakConsumption() + electrictyDataDay.getOffPeakConsumption();
					ssv.setConsumption(Math.round(consumption/1000 * 100.0)/100.0 );
					
					ElectricityDataDay previousDay = electricityDao.getElectricityDayByDate(activeSource.getId(), year, month, --day);
					if (previousDay != null){
						if (previousDay.getEnergy() > electrictyDataDay.getEnergy()){
							ssv.setTrend(1);
						}else if (previousDay.getEnergy() < electrictyDataDay.getEnergy()){
							ssv.setTrend(2);
						}else{
							ssv.setTrend(-1);
						}
					}else{
						//Since there was no previous value it should be incremented
						ssv.setTrend(2);
					}
				}else{
					ssv.setSpend(0.0);
					ssv.setSavings(0.0);
					ssv.setConsumption(0.0);
					ssv.setTrend(-1);
				}
			}else{
				//XXX: For Utility Summary, we only have electricity built in. For now water and natural gas returns nothing
				
			}
		}else if (periodType.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
			if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY)){
				ElectricityDataMonth electricityDataMonth = electricityDao.getElectricityMonthByDate(activeSource.getId(), year, month);
				if (electricityDataMonth != null){
					Double spending = electricityDataMonth.getOnPeakSpend() + electricityDataMonth.getMidPeakSpend() + electricityDataMonth.getOffPeakSpend();
					ssv.setSpend(Math.round(spending * 100.0)/100.0);
					ssv.setSavings(Math.round((spending *0.12 *12) * 100.0)/100.0);
					Double consumption = electricityDataMonth.getOnPeakConsumption() + electricityDataMonth.getMidPeakConsumption() + electricityDataMonth.getOffPeakConsumption();
					ssv.setConsumption(Math.round(consumption/1000 * 100.0)/100.0 );
					
					ElectricityDataMonth previousMonth = electricityDao.getElectricityMonthByDate(activeSource.getId(), year, --month);
					if (previousMonth != null){
						if (previousMonth.getEnergy() > electricityDataMonth.getEnergy()){
							ssv.setTrend(1);
						}else if (previousMonth.getEnergy() < electricityDataMonth.getEnergy()){
							ssv.setTrend(2);
						}else{
							ssv.setTrend(-1);
						}
					}else{
						//Since there was no previous value it should be incremented
						ssv.setTrend(2);
					}
				}else{
					ssv.setSpend(0.0);
					ssv.setSavings(0.0);
					ssv.setConsumption(0.0);
					ssv.setTrend(-1);
				}
			}else{
				//XXX: For Utility Summary, we only have electricity built in. For now water and natural gas returns nothing
				
			}
		}
	}
	
	/**
	 * GET active member source with given source type
	 */
	private Source getActiveSourceBySourceType(Long memberId, String sourceType){
		Source activeSource = null;
		
		List<Source> sources = null;
		sources = userDao.findDefaultSourceByAccountAndType(memberId, sourceType);
		
		/*
		 * If there is no default source... fetch all non default source
		 */
		if (sources.isEmpty()){
			sources = userDao.findSourceByAccountAndType(memberId, sourceType);
		}
		
		if (!sources.isEmpty()){
			for (Source source : sources){
				if (source.getStatus().getCode().equals(AppConstants.STATUS_ACTIVE)){
					activeSource = source;
					break;
				}
			}
		}
		
		return activeSource;
	}
	
	/**
	 * Get values for community peers and get rank among them
	 */
	private void getRankPeers(SourceSummaryView ssv, Long memberId, Long communityId, String sourceType, String periodType, Integer year, Integer month, Integer day){
		//Check if member has calories value.. otherwise 
		if (ssv.getCalories() == null && ssv.getConsumption() == null){
			ssv.setRank(1);
			return;
		}
		
		if ((ssv.getCalories() != null && ssv.getCalories() == 0) || (ssv.getConsumption() != null && ssv.getConsumption() == 0.0)){
			ssv.setRank(1);
			return;
		}
		
		List<Long> peerIds = userDao.getPeersInCommunity(memberId, communityId);
		List<Long> peerSourceIds = new ArrayList<Long>();
		if (!peerIds.isEmpty()){
			for (Long peerId : peerIds){
				Source active = getActiveSourceBySourceType(peerId, sourceType);
				if (active != null)
					peerSourceIds.add(active.getId());
			}
		}else{
			ssv.setRank(1);
		}
		
		if (!peerSourceIds.isEmpty()){
			int peerSize = 1;
			int counter = 1;
			
			for (Long peerSourceId: peerSourceIds){
				if (periodType.equals(AppConstants.PERIOD_TYPE_DAILY)){
					if (sourceType.equals(AppConstants.SOURCETYPE_CODE_WALKING)){
						WalkingDay walkingDay = personalMotionDao.getWalkingDayByDate(peerSourceId, year, month, day);
						if (walkingDay != null){
							if (ssv.getCalories() < walkingDay.getCalories()) counter++;
							peerSize++;
						}
					}else if (sourceType.equals(AppConstants.SOURCETYPE_CODE_CYCLING)){
						CyclingDataDay cyclingDataDay = personalMotionDao.getCyclingDayByDate(peerSourceId, year, month, day);
						if (cyclingDataDay != null){
							if (ssv.getCalories() < cyclingDataDay.getCalories()) counter++;
							peerSize++;
						}
					}else if (sourceType.equals(AppConstants.SOURCETYPE_CODE_RUNNING)){
						RunningDataDay runningDataDay = personalMotionDao.getRunningDayByDate(peerSourceId, year, month, day);
						if (runningDataDay != null){
							if (ssv.getCalories() < runningDataDay.getCalories()) counter++;
							peerSize++;
						}
					}else if (sourceType.equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY)){
						ElectricityDataDay electricityDataDay = electricityDao.getElectricityDayByDate(peerSourceId, year, month, day);
						if (electricityDataDay != null){
							if (ssv.getConsumption() < electricityDataDay.getEnergy()) counter++;
							peerSize++;
						}
					}else if (sourceType.equals(AppConstants.SOURCETYPE_CODE_DRIVING)){
						DrivingDay drivingDay = drivingDao.getDrivingDayByDate(peerSourceId, year, month, day);
						if (drivingDay != null){
							if (ssv.getEfficiency() < drivingDay.getFuelEfficiency()) counter++;
							peerSize++;
						}
					}
				}else if (periodType.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
					if (sourceType.equals(AppConstants.SOURCETYPE_CODE_WALKING)){
						WalkingMonth walkingMonth = personalMotionDao.getWalkingMonthByDate(peerSourceId, year, month);
						if (walkingMonth != null){
							if (ssv.getCalories() < walkingMonth.getCalories()) counter++;
							peerSize++;
						}
					}else if (sourceType.equals(AppConstants.SOURCETYPE_CODE_CYCLING)){
						CyclingDataMonth cyclingMonth = personalMotionDao.getCyclingMonthByDate(peerSourceId, year, month);
						if (cyclingMonth != null){
							if (ssv.getCalories() < cyclingMonth.getCalories()) counter++;
							peerSize++;
						}
					}else if (sourceType.equals(AppConstants.SOURCETYPE_CODE_RUNNING)){
						RunningDataMonth runningMonth = personalMotionDao.getRunningMonthByDate(peerSourceId, year, month);
						if (runningMonth != null){
							if (ssv.getCalories() < runningMonth.getCalories()) counter++;
							peerSize++;
						}
					}else if (sourceType.equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY)){
						ElectricityDataMonth electricityDataMonth = electricityDao.getElectricityMonthByDate(peerSourceId, year, month);
						if (electricityDataMonth != null){
							if (ssv.getConsumption() < electricityDataMonth.getEnergy()) counter++;
							peerSize++;
						}
					}else if (sourceType.equals(AppConstants.SOURCETYPE_CODE_DRIVING)){
						DrivingMonth drivingMonth = drivingDao.getDrivingMonthByDate(peerSourceId, year, month);
						if (drivingMonth != null){
							if (ssv.getEfficiency() < drivingMonth.getFuelEfficiency()) counter++;
							peerSize++;
						}
					}
				}
			}
			
			if (peerSize >=3){
				if ((double)counter / peerSize > 2.0/3.0){
					ssv.setRank(3);
				}else if ((double) counter /peerSize > 1.0/3.0){
					ssv.setRank(2);
				}else{
					ssv.setRank(1);
				}
			}else{
				ssv.setRank(1);
			}
		}else{
			ssv.setRank(1);
		}
	}
	
	
	
	/**
	 * GET source summary for particular date and period type
	 */
	private void getExerciseSummary(SourceSummaryView ssv, Source activeSource, String sourceTypeCode, String periodType, Integer year, Integer month, Integer day){
		
		if (periodType.equals(AppConstants.PERIOD_TYPE_DAILY)){
			if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_WALKING)){
				
				System.out.println("-------------" + year + " : " + month + " : " + day);
				WalkingDay walkingDay = personalMotionDao.getWalkingDayByDate(activeSource.getId(), year, month, day);
				if (walkingDay != null){
					ssv.setCalories(walkingDay.getCalories());
					ssv.setDurationInSeconds(walkingDay.getDuration());
					ssv.setDistance(walkingDay.getDistance());
					ssv.setSteps(walkingDay.getSteps());
					
					WalkingDay previousDay = personalMotionDao.getWalkingDayByDate(activeSource.getId(), year, month, --day);
					if (previousDay != null){
						if (previousDay.getCalories() > walkingDay.getCalories()){
							ssv.setTrend(1);
						}else if (previousDay.getCalories() < walkingDay.getCalories()){
							ssv.setTrend(2);
						}else{
							ssv.setTrend(-1);
						}
					}else{
						//Since there was no previous value it should be incremented
						ssv.setTrend(2);
					}
				}else{
					System.out.println("Can't find the walking data");
					ssv.setCalories(0);
					ssv.setDurationInSeconds(0.0);
					ssv.setDistance(0.0);
					ssv.setSteps(0);
					ssv.setTrend(-1);
				}
			}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)){
				CyclingDataDay cyclingDataDay = personalMotionDao.getCyclingDayByDate(activeSource.getId(), year, month, day);
				if (cyclingDataDay != null){
					ssv.setCalories(cyclingDataDay.getCalories());
					ssv.setDurationInSeconds(cyclingDataDay.getDuration());
					ssv.setDistance(cyclingDataDay.getDistance());
					
					CyclingDataDay previousDay = personalMotionDao.getCyclingDayByDate(activeSource.getId(), year, month, --day);
					if (previousDay != null){
						if (previousDay.getCalories() > cyclingDataDay.getCalories()){
							ssv.setTrend(1);
						}else if (previousDay.getCalories() < cyclingDataDay.getCalories()){
							ssv.setTrend(2);
						}else{
							ssv.setTrend(-1);
						}
					}else{
						//Since there was no previous value it should be incremented
						ssv.setTrend(2);
					}
				}else{
					ssv.setCalories(0);
					ssv.setDurationInSeconds(0.0);
					ssv.setDistance(0.0);
					ssv.setTrend(-1);
				}
			}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_RUNNING)){
				RunningDataDay runningDataDay = personalMotionDao.getRunningDayByDate(activeSource.getId(), year, month, day);
				if (runningDataDay != null){
					ssv.setCalories(runningDataDay.getCalories());
					ssv.setDurationInSeconds(runningDataDay.getDuration());
					ssv.setDistance(runningDataDay.getDistance());
					ssv.setSteps(runningDataDay.getSteps());
					
					RunningDataDay previousDay = personalMotionDao.getRunningDayByDate(activeSource.getId(), year, month, --day);
					if (previousDay != null){
						if (previousDay.getCalories() > runningDataDay.getCalories()){
							ssv.setTrend(1);
						}else if (previousDay.getCalories() < runningDataDay.getCalories()){
							ssv.setTrend(2);
						}else{
							ssv.setTrend(-1);
						}
					}else{
						//Since there was no previous value it should be incremented
						ssv.setTrend(2);
					}
				}else{
					ssv.setCalories(0);
					ssv.setDurationInSeconds(0.0);
					ssv.setDistance(0.0);
					ssv.setSteps(0);
					ssv.setTrend(-1);
				}
			}
		}else if (periodType.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
			if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_WALKING)){
				WalkingMonth walkingMonth = personalMotionDao.getWalkingMonthByDate(activeSource.getId(), year, month);
				if (walkingMonth != null){
					ssv.setCalories(walkingMonth.getCalories());
					ssv.setDurationInSeconds(walkingMonth.getDuration());
					ssv.setDistance(walkingMonth.getDistance());
					ssv.setSteps(walkingMonth.getSteps());
					
					WalkingMonth previousMonth = personalMotionDao.getWalkingMonthByDate(activeSource.getId(), year, --month);
					if (previousMonth != null){
						if (previousMonth.getCalories() > walkingMonth.getCalories()){
							ssv.setTrend(1);
						}else if (previousMonth.getCalories() < walkingMonth.getCalories()){
							ssv.setTrend(2);
						}else{
							ssv.setTrend(-1);
						}
					}else{
						//Since there was no previous value it should be incremented
						ssv.setTrend(2);
					}
				}else{
					ssv.setCalories(0);
					ssv.setDurationInSeconds(0.0);
					ssv.setDistance(0.0);
					ssv.setSteps(0);
					ssv.setTrend(-1);
				}
			}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)){
				CyclingDataMonth cyclingMonth = personalMotionDao.getCyclingMonthByDate(activeSource.getId(), year, month);
				if (cyclingMonth != null){
					ssv.setCalories(cyclingMonth.getCalories());
					ssv.setDurationInSeconds(cyclingMonth.getDuration());
					ssv.setDistance(cyclingMonth.getDistance());
					
					CyclingDataMonth previousMonth = personalMotionDao.getCyclingMonthByDate(activeSource.getId(), year, --month);
					if (previousMonth != null){
						if (previousMonth.getCalories() > cyclingMonth.getCalories()){
							ssv.setTrend(1);
						}else if (previousMonth.getCalories() < cyclingMonth.getCalories()){
							ssv.setTrend(2);
						}else{
							ssv.setTrend(-1);
						}
					}else{
						//Since there was no previous value it should be incremented
						ssv.setTrend(2);
					}
				}else{
					ssv.setCalories(0);
					ssv.setDurationInSeconds(0.0);
					ssv.setDistance(0.0);
					ssv.setTrend(-1);
				}
			}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_RUNNING)){
				RunningDataMonth runningMonth = personalMotionDao.getRunningMonthByDate(activeSource.getId(), year, month);
				if (runningMonth != null){
					ssv.setCalories(runningMonth.getCalories());
					ssv.setDurationInSeconds(runningMonth.getDuration());
					ssv.setDistance(runningMonth.getDistance());
					ssv.setSteps(runningMonth.getSteps());
					
					RunningDataMonth previousMonth = personalMotionDao.getRunningMonthByDate(activeSource.getId(), year, --month);
					if (previousMonth != null){
						if (previousMonth.getCalories() > runningMonth.getCalories()){
							ssv.setTrend(1);
						}else if (previousMonth.getCalories() < runningMonth.getCalories()){
							ssv.setTrend(2);
						}else{
							ssv.setTrend(-1);
						}
					}else{
						//Since there was no previous value it should be incremented
						ssv.setTrend(2);
					}
				}else{
					ssv.setCalories(0);
					ssv.setDurationInSeconds(0.0);
					ssv.setDistance(0.0);
					ssv.setSteps(0);
					ssv.setTrend(-1);
				}
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public PersonalMotionDailyView getPersonalMotionDataInRange(Long memberId, String sourceTypeCode, String periodType,
			String startDate, String endDate){
		PersonalMotionDailyView pmdv = new PersonalMotionDailyView();
		
		if (memberId == null || sourceTypeCode == null || periodType == null || startDate == null || endDate == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			pmdv.setResult(rv);
			return pmdv;
		}
		
		if (!(sourceTypeCode.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_CYCLING) || sourceTypeCode.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_RUNNING)||
				sourceTypeCode.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_WALKING))){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid source type");
			pmdv.setResult(rv);
			return pmdv;
		}
		
		/*
		 * Parse dates
		 */
		Date start = null;
		Date end = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		try{
			start = format.parse(startDate);
			end = format.parse(endDate);
			
			if (start.after(end)){
				ResultView rv = new ResultView();
				rv.fill(AppConstants.FAILURE, "Start Date is after End Date");
				pmdv.setResult(rv);
				return pmdv;
			}
		}catch(Exception e){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid date format");
			pmdv.setResult(rv);
			return pmdv;
		}
		
		Long sourceId = null;
		ResultView resultView = sourceService.getMasterSourceId(memberId, sourceTypeCode);
		if (resultView.getResultCode() == AppConstants.SUCCESS){
			sourceId = Long.parseLong(resultView.getResultMessage());
		}
		
		if (sourceId != null){
			/*
			 * Check to see which period type user wants
			 */
			getPersonalMotionInRangeHelper(sourceId, sourceTypeCode, periodType, pmdv, start, end);
			
		}else{
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "This member doesnt have a source for " + sourceTypeCode);
			pmdv.setResult(rv);
			return pmdv;
		}
		
		return pmdv;
	}
	
	private void getPersonalMotionInRangeHelper(Long sourceId, String sourceTypeCode, String periodType, PersonalMotionDailyView pmdv, Date startDate, Date endDate){
		List<PersonalMotionView> pmvs = new ArrayList<PersonalMotionView>();
		
		/*
		 * Variables to keep track total
		 */
		int totalCalories = 0;
		int totalSteps = 0;
		double totalDuration = 0.0;
		double totalDistance = 0.0;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		
		if (periodType.equals(AppConstants.PERIOD_TYPE_DAILY)){
			Calendar start = Calendar.getInstance();
			start.setTime(startDate);
			Calendar end = Calendar.getInstance();
			end.setTime(endDate);
			
			for (Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
				PersonalMotionView pmv = new PersonalMotionView();
				
				/*
				 * Variables to keep track parts
				 */
				int calories = 0;
				int steps = 0;
				double duration = 0.0;
				double distance = 0.0;
				
				/*
				 * Case where source type code is walking
				 */
				if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_WALKING)){
					WalkingDay walkingDatas = personalMotionDao.getWalkingDay(sourceId, date);
					//adding steps and calories
					
					if (walkingDatas != null){
						steps += walkingDatas.getSteps();
						calories += walkingDatas.getCalories();
						totalCalories += walkingDatas.getCalories();
						totalSteps += walkingDatas.getSteps();
						totalDuration += walkingDatas.getDuration().intValue();
						duration += walkingDatas.getDuration();
						distance += walkingDatas.getDistance();
						totalDistance += walkingDatas.getDistance().intValue();
						
					}
					pmv.setId(date.getTime());
					pmv.setDuration(format.format(date));
				
				/*
				 * Case where source type code is running
				 */
				}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_RUNNING)){
					RunningDataDay runningDatas = personalMotionDao.getRunningDay( sourceId, date);
					//adding steps and calories
					
					if (runningDatas != null){
						steps += runningDatas.getSteps();
						calories += runningDatas.getCalories();
						totalCalories += runningDatas.getCalories();
						totalSteps += runningDatas.getSteps();
						totalDuration += runningDatas.getDuration().intValue();
						duration += runningDatas.getDuration();
						distance += runningDatas.getDistance();
						totalDistance += runningDatas.getDistance().intValue();
						
					}
					pmv.setId(date.getTime());
					pmv.setDuration(format.format(date));
				
				/*
				 * Case where source type code is cycling 
				 */
				}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)){
					CyclingDataDay cyclingDatas = personalMotionDao.getCyclingDay(sourceId, date);
					//adding steps and calories
					
					if (cyclingDatas != null){
						calories += cyclingDatas.getCalories();
						totalCalories += cyclingDatas.getCalories();
						totalDuration += cyclingDatas.getDuration().intValue();
						duration += cyclingDatas.getDuration();
						distance += cyclingDatas.getDistance();
						totalDistance += cyclingDatas.getDistance().intValue();
						
					}
					pmv.setId(date.getTime());
					pmv.setDuration(format.format(date));
				}
				
				pmv.setDistance(distance);
				pmv.setDurationInNumber((double) duration/60.0);
				pmv.setCalories(calories);
				
				/*
				 * Include steps only if source type is not cycling
				 */
				if (!sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)) pmv.setSteps(steps);
				
				pmvs.add(pmv);
			}
			
			
		}else if (periodType.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
			/*
			 * Set start date and end date to beginning of month
			 */
			startDate = DateParserUtil.getBeginningOfMonth(startDate);
			endDate = DateParserUtil.getBeginningOfMonth(endDate);
			
			Calendar start = Calendar.getInstance();
			start.setTime(startDate);
			Calendar end = Calendar.getInstance();
			end.setTime(endDate);
			
			for (Date date = start.getTime(); !start.after(end); start.add(Calendar.MONTH, 1), date = start.getTime()) {
				PersonalMotionView pmv = new PersonalMotionView();
				
				/*
				 * Variables to keep track parts
				 */
				int calories = 0;
				int steps = 0;
				double duration = 0.0;
				double distance = 0.0;
				
				/*
				 * Case where source type code is walking
				 */
				if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_WALKING)){
					WalkingMonth walkingDatas = personalMotionDao.getWalkingMonth(sourceId, date);
					//adding steps and calories
					
					if (walkingDatas != null){
						steps += walkingDatas.getSteps();
						calories += walkingDatas.getCalories();
						totalCalories += walkingDatas.getCalories();
						totalSteps += walkingDatas.getSteps();
						totalDuration += walkingDatas.getDuration().intValue();
						duration += walkingDatas.getDuration();
						distance += walkingDatas.getDistance();
						totalDistance += walkingDatas.getDistance().intValue();
						
					}
					pmv.setId(date.getTime());
					pmv.setDuration(format.format(date));
				
				/*
				 * Case where source type code is running
				 */
				}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_RUNNING)){
					RunningDataMonth runningDatas = personalMotionDao.getRunningMonth(sourceId, date);
					//adding steps and calories
					
					if (runningDatas != null){
						steps += runningDatas.getSteps();
						calories += runningDatas.getCalories();
						totalCalories += runningDatas.getCalories();
						totalSteps += runningDatas.getSteps();
						totalDuration += runningDatas.getDuration().intValue();
						duration += runningDatas.getDuration();
						distance += runningDatas.getDistance();
						totalDistance += runningDatas.getDistance().intValue();
						
					}
					pmv.setId(date.getTime());
					pmv.setDuration(format.format(date));
				
				/*
				 * Case where source type code is cycling 
				 */
				}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)){
					CyclingDataMonth cyclingDatas = personalMotionDao.getCyclingMonth(sourceId, date);
					//adding steps and calories
					
					if (cyclingDatas != null){
						steps += cyclingDatas.getSteps();
						calories += cyclingDatas.getCalories();
						totalCalories += cyclingDatas.getCalories();
						totalSteps += cyclingDatas.getSteps();
						totalDuration += cyclingDatas.getDuration().intValue();
						duration += cyclingDatas.getDuration();
						distance += cyclingDatas.getDistance();
						totalDistance += cyclingDatas.getDistance().intValue();
						
					}
					pmv.setId(date.getTime());
					pmv.setDuration(format.format(date));
				}
				
				pmv.setDistance(distance);
				pmv.setDurationInNumber((double) duration/60.0);
				pmv.setCalories(calories);
				/*
				 * Include steps only if source type is not cycling
				 */
				if (!sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)) pmv.setSteps(steps);
				pmvs.add(pmv);
			}
		}
		
		pmdv.setPersonalMotionViews(pmvs);
		pmdv.setCalories(totalCalories);
		/*
		 * Include steps only if source type is not cycling
		 */
		if (!sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)) pmdv.setSteps(totalSteps);
		pmdv.setDistance(totalDistance);
		pmdv.setDuration(String.valueOf((totalDuration/60)));
		
	}
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public PersonalMotionDailyView getPersonalMotionData(Long accountId, String sourceTypeCode, String periodType, Integer year, Integer month, Integer day){
		System.out.println("getPersonalMotionData : " + new Date());
		
		PersonalMotionDailyView pmdv = new PersonalMotionDailyView();
		
		if (accountId == null || sourceTypeCode == null || periodType == null || year == null || month == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			pmdv.setResult(rv);
			return pmdv;
		}
		
		if (!(sourceTypeCode.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_CYCLING) || sourceTypeCode.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_RUNNING)||
				sourceTypeCode.toUpperCase().equals(AppConstants.SOURCETYPE_CODE_WALKING))){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid source type");
			pmdv.setResult(rv);
			return pmdv;
		}
		
		List<PersonalMotionView> pmvs = new ArrayList<PersonalMotionView>();
		
		Long sourceId = null;
		ResultView rv = sourceService.getMasterSourceId(accountId, sourceTypeCode);
		if (rv.getResultCode() == AppConstants.SUCCESS){
			sourceId = Long.parseLong(rv.getResultMessage());
		}
		
		System.out.println("Source ID " + sourceId);
		int totalCalories = 0;
		int totalSteps = 0;
		int totalDuration = 0;
		double totalDistance = 0;
		
		if ( sourceId != null){
			Calendar calendar = new GregorianCalendar();
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month);
			if (day != null) calendar.set(Calendar.DAY_OF_MONTH, day);
			
			Date requestTime = calendar.getTime();
			
			if (periodType.equals(AppConstants.PERIOD_TYPE_DAILY)){
			
				//Get List of Hourly Data with matching year, month, day
				Date startTime = DateParserUtil.getBeginningOfDay(requestTime);
				Date endTime = DateParserUtil.getEndOfHour(startTime);
				
				int counter = 0;
				while (counter < 24){
					PersonalMotionView pmv = new PersonalMotionView();
					int steps = 0;
					int calories = 0;
					double distance = 0;
					int duration = 0;
					
					if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_WALKING)){
						List<WalkingHour> walkingDatas = personalMotionDao.getWalkingByInterval(sourceId, startTime, endTime);
						//adding steps and calories
						
						if (walkingDatas != null && walkingDatas.size() > 0){
							for (WalkingHour walking : walkingDatas){
								steps += walking.getSteps();
								calories += walking.getCalories();
								totalCalories += walking.getCalories();
								totalSteps += walking.getSteps();
								totalDuration += walking.getDuration().intValue();
								duration += walking.getDuration().intValue();
								distance += walking.getDistance();
								totalDistance += walking.getDistance().intValue();
							}
						}
						if (counter % 12 == 0){
							pmv.setDuration(DateParserUtil.getHourValue(startTime) + " " + DateParserUtil.getAMPMValue(startTime));
						}else{
							pmv.setDuration(DateParserUtil.getHourValue(startTime)+"");
						}
					}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_RUNNING)){
						List<RunningDataHour> runningDatas = personalMotionDao.getRunningByInterval( sourceId, startTime, endTime);
						//adding steps and calories
						
						if (runningDatas != null && runningDatas.size() > 0){
							for (RunningDataHour running : runningDatas){
								steps += running.getSteps();
								calories += running.getCalories();
								totalCalories += running.getCalories();
								totalSteps += running.getSteps();
								totalDuration += running.getDuration().intValue();
								duration += running.getDuration().intValue();
								distance += running.getDistance();
								totalDistance += running.getDistance().intValue();
							}
						}
						if (counter % 12 == 0){
							pmv.setDuration(DateParserUtil.getHourValue(startTime) + " " + DateParserUtil.getAMPMValue(startTime));
						}else{
							pmv.setDuration(DateParserUtil.getHourValue(startTime)+"");
						}
					}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)){
						List<CyclingDataHour> cyclingDatas = personalMotionDao.getCyclingByInterval(sourceId, startTime, endTime);
						//adding steps and calories
						
						if (cyclingDatas != null && cyclingDatas.size() > 0){
							for (CyclingDataHour cycling : cyclingDatas){
								steps += cycling.getSteps();
								calories += cycling.getCalories();
								totalCalories += cycling.getCalories();
								totalSteps += cycling.getSteps();
								totalDuration += cycling.getDuration().intValue();
								duration += cycling.getDuration().intValue();
								distance += cycling.getDistance();
								totalDistance += cycling.getDistance().intValue();
							}
						}
						if (counter % 12 == 0){
							pmv.setDuration(DateParserUtil.getHourValue(startTime) + " " + DateParserUtil.getAMPMValue(startTime));
						}else{
							pmv.setDuration(DateParserUtil.getHourValue(startTime)+"");
						}
					}
					
					//round distance and minute
					distance = (double)(Math.round(distance));
					duration = (duration/60);
					
					pmv.setDistance(distance);
					pmv.setDurationInNumber((double) duration);
					pmv.setId((long)counter);
					pmv.setCalories(calories);
					pmv.setSteps(steps);
					pmvs.add(pmv);
					counter++;
					startTime = new Date(endTime.getTime() + 1l);
					endTime = DateParserUtil.getEndOfHour(startTime);
				}
				
			}else if (periodType.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
				//Get List of Daily Data with matching year, month
				Date startTime = DateParserUtil.getBeginningOfMonth(requestTime);
				Date endTime = DateParserUtil.getEndOfDay(startTime);
				
				//Get current Month end date
				Integer dayCount = calendar.getActualMaximum(Calendar.DATE);
				int counter = 1;
				
				while (counter <= dayCount){
					PersonalMotionView pmv = new PersonalMotionView();
					int steps = 0;
					int calories = 0;
					double distance = 0;
					int duration = 0;
					
					if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_WALKING)){
						List<WalkingDay> walkingDatas = personalMotionDao.getWalkingDayByInterval(sourceId, startTime, endTime);
						//adding steps and calories
						
						if (walkingDatas != null && walkingDatas.size() > 0){
							for (WalkingDay walking : walkingDatas){
								steps += walking.getSteps();
								calories += walking.getCalories();
								totalCalories += walking.getCalories();
								totalSteps += walking.getSteps();
								totalDuration += walking.getDuration().intValue();
								duration += walking.getDuration().intValue();
								distance += walking.getDistance();
								totalDistance += walking.getDistance().intValue();
							}
						}
						pmv.setDuration(DateParserUtil.getDayValue(startTime)+"");
						
					}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_RUNNING)){
						List<RunningDataDay> runningDatas = personalMotionDao.getRunningDayByInterval(sourceId, startTime, endTime);
						//adding steps and calories
						
						if (runningDatas != null && runningDatas.size() > 0){
							for (RunningDataDay running : runningDatas){
								steps += running.getSteps();
								calories += running.getCalories();
								totalCalories += running.getCalories();
								totalSteps += running.getSteps();
								totalDuration += running.getDuration().intValue();
								duration += running.getDuration().intValue();
								distance += running.getDistance();
								totalDistance += running.getDistance().intValue();
							}
						}
						pmv.setDuration(DateParserUtil.getDayValue(startTime)+"");
					}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)){
						List<CyclingDataDay> cyclingDatas = personalMotionDao.getCyclingDayByInterval(sourceId, startTime, endTime);
						//adding steps and calories
						
						if (cyclingDatas != null && cyclingDatas.size() > 0){
							for (CyclingDataDay cycling : cyclingDatas){
								steps += cycling.getSteps();
								calories += cycling.getCalories();
								totalCalories += cycling.getCalories();
								totalSteps += cycling.getSteps();
								totalDuration += cycling.getDuration().intValue();
								duration += cycling.getDuration().intValue();
								distance += cycling.getDistance();
								totalDistance += cycling.getDistance().intValue();
							}
						}
						pmv.setDuration(DateParserUtil.getDayValue(startTime)+"");
					}
					
					pmv.setDistance(distance);
					pmv.setDurationInNumber((double)(duration/60));
					pmv.setId((long)counter);
					pmv.setCalories(calories);
					pmv.setSteps(steps);
					pmvs.add(pmv);
					counter++;
					startTime = new Date(endTime.getTime() + 1l);
					endTime = DateParserUtil.getEndOfDay(startTime);
				}
				
			}
			
		}
		//Get Hourly Baseline for 
		List<WalkingBoundary> boundaries = personalMotionDao.getWalkingBoundaries(AppConstants.PERIOD_TYPE_HOURLY);
		
		pmdv.setWalkingBaselineView(boundaries);
		
		pmdv.setPersonalMotionViews(pmvs);
		pmdv.setCalories(totalCalories);
		pmdv.setSteps(totalSteps);
		pmdv.setDistance(totalDistance);
		pmdv.setDuration(String.valueOf((totalDuration/60)));
		
		/*
		 * Get baseline for this user
		 */
		pmdv.setBaselineView(getMemberBaseline(sourceId, sourceTypeCode));
		return pmdv;
	}
	
	/**
	 * Get member's baseline for a given source type
	 * 
	 * @param sourceId
	 * @param sourceTypeCode
	 * @return baseline view
	 */
	public BaselineView getMemberBaseline(Long sourceId, String sourceTypeCode){
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
	public DrivingSummaryView getDrivingData(Long memberId, String periodType, Integer year, Integer month, Integer day){
		System.out.println("GET Driving Data : " + new Date());
		DrivingSummaryView dsv = new DrivingSummaryView();
		List<DrivingView> drivingViews = new ArrayList<DrivingView>();
		
		if (memberId == null || periodType == null || year == null || month == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid parameter");
			dsv.setResult(rv);
			return dsv;
		}
		
		Long sourceId = null;
		ResultView rv = sourceService.getMasterSourceId(memberId, AppConstants.SOURCETYPE_CODE_DRIVING);
		if (rv.getResultCode() == AppConstants.SUCCESS){
			sourceId = Long.parseLong(rv.getResultMessage());
		}
		
		double totalFuelEfficiency = 0.0;
		double totalDistance = 0.0;
		double totalDuration = 0.0;
		double totalFuelConsumption = 0.0;
		int dataCounter = 0;
		if (sourceId != null){
			
			Calendar calendar = new GregorianCalendar();
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month);
			if (day != null) calendar.set(Calendar.DAY_OF_MONTH, day);
			
			Date requestTime = calendar.getTime();
			
			if (periodType.equals(AppConstants.PERIOD_TYPE_DAILY)){
				
				//Get List of Hourly Data with matching year, month, day
				Date startTime = DateParserUtil.getBeginningOfDay(requestTime);
				Date endTime = DateParserUtil.getEndOfHour(startTime);
				
				int counter = 0;
				
				while (counter < 24){
					DrivingView dv = new DrivingView();
					
					DrivingHour ddh = drivingDao.getDrivingDataHour(sourceId, startTime);
					
					if (ddh != null){
						dv.setDistance(ddh.getDistance());
						dv.setDurationInNumber((double)(ddh.getDuration()/60));
						dv.setId(ddh.getId());
						dv.setTripCount(ddh.getTripCount());
						dv.setMaxSpeed(ddh.getMaxSpeed());
						dv.setMaxRPM(ddh.getMaxRPM());
						dv.setMaxAcceleration(ddh.getMaxAcceleration());
						dv.setMaxDesceleration(ddh.getMaxDesceleration());
						dv.setFuel(ddh.getFuel());
						dv.setFuelEfficiency(ddh.getFuelEfficiency());
						
						totalFuelEfficiency += ddh.getFuelEfficiency();
						totalDistance += ddh.getDistance();
						totalDuration += ddh.getDuration();
						totalFuelConsumption += ddh.getFuel();
						
						dataCounter++;
						
					}else{
						dv.setDistance(0.0);
						dv.setDurationInNumber((double)0);
						dv.setId((long)counter);
						dv.setTripCount(0);
						dv.setMaxSpeed(0.0);
						dv.setMaxRPM(0.0);
						dv.setMaxAcceleration(0.0);
						dv.setMaxDesceleration(0.0);
						dv.setFuel(0.0);
						dv.setFuelEfficiency(0.0);
					}
					
					if (counter % 12 == 0){
						dv.setDuration(DateParserUtil.getHourValue(startTime) + " " + DateParserUtil.getAMPMValue(startTime));
					}else{
						dv.setDuration(DateParserUtil.getHourValue(startTime)+"");
					}
					
					counter++;
					startTime = new Date(endTime.getTime() + 1l);
					endTime = DateParserUtil.getEndOfHour(startTime);
					
					drivingViews.add(dv);
				}
				
			}else if (periodType.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
				Date startTime = DateParserUtil.getBeginningOfMonth(requestTime);
				Date endTime = DateParserUtil.getEndOfDay(startTime);
				
				//Get current Month end date
				Integer dayCount = calendar.getActualMaximum(Calendar.DATE);
				int counter = 1;
				
				while (counter <= dayCount){
					DrivingView dv = new DrivingView();
					
					DrivingDay ddh = drivingDao.getDrivingDataDay(sourceId, startTime);
					
					System.out.println("StartTime : " + startTime + " sourceId " + sourceId );
					if (ddh != null){
						System.out.println("DDH " + ddh.getDistance());
						dv.setDistance(ddh.getDistance());
						dv.setDurationInNumber((double)(ddh.getDuration()/60));
						dv.setId(ddh.getId());
						dv.setTripCount(ddh.getTripCount());
						dv.setMaxSpeed(ddh.getMaxSpeed());
						dv.setMaxRPM(ddh.getMaxRPM());
						dv.setMaxAcceleration(ddh.getMaxAcceleration());
						dv.setMaxDesceleration(ddh.getMaxDesceleration());
						dv.setFuel(ddh.getFuel());
						dv.setFuelEfficiency(ddh.getFuelEfficiency());
						
						totalFuelEfficiency += ddh.getFuelEfficiency();
						totalDistance += ddh.getDistance();
						totalDuration += ddh.getDuration();
						totalFuelConsumption += ddh.getFuel();
						
						dataCounter++;
						
					}else{
						dv.setDistance(0.0);
						dv.setDurationInNumber((double)0);
						dv.setId((long)counter);
						dv.setTripCount(0);
						dv.setMaxSpeed(0.0);
						dv.setMaxRPM(0.0);
						dv.setMaxAcceleration(0.0);
						dv.setMaxDesceleration(0.0);
						dv.setFuel(0.0);
						dv.setFuelEfficiency(0.0);
					}
					
					dv.setDuration(DateParserUtil.getDayValue(startTime)+"");
					
					counter++;
					startTime = new Date(endTime.getTime() + 1l);
					endTime =  DateParserUtil.getEndOfDay(startTime);
					
					drivingViews.add(dv);
				}
				
				
			}
		}
		
		if (dataCounter > 0) totalFuelEfficiency /= dataCounter;
		
		//Round up
		totalDistance = (double)(Math.round(totalDistance));
		totalFuelEfficiency = (double)(Math.round(totalFuelEfficiency * 10)) / 10;
		totalDuration = (double)(Math.round(totalDuration/3600 * 10)) / 10;
		totalFuelConsumption = (double)(Math.round(totalFuelConsumption * 10)) /10;
		
		dsv.setDistance(totalDistance);
		dsv.setDrivingViews(drivingViews);
		dsv.setEfficiency(totalFuelEfficiency);
		dsv.setFuelConsumption(totalFuelConsumption);
		dsv.setDuration(totalDuration);
		return dsv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ActivityViews getActivities(Long memberId){
		
		ActivityViews activityViews = new ActivityViews();
	
		List<ActivityView> avs = new ArrayList<ActivityView>();
		
		if (memberId == null) {
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid parameter");
			activityViews.setResult(rv);
			return activityViews;
		}
		//Check if activity
		ActivityView electricity = getElectricityActivity(memberId);
		if (electricity != null) avs.add(electricity);
		
		ActivityView walking = getWalkingActivity(memberId);
		if (walking != null) avs.add(walking);
		
		ActivityView driving = getDrivingActivity(memberId);
		if (driving != null) avs.add(driving);
		
		if (avs.isEmpty()){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "No activities under the member");
			activityViews.setResult(rv);
			
		}else{
			activityViews.setActivityList(avs);
		}
		return activityViews;
	}
	
	private ActivityView getDrivingActivity(Long memberId){
		ActivityView driving = null;
		//Check if a user has electricity source
		Long sourceId = null;
		ResultView rv = sourceService.getMasterSourceId(memberId, AppConstants.SOURCETYPE_CODE_DRIVING);
		if (rv.getResultCode() == AppConstants.SUCCESS){
			sourceId = Long.parseLong(rv.getResultMessage());
		}
				
		if (sourceId != null){
			driving = new ActivityView();
			//ActivityView
			driving.setHeading("MY DRIVING - TODAY");
			driving.setSourceType(3);
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			
			DrivingSummaryView dsv = getDrivingData(memberId, AppConstants.PERIOD_TYPE_DAILY, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			
			double distance = 0.0;
			double fuelEfficiency = 0.0;
			double fuelConsumption = 0.0;
			double duration = 0.0;
			
			if (dsv != null){
				distance = dsv.getDistance();
				fuelEfficiency = dsv.getEfficiency();
				fuelConsumption = dsv.getFuelConsumption();
				duration = dsv.getDuration();
				
			}
			
			driving.setActual((double)(Math.round(distance)));
			driving.setGoal(200.0);
			driving.setProgress(distance/200.0 * 100);
			
			//Cost unit
			Unit distanceUnit = unitDao.getUnitByCode(AppConstants.UNIT_KILOMETER);
			
			if (distanceUnit != null){
				driving.setUnitDescription(distanceUnit.getDescription());
				driving.setUnitImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(distanceUnit.getImageUrl()));
				driving.setUnitName(distanceUnit.getName());
			}
			
			//Gotta create 3 subview
			ActivitySubView fuelEfficiencyView = new ActivitySubView();
			ActivitySubView durataionView = new ActivitySubView();
			ActivitySubView fuelConsumptionView = new ActivitySubView();
			
			fuelEfficiencyView.setActual( (double)(Math.round(fuelEfficiency*10)/10));
			//distance unit
			Unit fuelEfficiencyUnit = unitDao.getUnitByCode(AppConstants.UNIT_FUEL_EFFICIENCY);
			
			if (fuelEfficiencyUnit != null){
				fuelEfficiencyView.setUnitDescription(fuelEfficiencyUnit.getDescription());
				fuelEfficiencyView.setUnitImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(fuelEfficiencyUnit.getImageUrl()));
				fuelEfficiencyView.setUnitName(fuelEfficiencyUnit.getName());
			}
			
			durataionView.setActual(duration);
			
			Unit activeUnit = unitDao.getUnitByCode(AppConstants.UNIT_HOUR);
			
			if (activeUnit != null){
				durataionView.setUnitDescription(activeUnit.getDescription());
				durataionView.setUnitImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(activeUnit.getImageUrl()));
				durataionView.setUnitName(activeUnit.getName());
			}
			
			fuelConsumptionView.setActual((double)(Math.round(fuelConsumption*10)/10));
			
			Unit literUnit = unitDao.getUnitByCode(AppConstants.UNIT_LITER);
			
			if (literUnit != null){
				fuelConsumptionView.setUnitDescription(literUnit.getDescription());
				fuelConsumptionView.setUnitImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(literUnit.getImageUrl()));
				fuelConsumptionView.setUnitName(literUnit.getName());
			}
			
			driving.getActivitySubViews().add(fuelEfficiencyView);
			driving.getActivitySubViews().add(durataionView);
			driving.getActivitySubViews().add(fuelConsumptionView);
			
		}
		
		return driving;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ElectricitySummaryView getElectricityData(Long memberId, String periodType, Integer year, Integer month, Integer day){
		System.out.println("Get Electricity Data " + new Date());
		
		ElectricitySummaryView esv = new ElectricitySummaryView();
		
		if (memberId == null || periodType == null || year == null || month == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid parameter");
			esv.setResult(rv);
			return esv;
		}
		
		Long sourceId = null;
		ResultView rv = sourceService.getMasterSourceId(memberId, AppConstants.SOURCETYPE_CODE_ELECTRICITY);
		if (rv.getResultCode() == AppConstants.SUCCESS){
			sourceId = Long.parseLong(rv.getResultMessage());
		}
		
		if (sourceId != null){
			//Get Electricity Data - User sub method
			List<ElectricityView> currentElectricity = getHistoryElectricityData(sourceId, periodType, year, month, day, false);
			esv.setCurrentElectricityViews(currentElectricity);
			
			List<ElectricityView> previousElectricity = getHistoryElectricityData(sourceId, periodType, year, month, day, true);
			esv.setPrevisouElectricityViews(previousElectricity);
			
			
			//Get Gauge Data
			esv.setConsumptionGaugeView(getElectricityGauge(memberId, sourceId, periodType, year, month, day, 1));
			esv.setCostGaugeView(getElectricityGauge(memberId, sourceId, periodType, year, month, day, 2));
			
			double offPeak = 0.0;
			double midPeak = 0.0;
			double onPeak = 0.0;
			double totalConsumption = 0.0;
			double totalSpending = 0.0;
			double potentialSaving = 0.0;
			
			double currentRateSpending = 0.0;
			int counter = 0;
			if (!currentElectricity.isEmpty()){
				for (ElectricityView ev : currentElectricity){
					offPeak += ev.getOffPeakConsumption();
					midPeak += ev.getMidPeakConsumption();
					onPeak += ev.getOnPeakConsumption();
					
					totalConsumption += ev.getConsumption();
					totalSpending += ev.getCost();
					
					if (ev.getConsumption() != 0) counter++;
				}
				
				if (counter > 0 ) currentRateSpending = totalSpending/counter;
				potentialSaving = totalSpending * 0.12;
			}
			
			if (periodType.equals(AppConstants.PERIOD_TYPE_DAILY)){
				//This is day
				potentialSaving *= 30 * 12; 
			}else if(periodType.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
				potentialSaving *= 12;
			}
			
			double previousSpending = 0.0;
			double previousRateSpending = 0.0;
			counter = 0;
			
			
			if (!previousElectricity.isEmpty()){
				for (ElectricityView ev: previousElectricity){
					previousSpending += ev.getCost();
					
					if (ev.getConsumption() != 0) counter++;
				}
				
				if (counter > 0 ) previousRateSpending = previousSpending/ counter;
			}
			
			Double rate = 0.0;
			
			if (currentRateSpending > 0 ) rate = (currentRateSpending - previousRateSpending)/currentRateSpending;
			
			esv.setRate(rate);
			//System.out.println("previous :: " + previousSpending + " :: current Total :: " + totalSpending + " :: " + rate);
			String trend = AppConstants.APACHE_IMAGE_LINK + "/images/equal-arrow-grey.png";
			if (rate >0) trend = AppConstants.APACHE_IMAGE_LINK + "/images/up-arrow-grey.png";
			if (rate <0){
				rate *= -1;
				trend = AppConstants.APACHE_IMAGE_LINK + "/images/down-arrow-grey.png";
			}
			
			rate *=100;
			
			esv.setOnPeak(onPeak);
			esv.setMidPeak(midPeak);
			esv.setOffPeak(offPeak);
			
			esv.setOnPeakRate(12.4);
			esv.setMidPeakRate(10.4);
			esv.setOffPeakRate(6.7);
			
			//round up
			
			totalConsumption = (double)(Math.round((totalConsumption * 10))) / 10;
			totalSpending = (double)(Math.round((totalSpending * 100)))/100;
			rate = (double) (Math.round(rate));
			potentialSaving = (double)(Math.round(potentialSaving));
			
			
			esv.setTotalConsumption(totalConsumption);
			esv.setTotalCost(totalSpending);
			esv.setCompareToPrevious(rate);
			esv.setTrendImageUrl(trend);
			esv.setPotentialSaving(potentialSaving);
			
		}else{
			ResultView resultView = new ResultView();
			resultView.fill(AppConstants.FAILURE, "This user does not have electricity source.");
			esv.setResult(resultView);
			return esv;
		}
		
		System.out.println("ESV :: " + esv.getPotentialSaving());
		return esv;
	}
	
private List<ElectricityView> getHistoryElectricityData(Long sourceId, String periodType, Integer year, Integer month, Integer day, Boolean isPrevious){
		
		System.out.println("Month : " + month + " Day : " + day);
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		if (day != null) calendar.set(Calendar.DAY_OF_MONTH, day);
		
		if (isPrevious){
			if (periodType.equals(AppConstants.PERIOD_TYPE_DAILY)) calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
			if (periodType.equals(AppConstants.PERIOD_TYPE_MONTHLY)) calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		}
		Date requestTime = calendar.getTime();
		
		List<ElectricityView> evs = new ArrayList<ElectricityView>();
		
		if (periodType.equals(AppConstants.PERIOD_TYPE_DAILY)){
			Date startTime = DateParserUtil.getBeginningOfDay(requestTime);
			
			/*
			 * To get last day's midnight electricity consumption
			 */
			startTime = DateParserUtil.getBeginningOfHour(new Date(startTime.getTime()-1));
			Date endTime = DateParserUtil.getEndOfHour(startTime);
			
			/*
			 * Get 24 hours of data
			 */
			List<ElectricityDataHour> edhs = electricityDao.findElectricityHourByInterval(sourceId, startTime, endTime);
			
			/*
			//Check first available hour in a day
			int firstAvailable = 24;
			int lastAvailable = 24;
			
			System.out.println(edhs.get(0).getCreated());
			
			if (!edhs.isEmpty()){
				firstAvailable = DateParserUtil.getHourOfDayValue(edhs.get(0).getCreated());
				lastAvailable = DateParserUtil.getHourOfDayValue(edhs.get(edhs.size()-1).getCreated());
			}
			
			System.out.println("First Available :: " + firstAvailable + " Last Available :: " + lastAvailable);
			//For unavailable data we make it 0
			 
			for (int i = 0; i < firstAvailable; i++){
				ElectricityView ev = new ElectricityView();
				
				if (i % 12 == 0){
					String AMPM = "AM";
					if (i == 12) AMPM = "PM";
					ev.setAggregationStartDate(12 + " " + AMPM);
				}else{
					ev.setAggregationStartDate(i % 12 +"");
				}
				
				evs.add(ev);
				
			}
			
			int lastHourValue = firstAvailable;
			for(ElectricityDataHour edh : edhs){
				int getHourValue = DateParserUtil.getHourOfDayValue(edh.getCreated());
				if (getHourValue - lastHourValue >= 2){
					
					System.out.println("Filling the gap... " + getHourValue +  " : " + lastHourValue);
					//For gap between hours add data
					 
					for (int i = lastHourValue+1 ; i < getHourValue; i++){
						ElectricityView ev = new ElectricityView();
						
						if (i % 12 == 0){
							String AMPM = "AM";
							if (i == 12) AMPM = "PM";
							ev.setAggregationStartDate(12 + " " + AMPM);
						}else{
							ev.setAggregationStartDate(i % 12 +"");
						}
						
						evs.add(ev);
					}
				}
				lastHourValue = getHourValue;
				
				ElectricityView ev = new ElectricityView();
				
				if (edh != null){
					System.out.println("Adding...! " + getHourValue);
					ev.setConsumption(Math.round( edh.getEnergy()/1000* 100.0 ) / 100.0);
					//Energy is in Wh 
					ev.setCost(Math.round( (edh.getOnPeakSpend() + edh.getMidPeakSpend() + edh.getOffPeakSpend())* 100.0 ) / 100.0);
					ev.setOnPeakConsumption(edh.getOnPeakConsumption());
					ev.setMidPeakConsumption(edh.getMidPeakConsumption());
					ev.setOffPeakConsumption(edh.getOffPeakConsumption());
				}
				
				if (getHourValue % 12 == 0){
					String AMPM = "AM";
					if (getHourValue == 12) AMPM = "PM";
					ev.setAggregationStartDate(12 + " " + AMPM);
				}else{
					ev.setAggregationStartDate(getHourValue % 12 +"");
				}
				
				evs.add(ev);
				
			}
			
			for (int i = lastAvailable+1; i <24; i++){
				ElectricityView ev = new ElectricityView();
				
				if (i % 12 == 0){
					String AMPM = "AM";
					if (i == 12) AMPM = "PM";
					ev.setAggregationStartDate(12 + " " + AMPM);
				}else{
					ev.setAggregationStartDate(i % 12 +"");
				}
				
				evs.add(ev);
			}
			*/
			
			
			int counter = 0;
			while (counter <= 24){
				ElectricityView ev = new ElectricityView();
				ElectricityDataHour edh = electricityDao.findElectricityDataHour(sourceId, startTime);
				
				if (edh != null){
					ev.setConsumption(Math.round( edh.getEnergy()/1000* 100.0 ) / 100.0);
					//Energy is in Wh 
					ev.setCost(Math.round( (edh.getOnPeakSpend() + edh.getMidPeakSpend() + edh.getOffPeakSpend())* 100.0 ) / 100.0);
					ev.setOnPeakConsumption(edh.getOnPeakConsumption());
					ev.setMidPeakConsumption(edh.getMidPeakConsumption());
					ev.setOffPeakConsumption(edh.getOffPeakConsumption());
				}
				
				if (counter % 12 == 0){
					ev.setAggregationStartDate(DateParserUtil.getHourValue(new Date(endTime.getTime() +1l)) + " " + DateParserUtil.getAMPMValue(new Date(endTime.getTime() +1l)));
				}else{
					ev.setAggregationStartDate(DateParserUtil.getHourValue(new Date(endTime.getTime() +1l))+"");
				}
				
				evs.add(ev);
				
				counter++;
				startTime = new Date(endTime.getTime() + 1l);
				endTime = DateParserUtil.getEndOfHour(startTime);
				
			}
		}else if (periodType.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
			Date startTime = DateParserUtil.getBeginningOfMonth(requestTime);
			Date endTime = DateParserUtil.getEndOfDay(startTime);
			
			Integer dayCount = calendar.getActualMaximum(Calendar.DATE);
			int counter = 1;
			
			while (counter <= dayCount){
				ElectricityView ev = new ElectricityView();
				ElectricityDataDay edd = electricityDao.findElectricityDataDay(sourceId, startTime);
				
				if (edd != null){
					ev.setConsumption(Math.round( edd.getEnergy()/1000* 100.0 ) / 100.0);
					//Energy is in Wh 
					ev.setCost(Math.round( (edd.getOnPeakSpend() + edd.getMidPeakSpend() + edd.getOffPeakSpend())* 100.0 ) / 100.0);
					ev.setOnPeakConsumption(edd.getOnPeakConsumption());
					ev.setMidPeakConsumption(edd.getMidPeakConsumption());
					ev.setOffPeakConsumption(edd.getOffPeakConsumption());
				}
				
				ev.setAggregationStartDate(DateParserUtil.getDayValue(startTime)+"");
				evs.add(ev);
				
				counter++;
				startTime = new Date(endTime.getTime() + 1l);
				endTime = DateParserUtil.getEndOfDay(startTime);
			}
			
		}
		
		return evs;
	}
	
	private Double getSumValue(String period, Long sourceId, Integer viewType, Date requestDate, Integer dayCount){
		Double totalValue = null;
		Date endDate = new Date();
		Date startDate = null;
		Integer counts = null;
		
		if (period.equals(AppConstants.PERIOD_TYPE_DAILY)){
			startDate = DateParserUtil.getBeginningOfDay(requestDate);
			endDate = DateParserUtil.getEndOfDay(startDate);
			
			if (viewType == 1){
				totalValue = electricityDao.findSumElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, sourceId, startDate, endDate);
				counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, sourceId, startDate, endDate);
			 
				if (counts != null && counts != 24){
					totalValue  = totalValue / counts * 24;
				}
			}else if (viewType == 2){
				totalValue = electricityDao.findSumCostElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, sourceId, startDate, endDate);
				counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, sourceId, startDate, endDate);
				
				if (counts != null && counts != 24){
					totalValue = totalValue / counts * 24;
				}
			}
			
		}else if (period.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
			startDate = DateParserUtil.getBeginningOfMonth(requestDate);
			endDate = DateParserUtil.getEndOfMonth(startDate);
			
			if (viewType == 1){
				totalValue = electricityDao.findSumElectricity(AppConstants.TABLE_ELECTRICITY_DATA_DAY, sourceId, startDate, endDate);
				counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_DAY, sourceId, startDate, endDate);
			 
				if (counts != null && counts != dayCount){
					totalValue  = totalValue / counts * dayCount;
				}
			}else if (viewType == 2){
				totalValue = electricityDao.findSumCostElectricity(AppConstants.TABLE_ELECTRICITY_DATA_DAY, sourceId, startDate, endDate);
				counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_DAY, sourceId, startDate, endDate);
				
				if (counts != null && counts != dayCount){
					totalValue = totalValue / counts * dayCount;
				}
			}
		}
		
		return totalValue;
	}

	public GaugeView getElectricityGauge(Long memberId, Long sourceId, String periodType, Integer year, Integer month, Integer day, Integer viewType){
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		if (day != null) calendar.set(Calendar.DAY_OF_MONTH, day);
		
		User currentUser = userDao.findById(memberId);
		
		Date requestTime = calendar.getTime();
		
		GaugeView gv = new GaugeView();
		
		if (viewType == 1){
			gv.setUnit("kWh");
		}else if (viewType == 2){
			gv.setUnit("$");
		}
		
		//Get User value
		Double myRank = getSumValue(periodType, sourceId, viewType, requestTime, calendar.getActualMaximum(Calendar.DATE));
		if (myRank == null) myRank = 0.0;
		
		
		if (viewType == 1) myRank /= 1000;
		gv.setValue(myRank);
		
		Double minValue = myRank;
		Double maxValue = myRank;
		
		Double[] zoneData = new Double[4];
		
		//Get community users
		List<User> communityUsers = userDao.getUsersInCommunity(currentUser.getDefaultCommunity());
		List<Source> sources = null;
		if (!communityUsers.isEmpty()){
			
			if (periodType.equals(AppConstants.PERIOD_TYPE_DAILY)){
				Date startDate = DateParserUtil.getBeginningOfDay(requestTime);
				Date endDate = DateParserUtil.getEndOfDay(startDate);
				
				if (viewType == 1){
					//Get maximum value of user
					maxValue = electricityDao.findMinMaxSumElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, communityUsers, startDate, endDate, false);
					//Get minimum value of user
					minValue = electricityDao.findMinMaxSumElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, communityUsers, startDate, endDate, true);
					
					//Convert Wh to kWh
					maxValue /= 1000;
					minValue /= 1000;
				}else if (viewType == 2){
					//Get maximum value of user
					maxValue = electricityDao.findMinMaxSumElectricityCost(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, communityUsers, startDate, endDate, false);
					//Get minimum value of user
					minValue = electricityDao.findMinMaxSumElectricityCost(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, communityUsers, startDate, endDate, true);
					
				}
			}else if (periodType.equals(AppConstants.PERIOD_TYPE_MONTHLY)){
				Date startDate = DateParserUtil.getBeginningOfMonth(requestTime);
				Date endDate = DateParserUtil.getEndOfMonth(startDate);
				
				if (viewType == 1){
					//Get maximum value of user
					maxValue = electricityDao.findMinMaxSumElectricity(AppConstants.TABLE_ELECTRICITY_DATA_DAY, communityUsers, startDate, endDate, false);
					//Get minimum value of user
					minValue = electricityDao.findMinMaxSumElectricity(AppConstants.TABLE_ELECTRICITY_DATA_DAY, communityUsers, startDate, endDate, true);
					
					//Convert Wh to kWh
					maxValue /= 1000;
					minValue /= 1000;
				}else if (viewType == 2){
					//Get maximum value of user
					maxValue = electricityDao.findMinMaxSumElectricityCost(AppConstants.TABLE_ELECTRICITY_DATA_DAY, communityUsers, startDate, endDate, false);
					//Get minimum value of user
					minValue = electricityDao.findMinMaxSumElectricityCost(AppConstants.TABLE_ELECTRICITY_DATA_DAY, communityUsers, startDate, endDate, true);
					
				}
			}
			
		}
		
		//Conver min value to max value
		
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
		
		gv.setZoneData(zoneData);
		
		return gv;
	}

	private ActivityView getElectricityActivity(Long memberId){
		ActivityView electricity = null;
		Long sourceId = null;
		
		ResultView rv = sourceService.getMasterSourceId(memberId, AppConstants.SOURCETYPE_CODE_ELECTRICITY);
		if (rv.getResultCode() == AppConstants.SUCCESS){
			sourceId = Long.parseLong(rv.getResultMessage());
		}
		
		if (sourceId != null){
			electricity = new ActivityView();
			//ActivityView
			electricity.setHeading("MY ELECTRICITY - TODAY");
			electricity.setSourceType(1);
			//List<ElectricityView> current = summarizeHourlyData(sourceId, 24, false);
			//List<ElectricityView> previous = summarizeHourlyData(sourceId, 24, true);
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			
			
			ElectricitySummaryView pmdv = getElectricityData(memberId, AppConstants.PERIOD_TYPE_DAILY, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			
			Double totalConsumption = 0.0;
			Double totalSpending = 0.0;
			Double potentialSaving = 0.0;
			Double rate = 0.0;
			Double compareToPrevious = 0.0;
			if (pmdv != null){
				
				if (pmdv.getTotalConsumption() != null) totalConsumption = pmdv.getTotalConsumption();
				if (pmdv.getTotalCost() != null) totalSpending = pmdv.getTotalCost();
				
				if (pmdv.getPotentialSaving() != null) potentialSaving = pmdv.getPotentialSaving();
				if (pmdv.getCompareToPrevious() != null) compareToPrevious = pmdv.getCompareToPrevious();
				if (pmdv.getRate() != null) rate = pmdv.getRate();
				
			}
			
			electricity.setActual( (double)(Math.round(totalSpending * 100)) / 100);
			electricity.setGoal(4.0);
			electricity.setProgress(totalSpending/4.0 * 100);
			
			//Cost unit
			Unit cost = unitDao.getUnitByCode(AppConstants.UNIT_DOLLAR);
			
			if (cost != null){
				electricity.setUnitDescription(cost.getDescription());
				electricity.setUnitImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(cost.getImageUrl()));
				electricity.setUnitName(cost.getName());
			}
			
			//Gotta create 3 subview
			ActivitySubView consumption = new ActivitySubView();
			ActivitySubView previousComp = new ActivitySubView();
			ActivitySubView potentialSav = new ActivitySubView();
			
			consumption.setActual((double)(Math.round(totalConsumption * 100)) / 100);
			Unit cons = unitDao.getUnitByCode(AppConstants.UNIT_CONSUMPTION);
			if (cons != null){
				consumption.setUnitDescription(cons.getDescription());
				consumption.setUnitImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(cons.getImageUrl()));
				consumption.setUnitName(cons.getName());
			}
			
			//System.out.println("previous :: " + previousSpending + " :: current Total :: " + totalSpending + " :: " + rate);
			String trend = AppConstants.APACHE_IMAGE_LINK + "/images/equal-arrow-grey.png";
			if (rate >0) trend =  AppConstants.APACHE_IMAGE_LINK + "/images/up-arrow-grey.png";
			if (rate <0){
				rate *= -1;
				trend =  AppConstants.APACHE_IMAGE_LINK + "/images/down-arrow-grey.png";
			}
			
			previousComp.setUnitImageUrl(trend);
			previousComp.setUnitName("Compare to Previous");
			previousComp.setActual((double)(Math.round(compareToPrevious * 10)) / 10);
			
			if (cost.getImageUrl() != null ) potentialSav.setUnitImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(cost.getImageUrl()));
			potentialSav.setUnitName("Potential Saving");
			potentialSav.setUnitDescription("yr");
			potentialSav.setActual((double)(Math.round(potentialSaving * 100)) / 100);
			
			electricity.getActivitySubViews().add(consumption);
			electricity.getActivitySubViews().add(previousComp);
			electricity.getActivitySubViews().add(potentialSav);
		}
		
		return electricity;
	}
		
	private ActivityView getWalkingActivity(Long memberId){
		ActivityView walkingView = null;
		
		Long sourceId = null;
		ResultView rv = sourceService.getMasterSourceId(memberId, AppConstants.SOURCETYPE_CODE_WALKING);
		if (rv.getResultCode() == AppConstants.SUCCESS){
			sourceId = Long.parseLong(rv.getResultMessage());
		}
		
		if (sourceId != null){
			walkingView = new ActivityView();
			walkingView.setHeading("MY WALKING - TODAY");
			walkingView.setSourceType(2);
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			
			
			int totalCalories = 0;
			int totalSteps = 0;
			int totalDuration = 0;
			double totalDistance = 0;
			
			PersonalMotionDailyView pmdv = getPersonalMotionData(memberId, AppConstants.SOURCETYPE_CODE_WALKING, AppConstants.PERIOD_TYPE_DAILY, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			
			if (pmdv != null){
				totalCalories = pmdv.getCalories();
				totalSteps = pmdv.getSteps();
				totalDistance = pmdv.getDistance();
				
				totalDuration = (int)Double.parseDouble(pmdv.getDuration());
			}
			//Steps in WalkingView
			walkingView.setActual((double)totalSteps);
			walkingView.setGoal(3000.0);
			walkingView.setProgress((double)totalSteps/3000.0 * 100);
			
			//steps unit
			Unit steps = unitDao.getUnitByCode(AppConstants.UNIT_STEPS);
			
			if (steps != null){
				walkingView.setUnitDescription(steps.getDescription());
				walkingView.setUnitImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(steps.getImageUrl()));
				walkingView.setUnitName(steps.getName());
			}
			
			//Gotta create 3 subview
			ActivitySubView distanceView = new ActivitySubView();
			ActivitySubView activeView = new ActivitySubView();
			ActivitySubView caloriesView = new ActivitySubView();
			
			distanceView.setActual(totalDistance);
			//distance unit
			Unit meter = unitDao.getUnitByCode(AppConstants.UNIT_METER);
			
			if (steps != null){
				distanceView.setUnitDescription(meter.getDescription());
				distanceView.setUnitImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(meter.getImageUrl()));
				distanceView.setUnitName(meter.getName());
			}
			
			activeView.setActual((int)Math.ceil((totalDuration)));
			
			Unit active = unitDao.getUnitByCode(AppConstants.UNIT_MINUTE);
			
			if (active != null){
				activeView.setUnitDescription(active.getDescription());
				activeView.setUnitImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(active.getImageUrl()));
				activeView.setUnitName(active.getName());
			}
			
			caloriesView.setActual((int)totalCalories);
			
			Unit calories = unitDao.getUnitByCode(AppConstants.UNIT_CALORIES);
			
			if (calories != null){
				caloriesView.setUnitDescription(calories.getDescription());
				caloriesView.setUnitImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(calories.getImageUrl()));
				caloriesView.setUnitName(calories.getName());
			}
			
			walkingView.getActivitySubViews().add(distanceView);
			walkingView.getActivitySubViews().add(activeView);
			walkingView.getActivitySubViews().add(caloriesView);
		}
		return walkingView;
	}
}
