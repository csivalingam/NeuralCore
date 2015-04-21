package net.zfp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="SourceType")
@XmlRootElement(name="SourceType")
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceType extends DomainEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlElement(name="code")
	private String code;
	
	@XmlElement(name="name")
	private String name;
	
	@XmlElement(name="standardUnit")
	private String standardUnit;
	
	@XmlElement(name="imageURL")
	private String imageURL;
	
	public SourceType() { }
	
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

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

	public String getStandardUnit() {
		return standardUnit;
	}

	public void setStandardUnit(String standardUnit) {
		this.standardUnit = standardUnit;
	}


   
}
