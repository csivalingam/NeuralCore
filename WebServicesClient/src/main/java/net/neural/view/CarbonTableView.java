package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CarbonTableView")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarbonTableView {

	@XmlAttribute(name="projectName")
	private String projectName;
	
	@XmlAttribute(name="carbonValue")
	private String carbonValue;
	
	@XmlAttribute(name="unitPrice")
	private String unitPrice;
	
	@XmlAttribute(name="totalPrice")
	private String totalPrice;
	
	public CarbonTableView() {
		super();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCarbonValue() {
		return carbonValue;
	}

	public void setCarbonValue(String carbonValue) {
		this.carbonValue = carbonValue;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
