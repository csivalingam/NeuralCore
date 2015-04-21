package net.zfp.entity.media;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

@Entity(name="MediaHeader" )
@XmlRootElement(name="mediaHeader")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaHeader extends BaseEntity {

	private static final long serialVersionUID = 6788935698811365341L;

	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="url")
	private String url;
	
	public MediaHeader() { }
	
	public MediaHeader(String name, String url) {
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
