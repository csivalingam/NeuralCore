package net.zfp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Domain;
import net.zfp.entity.Source;
import net.zfp.entity.SourceType;
import net.zfp.entity.User;
import net.zfp.entity.carbon.CarbonFootprint;
import net.zfp.entity.tips.Tips;
import net.zfp.service.TipService;
import net.zfp.util.AppConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class TipServiceImpl implements TipService {

	private static Log LOG = LogFactory.getLog(UserServiceImpl.class.getName());
	
	@Resource
	private EntityDao<Tips> tipDao;
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<CarbonFootprint> carbonFootprintDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public SourceType test(){
		System.out.println("Test : " + new Date());
		SourceType sourceType = new SourceType();
		
		sourceType.setId(2l);
		sourceType.setName("Hello Me");
		sourceType.setStandardUnit("12231");
		
		System.out.println("Source Type name : " + sourceType.getName());
		return sourceType;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<SourceType> getAllSupportedSourceTypeByAccount(String domainName, Long tipCategoryId, String accountEmail){
		
		//Get communityId
		Long communityId = getCommunityId(domainName);
		//Get AccountId
		User user = userDao.findByNameCommunity(accountEmail);
		Long accountId = user.getId();
		
		int category = AppConstants.CATEGORY_TYPE_CORPORATE_FOOTPRINT;
		
		//In AccountSource table, check if there are any sources associated with account
		List<Source> sources = userDao.findSourceByAccount(accountId);
		Long sourceId = null;
		for (Source source : sources){
			System.out.println("Source :: " + source.getName() + " From Source ::  Category :: " + category);
			if (source.getCategory().getCategoryType().getId() == category){
				sourceId = source.getId();
				System.out.println("***********************************************Match *********************************" + sourceId);
				break;
			}
		}
		if (sourceId == null) return null;
		
		//Get All sourceType
		List<SourceType> sourceTypes = carbonFootprintDao.getSourceTypesFromCarbonFootprint(sourceId, communityId, tipCategoryId);
		List<SourceType> sourceNames = new ArrayList<SourceType>();
		SourceType allST = new SourceType();
		allST.setId(0l);
		allST.setName("All");
		sourceNames.add(allST);
		sourceNames.addAll(sourceTypes);
		return sourceNames;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Tips> getAllSupportedTipsByAccount(String domainName, Long tipCategoryId, String accountEmail){
		
		//Get communityId
		Long communityId = getCommunityId(domainName);
		//Get AccountId
		User user = userDao.findByNameCommunity(accountEmail);
		Long accountId = user.getId();
		
		int category = AppConstants.CATEGORY_TYPE_CORPORATE_FOOTPRINT;
		
		//In AccountSource table, check if there are any sources associated with account
		List<Source> sources = userDao.findSourceByAccount(accountId);
		Long sourceId = null;
		for (Source source : sources){
			System.out.println("Source :: " + source.getName() + " From Source ::  :: Category :: " + category);
			if (source.getCategory().getCategoryType().getId() == category){
				sourceId = source.getId();
				System.out.println("***********************************************Match *********************************" + sourceId);
				break;
			}
		}
		if (sourceId == null) return null;
		
		//Get All sourceType
		List<SourceType> sourceTypes = carbonFootprintDao.getSourceTypesFromCarbonFootprint(sourceId, communityId, AppConstants.FOOTPRINT_CORPORATE);
		
		List<Tips> tips = new ArrayList<Tips>();
		for (SourceType sourceType : sourceTypes){
			tips.addAll(tipDao.getAllTips(tipCategoryId, sourceType.getId()));
		}
		
		for(Tips tip : tips){
			
			if (tip.getImageUrl() == null || tip.getImageUrl().equals("")){
				tip.setImageUrl("/portal-core/images/common/tips/generic_tip.jpg");
			}
		}
		return tips;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<SourceType> getAllSupportedSourceNames(Long tipCategoryId){
		
		System.out.println("category ID :: " + tipCategoryId);
		List<SourceType> supportedSourceTypes = tipDao.getTipsSourceTypes(tipCategoryId);
		
		System.out.println("Size of supported :: " + supportedSourceTypes.size());
		
		if (supportedSourceTypes.size() > 0){
			List<SourceType> sourceNames = new ArrayList<SourceType>();
			SourceType allST = new SourceType();
			allST.setId(0l);
			allST.setName("All");
			sourceNames.add(allST);
			
			for (SourceType st : supportedSourceTypes){
				sourceNames.add(st);
			}
			return sourceNames;
		}
		
		
		
		System.out.println("This should not be seen :: TipServiceImpl line 42");
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Tips> getAllSupportedTips( Long tipCategoryId){
		System.out.println("*********** LIST of Tips");
		
		List<Tips> tips = tipDao.getAllTips(tipCategoryId);
		for(Tips tip : tips){
			
			if (tip.getImageUrl() == null || tip.getImageUrl().equals("")){
				tip.setImageUrl("/portal-core/images/common/tips/generic_tip.jpg");
			}
		}
		return tips;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Tips> getAllSupportedTips(Long tipCategoryId, Long sourceTypeId){
		
		List<Tips> tips = tipDao.getAllTips(tipCategoryId, sourceTypeId);
		
		return tips;
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
