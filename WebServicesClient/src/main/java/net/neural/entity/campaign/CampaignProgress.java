package net.zfp.entity.campaign;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="CampaignProgress")
@XmlRootElement(name="CampaignProgress")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignProgress extends DomainEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlElement(name="Campaign")
	@ManyToOne
	@JoinColumn(name="campaignId")
	private Campaign campaign;
	
	@XmlAttribute(name="accountId")
	private Long accountId;
	
	@XmlAttribute(name="progressValue")
	private Double progressValue;
	
	@XmlAttribute(name="accountBaseline")
	private Integer accountBaseline;
	
	@XmlAttribute(name="iteration")
	private Integer iteration;
	
	public CampaignProgress() {}

	
	public Integer getIteration() {
		return iteration;
	}


	public void setIteration(Integer iteration) {
		this.iteration = iteration;
	}


	public Campaign getCampaign() {
		return campaign;
	}


	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}


	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Double getProgressValue() {
		return progressValue;
	}

	public void setProgressValue(Double progressValue) {
		this.progressValue = progressValue;
	}

	public Integer getAccountBaseline() {
		return accountBaseline;
	}

	public void setAccountBaseline(Integer accountBaseline) {
		this.accountBaseline = accountBaseline;
	}

	
	
   
}
