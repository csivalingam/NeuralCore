package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="DeviceView")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceView {
		
	@XmlElement
	private String name;
	@XmlElement
	private String code;
	@XmlElement
	private String status;
	@XmlElement
	private Integer type;
	@XmlElement
	private String smallImageUrl;
	@XmlElement
	private String largeImageUrl;
	@XmlElement
	private String shortDescription;
	@XmlElement
	private String longDescription;
	@XmlElement
	private Double amount;
	@XmlElement
	private Integer points;
	@XmlElement
	private Integer offerPoints;
	@XmlElement
	private String reference;
	@XmlElement
	private String externalReference;
	@XmlElement
	private String UPC;
	@XmlElement
	private String SKU;
	@XmlElement
	private String providerCode;
	@XmlElement
	private boolean defaultDevice;
	@XmlElement
	private Long id;
	@XmlElement
	private Boolean registered;
	
	@XmlElement
	private Long communityId;
	
	@XmlElement
	private Integer rank;
	
	@XmlElement
	private List<MobileDeviceView> mobileDeviceViews;
	
	public DeviceView() {}

	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Long getCommunityId() {
		return communityId;
	}


	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}


	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}


	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}


	


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getOfferPoints() {
		return offerPoints;
	}

	public void setOfferPoints(Integer offerPoints) {
		this.offerPoints = offerPoints;
	}

	public String getUPC() {
		return UPC;
	}

	public void setUPC(String uPC) {
		UPC = uPC;
	}

	public String getSKU() {
		return SKU;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}


	public String getExternalReference() {
		return externalReference;
	}


	public void setExternalReference(String externalReference) {
		this.externalReference = externalReference;
	}


	public Boolean getRegistered() {
		return registered;
	}


	public void setRegistered(Boolean registered) {
		this.registered = registered;
	}


	public List<MobileDeviceView> getMobileDeviceViews() {
		return mobileDeviceViews;
	}


	public void setMobileDeviceViews(List<MobileDeviceView> mobileDeviceViews) {
		this.mobileDeviceViews = mobileDeviceViews;
	}


	public boolean isDefaultDevice() {
		return defaultDevice;
	}


	public void setDefaultDevice(boolean defaultDevice) {
		this.defaultDevice = defaultDevice;
	}

	
}
