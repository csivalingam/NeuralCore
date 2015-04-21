package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="oneByOneView")
@XmlAccessorType(XmlAccessType.FIELD)
public class OneByOneView {
	
	@XmlAttribute(name="masterHeader")
	private String masterHeader;
	
	@XmlAttribute(name="header")
	private String header;
	@XmlAttribute(name="image")
	private String image;
	@XmlAttribute(name="text")
	private String text;

	public OneByOneView() {}

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
