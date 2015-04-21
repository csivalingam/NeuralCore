package net.zfp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="Account_Authority" )
@XmlRootElement(name="Account_Authority")
@XmlAccessorType(XmlAccessType.FIELD)
public class Account_Authority extends BaseEntity {


	private static final long serialVersionUID = 7916469140950533298L;
		
	@XmlElement(name="user")
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="account_id")
	private User user;
	
	@XmlElement(name="role")
	@OneToOne
	@JoinColumn(name="authority_id")
	private UserRole userRole;
	
	public Account_Authority() { }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

}