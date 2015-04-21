package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="FootprintView")
@XmlAccessorType(XmlAccessType.FIELD)
public class FootprintView {
	
	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="sourceType")
	private Long sourceType;
	@XmlAttribute(name="footprintType")
	private Long footprintType;
	@XmlAttribute(name="value")
	private Double value;
	@XmlAttribute(name="unit")
	private String unit;
	@XmlAttribute(name="trend")
	private String trend;
	@XmlAttribute(name="sourceId")
	private Long sourceId;
	
	public FootprintView() {
		super();
	}

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public String getTrend() {
		return trend;
	}


	public void setTrend(String trend) {
		this.trend = trend;
	}


	public Long getSourceType() {
		return sourceType;
	}

	public void setSourceType(Long sourceType) {
		this.sourceType = sourceType;
	}

	public Long getFootprintType() {
		return footprintType;
	}

	public void setFootprintType(Long footprintType) {
		this.footprintType = footprintType;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}


	public Long getSourceId() {
		return sourceId;
	}


	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	
	
	
}
