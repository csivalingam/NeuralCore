package net.zfp.entity.electricity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="ElectricityRate")
@XmlRootElement(name="ElectricityRate")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElectricityRate extends BaseEntity {
	
	private static final long serialVersionUID = 853164429415146950L;

	@XmlAttribute(name="type")
	private Integer type;
	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="unitFrom")
	private String unitFrom;
	@XmlAttribute(name="unitTo")
	private String unitTo;
	@XmlAttribute(name="value")
	private Double value;
	
	
	public ElectricityRate() { }


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUnitFrom() {
		return unitFrom;
	}


	public void setUnitFrom(String unitFrom) {
		this.unitFrom = unitFrom;
	}


	public String getUnitTo() {
		return unitTo;
	}


	public void setUnitTo(String unitTo) {
		this.unitTo = unitTo;
	}


	public Double getValue() {
		return value;
	}


	public void setValue(Double value) {
		this.value = value;
	}
	
}

