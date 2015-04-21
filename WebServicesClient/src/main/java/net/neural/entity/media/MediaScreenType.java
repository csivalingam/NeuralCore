package net.zfp.entity.media;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

@Entity(name="MediaScreenType" )
@XmlRootElement(name="MediaScreenType")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaScreenType extends BaseEntity {

	private static final long serialVersionUID = 6789935693441365341L;

	@XmlAttribute(name="type")
	private String type;
	
	public MediaScreenType() { }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}