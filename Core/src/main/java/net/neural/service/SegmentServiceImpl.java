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
import net.zfp.entity.group.Groups;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.segment.SegmentType;
import net.zfp.util.AppConstants;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class SegmentServiceImpl implements SegmentService {

	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<Segment> segmentDao;
	@Resource
	private EntityDao<Groups> groupsDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Segment> getDefaultSegment(Long communityId){
		List<Segment> segments = new ArrayList<Segment>();
		
		//Get TYPE 1 ALL GOODCOINS SEGMENT
		System.out.println(AppConstants.SEGMENTTYPE_ALL_COMMUNITIES);
		SegmentType allGC = segmentDao.getSegmentType(AppConstants.SEGMENTTYPE_ALL_COMMUNITIES);
		segments.addAll(segmentDao.getSegmentsByType(allGC.getId()));
		
		//Get Parent Community Default Segment
		//Get Community Hierarchy
		//Get DEFAULT SEGMENT
		SegmentType communityDefault = segmentDao.getSegmentType(AppConstants.SEGMENTTYPE_COMMUNITY_DEFAULT);
		
		CommunityHierarchy position = communityDao.getCommunityHierarchyByCommunityId(communityId);
		Long parentId = communityId;
		for (int i = position.getDepth(); i >= 1; i--){
			
			CommunityHierarchy ch = communityDao.getCommunityHierarchyByCommunityId(parentId);
			segments.addAll(segmentDao.getSegmentsByTypeAndCommunity(communityDefault.getId(), ch.getCommunity().getId()));
			if (ch.getParent() != null){
				parentId = ch.getParent().getId();
			}
		}
		
		return segments;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Segment> getMemberSegment(User user){
		//System.out.println("Member Segment : " + new Date());
		
		List<Segment> segments = new ArrayList<Segment>();
		
		//Get TYPE 1 ALL GOODCOINS SEGMENT
		SegmentType allGC = segmentDao.getSegmentType(AppConstants.SEGMENTTYPE_ALL_COMMUNITIES);
		segments.addAll(segmentDao.getSegmentsByType(allGC.getId()));
		
		//Get Parent Community Default Segment
		//Get Community Hierarchy
		//Get DEFAULT SEGMENT
		Long communityId = user.getDefaultCommunity();
		SegmentType communityDefault = segmentDao.getSegmentType(AppConstants.SEGMENTTYPE_COMMUNITY_DEFAULT);
				
		CommunityHierarchy position = communityDao.getCommunityHierarchyByCommunityId(communityId);
		Long parentId = communityId;
		for (int i = position.getDepth(); i >= 1; i--){
					
			CommunityHierarchy ch = communityDao.getCommunityHierarchyByCommunityId(parentId);
			segments.addAll(segmentDao.getSegmentsByTypeAndCommunity(communityDefault.getId(), ch.getCommunity().getId()));
			if (ch.getParent() != null){
				parentId = ch.getParent().getId();
			}
		}
				
		//GET SEGMENT FROM ACCOUNT SEGMENT
		segments.addAll(segmentDao.getSegmentByAccount(user.getId()));
		
		/*
		 * Get group segment
		 */
		List<Groups> groups = groupsDao.getGroups(user.getId());
		if (!groups.isEmpty()){
			for(Groups group : groups){
				/*
				 * Get all segments in groupsSegment
				 */
				segments.addAll(segmentDao.getSegmentByGroup(group.getId()));
			}
		}
		
		return segments;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Segment> getMemberSegment(Long memberId){
		//System.out.println("Member Segment : " + new Date() + " : memberId " + memberId);
		
		List<Segment> segments = new ArrayList<Segment>();
		
		User user = userDao.findById(memberId);
				
		if (user == null)
			return segments;
		
		Community community = communityDao.findById(user.getDefaultCommunity());
		
		if (community.getCommunityType() == null || community.getCommunityType().getType().equals("CONSUMER")){
			//Get TYPE 1 ALL GOODCOINS SEGMENT
			SegmentType allGC = segmentDao.getSegmentType(AppConstants.SEGMENTTYPE_ALL_COMMUNITIES);
			segments.addAll(segmentDao.getSegmentsByType(allGC.getId()));
			
			//Get Parent Community Default Segment
			//Get Community Hierarchy
			//Get DEFAULT SEGMENT
			Long communityId = user.getDefaultCommunity();
			SegmentType communityDefault = segmentDao.getSegmentType(AppConstants.SEGMENTTYPE_COMMUNITY_DEFAULT);
			
			
			CommunityHierarchy position = communityDao.getCommunityHierarchyByCommunityId(communityId);
			Long parentId = communityId;
			int positionForDepth = 1;
			
			
			for (int i = position.getDepth(); i >= positionForDepth; i--){
								
				CommunityHierarchy ch = communityDao.getCommunityHierarchyByCommunityId(parentId);
				segments.addAll(segmentDao.getSegmentsByTypeAndCommunity(communityDefault.getId(), ch.getCommunity().getId()));
				if (ch.getParent() != null){
					parentId = ch.getParent().getId();
				}
			}
					
			//GET SEGMENT FROM ACCOUNT SEGMENT
			segments.addAll(segmentDao.getSegmentByAccount(memberId));
			
			/*
			 * Get group segment
			 */
			List<Groups> groups = groupsDao.getGroups(user.getId());
			if (!groups.isEmpty()){
				for(Groups group : groups){
					/*
					 * Get all segments in groupsSegment
					 */
					segments.addAll(segmentDao.getSegmentByGroup(group.getId()));
				}
			}
			
		}else if (community.getCommunityType().getType().equals("CORPORATE")){
			SegmentType communityDefault = segmentDao.getSegmentType(AppConstants.SEGMENTTYPE_COMMUNITY_CORPORATE);
			segments.addAll(segmentDao.getSegmentsByType(communityDefault.getId()));
		}
		
		return segments;
	}
}
