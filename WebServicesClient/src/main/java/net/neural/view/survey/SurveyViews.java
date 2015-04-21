package net.zfp.view.survey;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.view.ResultView;

/**
 * View implementation class for Entity: SurveyViews
 * 
 * @author Youngwook Yoo
 * @since 2014-09-05
 *
 */
@XmlRootElement(name="SurveyViews")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class SurveyViews {
	
	@XmlElement
	private List<SurveyView> surveyViews;
	@XmlElement
	private ResultView result;
	
	public SurveyViews(){}

	public List<SurveyView> getSurveyViews() {
		return surveyViews;
	}

	public void setSurveyViews(List<SurveyView> surveyViews) {
		this.surveyViews = surveyViews;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
