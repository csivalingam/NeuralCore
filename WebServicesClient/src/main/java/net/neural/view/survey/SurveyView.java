package net.zfp.view.survey;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.view.ResultView;


/**
 * View implementation class for Entity: SurveyView
 * 
 * @author Youngwook Yoo
 * @since 2014-09-03
 *
 */
@XmlRootElement(name="SurveyView")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class SurveyView {
	
	@XmlElement
	private String surveyName;
	
	@XmlElement
	private Long surveyId;
	
	@XmlElement
	private String surveyDescription;
	
	@XmlElement
	private String surveyLongDescription;
	
	@XmlElement
	private String imageURL;
	
	@XmlElement
	private String sponsorImageURL;
	
	@XmlElement
	private String type;
	
	@XmlElement
	private String status;
	
	@XmlElement
	private Integer offerCoins;
	
	@XmlElement
	private String startDate;
	
	@XmlElement
	private String endDate;
	
	@XmlElement
	private String outline;
	
	@XmlElement(name="categoryViews")
	private List<CategoryView> categoryViews;

	@XmlElement
	private ResultView result;

	public SurveyView() {
		categoryViews = new ArrayList<CategoryView>();
	}


	public Integer getOfferCoins() {
		return offerCoins;
	}


	public void setOfferCoins(Integer offerCoins) {
		this.offerCoins = offerCoins;
	}


	public String getImageURL() {
		return imageURL;
	}


	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}


	public String getSponsorImageURL() {
		return sponsorImageURL;
	}


	public void setSponsorImageURL(String sponsorImageURL) {
		this.sponsorImageURL = sponsorImageURL;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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


	public String getSurveyLongDescription() {
		return surveyLongDescription;
	}


	public void setSurveyLongDescription(String surveyLongDescription) {
		this.surveyLongDescription = surveyLongDescription;
	}


	public String getOutline() {
		return outline;
	}


	public void setOutline(String outline) {
		this.outline = outline;
	}


	public String getSurveyName() {
		return surveyName;
	}


	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}


	public Long getSurveyId() {
		return surveyId;
	}


	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}


	public String getSurveyDescription() {
		return surveyDescription;
	}


	public void setSurveyDescription(String surveyDescription) {
		this.surveyDescription = surveyDescription;
	}


	public List<CategoryView> getCategoryViews() {
		return categoryViews;
	}


	public void setCategoryViews(List<CategoryView> categoryViews) {
		this.categoryViews = categoryViews;
	}


	public ResultView getResult() {
		return result;
	}


	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
