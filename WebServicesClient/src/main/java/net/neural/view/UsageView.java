package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="usage")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsageView {

	@XmlAttribute(name="usage")
	private Integer usage;
	
	@XmlAttribute(name="header")
	private String header;
	
	@XmlAttribute(name="equivalencyHeader")
	private String equivalencyHeader;
	
	@XmlAttribute(name="displayUnit")
	private String displayUnit;
	
	@XmlElement(name="imageViews")
	private List<ImageView> imageViews;
	
	@XmlAttribute(name="masterHeader")
	private String masterHeader;
	
	public UsageView() {
		super();
	}

	public Integer getUsage() {
		return usage;
	}

	public void setUsage(Integer usage) {
		this.usage = usage;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getEquivalencyHeader() {
		return equivalencyHeader;
	}

	public void setEquivalencyHeader(String equivalencyHeader) {
		this.equivalencyHeader = equivalencyHeader;
	}

	public String getDisplayUnit() {
		return displayUnit;
	}

	public void setDisplayUnit(String displayUnit) {
		this.displayUnit = displayUnit;
	}

	public List<ImageView> getImageViews() {
		return imageViews;
	}

	public void setImageViews(List<ImageView> imageViews) {
		this.imageViews = imageViews;
	}

	public String getMasterHeader() {
		return masterHeader;
	}

	public void setMasterHeader(String masterHeader) {
		this.masterHeader = masterHeader;
	}
	
	
}
