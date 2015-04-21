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
@Entity(name="CampaignActionList")
@XmlRootElement(name="CampaignActionList")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignActionList extends BaseEntity {

	private static final long serialVersionUID = 813164429415846950L;
				
	@XmlElement(name="CampaignAction")
	@ManyToOne
	@JoinColumn(name="actionId")
	private CampaignAction campaignAction;
	
	@XmlElement(name="Campaign")
	@ManyToOne
	@JoinColumn(name="campaignId")
	private Campaign campaign;
	
	public CampaignActionList() {}

	


	public Campaign getCampaign() {
		return campaign;
	}




	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}




	public CampaignAction getCampaignAction() {
		return campaignAction;
	}




	public void setCampaignAction(CampaignAction campaignAction) {
		this.campaignAction = campaignAction;
	}
	
	
   
}
