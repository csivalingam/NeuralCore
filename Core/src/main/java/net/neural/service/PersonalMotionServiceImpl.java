package net.zfp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.FormParam;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Source;
import net.zfp.entity.User;
import net.zfp.entity.community.Community;
import net.zfp.entity.personalmotion.CyclingDataDay;
import net.zfp.entity.personalmotion.CyclingDataHour;
import net.zfp.entity.personalmotion.PersonalMotion;
import net.zfp.entity.personalmotion.RunningDataDay;
import net.zfp.entity.personalmotion.RunningDataHour;
import net.zfp.entity.personalmotion.WalkingDay;
import net.zfp.entity.personalmotion.WalkingHour;
import net.zfp.entity.personalmotion.WalkingBoundary;
import net.zfp.service.PersonalMotionService;
import net.zfp.util.AppConstants;
import net.zfp.util.DateParserUtil;
import net.zfp.view.PersonalMotionDailyView;
import net.zfp.view.PersonalMotionView;
import net.zfp.view.ResultView;
import net.zfp.view.SourceSummaryView;
import net.zfp.view.personalmotion.MonthlySummaryView;
import net.zfp.view.personalmotion.MonthlyView;
import net.zfp.view.personalmotion.PersonalMotionDetailView;

public class PersonalMotionServiceImpl implements PersonalMotionService {
	
	@Resource
	private SourceService sourceService;
	
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Source> sourceDao;
	@Resource
	private EntityDao<PersonalMotion> personalMotionDao;
	
	private static long DAY_IN_SECONDS = 24*60*60l;
	
