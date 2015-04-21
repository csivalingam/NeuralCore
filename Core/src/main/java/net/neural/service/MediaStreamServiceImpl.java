package net.zfp.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Domain;
import net.zfp.entity.Factor;
import net.zfp.entity.Source;
import net.zfp.entity.SourceType;
import net.zfp.entity.Tweet;
import net.zfp.entity.User;
import net.zfp.entity.campaign.CampaignProgress;
import net.zfp.entity.electricity.ElectricityDataDay;
import net.zfp.entity.electricity.ElectricityDataMinute;
import net.zfp.entity.electricity.ElectricityDataMonth;
import net.zfp.entity.media.MediaImage;
import net.zfp.entity.media.MediaScreen;
import net.zfp.entity.media.MediaScreenAttributeValue;
import net.zfp.entity.media.MediaScreenList;
import net.zfp.entity.media.MediaStream;
import net.zfp.entity.personalmotion.PersonalMotion;
import net.zfp.entity.personalmotion.WalkingHour;
import net.zfp.entity.print.PrintDataMinute;
import net.zfp.form.TranslationForm;
import net.zfp.service.MediaStreamService;
import net.zfp.util.AppConstants;
import net.zfp.util.ImageUtil;
import net.zfp.view.GaugeView;
import net.zfp.view.ImageView;
import net.zfp.view.LeaderboardView;
import net.zfp.view.MediaLeaderboardView;
import net.zfp.view.MediaScreenView;
import net.zfp.view.MediaStreamView;
import net.zfp.view.OneByOneView;
import net.zfp.view.ResultView;
import net.zfp.view.TextView;
import net.zfp.view.TwitterView;
import net.zfp.view.UsageView;
import net.zfp.view.UserView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class MediaStreamServiceImpl implements MediaStreamService {

	@Resource
	private EntityDao<Domain>						domainDao;

	@Resource
	private EntityDao<MediaStream>						mediaStreamDao;
	
	@Resource
	private EntityDao<Source>						sourceDao;
	
	@Resource
	private EntityDao<ElectricityDataMinute>						electricityDao;
	
	@Resource
	private EntityDao<Tweet> tweetDao;
	
	@Resource
	private EntityDao<PrintDataMinute> paperDao;
	
	@Resource
	private EntityDao<Factor> factorDao;
	
	@Resource
	private EntityDao<PersonalMotion> personalMotionDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public MediaStreamView getMediaStreamInfo( String serverName, String streamCode ) {
		
		System.out.println("++++++++++++++++++++Media Stream Info++++++++++++++++++++++++++++");
			
		MediaStreamView mediaStreamView = new MediaStreamView();
		List<MediaScreenView> mediaScreenViews = new ArrayList<MediaScreenView>();
		mediaStreamView.setMediaScreenView(mediaScreenViews);
		
		MediaStream stream = getMediaStream(serverName, streamCode);
		
		List<MediaScreenList> screens = null;
		
		mediaStreamView.setName(stream.getName());
		mediaStreamView.setStreamCode(stream.getStreamCode());
		//mediaStreamView.setRotationInternal(stream.getRotationInternal());
		mediaStreamView.setTransitionType(stream.getMediaTransitionType().getType());
		mediaStreamView.setResolutionWidth(stream.getMediaResolutionType().getWidth());
		mediaStreamView.setResolutionHeight(stream.getMediaResolutionType().getHeight());
		mediaStreamView.setStatus(stream.getStatus());

		mediaStreamView.setHeaderName(stream.getHeader().getName());
		mediaStreamView.setHeaderUrl(stream.getHeader().getUrl());
		mediaStreamView.setFooterName(stream.getFooter().getName());
		mediaStreamView.setFooterUrl(stream.getFooter().getUrl());
		
		try{
			screens = mediaStreamDao.findMediaScreenList(stream.getId());
			System.out.println("++++++++++Found "+stream.getName()+" stream have "+screens.size()+" screen(s).+++++++++++++++");
		}catch(NoResultException e){
			mediaStreamView.setResult(new ResultView(AppConstants.MEDIA_STREAM_EMPTY_SCREEN_LIST, "Media Stream have empty screen list."));
			return mediaStreamView;
		}catch(Exception e){
			e.printStackTrace();
			mediaStreamView.setResult(new ResultView(AppConstants.UNKNOWN_ERROR, "Unknown error."));
			return mediaStreamView;
		}
		
//		for (MediaScreen mm : mediaStream.getScreenList()) {
		for ( int i=0 ; i < screens.size() ; i++ ){
			
			MediaScreenList mm = screens.get(i);
			MediaScreenView mediaScreenView = new MediaScreenView();
			
			mediaScreenView.setId(mm.getId());
			mediaScreenView.setName(mm.getMediaScreen().getName());
			mediaScreenView.setUrl(mm.getMediaScreen().getUrl());
			mediaScreenView.setOrder(mm.getScreenOrder());
			mediaScreenView.setRotationInterval(stream.getRotationInterval());
			
			mediaScreenViews.add(mediaScreenView);
			
		}
		
		mediaStreamView.setMediaScreenView(mediaScreenViews);
		
		mediaStreamView.setResult(new ResultView(AppConstants.SUCCESS, "Get Media Stream success"));
		//return mediaStreamDao.findById(1l);
		return mediaStreamView;

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public TextView getTextData(String serverName, String streamCode, Integer screenOrder){

		System.out.println("++++++++++++++++++++Media TEXT DATA++++++++++++++++++++++++++++");
		
		MediaStream stream = getMediaStream(serverName, streamCode);
		
		List<MediaScreenAttributeValue> mediaScreenAttributeValues = getMediaScreenAttributeValue(stream.getId(), AppConstants.MEDIA_SCREEN_TEXTCONTENT, screenOrder);

		String screenHeader = null;
		String screenText = null;
		
		for (MediaScreenAttributeValue value : mediaScreenAttributeValues){
			if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER)){
				screenHeader = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_TEXT)){
				screenText = value.getValue();
			}
		}
		
		TextView textView = new TextView();
		textView.setMasterHeader(stream.getHeader().getUrl());
		textView.setHeader(screenHeader);
		textView.setText(screenText);
		
		return textView;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public MediaLeaderboardView getLeaderboardData(String serverName, String streamCode, Integer screenOrder) {
		System.out.println("++++++++++++++++++++Media Stream Leaderboard View Info++++++++++++++++++++++++++++");
		MediaLeaderboardView leaderboardView = new MediaLeaderboardView();
		
		MediaStream stream = getMediaStream(serverName, streamCode);
		//Set Header
		leaderboardView.setMasterHeader(stream.getHeader().getUrl());
		
		List<MediaScreenAttributeValue> mediaScreenAttributeValues = getMediaScreenAttributeValue(stream.getId(), AppConstants.MEDIA_SCREEN_LEADERBOARD, screenOrder);
		
		List<String> sourceCodes = new ArrayList<String>();
		String screenHeader = null;
		String screenHeaderFontFamily = null;
		String screenHeaderSize = null;
		String screenHeaderColor = null;
		String screenHeaderJustification = null;
		
		String screenSubHeader = null;
		String screenSubHeaderFontFamily = null;
		String screenSubHeaderSize = null;
		String screenSubHeaderColor = null;
		String screenSubHeaderJustification = null;
		Long data_period = null;
		boolean displayUnitBoolean = false;
		boolean includingWeekends = false;
		boolean includingLowPerformers = false;
		boolean includingBoundary = false;
		Double boundaryPercentage = 0.0;
		String comparisonType = null; // YEAR_OVER_YEAR || PERIOD_OVER_PERIOD
		String periodHistory = null;  // DAILY || MONTHLY
		String comparisonMode = null; // abs || per
		Long sourceType = null; 
		Integer maxSources = 0;
		
		for (MediaScreenAttributeValue value : mediaScreenAttributeValues){
			System.out.println(value.getMediaScreenAttribute().getAttribute());
			if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SOURCE_CODE)){
				if (value.getValue() != null && !value.getValue().equals("")) sourceCodes.add(value.getValue());
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SOURCE_TYPE)){
				if (value.getValue() != null && !value.getValue().equals("")) sourceType = Long.parseLong(value.getValue());
			}
			else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER)){
				if (value.getValue() != null && !value.getValue().equals("")) screenHeader = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER_FONT_FAMILY)){
				if (value.getValue() != null && !value.getValue().equals("")) screenHeaderFontFamily = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER_FONT_COLOR)){
				if (value.getValue() != null && !value.getValue().equals("")) screenHeaderColor = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER_FONT_SIZE)){
				if (value.getValue() != null && !value.getValue().equals("")) screenHeaderSize = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER_JUSTIFICATION)){
				if (value.getValue() != null && !value.getValue().equals("")) screenHeaderJustification = value.getValue();
			}
			
			
			else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_SUBHEADER)){
				if (value.getValue() != null && !value.getValue().equals("")) screenSubHeader = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_SUBHEADER_FONT_FAMILY)){
				if (value.getValue() != null && !value.getValue().equals("")) screenSubHeaderFontFamily = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_SUBHEADER_FONT_SIZE)){
				if (value.getValue() != null && !value.getValue().equals("")) screenSubHeaderSize = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_SUBHEADER_FONT_COLOR)){
				if (value.getValue() != null && !value.getValue().equals("")) screenSubHeaderColor = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_SUBHEADER_JUSTIFICATION)){
				if (value.getValue() != null && !value.getValue().equals("")) screenSubHeaderJustification = value.getValue();
			}
			else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_COMPARISON_TYPE)){
				if (value.getValue() != null && !value.getValue().equals("")) comparisonType = value.getValue().toUpperCase();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_BOUNDARY_PERCENTAGE)){
				if (value.getValue() != null && !value.getValue().equals("")) boundaryPercentage = Double.parseDouble(value.getValue());
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_PERIOD_HISTORY)){
				if (value.getValue() != null && !value.getValue().equals("")) periodHistory = value.getValue().toUpperCase();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_COMPARISON_MODE)){
				if (value.getValue() != null && !value.getValue().equals("")) comparisonMode = value.getValue();
			}
			
			else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_DATA_HISTORY)){
				if (value.getValue() != null && !value.getValue().equals("")) data_period = Long.parseLong(value.getValue());
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_COMPARISON_MODE)){
				if (value.getValue() != null && !value.getValue().equals("")) comparisonMode = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_TOP_NUMBER)){
				if (value.getValue() != null && !value.getValue().equals("")) maxSources = Integer.parseInt(value.getValue());
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_DISPLAY_UNITS_BOOELAN)){
				if (value.getValue() != null && !value.getValue().equals("")){
					if (value.getValue().toString().toLowerCase().equals("true")){
						displayUnitBoolean = true;
					}else if (value.getValue().toString().toLowerCase().equals("false")){
						displayUnitBoolean = false;
					}
				}
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_BOUNDARY_BOOLEAN)){
				if (value.getValue() != null && !value.getValue().equals("")){
					if (value.getValue().toString().toLowerCase().equals("true")){
						includingBoundary = true;
					}else if (value.getValue().toString().toLowerCase().equals("false")){
						includingBoundary = false;
					}
				}
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_INCLUDE_WEEKENDS)){
				if (value.getValue() != null && !value.getValue().equals("")){
					if (value.getValue().toString().toLowerCase().equals("true")){
						includingWeekends = true;
					}else if (value.getValue().toString().toLowerCase().equals("false")){
						includingWeekends = false;
					}
				}
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_INCLUDE_LOW_PERFORMERS)){
				if (value.getValue() != null && !value.getValue().equals("")){
					if (value.getValue().toString().toLowerCase().equals("true")){
						includingLowPerformers = true;
					}else if (value.getValue().toString().toLowerCase().equals("false")){
						includingLowPerformers = false;
					}
				}
			}
		}
		
		leaderboardView.setHeader(filterString(screenHeader,true));
		leaderboardView.setHeaderFontFamily(screenHeaderFontFamily);
		leaderboardView.setHeaderColor(screenHeaderColor);
		leaderboardView.setHeaderSize(screenHeaderSize);
		leaderboardView.setHeaderJustification(screenHeaderJustification);
		
		leaderboardView.setSubHeader(filterString(screenSubHeader,true ));
		leaderboardView.setSubHeaderFontFamily(screenSubHeaderFontFamily);
		leaderboardView.setSubHeaderSize(screenSubHeaderSize);
		leaderboardView.setSubHeaderColor(screenSubHeaderColor);
		leaderboardView.setSubHeaderJustification(screenSubHeaderJustification);
		
		leaderboardView.setDisplayUnitBoolean(displayUnitBoolean);
		
		SourceType st = sourceDao.getSourceTypeById(sourceType);
		
		if (sourceCodes.size() > 0 && st != null){
			
			if (st.getCode().equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY)){
				leaderboardView.setLeaderboardViews(leaderboardElectricityData(sourceCodes, data_period, includingWeekends, maxSources, comparisonMode, includingLowPerformers, includingBoundary, boundaryPercentage, comparisonType, periodHistory));
			}else if (st.getCode().equals(AppConstants.SOURCETYPE_CODE_WALKING)){
				//leaderboardView.setLeaderboardViews(leaderboardWalkingData(sourceCodes, (int)(long)data_period, includingWeekends, maxSources, comparisonMode));
			}else if (st.getCode().equals(AppConstants.SOURCETYPE_CODE_DRIVING)){
				
			}
		}
		
		return leaderboardView;
	}
	
	private List<LeaderboardView> leaderboardElectricityData(List<String> sourceCodes, long dataPeriod, boolean includingWeekends, Integer maxSources, String comparisonMode, boolean includingLowPerformers, boolean includingBoundary, Double boundaryPercentage, String comparisonType, String periodHistory){
		List<LeaderboardView> leaderboardViews = new ArrayList<LeaderboardView>();
		Integer greenZone =0, yellowzone =0;
		if (sourceCodes.size() == 1){
			greenZone = 0;
		}else if (sourceCodes.size() == 2){
			greenZone = 0;
			yellowzone = 1;
		}else{
			greenZone = sourceCodes.size()/3;
			yellowzone = 2 * sourceCodes.size()/3;
		}
		for (String s : sourceCodes){
			LeaderboardView leaderboardView = new LeaderboardView();
			Source source = sourceDao.findSourceBySourceCode(s);
			System.out.println(s);
			if (source != null){
				if (source.getSupressDisplay()) continue;
				
				leaderboardView.setSourceId(source.getId());
				
				if (!source.getSupressName()) leaderboardView.setSourceName(source.getName());
				
				Double currentConsumption = 0.0;
				Double previousConsumption = 0.0;
				List<Source> meterSources = getMeterSources(source);
				if (periodHistory == null || periodHistory.equals("DAILY")){
					if (comparisonType == null || comparisonType.equals("PERIOD_OVER_PERIOD")){
						List<ElectricityDataDay> currentDays = null;
						try{
							currentDays = getDailyLeaderboardData(meterSources, (int)dataPeriod, new Date(), includingWeekends);
						}catch(Exception e1){
							e1.printStackTrace();
						}
						//Get ElectricityDays 
						if ( currentDays != null && currentDays.size() > 0){
							Date previousEndDate = currentDays.get(currentDays.size()-1).getCreated();
							System.out.println(previousEndDate);
							List<ElectricityDataDay> previousDays = null;
							try{
								previousDays = getDailyLeaderboardData(meterSources, (int)dataPeriod, previousEndDate, includingWeekends);
							}catch(Exception e2){
								e2.printStackTrace();
							}
							 
							if (previousDays != null && previousDays.size() > 0){
								//Let's calculate consumption of currentDays and previousDays
								for (ElectricityDataDay edd : currentDays){
									currentConsumption += (edd.getMidPeakConsumption()+edd.getOffPeakConsumption()+edd.getOnPeakConsumption());
								}
								for (ElectricityDataDay edd : previousDays){
									previousConsumption += (edd.getMidPeakConsumption()+edd.getOffPeakConsumption()+edd.getOnPeakConsumption());
								}
								
								currentConsumption /= 1000;
								previousConsumption /= 1000;
								
								if (comparisonMode.equals("per")){
									leaderboardView.setValue(((previousConsumption - currentConsumption) / previousConsumption)*100);
								}else if (comparisonMode.equals("abs")){
									leaderboardView.setValue(previousConsumption - currentConsumption);
								}
								
							}else{
								leaderboardView.setValue(0.0);
							}
						}else{
							leaderboardView.setValue(0.0);
						}
					}else if (comparisonType.equals("YEAR_OVER_YEAR")){
						//Get Calendar... get last year
						Calendar currentPeriod = new GregorianCalendar();
						currentPeriod.setTime(new Date());
						currentPeriod.set(Calendar.MONTH, currentPeriod.get(Calendar.MONTH)-1);
						List<ElectricityDataDay> currentDays = null;
						try{
							currentDays = getDailyLeaderboardData(meterSources, (int)dataPeriod, currentPeriod.getTime(), includingWeekends);
						}catch(Exception e1){
							e1.printStackTrace();
						}
						//Get ElectricityDays 
						if ( currentDays != null && currentDays.size() > 0){
							Date previousEndDate = currentDays.get(currentDays.size()-1).getCreated();
							System.out.println(previousEndDate);
							List<ElectricityDataDay> previousDays = null;
							Calendar previousPeriod = new GregorianCalendar();
							previousPeriod.setTime(new Date());
							previousPeriod.set(Calendar.YEAR, previousPeriod.get(Calendar.YEAR)-1);
							previousPeriod.set(Calendar.MONTH, currentPeriod.get(Calendar.MONTH)-1);
							
							try{
								previousDays = getDailyLeaderboardData(meterSources, (int)dataPeriod, previousPeriod.getTime(), includingWeekends);
							}catch(Exception e2){
								e2.printStackTrace();
							}
							 
							if (previousDays != null && previousDays.size() > 0){
								//Let's calculate consumption of currentDays and previousDays
								for (ElectricityDataDay edd : currentDays){
									currentConsumption += (edd.getMidPeakConsumption()+edd.getOffPeakConsumption()+edd.getOnPeakConsumption());
								}
								for (ElectricityDataDay edd : previousDays){
									previousConsumption += (edd.getMidPeakConsumption()+edd.getOffPeakConsumption()+edd.getOnPeakConsumption());
								}
								
								currentConsumption /= 1000;
								previousConsumption /= 1000;
								
								if (comparisonMode.equals("per")){
									leaderboardView.setValue(((previousConsumption - currentConsumption) / previousConsumption)*100);
								}else if (comparisonMode.equals("abs")){
									leaderboardView.setValue(previousConsumption - currentConsumption);
								}
								
							}else{
								leaderboardView.setValue(0.0);
							}
						}else{
							leaderboardView.setValue(0.0);
						}
					}
				}else if (periodHistory.equals("MONTHLY")){
					if (comparisonType == null || comparisonType.equals("PERIOD_OVER_PERIOD")){
						List<ElectricityDataMonth> currentMonths = null;
						try{
							currentMonths = getMonthlyLeaderboardData(meterSources, (int)dataPeriod, new Date());
						}catch(Exception e1){
							e1.printStackTrace();
						}
						if (currentMonths != null && currentMonths.size() > 0){
							Date previousEndDate = new Date(currentMonths.get(currentMonths.size()-1).getCreated().getTime() -1l);
							List<ElectricityDataMonth> previousMonths = null;
							try{
								previousMonths = getMonthlyLeaderboardData(meterSources, (int)dataPeriod, previousEndDate);
							}catch(Exception e2){
								e2.printStackTrace();
							}
							 
							if (previousMonths != null && previousMonths.size() > 0){
								//Let's calculate consumption of currentDays and previousDays
								for (ElectricityDataMonth edd : currentMonths){
									currentConsumption += (edd.getMidPeakConsumption()+edd.getOffPeakConsumption()+edd.getOnPeakConsumption());
								}
								for (ElectricityDataMonth edd : previousMonths){
									previousConsumption += (edd.getMidPeakConsumption()+edd.getOffPeakConsumption()+edd.getOnPeakConsumption());
								}
								
								currentConsumption /= 1000;
								previousConsumption /= 1000;
								
								if (comparisonMode.equals("per")){
									leaderboardView.setValue(((previousConsumption - currentConsumption) / previousConsumption)*100);
								}else if (comparisonMode.equals("abs")){
									leaderboardView.setValue(previousConsumption - currentConsumption);
								}
								
							}else{
								leaderboardView.setValue(0.0);
							}
						}else{
							leaderboardView.setValue(0.0);
						}
					}else if (comparisonType.equals("YEAR_OVER_YEAR")){
						Calendar currentPeriod = new GregorianCalendar();
						currentPeriod.setTime(new Date());
						currentPeriod.set(Calendar.MONTH, currentPeriod.get(Calendar.MONTH)-1);
						List<ElectricityDataMonth> currentMonths = null;
						try{
							currentMonths = getMonthlyLeaderboardData(meterSources, (int)dataPeriod, currentPeriod.getTime());
						}catch(Exception e1){
							e1.printStackTrace();
						}
						if (currentMonths != null && currentMonths.size() > 0){
							Calendar previousPeriod = new GregorianCalendar();
							previousPeriod.setTime(new Date());
							previousPeriod.set(Calendar.YEAR, previousPeriod.get(Calendar.YEAR)-1);
							previousPeriod.set(Calendar.MONTH, currentPeriod.get(Calendar.MONTH)-1);
							List<ElectricityDataMonth> previousMonths = null;
							try{
								previousMonths = getMonthlyLeaderboardData(meterSources, (int)dataPeriod, previousPeriod.getTime());
							}catch(Exception e2){
								e2.printStackTrace();
							}
							if (previousMonths != null && previousMonths.size() > 0){
								//Let's calculate consumption of currentDays and previousDays
								for (ElectricityDataMonth edd : currentMonths){
									currentConsumption += (edd.getMidPeakConsumption()+edd.getOffPeakConsumption()+edd.getOnPeakConsumption());
								}
								for (ElectricityDataMonth edd : previousMonths){
									previousConsumption += (edd.getMidPeakConsumption()+edd.getOffPeakConsumption()+edd.getOnPeakConsumption());
								}
								
								currentConsumption /= 1000;
								previousConsumption /= 1000;
								
								if (comparisonMode.equals("per")){
									leaderboardView.setValue(((previousConsumption - currentConsumption) / previousConsumption)*100);
								}else if (comparisonMode.equals("abs")){
									leaderboardView.setValue(previousConsumption - currentConsumption);
								}
								
							}else{
								leaderboardView.setValue(0.0);
							}
						}else{
							leaderboardView.setValue(0.0);
						}
					}
				}
				
				if (includingBoundary){
					Double absValue = leaderboardView.getValue() < 0 ? leaderboardView.getValue()*-1 : leaderboardView.getValue();
					if (absValue <= boundaryPercentage){
						leaderboardViews.add(leaderboardView);
					}
				}else{
					leaderboardViews.add(leaderboardView);
				}
			}
		}
		
		
		System.out.println("Leaderboard View Size " + leaderboardViews.size());
		//sort leaderboard view to highest value
		Comparator<LeaderboardView> comparator = new Comparator<LeaderboardView>(){
			public int compare(LeaderboardView lv1, LeaderboardView lv2){
				if (lv1.getValue() < lv2.getValue()) return 1;
				else if (lv1.getValue() > lv2.getValue()) return -1;
				else return 0;
			}
		};
		Collections.sort(leaderboardViews, comparator);
		
		List<LeaderboardView> orderedLvs = new ArrayList<LeaderboardView>(); 
		int maxSize = leaderboardViews.size() > maxSources ? maxSources : leaderboardViews.size();
		for (int i = 0; i<maxSize; i++){
			LeaderboardView leaderboardView = leaderboardViews.get(i);
			
			//Each source get Id and name and value
			if (i <= greenZone){
				leaderboardView.setRank(1);
			}else if (i <= yellowzone){
				leaderboardView.setRank(2);
				if (!includingLowPerformers) leaderboardView.setSourceName("");
			}else{
				leaderboardView.setRank(3);
				if (!includingLowPerformers) leaderboardView.setSourceName("");
			}
			
			leaderboardView.setRanking(i+1);
			
			orderedLvs.add(leaderboardView);
			
		}
		return orderedLvs;
	}
	
	private String filterString(String words, boolean isLeaderboard){
		if (words == null) return null;
		SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMMM");
		SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
		
		if (isLeaderboard){
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) -1);
			words = words.replaceAll("<MONTH>", monthFormatter.format(calendar.getTime()));
			words = words.replaceAll("<MONTH_CAPITAL>", monthFormatter.format(calendar.getTime()).toString().toUpperCase());
			words = words.replaceAll("<YEAR>", yearFormatter.format(calendar.getTime()));
		}else{
			words = words.replaceAll("<MONTH>", monthFormatter.format(new Date()));
			words = words.replaceAll("<MONTH_CAPITAL>", monthFormatter.format(new Date()).toString().toUpperCase());
			words = words.replaceAll("<YEAR>", yearFormatter.format(new Date()));
		}
		return words;
	}
	private List<LeaderboardView> leaderboardWalkingData(List<String> sourceCodes, int dataPeriod, boolean includingWeekends, Integer maxSources, String comparisonMode){
		List<LeaderboardView> leaderboardViews = new ArrayList<LeaderboardView>();
		Integer greenZone =0, yellowzone =0;
		if (sourceCodes.size() == 1){
			greenZone = 0;
		}else if (sourceCodes.size() == 2){
			greenZone = 0;
			yellowzone = 1;
		}else{
			greenZone = sourceCodes.size()/3;
			yellowzone = 2 * sourceCodes.size()/3;
		}
		
		for (String s : sourceCodes){
			LeaderboardView leaderboardView = new LeaderboardView();
			Source source = sourceDao.findSourceBySourceCode(s);
			leaderboardView.setSourceId(source.getId());
			leaderboardView.setSourceName(source.getName());
			
			Double currentSteps = 0.0;
			Double previousSteps = 0.0;
			
			Date today = new Date();
			
			List<WalkingHour> currentDays = new ArrayList<WalkingHour>();
			while(currentDays.size() < dataPeriod){
				//Check if today is weekend
				if (!includingWeekends){
					if (isWeekend(today)){
						today = new Date(today.getTime()-24 * 60 * 60 * 1000l);
						continue;
					}
				}
				List<WalkingHour> walkings = personalMotionDao.getWalkingBySource(source.getId(), today);
				
				if (walkings != null && walkings.size() > 0){
					WalkingHour newWalking = new WalkingHour();
					int totalSteps = 0;
					double totalDistance = 0.0;
					
					for (WalkingHour w : walkings){
						totalSteps += w.getSteps();
						totalDistance += w.getDistance();
						
					}
					newWalking.setSource(walkings.get(0).getSource());
					newWalking.setSteps(totalSteps);
					newWalking.setDistance(totalDistance);
					currentDays.add(newWalking);
				}
				
				//Check if there is data before today
				List<WalkingHour> checkWalkings = personalMotionDao.checkWalkingBySource(source.getId(), getBeginningOfDay(today));
				if (checkWalkings == null || checkWalkings.size() == 0){
					break;
				}else{
					today = new Date(today.getTime()-24 * 60 * 60 * 1000l);
				}
			}
			
			//Get ElectricityDays 
			if ( currentDays != null && currentDays.size() > 0){
				Date previousEndDate = currentDays.get(currentDays.size()-1).getStartTime();
				System.out.println(previousEndDate);
				List<WalkingHour> previousDays = new ArrayList<WalkingHour>();
				while(previousDays.size() < dataPeriod){
					//Check if today is weekend
					if (!includingWeekends){
						if (isWeekend(previousEndDate)){
							previousEndDate = new Date(today.getTime()-24 * 60 * 60 * 1000l);
							continue;
						}
					}
					List<WalkingHour> walkings = personalMotionDao.getWalkingBySource(source.getId(), previousEndDate);
					
					if (walkings != null && walkings.size() > 0){
						WalkingHour newWalking = new WalkingHour();
						int totalSteps = 0;
						double totalDistance = 0.0;
						
						for (WalkingHour w : walkings){
							totalSteps += w.getSteps();
							totalDistance += w.getDistance();
							
						}
						newWalking.setSource(walkings.get(0).getSource());
						newWalking.setSteps(totalSteps);
						newWalking.setDistance(totalDistance);
						previousDays.add(newWalking);
					}
					
					//Check if there is data before today
					List<WalkingHour> checkWalkings = personalMotionDao.checkWalkingBySource(source.getId(), getBeginningOfDay(previousEndDate));
					if (checkWalkings == null || checkWalkings.size() == 0){
						break;
					}else{
						previousEndDate = new Date(previousEndDate.getTime()-24 * 60 * 60 * 1000l);
					}
				}
				
				if (previousDays != null && previousDays.size() > 0){
					//Let's calculate consumption of currentDays and previousDays
					for (WalkingHour w : currentDays){
						currentSteps += w.getSteps();
					}
					for (WalkingHour w : previousDays){
						previousSteps += w.getSteps();
					}
					
					if (comparisonMode.equals("per")){
						leaderboardView.setValue((previousSteps - currentSteps) / previousSteps);
					}else if (comparisonMode.equals("abs")){
						leaderboardView.setValue(previousSteps - currentSteps);
					}
					
				}else{
					leaderboardView.setValue(0.0);
				}
			}else{
				leaderboardView.setValue(0.0);
			}
			
			leaderboardViews.add(leaderboardView);
		}
		
		
		System.out.println("Leaderboard View Size " + leaderboardViews.size());
		//sort leaderboard view to highest value
		Comparator<LeaderboardView> comparator = new Comparator<LeaderboardView>(){
			public int compare(LeaderboardView lv1, LeaderboardView lv2){
				if (lv1.getValue() < lv2.getValue()) return 1;
				else if (lv1.getValue() > lv2.getValue()) return -1;
				else return 0;
			}
		};
		Collections.sort(leaderboardViews, comparator);
		
		List<LeaderboardView> orderedLvs = new ArrayList<LeaderboardView>(); 
		for (int i = 0; i<leaderboardViews.size(); i++){
			LeaderboardView leaderboardView = leaderboardViews.get(i);
			
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
			
		}
		return orderedLvs;
	}
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public GaugeView getGaugeData(String serverName, String streamCode, Integer screenOrder) {
		System.out.println("++++++++++++++++++++Media Stream Gauge Info++++++++++++++++++++++++++++");
		GaugeView gaugeView = new GaugeView();
		
		MediaStream stream = getMediaStream(serverName, streamCode);
		//Set Header
		gaugeView.setMasterHeader(stream.getHeader().getUrl());
		
		List<MediaScreenAttributeValue> mediaScreenAttributeValues = getMediaScreenAttributeValue(stream.getId(), AppConstants.MEDIA_SCREEN_GAUGE, screenOrder);
		
		String screenHeader = null;
		String screenHeaderFontFamily = null;
		String screenHeaderSize = null;
		String screenHeaderColor = null;
		String screenHeaderJustification = null;
		
		String screenSubHeader = null;
		String screenSubHeaderFontFamily = null;
		String screenSubHeaderSize = null;
		String screenSubHeaderColor = null;
		String screenSubHeaderJustification = null;
		Long data_period = null;
		boolean displayUnitBoolean = false;
		boolean includingWeekends = false;
		String periodHistory = null;
		String sourceCode = null;
		String displayUnit = null;
		Integer	sourceType = 0; 
		
		
		for (MediaScreenAttributeValue value : mediaScreenAttributeValues){
			System.out.println(value.getMediaScreenAttribute().getAttribute());
			if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SOURCE_CODE)){
				if (value.getValue() != null && !value.getValue().equals("")) sourceCode = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SOURCE_TYPE)){
				if (value.getValue() != null && !value.getValue().equals("")) sourceType = Integer.parseInt(value.getValue());
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER)){
				if (value.getValue() != null && !value.getValue().equals("")) screenHeader = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER_FONT_FAMILY)){
				if (value.getValue() != null && !value.getValue().equals("")) screenHeaderFontFamily = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER_FONT_COLOR)){
				if (value.getValue() != null && !value.getValue().equals("")) screenHeaderColor = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER_FONT_SIZE)){
				if (value.getValue() != null && !value.getValue().equals("")) screenHeaderSize = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER_JUSTIFICATION)){
				if (value.getValue() != null && !value.getValue().equals("")) screenHeaderJustification = value.getValue();
			}
			
			
			else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_SUBHEADER)){
				if (value.getValue() != null && !value.getValue().equals("")) screenSubHeader = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_SUBHEADER_FONT_FAMILY)){
				if (value.getValue() != null && !value.getValue().equals("")) screenSubHeaderFontFamily = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_SUBHEADER_FONT_SIZE)){
				if (value.getValue() != null && !value.getValue().equals("")) screenSubHeaderSize = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_SUBHEADER_FONT_COLOR)){
				if (value.getValue() != null && !value.getValue().equals("")) screenSubHeaderColor = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_SUBHEADER_JUSTIFICATION)){
				if (value.getValue() != null && !value.getValue().equals("")) screenSubHeaderJustification = value.getValue();
			}
			
			else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_DISPLAY_UNITS)){
				if (value.getValue() != null && !value.getValue().equals("")) displayUnit = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_PERIOD_HISTORY)){
				if (value.getValue() != null && !value.getValue().equals("")) periodHistory = value.getValue().toUpperCase();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_DATA_HISTORY)){
				if (value.getValue() != null && !value.getValue().equals("")) data_period = Long.parseLong(value.getValue());
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_DISPLAY_UNITS_BOOELAN)){
				System.out.println(AppConstants.MEDIA_ATTRIBUTE_DISPLAY_UNITS_BOOELAN);
				System.out.println(value.getValue());
				if (value.getValue() != null && !value.getValue().equals("")){
					if (value.getValue().toString().toLowerCase().equals("true")){
						displayUnitBoolean = true;
					}else if (value.getValue().toString().toLowerCase().equals("false")){
						displayUnitBoolean = false;
					}
				}
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_INCLUDE_WEEKENDS)){
				if (value.getValue() != null && !value.getValue().equals("")){
					if (value.getValue().toString().toLowerCase().equals("true")){
						includingWeekends = true;
					}else if (value.getValue().toString().toLowerCase().equals("false")){
						includingWeekends = false;
					}
				}
			}
		}
		
		Double[] zoneData = new Double[4];
		
		System.out.println("ASD-------------------- " + sourceCode);
		
		//From sourceCode to get source id
		if (sourceCode != null){
			Source source = sourceDao.findSourceBySourceCode(sourceCode);
			Long sourceId = null;
			if (source != null ) sourceId = source.getId();
			System.out.println("############# source ID from MediaStreamServiceImpl :: " + sourceId);
			
			
			//Get List of ElectricityDataMinute		
			if (data_period != null){
				List<Source> meterSources = getMeterSources(source);
				if (periodHistory == null || periodHistory.equals("DAILY")){
					//Get Last Date which is now
					
					List<ElectricityDataDay> edds = getDailyGaugeData(meterSources, (int)(long)data_period, includingWeekends);
					
					List<Double> energyList = new ArrayList<Double>();
					List<Double> powerList = new ArrayList<Double>();
					Double totalEnergy = 0.0;
					Double totalPower = 0.0;
					if (edds != null && edds.size() > 0){
						for (ElectricityDataDay edm : edds){
							Double energy = edm.getEnergy();
							Double power = edm.getPower();
							totalEnergy += energy;
							totalPower += power;
							energyList.add(energy);
							powerList.add(power);
						}
						
						System.out.println("power" + totalPower + " :: totalEnergy " + totalEnergy + " current Needle :: " + edds.get(0).getPower() + " Size " + edds.size());
							if (displayUnit == null || displayUnit.toUpperCase().equals("KW")){
								gaugeView.setValue(edds.get(0).getPower());
								zoneData = getGaugeZone(powerList, totalPower/edds.size());
							}else if (displayUnit.toUpperCase().equals("KWH")){
								gaugeView.setValue(edds.get(0).getEnergy());
								zoneData = getGaugeZone(energyList, totalEnergy/edds.size());
							}
					}else{
						gaugeView.setValue(0.0);
						zoneData[0] = 1.0;
						zoneData[1] = 2.0;
						zoneData[2] = 3.0;
						zoneData[3] = 4.0;
					}
				}else if (periodHistory.equals("MONTHLY")){
					//Get latest monthly data
					List<ElectricityDataMonth> edms = getMonthlyGaugeData(meterSources, (int)(long)data_period);
					List<Double> energyList = new ArrayList<Double>();
					List<Double> powerList = new ArrayList<Double>();
					Double totalEnergy = 0.0;
					Double totalPower = 0.0;
					if (edms != null){
						for (ElectricityDataMonth edm : edms){
							Double energy = edm.getEnergy();
							Double power = edm.getPower();
							totalEnergy += energy;
							totalPower += power;
							energyList.add(energy);
							powerList.add(power);
						}
						if (edms.size() > 0){
							if (displayUnit == null || displayUnit.toUpperCase().equals("KW")){
								gaugeView.setValue(edms.get(0).getPower());
								zoneData = getGaugeZone(powerList, totalPower/edms.size());
							}else if (displayUnit.toUpperCase().equals("KWH")){
								gaugeView.setValue(edms.get(0).getEnergy());
								zoneData = getGaugeZone(energyList, totalEnergy/edms.size());
							}
						}
					}
				}
				
				
				
			}else{
				System.out.println("************ Get error in MediaStreamServiceImpl for Gauge. Error: NO VALUES IN DATA_HISTORY");
				gaugeView.setResult(new ResultView(AppConstants.UNKNOWN_ERROR, "Error: NO VALUES IN DATA_HISTORY: "));
			}
			
		}else{
			
			gaugeView.setValue(0.0);
			zoneData[0] = 1.0;
			zoneData[1] = 2.0;
			zoneData[2] = 3.0;
			zoneData[3] = 4.0;
			
		}
		
		gaugeView.setHeader(filterString(screenHeader,false));
		gaugeView.setHeaderFontFamily(screenHeaderFontFamily);
		gaugeView.setHeaderColor(screenHeaderColor);
		gaugeView.setHeaderSize(screenHeaderSize);
		gaugeView.setHeaderJustification(screenHeaderJustification);
		
		gaugeView.setSubHeader(filterString(screenSubHeader,false));
		gaugeView.setSubHeaderFontFamily(screenSubHeaderFontFamily);
		gaugeView.setSubHeaderSize(screenSubHeaderSize);
		gaugeView.setSubHeaderColor(screenSubHeaderColor);
		gaugeView.setSubHeaderJustification(screenSubHeaderJustification);
		
		gaugeView.setDisplayUnitBoolean(displayUnitBoolean);
		gaugeView.setUnit(displayUnit);
		gaugeView.setZoneData(zoneData);
		
		
		return gaugeView;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Tweet getLastTweet(String serverName) {
		Long communityId = getCommunityId(serverName);
		List<Tweet> tweets = tweetDao.findTweet(communityId);
		
		return tweets.size()>0 ? tweets.get(0) : null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public TwitterView getTweetAccount(String serverName, String streamCode, Integer screenOrder) {
		
		MediaStream stream = getMediaStream(serverName, streamCode);
		
		List<MediaScreenAttributeValue> mediaScreenAttributeValues = getMediaScreenAttributeValue(stream.getId(), AppConstants.MEDIA_SCREEN_TWITTER, screenOrder);
		
		String twitterQuery = null;
		String twitterWidgetId = null;
		String screenHeader = null;
		String screenImage = null;
		String screenText = null;
		
		for (MediaScreenAttributeValue value : mediaScreenAttributeValues){
			if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_TWITTER_QUERY)){
				twitterQuery = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_TWITTER_WIDGET)){
				twitterWidgetId = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER)){
				screenHeader = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_IMAGE)){
				screenImage = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_TEXT)){
				screenText = value.getValue();
			}
		}
		
		TwitterView twitterView = new TwitterView();
		twitterView.setMasterHeader(stream.getHeader().getUrl());
		twitterView.setHeader(screenHeader);
		twitterView.setImage(screenImage);
		twitterView.setQuery(twitterQuery);
		twitterView.setText(screenText);
		twitterView.setWidgetId(twitterWidgetId);
		
		return twitterView;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public UsageView getTotalConsumption(String serverName, String streamCode, Integer screenOrder, String locale) {
		MediaStream stream = getMediaStream(serverName, streamCode);
		
		Long communityId = getCommunityId(serverName);
		
		List<MediaScreenAttributeValue> mediaScreenAttributeValues = getMediaScreenAttributeValue(stream.getId(), AppConstants.MEDIA_SCREEN_USAGE, screenOrder);
		UsageView usageView = new UsageView();
		
		usageView.setMasterHeader(stream.getHeader().getUrl());
		
		String sourceCode = null;
		String screenHeader = "";
		String equivalencyHeader ="";
		String displayUnit = "";
		Date startDate = null;
		Integer	sourceType = 0; 
		
		for (MediaScreenAttributeValue value : mediaScreenAttributeValues){
			if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SOURCE_CODE)){
				if (value.getValue() != null && !value.getValue().equals("")) sourceCode = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SOURCE_TYPE)){
				if (value.getValue() != null && !value.getValue().equals("")) sourceType = Integer.parseInt(value.getValue());
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER)){
				if (value.getValue() != null && !value.getValue().equals("")) screenHeader = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_EQUIVALENCY_HEADER)){
				if (value.getValue() != null && !value.getValue().equals("")) equivalencyHeader = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_DISPLAY_UNITS)){
				if (value.getValue() != null && !value.getValue().equals("")) displayUnit = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_USAGE_START_DATE)){
				if (value.getValue() != null && !value.getValue().equals("")){
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
					try {
						startDate = df.parse(value.getValue());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						System.out.println("String cannot be parsed to Date ");
						e.printStackTrace();
					}
				}
			}
		}
		
		if (sourceCode != null){
			//From sourceCode to get source id
			Source source = sourceDao.findSourceBySourceCode(sourceCode);
			Long sourceId = null;
			if (source != null) sourceId = source.getId();
			System.out.println("############# source ID from MediaStreamServiceImpl :: " + sourceId);
			
			Date endDate = new Date();
			
			
			switch(sourceType){
			case 1:
				break;
			case 5:
				
				Integer datas = paperDao.findPrintDataSum(sourceId, startDate, endDate);
				usageView.setUsage(datas);
				
				//List of Media Images by giving sourceTypeId and imageType Id and CommunityId, locale
				List<MediaImage> mediaImages = paperDao.findMediaImages(communityId, 5l, 8l, locale);
				List<ImageView> imageViews = new ArrayList<ImageView>();
				//Get List of factors using sourceTypeId as a parameter
				for (MediaImage mediaImage : mediaImages){
					ImageView imageView = new ImageView();
					imageView.setName(mediaImage.getFactorAttribute().getName());
					imageView.setImageURL(ImageUtil.parseImageUrl(mediaImage.getUrl()));
					//using sourceTypeId and Factor Attribute ID, go to factor table fetch factor
					Factor factor = factorDao.findFactor(5l, mediaImage.getFactorAttribute().getId());
					
					if (factor != null){
						System.out.println("--------------------------------------------------------------------" +factor.getValue());
						Double value = factor.getValue() * datas;
						System.out.println("--------------------------------------------------------------------" +value.toString());
						imageView.setImageValue(value.toString());
					}
					
					imageViews.add(imageView);
				}
				
				usageView.setImageViews(imageViews);
				
				break;
			default:
				break;
			}
			
			usageView.setEquivalencyHeader(equivalencyHeader);
			usageView.setHeader(screenHeader);
			usageView.setDisplayUnit(displayUnit);
		}else{
			usageView.setUsage(0);
			usageView.setImageViews(null);
			usageView.setHeader(screenHeader);
			usageView.setDisplayUnit(displayUnit);
			usageView.setEquivalencyHeader(equivalencyHeader);
		}
		
		
		return usageView;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public OneByOneView getOneByOneData(String serverName, String streamCode, Integer screenOrder) {
		MediaStream stream = getMediaStream(serverName, streamCode);
		
		List<MediaScreenAttributeValue> mediaScreenAttributeValues = getMediaScreenAttributeValue(stream.getId(), AppConstants.MEDIA_SCREEN_ONEBYONE, screenOrder);

		String screenHeader = null;
		String screenImage = null;
		String screenText = null;
		
		for (MediaScreenAttributeValue value : mediaScreenAttributeValues){
			if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_HEADER)){
				screenHeader = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_IMAGE)){
				screenImage = value.getValue();
			}else if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_TEXT)){
				screenText = value.getValue();
			}
		}
		
		OneByOneView oneByOneView = new OneByOneView();
		oneByOneView.setMasterHeader(stream.getHeader().getUrl());
		oneByOneView.setHeader(screenHeader);
		oneByOneView.setImage(screenImage);
		oneByOneView.setText(screenText);
		
		return oneByOneView;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ImageView getImageData(String serverName, String streamCode, Integer screenOrder) {
		MediaStream stream = getMediaStream(serverName, streamCode);
		
		List<MediaScreenAttributeValue> mediaScreenAttributeValues = getMediaScreenAttributeValue(stream.getId(), AppConstants.MEDIA_SCREEN_IMAGECONTENT, screenOrder);
		
		ImageView imageView = new ImageView();
		String imageURL = null;
		for (MediaScreenAttributeValue value : mediaScreenAttributeValues){
			if (value.getMediaScreenAttribute().getAttribute().equals(AppConstants.MEDIA_ATTRIBUTE_SCREEN_IMAGE)){
				imageURL = value.getValue();
			}
		}
		imageView.setImageURL(ImageUtil.parseImageUrl(imageURL));
		
		return imageView;
	}
	private List<MediaScreenAttributeValue> getMediaScreenAttributeValue (Long streamId, String screenType, Integer screenOrder){
		MediaScreenList screenList = new MediaScreenList();
		
		List<MediaScreenList> mediascreenList = mediaStreamDao.findMediaScreenList(streamId, screenType, screenOrder);
		if (mediascreenList.size()> 0) screenList = mediascreenList.get(0);
		
		//Get Stored value info
		List<MediaScreenAttributeValue> mediaScreenAttributeValues = mediaStreamDao.findMediaScreenAttributeValues(screenList.getId());
		
		return mediaScreenAttributeValues;
	}
	
	private MediaStream getMediaStream( String serverName, String streamCode){
		Long communityId = getCommunityId(serverName);
		
		MediaStream stream = null;
		
		try{
			System.out.println("Finding media stream ...");
			stream = mediaStreamDao.findMediaStream(streamCode, communityId).get(0);
			System.out.println("After Finding " + stream.getId());
			
		}catch(NoResultException e){
			System.out.println("Media Stream not found: " + e);
		}catch(IndexOutOfBoundsException e){
			System.out.println("Media Stream not found: " + e);
		}catch(Exception e){
			System.out.println("************ Get error in MediaStreamServiceImpl for Community. Error: " + e);
		}
		
		return stream;
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
	
	public static boolean isWeekend(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int dow = calendar.get (Calendar.DAY_OF_WEEK);

		return (dow == Calendar.SUNDAY) || (dow == Calendar.SATURDAY);
	}
	
	public static Date getPreviousDate(Date endDate){
		Date startDate = null;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date(endDate.getTime() -1l));
		
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
			startDate = new Date(endDate.getTime() - 6*AppConstants.DAY_IN_MILLISECONDS);
		}else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
			startDate = new Date(endDate.getTime() - 3 *AppConstants.DAY_IN_MILLISECONDS);
		}else{
			startDate = new Date(endDate.getTime() - AppConstants.DAY_IN_MILLISECONDS);
		}
		
		return startDate;
	}
	
	public List<ElectricityDataMonth> getMonthlyGaugeData(List<Source> meterSources, Integer days){
		List<ElectricityDataMonth> edms = new ArrayList<ElectricityDataMonth>();
		int dayCounter = 0;
		Calendar calendar = new GregorianCalendar();
		
		while (dayCounter < days){
			ElectricityDataMonth edm = new ElectricityDataMonth();
			ElectricityDataMonth tempEdm = null;
			Double power = 0.0;
			Double energy = 0.0;
			int powerCounter = 0;
			
			calendar.setTime(new Date());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - dayCounter);
			for (Source meterSource : meterSources){
				List<ElectricityDataMonth> tempEdms = electricityDao.findElectricityMonthByTimestamp(1, meterSource.getId(), calendar.getTime());
				if (tempEdms != null && tempEdms.size() > 0){
					tempEdm = tempEdms.get(0);
				}
				power += tempEdm.getPower()/1000;
				if (tempEdm.getPower() > 0.0) powerCounter++;
				energy += tempEdm.getEnergy()/1000;
			}
			
			edm.setEnergy(energy);
			edm.setPower((power/powerCounter));
			
			edms.add(edm);
			power = 0.0;
			energy = 0.0;
			powerCounter = 0;
			dayCounter++;
		}
		return edms;
	}
	public List<ElectricityDataDay> getDailyGaugeData(List<Source> meterSources, Integer days, Boolean includingWeekends){
		List<ElectricityDataDay> edds = new ArrayList<ElectricityDataDay>();
		int dayCounter = 0;
		while (dayCounter < days){
			ElectricityDataDay edd = new ElectricityDataDay();
			ElectricityDataDay tempEdd = null;
			Double power = 0.0;
			Double energy = 0.0;
			int powerCounter = 0;
			for (Source meterSource : meterSources){
				tempEdd = getDailyGaugeDataHelper(meterSource.getId(), dayCounter,includingWeekends);
				power += tempEdd.getPower();
				if (tempEdd.getPower() > 0.0) powerCounter++;
				energy += tempEdd.getEnergy();
			}
			
			if (powerCounter != 0){
				edd.setEnergy(energy);
				if (powerCounter != 0 ) edd.setPower((power/powerCounter));
				else edd.setPower(0.0);
				edds.add(edd);
			}
			
			power = 0.0;
			energy = 0.0;
			powerCounter = 0;
			dayCounter++;
		}
		
		
		return edds;
	}
	
	public ElectricityDataDay getDailyGaugeDataHelper(Long sourceId, Integer days, Boolean includingWeekends){
		ElectricityDataDay edd = new ElectricityDataDay();
		edd.setEnergy(0.0);
		edd.setPower(0.0);
		
		Date endDate = electricityDao.getLatestMinuteData(sourceId);
		System.out.println(sourceId);
		System.out.println(endDate);
		//Get Start Date which is Last Date - date_period in days
		if (endDate != null){
			Date startDate = getPreviousDate(endDate);
			while(days > 0){
				endDate = startDate;
				startDate = getPreviousDate(endDate);
				days--;
			}
			
			Double energy = 0.0;
			Double power = 0.0;
			
				
				List<ElectricityDataMinute> datas = null;
				if (isWeekend(endDate)){
					datas = electricityDao.findElectricityMinuteWithWeekends(sourceId, startDate, endDate, includingWeekends);
				}else{
					datas = electricityDao.findElectricityMinuteWithoutWeekends(sourceId, startDate, endDate, includingWeekends);
				}
				if (datas != null && datas.size() > 0){
					for (ElectricityDataMinute data: datas){
						energy += data.getEnergy();
						power += data.getPower();
					}
					//Conversion to kW from W or to kWh from Wh
					energy = energy/1000;
					power = power/datas.size();
					power /= 1000;
					System.out.println(datas.size());
					System.out.println(power);
					edd.setEnergy(energy);
					edd.setPower(power);
					
					
			}
		}
		return edd;
	}
	
	public List<ElectricityDataDay> getDailyLeaderboardData(List<Source> meterSources, Integer days, Date date, Boolean includingWeekends){
		List<ElectricityDataDay> edds = new ArrayList<ElectricityDataDay>();

		for (Source meterSource : meterSources){
			if (isWeekend(date)){
				edds.addAll(electricityDao.findElectricityDayByTimestampWithWeekends(days, meterSource.getId(), date, includingWeekends));
			}else{
				edds.addAll(electricityDao.findElectricityDayByTimestampWithoutWeekends(days, meterSource.getId(), date, includingWeekends));
			}
			
		}
		
		return edds;
	}
	
	public List<ElectricityDataMonth> getMonthlyLeaderboardData(List<Source> meterSources, Integer days, Date date){
		List<ElectricityDataMonth> edms = new ArrayList<ElectricityDataMonth>();

		for (Source meterSource : meterSources){
			//System.out.println("Meter source " + meterSource.getName());
			edms.addAll(electricityDao.findElectricityMonthByTimestamp(days, meterSource.getId(), date));
		}
		
		return edms;
	}
	
	public List<Source> getMeterSources(Source source){
		List<Source> meterSources = new ArrayList<Source>();
		//System.out.println("Entered Meter Sources....");
		if (source.getSourceMode() == null || source.getSourceMode().getCode().equals(AppConstants.SOURCEMODE_CODE_METER)){
			meterSources.add(source);
			System.out.println("Meter sources entered...!");
		}else if (source.getSourceMode().getCode().equals(AppConstants.SOURCEMODE_CODE_CLUSTER) || source.getSourceMode().getCode().equals(AppConstants.SOURCEMODE_CODE_VIRTUAL)){
			//Get sourceHierarchy Get Children
			List<Source> children = sourceDao.getSourceChildren(source.getId());
			if (children != null && children.size() > 0){
				for (Source child :children){
					meterSources.addAll(getMeterSources(child));
				}
			}
		}
		
		return meterSources;
		
	}
	public static Double[] getGaugeZone(List<Double> datas, Double mean){
		
		Double standardDeviation = 0.0;
		Double sum = 0.0;
		for (Double data : datas){
			System.out.println("Data :: " + data);
			sum += Math.pow((data- mean), 2);
		}
		sum /= datas.size();
		
		
		standardDeviation = Math.sqrt(sum);
		
		System.out.println("Sum :: " + sum + " :: mena " + mean + " :: standardDeviation " + standardDeviation);
		Double[] gaugeData = new Double[4];
		double lowest = mean - 3 * standardDeviation;
		if (lowest < 0) lowest = 0;
		gaugeData[0] = lowest;
		gaugeData[1] = mean - 0.491 * standardDeviation;
		gaugeData[2] = mean + 0.491 * standardDeviation;
		gaugeData[3] = mean + 3 * standardDeviation;
		
		System.out.println(gaugeData[0] + " :: " + gaugeData[1] + " :: " + gaugeData[2] + " :: " + gaugeData[3]);
		return gaugeData;
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

	public static Date getEndOfDay(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMaximum(Calendar.MILLISECOND));

		return new Date(calendar.getTimeInMillis());
	}
	
}
