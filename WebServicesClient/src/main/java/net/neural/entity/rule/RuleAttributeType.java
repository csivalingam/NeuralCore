package net.zfp.entity.rule;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="RuleAttributeType")
@XmlRootElement(name="RuleAttributeType")
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleAttributeType extends DomainEntity {


	private static final long serialVersionUID = 7913469140950533298L;
	
	@XmlAttribute(name="type")
	private String type;
	
	public RuleAttributeType() { }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
