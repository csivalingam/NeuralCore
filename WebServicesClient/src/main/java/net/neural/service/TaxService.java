package net.zfp.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import net.zfp.entity.SourceType;
import net.zfp.entity.tips.Tips;
import net.zfp.view.TaxView;


@Path("/taxservice")
public interface TaxService {

	@POST
	@Path("calculateTax")
	@Produces("text/xml")
	TaxView calculateTax(@FormParam("domainName") String domainName, @FormParam("locationCode") String locationCode, @FormParam("subTotalAmount") Double subTotalAmount, @FormParam("locale") String locale);
	
}
