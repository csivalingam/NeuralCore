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

import net.zfp.entity.personalmotion.PersonalMotionInterval;
import net.zfp.entity.personalmotion.WalkingHour;

@XmlRootElement(name="HourlyView")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalMotionView {

	@XmlElement
	private Long id;
		
	@XmlElement
	private Double hourValue;
	
	@XmlElement
	private Double distance;
	
	@XmlElement
	private String startTime;
	
	@XmlElement
	private String endTime;
	
	@XmlElement
	private Integer fuel;
	
	@XmlElement
	private Integer calories;
	
	@XmlElement
	private Integer steps;
		
	@XmlElement
	private String duration;
	
	@XmlElement
	private Double durationInNumber;
	
	@XmlElement
	private Long sourceId;
	
	
	
	public PersonalMotionView() {
	}
	public PersonalMotionView(WalkingHour w){
		
		this.id = w.getId();
		this.sourceId =w.getSource().getId();
		this.calories = w.getCalories();
		this.steps = w.getSteps();
		this.hourValue = getHourValue(w.getStartTime(), w.getEndTime());
		this.duration = getDurationFormat(w.getDuration());
		this.durationInNumber = w.getDuration();
		
		this.startTime = getTimeFormat(w.getStartTime());
		this.endTime = getTimeFormat(w.getEndTime());
		
		this.distance = Math.round((w.getDistance()/1000) * 100.0) /100.0; 
	}
	
	public PersonalMotionView(PersonalMotionInterval pm){
		
		this.id = pm.getId();
		this.sourceId =pm.getSource().getId();
		this.fuel = pm.getFuel();
		this.calories = pm.getCalories();
		this.steps = pm.getSteps();
		this.hourValue = getHourValue(pm.getStartTime(), pm.getEndTime());
		//this.duration = getDurationFormat(pm.getEndTime().getTime() - pm.getStartTime().getTime());
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

	public Integer getFuel() {
		return fuel;
	}

	public void setFuel(Integer fuel) {
		this.fuel = fuel;
	}

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Integer getSteps() {
		return steps;
	}

	public void setSteps(Integer steps) {
		this.steps = steps;
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
