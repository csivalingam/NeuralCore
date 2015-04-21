package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.entity.PersonalFootprintSurvey;
import net.zfp.form.FootprintSurveyForm;
import net.zfp.view.FootprintView;
import net.zfp.view.GaugeView;
import net.zfp.view.HistoryView;
import net.zfp.view.LeaderboardView;
import net.zfp.view.PersonalDashboardView;
import net.zfp.view.ResultView;


@Path("/footprint")
public interface FootprintService {

	@POST
	@Path("gauge")
	@Produces("text/xml")
	GaugeView getGaugeData(@FormParam("serverName") String domainName, @FormParam("footprintTypeId") Long footprintTypeId);

	@POST
	@Path("leaderboard")
	@Produces("text/xml")
	List<LeaderboardView> getLeaderboardData(@FormParam("serverName") String domainName, @FormParam("sourceId") Long sourceId, @FormParam("footprintTypeId") Long footprintTypeId);
	
	@POST
	@Path("history")
	@Produces("text/xml")
	List<HistoryView> getHistoryData(@FormParam("serverName") String domainName, @FormParam("sourceId") Long sourceId);
	
	@POST
	@Path("saveSurvey")
	@Produces("text/xml")
	ResultView storeSurveyData(@FormParam("") FootprintSurveyForm surveyForm);
	
	@POST
	@Path("navigation")
	@Produces("text/xml")
	ResultView checkFootprintStep(@FormParam("serverName") String domainName, @FormParam("accountId") Long accountId);
	
	@POST
	@Path("myfootprint")
	@Produces("text/xml")
	List<FootprintView> getMyFootprint(@FormParam("serverName") String domainName, @FormParam("accountId") Long accountId, @FormParam("footprintTypeId") Long footprintTypeId);
	
	@POST
	@Path("getSurvey")
	@Produces("text/xml")
	PersonalFootprintSurvey getPersonalFootprintSurvey(@FormParam("serverName") String domainName, @FormParam("accountId") Long accountId);
	
	@POST
	@Path("getDashboardData")
	@Produces("text/xml")
	PersonalDashboardView getCarbonFootprint(@FormParam("serverName") String domainName, @FormParam("accountId") Long accountId);
	
	
}
