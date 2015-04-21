package net.zfp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Domain;
import net.zfp.entity.User;
import net.zfp.entity.community.Community;
import net.zfp.entity.community.CommunityHierarchy;
import net.zfp.util.AppConstants;
import net.zfp.view.CommunityView;
import net.zfp.view.CommunityViews;
import net.zfp.view.ResultView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class CommunityServiceImpl implements CommunityService {

	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<Community> communityDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CommunityViews getAllCommunities(String domainName){
		
		CommunityViews communityViews = new CommunityViews();
		List<CommunityView> cvs = new ArrayList<CommunityView>();
		
		List<Community> communities = communityDao.getAllCommunities();
		
		if (communities.isEmpty()) communities = new ArrayList<Community>();
		else{
			for (Community community : communities){
				CommunityView cv = new CommunityView(community);
				
				//Get domain Name
				Domain domain = domainDao.getDomainByCommunityId(community.getId());
				if (domain != null){
					cv.setDomain(domain.getName());
				}else{
					cv.setDomain(community.getName());
				}
				
				//Get National Level which is community hierarchy 1
				CommunityHierarchy position = communityDao.getCommunityHierarchyByCommunityId(community.getId());
				
				while(position.getDepth() != 1){
					position = communityDao.getCommunityHierarchyByCommunityId(position.getParent().getId());
					
				}
				cv.setNational(position.getCommunity().getCode());
				
				cvs.add(cv);
			}
		}
		
		communityViews.setCommunityViews(cvs);
		return communityViews;
	}
	
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setMyCommunity(String communityCode, Long memberId){
		System.out.println("Set My Community : " + new Date());
		ResultView rv = new ResultView();
		Community community = communityDao.getCommunityByCode(communityCode);
		
		User user = userDao.findById(memberId);
		
		if (user != null && community != null){
			user.setDefaultCommunity(community.getId());
			try{
				userDao.save(user, true);
				rv.fill(AppConstants.SUCCESS, "Successfully saved.");
			}catch(Exception e){
				e.printStackTrace();
				rv.fill(AppConstants.FAILURE, "Parameters are not invalid.");
			}
		}else{
			rv.fill(AppConstants.FAILURE, "Parameters are not invalid.");
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Community> getMyCommunityAndGlobal(@FormParam("domainName")String domainName){
		List<Community> communities = new ArrayList<Community>();
		
		Community community = communityDao.getCommunity(domainName);
		
		communities.add(community);
		
		//Get 
		CommunityHierarchy position = communityDao.getCommunityHierarchyByCommunityId(community.getId());
		
		while(position != null && position.getParent() != null){
			position = communityDao.getCommunityHierarchyByCommunityId(position.getParent().getId());
		}
		
		if (position != null && position.getDepth() == 0) communities.add(position.getCommunity());
		return communities;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CommunityViews getSubCommunities(String domainName){
		CommunityViews communityViews = new CommunityViews();
		
		if (domainName == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			communityViews.setResult(rv);
			
			return communityViews;
		}
		
		Community community = communityDao.getCommunity(domainName);
		if (community == null){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid domain name.");
			communityViews.setResult(rv);
			
			return communityViews;
		}
		
		List<CommunityHierarchy> subCommunityHierarchies = communityDao.getSubCommunityHierarchyByCommunityId(community.getId());
		
		List<CommunityView> cvs = new ArrayList<CommunityView>();
		for (CommunityHierarchy subCommunityHierarchy : subCommunityHierarchies){
			CommunityView communityView = new CommunityView(subCommunityHierarchy.getCommunity());
			cvs.add(communityView);
			
		}
		communityViews.setCommunityViews(cvs);
		
		return communityViews;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Community> getMyCommunities(String domainName){
		System.out.println("Get My Communities : " + new Date());
		
		List<Community> communities = new ArrayList<Community>();
		
		Community community = communityDao.getCommunity(domainName);
		
		communities.add(community);
		CommunityHierarchy position = communityDao.getCommunityHierarchyByCommunityId(community.getId());
		
		if (position != null && position.getParent() != null){
			Long parentId = position.getParent().getId();
			
			if (position != null){
				for (int i = position.getDepth(); i >= 0; i--){
					
					CommunityHierarchy ch = communityDao.getCommunityHierarchyByCommunityId(parentId);
					communities.add(ch.getCommunity());
					if (ch.getParent() != null){
						parentId = ch.getParent().getId();
					}else{
						break;
					}
				}
				
			}
		}
		
		return communities;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Community> getMyCommunitiesById(Long memberId){
		System.out.println("Get My Communities By Id : " + new Date());
		
		List<Community> communities = new ArrayList<Community>();
		
		User member = userDao.findById(memberId);
		Community community = communityDao.findById(member.getDefaultCommunity());
		
		communities.add(community);
		CommunityHierarchy position = communityDao.getCommunityHierarchyByCommunityId(community.getId());
		
		if (position != null && position.getParent() != null){
			Long parentId = position.getParent().getId();
			
			if (position != null){
				for (int i = position.getDepth(); i >= 0; i--){
					
					CommunityHierarchy ch = communityDao.getCommunityHierarchyByCommunityId(parentId);
					communities.add(ch.getCommunity());
					if (ch.getParent() != null){
						parentId = ch.getParent().getId();
					}else{
						break;
					}
				}
				
			}
		}
		
		return communities;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Community getMyCommunityById(Long memberId){
		System.out.println("Get My Community Id : " + new Date());
		User member = userDao.findById(memberId);
		Community community = communityDao.findById(member.getDefaultCommunity());
		
		return community;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public CommunityView getDomainCommunity(String domainName){
		CommunityView cv = null;
		if (domainName == null){
			
			cv = new CommunityView();
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			cv.setResult(rv);
			
			return cv;
		}
		
		Community community = communityDao.getCommunity(domainName);
		if (community == null){
			cv = new CommunityView();
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Invalid domain.");
			cv.setResult(rv);
			
			return cv;
		}
		
		cv = new CommunityView(community);
		
		//Get domain Name
		Domain domain = domainDao.getDomainByCommunityId(community.getId());
		if (domain != null) cv.setDomain(domain.getName());
		
		//Get National Level which is community hierarchy 1
		CommunityHierarchy position = communityDao.getCommunityHierarchyByCommunityId(community.getId());
		
		while(position.getDepth() != 1 && position.getParent() != null){
			position = communityDao.getCommunityHierarchyByCommunityId(position.getParent().getId());			
		}
		cv.setNational(position.getCommunity().getCode());
		
		return cv;
	}
	
}