	private void compareWalkingWithLastWeek (List<PersonalMotionDetailView> pdv, Long sourceId){
		
		PersonalMotionDetailView stepsView = null;
		PersonalMotionDetailView activeView = null;
		PersonalMotionDetailView caloriesView = null;
		PersonalMotionDetailView distanceView = null;
		
		if (pdv != null){
			for (PersonalMotionDetailView pmdv : pdv){
				if (pmdv.getUnitType() == 1){
					stepsView = pmdv;
				}else if (pmdv.getUnitType() == 2){
					activeView = pmdv;
				}else if (pmdv.getUnitType() == 3){
					distanceView = pmdv;
				}else if (pmdv.getUnitType() == 4){
					caloriesView = pmdv;
				}
			}
		}
		if ( sourceId != null){
			
			//Get This week Walking
			Date startDate = DateParserUtil.getFirstDayOfWeek(DateParserUtil.getBeginningOfDay(new Date()));
			Date endDate = new Date();
			System.out.println("This Week! startDAte :: " + startDate + " , endDate :: " + endDate);
			List<WalkingHour> thisWeekWalking = personalMotionDao.getWalkingByInterval(sourceId, startDate, endDate);
			//Get Last week Walking
			endDate = new Date(startDate.getTime() - 1l);
			startDate = DateParserUtil.getFirstDayOfWeek(DateParserUtil.getBeginningOfDay(endDate));
			System.out.println("Last Week! startDAte :: " + startDate + " , endDate :: " + endDate);
			List<WalkingHour> lastWeekWalking = personalMotionDao.getWalkingByInterval(sourceId, startDate, endDate);
			
			int totalCalories = 0;
			int totalSteps = 0;
			int totalDuration = 0;
			int totalDistance = 0;
			
			int averageSteps = 0;
			int averageCalories = 0;
			int averageDuration = 0;
			int averageDistance = 0;
			
			//Get total walks in this week
			if (thisWeekWalking != null && thisWeekWalking.size() > 0){
				for (WalkingHour data : thisWeekWalking){
					totalCalories += data.getCalories();
					totalSteps += data.getSteps();
					totalDuration += data.getDuration().intValue();
					totalDistance += data.getDistance();
				}
				
				
				int daysOfWeek = DateParserUtil.whichDaysOfWeek(new Date());
				
				System.out.println("Steps :: " + totalSteps + " Days of Week :: " + daysOfWeek);
				
					//Steps
				stepsView.setThisWeekValue(totalSteps);
					averageSteps = stepsView.getThisWeekValue()/daysOfWeek;
					stepsView.setDailyAverage(averageSteps);
				
					//Duration
					activeView.setThisWeekValue(totalDuration/60);
					averageDuration = activeView.getThisWeekValue()/daysOfWeek;
					activeView.setDailyAverage(averageDuration);
				
					//Distance
					distanceView.setThisWeekValue(totalDistance);
					averageDistance = distanceView.getThisWeekValue()/daysOfWeek;
					distanceView.setDailyAverage(averageDistance);
				
					//Calories
					caloriesView.setThisWeekValue(totalCalories);
					averageCalories = caloriesView.getThisWeekValue() / daysOfWeek;
					caloriesView.setDailyAverage(averageCalories);
				
				
			}
			if (lastWeekWalking != null && lastWeekWalking.size() > 0){
				for (WalkingHour data : lastWeekWalking){
					totalCalories += data.getCalories();
					totalSteps += data.getSteps();
					totalDuration += data.getDuration().intValue();
					totalDistance += data.getDistance();
				}
				totalCalories /= 7;
				totalSteps /= 7;
				totalDuration /= 60;
				totalDuration /= 7;
				totalDistance /=7;
			}
			if (totalCalories > 0) averageCalories = (100*(averageCalories - totalCalories)) / totalCalories;
			if (totalSteps > 0 ) averageSteps = (100*(averageSteps - totalSteps)) / totalSteps;
			if (totalDuration > 0 )averageDuration = (100*(averageDuration - totalDuration)) / totalDuration;
			if (totalDistance > 0) averageDistance = (100*(averageDistance - totalDistance)) / totalDistance;
			
			
				//Steps
				if (averageSteps < 0){
					stepsView.setTrendImageUrl("/portal-core/images/rewards/icons/performance-downarrow.png");
					averageSteps *= -1;
				}else{
					stepsView.setTrendImageUrl("/portal-core/images/rewards/icons/performance-uparrow.png");
				}
				stepsView.setTrend(averageSteps);
				
			
				//Duration
				if (averageDuration < 0){
					activeView.setTrendImageUrl("/portal-core/images/rewards/icons/performance-downarrow.png");
					averageDuration *= -1;
				}else{
					activeView.setTrendImageUrl("/portal-core/images/rewards/icons/performance-uparrow.png");

				}
				activeView.setTrend(averageDuration);
				
			
				//Distance
				if (averageDistance < 0){
					distanceView.setTrendImageUrl("/portal-core/images/rewards/icons/performance-downarrow.png");
					averageDistance *= -1;
				}else{
					distanceView.setTrendImageUrl("/portal-core/images/rewards/icons/performance-uparrow.png");
				}
				distanceView.setTrend(averageDistance);
				
			
				//Calories
				if (averageCalories < 0){
					caloriesView.setTrendImageUrl("/portal-core/images/rewards/icons/performance-downarrow.png");
					averageCalories *= -1;
				}else{
					caloriesView.setTrendImageUrl("/portal-core/images/rewards/icons/performance-uparrow.png");

				}
				caloriesView.setTrend(averageCalories);
			
			pdv = new ArrayList<PersonalMotionDetailView>();
			pdv.add(stepsView);
			pdv.add(activeView);
			pdv.add(caloriesView);
			pdv.add(distanceView);
		}
	}
	
