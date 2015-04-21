package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.entity.mobile.MobileDevice;
import net.zfp.form.MobileDeviceForm;
import net.zfp.view.ResultView;
import net.zfp.view.WhatsUpView;


@Path("/mobile")
public interface MobileService {
	
	@POST
	@Path("setmobiledeviceinfo")
	@Produces("text/xml")
	ResultView setMobileDeviceInfo(@FormParam("") MobileDeviceForm mobileDeviceForm);
	
}
