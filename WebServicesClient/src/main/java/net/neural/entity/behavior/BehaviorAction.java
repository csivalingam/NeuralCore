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
@Entity(name="BehaviorAction")
@XmlRootElement(name="BehaviorAction")
@XmlAccessorType(XmlAccessType.FIELD)
public class BehaviorAction extends DomainEntity {
	
	private static final long serialVersionUID = 719164429415546950L;
	
	@XmlAttribute(name="code")
	private String code;
	
	public BehaviorAction() { }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}

