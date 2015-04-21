package net.zfp.view;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlRootElement(name="Campaign")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class CampaignView {
	
	@XmlElement
	private Long campaignId;
	
	@XmlElement
	private String imageURL;
	
	@XmlElement
	private String sourceTypeImageUrl;
	
	@XmlElement
	private String campaignType;
	
	@XmlElement
	private String type;
	
	@XmlElement
	private String mode;
	
	@XmlElement
	private Boolean isFundraising;
	
	@XmlElement
	private String name;
	
	@XmlElement
	private String description;
	
	@XmlElement
	private String longDescription;
	
	@XmlElement
	private String longDescriptionRefImageUrl;
	
	@XmlElement
	private Integer remainingInNumber;
	
	@XmlElement
	private String remainingInUnit;
	
	@XmlElement
	private Double progress;
	
	@XmlElement
	private String startDate;
	
	@XmlElement
	private String endDate;
	
	@XmlElement
	private Date startTime;
	
	@XmlElement
	private Date endTime;
	
	@XmlElement
	private Integer progressbarValue;
	
	@XmlElement
	private Integer earnedPoints;
	
	@XmlElement
	private Integer offerPoints;
	
	@XmlElement
	private String statusText;
	
	@XmlElement
	private Long statusId;
	
	@XmlElement
	private Integer frequency;
	
	@XmlElement
	private Integer period;
	
	@XmlElement
	private Integer periodType;
	
	@XmlElement
	private Integer currentFrequencyStage;
	
	@XmlElement
	private String sponsorImageUrl;
	
	@XmlElement
	private ResultView result;
	
	public CampaignView() {}
	
	
	public String getCampaignType() {
		return campaignType;
	}


	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
	}


	public String getMode() {
		return mode;
	}


	public void setMode(String mode) {
		this.mode = mode;
	}


	public String getSponsorImageUrl() {
		return sponsorImageUrl;
	}


	public void setSponsorImageUrl(String sponsorImageUrl) {
		this.sponsorImageUrl = sponsorImageUrl;
	}


	public String getLongDescription() {
		return longDescription;
	}


	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}


	public Integer getCurrentFrequencyStage() {
		return currentFrequencyStage;
	}


	public void setCurrentFrequencyStage(Integer currentFrequencyStage) {
		this.currentFrequencyStage = currentFrequencyStage;
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


	public Integer getPeriodType() {
		return periodType;
	}


	public void setPeriodType(Integer periodType) {
		this.periodType = periodType;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	

	public Long getCampaignId() {
		return campaignId;
	}


	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
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

	public Double getProgress() {
		return progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public Integer getRemainingInNumber() {
		return remainingInNumber;
	}

	public void setRemainingInNumber(Integer remainingInNumber) {
		this.remainingInNumber = remainingInNumber;
	}

	public String getRemainingInUnit() {
		return remainingInUnit;
	}

	public void setRemainingInUnit(String remainingInUnit) {
		this.remainingInUnit = remainingInUnit;
	}

	public Integer getProgressbarValue() {
		return progressbarValue;
	}

	public void setProgressbarValue(Integer progressbarValue) {
		this.progressbarValue = progressbarValue;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Boolean getIsFundraising() {
		return isFundraising;
	}

	public void setIsFundraising(Boolean isFundraising) {
		this.isFundraising = isFundraising;
	}


	public Integer getOfferPoints() {
		return offerPoints;
	}


	public void setOfferPoints(Integer offerPoints) {
		this.offerPoints = offerPoints;
	}


	public String getSourceTypeImageUrl() {
		return sourceTypeImageUrl;
	}


	public void setSourceTypeImageUrl(String sourceTypeImageUrl) {
		this.sourceTypeImageUrl = sourceTypeImageUrl;
	}


	public Integer getEarnedPoints() {
		return earnedPoints;
	}


	public void setEarnedPoints(Integer earnedPoints) {
		this.earnedPoints = earnedPoints;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public Long getStatusId() {
		return statusId;
	}


	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public String getLongDescriptionRefImageUrl() {
		return longDescriptionRefImageUrl;
	}


	public void setLongDescriptionRefImageUrl(String longDescriptionRefImageUrl) {
		this.longDescriptionRefImageUrl = longDescriptionRefImageUrl;
	}


	public ResultView getResult() {
		return result;
	}


	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
