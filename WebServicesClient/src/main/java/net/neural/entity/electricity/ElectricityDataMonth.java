package net.zfp.entity.electricity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.Source;


@Entity(name="ElectricityDataMonth" )
@XmlRootElement(name="ElectricityDataMonth")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElectricityDataMonth extends BaseEntity {
	
	private static final long serialVersionUID = 819164429415146950L;

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

	@XmlAttribute(name="onPeakSpend")
	private Double onPeakSpend;
	
	@XmlAttribute(name="midPeakSpend")
	private Double midPeakSpend;
	
	@XmlAttribute(name="offPeakSpend")
	private Double offPeakSpend;
	
	@XmlAttribute(name="onPeakConsumption")
	private Double onPeakConsumption;
	
	@XmlAttribute(name="midPeakConsumption")
	private Double midPeakConsumption;
	
	@XmlAttribute(name="offPeakConsumption")
	private Double offPeakConsumption;
	
	public ElectricityDataMonth() {
		
		this.energy = 0.0;
		this.onPeakConsumption = 0.0;
		this.onPeakSpend = 0.0;
		this.midPeakConsumption = 0.0;
		this.midPeakSpend = 0.0;
		this.offPeakConsumption = 0.0;
		this.offPeakSpend = 0.0;
		
	}

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

	public Double getOnPeakSpend() {
		return onPeakSpend;
	}

	public void setOnPeakSpend(Double onPeakSpend) {
		this.onPeakSpend = onPeakSpend;
	}

	public Double getMidPeakSpend() {
		return midPeakSpend;
	}

	public void setMidPeakSpend(Double midPeakSpend) {
		this.midPeakSpend = midPeakSpend;
	}

	public Double getOffPeakSpend() {
		return offPeakSpend;
	}

	public void setOffPeakSpend(Double offPeakSpend) {
		this.offPeakSpend = offPeakSpend;
	}

	public Double getOnPeakConsumption() {
		return onPeakConsumption;
	}

	public void setOnPeakConsumption(Double onPeakConsumption) {
		this.onPeakConsumption = onPeakConsumption;
	}

	public Double getMidPeakConsumption() {
		return midPeakConsumption;
	}

	public void setMidPeakConsumption(Double midPeakConsumption) {
		this.midPeakConsumption = midPeakConsumption;
	}

	public Double getOffPeakConsumption() {
		return offPeakConsumption;
	}

	public void setOffPeakConsumption(Double offPeakConsumption) {
		this.offPeakConsumption = offPeakConsumption;
	}
	
	
}

