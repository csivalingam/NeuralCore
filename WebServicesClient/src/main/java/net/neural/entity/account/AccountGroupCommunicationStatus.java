package net.zfp.entity.account;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.Status;
import net.zfp.entity.User;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.group.Groups;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="AccountGroupCommunicationStatus")
@XmlRootElement(name="AccountGroupCommunicationStatus")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountGroupCommunicationStatus extends DomainEntity {

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
	
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="status")
	private Status status;
	
	public AccountGroupCommunicationStatus() {}

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	
}
