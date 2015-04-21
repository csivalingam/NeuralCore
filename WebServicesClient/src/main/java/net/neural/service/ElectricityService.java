package net.zfp.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.entity.electricity.ElectricityRate;
import net.zfp.view.ElectricitySummaryView;
import net.zfp.view.ElectricityView;
import net.zfp.view.GaugeView;
import net.zfp.view.SourceSummaryView;


@Path("/electricity")
public interface ElectricityService {
	
	@POST
	@Path("summaries")
	@Produces("text/xml")
	List<SourceSummaryView> getUtilitySummaryData(@FormParam("accountId") Long accountId);

	@POST
	@Path("electricityConsumption")
	@Produces("text/xml")
	List<ElectricityView> getElectricityConsumptionData(@FormParam("serverName") String domainName, @FormParam("accountId") Long accountId, @FormParam("period") Integer period, @FormParam("isPrevious") Boolean isPrevious);
		
	@POST
	@Path("getelectricityconsumptiongauge")
	@Produces("text/xml")
	GaugeView getElectricityConsumptionGauge(@FormParam("domainName") String domainName, @FormParam("accountId") Long accountId, @FormParam("period") Integer period, @FormParam("viewType") Integer viewType);
	
	@POST
	@Path("getelectricityconsumptiongaugemobile")
	@Produces("text/xml")
	GaugeView getElectricityConsumptionGaugeMobile(@FormParam("communityCode") String communityCode, @FormParam("accountId") Long accountId, @FormParam("period") Integer period, @FormParam("viewType") Integer viewType);
	
	@POST
	@Path("electricityconsumptionmobile")
	@Produces("text/xml")
	List<ElectricityView> getElectricityConsumptionDataMobile(@FormParam("communityCode") String communityCode, @FormParam("accountId") Long accountId, @FormParam("period") Integer period, @FormParam("isPrevious") Boolean isPrevious);
	
	@POST
	@Path("electricityRate")
	@Produces("text/xml")
	List<ElectricityRate> getElectricityRates();
	
}
