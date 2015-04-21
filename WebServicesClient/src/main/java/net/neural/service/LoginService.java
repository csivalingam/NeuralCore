package net.zfp.service;

import java.util.Collection;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.zfp.entity.User;
import net.zfp.view.UserView;


@Path("/login")
public interface LoginService {

	@POST
	@Path("authenticate")
	@Produces("text/xml")
	UserView authenticate(@FormParam("username")String username, @FormParam("password")String password, @FormParam("locale")String locale);
	
	@POST
	@Path("forgotPassword")
	@Produces("text/xml")
	UserView forgotPassword(
			@FormParam("")User user, 
			@FormParam("community")String community, 
			@FormParam("url")String url, 
			@FormParam("portalUrl")String portalUrl, 
			@FormParam("locale")String locale);

	@POST
	@Path("resetPassword")
	@Produces("text/xml")
	UserView resetPassword(@FormParam("")User user, @FormParam("community")String community, @FormParam("code")String code, @FormParam("locale")String locale);

	@POST
	@Path("checkExpires")
	@Produces("text/xml")
	UserView checkExpires(@FormParam("")User user, @FormParam("community")String community, @FormParam("code")String code);
	

}
