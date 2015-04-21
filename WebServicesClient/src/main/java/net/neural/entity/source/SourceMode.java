package net.zfp.entity.source;

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
@Entity(name="SourceMode" )
@XmlRootElement(name="SourceMode")
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceMode extends DomainEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="code")
	private String code;
	
	@XmlAttribute(name="name")
	private String name;
		
	public SourceMode() { }

	
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
