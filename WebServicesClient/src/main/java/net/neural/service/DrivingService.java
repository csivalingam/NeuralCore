package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.view.DrivingSummaryView;
import net.zfp.view.DrivingView;
import net.zfp.view.PersonalMotionDailyView;
import net.zfp.view.SourceSummaryView;



@Path("/driving")
public interface DrivingService {

	/**
	 * 
	 * Deprecated. substitude with 
	 * Get Half day driving data
	 * 
	 * Returns last 12 hours of worth driving data
	 * 
	 * @param memberId
	 * @return
	
	@POST
	@Path("gethalfdaydrivingdata")
	@Produces("text/xml")
	List<DrivingView> getHalfDayDrivingData(@FormParam("memberId") Long memberId);
	 */
	
	/**
	 * Get driving data of specified Date for daily and monthly
	 * 
	 * @param accountId
	 * @param periodType
	 * @param year
	 * @param month (0-11)
	 * @param day
	 * @return
	 */
	
	@POST
	@Path("dailyDrivingRecords")
	@Produces("text/xml")
	List<DrivingSummaryView> getDrivingDailyData(@FormParam("domainName") String domainName, @FormParam("accountId") Long accountId);
	
	@POST
	@Path("dailyDrivingSummary")
	@Produces("text/xml")
	List<SourceSummaryView> getDrivingSummary(@FormParam("accountId") Long accountId);
}
