package net.zfp.service;

import javax.annotation.Resource;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Translation;
import net.zfp.entity.community.Community;
import net.zfp.entity.tax.TaxLocation;
import net.zfp.entity.tax.TaxValue;
import net.zfp.view.TaxView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class TaxServiceImpl implements TaxService {
	
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<Translation> translationDao;
	@Resource
	private EntityDao<TaxValue> taxValueDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public TaxView calculateTax(String domainName, String locationCode, Double subTotalAmount, String locale){
		TaxView tv = new TaxView();
		TaxLocation tl = taxValueDao.getTaxLocation(locationCode);
		TaxValue tvs = taxValueDao.getTaxValue(tl.getId());
		
		Community community = communityDao.getCommunity(domainName);
		Long communityId = community.getId();
		
		Double taxAmount = subTotalAmount * tvs.getTaxAmount();
		tv.setSubTotalAmount(subTotalAmount);
		tv.setTaxAmount(taxAmount);
		tv.setTaxType(tvs.getTaxType());
		try{
			String taxTranslation = translationDao.findTranslation(tvs.getTaxTypeTranslation(), communityId, 2l, locale, 3l).getTranslation();
			System.out.println(taxTranslation);
			System.out.println(locale);
			tv.setTaxType(taxTranslation);
			
		}catch (Exception e){
			//Its not a transaltion key.
			e.printStackTrace();
		}
		tv.setTotalAmount(subTotalAmount + taxAmount);
		return tv;
	}
	
}
