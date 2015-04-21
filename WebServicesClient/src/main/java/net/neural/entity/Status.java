package net.zfp.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="Status" )
@XmlRootElement(name="Status")
@XmlAccessorType(XmlAccessType.FIELD)
public class Status extends DomainEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="code")
	private String code;
	
	@XmlAttribute(name="name")
	private String name;

	public Status() { }

	
	
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
   
}
