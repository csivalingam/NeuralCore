package net.zfp.entity.survey;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.DomainEntity;
import net.zfp.entity.category.Category;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="SurveyCategory")
@XmlRootElement(name="SurveyCategory")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyCategory extends DomainEntity {

	private static final long serialVersionUID = 829164429415846950L;
	
	@XmlElement(name="Survey")
	@ManyToOne
	@JoinColumn(name="surveyId")
	private Survey survey;
		
	@XmlElement(name="Category")
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;
	
	public SurveyCategory() {}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
