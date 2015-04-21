package net.zfp.entity.memberactivity;

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
import net.zfp.entity.offer.Offer;

@Entity(name="MemberActivity")
@XmlRootElement(name="MemberActivity")
@XmlAccessorType(XmlAccessType.FIELD)

public class MemberActivity extends DomainEntity{ 

	private static final long serialVersionUID = 6839277385911612500L;
	
	@XmlAttribute(name="membershipId")
	private Long membershipId;
	
	@XmlElement(name="user")
	@OneToOne
	@JoinColumn(name="accountId")
	private User user;
	
	@XmlElement(name="MemberActivityType")
	@OneToOne
	@JoinColumn(name="activityType")
	private MemberActivityType memberActivityType;
	
	@XmlAttribute(name="date")
	private Date date;
		
	@XmlAttribute(name="referenceCode")
	private String referenceCode;
	
	@XmlAttribute(name="reference")
	private Long reference;
	
	@XmlAttribute(name="processed")
	private Boolean processed;
	
	@XmlAttribute(name="processed")
	private String promoCode;
	
	@XmlAttribute(name="createdById")
	private Long createdById;
	
	public MemberActivity() {
	}

	public Long getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(Long membershipId) {
		this.membershipId = membershipId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MemberActivityType getMemberActivityType() {
		return memberActivityType;
	}

	public void setMemberActivityType(MemberActivityType memberActivityType) {
		this.memberActivityType = memberActivityType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}
	
	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}
	
}
