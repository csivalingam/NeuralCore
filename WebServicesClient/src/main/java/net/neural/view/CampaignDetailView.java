package net.zfp.view;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.campaign.CampaignAction;
import net.zfp.entity.campaign.CampaignFact;


@XmlRootElement(name="CampaignDetailView")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class CampaignDetailView {
	
	@XmlElement
	private Long campaignId;
	
	@XmlElement
	private Long groupId;
	
	@XmlElement
	private String imageURL;
	
	@XmlElement
	private String title;
	
	@XmlElement
	private String ownerName;
	
	@XmlElement
	private String type;
	
	@XmlElement
	private String status;
	
	@XmlElement
	private String description;
	
	@XmlElement
	private String longDescription;
	
	@XmlElement
	private Integer target;
	
	@XmlElement
	private Integer progressbarValue;
	
	@XmlElement
	private String targetUnit;
	
	@XmlElement
	private Double timeProgress;
	
	@XmlElement
	private Integer timeRemaining;
	
	@XmlElement
	private String timeRemainingUnit;
	
	@XmlElement
	private Integer progress;
	
	@XmlElement
	private String rewards;
	
	@XmlElement
	private String sourceTypeImage;
	
	@XmlElement
	private String sourceTypeName;
	
	@XmlElement
	private String sourceTypeUnit;
	
	@XmlElement
	private String sourceType;
	
	@XmlElement
	private String offerValue;
	
	@XmlElement
	private String offerImageUrl;
	
	@XmlElement
	private String message;
	
	@XmlElement
	private String startDate;
	
	@XmlElement
	private String endDate;
	
	@XmlElement
	private List<OfferView> offerviews;
	
	@XmlElement
	private List<CampaignAction> actions;
	
	@XmlElement
	private List<CampaignFact> facts;
	
	@XmlElement
	private String templateType;
	
	@XmlElement
	private Boolean isFundraising;
	
	@XmlElement
	private Boolean isCompetition;
	
	@XmlElement
	private Boolean isContinuous;
	
	@XmlElement
	private Integer currentIteration;
	
	@XmlElement
	private Integer frequency;
	
	@XmlElement
	private ResultView result;
	
	public CampaignDetailView() {}

	public Boolean getIsContinuous() {
		return isContinuous;
	}

	public void setIsContinuous(Boolean isContinuous) {
		this.isContinuous = isContinuous;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getImageURL() {
		return imageURL;
	}
	
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getSourceTypeImage() {
		return sourceTypeImage;
	}



	public void setSourceTypeImage(String sourceTypeImage) {
		this.sourceTypeImage = sourceTypeImage;
	}



	public String getSourceTypeName() {
		return sourceTypeName;
	}



	public void setSourceTypeName(String sourceTypeName) {
		this.sourceTypeName = sourceTypeName;
	}



	public String getSourceTypeUnit() {
		return sourceTypeUnit;
	}



	public void setSourceTypeUnit(String sourceTypeUnit) {
		this.sourceTypeUnit = sourceTypeUnit;
	}



	public String getLongDescription() {
		return longDescription;
	}



	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}



	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public String getTargetUnit() {
		return targetUnit;
	}

	public void setTargetUnit(String targetUnit) {
		this.targetUnit = targetUnit;
	}

	public Double getTimeProgress() {
		return timeProgress;
	}

	public void setTimeProgress(Double timeProgress) {
		this.timeProgress = timeProgress;
	}

	public Integer getTimeRemaining() {
		return timeRemaining;
	}

	public void setTimeRemaining(Integer timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public String getTimeRemainingUnit() {
		return timeRemainingUnit;
	}

	public void setTimeRemainingUnit(String timeRemainingUnit) {
		this.timeRemainingUnit = timeRemainingUnit;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public String getRewards() {
		return rewards;
	}

	public void setRewards(String rewards) {
		this.rewards = rewards;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<CampaignAction> getActions() {
		return actions;
	}

	public void setActions(List<CampaignAction> actions) {
		this.actions = actions;
	}

	public List<CampaignFact> getFacts() {
		return facts;
	}

	public void setFacts(List<CampaignFact> facts) {
		this.facts = facts;
	}

	public String getOfferValue() {
		return offerValue;
	}

	public void setOfferValue(String offerValue) {
		this.offerValue = offerValue;
	}

	public String getOfferImageUrl() {
		return offerImageUrl;
	}

	public void setOfferImageUrl(String offerImageUrl) {
		this.offerImageUrl = offerImageUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getProgressbarValue() {
		return progressbarValue;
	}

	public void setProgressbarValue(Integer progressbarValue) {
		this.progressbarValue = progressbarValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public Boolean getIsFundraising() {
		return isFundraising;
	}



	public void setIsFundraising(Boolean isFundraising) {
		this.isFundraising = isFundraising;
	}



	public Boolean getIsCompetition() {
		return isCompetition;
	}



	public void setIsCompetition(Boolean isCompetition) {
		this.isCompetition = isCompetition;
	}



	public List<OfferView> getOfferviews() {
		return offerviews;
	}



	public void setOfferviews(List<OfferView> offerviews) {
		this.offerviews = offerviews;
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



	public String getSourceType() {
		return sourceType;
	}



	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getCurrentIteration() {
		return currentIteration;
	}

	public void setCurrentIteration(Integer currentIteration) {
		this.currentIteration = currentIteration;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	
}
