package net.zfp.service;

import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.zfp.entity.Feedback;
import net.zfp.entity.Image;
import net.zfp.entity.carbon.CarbonFootprint;
import net.zfp.entity.carbon.CarbonTransaction;
import net.zfp.entity.portal.PortalLayout;
import net.zfp.form.FootprintForm;
import net.zfp.form.TranslationForm;
import net.zfp.view.FeatureView;
import net.zfp.view.GaugeView;
import net.zfp.view.PortalLayoutView;
import net.zfp.view.ResultView;
import net.zfp.view.TrackingView;
import net.zfp.view.TranslationView;
import net.zfp.view.TravelTypeView;
import net.zfp.view.UserView;
import net.zfp.view.VehicleView;

@Path("/utility")
public interface UtilityService {
	
	/**
	 * Based on domain and portal type it returns corresponding portal layout
	 * 
	 * @author Youngwook Yoo
	 *
	 * @param domain
	 * @param portalType
	 * 
	 * @return Portal layout view for requested page
	 */
	@GET
	@Path("getportallayout")
	@Produces("application/json")
	PortalLayoutView getPortalLayout(
			@QueryParam("domain") String domain, 
			@QueryParam("portaltype") String portalType);
	
	
	//-----------------------------------------------
	@POST
	@Path("community")
	@Produces("text/xml")
	Long getCommuniyId(@FormParam("communityName") String communityName);

	@POST
	@Path("translation")
	@Produces("text/xml")
	TranslationView getTranslation(@FormParam("") TranslationForm translationFrom);
	
	@POST
	@Path("trackingId")
	@Produces("text/xml")
	TrackingView getTrackingId(@FormParam("domainName") String domainName);
	
	@POST
	@Path("translations")
	@Produces("text/xml")
	List<TranslationView> getTranslations(@FormParam("") TranslationForm translationFrom);
	
	@POST
	@Path("feedback")
	@Produces("text/xml")
	ResultView addFeedback(@FormParam("") Feedback feedback);
	
	@POST
	@Path("footprint")
	@Produces("text/xml")
	ResultView saveFootprint(@FormParam("") FootprintForm footprintForm);
	
	@POST
	@Path("sendBasicEmail")
	@Produces("text/xml")
	ResultView sendBasicEmail(@FormParam("emails") String emails,@FormParam("fromEmail") String fromEmail,@FormParam("fromName") String fromName, @FormParam("subject") String subject, @FormParam("content") String content, @FormParam("domainName") String domainName);
	
	@POST
	@Path("sendgroupinvitationemail")
	@Produces("text/xml")
	ResultView sendGroupInvitationEmail(@FormParam("emails") String emails, @FormParam("groupId") Long groupId, @FormParam("memeberId") Long memberId);
	
	@POST
	@Path("sendSponsorEmail")
	@Produces("text/xml")
	ResultView sendSponsorEmail(@FormParam("emails") String emails, @FormParam("groupId") Long groupId, @FormParam("programId") Long programId, @FormParam("fromEmail") String fromEmail,@FormParam("fromName") String fromName, @FormParam("subject") String subject, @FormParam("content") String content, @FormParam("domainName") String domainName);
	
	@POST
	@Path("updateStatus")
	@Produces("text/xml")
	ResultView updateStatus(@FormParam("emails") String emails, @FormParam("uid") String uid, @FormParam("groupId") Long groupId, @FormParam("programId") Long programId, @FormParam("statusId") Long statusId);
	
	@POST
	@Path("getVehicleTypes")
	@Produces("text/xml")
	List<VehicleView> getVehicleTypes(@FormParam("domainName") String domainName, @FormParam("portalName") String portalName, @FormParam("locale") String locale);
	
	@POST
	@Path("sourceChecks")
	@Produces("text/xml")
	ResultView isSourceExists(@FormParam("domainName") String domainName, @FormParam("accountEmail") String accountEmail, @FormParam("category") String category);
	
	@POST
	@Path("getTravelTypes")
	@Produces("text/xml")
	List<TravelTypeView> getTravelTypes(@FormParam("domainName") String domainName, @FormParam("portalName") String portalName, @FormParam("locale") String locale);
	
	@POST
	@Path("getFeatures")
	@Produces("text/xml")
	List<FeatureView> getFeatures(@FormParam("domainName") String domainName, @FormParam("portalName") String portalName);
	
	@POST
	@Path("getImage")
	@Produces("text/xml")
	Image getImage(
			@FormParam("imageName") String imageName,
			@FormParam("serverName") String serverName, 
			@FormParam("locale") String locale,
			@FormParam("portalId") Long portalId);

	@POST
	@Path("getImages")
	@Produces("text/xml")
	List<Image> getImages(
			@FormParam("serverName") String serverName, 
			@FormParam("locale") String locale,
			@FormParam("portalName") String portalName);

	
	
	@POST
	@Path("generateCarbonCertificate")
	@Produces("text/xml")
	String generateCarbonCertificate(@FormParam("id") Long id, @FormParam("name") String name, 
			@FormParam("value") String value, @FormParam("date") String date);

	@POST
	@Path("isvalidemail")
	@Produces("text/xml")
	ResultView isValidEmail(@FormParam("email") String email);
}
