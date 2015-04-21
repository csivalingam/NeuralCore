package net.zfp.entity.utility;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.IntegerEntity;

@Entity(name="UtilityType" )
@XmlRootElement(name="UtilityType")
@XmlAccessorType(XmlAccessType.FIELD)

public class UtilityType extends IntegerEntity{ 

	private static final long serialVersionUID = 6839277385913612500L;
		
	@XmlAttribute(name="type")
	private String type;
	
	public UtilityType() {
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}
