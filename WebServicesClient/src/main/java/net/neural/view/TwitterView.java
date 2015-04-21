package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="twitter")
@XmlAccessorType(XmlAccessType.FIELD)
public class TwitterView {
	
	@XmlAttribute(name="header")
	private String header;
	@XmlAttribute(name="text")
	private String text;
	@XmlAttribute(name="image")
	private String image;
	@XmlAttribute(name="query")
	private String query;
	@XmlAttribute(name="widgetId")
	private String widgetId;
	@XmlAttribute(name="masterHeader")
	private String masterHeader;
	
	public TwitterView() {}

	
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}


	public String getMasterHeader() {
		return masterHeader;
	}


	public void setMasterHeader(String masterHeader) {
		this.masterHeader = masterHeader;
	}
	
	

}
