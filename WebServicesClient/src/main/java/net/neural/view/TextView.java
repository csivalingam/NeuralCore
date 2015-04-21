package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="TextView")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextView {
	
	@XmlElement
	private String masterHeader;
	
	@XmlElement
	private String header;
	@XmlElement
	private String text;

	public TextView() {}

	
	public String getMasterHeader() {
		return masterHeader;
	}
	public void setMasterHeader(String masterHeader) {
		this.masterHeader = masterHeader;
	}
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
