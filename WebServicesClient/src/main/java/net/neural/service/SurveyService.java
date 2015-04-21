package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.zfp.form.CarbonDetailForm;
import net.zfp.form.MobileSurveyForm;
import net.zfp.form.SurveyForm;
import net.zfp.view.ResultView;
import net.zfp.view.survey.BenchmarkView;
import net.zfp.view.survey.HistoryViews;
import net.zfp.view.survey.SurveySummaryView;
import net.zfp.view.survey.SurveyView;
import net.zfp.view.survey.SurveyViews;
import net.zfp.view.survey.TipsViews;


@Path("/survey")
public interface SurveyService {
	
	/**
	 * Get SurveyList based on member or community privilege
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-05
	 * @param memberId
	 * @param domain
	 * @return a list of surveys
	 */
	@GET
	@Path("getsurveys")
	@Produces("application/json")
	SurveyViews getSurveyList(@QueryParam("memberid") Long memberId, @QueryParam("domain") String domain);
	
	/**
	 * Get a list of completed surveys that member took
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-19
	 * @param memberId
	 * @return a list of completed surveys
	 */
	@GET
	@Path("getcompletedsurveys")
	@Produces("application/json")
	SurveyViews getCompletedSurveyList(@QueryParam("memberid") Long memberId);
	
	/**
	 * Get a survey information
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-05
	 * @param surveyId
	 * @param memberId
	 * @return the survey information
	 */
	@GET
	@Path("getsurveydetail")
	@Produces("application/json")
	SurveyView getSurvey(@QueryParam("surveyid") Long surveyId, @QueryParam("memberid") Long memberId);
	
	/**
	 * Get benchmark information.
	 * limit -> number of people to show in leaderboard
	 * compareto parameter 1 -> me compare to others in community
	 * 					   2 -> my community compare to community with similar size
	 * 					   3 -> me compare to everyone who took the survey
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-11
	 * @param surveyId
	 * @param memberId
	 * @param limit
	 * @param compareTo
	 * @return the survey information
	 */
	@GET
	@Path("getsurveybenchmarkdetail")
	@Produces("application/json")
	BenchmarkView getSurveyBenchmarkDetail(@QueryParam("surveyid") Long surveyId, @QueryParam("memberid") Long memberId, @QueryParam("limit") Integer limit, @QueryParam("compareto") Integer compareTo);
	
	/**
	 * Get past survey histories. Histories will be fetched in order of latest date.
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-05
	 * @param surveyId
	 * @param memberId
	 * @return the survey information
	 */
	@GET
	@Path("getsurveyhistory")
	@Produces("application/json")
	HistoryViews getSurveyHistory(@QueryParam("surveyid") Long surveyId, @QueryParam("memberid") Long memberId, @QueryParam("limit")Integer limit);
	
	/**
	 * Get a survey tips based on member results
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-05
	 * @param surveyId
	 * @param memberId
	 * @param limit
	 * @return the survey tips
	 */
	@GET
	@Path("getsurveytips")
	@Produces("application/json")
	TipsViews getSurveyTips(@QueryParam("surveyid")Long surveyId, @QueryParam("memberid")Long memberId, @QueryParam("limit") Integer limit);
	
	/**
	 * Get all available survey tips
	 * 
	 * @author Youngwook Yoo
	 * @sicne 2014-09-19
	 * @return a list of suvrey tips
	 */
	@GET
	@Path("getallsurveytips")
	@Produces("application/json")
	TipsViews getAllSurveyTips();
	
	/**
	 * Store formula based survey results into the database
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-05
	 * @param surveyId
	 * @param memberId
	 * @param result
	 * @return success or failure
	 */
	@POST
	@Path("setsurveysummary")
	@Produces("application/json")
	ResultView setSurveySummary(@FormParam("surveyid") Long surveyId, @FormParam("memberid") Long memberId, @FormParam("result") String result);
	
	/**
	 * Get a latest survey summary for a member
	 * 
	 * @author Youngwook Yoo
	 * @since 2014-09-011
	 * @param memberId
	 * @param domain
	 * @return a list of surveys
	 */
	@GET
	@Path("getsurveysummary")
	@Produces("application/json")
	SurveySummaryView getSurveySummary(@QueryParam("surveyid") Long surveyId, @QueryParam("memberid") Long memberId);
	
	//---------------------
	
	@POST
	@Path("getMobileSurveys")
	@Produces("text/xml")
	List<SurveyView> getMobileSurveys(@FormParam("domainName") String domainName, @FormParam("surveyId") Long surveyId);

	
	
	@POST
	@Path("storesurveyanswers")
	@Produces("text/xml")
	ResultView storeSurveyAnswers(@FormParam("") SurveyForm surveyForm);
	
	@POST
	@Path("storesurveys")
	@Produces("text/xml")
	ResultView storeSurveys(@FormParam("") MobileSurveyForm mobileSurveyForm);
}
