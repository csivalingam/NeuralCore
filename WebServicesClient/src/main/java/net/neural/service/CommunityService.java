package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.zfp.entity.community.Community;
import net.zfp.view.CommunityView;
import net.zfp.view.CommunityViews;
import net.zfp.view.ResultView;

/**
 * Community Service
 * 
 * @author youngwookyoo
 * @since 2014-09-03
 * 
 */
@Path("/community")
public interface CommunityService {
	
	/**
	 * Get the current community information
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-03
	 * @param domainName
	 * @return community information
	 */
	@GET
	@Path("getdomaincommunity")
	@Produces("application/json")
	CommunityView getDomainCommunity(@QueryParam("domain")String domainName);
	
	/**
	 * Get sub communities for the current domain
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-12
	 * @param domain
	 * @return a list of sub communities
	 */
	@GET
	@Path("getsubcommunities")
	@Produces("application/json")
	CommunityViews getSubCommunities(@QueryParam("domain")String domain);
	
	/**
	 * Get all communitieis listed in the database
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-15
	 * @param domainName
	 * @return a list of all communities
	 */
	@GET
	@Path("getallcommunities")
	@Produces("application/json")
	CommunityViews getAllCommunities(@QueryParam("domain")String domainName);
	
	//-----------------------------------------
	
	
	@POST
	@Path("getmycommunities")
	@Produces("text/xml")
	List<Community> getMyCommunities(@FormParam("domainName")String domainName);
	
	@POST
	@Path("getmycommunityandglobal")
	@Produces("text/xml")
	List<Community> getMyCommunityAndGlobal(@FormParam("domainName")String domainName);
	
	
	
	@POST
	@Path("getmycommunitybyid")
	@Produces("text/xml")
	Community getMyCommunityById(@FormParam("memberId")Long memberId);
	
	@POST
	@Path("getmycommunitiesbyid")
	@Produces("text/xml")
	List<Community> getMyCommunitiesById(@FormParam("memberId")Long memberId);
	
	@POST
	@Path("setmycommunity")
	@Produces("text/xml")
	ResultView setMyCommunity(@FormParam("communityCode")String communityCode, @FormParam("memberId")Long memberId);
	
}
