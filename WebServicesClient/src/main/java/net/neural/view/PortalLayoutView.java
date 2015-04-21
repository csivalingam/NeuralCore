package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.portal.PortalLayout;
import net.zfp.util.AppConstants;
import net.zfp.util.ImageUtil;

@XmlRootElement(name="PortalLayoutView")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class PortalLayoutView {
	
	@XmlElement
	private String backgroundImageURL;
	
	@XmlElement
	private String mobileBackgroundImageURL;
	
	@XmlElement
	private String backgroundBlurredImageURL;
	
	@XmlElement
	private String mobileBackgroundBlurredImageURL;
	
	@XmlElement
	private String cobrandedURL;
	
	@XmlElement
	private String zfpLogoURL;
	
	@XmlElement
	private String slogan;
	
	@XmlElement
	private String sloganFontColor;
	
	@XmlElement
	private String speech;
	
	@XmlElement
	private ResultView result;
	
	public PortalLayoutView(){
	
	}
	
	public PortalLayoutView(Integer constant){
		this.backgroundImageURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(AppConstants.DEFAULTBACKGROUNDIMAGEURL);
		this.backgroundBlurredImageURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(AppConstants.DEFAULTBACKGROUNDBLURREDIMAGEURL);
		this.mobileBackgroundImageURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(AppConstants.MOBILEDEFAULTBACKGROUNDIMAGEURL);
		this.mobileBackgroundBlurredImageURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(AppConstants.MOBILEDEFAULTBACKGROUNDBLURREDIMAGEURL);
		this.slogan = AppConstants.DEFAULT_SLOGAN;
		this.sloganFontColor = AppConstants.DEFAULT_SLOGAN_COLOR;
		
	}
	
	public PortalLayoutView(PortalLayout pl){
		
		if (pl.getBackgroundImageURL() != null) this.backgroundImageURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pl.getBackgroundImageURL());
		else this.backgroundImageURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(AppConstants.DEFAULTBACKGROUNDIMAGEURL);
		
		if (pl.getBackgroundBlurredImageURL() != null) this.backgroundBlurredImageURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pl.getBackgroundBlurredImageURL());
		else this.backgroundBlurredImageURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(AppConstants.DEFAULTBACKGROUNDBLURREDIMAGEURL);
		
		if (pl.getMobileBackgroundImageURL() != null) this.mobileBackgroundImageURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pl.getMobileBackgroundImageURL());
		else this.mobileBackgroundImageURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(AppConstants.MOBILEDEFAULTBACKGROUNDIMAGEURL);
		
		if (pl.getMobileBackgroundBlurredImageURL() != null) this.mobileBackgroundBlurredImageURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pl.getMobileBackgroundBlurredImageURL());
		else this.mobileBackgroundBlurredImageURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(AppConstants.MOBILEDEFAULTBACKGROUNDBLURREDIMAGEURL);
		
		if (pl.getSlogan() != null) this.slogan = pl.getSlogan();
		else this.slogan = AppConstants.DEFAULT_SLOGAN;
		
		if (pl.getSloganFontColor() != null) this.sloganFontColor = pl.getSloganFontColor();
		else this.sloganFontColor = AppConstants.DEFAULT_SLOGAN_COLOR;
		
		if (pl.getSpeech() != null) this.speech = pl.getSpeech();
		
		if (pl.getCobranded() != null && pl.getCobranded()) this.cobrandedURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pl.getCobrandedURL());
		
		
	}
	public String getBackgroundImageURL() {
		return backgroundImageURL;
	}

	public void setBackgroundImageURL(String backgroundImageURL) {
		this.backgroundImageURL = backgroundImageURL;
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

	public String getZfpLogoURL() {
		return zfpLogoURL;
	}

	public void setZfpLogoURL(String zfpLogoURL) {
		this.zfpLogoURL = zfpLogoURL;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getSloganFontColor() {
		return sloganFontColor;
	}

	public void setSloganFontColor(String sloganFontColor) {
		this.sloganFontColor = sloganFontColor;
	}

	public String getSpeech() {
		return speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
