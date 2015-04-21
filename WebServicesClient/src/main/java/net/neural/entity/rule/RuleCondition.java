package net.zfp.entity.rule;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="RuleCondition")
@XmlRootElement(name="RuleCondition")
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleCondition extends DomainEntity {


	private static final long serialVersionUID = 7913469140950533298L;
	
	@XmlAttribute(name="condition")
	private String condition;
	
	public RuleCondition() { }

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
}
