package net.zfp.entity.campaign;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.rule.RuleAttribute;
import net.zfp.entity.rule.RuleCondition;

@Entity(name="CampaignRule")
@XmlRootElement(name="CampaignRule")
@XmlAccessorType(XmlAccessType.FIELD)
public class CampaignRule extends BaseEntity {


	private static final long serialVersionUID = 7913469140950533298L;
	
	@XmlElement(name="Campaign")
	@OneToOne
	@JoinColumn(name="campaignId")
	private Campaign campaign;
	
	@XmlElement(name="RuleAttribute")
	@OneToOne
	@JoinColumn(name="attributeId")
	private RuleAttribute ruleAttribute;
	
	@XmlElement(name="RuleCondition")
	@OneToOne
	@JoinColumn(name="conditionId")
	private RuleCondition ruleCondition;
	
	@XmlAttribute(name="value")
	private String value;
	
	@XmlAttribute(name="hierarchy")
	private Integer hierarchy;
	
	@XmlAttribute(name="operator")
	private String operator;
	
	private Integer integerValue;
	
	public CampaignRule() { }

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public RuleAttribute getRuleAttribute() {
		return ruleAttribute;
	}

	public void setRuleAttribute(RuleAttribute ruleAttribute) {
		this.ruleAttribute = ruleAttribute;
	}
	
	public RuleCondition getRuleCondition() {
		return ruleCondition;
	}

	public void setRuleCondition(RuleCondition ruleCondition) {
		this.ruleCondition = ruleCondition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(Integer hierarchy) {
		this.hierarchy = hierarchy;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getIntegerValue() {
		return integerValue;
	}

	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}
	
	
}