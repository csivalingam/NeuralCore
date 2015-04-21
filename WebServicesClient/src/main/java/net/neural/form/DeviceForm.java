package net.zfp.form;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DeviceForm")
@XmlAccessorType(XmlAccessType.FIELD)

public class DeviceForm {
	
	@XmlElement(name="domain")
	private String domain;
	@XmlElement(name="accountId")
	private Long accountId;
	
	@XmlElement(name="providerDeviceId")
	private Long providerDeviceId;
	
	@XmlElement(name="email")
	private String email;
	@XmlElement(name="type")
	private String type;
	@XmlElement(name="name")
	private String name;
	@XmlElement(name="serialNumber")
	private String serialNumber;
	@XmlElement(name="userName")
	private String userName;
	@XmlElement(name="password")
	private String password;
	
	@XmlElement(name="field1")
	private String field1;
	
	@XmlElement(name="field2")
	private String field2;
	
	@XmlElement(name="provider")
	private String provider;
	
	@XmlElement(name="update")
	private Boolean update;
	
	public DeviceForm() {
	}

	
	public Boolean getUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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


	public String getField1() {
		return field1;
	}


	public void setField1(String field1) {
		this.field1 = field1;
	}


	public String getField2() {
		return field2;
	}


	public void setField2(String field2) {
		this.field2 = field2;
	}


	public Long getProviderDeviceId() {
		return providerDeviceId;
	}


	public void setProviderDeviceId(Long providerDeviceId) {
		this.providerDeviceId = providerDeviceId;
	}

	
}
