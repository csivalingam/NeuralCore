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

@Entity(name="Domain" )
@XmlRootElement(name="domain")
@XmlAccessorType(XmlAccessType.FIELD)
public class Domain extends BaseEntity{

	private static final long serialVersionUID = -4849066951005966572L;

	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="locale")
	private String locale;	
	
	@XmlElement(name="Community")
	@ManyToOne
	@JoinColumn(name="community_id")
	private Community community;
		
	@XmlAttribute(name="trackingId")
	private String trackingId;	
	
	public Domain() {
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	
}
