package net.zfp.entity.group;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.group.Groups;

/**
 * Entity implementation class for Entity: GroupCampaign
 *
 */
@Entity(name="GroupCampaign")
@XmlRootElement(name="GroupCampaign")
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupCampaign extends BaseEntity {

	private static final long serialVersionUID = 819164129415846950L;
	
	@XmlElement(name="Groups")
	@ManyToOne
	@JoinColumn(name="groupId")
	private Groups groups;
	
	@XmlElement(name="Campaign")
	@ManyToOne
	@JoinColumn(name="campaignId")
	private Campaign campaign;
	
	@XmlAttribute(name="campaignTarget")
	private int campaignTarget;
	
	@XmlAttribute(name="campaignDailyTarget")
	private int campaignDailyTarget;
	
	public GroupCampaign() {}

	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public int getCampaignDailyTarget() {
		return campaignDailyTarget;
	}

	public void setCampaignDailyTarget(int campaignDailyTarget) {
		this.campaignDailyTarget = campaignDailyTarget;
	}

	public int getCampaignTarget() {
		return campaignTarget;
	}

	public void setCampaignTarget(int campaignTarget) {
		this.campaignTarget = campaignTarget;
	}

	
}
