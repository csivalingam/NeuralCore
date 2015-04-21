package net.zfp.entity.campaign;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="CampaignFactList")
@XmlRootElement(name="CampaignFactList")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignFactList extends BaseEntity {

	private static final long serialVersionUID = 813164429415846950L;
			
	@XmlElement(name="CampaignFact")
	@ManyToOne
	@JoinColumn(name="factId")
	private CampaignFact campaignFact;
	
	@XmlElement(name="Campaign")
	@ManyToOne
	@JoinColumn(name="campaignId")
	private Campaign campaign;
	
	public CampaignFactList() {}

	

	public Campaign getCampaign() {
		return campaign;
	}



	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}



	public CampaignFact getCampaignFact() {
		return campaignFact;
	}



	public void setCampaignFact(CampaignFact campaignFact) {
		this.campaignFact = campaignFact;
	}
	
	
}
