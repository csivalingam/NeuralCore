package net.zfp.service;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.zfp.view.DrivingSummaryView;
import net.zfp.view.ElectricitySummaryView;
import net.zfp.view.PersonalMotionDailyView;
import net.zfp.view.SourceSummaryViews;
import net.zfp.view.activity.ActivityViews;

/**
 * Provide any information about member's activity tracking.
 * 
 * @author Youngwook Yoo
 * @since 5.0
 *
 */
@Path("/activity")
public interface ActivityService {
	
	/**
	 * Get member's all activity summaries
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * 
	 * @return detail views of each activity
	 */
	@GET
	@Path("getactivitysummary")
	@Produces("application/json")
	SourceSummaryViews getActivitySummary(@QueryParam("memberid") Long memberId, @QueryParam("activitytype") String activityType, @QueryParam("periodtype") String periodType,
			@QueryParam("year") Integer year, @QueryParam("month") Integer month, @QueryParam("day") Integer day);
	
	/**
	 * Get member's all activity summaries
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * 
	 * @return detail views of each activity
	 */
	@GET
	@Path("getallactivitysummary")
	@Produces("application/json")
	ActivityViews getActivities(@QueryParam("memberid") Long memberId);
	
	/**
	 * Get member's personal motion activity (walking, running, cycling)
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * @param sourceTypeCode
	 * @param periodType
	 * @param year
	 * @param month
	 * @param day
	 * 
	 * @return detail view of member's personal motion
	 */
	@GET
	@Path("getpersonalmotionactivity")
	@Produces("application/json")
	PersonalMotionDailyView getPersonalMotionData(@QueryParam("memberid") Long memberId, @QueryParam("sourcetypecode") String sourceTypeCode, @QueryParam("periodtype") String periodType,
			@QueryParam("year") Integer year, @QueryParam("month") Integer month, @QueryParam("day") Integer day);
	
	/**
	 * Get personal motion activity in range
	 * @param memberId
	 * @param sourceTypeCode
	 * @param periodType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@GET
	@Path("getpersonalmotioninrange")
	@Produces("application/json")
	PersonalMotionDailyView getPersonalMotionDataInRange(@QueryParam("memberid") Long memberId, @QueryParam("sourcetypecode") String sourceTypeCode, @QueryParam("periodtype") String periodType,
			@QueryParam("startdate") String startDate, @QueryParam("enddate") String endDate);
	
	/**
	 * Get member's driving activity
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * @param periodType
	 * @param year
	 * @param month
	 * @param day
	 * 
	 * @return detail view of member's driving
	 */
	@GET
	@Path("getdrivingactivity")
	@Produces("application/json")
	DrivingSummaryView getDrivingData(@QueryParam("memberid") Long memberId, @QueryParam("periodtype") String periodType,
			@QueryParam("year") Integer year, @QueryParam("month") Integer month, @QueryParam("day") Integer day);
	
	/**
	 * Get member's electricity activity
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param accountId
	 * @param periodType
	 * @param year
	 * @param month
	 * @param day
	 * 
	 * @return detail view of member's electricity
	 */
	@GET
	@Path("getelectricityactivity")
	@Produces("application/json")
	ElectricitySummaryView getElectricityData(@QueryParam("memberid") Long accountId, @QueryParam("periodtype") String periodType,
			@QueryParam("year") Integer year, @QueryParam("month") Integer month, @QueryParam("day") Integer day);
	
}
