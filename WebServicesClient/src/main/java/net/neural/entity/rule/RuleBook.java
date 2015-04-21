package net.zfp.entity.rule;

import java.util.ArrayList;
import java.util.List;

import net.zfp.entity.campaign.CampaignRule;

public class RuleBook {
	
	private List<CampaignRule> rules = new ArrayList<CampaignRule>();

	public List<CampaignRule> getRules() {
		return rules;
	}

	public void setRules(List<CampaignRule> rules) {
		this.rules = rules;
	}
}
