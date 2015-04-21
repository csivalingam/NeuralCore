package net.zfp.form;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="HouseholdForm")
@XmlAccessorType(XmlAccessType.FIELD)

public class HouseholdForm {
	
	@XmlElement(name="domainName")
	private String domainName;
	@XmlElement(name="memberId")
	private Long memberId;

	@XmlElement(name="houseType")
	private String houseType;
	@XmlElement(name="houseArea")
	private String houseArea;
	@XmlElement(name="occupants")
	private String occupants;
	@XmlElement(name="houseHeating")
	private String houseHeating;
	@XmlElement(name="waterHeating")
	private String waterHeating;
	@XmlElement(name="fireplace")
	private String fireplace;
	@XmlElement(name="stove")
	private String stove;
	@XmlElement(name="stove")
	private String BBQGas;
	
	public HouseholdForm() {
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}

	public String getOccupants() {
		return occupants;
	}

	public void setOccupants(String occupants) {
		this.occupants = occupants;
	}

	public String getHouseHeating() {
		return houseHeating;
	}

	public void setHouseHeating(String houseHeating) {
		this.houseHeating = houseHeating;
	}

	public String getWaterHeating() {
		return waterHeating;
	}

	public void setWaterHeating(String waterHeating) {
		this.waterHeating = waterHeating;
	}

	public String getFireplace() {
		return fireplace;
	}

	public void setFireplace(String fireplace) {
		this.fireplace = fireplace;
	}

	public String getStove() {
		return stove;
	}

	public void setStove(String stove) {
		this.stove = stove;
	}

	public String getBBQGas() {
		return BBQGas;
	}

	public void setBBQGas(String bBQGas) {
		BBQGas = bBQGas;
	}
	
	
}