	private void setBaseline(PersonalMotionDetailView pdv){
		List<WalkingBoundary> walkingBoundaries = null;
		String periodType = AppConstants.PERIOD_TYPE_DAILY;
		if (pdv.getUnitType() == 1){
			//Steps
			walkingBoundaries = personalMotionDao.getWalkingBoundariesByActivityTrackerType(periodType, AppConstants.ACTIVITY_TRACKER_TYPE_STEPS);
			
		}else if (pdv.getUnitType() ==2){
			//Duration
			walkingBoundaries = personalMotionDao.getWalkingBoundariesByActivityTrackerType(periodType, AppConstants.ACTIVITY_TRACKER_TYPE_DURATION);
		}else if (pdv.getUnitType() ==3){
			//Distance
			walkingBoundaries = personalMotionDao.getWalkingBoundariesByActivityTrackerType(periodType, AppConstants.ACTIVITY_TRACKER_TYPE_DISTANCE);
		}else if (pdv.getUnitType() == 4){
			//Calories
			walkingBoundaries = personalMotionDao.getWalkingBoundariesByActivityTrackerType(periodType, AppConstants.ACTIVITY_TRACKER_TYPE_CALORIES);
		}
		
		if (walkingBoundaries != null){
			for (WalkingBoundary walkingBoundary : walkingBoundaries){
				if (walkingBoundary.getBaselineType().getType().equals(AppConstants.BASELINE_TYPE_MEDIUM)){
					pdv.setBaselineMedium(walkingBoundary.getBoundary());
				}else if (walkingBoundary.getBaselineType().getType().equals(AppConstants.BASELINE_TYPE_HIGH)){
					pdv.setBaselineHigh(walkingBoundary.getBoundary());
				}
			}
		}else{
			pdv.setBaselineHigh(0);
			pdv.setBaselineMedium(0);
		}
	}
	
