package net.zfp.entity.offer;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="OfferBehaviorAttribute")
@XmlRootElement(name="OfferBehaviorAttribute")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferBehaviorAttribute extends DomainEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlElement(name="Offer")
	@ManyToOne
	@JoinColumn(name="offerId")
	private Offer offer;
	
	@XmlElement(name="OfferBehavior")
	@ManyToOne
	@JoinColumn(name="behaviorId")
	private OfferBehavior offerBehavior;
	
	@XmlAttribute(name="behaviorAttribute")
	private String behaviorAttribute;
		
	public OfferBehaviorAttribute() {}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public OfferBehavior getOfferBehavior() {
		return offerBehavior;
	}

	public void setOfferBehavior(OfferBehavior offerBehavior) {
		this.offerBehavior = offerBehavior;
	}

	public String getBehaviorAttribute() {
		return behaviorAttribute;
	}

	public void setBehaviorAttribute(String behaviorAttribute) {
		this.behaviorAttribute = behaviorAttribute;
	}

	
	
}
