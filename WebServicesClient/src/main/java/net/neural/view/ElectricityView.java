package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ElectricityView")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElectricityView {

	@XmlElement
	private String aggregationStartDate;
	
	@XmlElement
	private Double consumption;
	
	@XmlElement
	private Double cost;
	
	@XmlElement
	private Double onPeakConsumption;
	
	@XmlElement
	private Double midPeakConsumption;
	
	@XmlElement
	private Double offPeakConsumption;
	
	@XmlElement
	private Double baseline;
	
	@XmlElement
	private Double weatherNorm;
	
	public ElectricityView() {
		this.consumption = 0.0;
		this.cost = 0.0;
		this.onPeakConsumption = 0.0;
		this.midPeakConsumption = 0.0;
		this.offPeakConsumption = 0.0;
		
	}


	public String getAggregationStartDate() {
		return aggregationStartDate;
	}


	public void setAggregationStartDate(String aggregationStartDate) {
		this.aggregationStartDate = aggregationStartDate;
	}


	public Double getConsumption() {
		return consumption;
	}


	public void setConsumption(Double consumption) {
		this.consumption = consumption;
	}


	public Double getCost() {
		return cost;
	}


	public void setCost(Double cost) {
		this.cost = cost;
	}


	public Double getOnPeakConsumption() {
		return onPeakConsumption;
	}


	public void setOnPeakConsumption(Double onPeakConsumption) {
		this.onPeakConsumption = onPeakConsumption;
	}


	public Double getMidPeakConsumption() {
		return midPeakConsumption;
	}


	public void setMidPeakConsumption(Double midPeakConsumption) {
		this.midPeakConsumption = midPeakConsumption;
	}


	public Double getOffPeakConsumption() {
		return offPeakConsumption;
	}


	public void setOffPeakConsumption(Double offPeakConsumption) {
		this.offPeakConsumption = offPeakConsumption;
	}


	public Double getBaseline() {
		return baseline;
	}


	public void setBaseline(Double baseline) {
		this.baseline = baseline;
	}


	public Double getWeatherNorm() {
		return weatherNorm;
	}


	public void setWeatherNorm(Double weatherNorm) {
		this.weatherNorm = weatherNorm;
	}
	
	
}
