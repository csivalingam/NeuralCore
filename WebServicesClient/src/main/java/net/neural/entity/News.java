package net.zfp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.community.Community;

@Entity(name="News" )
@XmlRootElement(name="News")
@XmlAccessorType(XmlAccessType.FIELD)
public class News extends BaseEntity{

	private static final long serialVersionUID = 1110417722183271203L;

	@XmlAttribute(name="header")
	private String header;
	
	@XmlAttribute(name="description")
	private String description;
	
	@XmlElement(name="Community")
	@ManyToOne
	@JoinColumn(name="communityId")
	private Community community;

	@XmlElement(name="PortalType")
	@ManyToOne
	@JoinColumn(name="portalTypeId")
	private PortalType portalType;
	
	public News() {
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	
	
}
