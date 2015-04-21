package net.zfp.entity.feature;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="Feature" )
@XmlRootElement(name="Feature")
@XmlAccessorType(XmlAccessType.FIELD)

public class Feature extends DomainEntity{

	private static final long serialVersionUID = 7844502384801561348L;

	@XmlAttribute(name="name")
	private String name;
	
	public Feature() {	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
