package net.zfp.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Location;
import net.zfp.entity.Translation;
import net.zfp.entity.User;
import net.zfp.entity.account.MemberExtendedProfile;
import net.zfp.entity.community.Community;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.weather.ForecastWeatherData;
import net.zfp.entity.weather.ForecastWeatherDataPOJO;
import net.zfp.entity.weather.WeatherRuleResult;
import net.zfp.form.NewsForm;
import net.zfp.util.AppConstants;
import net.zfp.util.DateParserUtil;
import net.zfp.view.ResultView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class WeatherServiceImpl implements WeatherService {
	@Resource
    private SegmentService segmentService;
	@Resource
    private WhatsUpService whatsUpService;
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<MemberExtendedProfile> memberExtendedProfileDao;
	@Resource
	private EntityDao<ForecastWeatherData> forecastWeatherDataDao;
	
	/*
	private static RuleBase rbase = RuleBaseFactory.newRuleBase();;
	private static PackageBuilder pbuilder = new PackageBuilder();
	private static StatefulSession sessionObject;
	private static String WEATHER_RULE = "/net/zfp/velo/rules/heatWeather.drl";
	*/
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setWeatherAlerts(String domainName){
		System.out.println("Entered Set Weather Alerts : " + new Date());
		ResultView rv = new ResultView();
		/*
		//Get Community
		Community community = communityDao.getCommunity(domainName);
		
		if (community != null){
			//Get Weather Location based on community users
			List<Location> weatherLocations = memberExtendedProfileDao.getWeatherLocationByCommunity(community.getId());
			//For each location check the rules if they qualify
			if (!weatherLocations.isEmpty()){
				
				//Load Drools rule
				initialiseDrools(WEATHER_RULE);
				Date tomorrowDate = DateParserUtil.setDateByDayOffset(new Date(), 1);
				for (Location weatherLocation : weatherLocations){
					
					//if heat alert is true then raise heat weather alerts
					//Get Weather data
					ForecastWeatherData fwd = forecastWeatherDataDao.getForecastWeatherData(weatherLocation.getId(), tomorrowDate);
					
					//If we are missing forecast weather data then skip
					if (fwd == null){
						System.out.println("Forecast is empty :: " + tomorrowDate);
						continue;
					}
					
					System.out.println("Applying the rule now");
					
					sessionObject = rbase.newStatefulSession();
					sessionObject.insert(fwd);
					
					WeatherRuleResult wrr = new WeatherRuleResult();
					sessionObject.setGlobal("result", wrr);
					sessionObject.fireAllRules();

					if (wrr.isHeatAlert()){
						//Get All People in this city
						
						System.out.println("WOO HOO I'm Writing this..!");
						List<User> members = memberExtendedProfileDao.getMembersByLocation(weatherLocation.getId());
						
						Date date = new Date();
						//XXX: For testing purpose we only allow members in targetted segment
						for (User member : members){
							
							//Generate heat alerts!!
							NewsForm nf = new NewsForm();
							nf.setHeading("Heat alert tomorrow");
							nf.setNews("Let's make a difference! Be mindful of your electricity usage and control peak demand.");
							nf.setImageUrl("/images/alert/heat_alert_small.png");
							nf.setMobileImageUrl("/images/alert/heat_alert_small.png");
							nf.setDate(date);
							nf.setTrackerTypeId(3l);
							nf.setReference(weatherLocation.getId() + "");
							try{
								whatsUpService.setAlertNews(member.getId(), nf.getHeading(), nf.getNews(), nf.getImageUrl(), nf.getMobileImageUrl(), nf.getTrackerTypeId(), nf.getReference());
							}catch(Exception e){
								e.printStackTrace();
							}
							
							/*
							List<Segment> segments = segmentService.getMemberSegment(member.getId());
							for (Segment segment: segments){
								if (segment.getCode().equals("Team Zero UAT")){
									System.out.println("Found Team Zero UAT Segments : " + member.getId());
									//Generate heat alerts!!	
									
									
									NewsForm nf = new NewsForm();
									nf.setHeading("Heat alert tomorrow");
									nf.setNews("Let's make a difference! Be mindful of your electricity usage and control peak demand.");
									nf.setImageUrl("/images/alert/heat_alert_small.png");
									nf.setMobileImageUrl("/images/alert/heat_alert_small.png");
									nf.setDate(date);
									nf.setTrackerTypeId(3l);
									nf.setReference(weatherLocation.getId() + "");
									try{
										whatsUpService.setAlertNews(domainName, member, nf);
									}catch(Exception e){
										e.printStackTrace();
									}
									
								}
							}
							
						}
						
						
						
					}else{
						System.out.println("NOT ALERTING BECAUSE WHY>>>>> ");
					}
				}
			}
			
			
		}else{
			rv.fill(AppConstants.FAILURE, "Community not exists.");
		}
		
		
		*/
		return rv;
	}
	
	private static void initialiseDrools(String drlFile) {
		/*
		//1. Read the DRL File and add to package builder
		try {
		Reader reader = new InputStreamReader(WeatherService.class.getResourceAsStream(drlFile));
		pbuilder.addPackageFromDrl(reader);
		} catch (DroolsParserException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		//2. Check for any errors
		PackageBuilderErrors errors = pbuilder.getErrors();

		if (errors.getErrors().length > 0) {
		System.out.println("Some errors exists in packageBuilder");
		for (int i = 0; i < errors.getErrors().length; i++) {
		System.out.println(errors.getErrors()[i]);
		}
		throw new IllegalArgumentException("Could not parse knowledge.");
		}

		//3. Add package to rule base
		try {
		rbase.addPackage(pbuilder.getPackage());
		} catch (Exception e) {
		System.out.println("Error: "+ e);
		}
		*/
	}
}
