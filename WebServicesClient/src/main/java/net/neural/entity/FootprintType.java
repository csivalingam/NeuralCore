package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="FootprintType" )
@XmlRootElement(name="FootprintType")
@XmlAccessorType(XmlAccessType.FIELD)
public class FootprintType extends DomainEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="type")
	private String type;

	public FootprintType() { }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}

