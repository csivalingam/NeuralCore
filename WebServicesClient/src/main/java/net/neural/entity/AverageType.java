package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="AverageType")
@XmlRootElement(name="AverageType")
@XmlAccessorType(XmlAccessType.FIELD)
public class AverageType extends DomainEntity {
	
	private static final long serialVersionUID = 819164429415146950L;

	@XmlAttribute(name="type")
	private String type;
	
	public AverageType() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}

