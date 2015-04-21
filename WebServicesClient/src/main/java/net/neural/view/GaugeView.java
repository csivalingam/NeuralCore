package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="gauge")
@XmlAccessorType(XmlAccessType.FIELD)
public class GaugeView {

	@XmlElement
	private String header;
	@XmlElement
	private String headerFontFamily;
	@XmlElement
	private String headerSize;
	@XmlElement
	private String headerColor;
	@XmlElement
	private String headerJustification;
	@XmlElement
	private String subHeader;
	@XmlElement
	private String subHeaderFontFamily;
	@XmlElement
	private String subHeaderSize;
	@XmlElement
	private String subHeaderColor;
	@XmlElement
	private String subHeaderJustification;
	@XmlElement
	private Double value;
	@XmlElement
	private Double deltaValue;
	@XmlElement
	private Double[] zoneData;
	@XmlElement
	private String unit;
	@XmlElement
	private String logo;
	@XmlElement
	private String copyright;
	@XmlElement
	private String[] legend;
	@XmlElement
	private String masterHeader;
	@XmlElement
	private Boolean displayUnitBoolean;;
	
	@XmlElement(name="result")
	private ResultView result;
	
	public GaugeView() {
		super();
	}
	public GaugeView(String header, Double value, Double deltaValue, Double[] zoneData,
			String unit, String logo, String copyright, String[] legend) {
		super();
		this.header = header;
		this.value = value;
		this.deltaValue = deltaValue;
		this.zoneData = zoneData;
		this.unit = unit;
		this.logo = logo;
		this.copyright = copyright;
		this.legend = legend;
	}
	
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getDeltaValue() {
		return deltaValue;
	}
	public void setDeltaValue(Double deltaValue) {
		this.deltaValue = deltaValue;
	}
	public Double[] getZoneData() {
		return zoneData;
	}
	public void setZoneData(Double[] zoneData) {
		this.zoneData = zoneData;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String[] getLegend() {
		return legend;
	}
	public void setLegend(String[] legend) {
		this.legend = legend;
	}
	
	public String getMasterHeader() {
		return masterHeader;
	}
	public void setMasterHeader(String masterHeader) {
		this.masterHeader = masterHeader;
	}
	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	public Boolean getDisplayUnitBoolean() {
		return displayUnitBoolean;
	}
	public void setDisplayUnitBoolean(Boolean displayUnitBoolean) {
		this.displayUnitBoolean = displayUnitBoolean;
	}
	public String getHeaderSize() {
		return headerSize;
	}
	public void setHeaderSize(String headerSize) {
		this.headerSize = headerSize;
	}
	public String getHeaderColor() {
		return headerColor;
	}
	public void setHeaderColor(String headerColor) {
		this.headerColor = headerColor;
	}
	public String getHeaderJustification() {
		return headerJustification;
	}
	public void setHeaderJustification(String headerJustification) {
		this.headerJustification = headerJustification;
	}
	public String getSubHeader() {
		return subHeader;
	}
	public void setSubHeader(String subHeader) {
		this.subHeader = subHeader;
	}
	public String getSubHeaderSize() {
		return subHeaderSize;
	}
	public void setSubHeaderSize(String subHeaderSize) {
		this.subHeaderSize = subHeaderSize;
	}
	public String getSubHeaderColor() {
		return subHeaderColor;
	}
	public void setSubHeaderColor(String subHeaderColor) {
		this.subHeaderColor = subHeaderColor;
	}
	public String getSubHeaderJustification() {
		return subHeaderJustification;
	}
	public void setSubHeaderJustification(String subHeaderJustification) {
		this.subHeaderJustification = subHeaderJustification;
	}
	public String getHeaderFontFamily() {
		return headerFontFamily;
	}
	public void setHeaderFontFamily(String headerFontFamily) {
		this.headerFontFamily = headerFontFamily;
	}
	public String getSubHeaderFontFamily() {
		return subHeaderFontFamily;
	}
	public void setSubHeaderFontFamily(String subHeaderFontFamily) {
		this.subHeaderFontFamily = subHeaderFontFamily;
	}
	
	
}
