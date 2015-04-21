package net.zfp.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.zfp.entity.PointsAccount;
import net.zfp.entity.User;
import net.zfp.entity.communication.CommunicationPreference;
import net.zfp.form.HouseholdForm;
import net.zfp.view.ResultView;
import net.zfp.view.TextView;
import net.zfp.view.UserView;

/**
 * Provide any information about member.
 * @author Youngwook Yoo
 * @since 5.0
 *
 */
@Path("/member")
public interface UserService {
	
	/**
	 * Sign up a given member to the system
	 * 
	 *  @author Youngwook Yoo
	 *  
	 * @param domain
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param postalcode
	 * @param birthYear
	 * @param gender
	 * @param mode
	 * @param locale
	 * @param portalName
	 * @return The member object
	 */
	@POST
	@Path("signup")
	@Produces("application/json")
	UserView signup(@FormParam("domain")String domain, @FormParam("firstname")String firstName, 
			@FormParam("lastname")String lastName, @FormParam("email")String email, @FormParam("password")String password, @FormParam("postalcode")String postalcode,
			@FormParam("birthyear")Integer birthYear, @FormParam("gender")Integer gender, @FormParam("mode")Integer mode, @FormParam("locale")String locale, @FormParam("portalName")String portalName);
	
	/**
	 * Sign up for a corporate member
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-12
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param communityCode
	 * @param rooms
	 * @param occupants
	 * @return a corporate member information
	 */
	@POST
	@Path("corporatesignup")
	@Produces("application/json")
	UserView corporateSignup(@FormParam("firstname")String firstName, @FormParam("lastname")String lastName, @FormParam("email")String email, @FormParam("password")String password,
			@FormParam("communitycode")String communityCode, @FormParam("rooms")Double rooms, @FormParam("occupants")Integer occupants);
	
	/**
	 * Sign in the user
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param domain
	 * @param email
	 * @param password
	 * @param mode
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * 
	 * @return true if email exists and given credential matches, false otherwise
	 */
	@POST
	@Path("signin")
	@Produces("application/json")
	UserView signin(@FormParam("domain")String domain, @FormParam("email")String email, @FormParam("password")String password, @FormParam("mode")Integer mode, @FormParam("firstname")String firstName, @FormParam("lastname")String lastName, @FormParam("gender")Integer gender);
	
	/**
	 * Add guest account with given information
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param domain
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param mode
	 * 
	 * @return true if guest account has been created, false otherwise
	 */
	@POST
	@Path("addguestprofile")
	@Produces("application/json")
	ResultView addTempProfile(@FormParam("domain")String domain, @FormParam("firstname")String firstName, 
			@FormParam("lastname")String lastName, @FormParam("email")String email, @FormParam("mode")Integer mode);
	
	/**
	 * Get given member profile
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * 
	 * @return member profile
	 */
	@GET
	@Path("getprofile")
	@Produces("application/json")
	UserView getMemberProfile(@QueryParam("memberid") Long memberId);
	
	/**
	 * Update member's profile
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * @param firstName
	 * @param lastName
	 * @param address1
	 * @param address2
	 * @param city
	 * @param country
	 * @param province
	 * @param postalCode
	 * 
	 * @return true if update is success, false otherwise
	 */
	@POST
	@Path("setprofile")
	@Produces("application/json")
	ResultView updateProfile(@FormParam("memberid")Long memberId, @FormParam("firstname")String firstName, @FormParam("lastname")String lastName, @FormParam("address1")String address1,
			@FormParam("address2")String address2, @FormParam("city")String city, @FormParam("country")String country, @FormParam("province")String province, @FormParam("postalcode")String postalCode);
	
	/**
	 * Update member's corporate profile
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-18
	 * 
	 * @param memberId
	 * @param firstName
	 * @param lastName
	 * @param rooms
	 * @param occupants
	 * 
	 * @return true if update is success, false otherwise
	 */
	@POST
	@Path("setcorporateprofile")
	@Produces("application/json")
	ResultView setCorporateProfile(@FormParam("memberid")Long memberId, @FormParam("rooms")Double rooms, @FormParam("occupants")Integer occupants);
	
	/**
	 * Get given member's setting
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * 
	 * @return member's setting
	 */
	@GET
	@Path("getsetting")
	@Produces("application/json")
	UserView getUser(@QueryParam("memberid") Long memberId);
	
