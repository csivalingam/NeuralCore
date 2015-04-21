package net.zfp.entity.salesorder;

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
import net.zfp.entity.Status;
import net.zfp.entity.User;
import net.zfp.entity.community.Community;

@Entity(name="SalesOrder" )
@XmlRootElement(name="SalesOrder")
@XmlAccessorType(XmlAccessType.FIELD)

public class SalesOrder extends BaseEntity{ 

	private static final long serialVersionUID = 6839277385951612500L;
	
	@XmlAttribute(name="orderNumber")
	private Long orderNumber;
	
	@XmlElement(name="SalesOrderReferenceType")
	@OneToOne
	@JoinColumn(name="referenceType")
	private SalesOrderReferenceType salesOrderReferenceType;
	
	@XmlElement(name="paymentTransaction")
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="paymentOrderNumber", referencedColumnName= "paymentOrderNumber")
	private PaymentTransaction payment;
	
	@XmlElement(name="user")
	@OneToOne
	@JoinColumn(name="accountId")
	private User user;
	
	@XmlAttribute(name="totalCost")
	private Double totalCost;
	@XmlAttribute(name="tax")
	private Double tax;
	@XmlAttribute(name="taxType")
	private String taxType;
	@XmlAttribute(name="subTotalCost")
	private Double subTotalCost;
	@XmlAttribute(name="costUnit")
	private String costUnit;
	@XmlAttribute(name="quantity")
	private Double quantity;
	@XmlAttribute(name="totalPoints")
	private Integer totalPoints;
	
	@XmlAttribute(name="receiptUrl")
	private String receiptUrl;
	
	@XmlElement(name="Community")
	@OneToOne
	@JoinColumn(name="communityId")
	private Community community;
		
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="orderStatus")
	private Status orderStatus;
	
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="paymentStatus")
	private Status paymentStatus;
	
	public SalesOrder() {
	}
	
	
	public Status getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(Status orderStatus) {
		this.orderStatus = orderStatus;
	}


	public Status getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(Status paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	public PaymentTransaction getPayment() {
		return payment;
	}

	public void setPayment(PaymentTransaction payment) {
		this.payment = payment;
	}
	
	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public SalesOrderReferenceType getSalesOrderReferenceType() {
		return salesOrderReferenceType;
	}

	public void setSalesOrderReferenceType(
			SalesOrderReferenceType salesOrderReferenceType) {
		this.salesOrderReferenceType = salesOrderReferenceType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
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

	public Double getSubTotalCost() {
		return subTotalCost;
	}

	public void setSubTotalCost(Double subTotalCost) {
		this.subTotalCost = subTotalCost;
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

	public String getReceiptUrl() {
		return receiptUrl;
	}

	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

}
