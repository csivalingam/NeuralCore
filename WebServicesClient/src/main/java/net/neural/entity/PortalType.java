package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="PortalType" )
@XmlRootElement(name="portalType")
@XmlAccessorType(XmlAccessType.FIELD)
public class PortalType extends BaseEntity {

	private static final long serialVersionUID = 6788935693441365341L;

	@XmlAttribute(name="type")
	private String type;
	
	public PortalType() { }
	
	public PortalType(String type) {
		super();
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}