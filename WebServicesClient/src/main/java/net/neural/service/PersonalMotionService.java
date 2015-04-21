package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.view.PersonalMotionDailyView;
import net.zfp.view.PersonalMotionView;
import net.zfp.view.SourceSummaryView;
import net.zfp.view.personalmotion.MonthlySummaryView;
import net.zfp.view.personalmotion.PersonalMotionDetailView;



@Path("/personalmotion")
public interface PersonalMotionService {

	@POST
	@Path("dailyPersonalMotion")
	@Produces("text/xml")
	List<PersonalMotionDailyView> getPersonalMotionDailyData(@FormParam("domainName") String domainName, @FormParam("accountId") Long accountId);
	
	/**
	 * Get personal motion data of specified Date for daily and monthly
	 * 
	 * @param accountId
	 * @param sourceTypeCode
	 * @param periodType
	 * @param year
	 * @param month (0-11)
	 * @param day
	 * @return
	 */
	@POST
	@Path("getpersonalmotiondata")
	@Produces("text/xml")
	PersonalMotionDailyView getPersonalMotionData(@FormParam("accountId") Long accountId, @FormParam("sourceTypeCode") String sourceTypeCode, @FormParam("periodType") String periodType,
			@FormParam("year") Integer year, @FormParam("month") Integer month, @FormParam("day") Integer day);
	
	@POST
	@Path("dailyPersonalSummary")
	@Produces("text/xml")
	List<SourceSummaryView> getPersonalMotionSummary(@FormParam("accountId") Long accountId, @FormParam("sourceTypeCode") String sourceTypeCode);
	
	/**
	 * Deprecated. Substitued with get Persoanl Motion Data
	 * @param accountId
	 * @param sourceTypeCode
	 * @return
	*/
	
	@POST
	@Path("halfDayPersonalSummary")
	@Produces("text/xml")
	PersonalMotionDailyView getHalfDayPersonalSummary(@FormParam("accountId") Long accountId, @FormParam("sourceTypeCode") String sourceTypeCode);
	 
	
	@POST
	@Path("getPastMonthSummary")
	@Produces("text/xml")
	List<PersonalMotionDetailView> getPastMonthSummary(@FormParam("accountId") Long accountId, @FormParam("walkingType") Integer walkingType);
	
}
