package net.zfp.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import net.zfp.entity.User;
import net.zfp.form.FlightForm;
import net.zfp.view.AirportListView;
import net.zfp.view.CalculaterResultView;
import net.zfp.view.UserView;

@Path("/calculater")
public interface CalculaterService {

	@POST
	@Path("airport")
	@Produces("text/xml")
	@Consumes("multipart/mixed")
	AirportListView getAllAirportList();

	@POST
	@Path("airport/{pre}")
	@Produces("text/xml")
	@Consumes("multipart/mixed")
	AirportListView getAirportList(@PathParam("pre") String pre);

	@POST
	@Path("carbon")
	@Produces("text/xml")
	CalculaterResultView getCarbonCalculater(@FormParam("") FlightForm flight);
	
}
