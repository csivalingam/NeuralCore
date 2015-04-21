package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import net.zfp.view.PartnerView;


@Path("/partner")
public interface PartnerService {

	@POST
	@Path("getBusinessPartners")
	@Produces("text/xml")
	List<PartnerView> getBusinessPartners(@FormParam("domainName") String domainName);
}