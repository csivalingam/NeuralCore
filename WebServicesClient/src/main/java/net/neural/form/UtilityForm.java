package net.zfp.form;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="UtilityForm")
@XmlAccessorType(XmlAccessType.FIELD)

public class UtilityForm {
	

	@XmlElement(name="String")
	private String utilityType;
	@XmlElement(name="accountId")
	private Long accountId;
	@XmlElement(name="provider")
	private String provider;
	@XmlElement(name="city")
	private String city;
	@XmlElement(name="postalCode")
	private String postalCode;
	@XmlElement(name="accountNumber")
	private String accountNumber;
	@XmlElement(name="password")
	private String password;
	
	public UtilityForm() {
	}

	public String getUtilityType() {
		return utilityType;
	}

	public void setUtilityType(String utilityType) {
		this.utilityType = utilityType;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
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

	
	
}
