package net.zfp.entity.carbon;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.PaymentTransaction;

@Entity(name="CarbonTransaction" )
@XmlRootElement(name="carbonTransaction")
@XmlAccessorType(XmlAccessType.FIELD)

public class CarbonTransaction extends BaseEntity{ 

	private static final long serialVersionUID = 6839277385951612500L;
	
	@XmlAttribute(name="reference")
	private String reference;
	@XmlAttribute(name="email")
	private String email;
	@XmlAttribute(name="totalCost")
	private Double totalCost;
	@XmlAttribute(name="hst")
	private Double hst = 0d;
	@XmlAttribute(name="subTotalCost")
	private Double subTotalCost;
	@XmlAttribute(name="costUnit")
	private String costUnit;
	@XmlAttribute(name="carbon")
	private Double carbon;
	@XmlAttribute(name="carbonUnit")
	private String carbonUnit;
	@XmlAttribute(name="firstName")
	private String firstName;
	@XmlAttribute(name="lastName")
	private String lastName;
	@XmlAttribute(name="address1")
	private String address1;
	@XmlAttribute(name="address2")
	private String address2;
	@XmlAttribute(name="city")
	private String city;
	@XmlAttribute(name="province")
	private String province;
	@XmlAttribute(name="country")
	private String country;
	@XmlAttribute(name="postalCode")
	private String postalCode;
	@XmlAttribute(name="locale")
	private String locale;
	
	@XmlAttribute(name="receiptURL")
	private String receiptURL;

	@XmlAttribute(name="communityId")
	private Long communityId;
		
	@XmlElement(name="paymentTransaction")
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="paymentId")
	private PaymentTransaction payment;
	
	public PaymentTransaction getPayment() {
		return payment;
	}

	public void setPayment(PaymentTransaction payment) {
		this.payment = payment;
	}

	public CarbonTransaction() {
	}
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCostUnit() {
		return costUnit;
	}
	public void setCostUnit(String costUnit) {
		this.costUnit = costUnit;
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
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Double getCarbon() {
		return carbon;
	}

	public void setCarbon(Double carbon) {
		this.carbon = carbon;
	}

	public String getCarbonUnit() {
		return carbonUnit;
	}

	public void setCarbonUnit(String carbonUnit) {
		this.carbonUnit = carbonUnit;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Double getSubTotalCost() {
		return subTotalCost;
	}

	public void setSubTotalCost(Double subTotalCost) {
		this.subTotalCost = subTotalCost;
	}

	public Double getHst() {
		return hst;
	}

	public void setHst(Double hst) {
		this.hst = hst;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

	public String getReceiptURL() {
		return receiptURL;
	}

	public void setReceiptURL(String receiptURL) {
		this.receiptURL = receiptURL;
	}

}
