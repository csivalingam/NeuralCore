package net.zfp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Translation;
import net.zfp.entity.account.AccountCampaign;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.category.Category;
import net.zfp.entity.community.Community;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.partner.BusinessPartner;
import net.zfp.entity.partner.BusinessPartnerType;
import net.zfp.service.UtilityService;
import net.zfp.util.AppConstants;
import net.zfp.view.PartnerView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Resource
public class PartnerServiceImpl implements PartnerService {
	@Resource
    private UtilityService utilityService;
	
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<Category> categoryDao;
	@Resource
	private EntityDao<AccountCampaign> accountCampaignDao;
	@Resource
	private EntityDao<Campaign> campaignDao;
	@Resource
	private EntityDao<BusinessPartner> businessPartnerDao;
	@Resource
	private EntityDao<Translation> translationDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<PartnerView> getBusinessPartners(String domainName){
		List<PartnerView> pvs = new ArrayList<PartnerView>();
		Community community = communityDao.getCommunity(domainName);
		
		BusinessPartnerType bpt = businessPartnerDao.getBusinessPartnerTypeByCode(AppConstants.BUSINESS_PARTNER_TYPE_ISSUANCE_SPONSOR);
		if (bpt != null){
			List<BusinessPartner> bps = businessPartnerDao.getBusinessPartnerByType(bpt.getId());
			if (bps != null && bps.size() > 0){
				for (BusinessPartner bp : bps){
					PartnerView pv = new PartnerView(bp);
					pvs.add(pv);
				}
			}
		}
		
		return pvs;
	}
}


