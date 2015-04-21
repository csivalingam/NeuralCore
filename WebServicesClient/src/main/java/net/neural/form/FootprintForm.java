package net.zfp.form;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="FootprintForm")
@XmlAccessorType(XmlAccessType.FIELD)

public class FootprintForm {
	
	@XmlElement(name="elec")
	private Double elec;
	@XmlElement(name="ng")
	private Double ng;
	@XmlElement(name="water")
	private Double water;
	@XmlElement(name="waste")
	private Double waste;
	@XmlElement(name="vehicle")
	private Double vehicle;
	@XmlElement(name="flights")
	private Double flights;
	@XmlElement(name="unit")
	private String unit;
	
	@XmlElement(name="domainName")
	private String domainName;
	@XmlElement(name="accountId")
	private Long accountId;
	@XmlElement(name="footprintType")
	private Long footprintType;
	
	public FootprintForm() {
	}
	public Double getElec() {
		return elec;
	}
	public void setElec(Double elec) {
		this.elec = elec;
	}
	public Double getNg() {
		return ng;
	}
	public void setNg(Double ng) {
		this.ng = ng;
	}
	public Double getWater() {
		return water;
	}
	public void setWater(Double water) {
		this.water = water;
	}
	public Double getWaste() {
		return waste;
	}
	public void setWaste(Double waste) {
		this.waste = waste;
	}
	public Double getVehicle() {
		return vehicle;
	}
	public void setVehicle(Double vehicle) {
		this.vehicle = vehicle;
	}
	public Double getFlights() {
		return flights;
	}
	public void setFlights(Double flights) {
		this.flights = flights;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getFootprintType() {
		return footprintType;
	}
	public void setFootprintType(Long footprintType) {
		this.footprintType = footprintType;
	}
	
}
