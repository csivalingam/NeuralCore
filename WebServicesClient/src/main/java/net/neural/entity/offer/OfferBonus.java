package net.zfp.entity.offer;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.Operator;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="OfferBonus")
@XmlRootElement(name="OfferBonus")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferBonus extends BaseEntity {
	
	private static final long serialVersionUID = 719164429415546950L;
	
	
	@XmlElement(name="Offer")
	@ManyToOne
	@JoinColumn(name="offerId")
	private Offer offer;
	
	@XmlElement(name="Operator")
	@ManyToOne
	@JoinColumn(name="operatorId")
	private Operator operator;
	
	@XmlAttribute(name="startDate")
	private Date startDate;
	
	@XmlAttribute(name="endDate")
	private Date endDate;
	
	@XmlAttribute(name="bonusValue")
	private Integer bonusValue;
	
	@XmlAttribute(name="promoCodeRequired")
	private Boolean promoCodeRequired;
	
	public OfferBonus() { }

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getBonusValue() {
		return bonusValue;
	}

	public void setBonusValue(Integer bonusValue) {
		this.bonusValue = bonusValue;
	}

	public Boolean getPromoCodeRequired() {
		return promoCodeRequired;
	}

	public void setPromoCodeRequired(Boolean promoCodeRequired) {
		this.promoCodeRequired = promoCodeRequired;
	}
	
	

}

