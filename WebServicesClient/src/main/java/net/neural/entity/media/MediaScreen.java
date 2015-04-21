package net.zfp.entity.media;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

@Entity(name="MediaScreen" )
@XmlRootElement(name="mediaScreen")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaScreen extends BaseEntity{

	private static final long serialVersionUID = -5888539559049594859L;

	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="url")
	private String url;
	
	@XmlAttribute(name="MediaScreenType")
	@OneToOne
	@JoinColumn(name="screenTypeId")
	private MediaScreenType mediaScreenType;
	
	public MediaScreen() { }
	
	public MediaScreen(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}
	public MediaScreen(String name, String url, MediaScreenType mediaScreenType) {
		super();
		this.name = name;
		this.url = url;
		this.mediaScreenType = mediaScreenType;
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

	public MediaScreenType getMediaScreenType() {
		return mediaScreenType;
	}

	public void setMediaScreenType(MediaScreenType mediaScreenType) {
		this.mediaScreenType = mediaScreenType;
	}


}
