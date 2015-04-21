package net.zfp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.NoResultException;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Domain;
import net.zfp.entity.Source;
import net.zfp.entity.User;
import net.zfp.entity.community.Community;
import net.zfp.entity.electricity.ElectricityDataDay;
import net.zfp.entity.electricity.ElectricityDataHour;
import net.zfp.entity.electricity.ElectricityDataMinute;
import net.zfp.entity.electricity.ElectricityDataMonth;
import net.zfp.entity.electricity.ElectricityRate;
import net.zfp.service.ElectricityService;
import net.zfp.util.AppConstants;
import net.zfp.util.DateParserUtil;
import net.zfp.view.ElectricitySummaryView;
import net.zfp.view.ElectricityView;
import net.zfp.view.GaugeView;
import net.zfp.view.ResultView;
import net.zfp.view.SourceSummaryView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class ElectricityServiceImpl implements ElectricityService {

	@Resource
	private SourceService sourceService;
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<ElectricityDataMinute> electricityDao;
	
	
	
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
		if (communityUsers != null && communityUsers.size() >0){
			for (User user : communityUsers){
				if (memberId == user.getId()) continue;
				
				sources = userDao.findSourceByAccountAndType(user.getId(), AppConstants.SOURCETYPE_CODE_ELECTRICITY);
				sourceId = null;
				for (Source source : sources){
					if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_HOME) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
						sourceId = source.getId();
						break;
					}
				}
				
				if (sourceId != null){
					Double peerRank = getSumValue(periodType, sourceId, viewType, requestTime, calendar.getActualMaximum(Calendar.DATE));
					
					if (peerRank == null) peerRank = 0.0;
					
					if (viewType == 1) peerRank /= 1000;
					
					if (peerRank > maxValue) maxValue = peerRank;
					else if (peerRank < minValue) minValue = peerRank;
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
		
		System.out.println("MIN :: " + minValue + " maxValue " + maxValue);
		gv.setZoneData(zoneData);
		
		
		return gv;

	}
	
		
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<ElectricityRate> getElectricityRates(){
		
		return electricityDao.findElectricityRate();
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
	
	private Double getRankValue(Integer period, Long sourceId, Integer viewType){
		Double totalValue = null;
		Date endDate = new Date();
		Date startDate = null;
		Integer counts = null;
		if (viewType == 1){
			if (period == 0){
				//Last 12 hours
				 startDate = new Date(endDate.getTime() - 12*60*60*1000l);
				 endDate = new Date(endDate.getTime() - 1*60*60*1000l);
				 totalValue = electricityDao.findSumElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, sourceId, startDate, endDate);
				 counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, sourceId, startDate, endDate);
				 
				 if (counts != null && counts != 12){
					 totalValue  = totalValue / counts * 12;
				 }
			}else if (period == 1){
				//Last 24 hours
				startDate = new Date(endDate.getTime() - 24*60*60*1000l);
				endDate = new Date(endDate.getTime() - 1*60*60*1000l);
				totalValue = electricityDao.findSumElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, sourceId, startDate, endDate);
				counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, sourceId, startDate, endDate);
				
				if (counts != null && counts != 24){
					totalValue = totalValue / counts * 24;
				}
			}else if (period == 2){
				//Last 30 days
				startDate = new Date(endDate.getTime() - 30*24*60*60*1000l);
				endDate = new Date(endDate.getTime() - 24*60*60*1000l);
				totalValue = electricityDao.findSumElectricity(AppConstants.TABLE_ELECTRICITY_DATA_DAY, sourceId, startDate, endDate);
				counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_DAY, sourceId, startDate, endDate);
				
				if (counts != null && counts != 30){
					totalValue = totalValue / counts * 30;
				}
			}else if (period == 3){
				//last 12 months
				Calendar c = Calendar.getInstance();
			    c.setTime(new Date());
			    c.set(Calendar.MONTH, c.get(Calendar.MONTH)-11);
				startDate = DateParserUtil.getBeginningOfMonth(c.getTime());
				
				totalValue = electricityDao.findSumElectricity(AppConstants.TABLE_ELECTRICITY_DATA_MONTH, sourceId, startDate, endDate);
				counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_MONTH, sourceId, startDate, endDate);
				
				if (counts != null && counts != 12){
					totalValue = totalValue / counts * 12;
				}
			}else if (period == 4){
				//Last 5 years
				Calendar c = Calendar.getInstance();
			    c.setTime(new Date());
			    c.set(Calendar.YEAR, c.get(Calendar.YEAR)-4);
				startDate = DateParserUtil.getBeginningOfYear(c.getTime());
				
				startDate = c.getTime();
				totalValue = electricityDao.findSumElectricity(AppConstants.TABLE_ELECTRICITY_DATA_MONTH, sourceId, startDate, endDate);
				counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_MONTH, sourceId, startDate, endDate);
				
				if (counts != null && counts != 60){
					totalValue = totalValue / counts * 60;
				}
			}
			
			//change to kWh
			if ( totalValue != null) totalValue /= 1000;
			
		}else if (viewType == 2){
			if (period == 0){
				//Last 12 hours
				 startDate = new Date(endDate.getTime() - 12*60*60*1000l);
				 totalValue = electricityDao.findSumCostElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, sourceId, startDate, endDate);
				 counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, sourceId, startDate, endDate);
				 
				 if (counts != null && counts != 12){
					 totalValue  = totalValue / counts * 12;
				 }
			}else if (period == 1){
				//Last 24 hours
				startDate = new Date(endDate.getTime() - 24*60*60*1000l);
				totalValue = electricityDao.findSumCostElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, sourceId, startDate, endDate);
				counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_HOUR, sourceId, startDate, endDate);
				
				if (counts != null && counts != 24){
					totalValue = totalValue / counts * 24;
				}
			}else if (period == 2){
				//Last 30 days
				startDate = new Date(endDate.getTime() - 30*24*60*60*1000l);
				totalValue = electricityDao.findSumCostElectricity(AppConstants.TABLE_ELECTRICITY_DATA_DAY, sourceId, startDate, endDate);
				counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_DAY, sourceId, startDate, endDate);
				
				if (counts != null && counts != 30){
					totalValue = totalValue / counts * 30;
				}
			}else if (period == 3){
				//last 12 months
				startDate = new Date(endDate.getTime() - 12*24*60*60*1000l);
				totalValue = electricityDao.findSumCostElectricity(AppConstants.TABLE_ELECTRICITY_DATA_MONTH, sourceId, startDate, endDate);
				counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_MONTH, sourceId, startDate, endDate);
				
				if (counts != null && counts != 12){
					totalValue = totalValue / counts * 12;
				}
			}else if (period == 4){
				//Last 5 years
				startDate = new Date(endDate.getTime() - 5*24*60*60*1000l);
				totalValue = electricityDao.findSumCostElectricity(AppConstants.TABLE_ELECTRICITY_DATA_MONTH, sourceId, startDate, endDate);
				counts = electricityDao.findCountsElectricity(AppConstants.TABLE_ELECTRICITY_DATA_MONTH, sourceId, startDate, endDate);
				
				if (counts != null && counts != 60){
					totalValue = totalValue / counts * 60;
				}
			}
		}
		return totalValue;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public GaugeView getElectricityConsumptionGaugeMobile(String communityCode, Long accountId, Integer period, Integer viewType){
		//Get communityId
		GaugeView gv = new GaugeView();
		
		
		if (viewType == 1){
			gv.setUnit("kWh");
		}else if (viewType == 2){
			gv.setUnit("$");
		}
		Long communityId = communityDao.getCommunityId(communityCode);
		
		Long sourceId = null;
		ResultView rv = sourceService.getMasterSourceId(accountId, AppConstants.SOURCETYPE_CODE_ELECTRICITY);
		if (rv.getResultCode() == AppConstants.SUCCESS){
			sourceId = Long.parseLong(rv.getResultMessage());
		}
		
		if (sourceId != null){
			//Get User value
			Double myRank = getRankValue((period+1), sourceId, viewType);
			if (myRank == null) myRank = 0.0;
			gv.setValue(myRank);
			
			Double minValue = myRank;
			Double maxValue = myRank;
			
			Double[] zoneData = new Double[4];
			
			//Get community users
			List<User> communityUsers = userDao.getUsersInCommunity(communityId);
			if (communityUsers != null && communityUsers.size() >0){
				for (User user : communityUsers){
					if (accountId == user.getId()) continue;
					
					sourceId = null;
					
					ResultView resultView = sourceService.getMasterSourceId(user.getId(), AppConstants.SOURCETYPE_CODE_ELECTRICITY);
					if (resultView.getResultCode() == AppConstants.SUCCESS){
						sourceId = Long.parseLong(resultView.getResultMessage());
					}
					
					if (sourceId != null){
						Double peerRank = getRankValue(period, sourceId, viewType);
						if (peerRank == null) peerRank = 0.0;
							
						if (peerRank > maxValue) maxValue = peerRank;
						else if (peerRank < minValue) minValue = peerRank;
					}
				}
			}
			
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
			
			System.out.println("PERIOD :: " + period);
			
			System.out.println("MIN :: " + minValue + " maxValue " + maxValue);
			gv.setZoneData(zoneData);
		}
		
		return gv;

	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public GaugeView getElectricityConsumptionGauge(String domainName, Long accountId, Integer period, Integer viewType){
		//Get communityId
		GaugeView gv = new GaugeView();
		
		if (viewType == 1){
			gv.setUnit("kWh");
		}else if (viewType == 2){
			gv.setUnit("$");
		}
		Long communityId = getCommunityId(domainName);
		
		//In AccountSource table, check if there are any sources associated with account
		List<Source> sources = userDao.findSourceByAccountAndType(accountId, AppConstants.SOURCETYPE_CODE_ELECTRICITY);
		Long sourceId = null;
		for (Source source : sources){
			if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_HOME) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				sourceId = source.getId();
				break;
			}
		}
		
		if (sourceId != null){
			//Get User value
			Double myRank = getRankValue((period+1), sourceId, viewType);
			if (myRank == null) myRank = 0.0;
			gv.setValue(myRank);
			
			Double minValue = myRank;
			Double maxValue = myRank;
			
			Double[] zoneData = new Double[4];
			
			//Get community users
			List<User> communityUsers = userDao.getUsersInCommunity(communityId);
			if (communityUsers != null && communityUsers.size() >0){
				for (User user : communityUsers){
					if (accountId == user.getId()) continue;
					
					sources = userDao.findSourceByAccountAndType(user.getId(), AppConstants.SOURCETYPE_CODE_ELECTRICITY);
					sourceId = null;
					for (Source source : sources){
						if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_HOME) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
							sourceId = source.getId();
							break;
						}
					}
					
					if (sourceId != null){
						Double peerRank = getRankValue(period, sourceId, viewType);
						if (peerRank == null) peerRank = 0.0;
							
						if (peerRank > maxValue) maxValue = peerRank;
						else if (peerRank < minValue) minValue = peerRank;
					}
				}
			}
			
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
			
			System.out.println("PERIOD :: " + period);
			
			System.out.println("MIN :: " + minValue + " maxValue " + maxValue);
			gv.setZoneData(zoneData);
		}
		
		return gv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<ElectricityView> getElectricityConsumptionDataMobile(String communityCode, Long accountId, Integer period, Boolean isPrevious){
		System.out.println("ElectricityServiceConsumptionData :: " + new Date());
		//Get communityId
		Long communityId = communityDao.getCommunityId(communityCode);
		//Get AccountId
		
		//In AccountSource table, check if there are any sources associated with account
		Long sourceId = null;
		ResultView rv = sourceService.getMasterSourceId(accountId, AppConstants.SOURCETYPE_CODE_ELECTRICITY);
		if (rv.getResultCode() == AppConstants.SUCCESS){
			sourceId = Long.parseLong(rv.getResultMessage());
		}
		List<ElectricityView> evs = new ArrayList<ElectricityView>();
		if (sourceId != null){
			//Check period
			/*
			 * 0  : hourly
			 * 1  : daily
			 * 2  : monthly
			 * 3  : yearly
			 */
			
			System.out.println("PERIOD :: " + period);
			switch (period){
			case 0:
				//Fetch from hourly table
				//Last 24 hours
				evs = summarizeHourlyData(sourceId, 24, isPrevious);
				break;
			case 1:
				//Fetch from daily table
				//Last 30 days
				evs = summarizeDailyData(sourceId, 30, isPrevious);
				break;
			case 2:
				//Fetch from Monthly table
				//Last 12 months
				evs = summarizeMonthlyData(sourceId, 12, isPrevious);
				break;
			case 3:
				//Fetch from year
				//Last 5 year
				
				evs = summarizeYearlyData(sourceId, 5, isPrevious);
				break;
			}
		}
		return evs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<ElectricityView> getElectricityConsumptionData(String domainName, Long accountId, Integer period, Boolean isPrevious){
		System.out.println("ElectricityServiceConsumptionData :: " + new Date());
		//Get communityId
		Long communityId = getCommunityId(domainName);
		//Get AccountId
		
		//In AccountSource table, check if there are any sources associated with account
		List<Source> sources = userDao.findSourceByAccountAndType(accountId, AppConstants.SOURCETYPE_CODE_ELECTRICITY);
		Long sourceId = null;
		for (Source source : sources){
			if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_HOME) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				sourceId = source.getId();
				break;
			}
		}
		
		List<ElectricityView> evs = new ArrayList<ElectricityView>();
		if (sourceId != null){
			//Check period
			/*
			 * 0  : hourly
			 * 1  : daily
			 * 2  : monthly
			 * 3  : yearly
			 */
			
			System.out.println("PERIOD :: " + period);
			switch (period){
			case 0:
				//Fetch from hourly table
				//Last 24 hours
				evs = summarizeHourlyData(sourceId, 24, isPrevious);
				break;
			case 1:
				//Fetch from daily table
				//Last 30 days
				evs = summarizeDailyData(sourceId, 30, isPrevious);
				break;
			case 2:
				//Fetch from Monthly table
				//Last 12 months
				evs = summarizeMonthlyData(sourceId, 12, isPrevious);
				break;
			case 3:
				//Fetch from year
				//Last 5 year
				
				evs = summarizeYearlyData(sourceId, 5, isPrevious);
				break;
			}
		}
		return evs;
	}
	
	
	private List<ElectricityView> summarizeYearlyData(Long sourceId, Integer years, boolean isPrevious){
		List<ElectricityView> evs = new ArrayList<ElectricityView>();
		
		//Last 24 hours
		Date currentTime = null;
		Calendar c = Calendar.getInstance();
	    c.setTime(new Date());
	    
		if (isPrevious){
			 c.set(Calendar.YEAR, c.get(Calendar.YEAR)-(years-1)-(years));
		}else{
			 c.set(Calendar.YEAR, c.get(Calendar.YEAR)-(years-1));
		}
		
		currentTime = c.getTime();
		
		Date startTime = DateParserUtil.getBeginningOfYear(currentTime);
		Date endTime = DateParserUtil.getEndOfYear(currentTime);
		
		int counter = 0;
		
		while (counter < years){
			ElectricityView ev = new ElectricityView();
			
			ElectricityDataMonth edh = null;
			List<ElectricityDataMonth> edhs = electricityDao.findElectricityDataYear(sourceId, startTime, endTime);
			
			if (edhs != null && edhs.size() > 0){
				edh = new ElectricityDataMonth();
				Double energy = 0.0;
				Double onPeakConsumption = 0.0;
				Double midPeakConsumption = 0.0;
				Double offPeakConsumption = 0.0;
				Double onPeakSpend = 0.0;
				Double midPeakSpend = 0.0;
				Double offPeakSpend = 0.0;
				for (ElectricityDataMonth e : edhs){
					energy += e.getEnergy();
					onPeakConsumption += e.getOnPeakConsumption();
					onPeakSpend += e.getOnPeakSpend();
					midPeakConsumption += e.getMidPeakConsumption();
					midPeakSpend += e.getMidPeakSpend();
					offPeakConsumption += e.getOffPeakConsumption();
					offPeakSpend += e.getOffPeakSpend();
					
				}
				edh.setCreated(startTime);
				edh.setEnergy(energy);
				edh.setOnPeakConsumption(onPeakConsumption);
				edh.setOnPeakSpend(onPeakSpend);
				edh.setMidPeakConsumption(midPeakConsumption);
				edh.setMidPeakSpend(midPeakSpend);
				edh.setOffPeakConsumption(offPeakConsumption);
				edh.setOffPeakSpend(offPeakSpend);
				
			}
			if (edh != null){
				ev.setAggregationStartDate(getCurrentYear(edh.getCreated()));
				ev.setConsumption(Math.round( edh.getEnergy()/1000* 100.0 ) / 100.0);
				//Energy is in Wh 
				ev.setCost(Math.round( (edh.getOnPeakSpend() + edh.getMidPeakSpend() + edh.getOffPeakSpend())* 100.0 ) / 100.0);
				ev.setOnPeakConsumption(edh.getOnPeakConsumption());
				ev.setMidPeakConsumption(edh.getMidPeakConsumption());
				ev.setOffPeakConsumption(edh.getOffPeakConsumption());
				evs.add(ev);
			}else{
				ev.setAggregationStartDate(getCurrentYear(startTime));
				evs.add(ev);
			}
			
			counter++;
			startTime = new Date(endTime.getTime() + 1l);
			endTime = DateParserUtil.getEndOfYear(startTime);
		}
		
		
		return evs;
	}

	private List<ElectricityView> summarizeMonthlyData(Long sourceId, Integer months, boolean isPrevious){
		
		List<ElectricityView> evs = new ArrayList<ElectricityView>();
		
		//Last 24 hours
		Date currentTime = null;
		Calendar c = Calendar.getInstance();
	    c.setTime(new Date());
	    
		if (isPrevious){
			 c.set(Calendar.MONTH, c.get(Calendar.MONTH)-(months-1)-(months));
		}else{
			 c.set(Calendar.MONTH, c.get(Calendar.MONTH)-(months-1));
		}
		
		currentTime = c.getTime();
		
		Date startTime = DateParserUtil.getBeginningOfMonth(currentTime);
		Date endTime = DateParserUtil.getEndOfMonth(currentTime);
		
		int counter = 0;
		
		while (counter < months){
			
			
			
			ElectricityView ev = new ElectricityView();
			ElectricityDataMonth edh = electricityDao.findElectricityDataMonth(sourceId, startTime);
			
			if (edh != null){
				ev.setAggregationStartDate(getCurrentMonth(edh.getCreated()));
				ev.setConsumption(Math.round( edh.getEnergy()/1000* 100.0 ) / 100.0);
				//Energy is in Wh 
				ev.setCost(Math.round( (edh.getOnPeakSpend() + edh.getMidPeakSpend() + edh.getOffPeakSpend())* 100.0 ) / 100.0);
				ev.setOnPeakConsumption(edh.getOnPeakConsumption());
				ev.setMidPeakConsumption(edh.getMidPeakConsumption());
				ev.setOffPeakConsumption(edh.getOffPeakConsumption());
				evs.add(ev);
			}else{
				ev.setAggregationStartDate(getCurrentMonth(startTime));
				evs.add(ev);
			}
			
			counter++;
			startTime = new Date(endTime.getTime() + 1l);
			endTime = DateParserUtil.getEndOfMonth(startTime);
		}
		
		System.out.println(evs.size());
		
		return evs;
	}

	private List<ElectricityView> summarizeDailyData(Long sourceId, Integer days, boolean isPrevious){
		
		List<ElectricityView> evs = new ArrayList<ElectricityView>();
		
		//Last 24 hours
		Date currentTime = null;
		if (isPrevious){
			currentTime = new Date(new Date().getTime() - 2 * days * 24 * 60 * 60 * 1000l);
		}else{
			currentTime = new Date(new Date().getTime() - days * 24 * 60 * 60 * 1000l);
		}
		
		Date startTime = DateParserUtil.getBeginningOfDay(currentTime);
		Date endTime = DateParserUtil.getEndOfDay(currentTime);
		
		int counter = 0;
		
		while (counter < days){
			ElectricityView ev = new ElectricityView();
			ElectricityDataDay edh = electricityDao.findElectricityDataDay(sourceId, startTime);
			
			if (edh != null){
				ev.setAggregationStartDate(getCurrentDay(edh.getCreated()));
				ev.setConsumption(Math.round( edh.getEnergy()/1000* 100.0 ) / 100.0);
				//Energy is in Wh 
				ev.setCost(Math.round( (edh.getOnPeakSpend() + edh.getMidPeakSpend() + edh.getOffPeakSpend())* 100.0 ) / 100.0);
				ev.setOnPeakConsumption(edh.getOnPeakConsumption());
				ev.setMidPeakConsumption(edh.getMidPeakConsumption());
				ev.setOffPeakConsumption(edh.getOffPeakConsumption());
				evs.add(ev);
			}else{
				ev.setAggregationStartDate(getCurrentDay(startTime));
				evs.add(ev);
			}
			
			counter++;
			startTime = new Date(endTime.getTime() + 1l);
			endTime = DateParserUtil.getEndOfDay(startTime);
		}
		
		return evs;
	}

	
	//private List<ElectricityView> summarizeHourlyData(List<ElectricityDataHour> edhs){
	private List<ElectricityView> summarizeHourlyData(Long sourceId, Integer hours, boolean isPrevious){
		List<ElectricityView> evs = new ArrayList<ElectricityView>();
		
		//Last 24 hours
		Date currentTime = null;
		if (isPrevious){
			currentTime = new Date(new Date().getTime() - 2 * hours * 60 * 60 * 1000l);
		}else{
			currentTime = new Date(new Date().getTime() - hours * 60 * 60 * 1000l);
		}
		
		Date startTime = DateParserUtil.getBeginningOfHour(currentTime);
		Date endTime = DateParserUtil.getEndOfHour(currentTime);
		
		int counter = 0;
		
		while (counter < hours){
			ElectricityView ev = new ElectricityView();
			ElectricityDataHour edh = electricityDao.findElectricityDataHour(sourceId, startTime);
			
			if (edh != null){
				ev.setAggregationStartDate(getCurrentHour(edh.getCreated()));
				ev.setConsumption(Math.round( edh.getEnergy()/1000* 100.0 ) / 100.0);
				//Energy is in Wh 
				ev.setCost(Math.round( (edh.getOnPeakSpend() + edh.getMidPeakSpend() + edh.getOffPeakSpend())* 100.0 ) / 100.0);
				ev.setOnPeakConsumption(edh.getOnPeakConsumption());
				ev.setMidPeakConsumption(edh.getMidPeakConsumption());
				ev.setOffPeakConsumption(edh.getOffPeakConsumption());
				evs.add(ev);
			}else{
				ev.setAggregationStartDate(getCurrentHour(startTime));
				evs.add(ev);
			}
			
			counter++;
			startTime = new Date(endTime.getTime() + 1l);
			endTime = DateParserUtil.getEndOfHour(startTime);
		}
		
		return evs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<SourceSummaryView> getUtilitySummaryData(Long accountId){
		List<SourceSummaryView> ssvs = new ArrayList<SourceSummaryView>();
		User user = userDao.findById(accountId);
		Long communityId = user.getDefaultCommunity();
		//catgoryType
		//Electricity
		List<Source> sources = null;
		sources = userDao.findDefaultSourceByAccountAndType(accountId, AppConstants.SOURCETYPE_CODE_ELECTRICITY);
		
		/*
		 * If there is no default source... fetch all non default source
		 */
		if (sources.isEmpty()){
			sources = userDao.findSourceByAccountAndType(accountId, AppConstants.SOURCETYPE_CODE_ELECTRICITY);
		}
		
		Long sourceId = null;
		for (Source source : sources){
			if (source.getStatus().getCode().equals(AppConstants.STATUS_CODE_ACTIVE) && source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_HOME) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				sourceId = source.getId();
				break;
			}
		}
		
		if (sourceId != null){
			Date current = new Date();
			List<ElectricityDataMonth> edms = electricityDao.findLatestElectricityMonth(13, sourceId);
			
			if (edms != null && edms.size() > 0){
				Comparator<ElectricityDataMonth> comparator = new Comparator<ElectricityDataMonth>(){
					public int compare(ElectricityDataMonth date1, ElectricityDataMonth date2){
						return date1.getCreated().compareTo(date2.getCreated());
					}
				};
				Collections.sort(edms, comparator);
				
				for (int i = 0; i < edms.size(); i++){
					
					ElectricityDataMonth edh = edms.get(i);
					SourceSummaryView ssv = new SourceSummaryView();
					ssv.setType(1);
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date(edh.getCreated().getTime()));
					int month = cal.get(Calendar.MONTH);
					int year = cal.get(Calendar.YEAR);
					ssv.setDate(edh.getCreated());
					ssv.setMonth(month);
					ssv.setYear(year);
					ssv.setSourceId(sourceId);
					
					Double spending = edh.getOnPeakSpend() + edh.getMidPeakSpend() + edh.getOffPeakSpend();
					ssv.setSpend(Math.round(spending * 100.0)/100.0);
					ssv.setSavings(Math.round((spending *0.12 *12) * 100.0)/100.0);
					Double consumption = edh.getOnPeakConsumption() + edh.getMidPeakConsumption() + edh.getOffPeakConsumption();
					ssv.setConsumption(Math.round(consumption/1000 * 100.0)/100.0 );
					
					
					ssvs.add(ssv);
				}
				
				List<Long> accountIds = userDao.getPeersInCommunity(accountId, communityId);
				Map<Long, Long> accountSources = new HashMap<Long, Long>();
				
				if (accountIds != null){
					for (Long id : accountIds){
						sources = userDao.findSourceByAccountAndType(id, AppConstants.SOURCETYPE_CODE_ELECTRICITY);
						sourceId = null;
						if (sources != null){
							for (Source source : sources){
								
								if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_HOME) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
									sourceId = source.getId();
								}
							}
						}
						if (sourceId != null) accountSources.put(id, sourceId);

					}
				}
				
				SourceSummaryView other = null;
				for (int i=0; i< ssvs.size(); i++){
					
					Iterator iter = accountSources.entrySet().iterator();
					
					if (i==0) ssvs.get(i).setTrend(-1);
					else{
						
						int daysOfCurrent = getDaysOfMonth(ssvs.get(i).getDate());
						Double currentEnergyUsage = ssvs.get(i).getConsumption() / daysOfCurrent;
						
						other = ssvs.get(i-1);
						int daysOfPrevious = getDaysOfMonth(other.getDate());
						
						//Average the energy usage
						Double previousEnergyUsage = (other.getConsumption()) / daysOfPrevious;
						
						Double rateChange = (previousEnergyUsage - currentEnergyUsage)/currentEnergyUsage;
						
						if (previousEnergyUsage > currentEnergyUsage){
							ssvs.get(i).setTrend(1);
							
						}else if (previousEnergyUsage < currentEnergyUsage){
							ssvs.get(i).setTrend(2);
							rateChange *= -1;
						}else{
							ssvs.get(i).setTrend(-1);
						}
						ssvs.get(i).setPrevDiff(Math.round(rateChange * 10.0)/10.0);
					}
					
					int peerSize = 1;
					int counter = 1;
					//calculate peers steps
					while (iter.hasNext()){
						Map.Entry<Long, Long> entry = (Map.Entry) iter.next();
						Double consumption = electricityDao.getElectricityMonthConsumptionBySource(entry.getValue(), ssvs.get(i).getMonth()+1, ssvs.get(i).getYear());
						if (consumption != null){
							consumption = Math.round(consumption/1000 * 100.0)/100.0;
							if (ssvs.get(i).getConsumption() > consumption ) counter ++;
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
	
	
	public static Date getLastHour(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, -1);
		
		return c.getTime();
	}
	
	public static Date getYesterday(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		
		return c.getTime();
	}
	
	public static Date getLastMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		
		return c.getTime();
	}
	
	public static Date getLastYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, -1);
		
		return c.getTime();
	}
	
	public static String getCurrentYear(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		Integer days = calendar.get(Calendar.YEAR);
		return days.toString();
	}
	
	public static String getCurrentHour(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		Integer days = calendar.get(Calendar.HOUR_OF_DAY);
		return days.toString();
	}
	
	public static String getCurrentDay(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		Integer days = calendar.get(Calendar.DAY_OF_MONTH);
		return days.toString();
	}
	
	public static int getCurrentMonthInNumber(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		return month + 1;
	}
	
	public static String getCurrentMonth(Date date){
		
		return new SimpleDateFormat("MMM").format(date);
	}
	
	public static int getCurrentDaysOfMonth(Date date){
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static int getDaysOfMonth(Date date){
		Calendar current = new GregorianCalendar();
		current.setTime(new Date());
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		int currentMonth = current.get(Calendar.MONTH);
		int targetMonth = calendar.get(Calendar.MONTH);
		if (currentMonth == targetMonth){
			return calendar.get(Calendar.DAY_OF_MONTH);
		}else{
			return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		
	}
	
	public static Date getBeginningOfMonth(Date date){
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		
		return new Date(calendar.getTimeInMillis());
	}
	
	public static Date getEndOfMonth(Date date){
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		
		return new Date(calendar.getTimeInMillis());
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
