package net.zfp.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import net.zfp.entity.SourceType;
import net.zfp.entity.tips.Tips;


@Path("/tips")
public interface TipService {
	
	@GET
	@Path("sourcetypetest")
	@Produces("application/json")
	SourceType test();
	
	@POST
	@Path("sourceTypes")
	@Produces("text/xml")
	List<SourceType> getAllSupportedSourceNames(@FormParam("tipCategoryId") Long tipCategoryId);
	
	@POST
	@Path("sourceTypesByAccount")
	@Produces("text/xml")
	List<SourceType> getAllSupportedSourceTypeByAccount(@FormParam("domainName") String domainName, @FormParam("tipCategoryId") Long tipCategoryId, @FormParam("accountEmail") String accountEmail);
	
	@POST
	@Path("tips")
	@Produces("text/xml")
	List<Tips> getAllSupportedTips(@FormParam("tipCategoryId") Long tipCategoryId);
	
	@POST
	@Path("tipsSourceType")
	@Produces("text/xml")
	List<Tips> getAllSupportedTips(@FormParam("tipCategoryId") Long tipCategoryId, @FormParam("sourceTypeId") Long sourceTypeId);
	
	@POST
	@Path("tipsByAccount")
	@Produces("text/xml")
	List<Tips> getAllSupportedTipsByAccount(@FormParam("domainName") String domainName, @FormParam("tipCategoryId") Long tipCategoryId, @FormParam("accountEmail") String accountEmail);
	
}
