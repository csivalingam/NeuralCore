package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlRootElement(name="ElectricitySummaryViews")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class ElectricitySummaryView {
	
	@XmlElement(name="currentTimeViews")
	private List<ElectricityView> currentElectricityViews;
	
	@XmlElement(name="previsouTimeViews")
	private List<ElectricityView> previsouElectricityViews;

	@XmlElement
	private GaugeView costGaugeView;
	
	@XmlElement
	private GaugeView consumptionGaugeView;
	
	@XmlElement
	private Double offPeak;
	
	@XmlElement
	private Double onPeak;
	
	@XmlElement
	private Double midPeak;
	
	@XmlElement
	private Double offPeakRate;
	
	@XmlElement
	private Double onPeakRate;
	
	@XmlElement
	private Double midPeakRate;
	
	@XmlElement
	private Double compareToPrevious;
	
	@XmlElement
	private Double rate;
	
	@XmlElement
	private String trendImageUrl;
	
	@XmlElement
	private Double totalCost;
	
	@XmlElement
	private Double totalConsumption;
	
	@XmlElement
	private Double potentialSaving;
	
	@XmlElement
	private ResultView result;
	
	public ElectricitySummaryView() {
	}
	
	
	public List<ElectricityView> getCurrentElectricityViews() {
		return currentElectricityViews;
	}


	public void setCurrentElectricityViews(List<ElectricityView> currentElectricityViews) {
		this.currentElectricityViews = currentElectricityViews;
	}

	public List<ElectricityView> getPrevisouElectricityViews() {
		return previsouElectricityViews;
	}


	public void setPrevisouElectricityViews(List<ElectricityView> previsouElectricityViews) {
		this.previsouElectricityViews = previsouElectricityViews;
	}


	public GaugeView getCostGaugeView() {
		return costGaugeView;
	}

	public void setCostGaugeView(GaugeView costGaugeView) {
		this.costGaugeView = costGaugeView;
	}

	public GaugeView getConsumptionGaugeView() {
		return consumptionGaugeView;
	}

	public void setConsumptionGaugeView(GaugeView consumptionGaugeView) {
		this.consumptionGaugeView = consumptionGaugeView;
	}

	public Double getOffPeak() {
		return offPeak;
	}

	public void setOffPeak(Double offPeak) {
		this.offPeak = offPeak;
	}

	public Double getOnPeak() {
		return onPeak;
	}

	public void setOnPeak(Double onPeak) {
		this.onPeak = onPeak;
	}

	public Double getMidPeak() {
		return midPeak;
	}

	public void setMidPeak(Double midPeak) {
		this.midPeak = midPeak;
	}

	public Double getOffPeakRate() {
		return offPeakRate;
	}

	public void setOffPeakRate(Double offPeakRate) {
		this.offPeakRate = offPeakRate;
	}

	public Double getOnPeakRate() {
		return onPeakRate;
	}

	public void setOnPeakRate(Double onPeakRate) {
		this.onPeakRate = onPeakRate;
	}

	public Double getMidPeakRate() {
		return midPeakRate;
	}

	public void setMidPeakRate(Double midPeakRate) {
		this.midPeakRate = midPeakRate;
	}

	public Double getCompareToPrevious() {
		return compareToPrevious;
	}

	public void setCompareToPrevious(Double compareToPrevious) {
		this.compareToPrevious = compareToPrevious;
	}

	public String getTrendImageUrl() {
		return trendImageUrl;
	}

	public void setTrendImageUrl(String trendImageUrl) {
		this.trendImageUrl = trendImageUrl;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Double getTotalConsumption() {
		return totalConsumption;
	}

	public void setTotalConsumption(Double totalConsumption) {
		this.totalConsumption = totalConsumption;
	}

	public Double getPotentialSaving() {
		return potentialSaving;
	}

	public void setPotentialSaving(Double potentialSaving) {
		this.potentialSaving = potentialSaving;
	}


	public Double getRate() {
		return rate;
	}


	public void setRate(Double rate) {
		this.rate = rate;
	}


	public ResultView getResult() {
		return result;
	}


	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
