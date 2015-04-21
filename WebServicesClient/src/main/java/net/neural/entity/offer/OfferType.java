package net.zfp.entity.offer;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.Status;
import net.zfp.entity.community.Community;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="OfferType" )
@XmlRootElement(name="OfferType")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferType extends DomainEntity {
	
	private static final long serialVersionUID = 619164429415546950L;
	
	@XmlAttribute(name="name")
	private String name;
	
	
	public OfferType() { }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	
}

