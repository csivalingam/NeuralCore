package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="personalDashboardView")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalDashboardView {
	
	@XmlAttribute(name="nickName")
	private String nickName;
	@XmlAttribute(name="sourceId")
	private Long sourceId;
	@XmlAttribute(name="elecValue")
	private Double elecValue;
	@XmlAttribute(name="ngValue")
	private Double ngValue;
	@XmlAttribute(name="waterValue")
	private Double waterValue;
	@XmlAttribute(name="wasteValue")
	private Double wasteValue;
	@XmlAttribute(name="vehicleValue")
	private Double vehicleValue;
	@XmlAttribute(name="flightValue")
	private Double flightValue;

	public PersonalDashboardView() {}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Double getElecValue() {
		return elecValue;
	}

	public void setElecValue(Double elecValue) {
		this.elecValue = elecValue;
	}

	public Double getNgValue() {
		return ngValue;
	}

	public void setNgValue(Double ngValue) {
		this.ngValue = ngValue;
	}

	public Double getWaterValue() {
		return waterValue;
	}

	public void setWaterValue(Double waterValue) {
		this.waterValue = waterValue;
	}

	public Double getWasteValue() {
		return wasteValue;
	}

	public void setWasteValue(Double wasteValue) {
		this.wasteValue = wasteValue;
	}

	public Double getVehicleValue() {
		return vehicleValue;
	}

	public void setVehicleValue(Double vehicleValue) {
		this.vehicleValue = vehicleValue;
	}

	public Double getFlightValue() {
		return flightValue;
	}

	public void setFlightValue(Double flightValue) {
		this.flightValue = flightValue;
	}

	
	
}
