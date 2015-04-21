package net.zfp.entity.electricity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="ElectricityBaseline" )
@XmlRootElement(name="ElectricityBaseline")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElectricityBaseline extends DomainEntity {
	
	private static final long serialVersionUID = 819164429415146950L;

	@XmlAttribute(name="month")
	private Integer month;
	
	@XmlAttribute(name="year")
	private Integer year;
	
	@XmlAttribute(name="consumption")
	private Double consumption;
	
	@XmlAttribute(name="unit")
	private String unit;
	
	public ElectricityBaseline() {
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getConsumption() {
		return consumption;
	}

	public void setConsumption(Double consumption) {
		this.consumption = consumption;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}

