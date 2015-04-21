package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.banner.MarketingBanner;
import net.zfp.util.AppConstants;

@XmlRootElement(name="BannerView")
@XmlAccessorType(XmlAccessType.FIELD)
public class BannerView {

	@XmlAttribute(name="isExternal")
	private Boolean isExternal;
	
	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="reference")
	private String reference;
	
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	
	public BannerView(){}
	
	public BannerView(MarketingBanner mb){
		this.isExternal = mb.getIsExternal();
		this.name = mb.getName();
		this.reference = mb.getReference();
		
		if (mb.getImageUrl() != null && mb.getImageUrl().charAt(0) == '/') this.imageUrl = AppConstants.APACHE_IMAGE_LINK + mb.getImageUrl();
		else this.imageUrl = mb.getImageUrl();
	}
	public Boolean getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(Boolean isExternal) {
		this.isExternal = isExternal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
