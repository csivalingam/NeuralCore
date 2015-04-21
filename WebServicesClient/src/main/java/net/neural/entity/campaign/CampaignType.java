package net.zfp.entity.campaign;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

/**
 * Entity implementation class for Entity: CampaignType
 *
 */
@Entity(name="CampaignType")
@XmlRootElement(name="CampaignType")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignType extends DomainEntity {

	private static final long serialVersionUID = 813167429415846950L;
		
	@XmlAttribute(name="type")
	private String type;
	
	public CampaignType() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
