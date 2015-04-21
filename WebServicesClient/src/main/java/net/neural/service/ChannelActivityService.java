package net.zfp.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.form.MobileDeviceForm;
import net.zfp.view.ResultView;

/**
 * Provide any information about member's channel activity.
 * 
 * @author Youngwook Yoo
 * @since 5.0
 *
 */
@Path("/channelactivity")
public interface ChannelActivityService {
	
	/**
	 * Add mobile member's information
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param accountId
	 * @param OS
	 * @param hardwareModel
	 * @param phoneName
	 * @param carrier
	 * @param identifer
	 * @param appversion
	 * 
	 * @return success if information is saved, fail otherwise
	 */
	@POST
	@Path("setmobiledevice")
	@Produces("application/json")
	ResultView setMobileDeviceInfo(@FormParam("memberid") Long accountId, @FormParam("OS") String OS, @FormParam("hardwaremodel") String hardwareModel, @FormParam("phonename") String phoneName,
			@FormParam("carrier") String carrier, @FormParam("identifier") String identifer, @FormParam("appversion") String appversion);
	
	/**
	 * Add uploaded image
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param imageUrl
	 * @param offerCode
	 * 
	 * @return success if information is saved, fail otherwise
	 */
	@POST
	@Path("setuploadimage")
	@Produces("application/json")
	ResultView setUploadImage(@FormParam("imageurl") String imageUrl, @FormParam("offercode") Long offerCode);
	
	/**
	 * Add given member's channel activity
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param channelType
	 * @param memberId
	 * @param activityType
	 * 
	 * @return success if channel activity is saved, fail otherwise
	 */
	@POST
	@Path("setchannelactivity")
	@Produces("application/json")
	ResultView setChannelActivity(@FormParam("channeltype") String channelType, @FormParam("memberid") Long memberId, @FormParam("activitytype") String activityType);
	
	/**
	 * Send shared link to given emails
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param accountId
	 * @param emails
	 * @param url
	 * @param domain
	 * 
	 * @return success if email is sent, fail otherwise
	 */
	@POST
	@Path("sharelinkbyemail")
	@Produces("application/json")
	ResultView shareLinkByEmail(@FormParam("memberid") Long accountId, @FormParam("guestemails") String guestemails, @FormParam("members") String members, @FormParam("url") String url,  @FormParam("domain") String domain);
}
