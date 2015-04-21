package net.zfp.entity.behavior;

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
@Entity(name="BehaviorTrackerType")
@XmlRootElement(name="BehaviorTrackerType")
@XmlAccessorType(XmlAccessType.FIELD)
public class BehaviorTrackerType extends DomainEntity {
	
	private static final long serialVersionUID = 719164429415546950L;
	
	@XmlAttribute(name="code")
	private String code;
	
	public BehaviorTrackerType() { }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}

