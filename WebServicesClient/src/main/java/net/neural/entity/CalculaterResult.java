package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="CalculaterResult" )
@XmlRootElement(name="calculaterResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class CalculaterResult extends BaseEntity{

	private static final long serialVersionUID = -2415764286751429164L;

	@XmlAttribute(name="accountId")
	private Long accountId;
	
	@XmlAttribute(name="email")
	private String email;
	
	@XmlAttribute(name="accountId")
	private Double carbon;
	
	@XmlAttribute(name="payment")
	private double payment;
	
	@XmlAttribute(name="tripType")
	private String tripType;

	public CalculaterResult() {
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getCarbon() {
		return carbon;
	}

	public void setCarbon(Double carbon) {
		this.carbon = carbon;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	
	
}
