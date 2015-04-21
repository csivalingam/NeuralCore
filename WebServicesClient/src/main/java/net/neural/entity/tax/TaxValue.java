package net.zfp.entity.tax;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.IntegerEntity;
import net.zfp.entity.User;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="TaxValue")
@XmlRootElement(name="TaxValue")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaxValue extends DomainEntity {

	private static final long serialVersionUID = 809164429415846950L;
	
	@XmlElement(name="TaxLocation")
	@ManyToOne
	@JoinColumn(name="taxLocationId")
	private TaxLocation taxLocation;
	
	@XmlAttribute(name="taxType")
	private String taxType;
	
	@XmlAttribute(name="taxTypeTranslation")
	private String taxTypeTranslation;
	
	@XmlAttribute(name="taxAmount")
	private Double taxAmount;
	
	@XmlElement(name="TaxRule")
	@ManyToOne
	@JoinColumn(name="taxRuleId")
	private TaxRule taxRule;
	
	
	public TaxValue() {}


	public TaxLocation getTaxLocation() {
		return taxLocation;
	}


	public void setTaxLocation(TaxLocation taxLocation) {
		this.taxLocation = taxLocation;
	}


	public String getTaxType() {
		return taxType;
	}


	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}


	public TaxRule getTaxRule() {
		return taxRule;
	}


	public void setTaxRule(TaxRule taxRule) {
		this.taxRule = taxRule;
	}


	public Double getTaxAmount() {
		return taxAmount;
	}


	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}


	public String getTaxTypeTranslation() {
		return taxTypeTranslation;
	}


	public void setTaxTypeTranslation(String taxTypeTranslation) {
		this.taxTypeTranslation = taxTypeTranslation;
	}
	
}
