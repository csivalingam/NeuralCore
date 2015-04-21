package net.zfp.view;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.offer.Offer;
import net.zfp.entity.product.Product;
import net.zfp.util.AppConstants;
import net.zfp.util.ImageUtil;


@XmlRootElement(name="Product")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class ProductView {
	
	@XmlElement
	private Long id;
	@XmlElement
	private String name;
	@XmlElement
	private String UPC;
	@XmlElement
	private String sku;
	@XmlElement
	private String shortDescription;
	@XmlElement
	private String longDescription;
	@XmlElement
	private String type;
	
	@XmlElement
	private Double quantity;
	
	@XmlElement
	private String smallImageUrl;
	@XmlElement
	private String largeImageUrl;
	@XmlElement
	private String mobileSmallImageUrl;
	@XmlElement
	private String mobileLargeImageUrl;
	@XmlElement
	private Double unitPrice;
	@XmlElement
	private Integer unitPoints;
	@XmlElement
	private Integer totalPoints;
	@XmlElement
	private Double carbonValue;
	@XmlElement
	private Double priceValue;
	
	@XmlElement
	private String productBenefits;
	
	@XmlElement
	private String linkUrl;
	
	@XmlElement
	private Long offerPoints;
	
	@XmlElement
	private Date saleStartDate;
	
	@XmlElement
	private Date saleEndDate;
	
	@XmlElement
	private String manufacturerName;
	
	@XmlElement
	private String manufacturerImageURL;
	
	@XmlElement
	private OfferView offer;
	
	@XmlElement
	private ResultView result;
	
	public ProductView() {}
	
	
	public ProductView(Product product) {
		this.id = product.getId();
		this.UPC = product.getUPC();
		this.name = product.getName();
		this.shortDescription = product.getShortDescription();
		this.longDescription = product.getLongDescription();
		this.type = product.getProductType().getType();
		if (product.getSmallImageUrl() != null && product.getSmallImageUrl().contains("/portal-core/")){
			//this.smallImageUrl = ImageUtil.parseImageUrl(product.getSmallImageUrl());
			this.smallImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(product.getSmallImageUrl().replaceAll("/portal-core", ""));
		}else{
			this.smallImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(product.getSmallImageUrl());
		}
		if (product.getLargeImageUrl() != null && product.getLargeImageUrl().contains("/portal-core/")){
			//this.largeImageUrl = ImageUtil.parseImageUrl(product.getLargeImageUrl());
			this.largeImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(product.getLargeImageUrl().replaceAll("/portal-core", ""));
		}else{
			this.largeImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(product.getLargeImageUrl());
		}
		if (product.getMobileSmallImageUrl() != null && product.getMobileSmallImageUrl().contains("/portal-core/")){
			//this.mobileSmallImageUrl = ImageUtil.parseImageUrl(product.getMobileSmallImageUrl());
			this.mobileSmallImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(product.getMobileSmallImageUrl().replaceAll("/portal-core", ""));
		}else{
			this.mobileSmallImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(product.getMobileSmallImageUrl());
		}
		if (product.getMobileLargeImageUrl() != null && product.getMobileLargeImageUrl().contains("/portal-core/")){
			//this.mobileLargeImageUrl = ImageUtil.parseImageUrl(product.getMobileLargeImageUrl());
			this.mobileLargeImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(product.getMobileLargeImageUrl().replaceAll("/portal-core", ""));
		}else{
			this.mobileLargeImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(product.getMobileLargeImageUrl());
		}
		this.unitPrice = product.getUnitPrice();
		this.unitPoints = product.getUnitPoints();
		this.productBenefits = product.getProductBenefits();
		this.linkUrl = product.getLinkUrl();
		
		if (product.getManufacturer() != null){
			this.manufacturerName = product.getManufacturer().getName();
			if (product.getManufacturer().getBannerSmallImageUrl() != null && !product.getManufacturer().getBannerSmallImageUrl().equals("")) this.manufacturerImageURL = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(product.getManufacturer().getBannerSmallImageUrl());
		}
		if (product.getSaleStartDate() != null) this.saleStartDate = product.getSaleStartDate();
		if (product.getSaleEndDate() != null) this.saleEndDate = product.getSaleEndDate();
		
	}
	
	
	public String getUPC() {
		return UPC;
	}

	public void setUPC(String uPC) {
		UPC = uPC;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getCarbonValue() {
		return carbonValue;
	}

	public void setCarbonValue(Double carbonValue) {
		this.carbonValue = carbonValue;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Integer getUnitPoints() {
		return unitPoints;
	}

	public void setUnitPoints(Integer unitPoints) {
		this.unitPoints = unitPoints;
	}

	public String getProductBenefits() {
		return productBenefits;
	}

	public void setProductBenefits(String productBenefits) {
		this.productBenefits = productBenefits;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

	public String getLargeImageUrl() {
		return largeImageUrl;
	}

	public void setLargeImageUrl(String largeImageUrl) {
		this.largeImageUrl = largeImageUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Double getPriceValue() {
		return priceValue;
	}

	public void setPriceValue(Double priceValue) {
		this.priceValue = priceValue;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}


	public String getMobileSmallImageUrl() {
		return mobileSmallImageUrl;
	}


	public void setMobileSmallImageUrl(String mobileSmallImageUrl) {
		this.mobileSmallImageUrl = mobileSmallImageUrl;
	}


	public String getMobileLargeImageUrl() {
		return mobileLargeImageUrl;
	}


	public void setMobileLargeImageUrl(String mobileLargeImageUrl) {
		this.mobileLargeImageUrl = mobileLargeImageUrl;
	}


	public Long getOfferPoints() {
		return offerPoints;
	}


	public void setOfferPoints(Long offerPoints) {
		this.offerPoints = offerPoints;
	}


	public Date getSaleStartDate() {
		return saleStartDate;
	}


	public void setSaleStartDate(Date saleStartDate) {
		this.saleStartDate = saleStartDate;
	}


	public Date getSaleEndDate() {
		return saleEndDate;
	}


	public void setSaleEndDate(Date saleEndDate) {
		this.saleEndDate = saleEndDate;
	}


	public String getManufacturerName() {
		return manufacturerName;
	}


	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}


	public String getManufacturerImageURL() {
		return manufacturerImageURL;
	}


	public void setManufacturerImageURL(String manufacturerImageURL) {
		this.manufacturerImageURL = manufacturerImageURL;
	}


	public OfferView getOffer() {
		return offer;
	}


	public void setOffer(OfferView offer) {
		this.offer = offer;
	}


	public ResultView getResult() {
		return result;
	}


	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
