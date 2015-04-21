package net.zfp.view;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="newsView")
@XmlAccessorType(XmlAccessType.FIELD)
public class NewsView {
	
	@XmlAttribute(name="header")
	private String header;
	@XmlAttribute(name="description")
	private String description;
	@XmlAttribute(name="created")
	private Date created;

	public NewsView() {}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	
	
}
