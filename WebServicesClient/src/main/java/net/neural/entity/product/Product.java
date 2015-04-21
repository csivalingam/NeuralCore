package net.zfp.entity.product;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.Status;
import net.zfp.entity.partner.BusinessPartner;
import net.zfp.entity.partner.BusinessPartnerType;

@Entity(name="Product")
@XmlRootElement(name="Product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product extends DomainEntity {


	private static final long serialVersionUID = 7913469140950533298L;
	
	@XmlAttribute(name="UPC")
	private String UPC;
	
	@XmlAttribute(name="name")
	private String name;

	@XmlAttribute(name="shortDescription")
	private String shortDescription;
	
	@XmlAttribute(name="longDescription")
	private String longDescription;
	
	@XmlAttribute(name="unitPrice")
	private Double unitPrice;
	
	@XmlAttribute(name="unitPoints")
	private Integer unitPoints;
	
	@XmlAttribute(name="smallImageUrl")
	private String smallImageUrl;
	
	@XmlAttribute(name="largeImageUrl")
	private String largeImageUrl;
	
	@XmlAttribute(name="mobileSmallImageUrl")
	private String mobileSmallImageUrl;
	
	@XmlAttribute(name="mobileLargeImageUrl")
	private String mobileLargeImageUrl;
	
	@XmlAttribute(name="translationKey")
	private String translationKey;
	
	@XmlAttribute(name="linkUrl")
	private String linkUrl;
	
	@XmlAttribute(name="productBenefits")
	private String productBenefits;
	
	@XmlElement(name="ProductType")
	@OneToOne
	@JoinColumn(name="productTypeId")
	private ProductType productType;
	
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="statusId")
	private Status status;
	
	@XmlAttribute(name="reference")
	private String reference;
	
	@XmlAttribute(name="saleStartDate")
	private Date saleStartDate;
	
	@XmlAttribute(name="saleEndDate")
	private Date saleEndDate;
	
	@XmlElement(name="BusinessPartner")
	@OneToOne
	@JoinColumn(name="manufacturerId")
	private BusinessPartner manufacturer;
	
	@XmlElement(name="BusinessPartner")
	@OneToOne
	@JoinColumn(name="supplierId")
	private BusinessPartner supplier;
	
	public Product() { }

	
	public String getUPC() {
		return UPC;
	}

	public void setUPC(String uPC) {
		UPC = uPC;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
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

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public Integer getUnitPoints() {
		return unitPoints;
	}

	public void setUnitPoints(Integer unitPoints) {
		this.unitPoints = unitPoints;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getProductBenefits() {
		return productBenefits;
	}

	public void setProductBenefits(String productBenefits) {
		this.productBenefits = productBenefits;
	}

	public String getTranslationKey() {
		return translationKey;
	}

	public void setTranslationKey(String translationKey) {
		this.translationKey = translationKey;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
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


	public String getReference() {
		return reference;
	}


	public void setReference(String reference) {
		this.reference = reference;
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


	public BusinessPartner getManufacturer() {
		return manufacturer;
	}


	public void setManufacturer(BusinessPartner manufacturer) {
		this.manufacturer = manufacturer;
	}


	public BusinessPartner getSupplier() {
		return supplier;
	}


	public void setSupplier(BusinessPartner supplier) {
		this.supplier = supplier;
	}
	

}