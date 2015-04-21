package net.zfp.entity.charity;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.Status;
import net.zfp.entity.partner.BusinessPartner;
import net.zfp.entity.segment.Segment;


/**
 * Entity implementation class for Entity: Charity
 *
 */
@Entity(name="Fundraiser" )
@XmlRootElement(name="Fundraiser")
@XmlAccessorType(XmlAccessType.FIELD)
public class Fundraiser extends BaseEntity {
	
	private static final long serialVersionUID = 819164429425546950L;
	
	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="description")
	private String description;
	
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	
	@XmlAttribute(name="largeImageUrl")
	private String largeImageUrl;
	
	@XmlAttribute(name="longDescription")
	private String longDescription;
		
	@XmlAttribute(name="startDate")
	private Date startDate;
	
	@XmlAttribute(name="endDate")
	private Date endDate;
	
	@XmlElement(name="BusinessPartner")
	@OneToOne
	@JoinColumn(name="businessPartnerId")
	private BusinessPartner businessPartner;
	
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="statusId")
	private Status status;
	
	@XmlElement(name="Segment")
	@OneToOne
	@JoinColumn(name="segmentId")
	private Segment segment;
	
	public Fundraiser() { }

	
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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BusinessPartner getBusinessPartner() {
		return businessPartner;
	}

	public void setBusinessPartner(BusinessPartner businessPartner) {
		this.businessPartner = businessPartner;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLargeImageUrl() {
		return largeImageUrl;
	}

	public void setLargeImageUrl(String largeImageUrl) {
		this.largeImageUrl = largeImageUrl;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	
	
}

