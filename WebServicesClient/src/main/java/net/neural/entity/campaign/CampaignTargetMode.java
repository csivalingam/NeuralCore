package net.zfp.entity.campaign;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

/**
 * Entity implementation class for Entity: CampaignTargetMode
 *
 */
@Entity(name="CampaignTargetMode")
@XmlRootElement(name="CampaignTargetMode")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignTargetMode extends DomainEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlAttribute(name="type")
	private String type;
		
	@XmlAttribute(name="name")
	private String name;
	
	public CampaignTargetMode() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
