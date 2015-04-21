package net.zfp.entity.print;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.DomainEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="PrintType" )
@XmlRootElement(name="PrintType")
@XmlAccessorType(XmlAccessType.FIELD)
public class PrintType extends DomainEntity {
	
	private static final long serialVersionUID = 809164429415146950L;
	
	@XmlAttribute(name="type")
	private String type;

	public PrintType() { }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	


   
}
