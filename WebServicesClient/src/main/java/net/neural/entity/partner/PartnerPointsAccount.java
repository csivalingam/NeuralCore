package net.zfp.entity.partner;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.wallet.PointsType;

@Entity(name="PartnerPointsAccount")
@XmlRootElement(name="PartnerPointsAccount")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartnerPointsAccount extends BaseEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="balance")
	private Integer balance;
		
	@XmlElement(name="BusinessPartner")
	@OneToOne
	@JoinColumn(name="businessPartnerId")
	private BusinessPartner businessPartner;
	
	@XmlElement(name="PointsType")
	@OneToOne
	@JoinColumn(name="pointsTypeId")
	private PointsType pointsType;
	
	@XmlAttribute(name="pricePerPoint")
	private Double pricePerPoint;
	
	@XmlAttribute(name="depletionThreshold")
	private Integer depletionThreshold;
		
	public PartnerPointsAccount() { }

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public BusinessPartner getBusinessPartner() {
		return businessPartner;
	}

	public void setBusinessPartner(BusinessPartner businessPartner) {
		this.businessPartner = businessPartner;
	}

	public Double getPricePerPoint() {
		return pricePerPoint;
	}

	public void setPricePerPoint(Double pricePerPoint) {
		this.pricePerPoint = pricePerPoint;
	}

	public Integer getDepletionThreshold() {
		return depletionThreshold;
	}

	public void setDepletionThreshold(Integer depletionThreshold) {
		this.depletionThreshold = depletionThreshold;
	}

	public PointsType getPointsType() {
		return pointsType;
	}

	public void setPointsType(PointsType pointsType) {
		this.pointsType = pointsType;
	}
	
	
	
}

