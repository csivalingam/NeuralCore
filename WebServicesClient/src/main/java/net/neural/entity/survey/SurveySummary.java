package net.zfp.entity.survey;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.User;
import net.zfp.entity.category.Category;

/**
 * Entity implementation class for Entity: SurveySummary
 * 
 * @author Youngwook Yoo
 * @since 2014-09-05
 *
 */
@Entity(name="SurveySummary")
@XmlRootElement(name="SurveySummary")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveySummary extends DomainEntity{
	private static final long serialVersionUID = 21943319415546950L;
	
	@Column(nullable = false)
	private Date created = new Date();
	
	@XmlElement(name="user")
	@ManyToOne
	@JoinColumn(name="memberId")
	private User user;
	
	@XmlElement(name="Survey")
	@ManyToOne
	@JoinColumn(name="surveyId")
	private Survey survey;
	
	@XmlElement(name="Category")
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;
	
	@XmlAttribute(name="result")
	private Double result;
	
	@XmlAttribute(name="iteration")
	private Integer iteration;
	
	public SurveySummary(){}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getIteration() {
		return iteration;
	}

	public void setIteration(Integer iteration) {
		this.iteration = iteration;
	}
	
	
}
