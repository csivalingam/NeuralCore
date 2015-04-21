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

@Entity(name="MediaScreenAttribute" )
@XmlRootElement(name="MediaScreenAttribute")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaScreenAttribute extends DomainEntity {

	private static final long serialVersionUID = 6789935693441365341L;

	@XmlAttribute(name="attribute")
	private String attribute;
		
	public MediaScreenAttribute() { }

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
}