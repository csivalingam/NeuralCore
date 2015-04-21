package net.zfp.entity.salesorder;

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
import net.zfp.entity.PointsAccount;
import net.zfp.entity.memberactivity.MemberActivity;
import net.zfp.entity.offer.Offer;

@Entity(name="PointsTransaction" )
@XmlRootElement(name="PointsTransaction")
@XmlAccessorType(XmlAccessType.FIELD)

public class PointsTransaction extends DomainEntity{ 

	private static final long serialVersionUID = 6839277385911612500L;
	
	@XmlAttribute(name="reference")
	private String reference;
	
	@XmlElement(name="PointsTransactionType")
	@OneToOne
	@JoinColumn(name="pointsTransactionType")
	private PointsTransactionType pointsTransactionType;
	
	@XmlElement(name="PointsAccount")
	@OneToOne
	@JoinColumn(name="pointsAccountId")
	private PointsAccount pointsAccount;
	
	@XmlElement(name="MemberActivity")
	@OneToOne
	@JoinColumn(name="memberActivityId")
	private MemberActivity memberActivity;
	
	@XmlElement(name="Offer")
	@OneToOne
	@JoinColumn(name="offerCode", referencedColumnName= "code")
	private Offer offer;
	
	@XmlAttribute(name="amount")
	private Integer amount;
	@XmlAttribute(name="date")
	private Date date;
	
	public PointsTransaction() {
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public PointsTransactionType getPointsTransactionType() {
		return pointsTransactionType;
	}

	public void setPointsTransactionType(PointsTransactionType pointsTransactionType) {
		this.pointsTransactionType = pointsTransactionType;
	}

	public PointsAccount getPointsAccount() {
		return pointsAccount;
	}

	public void setPointsAccount(PointsAccount pointsAccount) {
		this.pointsAccount = pointsAccount;
	}

	public MemberActivity getMemberActivity() {
		return memberActivity;
	}

	public void setMemberActivity(MemberActivity memberActivity) {
		this.memberActivity = memberActivity;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	
}
