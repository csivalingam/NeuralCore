package net.zfp.entity.driving;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.Source;

@Entity(name="DrivingDay" )
@XmlRootElement(name="DrivingDay")
@XmlAccessorType(XmlAccessType.FIELD)
public class DrivingDay extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1371072178872953101L;
	@XmlElement(name="Source")
	@ManyToOne
	@JoinColumn(name="sourceId")
	private Source source;
	@XmlAttribute(name="startTime")
	private Date startTime;
	@XmlAttribute(name="endTime")
	private Date endTime;
	
	@XmlAttribute(name="tripCount")
	private Integer tripCount;
	@XmlAttribute(name="maxSpeed")
	private Double maxSpeed;
	@XmlAttribute(name="maxAcceleration")
	private Double maxAcceleration;
	@XmlAttribute(name="maxDesceleration")
	private Double maxDesceleration;
	@XmlAttribute(name="maxRPM")
	private Double maxRPM;
	@XmlAttribute(name="fuel")
	private Double fuel;
	@XmlAttribute(name="fuelEfficiency")
	private Double fuelEfficiency;
	@XmlAttribute(name="distance")
	private Double distance;
	@XmlAttribute(name="duration")
	private Double duration;
	@XmlAttribute(name="startMilage")
	private Double startMilage;
	@XmlAttribute(name="endMilage")
	private Double endMilage;
	
	
	public DrivingDay() { }


	public Source getSource() {
		return source;
	}


	public void setSource(Source source) {
		this.source = source;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public Integer getTripCount() {
		return tripCount;
	}


	public void setTripCount(Integer tripCount) {
		this.tripCount = tripCount;
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


	public Double getFuel() {
		return fuel;
	}


	public void setFuel(Double fuel) {
		this.fuel = fuel;
	}


	public Double getFuelEfficiency() {
		return fuelEfficiency;
	}


	public void setFuelEfficiency(Double fuelEfficiency) {
		this.fuelEfficiency = fuelEfficiency;
	}


	public Double getDistance() {
		return distance;
	}


	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getDuration() {
		return duration;
	}


	public void setDuration(Double duration) {
		this.duration = duration;
	}


	public Double getStartMilage() {
		return startMilage;
	}


	public void setStartMilage(Double startMilage) {
		this.startMilage = startMilage;
	}


	public Double getEndMilage() {
		return endMilage;
	}


	public void setEndMilage(Double endMilage) {
		this.endMilage = endMilage;
	}
	
	
}