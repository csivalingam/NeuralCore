package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.zfp.view.CampaignSummaryView;
import net.zfp.view.CampaignSummaryViews;
import net.zfp.view.CampaignViews;
import net.zfp.view.FundraiseView;
import net.zfp.view.GroupDetailView;
import net.zfp.view.GroupView;
import net.zfp.view.LeaderboardView;
import net.zfp.view.CampaignDailyView;
import net.zfp.view.CampaignDetailView;
import net.zfp.view.CampaignView;
import net.zfp.view.LeaderboardViews;
import net.zfp.view.ResultView;
import net.zfp.view.SourceSummaryView;
import net.zfp.view.SponsorView;
import net.zfp.view.mobile.MobileLeaderboardView;

/**
 * Provide any information about campaign.
 * 
 * @author Youngwook Yoo
 * @since 5.0
 *
 */
@Path("/campaign")
public interface CampaignService {
	
	/**
	 * Get member's campaigns based on campaign type mode
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param accountId
	 * @param campaignTypeMode
	 * 
	 * @return list of member's campaign
	 */
	@GET
	@Path("getmembercampaign")
	@Produces("application/json")
	CampaignViews getUserCampaigns(@QueryParam("memberid") Long accountId, @QueryParam("campaigntypemode") Long campaignTypeMode);

	/**
	 * Get any campaigns, based on campaign type mode, that member has not joined yet.
	 * 
	 * @author Youngwook Yoo
	 *  
	 * @param accountId
	 * @param campaignTypeMode
	 * 
	 * @return list of campaigns that member has not join
	 */
	@GET
	@Path("getnonmembercampaign")
	@Produces("application/json")
	CampaignViews getNonUserCampaigns(@QueryParam("memberid") Long accountId, @QueryParam("campaigntypemode") Long campaignTypeMode);
	
	/**
	 * Get all available campaigns based on domain and campaign type mode
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param domain
	 * @param campaignTypeMode
	 * 
	 * @return list of all campaigns
	 */
	@GET
	@Path("getallcampaign")
	@Produces("application/json")
	CampaignViews getAllCampaigns(@QueryParam("domain") String domain, @QueryParam("campaigntypemode") Long campaignTypeMode);
	
	/**
	 * Get given campaign's detail. 
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param campaignId
	 * @param accountId
	 * @param iteration
	 * 
	 * @return campaign detail
	 */
	@GET
	@Path("getcampaigndetail")
	@Produces("application/json")
	CampaignDetailView getCampaignDetail(@QueryParam("campaignid") Long campaignId, @QueryParam("memberid") Long accountId, @QueryParam("iteration") Integer iteration);

	/**
	 * Get leaderboard data set based on given member and campaign
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param campaignId
	 * @param accountId
	 * @param iteration
	 * 
	 * @return leaderboard data
	 */
	@GET
	@Path("getleaderboard")
	@Produces("application/json")
	LeaderboardViews getLeaderboardData(@QueryParam("campaignid") Long campaignId, @QueryParam("memberid") Long accountId, @QueryParam("iteration") Integer iteration);
	
	/**
	 * Get campaign source activity summary based on campaign and member
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param accountId
	 * @param campaignId
	 * 
	 * @return campaign activity summary
	 */
	@GET
	@Path("getsourcesummary")
	@Produces("application/json")
	CampaignSummaryViews getCampaignSourceSummary(@QueryParam("memberid") Long accountId, @QueryParam("campaignid") Long campaignId);
	
	/**
	 * Get campaign detail based on member
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param campaignId
	 * @param accountId
	 * 
	 * @return campaign detail
	 */
	@GET
	@Path("getdailycampaigndetail")
	@Produces("application/json")
	CampaignDailyView getCampaignDaily(@QueryParam("campaignid") Long campaignId, @QueryParam("memberid") Long accountId);
	
