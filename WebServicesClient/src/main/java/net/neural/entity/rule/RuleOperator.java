package net.zfp.entity.rule;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="RuleOperator")
@XmlRootElement(name="RuleOperator")
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleOperator extends DomainEntity {


	private static final long serialVersionUID = 7913469140950533298L;
	
	@XmlAttribute(name="operator")
	private String operator;
	
	public RuleOperator() { }

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
}
