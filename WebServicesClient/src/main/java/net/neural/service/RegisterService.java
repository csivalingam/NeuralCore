package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.form.DeviceForm;
import net.zfp.view.DeviceView;
import net.zfp.view.ResultView;


@Path("/register")
public interface RegisterService {
	
	@POST
	@Path("registereddevice")
	@Produces("text/xml")
	List<DeviceView> getRegisteredDevice(@FormParam("domainName") String domainName, @FormParam("accountId") Long accountId, @FormParam("sourceType") String sourceType, @FormParam("categoryCode") String categoryCode);
	
	@POST
	@Path("removedevice")
	@Produces("text/xml")
	ResultView removeDevice(@FormParam("domainName") String domainName, @FormParam("accountId") Long accountId, @FormParam("sourceCode") String sourceCode );
	
	@POST
	@Path("adddevice")
	@Produces("text/xml")
	ResultView addDevice(@FormParam("") DeviceForm deviceForm);
	
	@POST
	@Path("adddevicemobile")
	@Produces("text/xml")
	ResultView addDeviceMobile(@FormParam("") DeviceForm deviceForm);
	
}