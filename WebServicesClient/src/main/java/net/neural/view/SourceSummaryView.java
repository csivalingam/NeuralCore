package net.zfp.view;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.zfp.entity.UserRole;


@XmlRootElement(name="SourceSummary")
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceSummaryView {
	
	@XmlElement
	private Long sourceId;
	@XmlElement
	private String sourceType;
	@XmlElement
	private Integer month;
	@XmlElement
	private Integer year;
	@XmlElement
	private Integer type;
	
	@XmlElement
	private Date date;
	
	@XmlElement
	private Double consumption;
	@XmlElement
	private Double prevDiff;
	@XmlElement
	private Integer trend;
	@XmlElement
	private Integer rank;
	@XmlElement
	private Double spend;
	
	@XmlElement
	private Integer calories;
	@XmlElement
	private Integer steps;
	@XmlElement
	private Double distance;
	@XmlElement
	private Double efficiency;
	@XmlElement
	private Double savings;
	@XmlElement
	private String duration;
	@XmlElement
	private Double durationInSeconds;
	
	public SourceSummaryView() {
	}
	
	public Double getDurationInSeconds() {
		return durationInSeconds;
	}


	public void setDurationInSeconds(Double durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}
	
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getConsumption() {
		return consumption;
	}

	public void setConsumption(Double consumption) {
		this.consumption = consumption;
	}

	public Double getPrevDiff() {
		return prevDiff;
	}

	public void setPrevDiff(Double prevDiff) {
		this.prevDiff = prevDiff;
	}

	public Double getSpend() {
		return spend;
	}

	public void setSpend(Double spend) {
		this.spend = spend;
	}

	public Integer getTrend() {
		return trend;
	}

	public void setTrend(Integer trend) {
		this.trend = trend;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Double getSavings() {
		return savings;
	}

	public void setSavings(Double savings) {
		this.savings = savings;
	}

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getSteps() {
		return steps;
	}

	public void setSteps(Integer steps) {
		this.steps = steps;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(Double efficiency) {
		this.efficiency = efficiency;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
