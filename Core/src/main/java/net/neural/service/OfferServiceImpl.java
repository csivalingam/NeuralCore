package net.zfp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Translation;
import net.zfp.entity.User;
import net.zfp.entity.account.AccountCampaign;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.category.Category;
import net.zfp.entity.community.Community;
import net.zfp.entity.memberactivity.MemberActivity;
import net.zfp.entity.memberactivity.MemberActivityType;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.offer.OfferBehavior;
import net.zfp.entity.offer.OfferBehaviorAttribute;
import net.zfp.entity.offer.OfferValue;
import net.zfp.entity.partner.BusinessPartner;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.segment.SegmentType;
import net.zfp.service.OfferService;
import net.zfp.service.UtilityService;
import net.zfp.util.AppConstants;
import net.zfp.util.ImageUtil;
import net.zfp.util.TextUtil;
import net.zfp.view.OfferView;
import net.zfp.view.OfferViews;
import net.zfp.view.ResultView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Resource
public class OfferServiceImpl implements OfferService {
	@Resource
    private UtilityService utilityService;
	@Resource
    private SegmentService segmentService;
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<Category> categoryDao;
	@Resource
	private EntityDao<AccountCampaign> accountCampaignDao;
	@Resource
	private EntityDao<BusinessPartner> businessPartnerDao;
	@Resource
	private EntityDao<Campaign> campaignDao;
	@Resource
	private EntityDao<Segment> segmentDao;
	@Resource
	private EntityDao<Offer> offerDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Translation> translationDao;
	@Resource
	private EntityDao<MemberActivity> memberActivityDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView checkMemberQualification(Long memberId, String action, String trackerType, Date date){
		ResultView rv = new ResultView();
		
		if (memberId == null || action == null || trackerType == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		
		if (date == null)
			date = new Date();
		
		List<Segment> segments = null;
		segments = segmentService.getMemberSegment(memberId);
		
		System.out.println(segments.size());
		List<Offer> offers = offerDao.getOfferByBehaviorActionsAndTrackerType(trackerType, action, segments, new Date());
		
		if (offers.isEmpty()){
			rv.fill(AppConstants.FAILURE, "Member does not qualify");
		}else{
			rv.fill(AppConstants.SUCCESS, "Member does qualify");
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView checkValidOffer(Long offerCode, Date date){
		ResultView rv = new ResultView();
		
		if (offerCode == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		
		if (date == null)
			date = new Date();
		
		Offer offer = offerDao.getOfferByCode(offerCode);
		
		if (offer != null){
			if (offer.getEndDate().after(new Date()) && offer.getStartDate().before(new Date())){
				rv.fill(AppConstants.SUCCESS, "Offer is valid");
			}else{
				rv.fill(AppConstants.FAILURE, "Offer is not valid");
			}
		}else{
			rv.fill(AppConstants.FAILURE, "Offer is not valid");
		}
		
		return rv;
	}
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<OfferView> getUploadOffers(String domainName, Long memberId){
		System.out.println("GET UPLOAD OFFER : " + new Date());
				
		List<OfferView> ovs = new ArrayList<OfferView>();
		Community community = communityDao.getCommunity(domainName);
		//Get upload offers
		//Get Upload behavior action
		//Get List of offer behavior
		
		List<Segment> segments = null;
		if (memberId != null) segments = segmentService.getMemberSegment(memberId);
		else segments = segmentService.getDefaultSegment(community.getId());
		
		System.out.println(segments.size());
		List<Offer> offers = offerDao.getOfferByBehaviorActions(AppConstants.BEHAVIOR_ACTION_UPLOAD, segments, new Date());
		//Get List of Offer Behavior attributes
		if (offers != null && offers.size() > 0){
			for (Offer offer : offers){
				System.out.println("OFFER NAME : " + offer.getName());
				if (offer.getSegment() == null && offer.getCommunity() != null && offer.getCommunity().getId() != community.getId()){
					continue;
				}
				OfferView ov = new OfferView(offer);
				
				ovs.add(ov);
			}
		}
		
		return ovs;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<OfferView> getOffersByCategoryType(String domainName, String offerType,  String categoryType, String locale){
		List<OfferView> ovs = new ArrayList<OfferView>();
		Community community = communityDao.getCommunity(domainName);
		Long communityId = community.getId();
		Category category = categoryDao.getCategoryByCode(categoryType, 3);
		List<Offer> offers = offerDao.getOffersByCategoryAndType(category.getId(), offerType, communityId, new Date());
		if (offers != null && offers.size() > 0){
			for (Offer offer : offers){
				OfferView ov = new OfferView(offer);
				
				if (offer.getNameTranslationKey() != null){
					try{
						String translation = translationDao.findTranslation(offer.getNameTranslationKey(), community.getId(), 2l, locale, 3l).getTranslation();
						ov.setName(translation);
						
					}catch (Exception e){
						//Its not a transaltion key.
					}
				}
				try{
					String imageTranslation = translationDao.findTranslation(offer.getSmallImageUrl(), community.getId(), 2l, locale, 3l).getTranslation();
					ov.setSmallImageUrl(ImageUtil.parseImageUrl(imageTranslation));
					
				}catch (Exception e){
					//Its not a transaltion key.
					e.printStackTrace();
				}
				
				System.out.println("OfferView :: " + ov.getSmallImageUrl());
				ovs.add(ov);
			}
		}
		return ovs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public OfferViews getOffersByCommunity(String communityCode, Integer topNumber){
		
		OfferViews offerViews = new OfferViews();
		ResultView rv = new ResultView();
		
		if (communityCode == null || topNumber == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			offerViews.setResult(rv);
			return offerViews;
		}
		
		Community community = communityDao.getCommunityByCode(communityCode);
		
		if (community == null){
			rv.fill(AppConstants.FAILURE, "Community code is invalid");
			offerViews.setResult(rv);
			return offerViews;
		}
		Long communityId = community.getId();
		
		List<Segment> segments = segmentService.getDefaultSegment(communityId);
		
		List<Offer> offers = null;
		
		offers = offerDao.getOffersByTopNumber(topNumber, segments, communityId);
		
		if (!offers.isEmpty()){

			List<OfferView> ovs = new ArrayList<OfferView>();
			OfferView ov = new OfferView();
			
			for (Offer offer : offers){
						
				ov = new OfferView(offer);
				ovs.add(ov);
			}
			offerViews.setOfferViews(ovs);
		}else{
			offerViews.setResult(rv);
			return offerViews;
		}
		
		return offerViews;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public OfferViews getOffersByTopNumber(String domainName, Long memberId, Long offerTypeMode, Integer topNumber)
	{
		OfferViews offerViews = new OfferViews();
		
		System.out.println("OFFER TYPE NUMBER : " + new Date());
		
		ResultView rv = new ResultView();
		if (offerTypeMode == null || topNumber == null || (domainName == null && memberId == null)){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			offerViews.setResult(rv);
			return offerViews;
		}
		
		List<Segment> segments = new ArrayList<Segment>();
		Long communityId = null;
		if (memberId != null){
			User user = userDao.findById(memberId);
			if (user != null){
				communityId = user.getId();
				segments = segmentService.getMemberSegment(memberId);
			}
		}
		
		if (segments.isEmpty()){
			Community community = communityDao.getCommunity(domainName);
			if (community != null){
				communityId = community.getId();
				segments = segmentService.getDefaultSegment(communityId);
			}
		}
		
		if (segments.isEmpty()){
			rv.fill(AppConstants.FAILURE, "Invalid domain or member");
			offerViews.setResult(rv);
			return offerViews;
		}
		
		List<Offer> offers = null;
		if (offerTypeMode == 0){
			offers = offerDao.getOffersByTopNumber(topNumber, segments, communityId);
			if (!offers.isEmpty()){
				offers.addAll(offerDao.getOffersByTopNumber(topNumber- offers.size(), segments, communityId));
			}else{
				offers = offerDao.getOffersByTopNumber(topNumber, segments, communityId);
			}
			
		}else{
			Category category = categoryDao.getCategoryByType(offerTypeMode);
			if (category != null){
				offers = offerDao.getOffersByCategoryAndTopNumber(category.getId(), topNumber, segments, communityId);
			}
			
		}
		if (!offers.isEmpty()){
			List<OfferView> ovs = new ArrayList<OfferView>();
			OfferView ov = new OfferView();
			
			for (Offer offer : offers){
						
				ov = new OfferView(offer);
				ovs.add(ov);
			}
			offerViews.setOfferViews(ovs);
		}else{
			rv.fill(AppConstants.FAILURE, "Currently there are no offers");
			offerViews.setResult(rv);
		}
		
		return offerViews;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public OfferViews getAllOffers(String domainName, Long offerTypeMode, Long memberId){
		OfferViews offerViews = new OfferViews();
		
		List<OfferView> ovs = new ArrayList<OfferView>();
		
		OfferView ov = new OfferView();
		ResultView rv = new ResultView();
		
		System.out.println("Offer :: " + domainName + " : " + offerTypeMode + " : " + memberId);
		
		if (offerTypeMode == null || (domainName == null && memberId == null)){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			offerViews.setResult(rv);
			return offerViews;
		}
		
		List<Category> categories = new ArrayList<Category>();
		if (offerTypeMode == 0){
			categories = categoryDao.getCategories(AppConstants.CATEGORYTYPE_OFFER);
		}else{
			categories.add(categoryDao.getCategoryByType(offerTypeMode));
		}
		
		List<Segment> segments = new ArrayList<Segment>();
		Long communityId = null;
		if (memberId != null){
			User user = userDao.findById(memberId);
			if (user != null){
				communityId = user.getId();
				segments = segmentService.getMemberSegment(memberId);
			}
		}
		
		if (communityId == null){
			Community community = communityDao.getCommunity(domainName);
			if (community != null){
				communityId = community.getId();
				segments = segmentService.getDefaultSegment(communityId);
			}
		}
		
		if (segments.isEmpty()){
			rv.fill(AppConstants.FAILURE, "Invalid domain or member");
			offerViews.setResult(rv);
			return offerViews;
		}
		
		if (categories != null && categories.size() > 0){
			for (Category category : categories){
			
				List<Offer> offers = offerDao.getOffersByCategory(category.getId(), segments);
				//System.out.println("BEFORE IF OFFER : " + offers.size());
				if (offers != null && offers.size() >0){
					for (Offer offer : offers){
						
						System.out.println("OFFER NAME : " + offer.getName());
						//check if no segments and community is not right one
						if (offer.getSegment() == null && offer.getCommunity() != null && offer.getCommunity().getId() != communityId){
							continue;
						}
						//This is webservice is for earn points. Thus omit targetted special offers
						if (offer.getSuppress() == null || offer.getSuppress()){
							//System.out.println("offer Special" + offer.getDisplay() + " :: " + isSpecial);
							continue;
						}
						
						if (offer.getSegment() != null || offer.getCommunity() == null || offer.getCommunity().getId() == communityId){
							 ov = new OfferView(offer);
							 
							 if (offer.getOfferValueType() != null && offer.getOfferValueType().getType().equals(AppConstants.OFFER_VALUE_TYPE_RATIO)){
								List<OfferValue> offerValues = offerDao.getOfferValueByOffer(offer.getCode());
								if (!offerValues.isEmpty()){
									ov.setRatioValue(offerValues.get(0).getValue());
									ov.setRatioValueUnit(offerValues.get(0).getUnit().getDescription());
								}
								
							 }
							 
							 //Type name to campaign offer
							 List<String> campaigns = offerDao.getCampaignByBehaviorAttributes(AppConstants.BEHAVIOR_TRACKERTYPE_CAMPAIGN, offer.getCode(), new Date());
							 if (campaigns != null && campaigns.size() > 0){
								 Campaign campaign = campaignDao.findById(Long.parseLong(campaigns.get(0)));
								 ov.setTypeName(AppConstants.OFFER_TYPE_CAMPAIGN);
								 ov.setProgramId(campaign.getId());
								 
								 if (memberId != null){
									 //Check if this account joined the program
									 AccountCampaign ap = accountCampaignDao.getAccountCampaign(memberId, campaign.getId());
									 System.out.println("OFFER SIZE :: " + memberId + " :: " + campaign.getId());
									 if (ap != null){
										 //Alreay Joined
										 System.out.println("Joined ");
										 ov.setProgramViewType(0);
									 }else{
										 System.out.println("Not joined ");
										 ov.setProgramViewType(1);
									 }
								 }else{
									 ov.setProgramViewType(1);
								 }
								
							 }
							 List<String> surveys = offerDao.getCampaignByBehaviorAttributes(AppConstants.BEHAVIOR_TRACKERTYPE_SURVEY, offer.getCode(), new Date());
							 if (surveys != null && surveys.size() > 0){
								 ov.setTypeName(AppConstants.OFFER_TYPE_SURVEY);
								 ov.setProgramId(Long.parseLong(surveys.get(0)));
							 }
							
							 if (offer.getOfferTemplate() != null){
								 //Check if it is sign up offer
								 if (offer.getOfferTemplate().getOfferBehavior().getBehaviorTrackerType().getCode().equals(AppConstants.BEHAVIOR_TRACKERTYPE_MEMBERSHIP) &&
										 offer.getOfferTemplate().getOfferBehavior().getBehaviorAction().getCode().equals(AppConstants.BEHAVIOR_ACTION_JOIN)){
									 ov.setTypeName(AppConstants.OFFER_TYPE_SIGNUP);
								 }else if (offer.getOfferTemplate().getOfferBehavior().getBehaviorTrackerType().getCode().equals(AppConstants.BEHAVIOR_TRACKERTYPE_MEMBERSHIP) &&
										 offer.getOfferTemplate().getOfferBehavior().getBehaviorAction().getCode().equals(AppConstants.BEHAVIOR_ACTION_SIGN_IN)){
									 
									 ov.setTypeName(AppConstants.OFFER_TYPE_SIGNIN);
									 
									 //check if this user already have sign in
									 if (memberId != null){
										 //Check if a user has already did in memberacitivity
										 MemberActivityType mat = memberActivityDao.getMemberActivityType(AppConstants.MEMBERACTIVITY_TYPE_SIGNIN);
										 MemberActivity ma = memberActivityDao.getMemberActivity(memberId, mat.getId(), AppConstants.REFERENCECODE_MEMBERSHIP);
										 if (ma != null){
											 System.out.println("Has member activity");
											 ov.setProgramViewType(0);
										 }else{
											 System.out.println("does not have a member activity");
											 ov.setProgramViewType(1);
										 }
									 }
									 
									 
								 }
								 
								 if (offer.getOfferTemplate().getOfferType().getName().equals(AppConstants.OFFER_TYPE_BUY_AND_GET)){
									 //Action and resultsType
									 
									 OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_BUY, AppConstants.BEHAVIOR_TRACKERTYPE_UPC);
									 if ( ob != null){
										 OfferBehaviorAttribute oba = offerDao.getOfferBehaviorAttribute(offer.getId(), ob.getId());
										 if (oba != null) ov.setProductUPC(oba.getBehaviorAttribute());
									 }
									 
								 }
							 }
							 ov.setCategoryType(category.getCategoryType().getId());
							 ovs.add(ov);
						}
					}
				}
				
			}
		}
		
		
		//Sort!\
		Comparator<OfferView> offerComparator = new Comparator<OfferView>(){
			public int compare(OfferView offer1, OfferView offer2){
				//return campaign1.getName().compareTo(campaign2.getName());
				return (offer1.getStartDate().compareTo(offer2.getStartDate())) * -1;
			}
		};
		
		Collections.sort(ovs, offerComparator);
		
		offerViews.setOfferViews(ovs);
		return offerViews;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public OfferView getOfferByBehavior(Long memberId, String attribute, String action, String trackerType){
		OfferView ov = new OfferView();
		ResultView rv = new ResultView();
		
		if (attribute == null || action == null || trackerType == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			ov.setResult(rv);
			return ov;
		}
		
		Date now = new Date();
		
		OfferBehavior ob = offerDao.getOfferBehavior(action, trackerType);
		
		if (ob != null){
			Offer offer = null;
			if (memberId == null)
				offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), attribute, now);
			else{
				List<Segment> segments = segmentService.getMemberSegment(memberId);
				offer = offerDao.getOfferByBehaviorAndSegments(ob.getId(), attribute, now, segments);
			}
				
			if (offer != null){
				ov = new OfferView(offer);
			}else{
				rv.fill(AppConstants.FAILURE,"Offer is not valid");
				ov.setResult(rv);
			}
		}else{
			rv.fill(AppConstants.FAILURE, "Offer behavior is not valid");
			ov.setResult(rv);
		}
		
		return ov;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public OfferView getOffer(Long offerCode, Long accountId){
		OfferView ov = new OfferView();
		ResultView rv = new ResultView();
		
		if (offerCode == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			ov.setResult(rv);
			return ov;
		}
		
		Offer offer = offerDao.getOfferByCode(offerCode);
		
		if (offer != null){
			ov = new OfferView(offer);
			ov.setName(TextUtil.parseString(offer.getName()));
			ov.setDescription(TextUtil.parseString(offer.getLongDescription()));
			
			if (offer.getOfferValueType() != null && offer.getOfferValueType().getType().equals(AppConstants.OFFER_VALUE_TYPE_RATIO)){
				List<OfferValue> offerValues = offerDao.getOfferValueByOffer(offer.getCode());
				if (!offerValues.isEmpty()){
					ov.setRatioValue(offerValues.get(0).getValue());
					ov.setRatioValueUnit(offerValues.get(0).getUnit().getDescription());
				}
				
			 }
			if (offer.getOfferTemplate() != null){
				//Type name to campaign offer
				 List<String> campaigns = offerDao.getCampaignByBehaviorAttributes(AppConstants.BEHAVIOR_TRACKERTYPE_CAMPAIGN, offer.getCode(), new Date());
				 if (campaigns != null && campaigns.size() > 0){
					 Campaign campaign = campaignDao.findById(Long.parseLong(campaigns.get(0)));
					 ov.setTypeName(AppConstants.OFFER_TYPE_CAMPAIGN);
					 ov.setProgramId(campaign.getId());
					 
					 if (accountId != null){
						 //Check if this account joined the program
						 AccountCampaign ap = accountCampaignDao.getAccountCampaign(accountId, campaign.getId());
						 if (ap != null){
							 //Alreay Joined
							 ov.setProgramViewType(0);
						 }else{
							 ov.setProgramViewType(1);
						 }
					 }else{
						 ov.setProgramViewType(1);
					 }
					 
				 }
				 if (offer.getOfferTemplate().getOfferBehavior().getBehaviorTrackerType().getCode().equals(AppConstants.BEHAVIOR_TRACKERTYPE_MEMBERSHIP) &&
						 offer.getOfferTemplate().getOfferBehavior().getBehaviorAction().getCode().equals(AppConstants.BEHAVIOR_ACTION_JOIN)){
					 ov.setTypeName(AppConstants.OFFER_TYPE_SIGNUP);
				 }
				 
				 List<String> surveys = offerDao.getCampaignByBehaviorAttributes(AppConstants.BEHAVIOR_TRACKERTYPE_SURVEY, offer.getCode(), new Date());
				 if (surveys != null && surveys.size() > 0){
					 
					 ov.setTypeName(AppConstants.OFFER_TYPE_SURVEY);
					 ov.setProgramId(Long.parseLong(surveys.get(0)));
				 }
				 
				 if (offer.getOfferTemplate().getOfferType().getName().equals(AppConstants.OFFER_TYPE_BUY_AND_GET)){
					 //Action and resultsType
					 
					 OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_BUY, AppConstants.BEHAVIOR_TRACKERTYPE_UPC);
					 if ( ob != null){
						 OfferBehaviorAttribute oba = offerDao.getOfferBehaviorAttribute(offer.getId(), ob.getId());
						 if (oba != null) ov.setProductUPC(oba.getBehaviorAttribute());
					 }
					 
				 }
			 }
		}else{
			rv.fill(AppConstants.FAILURE, "Offer is not valid");
			ov.setResult(rv);
		}
		
		return ov;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<OfferView> getOffers(String domainName, String offerTypeName)
	{
		List<OfferView> ovs = new ArrayList<OfferView>();
		//getCommunityId
		//Get communityCode
		Community community = communityDao.getCommunity(domainName);
		Long communityId = community.getId();
		
		Date currentDate = new Date();
		//based on offerTypeName and offerId
		//fetch all the offers
		List<Offer> offers = offerDao.getOffers(offerTypeName, communityId, currentDate);		
		
		if (offers != null){
			//store them into ovs
			OfferView ov = null;
			for (Offer offer : offers){
				ov = new OfferView(offer);
				ovs.add(ov);
			}
		}
		
		return ovs;
	}
}


