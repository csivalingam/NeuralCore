package net.zfp.entity.survey;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.User;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="SurveyAnswers")
@XmlRootElement(name="SurveyAnswers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyAnswers extends DomainEntity {

	private static final long serialVersionUID = 829164429415846948L;
	
	@XmlElement(name="Survey")
	@ManyToOne
	@JoinColumn(name="surveyId")
	private Survey survey;
	
	@XmlElement(name="user")
	@ManyToOne
	@JoinColumn(name="memberId")
	private User user;
	
	@XmlElement(name="SurveyQuestions")
	@OneToOne
	@JoinColumn(name="questionId")
	private SurveyQuestions surveyQuestion;
	
	@XmlElement(name="SurveyAnswerValueType")
	@OneToOne
	@JoinColumn(name="answerValueType")
	private SurveyAnswerValueType surveyAnswerValueType;
	
	@XmlAttribute(name="answerValue")
	private String answerValue;
	
	@XmlAttribute(name="completedDate")
	private Date completedDate;
	
	public SurveyAnswers() {}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SurveyAnswerValueType getSurveyAnswerValueType() {
		return surveyAnswerValueType;
	}

	public void setSurveyAnswerValueType(SurveyAnswerValueType surveyAnswerValueType) {
		this.surveyAnswerValueType = surveyAnswerValueType;
	}

	public String getAnswerValue() {
		return answerValue;
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	
}
