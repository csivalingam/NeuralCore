package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@Entity(name="PaymentTransaction" )
@XmlRootElement(name="paymentTransaction")
@XmlAccessorType(XmlAccessType.FIELD)

public class PaymentTransaction extends BaseEntity{

	private static final long serialVersionUID = 4415484578354619089L;

	@XmlAttribute(name="externalReferenceId")
	private String externalReferenceId;
	@XmlAttribute(name="paymentAmount")
	private Double paymentAmount;
	@XmlAttribute(name="paymentTimestamp")
	private Date paymentTimestamp;
	@XmlAttribute(name="paymentAudit")
	private String paymentAudit;
	@XmlAttribute(name="paymentStatus")
	private String paymentStatus;
	@XmlAttribute(name="paymentReturnCode")
	private String paymentReturnCode;
	@XmlAttribute(name="transactionReferenceId")
	private String transactionReferenceId;
	@XmlAttribute(name="CardReferenceId")
	private String CardReferenceId;
	@XmlAttribute(name="CardAuthNo")
	private String CardAuthNo;
	@XmlAttribute(name="paymentOrderNumber")
	private Long paymentOrderNumber;
	@XmlAttribute(name="cardType")
	private String cardType;
	@XmlAttribute(name="cardNumber")
	private String cardNumber;
	

	public PaymentTransaction() {
		super();
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
	public Date getPaymentTimestamp() {
		return paymentTimestamp;
	}
	public void setPaymentTimestamp(Date paymentTimestamp) {
		this.paymentTimestamp = paymentTimestamp;
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
		return CardReferenceId;
	}
	public void setCardReferenceId(String cardReferenceId) {
		CardReferenceId = cardReferenceId;
	}
	public String getCardAuthNo() {
		return CardAuthNo;
	}
	public void setCardAuthNo(String cardAuthNo) {
		CardAuthNo = cardAuthNo;
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
	
}
