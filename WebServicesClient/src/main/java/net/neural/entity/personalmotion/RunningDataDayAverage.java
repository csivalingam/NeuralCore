package net.zfp.entity.personalmotion;

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

@Entity(name="RunningDataDayAverage" )
@XmlRootElement(name="RunningDataDayAverage")
@XmlAccessorType(XmlAccessType.FIELD)
public class RunningDataDayAverage extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1371072178872953101L;
	@XmlElement(name="Source")
	@ManyToOne
	@JoinColumn(name="sourceId")
	private Source source;
	@XmlAttribute(name="duration")
	private Double duration;
	@XmlAttribute(name="distance")
	private Double distance;
	@XmlAttribute(name="calories")
	private Integer calories;
	@XmlAttribute(name="steps")
	private Integer steps;
	
	public RunningDataDayAverage() {}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
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