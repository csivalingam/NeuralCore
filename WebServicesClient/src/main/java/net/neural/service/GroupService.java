package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.entity.EmailTemplate;
import net.zfp.view.ContactView;
import net.zfp.view.GroupDetailView;
import net.zfp.view.GroupView;
import net.zfp.view.GroupViews;
import net.zfp.view.LeaderboardView;
import net.zfp.view.RankView;
import net.zfp.view.ResultView;
import net.zfp.view.UserView;

/**
 * 
 * @author youngwookyoo
 * @since 2014-10-16
 * 
 */
@Path("/group")
public interface GroupService {

	/**
	 * Get member's groups
	 * 
	 * Groups including member created and member invited
	 * 
	 * @param accountId
	 * @return a list of groups
	 */
	@POST
	@Path("getmembergroups")
	@Produces("application/json")
	GroupViews getGroupList(@FormParam("memberid") Long accountId);
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	@POST
	@Path("getmembersingroup")
	@Produces("text/xml")
	List<ContactView> getMembersInGroup(@FormParam("memberId") Long memberId, @FormParam("groupId") Long groupId);
	
	@POST
	@Path("getEmailTemplate")
	@Produces("text/xml")
	List<EmailTemplate> getEmailTemplate(@FormParam("emailTemplateTypeId") Long emailTemplateTypeId,  @FormParam("memberId") Long memberId);
	
	@POST
	@Path("getMyGroupUsers")
	@Produces("text/xml")
	List<UserView> getMyGroupUsers(@FormParam("domainName") String domainName, @FormParam("accountId") Long accountId);
	
	@POST
	@Path("getGroupFriends")
	@Produces("text/xml")
	List<RankView> getGroupFriends(@FormParam("accountId") Long accountId, @FormParam("mode") String mode, @FormParam("period")Integer period);
	
	@POST
	@Path("getfriendsranked")
	@Produces("text/xml")
	List<RankView> getFriendsRanked(@FormParam("accountId") Long accountId, @FormParam("sourceTypeCode") String sourceTypeCode, @FormParam("periodType")String periodType
			, @FormParam("year")Integer year, @FormParam("month")Integer month, @FormParam("day")Integer day);
	
	@POST
	@Path("getCampaignGroupFriends")
	@Produces("text/xml")
	List<RankView> getCampaignGroupFriends(@FormParam("memberId") Long memberId, @FormParam("campaignId") Long campaignId, @FormParam("period")Integer period);
	
	@POST
	@Path("groupInfo")
	@Produces("text/xml")
	GroupDetailView getGroupInfo(@FormParam("groupId") Long groupId);
	
	@POST
	@Path("setaccountgroupstatus")
	@Produces("text/xml")
	ResultView setAccountGroupStatus(@FormParam("accountGroupId") Long accountGroupId, @FormParam("statusCode") String statusCode);
	
	@POST
	@Path("addGroupMember")
	@Produces("text/xml")
	ResultView addGroupMember(@FormParam("domainName") String domainName, @FormParam("groupId") Long groupId, @FormParam("memberIds") String memberIds);
	
	@POST
	@Path("saveGroupMember")
	@Produces("text/xml")
	ResultView saveGroupMember(@FormParam("domainName") String domainName, @FormParam("groupId") Long groupId, @FormParam("memberIds") String memberIds);
	
	
	@POST
	@Path("saveDefaultGroup")
	@Produces("text/xml")
	List<UserView> saveDefaultGroup(@FormParam("domain") String domain, @FormParam("memberid") Long memberId, @FormParam("guestemails") String guestemails, @FormParam("memberids") String ids);
	
	@POST
	@Path("validateGuest")
	@Produces("text/xml")
	List<UserView> validateGuest(@FormParam("domainName") String domainName, @FormParam("accountId") Long accountId, @FormParam("emails") String emails);
	
	@POST
	@Path("removegroup")
	@Produces("text/xml")
	ResultView removeGroup(@FormParam("groupId") Long groupId);
	
	@POST
	@Path("removegroupmember")
	@Produces("text/xml")
	ResultView removeGroupMember(@FormParam("groupId") Long groupId, @FormParam("memberIds") String memberIds);
	
	@POST
	@Path("createAndlinktoCampaign")
	@Produces("text/xml")
	ResultView createGroupAndLinkCampaign(@FormParam("domainName") String domainName, @FormParam("accountEmail") String accountEmail, @FormParam("campaignId") Long campaignId, @FormParam("emails") String emails);
	
	@POST
	@Path("saveGroup")
	@Produces("text/xml")
	ResultView saveGroup(@FormParam("groupId") Long groupId, @FormParam("groupName") String groupName, @FormParam("groupDescription") String groupDescription, @FormParam("imageUrl") String imageUrl, @FormParam("domainName") String domainName, @FormParam("memberId") Long memberId);
	
}
