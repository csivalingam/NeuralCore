package net.zfp.entity.partner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.User;
import net.zfp.entity.salesorder.PointsTransactionType;

@Entity(name="PartnerPointsTransaction")
@XmlRootElement(name="PartnerPointsTransaction")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartnerPointsTransaction extends DomainEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlElement(name="PointsTransactionType")
	@OneToOne
	@JoinColumn(name="pointsTransactionType")
	private PointsTransactionType pointsTransactionType;
	
	@XmlElement(name="PartnerPointsAccount")
	@OneToOne
	@JoinColumn(name="partnerPointsAccountId")
	private PartnerPointsAccount partnerPointsAccount;
	
	@XmlAttribute(name="GLReference")
	private Long GLReference;
		
	@XmlElement(name="PartnerActivityType")
	@OneToOne
	@JoinColumn(name="activityTypeId")
	private PartnerActivityType partnerActivityType;
	
	@XmlElement(name="User")
	@OneToOne
	@JoinColumn(name="memberId")
	private User user;
	
	@XmlAttribute(name="amount")
	private Double amount;
	
	@XmlAttribute(name="date")
	private Date date;
		
	public PartnerPointsTransaction() { }

	public PointsTransactionType getPointsTransactionType() {
		return pointsTransactionType;
	}

	public void setPointsTransactionType(PointsTransactionType pointsTransactionType) {
		this.pointsTransactionType = pointsTransactionType;
	}

	public PartnerPointsAccount getPartnerPointsAccount() {
		return partnerPointsAccount;
	}

	public void setPartnerPointsAccount(PartnerPointsAccount partnerPointsAccount) {
		this.partnerPointsAccount = partnerPointsAccount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getGLReference() {
		return GLReference;
	}

	public void setGLReference(Long gLReference) {
		GLReference = gLReference;
	}

	public PartnerActivityType getPartnerActivityType() {
		return partnerActivityType;
	}

	public void setPartnerActivityType(PartnerActivityType partnerActivityType) {
		this.partnerActivityType = partnerActivityType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
}

