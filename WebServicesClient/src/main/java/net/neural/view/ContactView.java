package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="ContactView")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContactView {
	
	@XmlAttribute(name="id")
	private Long id;
	@XmlAttribute(name="uid")
	private String uid;
	@XmlAttribute(name="email")
	private String email;
	@XmlAttribute(name="firstName")
	private String firstName;
	@XmlAttribute(name="lastName")
	private String lastName;
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	@XmlAttribute(name="status")
	private String status;
	@XmlAttribute(name="statusCode")
	private String statusCode;
	@XmlAttribute(name="amount")
	private String amount;
	@XmlAttribute(name="accountGroupId")
	private Long accountGroupId;
	
	public ContactView() {}

	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}


	public Long getAccountGroupId() {
		return accountGroupId;
	}


	public void setAccountGroupId(Long accountGroupId) {
		this.accountGroupId = accountGroupId;
	}
	
	

}
