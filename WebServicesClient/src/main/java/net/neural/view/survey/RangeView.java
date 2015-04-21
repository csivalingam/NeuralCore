package net.zfp.view.survey;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="RangeView")
@XmlAccessorType(XmlAccessType.FIELD)
public class RangeView {
		
	@XmlElement
	private Double maximum;
	@XmlElement
	private Double minimum;
	@XmlElement
	private Double incrementer;

	public RangeView() {}

	public Double getMaximum() {
		return maximum;
	}

	public void setMaximum(Double maximum) {
		this.maximum = maximum;
	}

	public Double getMinimum() {
		return minimum;
	}

	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}

	public Double getIncrementer() {
		return incrementer;
	}

	public void setIncrementer(Double incrementer) {
		this.incrementer = incrementer;
	}
	
	
}
