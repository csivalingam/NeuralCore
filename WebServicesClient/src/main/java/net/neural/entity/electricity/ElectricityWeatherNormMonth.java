package net.zfp.entity.electricity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

@Entity(name="ElectricityWeatherNormMonth" )
@XmlRootElement(name="ElectricityWeatherNormMonth")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElectricityWeatherNormMonth extends BaseEntity {
	
	private static final long serialVersionUID = 813164429415146950L;

	@XmlAttribute(name="month")
	private Integer month;
	
	@XmlAttribute(name="year")
	private Integer year;
	
	@XmlAttribute(name="cdd")
	private Double cdd;
	
	@XmlAttribute(name="hdd")
	private Double hdd;
	
	public ElectricityWeatherNormMonth() {
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


	public Double getCdd() {
		return cdd;
	}


	public void setCdd(Double cdd) {
		this.cdd = cdd;
	}


	public Double getHdd() {
		return hdd;
	}

	public void setHdd(Double hdd) {
		this.hdd = hdd;
	}
	
	
}

