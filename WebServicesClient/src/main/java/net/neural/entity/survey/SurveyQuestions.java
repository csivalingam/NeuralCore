package net.zfp.entity.survey;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.Operator;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="SurveyQuestions")
@XmlRootElement(name="SurveyQuestions")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyQuestions extends DomainEntity {

	private static final long serialVersionUID = 829164429415846950L;
	
	@XmlElement(name="Survey")
	@ManyToOne
	@JoinColumn(name="surveyId")
	private Survey survey;
	
	@XmlElement(name="SurveyQuestionType")
	@OneToOne
	@JoinColumn(name="questionTypeId")
	private SurveyQuestionType questionType;
	
	@XmlElement(name="SurveyAnswerValueType")
	@OneToOne
	@JoinColumn(name="answerValueTypeId")
	private SurveyAnswerValueType answerValueType;
	
	@XmlAttribute(name="question")
	private String question;
	
	@XmlAttribute(name="questionCode")
	private Integer questionCode;
	
	@XmlElement(name="SurveyCategory")
	@OneToOne
	@JoinColumn(name="surveyCategoryId")
	private SurveyCategory surveyCategory;
	
	@XmlElement(name="Operator")
	@OneToOne
	@JoinColumn(name="operatorId")
	private Operator operator;
	
	@XmlElement(name="SurveyFactor")
	@OneToOne
	@JoinColumn(name="factorId")
	private SurveyFactor surveyFactor;
	
	@XmlElement(name="Operator")
	@OneToOne
	@JoinColumn(name="referenceOperatorId")
	private Operator referenceOperator;
	
	@XmlElement(name="SurveyFactorReference")
	@OneToOne
	@JoinColumn(name="referenceId")
	private SurveyFactorReference surveyFactorReference;
	
	@XmlAttribute(name="isMandatory")
	private Boolean isMandatory;
	
	@XmlAttribute(name="moreInfoImage")
	private String moreInfoImage;
	
	public SurveyQuestions() {}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public SurveyQuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(SurveyQuestionType questionType) {
		this.questionType = questionType;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public SurveyCategory getSurveyCategory() {
		return surveyCategory;
	}

	public void setSurveyCategory(SurveyCategory surveyCategory) {
		this.surveyCategory = surveyCategory;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public SurveyAnswerValueType getAnswerValueType() {
		return answerValueType;
	}

	public void setAnswerValueType(SurveyAnswerValueType answerValueType) {
		this.answerValueType = answerValueType;
	}

	public SurveyFactor getSurveyFactor() {
		return surveyFactor;
	}

	public void setSurveyFactor(SurveyFactor surveyFactor) {
		this.surveyFactor = surveyFactor;
	}

	public Operator getReferenceOperator() {
		return referenceOperator;
	}

	public void setReferenceOperator(Operator referenceOperator) {
		this.referenceOperator = referenceOperator;
	}

	public SurveyFactorReference getSurveyFactorReference() {
		return surveyFactorReference;
	}

	public void setSurveyFactorReference(SurveyFactorReference surveyFactorReference) {
		this.surveyFactorReference = surveyFactorReference;
	}

	public Integer getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(Integer questionCode) {
		this.questionCode = questionCode;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getMoreInfoImage() {
		return moreInfoImage;
	}

	public void setMoreInfoImage(String moreInfoImage) {
		this.moreInfoImage = moreInfoImage;
	}
	
}
