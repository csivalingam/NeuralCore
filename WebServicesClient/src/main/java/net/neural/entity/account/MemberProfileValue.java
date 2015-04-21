package net.zfp.entity.account;

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
@Entity(name="MemberProfileValue")
@XmlRootElement(name="MemberProfileValue")
@XmlAccessorType(XmlAccessType.FIELD)
public class MemberProfileValue extends DomainEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlAttribute(name="value")
	private String value;
	
	public MemberProfileValue() {}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