	private static String[] weekDays = {"S", "M", "T", "W", "T", "F", "S"};
	
	
	private String setWeekDay(Integer index){
		return weekDays[index-1];
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<PersonalMotionDetailView> getPastMonthSummary(Long accountId, Integer walkingType){
		List<PersonalMotionDetailView> msv = new ArrayList<PersonalMotionDetailView>();
		
		Long sourceId = null;
		ResultView rv = sourceService.getMasterSourceId(accountId, AppConstants.SOURCETYPE_CODE_WALKING);
		if (rv.getResultCode() == AppConstants.SUCCESS){
			sourceId = Long.parseLong(rv.getResultMessage());
		}
		
		//Get Last Month
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		
		//get Title
		String[] sTitle = new String[4];
		Date endDate = null;
		Date startDate = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMM");
		
		Date startInterval = null;
		
		Date finalWeekEndDate = null;
		for (int i = 0; i <4; i++){
			//Get end of last week
			calendar.add(Calendar.DATE, -calendar.get(Calendar.DAY_OF_WEEK));
			endDate = DateParserUtil.getEndOfDay(calendar.getTime());
			
			if (i==0) finalWeekEndDate = endDate;
			calendar.add(Calendar.DATE, -(calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek()));
			startDate = DateParserUtil.getBeginningOfDay(calendar.getTime());
			
			if (formatter.format(startDate).equals(formatter.format(endDate))){
				//Same month so title will be
				sTitle[i] = formatter.format(startDate) + " " + DateParserUtil.getCurrentDate(startDate) +"-" + DateParserUtil.getCurrentDate(endDate);
			}else{
				sTitle[i] = formatter.format(startDate) + " " + DateParserUtil.getCurrentDate(startDate) +"-" + formatter.format(endDate) +" " + DateParserUtil.getCurrentDate(endDate);
			}
			
			if (i == 3) startInterval = startDate;
		}
		
		Date endInterval = DateParserUtil.getEndOfDay(startInterval);
		
		PersonalMotionDetailView activeView = new PersonalMotionDetailView();
		PersonalMotionDetailView stepView = new PersonalMotionDetailView();
		PersonalMotionDetailView distanceView = new PersonalMotionDetailView();
		PersonalMotionDetailView caloriesView = new PersonalMotionDetailView();
		
		MonthlySummaryView activeSummaryView = new MonthlySummaryView();
		MonthlySummaryView stepSummaryView = new MonthlySummaryView();
		MonthlySummaryView distanceSummaryView = new MonthlySummaryView();
		MonthlySummaryView caloriesSummaryView = new MonthlySummaryView();
		
		Integer calCounter = 0;
		Integer stepCounter = 0;
		Integer durationCounter = 0;
		Integer distanceCounter = 0;
		
		Integer hCal = 0;
		Integer hStep = 0;
		Integer hDur = 0;
		Integer hDist = 0;
		
		int dayPointer = 1;
		int weekCounter = 3;
		while(endInterval.getTime() <= finalWeekEndDate.getTime()){
			List<WalkingHour> walkingDatas = personalMotionDao.getWalkingByInterval(sourceId, startInterval, endInterval);
			System.out.println("Start Intervla " + startInterval +" :: Ending Interval " + endInterval );
			System.out.println("DayCounter = " + dayPointer + " :: weekCounter = " + weekCounter );
			System.out.println("walkingDatas size " + walkingDatas.size() );
			if (walkingDatas != null && walkingDatas.size()>0){
				for (WalkingHour w : walkingDatas){
					calCounter += w.getCalories();
					stepCounter += w.getSteps();
					durationCounter += w.getDuration().intValue();
					distanceCounter += w.getDistance().intValue();
				}
				System.out.println(stepCounter);
				//Convert seconds to minute
				durationCounter /= 60;
			}
			
			MonthlyView aView = new MonthlyView();
			MonthlyView sView = new MonthlyView();
			MonthlyView dView = new MonthlyView();
			MonthlyView cView = new MonthlyView();
			
			cView.setLabel(setWeekDay(DateParserUtil.whichDaysOfWeek(startInterval)));
			sView.setLabel(setWeekDay(DateParserUtil.whichDaysOfWeek(startInterval)));
			dView.setLabel(setWeekDay(DateParserUtil.whichDaysOfWeek(startInterval)));
			aView.setLabel(setWeekDay(DateParserUtil.whichDaysOfWeek(startInterval)));
			
			cView.setValue(calCounter);
			sView.setValue(stepCounter);
			dView.setValue(distanceCounter);
			aView.setValue(durationCounter);
			
			if (hCal < calCounter) hCal = calCounter;
			if (hStep < stepCounter) hStep = stepCounter;
			if (hDur < durationCounter) hDur = durationCounter;
			if (hDist < distanceCounter) hDist = distanceCounter;
			
			startInterval = new Date(endInterval.getTime() + 1l);
			endInterval = DateParserUtil.getEndOfDay(startInterval);
			
			aView.setTitle(sTitle[weekCounter]);
			sView.setTitle(sTitle[weekCounter]);
			dView.setTitle(sTitle[weekCounter]);
			cView.setTitle(sTitle[weekCounter]);
			
			
			if (weekCounter == 3){
				activeSummaryView.getFirstQuarter().add(aView);
				stepSummaryView.getFirstQuarter().add(sView);
				distanceSummaryView.getFirstQuarter().add(dView);
				caloriesSummaryView.getFirstQuarter().add(cView);
			}else if (weekCounter == 2){
				activeSummaryView.getSecondQuarter().add(aView);
				stepSummaryView.getSecondQuarter().add(sView);
				distanceSummaryView.getSecondQuarter().add(dView);
				caloriesSummaryView.getSecondQuarter().add(cView);
			}else if (weekCounter == 1){
				activeSummaryView.getThirdQuarter().add(aView);
				stepSummaryView.getThirdQuarter().add(sView);
				distanceSummaryView.getThirdQuarter().add(dView);
				caloriesSummaryView.getThirdQuarter().add(cView);
			}else if (weekCounter == 0){
				activeSummaryView.getFourthQuarter().add(aView);
				stepSummaryView.getFourthQuarter().add(sView);
				distanceSummaryView.getFourthQuarter().add(dView);
				caloriesSummaryView.getFourthQuarter().add(cView);
			}
			
			calCounter = 0;
			stepCounter = 0;
			durationCounter =0;
			distanceCounter =0;
			
			
			if (dayPointer == 7){
				weekCounter--;
				dayPointer = 1;
			}else{
				dayPointer++;
			}
			
			System.gc();
		}
		
		activeSummaryView.setHighestValue(hDur);
		stepSummaryView.setHighestValue(hStep);
		distanceSummaryView.setHighestValue(hDist);
		caloriesSummaryView.setHighestValue(hCal);
				
		activeView.setMonthlySummaryView(activeSummaryView);
		stepView.setMonthlySummaryView(stepSummaryView);
		distanceView.setMonthlySummaryView(distanceSummaryView);
		caloriesView.setMonthlySummaryView(caloriesSummaryView);
		
		activeView.setUnitType(2);
		stepView.setUnitType(1);
		distanceView.setUnitType(3);
		caloriesView.setUnitType(4);
		
		setBaseline(activeView);
		setBaseline(stepView);
		setBaseline(distanceView);
		setBaseline(caloriesView);
		
		msv.add(activeView);
		msv.add(stepView);
		msv.add(distanceView);
		msv.add(caloriesView);
		
		compareWalkingWithLastWeek(msv, sourceId);
		
		return msv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public PersonalMotionDailyView getPersonalMotionData(Long accountId, String sourceTypeCode, String periodType, Integer year, Integer month, Integer day){
		System.out.println("getPersonalMotionData : " + new Date());
		
		PersonalMotionDailyView pmdv = new PersonalMotionDailyView();
		List<PersonalMotionView> pmvs = new ArrayList<PersonalMotionView>();
		List<Source> sources = userDao.findSourceByAccountAndType(accountId, sourceTypeCode);
		Long sourceId = null;
		
		for (Source source : sources){
			
			if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_PERSONAL) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				sourceId = source.getId();
				break;
			}
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
		return pmdv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public PersonalMotionDailyView getHalfDayPersonalSummary(Long accountId, String sourceTypeCode){
		System.out.println("getHALF DAY Personal Summary !!");
		PersonalMotionDailyView pmdv = new PersonalMotionDailyView();
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
			
			
			//get current time
			Date currentTime = new Date(new Date().getTime()- 12 * 60 * 60 * 1000l);
			
			Date startTime = DateParserUtil.getBeginningOfHour(currentTime);
			Date endTime = DateParserUtil.getEndOfHour(currentTime);
			
			int counter =0;
			//Past 12 hours of walking data from the source
			while (counter < 12){
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
					if (counter % 6 == 0){
						pmv.setDuration(DateParserUtil.getHourValue(startTime) + " " + DateParserUtil.getAMPMValue(startTime));
					}else{
						pmv.setDuration(DateParserUtil.getHourValue(startTime)+"");
					}
				}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_RUNNING)){
					List<RunningDataHour> runningDatas = personalMotionDao.getRunningByInterval(sourceId, startTime, endTime);
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
					if (counter % 6 == 0){
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
					if (counter % 6 == 0){
						pmv.setDuration(DateParserUtil.getHourValue(startTime) + " " + DateParserUtil.getAMPMValue(startTime));
					}else{
						pmv.setDuration(DateParserUtil.getHourValue(startTime)+"");
					}
				}
				
				
				
				System.out.println(pmv.getDuration());
				pmv.setDistance(distance);
				pmv.setDurationInNumber((double)(duration/60));
				pmv.setId((long)counter);
				pmv.setCalories(calories);
				pmv.setSteps(steps);
				pmvs.add(pmv);
				counter++;
				startTime = new Date(endTime.getTime() + 1l);
				endTime = DateParserUtil.getEndOfHour(startTime);
			}
			//pm or am in hour value only at the 
		}
		
