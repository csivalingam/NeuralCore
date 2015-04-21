package net.zfp.entity.campaign;

import java.util.Date;

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
@Entity(name="CampaignProgressActivity")
@XmlRootElement(name="CampaignProgressActivity")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignProgressActivity extends DomainEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlElement(name="CampaignProgress")
	@ManyToOne
	@JoinColumn(name="campaignProgressId")
	private CampaignProgress campaignProgress;
	
	@XmlAttribute(name="startDate")
	private Date startDate;
	
	@XmlAttribute(name="progressValue")
	private Double progressValue;
		
	public CampaignProgressActivity() {}
	
	
	public CampaignProgress getCampaignProgress() {
		return campaignProgress;
	}


	public void setCampaignProgress(CampaignProgress campaignProgress) {
		this.campaignProgress = campaignProgress;
	}


	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Double getProgressValue() {
		return progressValue;
	}



	public void setProgressValue(Double progressValue) {
		this.progressValue = progressValue;
	}
	
	
	
	
}
