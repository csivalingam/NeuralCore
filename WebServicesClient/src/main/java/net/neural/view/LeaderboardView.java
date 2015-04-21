package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name="leaderboard")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class LeaderboardView {

	@XmlElement
	private String sourceName;
	
	@XmlElement
	private String sourceFirstName;
	
	@XmlElement
	private String sourceLastName;
	
	@XmlElement
	private Long sourceId;
	
	@XmlElement
	private Long accountId;
	
	@XmlElement
	private Double value;
	
	@XmlElement
	private Integer target;
	
	@XmlElement
	private Double sortingValue;
	
	@XmlElement
	private Integer rank;
	
	@XmlElement
	private Boolean selected = false;
	
	@XmlElement
	private Integer ranking;
	
	@XmlElement
	private ResultView result;
	
	public LeaderboardView() {
	}
	
	
	public String getSourceName() {
		return sourceName;
	}


	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}


	public String getSourceFirstName() {
		return sourceFirstName;
	}


	public void setSourceFirstName(String sourceFirstName) {
		this.sourceFirstName = sourceFirstName;
	}


	public String getSourceLastName() {
		return sourceLastName;
	}


	public void setSourceLastName(String sourceLastName) {
		this.sourceLastName = sourceLastName;
	}


	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}


	public Double getSortingValue() {
		return sortingValue;
	}


	public void setSortingValue(Double sortingValue) {
		this.sortingValue = sortingValue;
	}


	public ResultView getResult() {
		return result;
	}


	public void setResult(ResultView result) {
		this.result = result;
	}


	public Boolean getSelected() {
		return selected;
	}


	public void setSelected(Boolean selected) {
		this.selected = selected;
	}


	public Integer getTarget() {
		return target;
	}


	public void setTarget(Integer target) {
		this.target = target;
	}
	
	
}
