package net.zfp.entity.device;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

/**
 * Entity implementation class for Entity: DeviceAttribute
 *
 */
@Entity(name="DeviceAttribute")
@XmlRootElement(name="DeviceAttribute")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceAttribute extends DomainEntity {

	private static final long serialVersionUID = 809164429415846950L;
	
	@XmlAttribute(name="attribute")
	private String attribute;
	
	public DeviceAttribute() {}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	
   
}
