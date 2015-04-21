package net.zfp.entity.offer;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.Unit;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="OfferValue" )
@XmlRootElement(name="OfferValue")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferValue extends DomainEntity {
	
	private static final long serialVersionUID = 619164429415546950L;
	
	@XmlElement(name="Offer")
	@ManyToOne
	@JoinColumn(name="offerId")
	private Offer offer;
	
	@XmlAttribute(name="value")
	private Integer value;
	
	@XmlElement(name="Unit")
	@OneToOne
	@JoinColumn(name="valueUnitId")
	private Unit unit;
	
	public OfferValue() { }


	public Offer getOffer() {
		return offer;
	}


	public void setOffer(Offer offer) {
		this.offer = offer;
	}


	public Integer getValue() {
		return value;
	}


	public void setValue(Integer value) {
		this.value = value;
	}


	public Unit getUnit() {
		return unit;
	}


	public void setUnit(Unit unit) {
		this.unit = unit;
	}	
	
	
}

