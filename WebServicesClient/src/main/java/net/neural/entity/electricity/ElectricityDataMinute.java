package net.zfp.entity.electricity;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.Source;

@Entity(name="ElectricityDataMinute" )
@XmlRootElement(name="ElectricityDataMinute")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElectricityDataMinute extends BaseEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="power")
	private Double power;
	@XmlAttribute(name="energy")
	private Double energy;
	
	@XmlElement(name="Source")
	@OneToOne
	@JoinColumn(name="sourceId")
	private Source source;

	@XmlAttribute(name="estimated")
	private Boolean estimated;

	public ElectricityDataMinute() { }

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	public Double getEnergy() {
		return energy;
	}

	public void setEnergy(Double energy) {
		this.energy = energy;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Boolean getEstimated() {
		return estimated;
	}

	public void setEstimated(Boolean estimated) {
		this.estimated = estimated;
	}
	
	

	
}

