package net.zfp.entity.survey;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.Operator;

/**
 * Entity implementation class for Entity: SurveyFactorReference
 * 
 * @author Youngwook Yoo
 * @since 2014-09-05
 *
 */
@Entity(name="SurveyFactorReference")
@XmlRootElement(name="SurveyFactorReference")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyFactorReference extends DomainEntity {
	private static final long serialVersionUID = 21913319415546950L;
	
	@XmlAttribute(name="reference")
	private String reference;
	
	public SurveyFactorReference(){}
	
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	
}
