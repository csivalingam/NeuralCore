package net.zfp.view.personalmotion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.personalmotion.PersonalMotion;


@XmlRootElement(name="PersonalMotionDetailView")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalMotionDetailView {
	
	@XmlAttribute(name="unitType")
	private Integer unitType;
	
	@XmlAttribute(name="unit")
	private String unit;
	
	@XmlAttribute(name="trendImageUrl")
	private String trendImageUrl;
	
	@XmlAttribute(name="trend")
	private Integer trend;
	
	@XmlAttribute(name="thisWeekValue")
	private Integer thisWeekValue;
	
	@XmlAttribute(name="dailyAverage")
	private Integer dailyAverage;
	
	@XmlAttribute(name="baselineMedium")
	private Integer baselineMedium;
	
	@XmlAttribute(name="baselineHigh")
	private Integer baselineHigh;
	
	@XmlElement(name="MonthlySummaryView")
	private MonthlySummaryView monthlySummaryView;
	
	
	public PersonalMotionDetailView() {
		this.unit = "steps";
		this.thisWeekValue = 0;
		this.dailyAverage = 0;
		this.trendImageUrl = "/portal-core/images/rewards/icons/performance-uparrow.png";
		this.trend = 0;
		monthlySummaryView = new MonthlySummaryView();
	}

	
	public Integer getUnitType() {
		return unitType;
	}


	public void setUnitType(Integer unitType) {
		this.unitType = unitType;
	}


	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getTrendImageUrl() {
		return trendImageUrl;
	}

	public void setTrendImageUrl(String trendImageUrl) {
		this.trendImageUrl = trendImageUrl;
	}
	
	public Integer getThisWeekValue() {
		return thisWeekValue;
	}

	public void setThisWeekValue(Integer thisWeekValue) {
		this.thisWeekValue = thisWeekValue;
	}

	public Integer getDailyAverage() {
		return dailyAverage;
	}

	public void setDailyAverage(Integer dailyAverage) {
		this.dailyAverage = dailyAverage;
	}

	public Integer getTrend() {
		return trend;
	}

	public void setTrend(Integer trend) {
		this.trend = trend;
	}


	public MonthlySummaryView getMonthlySummaryView() {
		return monthlySummaryView;
	}


	public void setMonthlySummaryView(MonthlySummaryView monthlySummaryView) {
		this.monthlySummaryView = monthlySummaryView;
	}


	public Integer getBaselineMedium() {
		return baselineMedium;
	}


	public void setBaselineMedium(Integer baselineMedium) {
		this.baselineMedium = baselineMedium;
	}


	public Integer getBaselineHigh() {
		return baselineHigh;
	}


	public void setBaselineHigh(Integer baselineHigh) {
		this.baselineHigh = baselineHigh;
	}
	
	
	
}
