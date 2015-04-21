package net.zfp.entity.campaign;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.group.Groups;

/**
 * Entity implementation class for Entity: CampaignGroupProgress
 *
 */
@Entity(name="CampaignGroupProgress")
@XmlRootElement(name="CampaignGroupProgress")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignGroupProgress extends DomainEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlElement(name="Campaign")
	@ManyToOne
	@JoinColumn(name="campaignId")
	private Campaign campaign;
	
	@XmlElement(name="Groups")
	@ManyToOne
	@JoinColumn(name="groupId")
	private Groups group;
	
	@XmlAttribute(name="progressValue")
	private Double progressValue;
	
	@XmlAttribute(name="iteration")
	private Integer iteration;
	
	public CampaignGroupProgress() {}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public Groups getGroup() {
		return group;
	}

	public void setGroup(Groups group) {
		this.group = group;
	}

	public Double getProgressValue() {
		return progressValue;
	}

	public void setProgressValue(Double progressValue) {
		this.progressValue = progressValue;
	}

	public Integer getIteration() {
		return iteration;
	}

	public void setIteration(Integer iteration) {
		this.iteration = iteration;
	}
	
	
}
