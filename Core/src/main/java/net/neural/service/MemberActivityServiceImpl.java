package net.zfp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Domain;
import net.zfp.entity.News;
import net.zfp.entity.PortalType;
import net.zfp.entity.Source;
import net.zfp.entity.SourceType;
import net.zfp.entity.User;
import net.zfp.entity.carbon.CarbonFootprint;
import net.zfp.entity.memberactivity.MemberActivity;
import net.zfp.entity.memberactivity.MemberActivityType;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.tips.Tips;
import net.zfp.service.NewsService;
import net.zfp.util.AppConstants;
import net.zfp.view.MemberActivityView;
import net.zfp.view.NewsView;
import net.zfp.view.ResultView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class MemberActivityServiceImpl implements MemberActivityService {

	
	@Resource
	private EntityDao<Tips> tipDao;
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<MemberActivity> memberActivityDao;
	@Resource
	private EntityDao<Offer> offerDao;
	@Resource
	private EntityDao<PortalType> portalTypeDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView updateMemberActivity(MemberActivityView mav){
		System.out.println("Entered updateMemberActivity : " + new Date());
		ResultView rv = new ResultView();
		
		if (mav == null){
			rv.fill(AppConstants.FAILURE, "Not exists");
		}else{
			MemberActivity ma = memberActivityDao.findById(mav.getId());
			
			ma.setDate(mav.getDate());
			ma.setProcessed(mav.getProcessed());
			ma.setReference(mav.getReference());
			
			try{
				rv.fill(AppConstants.SUCCESS, ma.getReference()+"");
				memberActivityDao.save(ma, true);
			}catch(Exception e){
				e.printStackTrace();
				rv.fill(AppConstants.FAILURE, e.getMessage());
			}
			
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView isMemberActivityExists(String domainName, User user, Long offerId, String referenceCode, String reference, String activityType, Date activityDate){
		System.out.println("Entered isMemberActivityExists : " + new Date());
		ResultView rv = new ResultView();
		
		try{
			MemberActivity ma = memberActivityDao.getMemberActivitiesByUserAndReferenceAndDate(user.getId(), referenceCode, activityType, activityDate);
			if (ma != null){
				rv.fill(AppConstants.SUCCESS, ma.getReference()+"");
			}else{
				rv.fill(AppConstants.FAILURE, "Not exists");
			}
		}catch(Exception e){
			e.printStackTrace();
			rv.fill(AppConstants.FAILURE, "Not exists");
		}
		
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public MemberActivityView getMemberActivity(Long memberId, String activityType, String referenceCode){
		MemberActivityView memberActivityView = new MemberActivityView();
		ResultView rv = new ResultView();
		
		if (memberId == null || activityType == null || referenceCode == null){
			rv.fill(AppConstants.FAILURE, "Invalid parameter");
		}
		
		try{
			MemberActivity ma = memberActivityDao.getMemberActivitiesByUserAndReferenceCode(memberId, referenceCode, activityType);
			if (ma != null)
				memberActivityView = new MemberActivityView(ma);
			else
				rv.fill(AppConstants.FAILURE, "Member activity does not exist");
			
		}catch(Exception e){
			
			rv.fill(AppConstants.FAILURE, "Member activity does not exist");
		}
		
		memberActivityView.setResult(rv);
		
		return memberActivityView;
	}
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setMemberActivity(Long memberId, String referenceCode, String reference, String activityType, String promoCode){
		ResultView rv = new ResultView();
		
		if (memberId == null || referenceCode == null || reference == null){
			rv.fill(AppConstants.FAILURE, "Invalid parameters");
			return rv;
		}
		
		User activityUser = userDao.findById(memberId);
		
		if (activityUser == null){
			rv.fill(AppConstants.FAILURE, "User does not exist");
			return rv;
		}
		
		if (activityType == null){
		
			if (referenceCode.equals(AppConstants.REFERENCECODE_SALES_ORDER)) activityType = "PURCHASE";
			else if (referenceCode.equals(AppConstants.REFERENCECODE_RECEIPT_UPLOAD)) activityType = "UPLOAD";
			else if (referenceCode.equals(AppConstants.REFERENCECODE_SIGN_UP)) activityType = "SIGN_UP";
			else if (referenceCode.equals(AppConstants.REFERENCECODE_JOIN_CAMPAIGN)) activityType = "JOIN";
			else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_LINK)) activityType = "SHARE";
			else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_CAMPAIGN)) activityType = "SHARE";
			else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_OFFER)) activityType = "SHARE";
			else if (referenceCode.equals(AppConstants.REFERENCECODE_SHARE_PRODUCT)) activityType = "SHARE";
			else if (referenceCode.equals(AppConstants.REFERENCECODE_REDEEM)) activityType = "REDEEM";
			else if (referenceCode.equals(AppConstants.REFERENCECODE_SURVEY)) activityType = "COMPLETE";
			else if (referenceCode.equals(AppConstants.REFERENCECODE_MEMBERSHIP)) activityType = AppConstants.MEMBERACTIVITY_TYPE_SIGNIN;
		}
		
		MemberActivityType mat = memberActivityDao.getMemberActivityType(activityType);
		
		try{
			MemberActivity ma = new MemberActivity();
			ma.setMembershipId(1l);
			ma.setUser(activityUser);
			ma.setDate(new Date());
			ma.setMemberActivityType(mat);
			ma.setReferenceCode(referenceCode);
			ma.setReference(Long.parseLong(reference));
			ma.setPromoCode(promoCode);
			ma.setProcessed(false);
			ma.setCreatedById(0l);
			memberActivityDao.save(ma);
			
			//Fetch member activity...
			ma = memberActivityDao.getMemberActivityByUserAndReference(activityUser.getId(), Long.parseLong(reference), referenceCode, activityType);
			rv.fill(AppConstants.SUCCESS, ma.getId() +"");
		}catch(Exception e){
			e.printStackTrace();
			rv.fill(AppConstants.FAILURE, e.getMessage());
		}
		return rv;
	}
}
