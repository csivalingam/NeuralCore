package net.zfp.entity.survey;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.DomainEntity;
import net.zfp.entity.Status;
import net.zfp.entity.community.Community;
import net.zfp.entity.segment.Segment;

@Entity(name="Survey")
@XmlRootElement(name="Survey")
@XmlAccessorType(XmlAccessType.FIELD)
public class Survey extends BaseEntity {

	private static final long serialVersionUID = 809164429415846950L;
	
	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="description")
	private String description;
	
	@XmlAttribute(name="longDescription")
	private String longDescription;
	
	@XmlAttribute(name="outline")
	private String outline;
	
	@XmlAttribute(name="startDate")
	private Date startDate;
	
	@XmlAttribute(name="endDate")
	private Date endDate;
	
	@XmlElement(name="SurveyType")
	@OneToOne
	@JoinColumn(name="surveyTypeId")
	private SurveyType surveyType;
	
	@XmlElement(name="Segment")
	@OneToOne
	@JoinColumn(name="segmentId")
	private Segment segment;
	
	@XmlElement(name="Community")
	@OneToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="statusId")
	private Status status;
		
	
	public Survey() {}
	
	
	public String getOutline() {
		return outline;
	}


	public void setOutline(String outline) {
		this.outline = outline;
	}


	public String getLongDescription() {
		return longDescription;
	}


	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}


	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SurveyType getSurveyType() {
		return surveyType;
	}

	public void setSurveyType(SurveyType surveyType) {
		this.surveyType = surveyType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	
   
}
