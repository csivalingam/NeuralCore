package net.zfp.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.zfp.view.OfferView;
import net.zfp.view.OfferViews;
import net.zfp.view.ResultView;

/**
 * Provide any information about offer.
 * 
 * @author Youngwook Yoo
 * @since 5.0
 *
 */
@Path("/offer")
public interface OfferService {
	
	/**
	 * Check if given offer is valid on given date
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param offercode
	 * @param Date
	 * 
	 * @return true if offer is valid, false otherwise
	 */
	@GET
	@Path("checkvalidoffer")
	@Produces("application/json")
	ResultView checkValidOffer(@QueryParam("offercode") Long offercode, @QueryParam("date") Date Date);
	
	/**
	 * Check if given offer is valid on given date
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param offercode
	 * @param Date
	 * 
	 * @return true if offer is valid, false otherwise
	 */
	@GET
	@Path("checkmemberqualification")
	@Produces("application/json")
	ResultView checkMemberQualification(@QueryParam("memberid") Long memberId, @QueryParam("action") String action, @QueryParam("trackertype") String trackerType, @QueryParam("date") Date date);
	
	/**
	 * Get given offer's detail. If member ID is passed then member oriented offer detail will be returned 
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param offerCode
	 * @param accountId
	 * 
	 * @return offer detail
	 */
	@GET
	@Path("getofferdetail")
	@Produces("application/json")
	OfferView getOffer(@QueryParam("offercode") Long offerCode, @QueryParam("memberid") Long accountId);
	
	/**
	 * Get offer detail using offer's behavior action and tracker type
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * @param attribute
	 * @param action
	 * @param trackerType
	 * 
	 * @return selected offer
	 */
	@GET
	@Path("getofferbybehavior")
	@Produces("application/json")
	OfferView getOfferByBehavior(@QueryParam("memberid") Long memberId, @QueryParam("attribute") String attribute, @QueryParam("action") String action, @QueryParam("trackertype") String trackerType);
	
	/**
	 * Get all offers based on offer type mode. All offers will be provided if offer type mode is 0
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param domain
	 * @param offerTypeMode
	 * @param accountId
	 * 
	 * @return list of offers based on offer type
	 */
	@GET
	@Path("getoffer")
	@Produces("application/json")
	OfferViews getAllOffers(@QueryParam("domain") String domain, @QueryParam("offertypemode") Long offerTypeMode, @QueryParam("memberid") Long accountId);
	
	/**
	 * Get limited number of offers based on offer type mode. All offers will be provided if offer type mode is 0
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param domain
	 * @param memberId
	 * @param offerTypeMode
	 * @param topNumber
	 * 
	 * @return limited number of offers
	 */
	@GET
	@Path("gettopoffer")
	@Produces("application/json")
	OfferViews getOffersByTopNumber(@QueryParam("domain") String domain,@QueryParam("memberid") Long memberId, @QueryParam("offertypemode") Long offerTypeMode, @QueryParam("topnumber") Integer topNumber);
	
	/**
	 * Get limited number of community offers.
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param communityCode
	 * @param topNumber
	 * 
	 * @return limited number of community offers
	 */
	@GET
	@Path("getcommunityoffer")
	@Produces("application/json")
	OfferViews getOffersByCommunity(@QueryParam("communitycode") String communityCode, @QueryParam("topnumber") Integer topNumber);
	
	//----------------
	
	//Deprecated
	@POST
	@Path("offers")
	@Produces("text/xml")
	List<OfferView> getOffers(@FormParam("domainName") String domainName, @FormParam("offerTypeName") String offerTypeName);
	
	
	//For Carbon
	@POST
	@Path("offersByCategory")
	@Produces("text/xml")
	List<OfferView> getOffersByCategoryType(@FormParam("domainName") String domainName, @FormParam("offerType") String offerType,  @FormParam("categoryType") String categoryType, @FormParam("locale") String locale);

	@POST
	@Path("getuploadoffers")
	@Produces("text/xml")
	List<OfferView> getUploadOffers(@FormParam("domainName") String domainName, @FormParam("memberId") Long memberId);
}