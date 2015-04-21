package net.zfp.view;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.offer.Offer;
import net.zfp.util.AppConstants;
import net.zfp.util.DateUtil;
import net.zfp.util.ImageUtil;
import net.zfp.util.TextUtil;


@XmlRootElement(name="Offer")
@XmlAccessorType(XmlAccessType.FIELD)

public class OfferView {
	
	@XmlElement
	private Long id;
	
	@XmlElement
	private Date startDate;
	
	@XmlElement
	private Date endDate;
	
	@XmlElement
	private Long code;
	
	@XmlElement
	private String name;
	
	@XmlElement
	private Long value;
	
	@XmlElement
	private String valueType;
	
	@XmlElement
	private Integer ratioValue;
	
	@XmlElement
	private String ratioValueUnit;
	
	@XmlElement
	private String units;
	
	@XmlElement
	private String description;
	
	@XmlElement
	private String longDescription;
	
	@XmlElement
	private String smallImageUrl;
	
	@XmlElement
	private String largeImageUrl;
	
	@XmlElement
	private String typeName;
	
	@XmlElement
	private Long programId;
	
	@XmlElement
	private Boolean isFundraising;
	
	@XmlElement
	private String productUPC;
	
	@XmlElement
	private Integer programViewType;
	
	@XmlElement
	private Integer categoryType;
	
	@XmlElement
	private Long communityId;

	@XmlElement
	private String colorCode;
	
	@XmlElement
	private String sponsorImageUrl;
	
	@XmlElement
	private String expiryDate;
	
	@XmlElement
	private ResultView result;
	
	public OfferView(){}
	
	public OfferView(Offer offer){
		this.id = offer.getId();
		this.code = offer.getCode();
		this.startDate = offer.getStartDate();
		this.endDate =offer.getEndDate();
		this.expiryDate = DateUtil.printCalendar4(offer.getEndDate());
		this.name = TextUtil.parseString(offer.getName());
		this.value = offer.getValue();
		if (offer.getOfferValueType() != null) this.valueType = offer.getOfferValueType().getType();
		
		if (offer.getUnit() != null ) this.units = offer.getUnit().getName();
		else this.units= "points";
		this.description = TextUtil.parseString(offer.getDescription());
		this.longDescription = TextUtil.parseString(offer.getLongDescription());
		if (offer.getSmallImageUrl() != null) this.smallImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(offer.getSmallImageUrl());
		if (offer.getLargeImageUrl() != null) this.largeImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(offer.getLargeImageUrl());
		if (offer.getOfferTemplate() != null){
			if (offer.getOfferTemplate().getOfferType() != null) this.typeName =offer.getOfferTemplate().getOfferType().getName();
		}
		if (offer.getCommunity() != null) this.communityId = offer.getCommunity().getId();
		this.colorCode = offer.getColorCode();
		
		if (offer.getBusinessPartner() != null) this.sponsorImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(offer.getBusinessPartner().getBannerSmallImageUrl());
		else this.sponsorImageUrl = null;
		
	}

	
	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getSponsorImageUrl() {
		return sponsorImageUrl;
	}

	public void setSponsorImageUrl(String sponsorImageUrl) {
		this.sponsorImageUrl = sponsorImageUrl;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

	public Integer getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	public Integer getProgramViewType() {
		return programViewType;
	}

	public void setProgramViewType(Integer programViewType) {
		this.programViewType = programViewType;
	}

	public String getProductUPC() {
		return productUPC;
	}

	public void setProductUPC(String productUPC) {
		this.productUPC = productUPC;
	}

	public Boolean getIsFundraising() {
		return isFundraising;
	}

	public void setIsFundraising(Boolean isFundraising) {
		this.isFundraising = isFundraising;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public Integer getRatioValue() {
		return ratioValue;
	}

	public void setRatioValue(Integer ratioValue) {
		this.ratioValue = ratioValue;
	}

	public String getRatioValueUnit() {
		return ratioValueUnit;
	}

	public void setRatioValueUnit(String ratioValueUnit) {
		this.ratioValueUnit = ratioValueUnit;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
