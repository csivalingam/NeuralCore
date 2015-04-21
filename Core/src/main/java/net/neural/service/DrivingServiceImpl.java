package net.zfp.service;

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

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Source;
import net.zfp.entity.User;
import net.zfp.entity.community.Community;
import net.zfp.entity.driving.DrivingDay;
import net.zfp.entity.driving.DrivingHour;
import net.zfp.service.DrivingService;
import net.zfp.util.AppConstants;
import net.zfp.util.DateParserUtil;
import net.zfp.view.DrivingSummaryView;
import net.zfp.view.DrivingView;
import net.zfp.view.SourceSummaryView;

public class DrivingServiceImpl implements DrivingService {
	
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Source> sourceDao;
	@Resource
	private EntityDao<DrivingHour> drivingDao;
	
	
	
	
	/*
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<DrivingView> getHalfDayDrivingData(Long memberId){
		System.out.println("Get Half Day Driving Data : " + new Date());
		List<DrivingView> drivingViews = new ArrayList<DrivingView>();
		
		List<Source> sources = userDao.findSourceByAccountAndType(memberId, AppConstants.SOURCETYPE_CODE_DRIVING);
		Long sourceId = null;
		for (Source source : sources){
			
			if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_CAR) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				sourceId = source.getId();
				break;
			}
		}
		
		System.out.println("Source ID : "+ sourceId);
		if (sourceId != null){
			Date currentTime = new Date(new Date().getTime()- 12 * 60 * 60 * 1000l);
			
			Date startTime = DateParserUtil.getBeginningOfHour(currentTime);
			Date endTime = DateParserUtil.getEndOfHour(currentTime);
			
			int counter = 0;
			
			while( counter < 12){
				DrivingView dv = new DrivingView();
				
				if (counter % 6 == 0){
					dv.setDuration(DateParserUtil.getHourValue(startTime) + " " + DateParserUtil.getAMPMValue(startTime));
				}else{
					dv.setDuration(DateParserUtil.getHourValue(startTime)+"");
				}
				
				DrivingHour ddh = drivingDao.getDrivingDataHour(sourceId, startTime);
				
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
				
				drivingViews.add(dv);
				counter++;
				startTime = new Date(endTime.getTime() + 1l);
				endTime = DateParserUtil.getEndOfHour(startTime);
			}
		}
		
		return drivingViews;
	}
	*/
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<SourceSummaryView> getDrivingSummary(Long accountId){
		List<SourceSummaryView> ssvs = new ArrayList<SourceSummaryView>();
		
		User user = userDao.findById(accountId);
		Long communityId = user.getDefaultCommunity();
		
		List<Source> sources = null;
		sources = userDao.findDefaultSourceByAccountAndType(accountId, AppConstants.SOURCETYPE_CODE_DRIVING);
		
		/*
		 * If there is no default source... fetch all non default source
		 */
		if (sources.isEmpty()){
			sources = userDao.findSourceByAccountAndType(accountId, AppConstants.SOURCETYPE_CODE_DRIVING);
		}
		
		Long sourceId = null;
		for (Source source : sources){
			
			if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_CAR) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				sourceId = source.getId();
				break;
			}
		}
		
		if (sourceId != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			
			List<Date> dates = drivingDao.getDrivingMonthBySource(sourceId, year-1, month, year);
			if (dates != null){
				for (Date date : dates){
					SourceSummaryView ssv = new SourceSummaryView();
					cal.setTime(date);
					int curMonth = cal.get(Calendar.MONTH);
					int curYear = cal.get(Calendar.YEAR);
					
					System.out.println("Date :: " + date + " :: " + curMonth + " :: " + curYear);
					
					ssv.setMonth(curMonth);
					ssv.setYear(curYear);
					
					Double efficiency = drivingDao.getDrivingMonthEfficiencyBySource(sourceId, curMonth+1, curYear);
					Double fuelConsumption = drivingDao.getDrivingMonthFuelBySource(sourceId, curMonth+1, curYear);
					Double distance = drivingDao.getDrivingMonthAttributeBySource("distance", sourceId, curMonth+1, curYear);
					Double duration = drivingDao.getDrivingMonthAttributeBySource("duration", sourceId, curMonth+1, curYear);
					
					System.out.println("efficiency :: " + efficiency);
					System.out.println(efficiency + " :: " + fuelConsumption + " :: " + distance + " :: " + duration);
					
					ssv.setEfficiency(Math.round(efficiency * 100.0) / 100.0);
					ssv.setConsumption(fuelConsumption);
					ssv.setDistance(distance);
					
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
			}
			List<Long> accountIds = userDao.getPeersInCommunity(accountId, communityId);
			Map<Long, Long> accountSources = new HashMap<Long, Long>();
			
			if (accountIds != null){
				for (Long id : accountIds){
					sources = userDao.findSourceByAccountAndType(id, AppConstants.SOURCETYPE_CODE_DRIVING);
					sourceId = null;
					if (sources != null){
						for (Source source : sources){
							
							if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_CAR) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
								sourceId = source.getId();
							}
						}
					}
					if (sourceId != null) accountSources.put(id, sourceId);

				}
			}
			
			for (int i=0; i< ssvs.size(); i++){
				
				Iterator iter = accountSources.entrySet().iterator();
				
				if (i==0) ssvs.get(i).setTrend(-1);
				else{
					if (ssvs.get(i-1).getEfficiency() > ssvs.get(i).getEfficiency()){
						ssvs.get(i).setTrend(1);
					}else if (ssvs.get(i-1).getEfficiency() < ssvs.get(i).getEfficiency()){
						ssvs.get(i).setTrend(2);
					}else{
						ssvs.get(i).setTrend(-1);
					}
				}
				
				int peerSize = 1;
				int counter = 1;
				//calculate peers steps
				while (iter.hasNext()){
					Map.Entry<Long, Long> entry = (Map.Entry) iter.next();
					Double efficiency = drivingDao.getDrivingMonthEfficiencyBySource(entry.getValue(), ssvs.get(i).getMonth()+1, ssvs.get(i).getYear());
					if (efficiency != null){
						if (ssvs.get(i).getEfficiency() < efficiency) counter ++;
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
		
		return ssvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<DrivingSummaryView> getDrivingDailyData(String domainName, Long accountId){
		
		System.out.println("This is Driving Daily Data!!");
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		List<DrivingSummaryView> ddvs = new ArrayList<DrivingSummaryView>();
		//Get Community Id
		Community community = communityDao.getCommunity(domainName);
		
				
		//In AccountSource table, check if there are any sources associated with account
		List<Source> sources = userDao.findSourceByAccountAndType(accountId, AppConstants.SOURCETYPE_CODE_DRIVING);
		Long sourceId = null;
		for (Source source : sources){
			System.out.println(source.getId());
			
			if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_CAR) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				sourceId = source.getId();
				break;
			}
		}
		
		if (sourceId != null){
			System.out.println("Source ID :: " + sourceId);
			//Get first source
			//Fecth Driving Data...
			/*
			List<String> dateInStrings = drivingDao.getDriving(sourceId);
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
						DrivingDailyView ddv = new DrivingDailyView();
						ddv.setDate(date);
						ddv.setStringDate(formatter.format(date));
						
						
						List<Driving> drivingRecords = drivingDao.getDrivingBySource(sourceId, date);
						List<DrivingView> dvs = new ArrayList<DrivingView>();
						
						Double totalFuelConsumption = 0.0;
						Double totalDistance = 0.0;
						Double travelTime = 0.0;
						Double averageEfficiency = 0.0;
						
						if (drivingRecords != null){
							System.out.println("Found... Driving View... " + drivingRecords.size());
							for (Driving dr : drivingRecords){
								
								DrivingView dv = new DrivingView(dr);
								totalFuelConsumption += dv.getFuel();
								totalDistance += dv.getDistance();
								travelTime += dv.getDuration();
								averageEfficiency += dv.getEfficiency();
								dvs.add(dv);
								
							}
							
							averageEfficiency /= drivingRecords.size();
						}
						
						totalFuelConsumption = Math.round(totalFuelConsumption * 100.0) / 100.0;
						totalDistance = Math.round(totalDistance * 100.0) / 100.0;
						averageEfficiency = Math.round(averageEfficiency * 100.0) / 100.0;
						
						ddv.setFuelConsumption(totalFuelConsumption);
						ddv.setDistance(totalDistance);
						ddv.setDrivingViews(dvs);
						ddv.setEfficiency(averageEfficiency);
						int travelTimeInSeconds = (int)Math.round(travelTime);
						int hourValue = travelTimeInSeconds / 3600;
						int minuteValue = (travelTimeInSeconds % 3600) / 60;
						if (minuteValue < 10){
							ddv.setTravelTime(hourValue + ":0" + minuteValue);
						}else{
							ddv.setTravelTime(hourValue + ":" + minuteValue);
						}
						
						System.out.println("Added");
						ddvs.add(ddv);
					}
				}
			
			}
				*/
		}
		return ddvs;
	}

	
	
}
