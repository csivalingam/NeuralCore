package net.zfp.entity.campaign;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.Status;
import net.zfp.entity.category.Category;
import net.zfp.entity.segment.Segment;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="Campaign")
@XmlRootElement(name="Campaign")
@XmlAccessorType(XmlAccessType.FIELD)
public class Campaign extends BaseEntity {

	private static final long serialVersionUID = 809164429415846950L;
	
	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="description")
	private String description;
	
	@XmlAttribute(name="longDescription")
	private String longDescription;
	
	@XmlAttribute(name="launchDate")
	private Date launchDate;
	
	@XmlAttribute(name="startDate")
	private Date startDate;
	
	@XmlAttribute(name="endDate")
	private Date endDate;
	
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="statusId")
	private Status status;
	
	@XmlElement(name="Segment")
	@ManyToOne
	@JoinColumn(name="segmentId")
	private Segment segment;
	
	@XmlElement(name="CampaignTemplate")
	@OneToOne
	@JoinColumn(name="templateId")
	private CampaignTemplate campaignTemplate;
	
	@XmlAttribute(name="communityId")
	private Long communityId;
		
	@XmlElement(name="Category")
	@OneToOne
	@JoinColumn(name="categoryId")
	private Category category;
	
	@XmlAttribute(name="smallImageUrl")
	private String smallImageUrl;
	
	@XmlAttribute(name="mobileSmallImageUrl")
	private String mobileSmallImageUrl;
	
	@XmlAttribute(name="mobileLargeImageUrl")
	private String mobileLargeImageUrl;
	
	@XmlAttribute(name="frequency")
	private Integer frequency;
	
	@XmlAttribute(name="period")
	private Integer period;
	
	@XmlAttribute(name="periodType")
	private String periodType;
	
	@XmlAttribute(name="currentFrequencyStage")
	private Integer currentFrequencyStage;
	
	@XmlAttribute(name="autoJoin")
	private Boolean autoJoin;
	
	@XmlAttribute(name="longDescriptionRefImageUrl")
	private String longDescriptionRefImageUrl;
	
	@XmlElement(name="CampaignType")
	@OneToOne
	@JoinColumn(name="typeId")
	private CampaignType campaignType;
	
	public Campaign() {}

	
	public Boolean getAutoJoin() {
		return autoJoin;
	}


	public void setAutoJoin(Boolean autoJoin) {
		this.autoJoin = autoJoin;
	}


	public String getLongDescription() {
		return longDescription;
	}


	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}


	public Integer getFrequency() {
		return frequency;
	}


	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}


	public Integer getPeriod() {
		return period;
	}


	public void setPeriod(Integer period) {
		this.period = period;
	}


	public String getPeriodType() {
		return periodType;
	}


	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}


	public Integer getCurrentFrequencyStage() {
		return currentFrequencyStage;
	}


	public void setCurrentFrequencyStage(Integer currentFrequencyStage) {
		this.currentFrequencyStage = currentFrequencyStage;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}


	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}
	
	public CampaignTemplate getCampaignTemplate() {
		return campaignTemplate;
	}

	public void setCampaignTemplate(CampaignTemplate campaignTemplate) {
		this.campaignTemplate = campaignTemplate;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}


	public Date getLaunchDate() {
		return launchDate;
	}


	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}


	public String getLongDescriptionRefImageUrl() {
		return longDescriptionRefImageUrl;
	}


	public void setLongDescriptionRefImageUrl(String longDescriptionRefImageUrl) {
		this.longDescriptionRefImageUrl = longDescriptionRefImageUrl;
	}


	public CampaignType getCampaignType() {
		return campaignType;
	}


	public void setCampaignType(CampaignType campaignType) {
		this.campaignType = campaignType;
	}

	
   
}
