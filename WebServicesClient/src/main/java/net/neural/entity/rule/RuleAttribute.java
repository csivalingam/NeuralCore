package net.zfp.entity.rule;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="RuleAttribute")
@XmlRootElement(name="RuleAttribute")
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleAttribute extends DomainEntity {


	private static final long serialVersionUID = 7913469140950533298L;
	
	@XmlElement(name="RuleAttributeType")
	@OneToOne
	@JoinColumn(name="typeId")
	private RuleAttributeType ruleAttributeType;
	
	@XmlAttribute(name="attribute")
	private String attribute;
	
	public RuleAttribute() { }

	public RuleAttributeType getRuleAttributeType() {
		return ruleAttributeType;
	}

	public void setRuleAttributeType(RuleAttributeType ruleAttributeType) {
		this.ruleAttributeType = ruleAttributeType;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	
	
}
