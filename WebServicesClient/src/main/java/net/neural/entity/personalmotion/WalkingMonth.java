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

@Entity(name="WalkingMonth" )
@XmlRootElement(name="WalkingMonth")
@XmlAccessorType(XmlAccessType.FIELD)
public class WalkingMonth extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1371072178872953101L;
	@XmlElement(name="Source")
	@ManyToOne
	@JoinColumn(name="sourceId")
	private Source source;
	@XmlAttribute(name="deviceId")
	private String deviceId;
	@XmlAttribute(name="startTime")
	private Date startTime;
	@XmlAttribute(name="endTime")
	private Date endTime;
	@XmlAttribute(name="duration")
	private Double duration;
	@XmlAttribute(name="distance")
	private Double distance;
	@XmlAttribute(name="calories")
	private Integer calories;
	@XmlAttribute(name="steps")
	private Integer steps;
	
	public WalkingMonth() { }

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
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

	
}