		//Get Hourly Baseline for 
		List<WalkingBoundary> boundaries = personalMotionDao.getWalkingBoundaries(AppConstants.PERIOD_TYPE_HOURLY);
		
		pmdv.setWalkingBaselineView(boundaries);
		
		pmdv.setPersonalMotionViews(pmvs);
		pmdv.setCalories(totalCalories);
		pmdv.setSteps(totalSteps);
		pmdv.setDistance(totalDistance);
		pmdv.setDuration(String.valueOf((totalDuration/60)));
		return pmdv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<SourceSummaryView> getPersonalMotionSummary(Long accountId, String sourceTypeCode){
		
		List<SourceSummaryView> ssvs = new ArrayList<SourceSummaryView>();
		
		User user = userDao.findById(accountId);
		Long communityId = user.getDefaultCommunity();
		
		List<Long> walkingSources = new ArrayList<Long>();
		List<Source> sources = null;
		sources = userDao.findDefaultSourceByAccountAndType(accountId, sourceTypeCode);
		
		/*
		 * If there is no default source... fetch all non default source
		 */
		if (sources.isEmpty()){
			sources = userDao.findSourceByAccountAndType(accountId, sourceTypeCode);
		}
		
		for (Source source : sources){
			
			if (source.getStatus().getCode().equals(AppConstants.STATUS_CODE_ACTIVE) && source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_PERSONAL) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				walkingSources.add(source.getId());
			}
		}
		
