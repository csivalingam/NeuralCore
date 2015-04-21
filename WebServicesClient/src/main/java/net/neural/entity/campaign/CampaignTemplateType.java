package net.zfp.entity.campaign;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="CampaignTemplateType")
@XmlRootElement(name="CampaignTemplateType")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignTemplateType extends DomainEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlAttribute(name="type")
	private String type;
		
	@XmlAttribute(name="mode")
	private String mode;
	
	@XmlAttribute(name="measure")
	private String measure;
	
	@XmlAttribute(name="name")
	private String name;
	
	public CampaignTemplateType() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	
	
}
