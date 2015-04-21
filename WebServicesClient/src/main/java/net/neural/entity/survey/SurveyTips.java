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
import net.zfp.entity.tips.Tips;

/**
 * Entity implementation class for Entity: SurveyQuestionTips
 * 
 * @author Youngwook Yoo
 * @since 2014-09-05
 *
 */
@Entity(name="SurveyTips")
@XmlRootElement(name="SurveyTips")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyTips extends DomainEntity {
	private static final long serialVersionUID = 21943319415546950L;
	
	@XmlAttribute(name="relevance")
	private Integer relevance;
	
	@XmlAttribute(name="rangeFrom")
	private Double rangeFrom;
	
	@XmlAttribute(name="rangeTo")
	private Double rangeTo;
	
	public SurveyTips(){}
	
	
	public Integer getRelevance() {
		return relevance;
	}

	public void setRelevance(Integer relevance) {
		this.relevance = relevance;
	}

	public Double getRangeFrom() {
		return rangeFrom;
	}

	public void setRangeFrom(Double rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	public Double getRangeTo() {
		return rangeTo;
	}

	public void setRangeTo(Double rangeTo) {
		this.rangeTo = rangeTo;
	}
	
	
	
}
