package net.zfp.entity.offer;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.category.Category;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="OfferCategory")
@XmlRootElement(name="OfferCategory")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferCategory extends DomainEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlElement(name="Category")
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;
	
	@XmlElement(name="Offer")
	@ManyToOne
	@JoinColumn(name="offerId")
	private Offer offer;
	
	public OfferCategory() {}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	
	
	
}
