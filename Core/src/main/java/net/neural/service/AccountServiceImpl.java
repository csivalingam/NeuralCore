package net.zfp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.AccountSource;
import net.zfp.entity.Account_Authority;
import net.zfp.entity.Domain;
import net.zfp.entity.News;
import net.zfp.entity.PointsAccount;
import net.zfp.entity.Source;
import net.zfp.entity.Status;
import net.zfp.entity.User;
import net.zfp.entity.UserRole;
import net.zfp.entity.account.AccountGroup;
import net.zfp.entity.memberactivity.MemberActivity;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.offer.OfferBehavior;
import net.zfp.entity.offer.OfferBonus;
import net.zfp.entity.offer.OfferPromoCode;
import net.zfp.entity.offer.OfferValue;
import net.zfp.entity.partner.BusinessPartner;
import net.zfp.entity.partner.PartnerActivityType;
import net.zfp.entity.partner.PartnerPointsAccount;
import net.zfp.entity.partner.PartnerPointsTransaction;
import net.zfp.entity.provider.ProviderSource;
import net.zfp.entity.salesorder.PointsTransaction;
import net.zfp.entity.salesorder.PointsTransactionType;
import net.zfp.entity.salesorder.SalesOrder;
import net.zfp.entity.salesorder.SalesOrderDetail;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.tips.Tips;
import net.zfp.entity.wallet.PointsType;
import net.zfp.notify.NotificationService;
import net.zfp.notify.impl.NotificationServiceImpl;
import net.zfp.util.AppConstants;
import net.zfp.view.PointsAccountView;
import net.zfp.view.ResultView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class AccountServiceImpl implements AccountService {

	@Resource
    private SegmentService segmentService;
	@Resource
	private EntityDao<Tips> tipDao;
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Offer> offerDao;
	@Resource
	private EntityDao<Source> sourceDao;
	@Resource
	private EntityDao<Status> statusDao;
	@Resource
	private EntityDao<SalesOrder> salesOrderDao;
	@Resource
	private EntityDao<MemberActivity> memberActivityDao;
	@Resource
	private EntityDao<Account_Authority> accountAuthorityDao;
	@Resource
	private EntityDao<PointsAccount> pointsAccountDao;
	@Resource
	private EntityDao<PartnerPointsAccount> partnerPointsAccountDao;
	@Resource
	private EntityDao<PartnerPointsTransaction> partnerPointsTransactionDao;
	@Resource
	private EntityDao<PointsTransaction> pointsTransactionDao;
	@Resource
	private EntityDao<AccountSource> accountSourceDao;
	@Resource
	private EntityDao<ProviderSource> providerSourceDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setMemberDefaultSource(Long memberId, String sourceCode){
		ResultView rv = new ResultView();
		if (memberId == null || sourceCode == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		
		User member = userDao.findById(memberId);
		if (member == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
			return rv;
		}
		
		Source source = sourceDao.findSourceBySourceCode(sourceCode);
		if (source == null){
			rv.fill(AppConstants.FAILURE, "Invalid source code.");
			return rv;
		}
		
		/*
		 * Check to see which provider is the source
		 */
		ProviderSource provider = providerSourceDao.getProviderSourceBySourceId(source.getId());
		if (provider != null && (provider.getProvider().getCode().equals(AppConstants.PROVIDER_MOVES) || provider.getProvider().getCode().equals(AppConstants.PROVIDER_ZFP))){
			
			/*
			 * Get all provider sources
			 */
			List<Source> providerSources = providerSourceDao.getProviderSources(memberId, provider.getProvider().getId());
			if (!providerSources.isEmpty()){
				for (Source providerSource : providerSources){
					//System.out.println("accountSource " + providerSource.getSourceType() + " code : " + providerSource.getCode());
					getAccountSource(memberId, providerSource, rv);
				}
			}
		}else{
			getAccountSource(memberId, source, rv);
		}
		
		
		
		
		return rv;
	}
	
	private void getAccountSource(Long memberId, Source source, ResultView rv){
		
		/*
		 * Disable all the account sources with source type code
		 */
		setMemberSourcesNonDefault(memberId, source.getSourceType().getCode());
		
		/*
		 * Make this source code to be default
		 */
		AccountSource accountSource = accountSourceDao.findAccountSource(memberId, source.getId());
		if (accountSource != null){
			accountSource.setDefaultSource(true);
			accountSourceDao.save(accountSource, true);
		}
		rv.fill(AppConstants.SUCCESS, "Successfully updated default source.");
	}
	
	/**
	 * For member's specific sources, make them non default
	 * 
	 * @param memberId
	 * @param sourceTypeCode
	 */
	private void setMemberSourcesNonDefault(Long memberId, String sourceTypeCode){
		/*
		 * Get all member's account source
		 */
		List<AccountSource> accountSources = userDao.findAccountSourceByAccountAndType(memberId, sourceTypeCode);
		if (!accountSources.isEmpty()){
			for (AccountSource accountSource : accountSources){
				accountSource.setLastModified(new Date());
				accountSource.setDefaultSource(false);
				accountSourceDao.save(accountSource, true);
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public PointsAccountView getBalance(Long memberId){
		
		PointsAccountView pointsAccountView = new PointsAccountView();
		ResultView rv = new ResultView();
		
		if (memberId == null){
			rv.fill(AppConstants.FAILURE, "Invalid parameter");
			pointsAccountView.setResult(rv);
			pointsAccountView.setBalance(0);
			
			return pointsAccountView;
		}
		
		pointsAccountView.setMemberId(memberId);
		pointsAccountView.setBalance(0);
		
    	PointsType defaultPT = userDao.getPointsType(AppConstants.POINTS_TYPE_DEFAULT);
    	PointsType charityPT = userDao.getPointsType(AppConstants.POINTS_TYPE_CHARITY);
    	
    	PointsAccount defaultPA = userDao.getPointsAccount(memberId, defaultPT.getId());
    	PointsAccount charityPA = userDao.getPointsAccount(memberId, charityPT.getId());
    	
    	if (defaultPA != null){
    		
    		if (charityPA != null){
    			pointsAccountView.setBalance(defaultPA.getBalance() + charityPA.getBalance());
    		}else{
    			pointsAccountView.setBalance(defaultPA.getBalance());
    		}
    		
    		return pointsAccountView;
    	}else if (charityPA != null){
    			pointsAccountView.setBalance(charityPA.getBalance());
    			return pointsAccountView;
    	}
    	
    	return pointsAccountView;
    }
	
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView processIssuance(){
		ResultView rv = new ResultView();
		
		//Read all the member activity with not processed order by reference code and 
		List<MemberActivity> mas = memberActivityDao.getMemberActivities();
		//For each member activity check activity type
		if (mas != null && mas.size() > 0){
			for (MemberActivity ma : mas){
				offerEvaluation(ma, null);
				
			}
		}
			
		rv.fill(AppConstants.SUCCESS, "Successfully processed issuances");
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView processIssuanceByAccount(Long memberId){
		ResultView rv = new ResultView();
		
		List<MemberActivity> mas = memberActivityDao.getMemberActivitiesByUser(memberId);
		//For each member activity check activity type
		if (mas != null && mas.size() > 0){
			//System.out.println("BY ACCOUNT MEMBER ACTIVITY SIZE :: " + mas.size());
			for (MemberActivity ma : mas){
				offerEvaluation(ma, null);
			}
		}
		
		rv.fill(AppConstants.SUCCESS, "Successfully processed issuances");
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView isRedeemable(Long memberId, Integer defaultRedeemPoints, Integer charityRedeemPoints){
		ResultView rv = new ResultView();
		
		//System.out.println("IS REDEEMABLE :: " + memberId + " : " + defaultRedeemPoints + " : " + charityRedeemPoints);
		
		if (memberId == null){
			rv.fill(AppConstants.FAILURE, "Invalid parameter");
			return rv;
			
		}
		boolean isDefaultSuccess = true;
		
		PointsType defaultPT = pointsAccountDao.getPointsType(AppConstants.POINTS_TYPE_DEFAULT);
		PointsType charityPT = pointsAccountDao.getPointsType(AppConstants.POINTS_TYPE_CHARITY);
		
		PointsAccount charityPA = pointsAccountDao.getPointsAccount(memberId, charityPT.getId());
		PointsAccount defaultPA = pointsAccountDao.getPointsAccount(memberId, defaultPT.getId());
		
		if (defaultRedeemPoints == null) defaultRedeemPoints = 0;
		if (charityRedeemPoints == null) charityRedeemPoints = 0;
		
		if (defaultRedeemPoints > 0){
			//Get user's points
			if (defaultPA != null){
				if (defaultPA.getBalance() - defaultRedeemPoints >= 0){
					
					rv.fill(AppConstants.SUCCESS, "Redeemable");
					rv.setResultCode(AppConstants.SUCCESS);
				}else{
					
					rv.fill(AppConstants.FAILURE, "Not enough balance to redeem");
					rv.setResultCode(AppConstants.FAILURE);
					isDefaultSuccess = false;
				}
			}
				
		}else{
			rv.fill(AppConstants.FAILURE, "Not enough balance to redeem");
			rv.setResultCode(AppConstants.FAILURE);
			
		}
		
		
		if (isDefaultSuccess && charityRedeemPoints > 0){
			//Get user's points
			if (charityPA != null){
				if (charityPA.getBalance() - charityRedeemPoints >= 0){
					
					rv.fill(AppConstants.SUCCESS, "Redeemable");
					rv.setResultCode(AppConstants.SUCCESS);
				}else{
					if (defaultPA != null){
						
						if (defaultPA.getBalance() + charityPA.getBalance() - charityRedeemPoints >=0){
							rv.fill(AppConstants.SUCCESS, "Redeemable");
							rv.setResultCode(AppConstants.SUCCESS);
						}else{
							rv.fill(AppConstants.FAILURE, "Not enough balance to redeem");
							rv.setResultCode(AppConstants.FAILURE);
						}
					}else{
						rv.fill(AppConstants.FAILURE, "Not enough balance to redeem");
						rv.setResultCode(AppConstants.FAILURE);
					}
				}
			}else{
				
				//charity wallet is not exists then get default wallet and check if you can use the points
				if (defaultPA != null){
					
					if (defaultPA.getBalance() - charityRedeemPoints >=0){
						rv.fill(AppConstants.SUCCESS, "Redeemable");
						rv.setResultCode(AppConstants.SUCCESS);
					}else{
						rv.fill(AppConstants.FAILURE, "Not enough balance to redeem");
						rv.setResultCode(AppConstants.FAILURE);
					}
				}else{
					rv.fill(AppConstants.FAILURE, "Not enough balance to redeem");
					rv.setResultCode(AppConstants.FAILURE);
				}
			}
		}
		
		//System.out.println("result... " + rv.getResultCode() + " : " + rv.getResultMessage());
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView processIssuanceForReferMembership(Long inviterId, Long inviteeId){
		ResultView rv = new ResultView();
		
		//System.out.println("Inviter :: " + inviterId + " : inviteeId " + inviteeId);
		List<MemberActivity> mas = memberActivityDao.getMemberActivitiesByUserAndReference(inviterId, inviteeId, AppConstants.REFERENCECODE_MEMBERSHIP, AppConstants.MEMBERACTIVITY_TYPE_INVITE);
		//For each member activity check activity type
		if (!mas.isEmpty()){
			//System.out.println("mas" + mas.size());
			for (MemberActivity ma : mas){
				offerEvaluation(ma, AppConstants.MEMBERACTIVITY_TYPE_INVITE);
			}
		}else{
			//System.out.println("Cant find member activities");
		}
		
		return rv;
	}
	
	private void offerEvaluation(MemberActivity ma, String trigger){
		
		//System.out.println("Evaluating Offer " + ma.getReferenceCode());
		
		List<Offer> offers = new ArrayList<Offer>();
		
		//System.out.println(ma.getMemberActivityType().getCode());
		if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_SIGNUP)){
			
			OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_JOIN, AppConstants.BEHAVIOR_TRACKERTYPE_MEMBERSHIP);
			if (ob != null){
				
				//System.out.println("Found Signup Offer " + ob.getId());
				Offer offer = offerDao.getOfferByBehavior(ob.getId(), ma.getDate());
				if (offer != null){
					Boolean duplicated = checkDuplicateOffer(ma);
					if (duplicated){
						//System.out.println("Duplicated...");
						setProcessed(ma);
					}else{
						offers.add(offer);
					}
				}
			}
		}else if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_PURCHASE)){
			//Get UPC by
			List<String> UPCs = new ArrayList<String>();
			List<SalesOrderDetail> sods = salesOrderDao.getTransactionDetails(ma.getReference());
			if (sods != null && sods.size() > 0){
				for (SalesOrderDetail sod : sods){
					if (sod.getSalesOrderType().getType() == 3){
						UPCs.add(sod.getUPC());
					}
				}
			}
			if (UPCs.size() > 0){
				OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_BUY, AppConstants.BEHAVIOR_TRACKERTYPE_UPC);
				for (String UPC : UPCs){
					Offer offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), UPC, ma.getDate());
					if (offer != null){
						Boolean duplicated = checkDuplicateOffer(ma);
						if (duplicated){
							setProcessed(ma);
						}else{
							offers.add(offer);
						}
					}else{
						//If a specific offer was not existed... then look for base upc offer
						offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), "*", ma.getDate());
						if (offer != null){
							Boolean duplicated = checkDuplicateOffer(ma);
							if (duplicated){
								setProcessed(ma);
							}else{
								offers.add(offer);
							}
						}
					}
				}
			}
		}else if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_JOIN)){
			
			if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_JOIN_CAMPAIGN)){
								
				OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_JOIN, AppConstants.BEHAVIOR_TRACKERTYPE_CAMPAIGN);
				if (ob != null){
					Offer offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), ma.getReference()+"", ma.getDate());
					if (offer != null){
						Boolean duplicated = checkDuplicateOffer(ma);
						if (duplicated){
							setProcessed(ma);
						}else{
							offers.add(offer);
						}
					}
				}
			}
		}else if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_SHARE)){
			OfferBehavior ob = null;
			if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_SHARE_OFFER)){
				ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_SHARE, AppConstants.BEHAVIOR_TRACKERTYPE_OFFER);
				
			}else if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_SHARE_CAMPAIGN)){
				ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_SHARE, AppConstants.BEHAVIOR_TRACKERTYPE_CAMPAIGN);
			}else if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_SHARE_PRODUCT)){
				ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_SHARE, AppConstants.BEHAVIOR_TRACKERTYPE_PRODUCT);
			}
			
			if (ob != null){
				Offer offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), ma.getReference()+"", ma.getDate());
				if (offer != null){
					Boolean duplicated = checkDuplicateOffer(ma);
					if (duplicated){
						setProcessed(ma);
					}else{
						offers.add(offer);
					}
				}
			}
		}else if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_COMPLETE)){
			OfferBehavior ob = null;
			
			Boolean isSurvey = false;
			if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_COMPLETE_CAMPAIGN)){
				ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_COMPLETE_GOAL, AppConstants.BEHAVIOR_TRACKERTYPE_CAMPAIGN);
			}
			if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_SURVEY)){
				isSurvey = true;
				ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_COMPLETE, AppConstants.BEHAVIOR_TRACKERTYPE_SURVEY);
				
			}
			
			if (ob != null){
				Offer offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), ma.getReference()+"", ma.getDate());
				if (offer != null){
					Boolean duplicated = checkDuplicateOffer(ma);
					//System.out.println("Offer : " + offer.getName() +  " : " + isSurvey + " : " + duplicated);
					if (isSurvey && duplicated){
						setProcessed(ma);
					}else{
						offers.add(offer);
					}
				}
			}
		}else if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_WIN)){
			OfferBehavior ob = null;
			if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_WIN_MOST_CAMPAIGN)){
				ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_WIN_MOST, AppConstants.BEHAVIOR_TRACKERTYPE_CAMPAIGN);
			}else if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_WIN_LEAST_CAMPAIGN)){
				ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_WIN_LEAST, AppConstants.BEHAVIOR_TRACKERTYPE_CAMPAIGN);
			}
			
			if (ob != null){
				Offer offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), ma.getReference()+"", ma.getDate());
				if (offer != null){
					offers.add(offer);
					/*
					Boolean duplicated = checkDuplicateOffer(ma);
					if (duplicated){
						setProcessed(ma);
					}else{
						offers.add(offer);
					}
					*/
				}
			}
		}else if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_INVITE)){
			//NEED A TRIGGER
			if (trigger != null){
				if (trigger.equals(AppConstants.MEMBERACTIVITY_TYPE_INVITE)){
					OfferBehavior ob = null;
					
					if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_MEMBERSHIP)){
						ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_INVITE, AppConstants.BEHAVIOR_TRACKERTYPE_MEMBERSHIP);
					}
					
					if (ob != null){
						Offer offer = offerDao.getOfferByBehavior(ob.getId(), ma.getDate());
						if (offer != null){
							
							Boolean duplicated = checkDuplicateOffer(ma);
							if (duplicated){
								setProcessed(ma);
							}else{
								offers.add(offer);
							}
						}
					}
					
				}
			}
		}else if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_SIGNIN)){
			OfferBehavior ob = null;
			if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_MEMBERSHIP)){
				ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_SIGN_IN, AppConstants.BEHAVIOR_TRACKERTYPE_MEMBERSHIP);
			}
			if (ob != null){
				Offer offer = offerDao.getOfferByBehavior(ob.getId(), ma.getDate());
				if (offer != null){
					//Sign in can be everyday so no need to check duplicates
					offers.add(offer);
				}
			}
		}else if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_DONATE)){
			OfferBehavior ob = null;
			if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_SALES_ORDER)){
				ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_DONATE, AppConstants.BEHAVIOR_TRACKERTYPE_SALES_ORDER);
			}
			if (ob != null){
				Offer offer = offerDao.getOfferByBehavior(ob.getId(), ma.getDate());
				if (offer != null){
					//Donations shouldn't have to check duplicates
					offers.add(offer);
				}
			}
		}else if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_GOODWILL)){
			OfferBehavior ob = null;
			if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_GOODWILL)){
				ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_GOODWILL, AppConstants.BEHAVIOR_TRACKERTYPE_GOODWILL);
			}
			if (ob != null){
				Offer offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), ma.getReference()+"", ma.getDate());
				if (offer != null){
					//Donations shouldn't have to check duplicates
					offers.add(offer);
				}
			}
		}else if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_PARTICIPATE)){
			OfferBehavior ob = null;
			if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_CAMPAIGN)){
				ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_PARTICIPATE, AppConstants.BEHAVIOR_TRACKERTYPE_CAMPAIGN);
			}
			if (ob != null){
				Offer offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), ma.getReference()+"", ma.getDate());
				if (offer != null){
					Boolean duplicated = checkDuplicateOffer(ma);
					if (duplicated){
						setProcessed(ma);
					}else{
						offers.add(offer);
					}
				}
			}
		}
		/*
		else if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_UPLOAD)){
			if (ma.getReferenceCode().equals(AppConstants.REFERENCECODE_RECEIPT_UPLOAD)){
				OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_UPLOAD, AppConstants.BEHAVIOR_TRACKERTYPE_RECEIPT_IMAGE);
				Offer offer = offerDao.getOfferByBehaviorActionAndOfferCode(ob.getId(), ma.getOffer().getCode());
				
				if (offer != null){
					Boolean duplicated = checkDuplicateOffer(ma);
					if (duplicated){
						setProcessed(ma);
					}else{
						offers.add(offer);
					}
					
				}
			}
		}
		*/
		if (offers.size() > 0){
			for (Offer offer : offers){
				calculateIssuance(ma, offer);
			}
			
		}
		
	}
	
	private void setProcessed(MemberActivity ma){
		ma.setProcessed(true);
		memberActivityDao.save(ma, true);
	}
	
	private void suspendOffers(Long businessPartnerId){
		//getStatus Id for suspended
		Status suspended = statusDao.getStatusByCode(AppConstants.STATUS_SUSPENDED);
		//Get all offers from businessPartnerId
		List<Offer> offers = offerDao.getBusinessPartnerOffers(businessPartnerId);
		
		if (offers != null && offers.size() > 0){
			Date now = new Date();
			for (Offer offer : offers){
				offer.setLastModified(now);
				offer.setStatus(suspended);
				offerDao.save(offer, true);
			}
		}
	}
	private Boolean checkDuplicateOffer(MemberActivity ma){
		List<MemberActivity> processedMA = memberActivityDao.checkDuplicates(ma);
		if (processedMA != null && processedMA.size() > 0){
			return true;
		}
		
		return false;
	}
	
	private void calculateIssuance(MemberActivity ma, Offer offer){

		Integer offerPoints;
		
		//System.out.println("Calculating the offer " + offer.getCode());
		//memberActivityDao.save(ma, true);
		
		offerPoints = offer.getValue().intValue();
		
		//If ma type is win count integer how many people won and split the prize
		if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_WIN)){
			//Count how many winners are there
				//same activitytype, referencecode, reference
			List<MemberActivity> mas = memberActivityDao.getMemberActivitiesByTypandReferenceCode(ma.getMemberActivityType().getId(), ma.getReferenceCode(), ma.getReference());
			if (mas != null & mas.size() > 0){
				offerPoints = offer.getValue().intValue()/mas.size();
			}
			
		}else if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_DONATE)){
			if (offer.getOfferValueType() == null || offer.getOfferValueType().getType().equals(AppConstants.OFFER_VALUE_TYPE_ABSOLUTE)){
				offerPoints = offer.getValue().intValue();
			}else if (offer.getOfferValueType().getType().equals(AppConstants.OFFER_VALUE_TYPE_RATIO)){
				offerPoints = offer.getValue().intValue();
				//Get Sales Order and check how much points I did
				Long salesOrderId = ma.getReference();
				if (salesOrderId != null){
					SalesOrder so = salesOrderDao.findTransactionByOrderNumber(salesOrderId);
					if (so != null){
						List<OfferValue> ovs = offerDao.getOfferValueByOfferAndCode(offer.getCode(), AppConstants.UNIT_DOLLAR);
						if (!ovs.isEmpty()){
							offerPoints = (int)(Math.floor(so.getTotalCost()/(double)ovs.get(0).getValue()) * offerPoints);
						}
					}
				}
			}
		}else if (ma.getMemberActivityType().getCode().equals(AppConstants.MEMBERACTIVITY_TYPE_PURCHASE)){
			if (offer.getOfferValueType() == null || offer.getOfferValueType().getType().equals(AppConstants.OFFER_VALUE_TYPE_ABSOLUTE)){
				offerPoints = offer.getValue().intValue();
			}else if (offer.getOfferValueType().getType().equals(AppConstants.OFFER_VALUE_TYPE_RATIO)){
				offerPoints = offer.getValue().intValue();
				//Get Sales Order and check how much points I did
				Long salesOrderId = ma.getReference();
				if (salesOrderId != null){
					SalesOrder so = salesOrderDao.findTransactionByOrderNumber(salesOrderId);
					if (so != null){
						List<OfferValue> ovs = offerDao.getOfferValueByOfferAndCode(offer.getCode(), AppConstants.UNIT_DOLLAR);
						if (!ovs.isEmpty()){
							offerPoints = (int)(Math.floor(so.getTotalCost()/(double)ovs.get(0).getValue()) * offerPoints);
						}
					}
				}
			}
		}
		
		issuePoints(ma, offerPoints, offer, null);
		
		Integer bonusOfferPoints = 0;
		//TODO:Check membership to see if offer points can be changed
		String promoCode = null;
		if (ma.getPromoCode() != null) promoCode = ma.getPromoCode().toUpperCase();
		
		if (promoCode != null){
			
			//check
			//Check if the promoCode exists in the promocode
			OfferPromoCode opc = offerDao.getOfferPromoCode(promoCode.toUpperCase(), ma.getDate());
			
			if (opc != null && opc.getOfferBonus() != null){
				OfferBonus offerBonus = opc.getOfferBonus();
				if (offerBonus != null && offerBonus.getOperator() != null && offerBonus.getBonusValue() != null){
					if (offerBonus.getOperator().getOperator().equals("+")){
						bonusOfferPoints += offerBonus.getBonusValue();
					}else if (offerBonus.getOperator().getOperator().equals("*")){
						bonusOfferPoints = offerPoints * offerBonus.getBonusValue();
					}else if (offerBonus.getOperator().getOperator().equals("%")){
						bonusOfferPoints = offerPoints * (int)((double)offerBonus.getBonusValue()/100.0);
					}
					
					issuePoints(ma, bonusOfferPoints, offer, offerBonus.getId()+"");
				}
			}
		}
		
		bonusOfferPoints = 0;
		
		//Check if an offer has bonus offer 
		OfferBonus offerBonus = offerDao.getOfferBonus(offer.getId(), ma.getDate(), false);
		if (offerBonus != null && offerBonus.getOperator() != null && offerBonus.getBonusValue() != null){
			if (offerBonus.getOperator().getOperator().equals("+")){
				bonusOfferPoints += offerBonus.getBonusValue();
			}else if (offerBonus.getOperator().getOperator().equals("*")){
				bonusOfferPoints = offerPoints * offerBonus.getBonusValue();
			}else if (offerBonus.getOperator().getOperator().equals("%")){
				bonusOfferPoints = offerPoints * (int)((double)offerBonus.getBonusValue()/100.0);
			}
			
			issuePoints(ma, bonusOfferPoints, offer, offerBonus.getId()+"");
		}
		
		
		
	}
	
	private void issuePoints(MemberActivity ma, Integer offerPoints, Offer offer, String reference){
		//Get User ID
		User user = ma.getUser();
		
		//Partner Points Transaction
		PointsType pointsType = offer.getPointsType() != null ? offer.getPointsType() : pointsAccountDao.getPointsType(AppConstants.POINTS_TYPE_DEFAULT);		
		PointsTransactionType ptt = pointsTransactionDao.getPointsTransactionType(AppConstants.POINTS_TRANSACTION_TYPE_ISSUANCE);
		
		//System.out.println("POINTS OFFER ::::: " + offer.getCode());
		//Offer tells me which business partner
		BusinessPartner bp = offer.getBusinessPartner();
		if (bp != null){
			PartnerActivityType pat = partnerPointsAccountDao.getPartnerActivityTypeByCode(AppConstants.PARTNERACTIVITY_TYPE_CODE_ISSUANCE);
			PartnerPointsAccount ppa = partnerPointsAccountDao.getPartnerPointsAccountByPartnerId(bp.getId(), pointsType.getId());
			
			//If Partner Points doesn't have this... then no transactions!
			if (ppa == null) return;
			
			//Created PartnerPointsTransaction
			PartnerPointsTransaction ppt = new PartnerPointsTransaction();
			ppt.setPointsTransactionType(ptt);
			ppt.setPartnerActivityType(pat);
			ppt.setPartnerPointsAccount(ppa);
			ppt.setAmount((double)offerPoints);
			ppt.setDate(new Date());
			ppt.setGLReference(offer.getCode());
			ppt.setUser(ma.getUser());
			
			partnerPointsTransactionDao.save(ppt);
					
			//Update PartnerPointsAccount
			Integer balance = ppa.getBalance();
			balance -= offerPoints;
			ppa.setBalance(balance);
			ppa.setLastModified(new Date());
			partnerPointsAccountDao.save(ppa, true);
					
			//Check if balance is lower than threshold
			if (balance <= ppa.getDepletionThreshold() || balance <= 0){
				//suspend the all offers under the business partner
				suspendOffers(bp.getId());
			}
						
							
		}
				
		//get points account from user
		PointsAccount pa = pointsAccountDao.getPointsAccount(user.getId(), pointsType.getId());
		if (pa != null){
			Integer points = pa.getBalance();
			points += offerPoints;
			pa.setLastModified(new Date());
			pa.setBalance(points);
			pointsAccountDao.save(pa, true);
			
		}else{
			pa = new PointsAccount();
			pa.setMembershipId(1l);
			pa.setUser(user);
			pa.setBalance(offerPoints);
			pa.setPointsType(pointsType);
			pointsAccountDao.save(pa);
			
			pa = pointsAccountDao.getPointsAccount(user.getId(), pointsType.getId());
		}
		
		//Save PointsTransaction
		PointsTransaction pt = new PointsTransaction();
		pt.setPointsTransactionType(ptt);
		pt.setPointsAccount(pa);
		pt.setMemberActivity(ma);
		pt.setOffer(offer);
		pt.setAmount(offerPoints);
		pt.setDate(new Date());
		if (reference == null) pt.setReference(System.currentTimeMillis() + "");
		else pt.setReference(reference);
		
		pointsTransactionDao.save(pt);
		
		setProcessed(ma);
		
	}
	
	
}
