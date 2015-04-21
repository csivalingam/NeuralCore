package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="FundraiseView")
@XmlAccessorType(XmlAccessType.FIELD)
public class FundraiseView {

	@XmlAttribute(name="actual")
	private Integer actual;
	@XmlAttribute(name="target")
	private Integer target;
	@XmlAttribute(name="isOffer")
	private Boolean isOffer;
	@XmlAttribute(name="offerPoints")
	private Integer offerPoints;
	@XmlAttribute(name="progress")
	private Integer progress;
	@XmlAttribute(name="unit")
	private String unit;
	
	@XmlAttribute(name="message")
	private String message;
	public FundraiseView() {
		actual = 0;
		target = 0;
		progress = 0;
		unit = "$";
		isOffer = false;
		offerPoints = 0;
	}
	public Integer getActual() {
		return actual;
	}
	public void setActual(Integer actual) {
		this.actual = actual;
	}
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
	}
	public Boolean getIsOffer() {
		return isOffer;
	}
	public void setIsOffer(Boolean isOffer) {
		this.isOffer = isOffer;
	}
	public Integer getOfferPoints() {
		return offerPoints;
	}
	public void setOfferPoints(Integer offerPoints) {
		this.offerPoints = offerPoints;
	}
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
