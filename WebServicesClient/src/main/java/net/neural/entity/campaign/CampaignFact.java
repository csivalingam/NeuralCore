package net.zfp.entity.campaign;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="CampaignFact")
@XmlRootElement(name="CampaignFact")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignFact extends BaseEntity {

	private static final long serialVersionUID = 813167429415846950L;
		
	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="value")
	private Double value;
	
	@XmlAttribute(name="description")
	private String description;
	
	@XmlElement(name="CampaignTemplateType")
	@OneToOne
	@JoinColumn(name="templateTypeId")
	private CampaignTemplateType campaignTemplateType;
	
	public CampaignFact() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CampaignTemplateType getCampaignTemplateType() {
		return campaignTemplateType;
	}

	public void setCampaignTemplateType(CampaignTemplateType campaignTemplateType) {
		this.campaignTemplateType = campaignTemplateType;
	}
	
	
   
}
