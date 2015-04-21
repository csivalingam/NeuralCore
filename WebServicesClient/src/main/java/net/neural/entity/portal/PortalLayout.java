package net.zfp.entity.portal;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.community.CommunityPortalTheme;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="PortalLayout")
@XmlRootElement(name="PortalLayout")
@XmlAccessorType(XmlAccessType.FIELD)
public class PortalLayout extends DomainEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="backgroundImageURL")
	private String backgroundImageURL;
	
	@XmlAttribute(name="mobileBackgroundImageURL")
	private String mobileBackgroundImageURL;
	
	@XmlAttribute(name="backgroundBlurredImageURL")
	private String backgroundBlurredImageURL;
	
	@XmlAttribute(name="mobileBackgroundBlurredImageURL")
	private String mobileBackgroundBlurredImageURL;
	
	@XmlAttribute(name="cobrandedURL")
	private String cobrandedURL;
	
	@XmlAttribute(name="zfpLogoURL")
	private String zfpLogoURL;
	
	@XmlElement(name="CommunityPortalTheme")
	@OneToOne
	@JoinColumn(name="communityPortalThemeId")
	private CommunityPortalTheme communityPortalTheme;
	
	@XmlAttribute(name="cobranded")
	private Boolean cobranded;
		
	@XmlAttribute(name="slogan")
	private String slogan;
	
	@XmlAttribute(name="sloganFontColor")
	private String sloganFontColor;
	
	@XmlAttribute(name="speech")
	private String speech;
	
	public PortalLayout() { }

	public String getZfpLogoURL() {
		return zfpLogoURL;
	}

	public void setZfpLogoURL(String zfpLogoURL) {
		this.zfpLogoURL = zfpLogoURL;
	}

	public String getBackgroundImageURL() {
		return backgroundImageURL;
	}

	public void setBackgroundImageURL(String backgroundImageURL) {
		this.backgroundImageURL = backgroundImageURL;
	}

	public CommunityPortalTheme getCommunityPortalTheme() {
		return communityPortalTheme;
	}

	public void setCommunityPortalTheme(CommunityPortalTheme communityPortalTheme) {
		this.communityPortalTheme = communityPortalTheme;
	}

	public Boolean getCobranded() {
		return cobranded;
	}

	public void setCobranded(Boolean cobranded) {
		this.cobranded = cobranded;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getSpeech() {
		return speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}

	public String getMobileBackgroundImageURL() {
		return mobileBackgroundImageURL;
	}

	public void setMobileBackgroundImageURL(String mobileBackgroundImageURL) {
		this.mobileBackgroundImageURL = mobileBackgroundImageURL;
	}

	public String getBackgroundBlurredImageURL() {
		return backgroundBlurredImageURL;
	}

	public void setBackgroundBlurredImageURL(String backgroundBlurredImageURL) {
		this.backgroundBlurredImageURL = backgroundBlurredImageURL;
	}

	public String getMobileBackgroundBlurredImageURL() {
		return mobileBackgroundBlurredImageURL;
	}

	public void setMobileBackgroundBlurredImageURL(
			String mobileBackgroundBlurredImageURL) {
		this.mobileBackgroundBlurredImageURL = mobileBackgroundBlurredImageURL;
	}

	public String getCobrandedURL() {
		return cobrandedURL;
	}

	public void setCobrandedURL(String cobrandedURL) {
		this.cobrandedURL = cobrandedURL;
	}

	public String getSloganFontColor() {
		return sloganFontColor;
	}

	public void setSloganFontColor(String sloganFontColor) {
		this.sloganFontColor = sloganFontColor;
	}
	
	
}
