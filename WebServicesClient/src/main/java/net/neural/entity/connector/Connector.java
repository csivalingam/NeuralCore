package net.zfp.entity.connector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="Connector")
@XmlRootElement(name="Connector")
@XmlAccessorType(XmlAccessType.FIELD)
public class Connector extends DomainEntity {

	private static final long serialVersionUID = 809164429415846950L;
	
	@XmlAttribute(name="code")
	private String code;
	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="smallImageUrl")
	private String smallImageUrl;
	@XmlAttribute(name="shortDescription")
	private String shortDescription;
	@XmlAttribute(name="largeImageUrl")
	private String largeImageUrl;
	@XmlAttribute(name="longDescription")
	private String longDescription;
	public Connector() {}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSmallImageUrl() {
		return smallImageUrl;
	}
	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
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
