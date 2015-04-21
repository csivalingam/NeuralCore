package net.zfp.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.zfp.entity.Tweet;
import net.zfp.entity.media.MediaStream;
import net.zfp.form.TranslationForm;
import net.zfp.view.GaugeView;
import net.zfp.view.ImageView;
import net.zfp.view.LeaderboardView;
import net.zfp.view.MediaLeaderboardView;
import net.zfp.view.MediaStreamView;
import net.zfp.view.OneByOneView;
import net.zfp.view.ResultView;
import net.zfp.view.TextView;
import net.zfp.view.TwitterView;
import net.zfp.view.UsageView;

@Path("/mediaStream")
public interface MediaStreamService {

	@POST
	@Path("mediaStream")
	@Produces("text/xml")
	MediaStreamView getMediaStreamInfo(
			@FormParam("serverName") String serverName,
			@FormParam("streamCode") String streamCode);

	@POST
	@Path("gauge")
	@Produces("text/xml")
	GaugeView getGaugeData(
			@FormParam("serverName") String serverName,
			@FormParam("streamCode") String streamCode,
			@FormParam("screenOrder") Integer screenOrder);
	
	@POST
	@Path("leaderboard")
	@Produces("text/xml")
	MediaLeaderboardView getLeaderboardData(
			@FormParam("serverName") String serverName,
			@FormParam("streamCode") String streamCode,
			@FormParam("screenOrder") Integer screenOrder);
	
	@POST
	@Path("textContent")
	@Produces("text/xml")
	TextView getTextData(
			@FormParam("serverName") String serverName,
			@FormParam("streamCode") String streamCode,
			@FormParam("screenOrder") Integer screenOrder);
	
	@POST
	@Path("tweet")
	@Produces("text/xml")
	Tweet getLastTweet(
			@FormParam("serverName") String serverName);

	@POST
	@Path("tweetAccount")
	@Produces("text/xml")
	TwitterView getTweetAccount(
			@FormParam("serverName") String serverName,
			@FormParam("streamCode") String streamCode,
			@FormParam("screenOrder") Integer screenOrder);

	@POST
	@Path("totalConsumption")
	@Produces("text/xml")
	UsageView getTotalConsumption(
			@FormParam("serverName") String serverName,
			@FormParam("streamCode") String streamCode,
			@FormParam("screenOrder") Integer screenOrder,
			@FormParam("locale") String locale);

	@POST
	@Path("oneByOne")
	@Produces("text/xml")
	OneByOneView getOneByOneData(
			@FormParam("serverName") String serverName,
			@FormParam("streamCode") String streamCode,
			@FormParam("screenOrder") Integer screenOrder);
	
	@POST
	@Path("imageContent")
	@Produces("text/xml")
	ImageView getImageData(
			@FormParam("serverName") String serverName,
			@FormParam("streamCode") String streamCode,
			@FormParam("screenOrder") Integer screenOrder);
}
