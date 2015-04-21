package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name="leaderboards")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class LeaderboardViews {
	
	@XmlElement
	private List<LeaderboardView> leaderboardView;
	
	@XmlElement
	private ResultView result;
	
	public LeaderboardViews() {
	}
	

	public List<LeaderboardView> getLeaderboardView() {
		return leaderboardView;
	}


	public void setLeaderboardView(List<LeaderboardView> leaderboardView) {
		this.leaderboardView = leaderboardView;
	}


	public ResultView getResult() {
		return result;
	}


	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
