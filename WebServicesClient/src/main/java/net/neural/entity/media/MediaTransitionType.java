package net.zfp.entity.media;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

@Entity(name="MediaTransitionType" )
@XmlRootElement(name="mediaTransitionType")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaTransitionType extends BaseEntity {

	private static final long serialVersionUID = 6788935693441365341L;

	@XmlAttribute(name="type")
	private String type;
	
	public MediaTransitionType() { }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}