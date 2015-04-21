package net.zfp.view;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlRootElement(name="Campaigns")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class CampaignViews {
	
	@XmlElement
	private List<CampaignView> campaign;
	
	@XmlElement
	private ResultView result;
	
	public CampaignViews() {}

	
	public List<CampaignView> getCampaign() {
		return campaign;
	}


	public void setCampaign(List<CampaignView> campaign) {
		this.campaign = campaign;
	}


	public ResultView getResult() {
		return result;
	}


	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
