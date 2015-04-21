package net.zfp.entity.wallet;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.User;
import net.zfp.entity.community.Community;

@Entity(name="Wallet")
@XmlRootElement(name="Wallet")
@XmlAccessorType(XmlAccessType.FIELD)
public class Wallet extends BaseEntity {


	private static final long serialVersionUID = 7913469140950533298L;
	
	@XmlElement(name="WalletType")
	@OneToOne
	@JoinColumn(name="typeId")
	private WalletType walletType;
	
	@XmlElement(name="user")
	@ManyToOne
	@JoinColumn(name="memberId")
	private User member;
	
	@XmlElement(name="Community")
	@ManyToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	public Wallet() { }

	public WalletType getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}

	public User getMember() {
		return member;
	}

	public void setMember(User member) {
		this.member = member;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}
	

}