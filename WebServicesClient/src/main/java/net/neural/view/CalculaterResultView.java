package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name="calculater")
@XmlAccessorType(XmlAccessType.FIELD)
public class CalculaterResultView {

	@XmlAttribute(name="userId")
	private long userId;
	@XmlAttribute(name="confirmNumber")
	private String confirmNumber;

	@XmlAttribute(name="carbon")
	private double carbon;
	@XmlAttribute(name="carbonUnit")
	private String carbonUnit = "kg CO2e";

	@XmlAttribute(name="cost")
	private double cost;
	@XmlAttribute(name="costUnit")
	private String costUnit = "$";

	public CalculaterResultView() {
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getConfirmNumber() {
		return confirmNumber;
	}
	public void setConfirmNumber(String confirmNumber) {
		this.confirmNumber = confirmNumber;
	}
	public double getCarbon() {
		return carbon;
	}
	public void setCarbon(double carbon) {
		this.carbon = carbon;
	}
	public String getCarbonUnit() {
		return carbonUnit;
	}
	public void setCarbonUnit(String carbonUnit) {
		this.carbonUnit = carbonUnit;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getCostUnit() {
		return costUnit;
	}
	public void setCostUnit(String costUnit) {
		this.costUnit = costUnit;
	}
}