		if (walkingSources.size() > 0){
			
			//Get database
			String database = null;
			if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_WALKING)){
				database = AppConstants.TABLE_WALKING_HOUR;
			}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING)){
				database = AppConstants.TABLE_CYCLING_HOUR;
			}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_RUNNING)){
				database = AppConstants.TABLE_RUNNING_HOUR;
			}
			
			//Get 12 months data
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			
			List<Date> dates = personalMotionDao.getPersonalMotionMonthBySource(database, walkingSources, year-1, month, year);
			//now by looking at the dates get total calories, distances, steps, 
			
			if (dates != null && dates.size() > 0){
				for(Date date: dates){
					
					SourceSummaryView ssv = new SourceSummaryView();
					cal.setTime(date);
					int curMonth = cal.get(Calendar.MONTH);
					int curYear = cal.get(Calendar.YEAR);
					
					System.out.println("Date :: " + date + " :: " + curMonth + " :: " + curYear);
					
					ssv.setMonth(curMonth);
					ssv.setYear(curYear);
					
					Integer caloriesBurned = (int)(long)personalMotionDao.getPersonalMotionMonthCaloriesBySource(database, walkingSources, curMonth+1, curYear);
					Double distance = personalMotionDao.getPersonalMotionMonthDistanceBySource(database, walkingSources, curMonth+1, curYear);
					Integer steps = (int)(long)personalMotionDao.getPersonalMotionMonthStepsBySource( database, walkingSources, curMonth+1, curYear);
					Double duration = personalMotionDao.getPersonalMotionMonthDurationBySource(database, walkingSources, curMonth+1, curYear);
					
					System.out.println("calories...!" + caloriesBurned);
					ssv.setCalories(caloriesBurned);
					ssv.setDistance(Math.round((distance/1000) * 100.0) / 100.0);
					ssv.setSteps(steps);
					ssv.setDurationInSeconds(duration);
					
					int travelTimeInSeconds = (int)Math.round(duration);
					int hourValue = travelTimeInSeconds / 3600;
					int minuteValue = (travelTimeInSeconds % 3600) / 60;
					if (minuteValue < 10){
						ssv.setDuration(hourValue + ":0" + minuteValue);
					}else{
						ssv.setDuration(hourValue + ":" + minuteValue);
					}
					
					ssvs.add(ssv);
				}
				
				List<Long> accountIds = userDao.getPeersInCommunity(accountId, communityId);
				Map<Long, List<Long>> accountSources = new HashMap<Long, List<Long>>();
				
				if (accountIds != null){
					for (Long id : accountIds){
						sources = userDao.findSourceByAccountAndType(id, sourceTypeCode);
						walkingSources = new ArrayList<Long>();
						if (sources != null){
							for (Source source : sources){
								
								if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_PERSONAL) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
									walkingSources.add(source.getId());
								}
							}
						}
						if (walkingSources.size() > 0) accountSources.put(id, walkingSources);
	
					}
				}
				
				for (int i=0; i< ssvs.size(); i++){
					
					Iterator iter = accountSources.entrySet().iterator();
					if (i==0) ssvs.get(i).setTrend(-1);
					else{
						if (ssvs.get(i-1).getSteps() > ssvs.get(i).getSteps()){
							ssvs.get(i).setTrend(1);
						}else if (ssvs.get(i-1).getSteps() < ssvs.get(i).getSteps()){
							ssvs.get(i).setTrend(2);
						}else{
							ssvs.get(i).setTrend(-1);
						}
					}
					
					int peerSize = 1;
					int counter = 1;
					//calculate peers steps
					while (iter.hasNext()){
						Map.Entry<Long, List<Long>> entry = (Map.Entry) iter.next();
						Long stepsInLong = personalMotionDao.getPersonalMotionMonthStepsBySource(database, entry.getValue(), ssvs.get(i).getMonth()+1, ssvs.get(i).getYear());
						if (stepsInLong != null){
							if (ssvs.get(i).getSteps() < (int)(long)stepsInLong) counter ++;
							peerSize++;
						}
					}
					//Now we have all peers steps...
					
					if (peerSize >=3){
						if ((double)counter / peerSize > 2.0/3.0){
							ssvs.get(i).setRank(3);
						}else if ((double) counter /peerSize > 1.0/3.0){
							ssvs.get(i).setRank(2);
						}else{
							ssvs.get(i).setRank(1);
						}
					}else{
						ssvs.get(i).setRank(1);
					}
					
					
				}
				
			}
		}
		return ssvs;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<PersonalMotionDailyView> getPersonalMotionDailyData(String domainName, Long accountId){
		List<PersonalMotionDailyView> pmdvs = new ArrayList<PersonalMotionDailyView>();
		System.out.println("This is Personal Motion Daily Data!!");
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		//Get Community Id
		Community community = communityDao.getCommunity(domainName);
		Long communityId = community.getId();
		
		//In AccountSource table, check if there are any sources associated with account
		List<Source> sources = userDao.findSourceByAccountAndType(accountId, AppConstants.SOURCETYPE_CODE_WALKING);
		Long sourceId = null;
		for (Source source : sources){
			
			if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_PERSONAL) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				sourceId = source.getId();
				break;
			}
		}
		
		
		if (sourceId != null){
			System.out.println("SourceID :: " + sourceId);
			//Get latest 7 data walking datas 
			
			List<String> dateInStrings = personalMotionDao.getWalking(sourceId);
			System.out.println(dateInStrings);
			if (dateInStrings != null){
				System.out.println(dateInStrings.size());
				List<Date> dates = new  ArrayList<Date>();
				for (String dateInString : dateInStrings){
					System.out.println("Date String :: " + dateInString);
					try {
						dates.add(dateFormat.parse(dateInString));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (dates != null && dates.size() > 0){
					for (Date date : dates){
						System.out.println(date);
						PersonalMotionDailyView pmdv = new PersonalMotionDailyView();
						
						List<WalkingHour> walkingDatas = personalMotionDao.getWalkingBySource(sourceId, date);
						List<PersonalMotionView> pmvs = new ArrayList<PersonalMotionView>();
						
						int totalSteps = 0;
						int totalCalories = 0;
						double totalDuration = 0.0;
						double totalDistance = 0.0;
						if (walkingDatas != null){
							for (WalkingHour w: walkingDatas){
								PersonalMotionView pmv = new PersonalMotionView(w);
								pmvs.add(pmv);
								totalSteps += w.getSteps();
								totalCalories += w.getCalories();
								totalDuration += w.getDuration();
								totalDistance += w.getDistance();
							}
						}
						
						totalDuration = Math.round(totalDuration * 100.0) / 100.0;
						totalDistance = Math.round((totalDistance/1000) * 100.0) / 100.0;
						
						int travelTimeInSeconds = (int)Math.round(totalDuration);
						int hourValue = travelTimeInSeconds / 3600;
						int minuteValue = (travelTimeInSeconds % 3600) / 60;
						if (minuteValue < 10){
							pmdv.setDuration(hourValue + ":0" + minuteValue);
						}else{
							pmdv.setDuration(hourValue + ":" + minuteValue);
						}
						pmdv.setDistance(totalDistance);
						pmdv.setDate(date);
						pmdv.setStringDate(formatter.format(date));
						pmdv.setSteps(totalSteps);
						pmdv.setCalories(totalCalories);
						pmdv.setPersonalMotionViews(pmvs);
						
						pmdvs.add(pmdv);
					}
				}
			}
			
			
		}
		return pmdvs;
	}
}
