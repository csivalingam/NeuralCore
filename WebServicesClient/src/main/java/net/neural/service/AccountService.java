package net.zfp.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.zfp.entity.PointsAccount;
import net.zfp.view.PointsAccountView;
import net.zfp.view.ResultView;

/**
 * Provide any information about member's account.
 * 
 * @author Youngwook Yoo
 * @since 5.0
 *
 */

@Path("/account")
public interface AccountService {
	/**
	 * Get the total default and charity points balance of member
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * 
	 * @return points balance of member
	 */
	@GET
	@Path("getbalance")
	@Produces("application/json")
	PointsAccountView getBalance( @QueryParam("memberid")Long memberId);
	
	/**
	 * Issue points related to member activities that are not processed
	 * @author Youngwook Yoo
	 * 
	 * @return result of process issuance
	 */
	@POST
	@Path("processissuance")
	@Produces("application/json")
	ResultView processIssuance();
	
	/**
	 * Issue points related to given member's activities that are not processed
	 * @param accountId
	 * @author Youngwook Yoo
	 * @return result of process issuance
	 */
	@POST
	@Path("processissuancebyaccount")
	@Produces("application/json")
	ResultView processIssuanceByAccount(@FormParam("meberid") Long accountId);
	
	/**
	 * Check if given member has enough balance to redeem
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * @param defaultRedeemPoints
	 * @param charityRedeemPoints
	 *
	 * @return true if member has enough balance, otherwise false
	 */
	@GET
	@Path("isredeemable")
	@Produces("application/json")
	ResultView isRedeemable(@QueryParam("memberid") Long memberId, @QueryParam("defaultpoints") Integer defaultRedeemPoints,  @QueryParam("charitypoints") Integer charityRedeemPoints);
	
	@POST
	@Path("setmemberdefaultsource")
	@Produces("application/json")
	ResultView setMemberDefaultSource(@FormParam("memberid") Long memberId, @FormParam("sourcecode") String sourceCode);
	
	
	//---------------------
	
	@POST
	@Path("processIssuanceforrefermembership")
	@Produces("text/xml")
	ResultView processIssuanceForReferMembership(@FormParam("inviterId") Long inviterId, @FormParam("inviteeId") Long inviteeId);
	
	
}
