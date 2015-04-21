package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.entity.provider.Provider;
import net.zfp.form.UtilityForm;
import net.zfp.view.ResultView;

/**
 * 
 * @author Youngwook Yoo
 * @since 2014-09-30
 * 
 */
@Path("/connector")
public interface ConnectorService {

	/**
	 * Store personal motion connector to database
	 * 
	 * It links between member and personal motion source to database. 
	 *
	 * @param memberId
	 * @param deviceName
	 * @param authorizationCode
	 * @param providerCode
	 * @return true if successful. Otherwise false
	 */
	@POST
	@Path("personalmotionconnector")
	@Produces("application/json")
	ResultView storeWalkingConnector(@FormParam("memberid") Long memberId, @FormParam("devicename") String deviceName, @FormParam("authorizationcode") String authorizationCode, @FormParam("providercode") String providerCode);
	
	/**
	 * Store running connector to database
	 * 
	 * It links between member and running source to database.
	 * 
	 * @param memberId
	 * @param deviceName
	 * @param authorizationCode
	 * @param providerCode
	 * @return true if successfully. Otherwise false
	 */
	@POST
	@Path("runningconnector")
	@Produces("application/json")
	ResultView storeRunningConnector(@FormParam("memberid") Long memberId, @FormParam("devicename") String deviceName, @FormParam("authorizationcode") String authorizationCode, @FormParam("providercode") String providerCode);
	
	//--------------------------------------------
	
	@POST
	@Path("moves")
	@Produces("text/xml")
	ResultView storeMovesCode(@FormParam("domainName") String domainName, @FormParam("accountId") Long accountId, @FormParam("deviceName") String deviceName, @FormParam("authorizationCode") String authorizationCode);
	
	@POST
	@Path("storeElectricityConnector")
	@Produces("text/xml")
	ResultView storeElectricityConnector(@FormParam("domainName") String domainName, @FormParam("memberId") Long memberId, @FormParam("deviceName") String deviceName, @FormParam("authorizationCode") String authorizationCode, @FormParam("providerCode") String providerCode);
	
	
	@POST
	@Path("utilityConnector")
	@Produces("text/xml")
	ResultView storeUtilityConnector(@FormParam("") UtilityForm utilityForm);
	
	//Deprecated
	@POST
	@Path("findUtilitiyProvider")
	@Produces("text/xml")
	List<Provider> findUtilitiyProvider(@FormParam("providerType") String providerType);
	
	@POST
	@Path("isUtilityConnectorRequested")
	@Produces("text/xml")
	ResultView isUtilityConnectorRequested(@FormParam("accountId") Long accountId, @FormParam("utilityType") String type);
	
	@POST
	@Path("isSourceConnected")
	@Produces("text/xml")
	ResultView isSourceConnected(@FormParam("accountId") Long accountId, @FormParam("sourceTypeCode") String sourceTypeCode, @FormParam("categoryCode") String categoryCode);
	
}
