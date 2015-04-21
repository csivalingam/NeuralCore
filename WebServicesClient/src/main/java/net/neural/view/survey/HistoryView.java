package net.zfp.view.survey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.view.ResultView;


/**
 * View implementation class for Entity: HistoryView
 * 
 * @author Youngwook Yoo
 * @since 2014-09-03
 *
 */
@XmlRootElement(name="HistoryView")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class HistoryView {
	
	@XmlElement
	private String date;
	
	@XmlElement
	private Long surveyId;
	
	@XmlElement
	private Double result;

	public HistoryView() {
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}
	
}
