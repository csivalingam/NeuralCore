package net.zfp.entity.campaign;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.SourceType;
import net.zfp.entity.Status;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="CampaignTemplate")
@XmlRootElement(name="CampaignTemplate")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignTemplate extends BaseEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlElement(name="CampaignTemplateType")
	@OneToOne
	@JoinColumn(name="type")
	private CampaignTemplateType campaignTemplateType;
	
	@XmlAttribute(name="name")
	private String name;
		
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="statusId")
	private Status status;
	
	@XmlAttribute(name="baselineId")
	private Long baselineId;
	
	@XmlAttribute(name="communityId")
	private Long communityId;
	
	@XmlAttribute(name="meterRequired")
	private Boolean meterRequired;
	
	@XmlElement(name="SourceType")
	@OneToOne
	@JoinColumn(name="sourceTypeId")
	private SourceType sourceType;
	
	@XmlAttribute(name="imageURL")
	private String imageURL;
	
	public CampaignTemplate() {}

	
	public CampaignTemplateType getCampaignTemplateType() {
		return campaignTemplateType;
	}


	public void setCampaignTemplateType(CampaignTemplateType campaignTemplateType) {
		this.campaignTemplateType = campaignTemplateType;
	}
	

	public Boolean getMeterRequired() {
		return meterRequired;
	}


	public void setMeterRequired(Boolean meterRequired) {
		this.meterRequired = meterRequired;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getBaselineId() {
		return baselineId;
	}

	public void setBaselineId(Long baselinId) {
		this.baselineId = baselinId;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

	public SourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	

	
	
	
}
