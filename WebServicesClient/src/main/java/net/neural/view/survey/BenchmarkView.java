package net.zfp.view.survey;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.view.GaugeView;
import net.zfp.view.LeaderboardView;
import net.zfp.view.ResultView;


/**
 * View implementation class for Entity: BenchmarkView
 * 
 * @author Youngwook Yoo
 * @since 2014-09-11
 *
 */
@XmlRootElement(name="BenchmarkView")
@XmlAccessorType(XmlAccessType.FIELD)
public class BenchmarkView {
	
	@XmlElement
	private GaugeView gaugeView;
	
	@XmlElement
	private List<LeaderboardView> leaderboardViews;
	
	@XmlElement
	private ResultView result;

	public BenchmarkView() {
		leaderboardViews = new ArrayList<LeaderboardView>();
	}


	public GaugeView getGaugeView() {
		return gaugeView;
	}


	public void setGaugeView(GaugeView gaugeView) {
		this.gaugeView = gaugeView;
	}


	public List<LeaderboardView> getLeaderboardViews() {
		return leaderboardViews;
	}


	public void setLeaderboardViews(List<LeaderboardView> leaderboardViews) {
		this.leaderboardViews = leaderboardViews;
	}


	public ResultView getResult() {
		return result;
	}


	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
	
}
