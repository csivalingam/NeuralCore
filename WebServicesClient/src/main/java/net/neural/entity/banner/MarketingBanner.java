package net.zfp.entity.banner;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.community.Community;
import net.zfp.entity.newscontent.NewsContentType;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="MarketingBanner")
@XmlRootElement(name="MarketingBanner")
@XmlAccessorType(XmlAccessType.FIELD)
public class MarketingBanner extends DomainEntity {
	
	private static final long serialVersionUID = 819164129415846950L;
	
	@XmlAttribute(name="name")
	private String name;
	
	@XmlElement(name="Community")
	@OneToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	@XmlElement(name="MarketingBannerType")
	@OneToOne
	@JoinColumn(name="typeId")
	private MarketingBannerType type;
	
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	
	@XmlAttribute(name="isExternal")
	private Boolean isExternal;
	
	@XmlAttribute(name="reference")
	private String reference;
	
	public MarketingBanner() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public MarketingBannerType getType() {
		return type;
	}

	public void setType(MarketingBannerType type) {
		this.type = type;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(Boolean isExternal) {
		this.isExternal = isExternal;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	
	
}