	/**
	 * Get limited amount of campaign based on the member or domain
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param domain
	 * @param memberid
	 * @param campaigntypemode
	 * @param topnumber
	 * 
	 * @return limited amount of campaigns
	 */
	@GET
	@Path("gettopcampaign")
	@Produces("application/json")
	CampaignViews getTopCampaigns(@QueryParam("domain")String domain, @QueryParam("memberid") Long memberid, @QueryParam("campaigntypemode") Long campaigntypemode, @QueryParam("topnumber") Integer topnumber);
	
	/**
	 * Calls when member trying to join the campaign. Connect member to campaign
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param accountId
	 * @param campaignId
	 * 
	 * @return true if connection is success, fail otherwise
	 */
	@POST
	@Path("setmembercampaign")
	@Produces("application/json")
	ResultView connectUserCampaign( @FormParam("memberid") Long accountId, @FormParam("campaignid") Long campaignId);
	
	//-------------
	
	@POST
	@Path("getUserActiveCampaigns")
	@Produces("text/xml")
	List<CampaignView> getUserActiveCampaigns(
			@FormParam("serverName") String serverName, @FormParam("accountId") Long accountId, @FormParam("campaignTypeMode") Long campaignTypeMode);
	
	@POST
	@Path("usercampaignmobile")
	@Produces("text/xml")
	List<CampaignView> getUserCampaignsMobile(
			@FormParam("communitycode") String communitycode, @FormParam("accountId") Long accountId, @FormParam("campaignTypeMode") Long campaignTypeMode);
	
	

	@POST
	@Path("checkGroupExists")
	@Produces("text/xml")
	ResultView checkGroupExists(@FormParam("accountEmail") String accountEmail, @FormParam("campaignId") Long campaignId);
	
	@POST
	@Path("addSponsorGroup")
	@Produces("text/xml")
	ResultView addSponsorGroup(@FormParam("accountEmail") String accountEmail, @FormParam("campaignId") Long campaignId, @FormParam("groupId") Long groupId);
	
	
	@POST
	@Path("getNonUserCampaignsMobile")
	@Produces("text/xml")
	List<CampaignView> getNonUserCampaignsMobile(
			@FormParam("communitycode") String communitycode, @FormParam("accountId") Long accountId, @FormParam("campaignTypeMode") Long campaignTypeMode);
	
	@POST
	@Path("campaignprogressmobile")
	@Produces("text/xml")
	CampaignDetailView getCampaignProgressMobile(
			@FormParam("campaignId") Long campaignId,  @FormParam("communitycode") String communitycode, @FormParam("accountId") Long accountId);
	
	@POST
	@Path("fundraisedCampaignDetail")
	@Produces("text/xml")
	CampaignDetailView getFundraisedCampaignDetail(@FormParam("accountCampaignId") Long accountCampaignId);
	
	
	
	@POST
	@Path("fundraising")
	@Produces("text/xml")
	FundraiseView getFundraiseCampaign(@FormParam("campaignId") Long campaignId, @FormParam("accountId") Long accountId);
	
	@POST
	@Path("leaderboardmobile")
	@Produces("text/xml")
	MobileLeaderboardView getLeaderboardDataMobile(@FormParam("campaignId") Long campaignId, @FormParam("accountId") Long accountId, @FormParam("topNumber") Integer topNumber);
	
	/**
	 * Get campaign summary based on the given iteration
	 * @author Youngwook Yoo
	 * @since 2014-09-02
	 * @param campaignId
	 * @param accountId
	 * @param iteration
	 */
	@POST
	@Path("getcampaignsumamry")
	@Produces("text/xml")
	CampaignDetailView getCampaignSummary(@FormParam("campaignId") Long campaignId, @FormParam("accountId") Long accountId, @FormParam("iteration") Integer iteration);
	
	@POST
	@Path("inviteSponsors")
	@Produces("text/xml")
	SponsorView getGroupDetail(@FormParam("serverName") String serverName, @FormParam("campaignId") Long campaignId, @FormParam("accountEmail") String accountEmail);
	
	@POST
	@Path("saveTarget")
	@Produces("text/xml")
	ResultView saveTarget(@FormParam("accountEmail") String accountEmail, @FormParam("campaignId") Long campaignId, @FormParam("target") Integer target, @FormParam("mode") Integer mode);
}
