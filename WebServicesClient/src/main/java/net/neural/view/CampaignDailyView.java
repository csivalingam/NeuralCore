package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlRootElement(name="DailyCampaign")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class CampaignDailyView {

	@XmlElement
	private Integer target;

	@XmlElement
	private Integer actual;
	
	@XmlElement
	private Integer progressbarValue;
	
	@XmlElement(name="timeViews")
	private List<PersonalMotionView> personalMotionViews;
	
	@XmlElement
	private ResultView result;
	
	public CampaignDailyView(){}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public Integer getActual() {
		return actual;
	}

	public void setActual(Integer actual) {
		this.actual = actual;
	}

	public List<PersonalMotionView> getPersonalMotionViews() {
		return personalMotionViews;
	}

	public void setPersonalMotionViews(List<PersonalMotionView> personalMotionViews) {
		this.personalMotionViews = personalMotionViews;
	}

	public Integer getProgressbarValue() {
		return progressbarValue;
	}

	public void setProgressbarValue(Integer progressbarValue) {
		this.progressbarValue = progressbarValue;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	

}
