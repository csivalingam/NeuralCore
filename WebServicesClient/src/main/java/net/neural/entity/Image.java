package net.zfp.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.community.Community;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="Image" )
@XmlRootElement(name="Image")
@XmlAccessorType(XmlAccessType.FIELD)
public class Image extends BaseEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="url")
	private String url;
	
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="statusId")
	private Status status;

	@XmlElement(name="PortalType")
	@OneToOne
	@JoinColumn(name="portalTypeId")
	private PortalType portalType;
	
	@XmlAttribute(name="locale")
	private String locale;
	
	@XmlElement(name="Community")
	@OneToOne
	@JoinColumn(name="communityId")
	private Community community;
		
	@XmlAttribute(name="imageTypeId")
	private Long imageTypeId;

	public Image() { }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public PortalType getPortalType() {
		return portalType;
	}

	public void setPortalType(PortalType portalType) {
		this.portalType = portalType;
	}

	public Long getImageTypeId() {
		return imageTypeId;
	}

	public void setImageTypeId(Long imageTypeId) {
		this.imageTypeId = imageTypeId;
	}

	
}

