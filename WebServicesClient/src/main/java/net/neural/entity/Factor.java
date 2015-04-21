package net.zfp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="Factor" )
@XmlRootElement(name="Factor")
@XmlAccessorType(XmlAccessType.FIELD)
public class Factor extends BaseEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlElement(name="FactorAttribute")
	@ManyToOne
	@JoinColumn(name="attributeId")
	private FactorAttribute factorAttribute;

	@XmlAttribute(name="action")
	private String action;
	
	@XmlAttribute(name="value")
	private Double value;
	
	@XmlAttribute(name="dateRangeFrom")
	private Date dateRangeFrom;
	
	@XmlAttribute(name="inputUnit")
	private String inputUnit;
	
	@XmlAttribute(name="outputUnit")
	private String outputUnit;
	
	@XmlElement(name="SourceType")
	@ManyToOne
	@JoinColumn(name="sourceTypeId")
	private SourceType sourceType;
	
	public Factor() { }

	public FactorAttribute getFactorAttribute() {
		return factorAttribute;
	}

	public void setFactorAttribute(FactorAttribute factorAttribute) {
		this.factorAttribute = factorAttribute;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getDateRangeFrom() {
		return dateRangeFrom;
	}

	public void setDateRangeFrom(Date dateRangeFrom) {
		this.dateRangeFrom = dateRangeFrom;
	}

	public String getInputUnit() {
		return inputUnit;
	}

	public void setInputUnit(String inputUnit) {
		this.inputUnit = inputUnit;
	}

	public String getOutputUnit() {
		return outputUnit;
	}

	public void setOutputUnit(String outputUnit) {
		this.outputUnit = outputUnit;
	}

	public SourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

   
}
