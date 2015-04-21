package net.zfp.entity.activity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="PeriodType")
@XmlRootElement(name="PeriodType")
@XmlAccessorType(XmlAccessType.FIELD)
public class PeriodType extends DomainEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -518297474284711014L;
	@XmlElement
	private String type;
	
	public PeriodType() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
