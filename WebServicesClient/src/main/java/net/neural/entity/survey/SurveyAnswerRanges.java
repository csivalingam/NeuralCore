package net.zfp.entity.survey;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

/**
 * Entity implementation class for Entity: SurveyAnswerRanges
 *
 */
@Entity(name="SurveyAnswerRanges")
@XmlRootElement(name="SurveyAnswerRanges")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyAnswerRanges extends BaseEntity {

	private static final long serialVersionUID = 829164429415846950L;
	
	@XmlElement(name="SurveyQuestions")
	@OneToOne
	@JoinColumn(name="questionId")
	private SurveyQuestions surveyQuestion;
	
	@XmlAttribute(name="minimum")
	private Double minimum;
	
	@XmlAttribute(name="maximum")
	private Double maximum;
	
	@XmlAttribute(name="incrementer")
	private Double incrementer;
	
	public SurveyAnswerRanges() {}

	public SurveyQuestions getSurveyQuestion() {
		return surveyQuestion;
	}

	public void setSurveyQuestion(SurveyQuestions surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}

	public Double getMinimum() {
		return minimum;
	}

	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}

	public Double getMaximum() {
		return maximum;
	}

	public void setMaximum(Double maximum) {
		this.maximum = maximum;
	}

	public Double getIncrementer() {
		return incrementer;
	}

	public void setIncrementer(Double incrementer) {
		this.incrementer = incrementer;
	}

	
}
