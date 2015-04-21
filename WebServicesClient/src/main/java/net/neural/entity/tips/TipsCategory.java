package net.zfp.entity.tips;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;


/**
 * Entity implementation class for Entity: CarbonFootprint
 *
 */
@Entity(name="TipsCategory" )
@XmlRootElement(name="TipsCategory")
@XmlAccessorType(XmlAccessType.FIELD)
public class TipsCategory extends DomainEntity {
	
	private static final long serialVersionUID = 809164429415546950L;

	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="type")
	private String type;
	

	public TipsCategory() {}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
}

