package net.zfp.entity.memberactivity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="MemberActivityType")
@XmlRootElement(name="MemberActivityType")
@XmlAccessorType(XmlAccessType.FIELD)

public class MemberActivityType extends DomainEntity{ 

	private static final long serialVersionUID = 6839277385911612500L;
	
	@XmlAttribute(name="code")
	private String code;
	
		
	public MemberActivityType() {
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	
	
}
