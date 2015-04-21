package net.zfp.entity.survey;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

/**
 * Entity implementation class for Entity: SurveyQuestionRank
 * 
 * @author Youngwook Yoo
 * @since 2014-09-05
 *
 */
@Entity(name="SurveyQuestionRank")
@XmlRootElement(name="SurveyQuestionRank")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyQuestionRank extends DomainEntity {
	private static final long serialVersionUID = 21913319415546950L;
	
	@XmlElement(name="SurveyQuestions")
	@OneToOne
	@JoinColumn(name="questionId")
	private SurveyQuestions surveyQuestions;
	
	@XmlAttribute(name="rank")
	private Integer rank;

	@XmlAttribute(name="value")
	private Double value;
	
	public SurveyQuestionRank(){}

	public SurveyQuestions getSurveyQuestions() {
		return surveyQuestions;
	}

	public void setSurveyQuestions(SurveyQuestions surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	
	
	
}
