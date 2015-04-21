package net.zfp.entity.tax;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.IntegerEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="TaxRule")
@XmlRootElement(name="TaxRule")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaxRule extends IntegerEntity {

	private static final long serialVersionUID = 809164429415846950L;
	
	@XmlAttribute(name="rule")
	private String rule;
	
	
	public TaxRule() {}


	public String getRule() {
		return rule;
	}


	public void setRule(String rule) {
		this.rule = rule;
	}
	

}
