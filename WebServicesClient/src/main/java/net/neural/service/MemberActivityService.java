package net.zfp.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.zfp.entity.News;
import net.zfp.entity.User;
import net.zfp.entity.memberactivity.MemberActivity;
import net.zfp.view.MemberActivityView;
import net.zfp.view.NewsView;
import net.zfp.view.ResultView;

/**
 * Provide any information about member's activity in GOODcoins.
 * 
 * @author Youngwook Yoo
 * @since 5.0
 *
 */
@Path("/memberactivity")
public interface MemberActivityService {
	
	/**
	 * Add member's activity
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberid
	 * @param referenceCode
	 * @param reference
	 * @param activityType
	 * @param promoCode
	 * 
	 * @return true if member's activity is added, false otherwise
	 */
	@POST
	@Path("setmemberactivity")
	@Produces("application/json")
	ResultView setMemberActivity(@FormParam("memberid") Long memberid, @FormParam("referencecode") String referenceCode, @FormParam("reference") String reference, @FormParam("activitytype") String activityType, @FormParam("promocode") String promoCode);
	
	/**
	 * Get member's activity
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * @param activityType
	 * @param referenceCode
	 * 
	 * @return member's activity information
	 */
	@GET
	@Path("getmemberactivity")
	@Produces("application/json")
	MemberActivityView getMemberActivity(@QueryParam("memberid") Long memberId, @QueryParam("activitytype") String activityType, @QueryParam("referencecode") String referenceCode);
	
	//-----
	@Deprecated
	@POST
	@Path("ismemberactivityexists")
	@Produces("text/xml")
	ResultView isMemberActivityExists(@FormParam("domainName") String domainName, @FormParam("") User user, @FormParam("offerId") Long offerId, @FormParam("referenceCode") String referenceCode, @FormParam("reference") String reference, @FormParam("activityType") String activityType, @FormParam("date") Date activityDate);
	
	@POST
	@Path("updatememberactivity")
	@Produces("text/xml")
	ResultView updateMemberActivity(@FormParam("") MemberActivityView memberActivity);
	
}
