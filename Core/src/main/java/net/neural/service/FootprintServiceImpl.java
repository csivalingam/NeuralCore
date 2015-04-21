package net.zfp.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.AccountSource;
import net.zfp.entity.Domain;
import net.zfp.entity.PersonalFootprintSurvey;
import net.zfp.entity.Source;
import net.zfp.entity.SourceType;
import net.zfp.entity.User;
import net.zfp.entity.carbon.CarbonFootprint;
import net.zfp.entity.community.Community;
import net.zfp.entity.source.SourceMode;
import net.zfp.form.FootprintSurveyForm;
import net.zfp.service.FootprintService;
import net.zfp.util.AppConstants;
import net.zfp.view.FootprintView;
import net.zfp.view.GaugeView;
import net.zfp.view.HistoryView;
import net.zfp.view.LeaderboardView;
import net.zfp.view.MediaScreenView;
import net.zfp.view.PersonalDashboardView;
import net.zfp.view.ResultView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Resource
public class FootprintServiceImpl implements FootprintService {
	
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<CarbonFootprint> carbonFootprintDao;
	@Resource
	private EntityDao<PersonalFootprintSurvey> personalFootprintSurveyDao;
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<AccountSource> accountSourceDao;
	@Resource
	private EntityDao<Source> sourceDao;
	
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public List<FootprintView> getMyFootprint(String domainName, Long accountId, Long footprintType){
		System.out.println("***********************************************GET getMyFootprint*********************************");
		List<FootprintView> fvs = new ArrayList<FootprintView>();
		//Get communityId
		Long communityId = getCommunityId(domainName);
		//Get AccountId
		
		String category = "";
		String st = "";
		if (footprintType == 1l){
			category = AppConstants.CATEGORY_CODE_CORPORTATE;
			st = AppConstants.SOURCETYPE_CODE_CORPORATE_FOOTPRINT;
		}else if (footprintType == 2l){
			category = AppConstants.CATEGORY_CODE_PERSONAL;
			st = AppConstants.SOURCETYPE_CODE_PERSONAL_FOOTPRINT;
		}
		
		//In AccountSource table, check if there are any sources associated with account
		List<Source> sources = userDao.findSourceByAccountAndType(accountId, st);
		Long sourceId = null;
		for (Source source : sources){
			System.out.println("Source :: " + source.getName() + " From Source ::  Category :: " + category);
			if (source.getCategory().getCode().equals(category) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				sourceId = source.getId();
				System.out.println("***********************************************Match *********************************" + sourceId);
				break;
			}
		}
		if (sourceId == null) return null;
		
		//Get All sourceType
		List<SourceType> sourceTypes = sourceDao.getSourceTypes();
		for (SourceType sourceType :sourceTypes){
			//cfs can only have max two results (latest)
			System.out.println("***********************************************sourceType *********************************" + sourceType);
			List<CarbonFootprint> cfs = carbonFootprintDao.findFirstAndSecondValueSourceTypeAndSource(sourceType.getId(), sourceId, communityId, footprintType);
			if (cfs.size()>0){
				
				FootprintView fv = new FootprintView();
				fv.setSourceId(sourceId);
				fv.setFootprintType(footprintType);
				fv.setSourceType(sourceType.getId());
				fv.setName(sourceType.getName());
				fv.setValue(cfs.get(0).getValue());
				if (cfs.size() ==2){
					if (cfs.get(0).getValue() > cfs.get(1).getValue()){
						fv.setTrend("u");
					}else{
						fv.setTrend("d");
					}
				}else{
					fv.setTrend("e");
				}
				fvs.add(fv);
			}
		}
		
		return fvs;
	}
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public PersonalDashboardView getCarbonFootprint( String domainName,  Long accountId){
		System.out.println("***********************************************GET getCarbonFootprint*********************************");
		PersonalDashboardView pdv = new PersonalDashboardView();
		
		//Get communityId
		Long communityId = getCommunityId(domainName);
		//Get AccountId
		User user = userDao.findById(accountId);
		
		//In AccountSource table, check if there are any sources associated with account
		List<Source> sources = userDao.findSourceByAccountAndType(accountId, AppConstants.SOURCETYPE_CODE_PERSONAL_FOOTPRINT);
		Long sourceId = null;
		for (Source source : sources){
			//Each sources check if category is 1 PERSOANL_FOOTPRINT SURVEY SAVE source id for further use
			if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_PERSONAL) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				sourceId = source.getId();
				break;
			}
		}
		
		Double elec = carbonFootprintDao.findValueBySourceTypeAndSource(1l, sourceId, communityId, 2l);
		Double ng = carbonFootprintDao.findValueBySourceTypeAndSource(2l, sourceId, communityId, 2l);
		Double water = carbonFootprintDao.findValueBySourceTypeAndSource(9l, sourceId, communityId, 2l);
		Double waste = carbonFootprintDao.findValueBySourceTypeAndSource(6l, sourceId, communityId, 2l);
		Double vehicle = carbonFootprintDao.findValueBySourceTypeAndSource(4l, sourceId, communityId, 2l);
		Double flight = carbonFootprintDao.findValueBySourceTypeAndSource(10l, sourceId, communityId, 2l);
		
		pdv.setNickName(user.getFirstName());
		pdv.setSourceId(sourceId);
		pdv.setElecValue(elec);
		pdv.setNgValue(ng);
		pdv.setWaterValue(water);
		pdv.setWasteValue(waste);
		pdv.setVehicleValue(vehicle);
		pdv.setFlightValue(flight);
		
		return pdv;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public PersonalFootprintSurvey getPersonalFootprintSurvey(String domainName, Long accountId){
		System.out.println("***********************************************GET PERSONAL FOOTPRINT SURVEY*********************************");
		//Get communityId
		Long communityId = getCommunityId(domainName);
		
		//PERSONAL_FOOT_PRINT_STEP_1 if not found entry in PersonalFootprintSurvey
		//In AccountSource table, check if there are any sources associated with account
		List<Source> sources = userDao.findSourceByAccountAndType(accountId, AppConstants.SOURCETYPE_CODE_PERSONAL_FOOTPRINT);
		Long sourceId = null;
		for (Source source : sources){
			//Each sources check if category is 1 PERSOANL_FOOTPRINT SURVEY SAVE source id for further use
			if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_PERSONAL) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				sourceId = source.getId();
				break;
			}
		}
		
		System.out.println("Source ID : " + sourceId);
		//From here, we expect source exists because before this method, we checked already
		//However we just want to double check...
		if (sourceId == null){
			return null;
		}else{
			PersonalFootprintSurvey pfs = personalFootprintSurveyDao.findSurveyByCommunity(sourceId, communityId);
			return pfs;
		}
	}
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public ResultView checkFootprintStep(String domainName, Long accountId){
		
		System.out.println("***********************************************CHECK FOOTPRINT STEP*********************************");
		//Get communityId
		Long communityId = getCommunityId(domainName);
		//Get AccountId
		
		//Check if has source
		ResultView rv = new ResultView();
		//Default to GET_STARTED Page
		rv.setResultCode(AppConstants.PERSONAL_FOOT_PRINT_STEP_1);
		
		//PERSONAL_FOOT_PRINT_STEP_1 if not found entry in PersonalFootprintSurvey
		//In AccountSource table, check if there are any sources associated with account
		List<Source> sources = userDao.findSourceByAccountAndType(accountId, AppConstants.SOURCETYPE_CODE_PERSONAL_FOOTPRINT);
		Long sourceId = null;
		for (Source source : sources){
			//Each sources check if category is 1 PERSOANL_FOOTPRINT SURVEY SAVE source id for further use
			if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_PERSONAL) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				sourceId = source.getId();
				break;
			}
		}
		
		if (sourceId != null){
			//Check if this sourceId has entry for PersonalFootprintSurvey
			PersonalFootprintSurvey pfs = personalFootprintSurveyDao.findSurveyByCommunity(sourceId, communityId);
			if(pfs != null){
				//Entry found! Go to step 2 !
				rv.setResultCode(AppConstants.PERSONAL_FOOT_PRINT_STEP_2);
				//PERSONAL_FOOT_PRINT_STEP_2 if not found entry in CarbonFootprint
				List<CarbonFootprint> cfs = carbonFootprintDao.findCarbonFootprint(sourceId, communityId, 2l);
				if (cfs.size()>0){
					//Entry found! Go to step 3!
					rv.setResultCode(AppConstants.PERSONAL_FOOT_PRINT_STEP_3);
				}
			}
		}
		return rv;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public GaugeView getGaugeData(String domainName, Long footprintTypeId ){
		
		System.out.println("--------------------------------------------GaugeData--------------------------");
		GaugeView gaugeView = new GaugeView();
		Long communityId = getCommunityId(domainName);
		
		Date endDate = new Date();
		
		Calendar startCal = Calendar.getInstance();
		startCal.add(Calendar.YEAR, -1);
		Date startDate = new Date(startCal.getTimeInMillis());
		
		Double[] zoneData = new Double[4];
		List<Double> datas = carbonFootprintDao.findFootprintByCommunity(communityId, startDate, endDate, footprintTypeId);
		
		System.out.println("data sizes :: " + datas.size() + " community ID :: " + communityId + " startDate :: " + startDate + "endDate :: "+ endDate);
		
		zoneData[0] = datas.get(0);
		zoneData[3] = datas.get(datas.size()-1);
		Double interval = (zoneData[3] - zoneData[0])/3;
		zoneData[1] = zoneData[0] + interval;
		zoneData[2] = zoneData[3] - interval;
		
		gaugeView.setZoneData(zoneData);
		gaugeView.setUnit("tCO2");
		return gaugeView;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public ResultView storeSurveyData(FootprintSurveyForm surveyForm){
		
		System.out.println("STORE SURVEY DATA :: " + new Date());
		
		//Get community Id
		Long communityId = getCommunityId(surveyForm.getCommunityName());
		//Get Community
		Community community = communityDao.getCommunityById(communityId);
		User user = userDao.findById(surveyForm.getAccountId());
		
		//Check AccountSource
		List<Source> sources = userDao.findSourceByAccountAndType(user.getId(), AppConstants.SOURCETYPE_CODE_PERSONAL_FOOTPRINT);
		Source s = null;
		for (Source source : sources){
			//Each sources check if category is 1 PERSOANL_FOOTPRINT SURVEY SAVE source id for further use
			if (source.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_PERSONAL) && source.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
				s = source;
				break;
			}
		}
		if (s == null){
			//No AccountSource...!
			Source userSource = sourceDao.findSourceBySourceCode(user.getEmail());
			
			if (userSource == null){
				//There is no source attached. we need to make source
				Source source = new Source();
				source.setName(user.getFirstName());
				source.setCode(user.getEmail());
				source.setCategory(userDao.getCategoryByCode(AppConstants.CATEGORY_CODE_PERSONAL, AppConstants.CATEGORYTYPE_SOURCE));
				source.setSourceType(userDao.getSourceTypeByCode(AppConstants.SOURCETYPE_CODE_PERSONAL_FOOTPRINT));
				source.setSupressName(false);
				source.setSupressDisplay(false);
				source.setStatus(userDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
				
				SourceMode virtualSource = sourceDao.getSourceModeByCode(AppConstants.SOURCEMODE_CODE_VIRTUAL);
				source.setSourceMode(virtualSource);
				
				sourceDao.save(source);
				Source src = sourceDao.findSourceBySourceCode(source.getCode());
				if (src != null){
					s = src;
				}
				
				System.out.println(s.getId() + ":: source ID !!!?!?!?!?!?");
				
			}else{
				if (userSource.getCategory().getCode().equals(AppConstants.CATEGORY_CODE_PERSONAL) && userSource.getCategory().getCategoryType().getId() == AppConstants.CATEGORYTYPE_SOURCE){
					s = userSource;
				}
			}
			
			if (s != null){
				AccountSource as = new AccountSource();
				as.setUser(user);
				as.setSource(s);
				as.setStatus(userDao.getStatusByCode(AppConstants.STATUS_ACTIVE));
				
				//Check if account source already exits if not then save it
				List<AccountSource> accountSources = sourceDao.findAccountSourceBYAccountAndSource(user.getId(), s.getId());
				if (accountSources.size() == 0){
					accountSourceDao.save(as);
				}
			}
		}
		
		
		System.out.println("Source ID " + s.getId());
		//Check if it exists
		PersonalFootprintSurvey pfs = personalFootprintSurveyDao.findSurveyByCommunity(s.getId(), communityId);
		
		ResultView result = new ResultView();
		
		try {
			if (pfs== null){
				pfs = new PersonalFootprintSurvey();
				
				pfs.setAgegroup(surveyForm.getAgegroup());
				pfs.setHeatingType(surveyForm.getHeatingType());
				pfs.setWaterHeatingType(surveyForm.getWaterHeatingType());
				pfs.setHomesquarefeet(surveyForm.getHomesquarefeet());
				pfs.setNickName(user.getFirstName());
				pfs.setOccupants(surveyForm.getOccupants());
				pfs.setPostalcode(surveyForm.getPostalcode());
				pfs.setCommunity(community);
				pfs.setSource(s);
				personalFootprintSurveyDao.save(pfs);
				
			}else{
				pfs.setAgegroup(surveyForm.getAgegroup());
				pfs.setHeatingType(surveyForm.getHeatingType());
				pfs.setWaterHeatingType(surveyForm.getWaterHeatingType());
				pfs.setHomesquarefeet(surveyForm.getHomesquarefeet());
				pfs.setNickName(user.getFirstName());
				pfs.setOccupants(surveyForm.getOccupants());
				pfs.setPostalcode(surveyForm.getPostalcode());
				pfs.setCommunity(community);
				pfs.setSource(s);
				
				personalFootprintSurveyDao.save(pfs, true);
			}
			System.out.println("************ Success save survey info to DB");
		}catch (Exception e) {
			System.out.println("************ Save feedback failure");
			e.printStackTrace();
			result.fill(AppConstants.FAILURE, "Add feedback failure");
			return result;
		}
		
		result.fill(AppConstants.SUCCESS, "Add feedback success");
		return result;
	}
	
	
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public List<HistoryView> getHistoryData(String domainName, Long sourceId){
		
		System.out.println("--------------------------------------------HistoryData--------------------------");
		List<HistoryView> historyViews = new ArrayList<HistoryView>();

		//Get community Id
		Long communityId = getCommunityId(domainName);
		
		//Get Latest 8(max) Dates
		List<Date> dates = carbonFootprintDao.findDatesBySourceAndCommunity(sourceId, communityId);

		//Reverse the order
		Comparator<Date> comparator = new Comparator<Date>(){
			public int compare(Date date1, Date date2){
				return date1.compareTo(date2);
			}
		};
		Collections.sort(dates, comparator);
		System.out.println("----------------------------- dates size :: " + dates.size());
		
		for (Date date : dates){
			HistoryView hv = new HistoryView();
			DateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");

			hv.setDate(formatter.format(date));
			Double value = carbonFootprintDao.findValueByDateAndSource(sourceId, communityId, date);
			hv.setValue(value);
			
			historyViews.add(hv);
		}
		
		return historyViews;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public List<LeaderboardView> getLeaderboardData(String domainName, Long sourceId, Long footprintTypeId ){
		
		System.out.println("--------------------------------------------LeaderboardData--------------------------");
		List<LeaderboardView> leaderboardViews = new ArrayList<LeaderboardView>();
		Long communityId = getCommunityId(domainName);
		
		Date endDate = new Date();
		
		Calendar startCal = Calendar.getInstance();
		startCal.add(Calendar.YEAR, -1);
		Date startDate = new Date(startCal.getTimeInMillis());
		
		//Get list of source
		List<Source> sources = carbonFootprintDao.findSourceByCommunity(communityId, startDate, endDate, footprintTypeId);
		Integer greenZone =0, yellowzone =0;
		if (sources.size() == 1){
			greenZone = 0;
		}else if (sources.size() == 2){
			greenZone = 0;
			yellowzone = 1;
		}else{
			greenZone = sources.size()/3;
			yellowzone = 2 * sources.size()/3;
		}
		
		boolean isSourceIncluded= false;
		
		for (int i = 0; i<sources.size(); i++){
			//Pass the 10th element if a user is not found yet
			if (!isSourceIncluded && sources.get(i).getId() != sourceId && i >8){
				continue;
			}
			
			//Each source get Id and name and value
			LeaderboardView leaderboardView = new LeaderboardView();
			if (i <= greenZone){
				leaderboardView.setRank(1);
			}else if (i <= yellowzone){
				leaderboardView.setRank(2);
			}else{
				leaderboardView.setRank(3);
			}
			leaderboardView.setSourceId(sources.get(i).getId());
			leaderboardView.setSourceName(sources.get(i).getName());
			
			System.out.println("NAME ::::: " + sources.get(i).getName());
			Double value = carbonFootprintDao.findValueBySourceAndCommunity(sources.get(i).getId(), communityId, startDate, endDate);
			leaderboardView.setValue(value);
			leaderboardView.setRanking(i+1);
			
			leaderboardViews.add(leaderboardView);
			
			if (sources.get(i).getId() == sourceId){
				isSourceIncluded = true;
				System.out.println("Found the source !!!!!!");
			}
			
			if (isSourceIncluded){
				if (i >= 9){
					break;
				}
			}
			
		}
		
		return leaderboardViews;
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
