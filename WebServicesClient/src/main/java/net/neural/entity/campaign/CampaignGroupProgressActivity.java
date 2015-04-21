package net.zfp.entity.campaign;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.User;

/**
 * Entity implementation class for Entity: CampaignGroupProgress
 *
 */
@Entity(name="CampaignGroupProgressActivity")
@XmlRootElement(name="CampaignGroupProgressActivity")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignGroupProgressActivity extends DomainEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlElement(name="CampaignGroupProgress")
	@ManyToOne
	@JoinColumn(name="campaignGroupProgressId")
	private CampaignGroupProgress campaignGroupProgress;
	
	@XmlElement(name="user")
	@ManyToOne
	@JoinColumn(name="memberId")
	private User user;
	
	@XmlAttribute(name="startDate")
	private Date startDate;
	
	@XmlAttribute(name="progressValue")
	private Double progressValue;
	
	public CampaignGroupProgressActivity() {}

	public CampaignGroupProgress getCampaignGroupProgress() {
		return campaignGroupProgress;
	}

	public void setCampaignGroupProgress(CampaignGroupProgress campaignGroupProgress) {
		this.campaignGroupProgress = campaignGroupProgress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
