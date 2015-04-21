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

@Entity(name="PersonalMotionInterval" )
@XmlRootElement(name="PersonalMotionInterval")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalMotionInterval extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1371072178872953101L;
	@XmlElement(name="Source")
	@ManyToOne
	@JoinColumn(name="sourceId")
	private Source source;
	
	@XmlAttribute(name="calories")
	private Integer calories;
	@XmlAttribute(name="fuel")
	private Integer fuel;
	@XmlAttribute(name="steps")
	private Integer steps;
	@XmlAttribute(name="startTime")
	private Date startTime;
	@XmlAttribute(name="endTime")
	private Date endTime;
	
	public PersonalMotionInterval() { }

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
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

	public Integer getSteps() {
		return steps;
	}

	public void setSteps(Integer steps) {
		this.steps = steps;
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
	
	
}