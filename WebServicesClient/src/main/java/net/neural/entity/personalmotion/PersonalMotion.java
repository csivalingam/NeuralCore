package net.zfp.entity.personalmotion;

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

@Entity(name="PersonalMotion" )
@XmlRootElement(name="PersonalMotion")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalMotion extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1371072178872953101L;
	@XmlElement(name="Source")
	@ManyToOne
	@JoinColumn(name="sourceId")
	private Source source;
	@XmlAttribute(name="activityId")
	private String activityId;
	@XmlAttribute(name="activityType")
	private String activityType;
	@XmlAttribute(name="startTime")
	private Date startTime;
	@XmlAttribute(name="device")
	private String device;
	@XmlAttribute(name="fuelGoal")
	private Integer fuelGoal;
	@XmlAttribute(name="calories")
	private Integer calories;
	@XmlAttribute(name="fuel")
	private Integer fuel;
	@XmlAttribute(name="distance")
	private Double distance;
	@XmlAttribute(name="steps")
	private Integer steps;
	@XmlAttribute(name="duration")
	private String duration;
	
	public PersonalMotion() { }

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public Integer getFuelGoal() {
		return fuelGoal;
	}

	public void setFuelGoal(Integer fuelGoal) {
		this.fuelGoal = fuelGoal;
	}

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Integer getFuel() {
		return fuel;
	}

	public void setFuel(Integer fuel) {
		this.fuel = fuel;
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

	
}