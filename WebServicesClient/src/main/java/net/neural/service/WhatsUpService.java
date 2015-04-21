package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.zfp.entity.User;
import net.zfp.form.NewsForm;
import net.zfp.view.ResultView;
import net.zfp.view.WhatsUpView;

/**
 * Provide any information about membership feed and news.
 * 
 * @author Youngwook Yoo
 * @since 5.0
 *
 */
@Path("/membershipfeed")
public interface WhatsUpService {
	
	/**
	 * Get limited amount of membership feeds 
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param accountId
	 * @param page
	 * @param offset
	 * @param isMobile
	 * 
	 * @return membership news
	 */
	@GET
	@Path("getmembershipfeed")
	@Produces("application/json")
	WhatsUpView getWhatsUps(@QueryParam("memberid") Long accountId, @QueryParam("page") Integer page, @QueryParam("offset") Integer offset,  @QueryParam("mobile") Boolean isMobile);
	
	/**
	 * Set given alerts as viewed
	 * 
	 * @param alertId
	 * 
	 * @return true if alert has set viewed, false otherwise
	 */
	@POST
	@Path("setalertviewed")
	@Produces("application/json")
	ResultView setAlertViewed(@FormParam("alertid") Long alertId);
	
	/**
	 * Set member's community news
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * @param referenceCode
	 * @param reference
	 * 
	 * @return true if community news has saved, false otherwise
	 */
	@POST
	@Path("setcommunitynews")
	@Produces("application/json")
	ResultView setCommunityNews(@FormParam("memberid") Long memberId, @FormParam("referencecode") String referenceCode, @FormParam("reference") String reference);
	
	/**
	 * Set member's alert
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * @param heading
	 * @param news
	 * @param imageUrl
	 * @param mobileImageUrl
	 * @param trackerType
	 * @param referenceCode
	 * 
	 * @return true if alert news has saved, false otherwise
	 */
	@POST
	@Path("setalertnews")
	@Produces("application/json")
	ResultView setAlertNews(@FormParam("memberid") Long memberId, @FormParam("heading") String heading, @FormParam("news") String news,
			@FormParam("imageurl") String imageUrl, @FormParam("mobileimageurl") String mobileImageUrl, @FormParam("trackertype") Long trackerType, 
			@FormParam("referencecode") String referenceCode);
		
}
