package net.zfp.entity.media;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

@Entity(name="MediaFooter" )
@XmlRootElement(name="mediaFooter")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaFooter extends BaseEntity {


	private static final long serialVersionUID = 6916469140950533298L;

	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="url")
	private String url;
	
	public MediaFooter() { }
	
	public MediaFooter(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
