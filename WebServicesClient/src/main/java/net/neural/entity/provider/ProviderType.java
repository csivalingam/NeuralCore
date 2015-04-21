package net.zfp.entity.provider;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.IntegerEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="ProviderType")
@XmlRootElement(name="ProviderType")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderType extends IntegerEntity {

	private static final long serialVersionUID = 809164429415246950L;
	
	@XmlAttribute(name="type")
	private String type;
	
	public ProviderType() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
