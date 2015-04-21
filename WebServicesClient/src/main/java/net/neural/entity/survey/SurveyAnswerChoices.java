package net.zfp.entity.survey;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

/**
 * Entity implementation class for Entity: SurveyAnswerChoices
 *
 */
@Entity(name="SurveyAnswerChoices")
@XmlRootElement(name="SurveyAnswerChoices")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyAnswerChoices extends BaseEntity {

	private static final long serialVersionUID = 829164429415846950L;
	
	@XmlElement(name="Survey")
	@ManyToOne
	@JoinColumn(name="surveyId")
	private Survey survey;
	
	@XmlElement(name="SurveyQuestions")
	@OneToOne
	@JoinColumn(name="questionId")
	private SurveyQuestions surveyQuestion;
	
	@XmlAttribute(name="choice")
	private String choice;
	
	@XmlAttribute(name="value")
	private String value;
	
	@XmlAttribute(name="choiceCode")
	private Integer choiceCode;
	
	public SurveyAnswerChoices() {}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public SurveyQuestions getSurveyQuestion() {
		return surveyQuestion;
	}

	public void setSurveyQuestion(SurveyQuestions surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public Integer getChoiceCode() {
		return choiceCode;
	}

	public void setChoiceCode(Integer choiceCode) {
		this.choiceCode = choiceCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	

}
