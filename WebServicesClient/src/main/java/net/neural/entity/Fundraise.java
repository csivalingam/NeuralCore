package net.zfp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.account.AccountCampaign;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.community.Community;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="Fundraise" )
@XmlRootElement(name="Fundraise")
@XmlAccessorType(XmlAccessType.FIELD)
public class Fundraise extends BaseEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="amount")
	private Double amount;
	@XmlAttribute(name="transactionDate")
	private Date transactionDate;
	
	@XmlElement(name="User")
	@ManyToOne
	@JoinColumn(name="accountId")
	private User user;

	@XmlElement(name="AccountCampaign")
	@ManyToOne
	@JoinColumn(name="accountCampaignId")
	private AccountCampaign accountCampaign;
	
	public Fundraise() { }

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AccountCampaign getAccountCampaign() {
		return accountCampaign;
	}

	public void setAccountCampaign(AccountCampaign accountCampaign) {
		this.accountCampaign = accountCampaign;
	}
	
	
	
}

