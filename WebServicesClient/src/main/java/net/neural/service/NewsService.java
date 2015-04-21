package net.zfp.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import net.zfp.entity.News;
import net.zfp.view.NewsView;


@Path("/news")
public interface NewsService {

	@POST
	@Path("news")
	@Produces("text/xml")
	List<NewsView> getNews(@FormParam("domainName") String domainName, @FormParam("portalName") String portalName);

	@POST
	@Path("latestnews")
	@Produces("text/xml")
	NewsView getLatestNews(@FormParam("domainName") String domainName, @FormParam("portalName") String portalName);
}
