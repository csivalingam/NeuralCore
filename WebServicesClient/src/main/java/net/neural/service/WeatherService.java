package net.zfp.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.view.ResultView;


@Path("/weatherservice")
public interface WeatherService {

	@POST
	@Path("setweatheralerts")
	@Produces("text/xml")
	ResultView setWeatherAlerts(@FormParam("domainName") String domainName);
	
}
