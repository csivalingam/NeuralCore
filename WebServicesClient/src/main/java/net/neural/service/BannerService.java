package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.view.BannerView;
import net.zfp.view.ProgramBannerView;

@Path("/banner")
public interface BannerService {
	
	/**
	 * 
	 * Returns limited amount of Marketing Banner Views for a specified community.
	 * @param domainName
	 * @param bannerType
	 * @param limit
	 * @Author Youngwook Yoo
	 * @return
	 */
	@POST
	@Path("getmarketingbanners")
	@Produces("text/xml")
	List<BannerView> getMarketingBanners(@FormParam("domainName") String domainName, @FormParam("bannerType") String bannerType, @FormParam("limit") Integer limit);
	
	/**
	 * 
	 * Returns limited amount of Program Banner Views for a specified community.
	 * @param domainName
	 * @param limit
	 * @Author Youngwook Yoo
	 * @return
	 */
	@POST
	@Path("getprogrambanners")
	@Produces("text/xml")
	List<ProgramBannerView> getProgramBanners(@FormParam("domainName") String domainName, @FormParam("limit") Integer limit);
	
}
