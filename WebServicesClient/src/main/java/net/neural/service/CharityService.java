package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.view.CharityView;


@Path("/charity")
public interface CharityService {

	@POST
	@Path("getfundraisers")
	@Produces("text/xml")
	List<CharityView> getFundraisers(@FormParam("domainname") String domainName, @FormParam("memberid") Long memberId);
	
	@POST
	@Path("getfundraiser")
	@Produces("text/xml")
	CharityView getFundraiser(@FormParam("charityid") Long charityId, @FormParam("domainname") String domainName, @FormParam("memberid") Long memberId);
}
