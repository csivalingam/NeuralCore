package net.zfp.view.activity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="ActivitySubView")
@XmlAccessorType(XmlAccessType.FIELD)
public class ActivitySubView {
	
	@XmlElement
	private String unitImageUrl;

	@XmlElement
	private String unitDescription;
	
	@XmlElement
	private String unitName;

	@XmlElement
	private double actual;
	
	public ActivitySubView() {
	}
	
	public String getUnitImageUrl() {
		return unitImageUrl;
	}

	public void setUnitImageUrl(String unitImageUrl) {
		this.unitImageUrl = unitImageUrl;
	}

	public String getUnitDescription() {
		return unitDescription;
	}

	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public double getActual() {
		return actual;
	}

	public void setActual(double actual) {
		this.actual = actual;
	}
	
	
}
