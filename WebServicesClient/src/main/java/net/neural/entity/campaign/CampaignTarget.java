package net.zfp.entity.campaign;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.Status;
import net.zfp.entity.Unit;
import net.zfp.entity.rule.RuleCondition;

/**
 * Entity implementation class for Entity: CampaignTarget
 *
 */
@Entity(name="CampaignTarget")
@XmlRootElement(name="CampaignTarget")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignTarget extends DomainEntity {

	private static final long serialVersionUID = 813164429415846950L;
		
	@XmlElement(name="Campaign")
	@OneToOne
	@JoinColumn(name="campaignId")
	private Campaign campaign;
	
	@XmlAttribute(name="target")
	private Integer target;
	
	@XmlAttribute(name="tolerance")
	private Integer tolerance;
	
	@XmlElement(name="Unit")
	@OneToOne
	@JoinColumn(name="unitId")
	private Unit unit;
	
	@XmlElement(name="CampaignTargetType")
	@OneToOne
	@JoinColumn(name="typeId")
	private CampaignTargetType campaignTargetType;
	
	@XmlElement(name="CampaignTargetMode")
	@OneToOne
	@JoinColumn(name="modeId")
	private CampaignTargetMode CampaignTargetMode;
	
	@XmlElement(name="RuleCondition")
	@OneToOne
	@JoinColumn(name="ruleConditionId")
	private RuleCondition ruleCondition;
	
	@XmlAttribute(name="baseline")
	private Integer baseline;
	
	public CampaignTarget() {}
	
	
	public Campaign getCampaign() {
		return campaign;
	}


	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}


	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public Integer getTolerance() {
		return tolerance;
	}

	public void setTolerance(Integer tolerance) {
		this.tolerance = tolerance;
	}


	public Unit getUnit() {
		return unit;
	}


	public void setUnit(Unit unit) {
		this.unit = unit;
	}


	public CampaignTargetType getCampaignTargetType() {
		return campaignTargetType;
	}


	public void setCampaignTargetType(CampaignTargetType campaignTargetType) {
		this.campaignTargetType = campaignTargetType;
	}


	public RuleCondition getRuleCondition() {
		return ruleCondition;
	}


	public void setRuleCondition(RuleCondition ruleCondition) {
		this.ruleCondition = ruleCondition;
	}


	public CampaignTargetMode getCampaignTargetMode() {
		return CampaignTargetMode;
	}


	public void setCampaignTargetMode(CampaignTargetMode campaignTargetMode) {
		CampaignTargetMode = campaignTargetMode;
	}


	public Integer getBaseline() {
		return baseline;
	}


	public void setBaseline(Integer baseline) {
		this.baseline = baseline;
	}
	
	
}
