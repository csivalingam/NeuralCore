package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.entity.User;
import net.zfp.entity.segment.Segment;
import net.zfp.form.CarbonDetailForm;
import net.zfp.form.MobileSurveyForm;
import net.zfp.form.SurveyForm;
import net.zfp.view.ResultView;
import net.zfp.view.survey.SurveyView;


@Path("/segment")
public interface SegmentService {
	
	@POST
	@Path("getdefaultsegment")
	@Produces("text/xml")
	List<Segment> getDefaultSegment(@FormParam("communityId")Long communityId);
	
	@POST
	@Path("getmembersegmentbyobject")
	@Produces("text/xml")
	List<Segment> getMemberSegment(@FormParam("")User user);
		
	@POST
	@Path("getmembersegment")
	@Produces("text/xml")
	List<Segment> getMemberSegment(@FormParam("memberId")Long memberId);
}
