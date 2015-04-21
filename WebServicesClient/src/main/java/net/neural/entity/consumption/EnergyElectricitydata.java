package net.zfp.entity.consumption;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.DomainEntity;

@Entity(name="EnergyElectricitydata")
@Table(name="ft_Electricitydata")
@XmlRootElement(name="EnergyElectricitydata")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnergyElectricitydata extends BaseEntity{

	@XmlAttribute(name="power")
	private double power;
	@XmlAttribute(name="energy")
	private double energy;

	@XmlAttribute(name="unit")
	private String unit;
	@XmlAttribute(name="sensorCode")
	private String sensorCode;
	
	@XmlAttribute(name="providerId")
	private long providerId;
	@XmlAttribute(name="estimated")
	private boolean estimated;
	
	public EnergyElectricitydata() {
	}
	
	public double getPower() {
		return power;
	}
	public void setPower(double power) {
		this.power = power;
	}
	public double getEnergy() {
		return energy;
	}
	public void setEnergy(double energy) {
		this.energy = energy;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSensorCode() {
		return sensorCode;
	}
	public void setSensorCode(String sensorCode) {
		this.sensorCode = sensorCode;
	}
	public long getProviderId() {
		return providerId;
	}
	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}
	public boolean isEstimated() {
		return estimated;
	}
	public void setEstimated(boolean estimated) {
		this.estimated = estimated;
	}
	
}
