package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.view.DeviceView;
import net.zfp.view.ResultView;

@Path("/provider")
public interface ProviderService {
	
	/**
	 * getAllProviderDevices will return all provider devices that a member can see 
	 * 
	 * @author Youngwook Yoo
	 * @param accountId
	 * @param serverName
	 * @return provider devices views
	 */
	@POST
	@Path("getAllProviderDevices")
	@Produces("text/xml")
	List<DeviceView> getAllProviderDevices( @FormParam("accountId") Long accountId, @FormParam("serverName") String serverName);
	
	/**
	 * getProviderDevices will return provider devices with type specified that a member can see 
	 * 
	 * @author Youngwook Yoo
	 * @param accountId
	 * @param providerType
	 * @param serverName
	 * @return provider devices views
	 */
	@POST
	@Path("getProviderDevices")
	@Produces("text/xml")
	List<DeviceView> getProviderDevices( @FormParam("accountId") Long accountId, @FormParam("providerType") String providerType, @FormParam("serverName") String serverName);
	
	/**
	 * getProviderSourceName will return a specified provider source name if exists in database, otherwise returns failure results
	 * 
	 * @author Youngwook Yoo
	 * @param accountId
	 * @param sourceCode
	 * @param providerCode
	 * @param providerType
	 * @return Result code with sourcename in the result message if source exists.
	 */
	@POST
	@Path("getprovidersourcename")
	@Produces("text/xml")
	ResultView getProviderSourceName( @FormParam("accountId") Long accountId, @FormParam("sourceCode") String sourceCode, @FormParam("providerCode") String providerCode, @FormParam("providerType") String providerType);
	
	/**
	 * 
	 * Check if a member has source under provider
	 * @param accountId
	 * @param providerCode
	 * @param providerType
	 * @return returns success code if a member has a provider source, failure code if a member does not have a provider source
	 */
	@POST
	@Path("checkprovidersource")
	@Produces("text/xml")
	ResultView checkProviderSource( @FormParam("accountId") Long accountId, @FormParam("providerCode") String providerCode);
	
}
