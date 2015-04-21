package net.zfp.entity.offer;

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
@Entity(name="OfferValueType" )
@XmlRootElement(name="OfferValueType")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferValueType extends DomainEntity {
	
	private static final long serialVersionUID = 619164429415546950L;
	
	@XmlAttribute(name="type")
	private String type;
	
	
	public OfferValueType() { }


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	
	
}

