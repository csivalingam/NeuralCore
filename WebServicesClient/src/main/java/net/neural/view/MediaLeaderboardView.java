package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MediaLeaderboardView")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaLeaderboardView {

	@XmlAttribute(name="header")
	private String header;
	@XmlAttribute(name="headerFontFamily")
	private String headerFontFamily;
	@XmlAttribute(name="headerSize")
	private String headerSize;
	@XmlAttribute(name="headerColor")
	private String headerColor;
	@XmlAttribute(name="headerJustification")
	private String headerJustification;
	@XmlAttribute(name="subHeader")
	private String subHeader;
	@XmlAttribute(name="subHeaderFontFamily")
	private String subHeaderFontFamily;
	@XmlAttribute(name="subHeaderSize")
	private String subHeaderSize;
	@XmlAttribute(name="subHeaderColor")
	private String subHeaderColor;
	@XmlAttribute(name="subHeaderJustification")
	private String subHeaderJustification;
	@XmlAttribute(name="masterHeader")
	private String masterHeader;
	@XmlAttribute(name="displayUnitBoolean")
	private Boolean displayUnitBoolean;;
	
	@XmlElement(name="leaderboardViews")
	private List<LeaderboardView> leaderboardViews;
	
	public MediaLeaderboardView() {
		super();
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getMasterHeader() {
		return masterHeader;
	}

	public void setMasterHeader(String masterHeader) {
		this.masterHeader = masterHeader;
	}

	public Boolean getDisplayUnitBoolean() {
		return displayUnitBoolean;
	}

	public void setDisplayUnitBoolean(Boolean displayUnitBoolean) {
		this.displayUnitBoolean = displayUnitBoolean;
	}

	public List<LeaderboardView> getLeaderboardViews() {
		return leaderboardViews;
	}

	public void setLeaderboardViews(List<LeaderboardView> leaderboardViews) {
		this.leaderboardViews = leaderboardViews;
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
