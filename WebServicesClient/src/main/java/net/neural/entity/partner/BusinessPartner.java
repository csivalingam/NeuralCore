package net.zfp.entity.partner;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="BusinessPartner")
@XmlRootElement(name="BusinessPartner")
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessPartner extends DomainEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="bannerSmallImageUrl")
	private String bannerSmallImageUrl;
	
	@XmlAttribute(name="bannerLargeImageUrl")
	private String bannerLargeImageUrl;
	
	@XmlAttribute(name="bannerUrl")
	private String bannerUrl;
	
	
	@XmlElement(name="BusinessPartnerType")
	@OneToOne
	@JoinColumn(name="typeId")
	private BusinessPartnerType businessPartnerType;
		
	public BusinessPartner() { }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBannerSmallImageUrl() {
		return bannerSmallImageUrl;
	}

	public void setBannerSmallImageUrl(String bannerSmallImageUrl) {
		this.bannerSmallImageUrl = bannerSmallImageUrl;
	}

	public String getBannerLargeImageUrl() {
		return bannerLargeImageUrl;
	}

	public void setBannerLargeImageUrl(String bannerLargeImageUrl) {
		this.bannerLargeImageUrl = bannerLargeImageUrl;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public BusinessPartnerType getBusinessPartnerType() {
		return businessPartnerType;
	}

	public void setBusinessPartnerType(BusinessPartnerType businessPartnerType) {
		this.businessPartnerType = businessPartnerType;
	}
	
	
}
