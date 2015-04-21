package net.zfp.service;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.entity.carbon.CarbonTransaction;
import net.zfp.form.CarbonDetailForm;
import net.zfp.view.CarbonTableView;
import net.zfp.view.ProductView;
import net.zfp.view.ResultView;
import net.zfp.view.SalesOrderView;
import net.zfp.view.UserView;

@Path("/carbon")
public interface CarbonService {

	/*
	@POST
	@Path("getCarbonTransaction")
	@Produces("text/xml")
	List<CarbonTransaction> getCarbonTransaction(@FormParam("email") String email, @FormParam("communityName") String communityName);
	*/
	@POST
	@Path("sendCarbonEmail")
	@Produces("text/xml")
	ResultView sendCarbonEmail(@FormParam("transactionId") Long transactionId, @FormParam("language") String language);
	
	/*
	@POST
	@Path("getAllCarbonTransaction")
	@Produces("text/xml")
	List<CarbonTransaction> getAllCarbonTransaction(@FormParam("communityName") String communityName);
	*/
	@POST
	@Path("getCarbonProjectsTransaction")
	@Produces("text/xml")
	SalesOrderView getCarbonProjectsTransaction(@FormParam("transactionId") Long transactionId, @FormParam("language") String language);
	
	@POST
	@Path("getCarbonProjects")
	@Produces("text/xml")
	List<ProductView> getCarbonProjects(@FormParam("domainName") String domainName, @FormParam("language") String language);
}
