package net.zfp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.AccountSegment;
import net.zfp.entity.Domain;
import net.zfp.entity.Status;
import net.zfp.entity.User;
import net.zfp.entity.account.AccountGroup;
import net.zfp.entity.alert.AlertNews;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.community.Community;
import net.zfp.entity.community.CommunityNews;
import net.zfp.entity.membership.MembershipNews;
import net.zfp.entity.newscontent.NewsContentType;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.offer.OfferBehavior;
import net.zfp.entity.product.Product;
import net.zfp.entity.salesorder.SalesOrder;
import net.zfp.entity.salesorder.SalesOrderDetail;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.segment.SegmentType;
import net.zfp.entity.survey.Survey;
import net.zfp.form.NewsForm;
import net.zfp.util.AppConstants;
import net.zfp.view.NewsContentView;
import net.zfp.view.ResultView;
import net.zfp.view.WhatsUpView;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class WhatsUpServiceImpl implements WhatsUpService {

	@Resource
    private SegmentService segmentService;
	
	@Resource
	private EntityDao<AlertNews> alertNewsDao;
	
	@Resource
	private EntityDao<Product> productDao;
	
	@Resource
	private EntityDao<Campaign> campaignDao;
	
	@Resource
	private EntityDao<Survey> surveyDao;
	
	@Resource
	private EntityDao<Status> statusDao;
	
	@Resource
	private EntityDao<SalesOrder> salesOrderDao;
	
	@Resource
	private EntityDao<AccountSegment> accountSegmentDao;
	
	@Resource
	private EntityDao<Segment> segmentDao;
	
	@Resource
	private EntityDao<User> userDao;
	
	@Resource
	private EntityDao<Community> communityDao;
	
	@Resource
	private EntityDao<Domain> domainDao;
	
	@Resource
	private EntityDao<Offer> offerDao;
	
	@Resource
	private EntityDao<AccountGroup> accountGroupDao;
	
	@Resource
	private EntityDao<MembershipNews> membershipNewsDao;
	
	@Resource
	private EntityDao<CommunityNews> communityNewsDao;
	
	public static final String vowelsRegex = "^[aeiou].*";
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setAlertViewed(Long alertId){
		ResultView rv = new ResultView();
		
		//Get Alert using alert Id
		AlertNews alert = alertNewsDao.findById(alertId);
		if (alert != null){
			alert.setIsViewed(true);
			
			alertNewsDao.save(alert, true);
			rv.fill(AppConstants.SUCCESS, "Alert has set to viewed");
		}else{
			rv.fill(AppConstants.FAILURE, "Invalid alert ID");
		}
		
		return rv;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setCommunityNews(Long memberId, String referenceCode, String reference){
		ResultView rv = new ResultView();
		
		if (memberId == null || referenceCode == null || reference == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		User activityUser = userDao.findById(memberId);
		if (activityUser == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
			return rv;
		}
		
		Community community = communityDao.findById(activityUser.getDefaultCommunity());
		
		NewsContentType type = communityNewsDao.getNewsContentType(AppConstants.NEWSCONTENT_TYPE_SOCIAL);
		
		Status status = statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE);
		CommunityNews cn = new CommunityNews();
		//Get Header
		String heading = getCommunityHeader(activityUser, referenceCode, reference);
		
		if (heading != null){
			cn.setHeading(heading);
			cn.setNews(getNewsContent(activityUser, referenceCode, reference));
			cn.setCreated(new Date());
			cn.setType(type);
			cn.setUser(activityUser);
			cn.setCommunity(community);
			cn.setStatus(status);
			
			//Image Url...
			cn.setImageUrl(getImageUrl(activityUser, referenceCode, reference));
			//Mobile ImageUrl
			cn.setMobileImageUrl(getMobileImageUrl(activityUser, referenceCode, reference));
			cn.setSupressedMobile(false);
			
			communityNewsDao.save(cn);
			rv.fill(AppConstants.SUCCESS, "Successfully added to community news");
		}else{
			rv.fill(AppConstants.FAILURE, "Invalid reference code or reference");
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setAlertNews(Long memberId, String heading, String news, String imageUrl, String mobileImageUrl, Long trackerType, String referenceCode){
		ResultView rv = new ResultView();
		
		if (memberId == null || heading == null || news == null || imageUrl == null || mobileImageUrl == null || trackerType == null || referenceCode == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		
		User activityUser = userDao.findById(memberId);
		if (activityUser == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
			return rv;
		}
		
		Community community = communityDao.findById(activityUser.getDefaultCommunity());
		
		NewsContentType type = communityNewsDao.getNewsContentType(AppConstants.NEWSCONTENT_TYPE_ALERT);
		Status status = statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE);
		
		AlertNews an = new AlertNews();
		
		an.setHeading(heading);
		an.setNews(news);
		an.setType(type);
		an.setUser(activityUser);
		an.setCommunity(community);
		an.setStatus(status);
		
		an.setImageUrl(imageUrl);
		an.setMobileImageUrl(mobileImageUrl);
		an.setSupressedMobile(false);
		an.setIsViewed(false);
		an.setTrackerTypeId(trackerType);
		an.setReference(referenceCode);
		
		an.setCreated(new Date());
		
		alertNewsDao.save(an);
		
		rv.fill(AppConstants.SUCCESS, "Successfully added to alert news");
		return rv;
	}
	
	
	private String getMobileImageUrl(User user, String referenceCode, String reference){
		String content = "";
		
		if (referenceCode.equals(AppConstants.REFERENCECODE_SALES_ORDER)){
			
			//reference is product
			Product product = productDao.getProductByUPC(reference);
			
			if (product != null){
				content += product.getMobileSmallImageUrl();
			}
			
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_RECEIPT_UPLOAD)){
			
			Offer offer = offerDao.getOfferByCode(Long.parseLong(reference));
			
			if (offer != null){
				content += offer.getMobileSmallImageUrl();
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_SIGN_UP)){
			OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_JOIN, AppConstants.BEHAVIOR_TRACKERTYPE_MEMBERSHIP);
			if (ob != null){
				Offer offer = offerDao.getOfferByBehavior(ob.getId(), new Date());
				if (offer != null){
					content += offer.getMobileSmallImageUrl();
				}
			}
		}
		else if (referenceCode.equals(AppConstants.REFERENCECODE_JOIN_CAMPAIGN)){
			//reference code is campaign name
			Campaign campaign = campaignDao.getCampaign(Long.parseLong(reference));
			if (campaign != null){
				content += campaign.getMobileSmallImageUrl();
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_LINK)) content = "";
		else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_CAMPAIGN)){
			Campaign campaign = campaignDao.findById(Long.parseLong(reference));
			if (campaign != null){
				content = campaign.getMobileSmallImageUrl();
			}
		}
		else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_OFFER)){
			Offer offer = offerDao.getOfferByCode(Long.parseLong(reference));
			if (offer != null){
				content = offer.getMobileSmallImageUrl();
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_PRODUCT)){
			Product product = productDao.getProductByUPC(reference);
			if (product != null){
				content = product.getMobileSmallImageUrl();
			}
		
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_REDEEM)){
			//reference code is sales order
			List<SalesOrderDetail> sod = salesOrderDao.getTransactionDetails(Long.parseLong(reference));
			if (sod != null && sod.size() > 0){
				//reference is product
				Product product = productDao.getProductByUPC(sod.get(0).getUPC());
				
				if (product != null){
					content += product.getMobileSmallImageUrl();
				}
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_SURVEY)){
			OfferBehavior ob = null;
			ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_COMPLETE, AppConstants.BEHAVIOR_TRACKERTYPE_SURVEY);
			if (ob != null){
				Offer offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), reference);
				
				if (offer != null){
					content += offer.getMobileSmallImageUrl();
				}
			}
			
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_COMPLETE_CAMPAIGN)){
			
			Campaign campaign = campaignDao.getCampaign(Long.parseLong(reference));
			if (campaign != null){
				content += campaign.getMobileSmallImageUrl();
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_WIN_MOST_CAMPAIGN) || referenceCode.equals(AppConstants.REFERENCECODE_WIN_LEAST_CAMPAIGN)){
			Campaign campaign = campaignDao.getCampaign(Long.parseLong(reference));
			if (campaign != null){
				content += campaign.getMobileSmallImageUrl();
			}
		}
		return content;
	}
	
	private String getImageUrl(User user, String referenceCode, String reference){
		String content = "";
		
		if (referenceCode.equals(AppConstants.REFERENCECODE_SALES_ORDER)){
			
			//reference is product
			Product product = productDao.getProductByUPC(reference);
			
			if (product != null){
				content += product.getSmallImageUrl();
			}
			
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_RECEIPT_UPLOAD)){
			
			Offer offer = offerDao.getOfferByCode(Long.parseLong(reference));
			
			if (offer != null){
				content += offer.getSmallImageUrl();
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_SIGN_UP)){
			OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_JOIN, AppConstants.BEHAVIOR_TRACKERTYPE_MEMBERSHIP);
			if (ob != null){
				Offer offer = offerDao.getOfferByBehavior(ob.getId(), new Date());
				if (offer != null){
					content += offer.getSmallImageUrl();
				}
			}
			
		}
		else if (referenceCode.equals(AppConstants.REFERENCECODE_JOIN_CAMPAIGN)){
			//reference code is campaign name
			Campaign campaign = campaignDao.getCampaign(Long.parseLong(reference));
			if (campaign != null){
				content += campaign.getSmallImageUrl();
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_LINK)) content = "";
		else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_CAMPAIGN)){
			Campaign campaign = campaignDao.findById(Long.parseLong(reference));
			if (campaign != null){
				content = campaign.getSmallImageUrl();
			}
		}
		else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_OFFER)){
			Offer offer = offerDao.getOfferByCode(Long.parseLong(reference));
			if (offer != null){
				content = offer.getSmallImageUrl();
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_PRODUCT)){
			Product product = productDao.getProductByUPC(reference);
			if (product != null){
				content = product.getSmallImageUrl();
			}
		
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_REDEEM)){
			//reference code is sales order
			List<SalesOrderDetail> sod = salesOrderDao.getTransactionDetails(Long.parseLong(reference));
			if (sod != null && sod.size() > 0){
				//reference is product
				Product product = productDao.getProductByUPC(sod.get(0).getUPC());
				
				if (product != null){
					content += product.getSmallImageUrl();
				}
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_SURVEY)){
			OfferBehavior ob = null;
			ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_COMPLETE, AppConstants.BEHAVIOR_TRACKERTYPE_SURVEY);
			if (ob != null){
				Offer offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), reference);
				
				if (offer != null){
					content += offer.getSmallImageUrl();
				}
			}
			
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_COMPLETE_CAMPAIGN)){
			
			Campaign campaign = campaignDao.getCampaign(Long.parseLong(reference));
			if (campaign != null){
				content += campaign.getSmallImageUrl();
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_WIN_MOST_CAMPAIGN) || referenceCode.equals(AppConstants.REFERENCECODE_WIN_LEAST_CAMPAIGN)){
			Campaign campaign = campaignDao.getCampaign(Long.parseLong(reference));
			if (campaign != null){
				content += campaign.getSmallImageUrl();
			}
		}
		return content;
	}
	
	private String getNewsContent(User user, String referenceCode, String reference){
		String content = "";
		
		if (referenceCode.equals(AppConstants.REFERENCECODE_SALES_ORDER)){
			
			//reference is product
			Product product = productDao.getProductByUPC(reference);
			
			if (product != null){
				content += "The " + product.getName();
			}
			
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_RECEIPT_UPLOAD)){
			content += "To the";
			Offer offer = offerDao.getOfferByCode(Long.parseLong(reference));
			
			if (offer != null){
				content += offer.getName() + " offer";
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_SIGN_UP)){
			content += "As a new member to our program!";
		}
		else if (referenceCode.equals(AppConstants.REFERENCECODE_JOIN_CAMPAIGN)){
			//reference code is campaign name
			Campaign campaign = campaignDao.getCampaign(Long.parseLong(reference));
			if (campaign != null){
				content += "The " + campaign.getName();
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_CAMPAIGN)){
			Campaign campaign = campaignDao.findById(Long.parseLong(reference));
			if (campaign != null){
				content = "The " + campaign.getName() + " with friends";
			}
		}
		else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_OFFER)){
			Offer offer = offerDao.getOfferByCode(Long.parseLong(reference));
			if (offer != null){
				content = "The " + offer.getName() + " with friends";
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_PRODUCT)){
			Product product = productDao.getProductByUPC(reference);
			if (product != null){
				content = "The " + product.getName() + " with friends";
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_REDEEM)){
		
			//reference code is sales order
			SalesOrder so = salesOrderDao.findTransactionByOrderNumber(Long.parseLong(reference));
			if (so != null){
				content += so.getTotalPoints() + "points";
				
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_SURVEY)){
			Survey survey = surveyDao.findById(Long.parseLong(reference));
			if (survey != null){
				content += "The " + survey.getName() + ". Have you?";
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_COMPLETE_CAMPAIGN)){
			Campaign campaign = campaignDao.getCampaign(Long.parseLong(reference));
			if (campaign != null){
				content += "In the "+campaign.getName();
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_WIN_MOST_CAMPAIGN) || referenceCode.equals(AppConstants.REFERENCECODE_WIN_LEAST_CAMPAIGN)){
			
			Campaign campaign = campaignDao.getCampaign(Long.parseLong(reference));
			if (campaign != null){
				content += "The " + campaign.getName();
			}
		}
		
		return content;
	}
	
	private Boolean isVowel(String character){
		if (character.toLowerCase().matches(vowelsRegex)) return true;
		else return false;
	}
	private String getCommunityHeader(User user, String referenceCode, String reference){
		String header= null;
		
		if (referenceCode.equals(AppConstants.REFERENCECODE_SALES_ORDER)){
			header = user.getFirstName() + " " + user.getLastName().toUpperCase().charAt(0) + ". purchased";
		}
		else if (referenceCode.equals(AppConstants.REFERENCECODE_RECEIPT_UPLOAD)){
			header = user.getFirstName() + " " + user.getLastName().toUpperCase().charAt(0) + ". uploaded";
		}
		else if (referenceCode.equals(AppConstants.REFERENCECODE_SIGN_UP)){
			header = "Welcome " + user.getFirstName() + " " + user.getLastName().toUpperCase().charAt(0) + ".";
		}
		else if (referenceCode.equals(AppConstants.REFERENCECODE_JOIN_CAMPAIGN)){
			header = user.getFirstName() + " " + user.getLastName().toUpperCase().charAt(0) + ". joined";
		}
		else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_LINK) || referenceCode.equals(AppConstants.REFERENCECODE_SHARE_CAMPAIGN) 
				|| referenceCode.equals(AppConstants.REFERENCECODE_SHARE_OFFER)
				|| referenceCode.equals(AppConstants.REFERENCECODE_SHARE_PRODUCT)){
			header = user.getFirstName() + " " + user.getLastName().toUpperCase().charAt(0) + ". shared";
			
		}
		else if (referenceCode.equals(AppConstants.REFERENCECODE_REDEEM)){
			header = user.getFirstName() + " " + user.getLastName().toUpperCase().charAt(0) + ". redeemed";
		}
		else if (referenceCode.equals(AppConstants.REFERENCECODE_SURVEY)){
			Survey survey = surveyDao.findById(Long.parseLong(reference));
			if (survey != null){
				header = user.getFirstName() + " " + user.getLastName().toUpperCase().charAt(0) + ". completed";
			}
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_COMPLETE_CAMPAIGN)){
			header = user.getFirstName() + " " + user.getLastName().toUpperCase().charAt(0) + ". met the target";
		}else if (referenceCode.equals(AppConstants.REFERENCECODE_WIN_MOST_CAMPAIGN) || referenceCode.equals(AppConstants.REFERENCECODE_WIN_LEAST_CAMPAIGN)){
			header = user.getFirstName() + " " + user.getLastName().toUpperCase().charAt(0) + ". won!";
		}
		
		return header;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public WhatsUpView getWhatsUps(Long accountId, Integer page, Integer limit, Boolean isMobile){
		WhatsUpView asv = new WhatsUpView();
		ResultView rv = new ResultView();
		
		if (accountId == null || page == null || limit == null || isMobile == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			asv.setResult(rv);
			return asv;
		}
		
		User user = userDao.findById(accountId);
		if (user == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
			asv.setResult(rv);
			return asv;
		}
		
		/*
		 * Calculate what's the maximum news we need to extract
		 */
		int maximumResult = page * limit;
		
		/*
		 * This counts how many items are in news
		 */
		int total = 0;
		
		Integer alertNotViewedCounter = 0;
		
		Date fromDate = null;
		if (isMobile != null && isMobile) fromDate = new Date(new Date().getTime() - 30*24*60*60*1000l);
		else fromDate = new Date(new Date().getTime() - 60*24*60*60*1000l);
		
		System.out.println("Local " + fromDate);
		
		List<NewsContentView> ncvs = new ArrayList<NewsContentView>();
		
		List<NewsContentView> alertNCVs = new ArrayList<NewsContentView>();
		
		//Check each for product category
		List<AlertNews> alerts = alertNewsDao.getAlertNewsByType(accountId, fromDate);
		
		NewsContentView ncv = null;
		if (!alerts.isEmpty()){
			
			/*
			 * Add all alerts news to total counter
			 */
			total += alerts.size();
			
			for (AlertNews an : alerts){
				if (isMobile && an.getSupressedMobile()) continue;
				
				ncv = new NewsContentView(an);
				ncv.setRemaining(getTimeRemaining(an.getCreated()));
				if (!an.getIsViewed()){
					alertNCVs.add(ncv);
					alertNotViewedCounter++;
				}else{
					ncvs.add(ncv);
				}
				
				
			}
		}
		
		asv.setAlerts(alertNCVs);
		asv.setNot_viewed_count(alertNotViewedCounter);
		
		//Get Friends from my default group
		List<User> friends = accountGroupDao.getFriends(accountId);
		
		if (friends != null && friends.size() > 0){
			List<CommunityNews> communityNews = communityNewsDao.getCommunityNewsByType(friends, fromDate, maximumResult);
			Integer communityNewsCounter = communityNewsDao.getNumberOfCommunityNewsByType(friends, fromDate);
			
			/*
			 * Add all community news to total counter
			 */
			total += communityNewsCounter;
			
			if (!communityNews.isEmpty()){
				for (CommunityNews an : communityNews){
					
					if (isMobile && an.getSupressedMobile()) continue;
					
					ncv = new NewsContentView(an);
					ncv.setRemaining(getTimeRemaining(an.getCreated()));
					ncvs.add(ncv);
				}
			}
		}
		
		List<Segment> segments = segmentService.getMemberSegment(user.getId());
		
		if (segments != null && segments.size() > 0){
			List<MembershipNews> membershipNews = membershipNewsDao.getMembershipNews(segments, user.getDefaultCommunity(), fromDate, maximumResult);
			Integer membershipNewsCounter = membershipNewsDao.getNumberOfMembershipNews(segments, user.getDefaultCommunity(), fromDate);
			
			System.out.println("membershipt counter " + membershipNewsCounter);
			/*
			 * Add all membership news to total counter
			 */
			total += membershipNewsCounter;
			
			if (membershipNews != null && membershipNews.size() > 0){
				for (MembershipNews an : membershipNews){
					
					if (an.getSegment() == null && !an.getCommunity().getId().equals(user.getDefaultCommunity())) continue;
					if (isMobile && an.getSupressedMobile()) continue;
					
					ncv = new NewsContentView(an);
					ncv.setRemaining(getTimeRemaining(an.getPublished()));
					ncvs.add(ncv);
				}
			}
		}
		
		
		//Sort by day late
		Comparator<NewsContentView> dateComparator = new Comparator<NewsContentView>(){
			public int compare(NewsContentView ncv1, NewsContentView ncv2){
				return ncv2.getDate().compareTo(ncv1.getDate());
			}
		};
		
		Collections.sort(ncvs, dateComparator);
		
		Integer pageCounts = total / limit;
		
		if (pageCounts * limit < total){
	    	pageCounts++;
	    }
		
		asv.setTotal(pageCounts);
		asv.setActual(page);
		
		List<NewsContentView> combineList = new ArrayList<NewsContentView>();
		
		if (page > pageCounts){
			//Something maybe is wrong
		}else{
			//Get right pages
			for (int i= ((page-1) * limit); i< (page * limit); i++){
				if (i <ncvs.size()) combineList.add(ncvs.get(i));
				else break;
			}
			
			asv.setNews(combineList);
		}
		
		return asv;
		
	}
	
	private String getTimeRemaining(Date alerts){
		//Check date compare to now
		Date now = new Date();
		String result = "";
		Integer days = Days.daysBetween(new DateTime(alerts), new DateTime(now)).getDays();
		if (days > 0){
			result = days + "d";
		}else{
			Integer hours = Hours.hoursBetween(new DateTime(alerts), new DateTime(now)).getHours();
			if (hours > 0){
				result = hours + "h";
			}else{
				Integer minutes = Minutes.minutesBetween(new DateTime(alerts), new DateTime(now)).getMinutes();
				if (minutes > 0){
					result = minutes + "m";
				}else{
					Integer seconds = Seconds.secondsBetween(new DateTime(alerts), new DateTime(now)).getSeconds();
					result = seconds + "s";
				}
				
			}
			
		}
		
		return result;
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
