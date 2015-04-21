package net.zfp.entity.utility;

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
import net.zfp.entity.provider.Provider;

@Entity(name="UtilityConnectionRequest" )
@XmlRootElement(name="UtilityConnectionRequest")
@XmlAccessorType(XmlAccessType.FIELD)

public class UtilityConnectionRequest extends DomainEntity{ 

	private static final long serialVersionUID = 6839277385913612500L;
		
	@XmlElement(name="UtilityType")
	@OneToOne
	@JoinColumn(name="utilityType")
	private UtilityType utilityType;
	
	@XmlElement(name="user")
	@OneToOne
	@JoinColumn(name="accountId")
	private User user;
	
	@XmlAttribute(name="provider")
	private String provider;
	
	@XmlAttribute(name="city")
	private String city;
	
	@XmlAttribute(name="postalCode")
	private String postalCode;
	
	@XmlAttribute(name="accountNumber")
	private String accountNumber;
	
	@XmlAttribute(name="password")
	private String password;
	
	public UtilityConnectionRequest() {
		
	}

	public UtilityType getUtilityType() {
		return utilityType;
	}

	public void setUtilityType(UtilityType utilityType) {
		this.utilityType = utilityType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
