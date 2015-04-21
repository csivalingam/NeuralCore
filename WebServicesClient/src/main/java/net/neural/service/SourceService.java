package net.zfp.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.view.ResultView;

/**
 * 
 * @author Youngwook Yoo
 * @since 2014-09-30
 * 
 */
@Path("/source")
public interface SourceService {

	/**
	 * Get Master Source for 
	 * 
	 * @param memberId
	 * @param sourceTypeCode
	 * @return source ID if success, -1 otherwise
	 */
	@GET
	@Path("getmastersource")
	@Produces("application/json")
	ResultView getMasterSourceId(@FormParam("memberid") Long memberId, @FormParam("sourcetypecode") String sourceTypeCode);
	
}
