package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.partner.BusinessPartner;
import net.zfp.util.AppConstants;


@XmlRootElement(name="PartnerView")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartnerView {
	
	@XmlAttribute(name="id")
	private Long id;
	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="bannerSmallImageUrl")
	private String bannerSmallImageUrl;
	@XmlAttribute(name="bannerLargeUrl")
	private String bannerLargeUrl;
	public PartnerView() {}
	
	public PartnerView(BusinessPartner bp){
		this.id = bp.getId();
		this.name = bp.getName();
		this.bannerSmallImageUrl = AppConstants.APACHE_IMAGE_LINK + bp.getBannerSmallImageUrl();
		this.bannerLargeUrl = AppConstants.APACHE_IMAGE_LINK + bp.getBannerLargeImageUrl();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getBannerLargeUrl() {
		return bannerLargeUrl;
	}

	public void setBannerLargeUrl(String bannerLargeUrl) {
		this.bannerLargeUrl = bannerLargeUrl;
	}
	
	
}
