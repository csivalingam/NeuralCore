package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.charity.Fundraiser;
import net.zfp.util.AppConstants;
import net.zfp.util.DateUtil;
import net.zfp.util.ImageUtil;
import net.zfp.util.TextUtil;


@XmlRootElement(name="CharityView")
@XmlAccessorType(XmlAccessType.FIELD)
public class CharityView {
	
	@XmlAttribute(name="id")
	private Long id;
	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="description")
	private String description;
	@XmlAttribute(name="longDescription")
	private String longDescription;
	@XmlAttribute(name="offerValueType")
	private String offerValueType;
	@XmlAttribute(name="offerCoins")
	private Integer offerCoins;
	@XmlAttribute(name="offerDollar")
	private Integer offerDollar;
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	@XmlAttribute(name="largeImageUrl")
	private String largeImageUrl;
	@XmlAttribute(name="foundationImageUrl")
	private String foundationImageUrl;
	@XmlAttribute(name="endDate")
	private String endDate;
	
	public CharityView() {}

	public CharityView(Fundraiser charity){
		this.id = charity.getId();
		this.name = TextUtil.parseString(charity.getName());
		this.description = TextUtil.parseString(charity.getDescription());
		this.longDescription = TextUtil.parseString(charity.getLongDescription());
		this.imageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(charity.getImageUrl());
		this.largeImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(charity.getLargeImageUrl());
		this.endDate = DateUtil.printCalendar4(charity.getEndDate());
		if (charity.getBusinessPartner() != null) this.foundationImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(charity.getBusinessPartner().getBannerSmallImageUrl());
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Integer getOfferCoins() {
		return offerCoins;
	}

	public void setOfferCoins(Integer offerCoins) {
		this.offerCoins = offerCoins;
	}

	public String getLargeImageUrl() {
		return largeImageUrl;
	}

	public void setLargeImageUrl(String largeImageUrl) {
		this.largeImageUrl = largeImageUrl;
	}

	public String getFoundationImageUrl() {
		return foundationImageUrl;
	}

	public void setFoundationImageUrl(String foundationImageUrl) {
		this.foundationImageUrl = foundationImageUrl;
	}

	public String getOfferValueType() {
		return offerValueType;
	}

	public void setOfferValueType(String offerValueType) {
		this.offerValueType = offerValueType;
	}

	public Integer getOfferDollar() {
		return offerDollar;
	}

	public void setOfferDollar(Integer offerDollar) {
		this.offerDollar = offerDollar;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
