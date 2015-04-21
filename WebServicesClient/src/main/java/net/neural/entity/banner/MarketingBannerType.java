package net.zfp.entity.banner;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="MarketingBannerType")
@XmlRootElement(name="MarketingBannerType")
@XmlAccessorType(XmlAccessType.FIELD)
public class MarketingBannerType extends DomainEntity {
	
	private static final long serialVersionUID = 819164129415846950L;
	
	@XmlAttribute(name="type")
	private String type;
	
	public MarketingBannerType() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
