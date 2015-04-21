package net.zfp.entity.offer;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="OfferPromoCode")
@XmlRootElement(name="OfferPromoCode")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferPromoCode extends BaseEntity {
	
	private static final long serialVersionUID = 619164429415546950L;
	
	@XmlElement(name="OfferBonus")
	@ManyToOne
	@JoinColumn(name="offerBonusId")
	private OfferBonus offerBonus;
	
	@XmlAttribute(name="promoCode")
	private String promoCode;
	
	public OfferPromoCode() { }

	public OfferBonus getOfferBonus() {
		return offerBonus;
	}

	public void setOfferBonus(OfferBonus offerBonus) {
		this.offerBonus = offerBonus;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

}

