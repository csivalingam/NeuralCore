package net.zfp.entity.community;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.Status;


/**
 * Entity implementation class for Entity: Community
 *
 */
@Entity(name="Community" )
@XmlRootElement(name="Community")
@XmlAccessorType(XmlAccessType.FIELD)
public class Community extends BaseEntity {
	
	private static final long serialVersionUID = 819164429415546950L;
	
	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="code")
	private String code;
	
	@XmlAttribute(name="longitude")
	private Double longitude;
	
	@XmlAttribute(name="latitude")
	private Double latitude;
	
	@XmlAttribute(name="activeFrom")
	private Date activeFrom;
	@XmlAttribute(name="activeTo")
	private Date activeTo;
			
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="status")
	private Status status;

	@XmlElement(name="CommunityType")
	@OneToOne
	@JoinColumn(name="typeId")
	private CommunityType communityType;
	
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	
	public Community() { }

	
	public Double getLongitude() {
		return longitude;
	}


	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	public Double getLatitude() {
		return latitude;
	}


	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public Date getActiveFrom() {
		return activeFrom;
	}

	public void setActiveFrom(Date activeFrom) {
		this.activeFrom = activeFrom;
	}

	public Date getActiveTo() {
		return activeTo;
	}

	public void setActiveTo(Date activeTo) {
		this.activeTo = activeTo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public CommunityType getCommunityType() {
		return communityType;
	}


	public void setCommunityType(CommunityType communityType) {
		this.communityType = communityType;
	}

	
}

