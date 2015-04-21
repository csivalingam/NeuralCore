package net.zfp.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DrivingView")
@XmlAccessorType(XmlAccessType.FIELD)
public class DrivingView {

	@XmlElement
	private Long id;
		
	@XmlElement
	private Double hourValue;
	
	@XmlElement
	private Double distance;
	
	@XmlElement
	private Double maxSpeed;
	
	@XmlElement
	private Double maxAcceleration;
	
	@XmlElement
	private Double maxDesceleration;
	
	@XmlElement
	private Double maxRPM;
	
	@XmlElement
	private Integer tripCount;
	
	@XmlElement
	private Double fuelEfficiency;
	
	@XmlElement
	private String startTime;
	
	@XmlElement
	private String endTime;
	
	@XmlElement
	private Double fuel;
		
	@XmlElement
	private String duration;
	
	@XmlElement
	private Double durationInNumber;
	
	@XmlElement
	private Long sourceId;
	
	public DrivingView() {
	}
	
	
	private String getTimeFormat(Date time){
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		
		return formatter.format(time);
	}
	
	private String getDurationFormat(Double duration){
		int travelTimeInSeconds = (int)Math.round(duration);
		int hourValue = travelTimeInSeconds / 3600;
		int minuteValue = (int)Math.ceil((travelTimeInSeconds % 3600) / 60.0);
		if (minuteValue < 10){
			return hourValue + ":0" + minuteValue;
		}else{
			return hourValue + ":" + minuteValue;
		}
	}
	
	private double getHourValue(Date startTime, Date endTime){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date(startTime.getTime() + (endTime.getTime()-startTime.getTime())/2));
		
		Double decimalValue = (double)calendar.get(Calendar.MINUTE) / 60;
		double roundOff = Math.round(decimalValue * 100.0) / 100.0;
		return (double)calendar.get(Calendar.HOUR_OF_DAY) + roundOff;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getHourValue() {
		return hourValue;
	}

	public void setHourValue(Double hourValue) {
		this.hourValue = hourValue;
	}

	public Double getFuel() {
		return fuel;
	}

	public void setFuel(Double fuel) {
		this.fuel = fuel;
	}
	
	
	public Double getMaxSpeed() {
		return maxSpeed;
	}


	public void setMaxSpeed(Double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}


	public Double getMaxAcceleration() {
		return maxAcceleration;
	}


	public void setMaxAcceleration(Double maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
	}


	public Double getMaxDesceleration() {
		return maxDesceleration;
	}


	public void setMaxDesceleration(Double maxDesceleration) {
		this.maxDesceleration = maxDesceleration;
	}


	public Double getMaxRPM() {
		return maxRPM;
	}


	public void setMaxRPM(Double maxRPM) {
		this.maxRPM = maxRPM;
	}


	public Integer getTripCount() {
		return tripCount;
	}


	public void setTripCount(Integer tripCount) {
		this.tripCount = tripCount;
	}


	public Double getFuelEfficiency() {
		return fuelEfficiency;
	}


	public void setFuelEfficiency(Double fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
	}


	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Double getDurationInNumber() {
		return durationInNumber;
	}
	public void setDurationInNumber(Double durationInNumber) {
		this.durationInNumber = durationInNumber;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	
}
