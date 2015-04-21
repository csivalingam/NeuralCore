package net.zfp.entity.electricity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

@Entity(name="ElectricityWeatherNormHistory" )
@XmlRootElement(name="ElectricityWeatherNormHistory")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElectricityWeatherNormHistory extends BaseEntity {
	
	private static final long serialVersionUID = 813164429415146950L;

	@XmlAttribute(name="month")
	private Integer month;
		
	@XmlAttribute(name="cdd")
	private Double cdd;
	
	@XmlAttribute(name="hdd")
	private Double hdd;
	
	public ElectricityWeatherNormHistory() {
	}


	public Integer getMonth() {
		return month;
	}


	public void setMonth(Integer month) {
		this.month = month;
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

