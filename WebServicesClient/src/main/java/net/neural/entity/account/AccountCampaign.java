package net.zfp.entity.account;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.User;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.group.Groups;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="AccountCampaign")
@XmlRootElement(name="AccountCampaign")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountCampaign extends DomainEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlElement(name="Groups")
	@ManyToOne
	@JoinColumn(name="groupId")
	private Groups groups;
	
	@XmlElement(name="User")
	@ManyToOne
	@JoinColumn(name="accountId")
	private User user;
	
	@XmlElement(name="Campaign")
	@ManyToOne
	@JoinColumn(name="campaignId")
	private Campaign campaign;
	
	@XmlAttribute(name="campaignTarget")
	private Integer campaignTarget;
	
	@XmlAttribute(name="campaignDailyTarget")
	private Integer campaignDailyTarget;
	
	public AccountCampaign() {}

	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	
	public Integer getCampaignTarget() {
		return campaignTarget;
	}

	public void setCampaignTarget(Integer campaignTarget) {
		this.campaignTarget = campaignTarget;
	}

	public Integer getCampaignDailyTarget() {
		return campaignDailyTarget;
	}

	public void setCampaignDailyTarget(Integer campaignDailyTarget) {
		this.campaignDailyTarget = campaignDailyTarget;
	}
	
	
	
}
