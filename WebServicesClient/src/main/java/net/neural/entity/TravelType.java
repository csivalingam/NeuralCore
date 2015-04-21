package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@Entity(name="TravelType" )
@XmlRootElement(name="TravelType")
@XmlAccessorType(XmlAccessType.FIELD)
public class TravelType extends DomainEntity{

	private static final long serialVersionUID = -1317675426837027468L;


	@XmlAttribute(name="type")
	private String type;
		
	@XmlAttribute(name="name")
	private String name;
	
	public TravelType() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