	/**
	 * Update member preferences
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * @param emailAlert
	 * @param emailNewsletter
	 * @param emailOffer
	 * @param language
	 * @param communicationMode
	 * @param communicationEmail
	 * @param alternativeEmail
	 * @param mobilePhone
	 * @param houseArea
	 * @param occupants
	 * @param houseHeating
	 * @param waterHeating
	 * 
	 * @return true if update is success, false otherwise
	 */
	@POST
	@Path("setprofilesetting")
	@Produces("application/json")
	ResultView updatePreferences(@FormParam("memberid")Long memberId, @FormParam("emailalert")Boolean emailAlert, @FormParam("emailnewsletter")Boolean emailNewsletter, 
			@FormParam("emailoffer")Boolean emailOffer, @FormParam("lanauge")String language, @FormParam("communicationmode")Integer communicationMode, 
			@FormParam("communicationemail")String communicationEmail, @FormParam("alternativeemail")String alternativeEmail, @FormParam("mobilephone")String mobilePhone, @FormParam("housearea")String houseArea, @FormParam("occupants")Integer occupants, @FormParam("househeating")String houseHeating, @FormParam("waterheating")String waterHeating);
	
	/**
	 * Deactivate given member
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * 
	 * @return true if member is deactivated, false otherwise
	 */
	@POST
	@Path("deactivate")
	@Produces("application/json")
	ResultView deactivateAccount(@FormParam("memberid")Long memberId);
	
	/**
	 * Authenticating user without password. This service is for mobile gateway at the moment
	 * 
	 * @param memberId
	 * @param locale
	 * @author Youngwook Yoo
	 * @return user information
	 */
	@POST
	@Path("authenticatewithoutpassword")
	@Produces("application/json")
	UserView authenticateWithoutPassword(@FormParam("memberid")Long memberId, @FormParam("locale")String locale);
	
	/**
	 * Reset password for a user
	 * 
	 * @param uid
	 * @param oldPassword
	 * @param newPassword
	 * @return success if a parameter is good, false otherwise
	 */
	@POST
	@Path("resetpassword")
	@Produces("application/json")
	ResultView resetPassword(@FormParam("memberid")Long accountId, @FormParam("oldpassword")String oldPassword, @FormParam("newpassword")String newPassword);
	
	//---------------------
	@POST
	@Path("mobilesignup")
	@Produces("text/xml")
	UserView mobileSignup(@FormParam("")User user, @FormParam("communityCode")String communityCode, @FormParam("locale")String locale);
	
	@POST
	@Path("setMemberProfileImage")
	@Produces("text/xml")
	ResultView setMemberProfileImage(@FormParam("accountId")Long accountId, @FormParam("profileImageUrl")String profileImageUrl);
	
	
	
	@POST
	@Path("getMemberDomain")
	@Produces("application/json")
	TextView getMemberDomain(@FormParam("memberId")Long memberId);
	
	@POST
	@Deprecated
	@Path("changePassword")
	@Produces("text/xml")
	ResultView changePassword(@FormParam("accountId")Long accountId, @FormParam("oldPassword")String oldPassword, @FormParam("newPassword")String newPassword);
	
	@POST
	@Path("user")
	@Produces("text/xml")
	UserView getUser(@FormParam("domainName") String domainName, @FormParam("accountEmail")String accountEmail);
	
	@POST
	@Path("getAllUsersInCommunity")
	@Produces("text/xml")
	List<UserView> getAllUsersInCommunity(@FormParam("domainName") String domainName, @FormParam("accountId")Long accountId, @FormParam("excludeFriends")Boolean excludeFriends);
	
	
	
	@POST
	@Path("checkUpdate")
	@Produces("text/xml")
	ResultView checkAndUpdateUser(@FormParam("")User user, @FormParam("communityCode") String communityCode);
	
	@Path("{userId}/profileManagement/user/{operation}")
	@POST
	@Produces("text/xml")
	@Consumes("multipart/mixed")
	UserView createUpdateUser(@PathParam("userId") Long userId,User user,@PathParam("operation") String operation);
	
	@Path("{userId}/profileManagement/user/delete")
	@POST
	@Produces("text/xml")
	@Consumes("multipart/mixed")
	UserView deleteUser(@PathParam("userId") Long userId,User user);
	
	@POST
	@Path("setmemberhouseholdprofile")
	@Produces("text/xml")
	ResultView setMemberHouseholdProfile(@FormParam("")HouseholdForm householdForm);

}
