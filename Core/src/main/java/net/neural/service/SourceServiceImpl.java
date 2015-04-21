package net.zfp.service;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.zfp.dao.EntityDao;
import net.zfp.entity.AccountSource;
import net.zfp.entity.Source;
import net.zfp.entity.SourceType;
import net.zfp.entity.Status;
import net.zfp.entity.User;
import net.zfp.entity.category.Category;
import net.zfp.entity.source.SourceMode;
import net.zfp.util.AppConstants;
import net.zfp.view.ResultView;


public class SourceServiceImpl implements SourceService {
	
	@Resource
	private EntityDao<User> userDao;
	
	@Resource
	private EntityDao<Source> sourceDao;
	
	@Resource
	private EntityDao<Category> categoryDao;
	
	@Resource
	private EntityDao<Status> statusDao;
	
	@Resource
	private EntityDao<AccountSource> accountSourceDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView getMasterSourceId(Long memberId, String sourceTypeCode){
		ResultView rv = new ResultView();
		
		if (memberId == null || sourceTypeCode == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		
		User member = userDao.findById(memberId);
		if (member == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_MEMBER);
			return rv;
		}
		
		/*
		 * Check to see if a member has a master source of the particular source account
		 */
		Source masterSource = sourceDao.findMasterSourceByAccountAndType(memberId, sourceTypeCode);
		
		if (masterSource == null){
			/*
			 * Master source does not exists... create master source
			 */
			SourceType sourceType = sourceDao.getSourceTypeByCode(sourceTypeCode);
			SourceMode sourceMode = sourceDao.getSourceModeByCode(AppConstants.SOURCEMODE_CODE_MASTER);
			String categoryCode = AppConstants.CATEGORY_CODE_PERSONAL;
			/*
			 * Depends on the source type... get corresponding categories
			 */
			if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_CYCLING) || sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_RUNNING) ||
					sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_WALKING)){
				categoryCode = AppConstants.CATEGORY_CODE_PERSONAL;
			}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_ELECTRICITY)){
				categoryCode = AppConstants.CATEGORY_CODE_HOME;
			}else if (sourceTypeCode.equals(AppConstants.SOURCETYPE_CODE_DRIVING)){
				categoryCode = AppConstants.CATEGORY_CODE_DRIVING;
			}
				
			Category category = categoryDao.getCategoryByCode(categoryCode, AppConstants.CATEGORYTYPE_SOURCE);
			Status activeStatus = statusDao.getStatusByCode(AppConstants.STATUS_ACTIVE);
			
			String sourceCodeName = member.getFirstName().toUpperCase() + "_" + sourceTypeCode.toUpperCase() +"_MASTER_SOURCE";
			masterSource = new Source();
			masterSource.setName(member.getFirstName() + "'s " + sourceTypeCode.toLowerCase() + " master source");
			masterSource.setCode(sourceCodeName);
			masterSource.setSourceType(sourceType);
			masterSource.setSourceMode(sourceMode);
			masterSource.setSupressName(false);
			masterSource.setSupressDisplay(false);
			masterSource.setStatus(activeStatus);
			masterSource.setCategory(category);
			
			sourceDao.save(masterSource);
			System.out.println("Source created..." + masterSource.getId());
			masterSource = sourceDao.findSourceBySourceCode(sourceCodeName);
			
			System.out.println("Source has been findd... " + masterSource.getId());
			
			AccountSource accountSource = new AccountSource();
			accountSource.setUser(member);
			accountSource.setSource(masterSource);
			accountSource.setStatus(activeStatus);
			accountSourceDao.save(accountSource);
		}
		
		/*
		 * At this point, the master source should not be empty...
		 */
		
		rv.fill(AppConstants.SUCCESS, masterSource.getId()+"");
		
		return rv;
	}
	
}
