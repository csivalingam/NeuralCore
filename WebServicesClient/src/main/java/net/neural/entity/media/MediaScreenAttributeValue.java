package net.zfp.entity.media;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="MediaScreenAttributeValue" )
@XmlRootElement(name="MediaScreenAttributeValue")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaScreenAttributeValue extends DomainEntity {

	private static final long serialVersionUID = 6789935693441365341L;
	
	@XmlElement(name="MediaScreenAttribute")
	@ManyToOne
	@JoinColumn(name="attributeId")
	private MediaScreenAttribute mediaScreenAttribute;
	
	@XmlAttribute(name="value")
	private String value;
	
	@XmlElement(name="MediaScreenList")
	@ManyToOne
	@JoinColumn(name="screenListId")
	private MediaScreenList mediaScreenList;
	
	
	public MediaScreenAttributeValue() { }


	public MediaScreenAttribute getMediaScreenAttribute() {
		return mediaScreenAttribute;
	}


	public void setMediaScreenAttribute(MediaScreenAttribute mediaScreenAttribute) {
		this.mediaScreenAttribute = mediaScreenAttribute;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public MediaScreenList getMediaScreenList() {
		return mediaScreenList;
	}


	public void setMediaScreenList(MediaScreenList mediaScreenList) {
		this.mediaScreenList = mediaScreenList;
	}

	
	
}