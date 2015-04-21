package net.zfp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.wallet.PointsType;

@Entity(name="PointsAccount")
@XmlRootElement(name="PointsAccount")
@XmlAccessorType(XmlAccessType.FIELD)
public class PointsAccount extends BaseEntity {

	private static final long serialVersionUID = 6788935693441365341L;

	@XmlAttribute(name="membershipId")
	private Long membershipId;
	
	@XmlElement(name="user")
	@OneToOne
	@JoinColumn(name="accountId")
	private User user;
	
	@XmlElement(name="PointsType")
	@OneToOne
	@JoinColumn(name="pointsTypeId")
	private PointsType pointsType;
	
	@XmlAttribute(name="balance")
	private Integer balance;
	
	public PointsAccount() { }

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

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public PointsType getPointsType() {
		return pointsType;
	}

	public void setPointsType(PointsType pointsType) {
		this.pointsType = pointsType;
	}
	
}