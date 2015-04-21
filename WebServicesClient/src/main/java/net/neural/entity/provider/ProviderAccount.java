package net.zfp.entity.provider;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.User;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="ProviderAccount")
@XmlRootElement(name="ProviderAccount")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderAccount extends DomainEntity {

	private static final long serialVersionUID = 809164429415846950L;
	
	@XmlAttribute(name="code")
	private String code;
	@XmlAttribute(name="deviceName")
	private String deviceName;
	
	@XmlElement(name="Provider")
	@OneToOne
	@JoinColumn(name="providerId")
	private Provider provider;
	
	@XmlElement(name="User")
	@OneToOne
	@JoinColumn(name="accountId")
	private User user;
	
	public ProviderAccount() {}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

   
}
