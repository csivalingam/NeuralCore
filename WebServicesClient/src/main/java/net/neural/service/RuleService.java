package net.zfp.service;

import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.view.ResultView;


@Path("/zfprules")
public interface RuleService {

	@POST
	@Path("applycampaignrule")
	@Produces("text/xml")
	ResultView applyCampaignRule(@FormParam("memberId") Long memberId, @FormParam("campaignId") Long campaignId, @FormParam("date") Date date, @FormParam("references") String references);
	
}
