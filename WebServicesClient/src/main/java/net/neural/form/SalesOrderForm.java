package net.zfp.form;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.view.ProductView;

@XmlRootElement(name="TransactionForm")
@XmlAccessorType(XmlAccessType.FIELD)

public class SalesOrderForm {
	
	@XmlElement(name="externalReferenceId")
	private String externalReferenceId;
	@XmlElement(name="paymentAmount")
	private Double paymentAmount;
	@XmlElement(name="paymentTimeestamp")
	private Date paymentTimeestamp;
	@XmlElement(name="paymentAudit")
	private String paymentAudit;
	@XmlElement(name="paymentStatus")
	private String paymentStatus;
	@XmlElement(name="paymentReturnCode")
	private String paymentReturnCode;
	@XmlElement(name="transactionReferenceId")
	private String transactionReferenceId;
	@XmlElement(name="cardReferenceId")
	private String cardReferenceId;
	@XmlElement(name="cardAuthNo")
	private String cardAuthNo;
	@XmlElement(name="paymentOrderNumber")
	private Long paymentOrderNumber;
	@XmlElement(name="cardType")
	private String cardType;
	@XmlElement(name="cardNumber")
	private String cardNumber;
	
	@XmlElement(name="orderNumber")
	private Long orderNumber;
	@XmlElement(name="referenceType")
	private String referenceType;
	
	@XmlElement(name="accountId")
	private Long accountId;
	@XmlElement(name="email")
	private String email;
	@XmlElement(name="firstName")
	private String firstName;
	@XmlElement(name="lastName")
	private String lastName;
	@XmlElement(name="address1")
	private String address1;
	@XmlElement(name="address2")
	private String address2;
	@XmlElement(name="city")
	private String city;
	@XmlElement(name="province")
	private String province;
	@XmlElement(name="postalCode")
	private String postalCode;
	@XmlElement(name="country")
	private String country;
	
	@XmlElement(name="subTotalCost")
	private Double subTotalCost;
	@XmlElement(name="tax")
	private Double tax;
	@XmlElement(name="taxType")
	private String taxType;
	@XmlElement(name="totalCost")
	private Double totalCost;
	@XmlElement(name="costUnit")
	private String costUnit;
	
	@XmlElement(name="locale")
	private String locale;
	
	@XmlElement(name="quantity")
	private Double quantity;
	
	@XmlElement(name="totalPoints")
	private Integer totalPoints;
	
	@XmlElement(name="domainName")
	private String domainName;
	
	@XmlElement(name="transactionType")
	private String transactionType;
		
	@XmlElement(name="orderStatus")
	private String orderStatus;
	
	@XmlElement(name="orderPaymentStatus")
	private String orderPaymentStatus;
	
	public SalesOrderForm() {
	}

	
	public String getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}


	public String getOrderPaymentStatus() {
		return orderPaymentStatus;
	}


	public void setOrderPaymentStatus(String orderPaymentStatus) {
		this.orderPaymentStatus = orderPaymentStatus;
	}


	public String getExternalReferenceId() {
		return externalReferenceId;
	}

	public void setExternalReferenceId(String externalReferenceId) {
		this.externalReferenceId = externalReferenceId;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getPaymentTimeestamp() {
		return paymentTimeestamp;
	}

	public void setPaymentTimeestamp(Date paymentTimeestamp) {
		this.paymentTimeestamp = paymentTimeestamp;
	}

	public String getPaymentAudit() {
		return paymentAudit;
	}

	public void setPaymentAudit(String paymentAudit) {
		this.paymentAudit = paymentAudit;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentReturnCode() {
		return paymentReturnCode;
	}

	public void setPaymentReturnCode(String paymentReturnCode) {
		this.paymentReturnCode = paymentReturnCode;
	}

	public String getTransactionReferenceId() {
		return transactionReferenceId;
	}

	public void setTransactionReferenceId(String transactionReferenceId) {
		this.transactionReferenceId = transactionReferenceId;
	}

	public String getCardReferenceId() {
		return cardReferenceId;
	}

	public void setCardReferenceId(String cardReferenceId) {
		this.cardReferenceId = cardReferenceId;
	}

	public String getCardAuthNo() {
		return cardAuthNo;
	}

	public void setCardAuthNo(String cardAuthNo) {
		this.cardAuthNo = cardAuthNo;
	}
	
	public Long getPaymentOrderNumber() {
		return paymentOrderNumber;
	}

	public void setPaymentOrderNumber(Long paymentOrderNumber) {
		this.paymentOrderNumber = paymentOrderNumber;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getSubTotalCost() {
		return subTotalCost;
	}

	public void setSubTotalCost(Double subTotalCost) {
		this.subTotalCost = subTotalCost;
	}
	

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public String getCostUnit() {
		return costUnit;
	}

	public void setCostUnit(String costUnit) {
		this.costUnit = costUnit;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
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

	
	
}
