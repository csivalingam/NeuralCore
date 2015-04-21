package net.zfp.entity.offer;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.DomainEntity;
import net.zfp.entity.Status;
import net.zfp.entity.community.Community;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="OfferTemplate")
@XmlRootElement(name="OfferTemplate")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferTemplate extends DomainEntity {
	
	private static final long serialVersionUID = 719164429415546950L;
	
	
	@XmlElement(name="OfferType")
	@ManyToOne
	@JoinColumn(name="offerTypeId")
	private OfferType offerType;
	
	@XmlElement(name="OfferType")
	@ManyToOne
	@JoinColumn(name="offerBehaviorId")
	private OfferBehavior offerBehavior;
	
	public OfferTemplate() { }

	public OfferType getOfferType() {
		return offerType;
	}

	public void setOfferType(OfferType offerType) {
		this.offerType = offerType;
	}

	public OfferBehavior getOfferBehavior() {
		return offerBehavior;
	}

	public void setOfferBehavior(OfferBehavior offerBehavior) {
		this.offerBehavior = offerBehavior;
	}
	
	

}

