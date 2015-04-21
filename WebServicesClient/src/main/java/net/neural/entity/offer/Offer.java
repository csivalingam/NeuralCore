package net.zfp.entity.offer;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.Status;
import net.zfp.entity.Unit;
import net.zfp.entity.community.Community;
import net.zfp.entity.partner.BusinessPartner;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.wallet.PointsType;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="Offer" )
@XmlRootElement(name="Offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Offer extends BaseEntity {
	
	private static final long serialVersionUID = 719164429415546950L;
	
	@XmlAttribute(name="startDate")
	private Date startDate;
	
	@XmlAttribute(name="endDate")
	private Date endDate;
	
	@XmlAttribute(name="code")
	private Long code;
	
	@XmlAttribute(name="colorCode")
	private String colorCode;
	
	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="nameTranslationKey")
	private String nameTranslationKey;
	
	@XmlAttribute(name="value")
	private Long value;
	
	@XmlElement(name="OfferValueType")
	@OneToOne
	@JoinColumn(name="valueTypeId")
	private OfferValueType offerValueType;
	
	@XmlElement(name="Unit")
	@OneToOne
	@JoinColumn(name="unitId")
	private Unit unit;
	
	@XmlAttribute(name="description")
	private String description;
	
	@XmlAttribute(name="longDescription")
	private String longDescription;
	
	@XmlAttribute(name="smallImageUrl")
	private String smallImageUrl;
	
	@XmlAttribute(name="largeImageUrl")
	private String largeImageUrl;
	
	@XmlAttribute(name="mobileSmallImageUrl")
	private String mobileSmallImageUrl;
	
	@XmlAttribute(name="mobileLargeImageUrl")
	private String mobileLargeImageUrl;
	
	@XmlElement(name="Segment")
	@OneToOne
	@JoinColumn(name="segmentId")
	private Segment segment;
	
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="statusId")
	private Status status;
	
	@XmlElement(name="BusinessPartner")
	@OneToOne
	@JoinColumn(name="businessPartnerId")
	private BusinessPartner businessPartner;
	
	@XmlElement(name="OfferTemplate")
	@ManyToOne
	@JoinColumn(name="offerTemplateId")
	private OfferTemplate offerTemplate;
	
	@XmlElement(name="Community")
	@ManyToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	@XmlAttribute(name="suppress")
	private Boolean suppress;
	
	@XmlElement(name="PointsType")
	@OneToOne
	@JoinColumn(name="pointsTypeId")
	private PointsType pointsType;
	
	public String getLongDescription() {
		return longDescription;
	}


	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
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


	public Offer() { }

	
	
	public Segment getSegment() {
		return segment;
	}


	public void setSegment(Segment segment) {
		this.segment = segment;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public String getNameTranslationKey() {
		return nameTranslationKey;
	}


	public void setNameTranslationKey(String nameTranslationKey) {
		this.nameTranslationKey = nameTranslationKey;
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
	
	
	public Unit getUnit() {
		return unit;
	}


	public void setUnit(Unit unit) {
		this.unit = unit;
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


	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public OfferTemplate getOfferTemplate() {
		return offerTemplate;
	}


	public void setOfferTemplate(OfferTemplate offerTemplate) {
		this.offerTemplate = offerTemplate;
	}


	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
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


	public BusinessPartner getBusinessPartner() {
		return businessPartner;
	}


	public void setBusinessPartner(BusinessPartner businessPartner) {
		this.businessPartner = businessPartner;
	}


	public OfferValueType getOfferValueType() {
		return offerValueType;
	}


	public void setOfferValueType(OfferValueType offerValueType) {
		this.offerValueType = offerValueType;
	}


	public PointsType getPointsType() {
		return pointsType;
	}


	public void setPointsType(PointsType pointsType) {
		this.pointsType = pointsType;
	}


	public Boolean getSuppress() {
		return suppress;
	}


	public void setSuppress(Boolean suppress) {
		this.suppress = suppress;
	}
	
}

