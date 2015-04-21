package net.zfp.entity.activity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="BaselineType")
@XmlRootElement(name="BaselineType")
@XmlAccessorType(XmlAccessType.FIELD)
public class BaselineType extends DomainEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -528247474284710014L;
	@XmlElement
	private String type;
	
	public BaselineType() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
