package net.zfp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Domain;
import net.zfp.entity.User;
import net.zfp.entity.charity.Fundraiser;
import net.zfp.entity.community.Community;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.offer.OfferValue;
import net.zfp.entity.segment.Segment;
import net.zfp.util.AppConstants;
import net.zfp.view.CharityView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class CharityServiceImpl implements CharityService {
	@Resource
    private SegmentService segmentService;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<Fundraiser> fundraiserDao;
	@Resource
	private EntityDao<Offer> offerDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CharityView getFundraiser(Long charityId, String domainName, Long memberId){
		Community community = communityDao.getCommunity(domainName);
		
		Fundraiser charity = fundraiserDao.findById(charityId);
		if (charity != null){
			CharityView cv = new CharityView(charity);
			List<Offer> offers = getDonateOffer(charity.getId(), community.getId(), new Date(), memberId);
			
			if (!offers.isEmpty()){
				Offer offer = offers.get(0);
				if (offer.getOfferValueType() != null && offer.getOfferValueType().getType().equals(AppConstants.OFFER_VALUE_TYPE_RATIO)){
					//Get Offer from offer value
					List<OfferValue> ovs = offerDao.getOfferValueByOfferAndCode(offer.getCode(), AppConstants.UNIT_DOLLAR);
					if (!ovs.isEmpty()){
						cv.setOfferValueType(AppConstants.OFFER_VALUE_TYPE_RATIO);
						cv.setOfferCoins(offer.getValue().intValue());
						cv.setOfferDollar(ovs.get(0).getValue());
					}
				}else{
					cv.setOfferValueType(AppConstants.OFFER_VALUE_TYPE_ABSOLUTE);
					cv.setOfferCoins(offer.getValue().intValue());
				}
				
			}
			return cv;
		}
		
		return new CharityView();
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<CharityView> getFundraisers(String domainName, Long memberId){
		System.out.println("Get Charities : " + new Date());
		List<CharityView> cvs = new ArrayList<CharityView>();
		List<Segment> segments = null;
		Community community = communityDao.getCommunity(domainName);
		
		if (memberId != null){
			segments = segmentService.getMemberSegment(memberId);
		}
		else{
			if (community != null) segments = segmentService.getDefaultSegment(community.getId());
		}
				
		if (!segments.isEmpty()){
			List<Fundraiser> charities = fundraiserDao.getCharityBySegments(segments, new Date());
			if (!charities.isEmpty()){
				for (Fundraiser charity : charities){
					CharityView cv = new CharityView(charity);
					List<Offer> offers = getDonateOffer(charity.getId(), community.getId(), new Date(),memberId);
					
					if (!offers.isEmpty()){
						Offer offer = offers.get(0);
						
						if (offer.getOfferValueType() != null && offer.getOfferValueType().getType().equals(AppConstants.OFFER_VALUE_TYPE_RATIO)){
							//Get Offer from offer value
							List<OfferValue> ovs = offerDao.getOfferValueByOfferAndCode(offer.getCode(), AppConstants.UNIT_DOLLAR);
							if (!ovs.isEmpty()){
								cv.setOfferValueType(AppConstants.OFFER_VALUE_TYPE_RATIO);
								cv.setOfferCoins(offer.getValue().intValue());
								cv.setOfferDollar(ovs.get(0).getValue());
							}
						}else{
							cv.setOfferValueType(AppConstants.OFFER_VALUE_TYPE_ABSOLUTE);
							cv.setOfferCoins(offer.getValue().intValue());
						}
						
					}
					
					cvs.add(cv);
				}
			}
			
		}
		
		return cvs;
	}
	
	private List<Offer> getDonateOffer(Long donationId, Long communityId, Date endDate, Long memberId){
		
		List<Segment> segments = null;
		
		if (memberId != null) segments = segmentService.getMemberSegment(memberId);
		else segments = segmentService.getDefaultSegment(communityId);
		
		
		List<Offer> offers = offerDao.getOfferByBehaviorActionsAndTrackerTypeAndAttributeId(AppConstants.BEHAVIOR_TRACKERTYPE_SALES_ORDER, AppConstants.BEHAVIOR_ACTION_DONATE, segments, donationId+"", endDate);
		
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
}
