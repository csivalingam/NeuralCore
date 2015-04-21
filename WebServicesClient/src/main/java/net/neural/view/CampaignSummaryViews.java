package net.zfp.view;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlRootElement(name="CampaignSummaries")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class CampaignSummaryViews {
	
	@XmlElement
	private List<CampaignSummaryView> campaignSummary;
	@XmlElement
	private ResultView result;
	
	public CampaignSummaryViews() {
		
	}
	

	public List<CampaignSummaryView> getCampaignSummary() {
		return campaignSummary;
	}


	public void setCampaignSummary(List<CampaignSummaryView> campaignSummary) {
		this.campaignSummary = campaignSummary;
	}


	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}

	
}
