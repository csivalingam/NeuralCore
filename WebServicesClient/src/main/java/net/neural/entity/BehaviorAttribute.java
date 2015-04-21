package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@Entity(name="BehaviorAttribute" )
@XmlRootElement(name="BehaviorAttribute")
@XmlAccessorType(XmlAccessType.FIELD)
public class BehaviorAttribute extends DomainEntity{

	private static final long serialVersionUID = 1110417722183271203L;

	@XmlAttribute(name="attribute")
	private String attribute;
	
	
	public BehaviorAttribute() {
	}


	public String getAttribute() {
		return attribute;
	}


	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

}
