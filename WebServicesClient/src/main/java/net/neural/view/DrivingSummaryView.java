package net.zfp.view;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlRootElement(name="DrivingDailyView")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class DrivingSummaryView {
	
	@XmlElement
	private Date date;
	
	@XmlElement
	private String stringDate;
	
	@XmlElement
	private Double fuelConsumption;
	
	@XmlElement
	private Double distance;
	
	@XmlElement
	private Double duration;
	
	@XmlElement
	private Double efficiency;
	
	@XmlElement
	private String travelTime;
	
	@XmlElement(name="timeViews")
	private List<DrivingView> drivingViews;
	
	@XmlElement
	private ResultView result;
	
	public DrivingSummaryView() {
	}
	
	
	public Double getDuration() {
		return duration;
	}


	public void setDuration(Double duration) {
		this.duration = duration;
	}


	public Date getDate() {
		return date;
	}
	
	public String getStringDate() {
		return stringDate;
	}
	
	public Double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(Double efficiency) {
		this.efficiency = efficiency;
	}

	public Double getFuelConsumption() {
		return fuelConsumption;
	}

	public void setFuelConsumption(Double fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public void setStringDate(String stringDate) {
		this.stringDate = stringDate;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<DrivingView> getDrivingViews() {
		return drivingViews;
	}

	public void setDrivingViews(List<DrivingView> drivingViews) {
		this.drivingViews = drivingViews;
	}


	public ResultView getResult() {
		return result;
	}


	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
	
}
