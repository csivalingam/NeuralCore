package net.zfp.entity.personalmotion;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.activity.ActivityTrackerType;
import net.zfp.entity.activity.BaselineType;
import net.zfp.entity.activity.PeriodType;
@Entity(name="WalkingBoundary" )
@XmlRootElement(name="WalkingBoundary")
@XmlAccessorType(XmlAccessType.FIELD)
public class WalkingBoundary extends DomainEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1371072178872953101L;
	
	@XmlElement(name="BoundaryType")
	@ManyToOne
	@JoinColumn(name="baselineTypeId")
	private BaselineType baselineType;
	
	@XmlElement(name="ActivityTrackerType")
	@ManyToOne
	@JoinColumn(name="activityTrackerTypeId")
	private ActivityTrackerType activityTrackerType;
	
	@XmlElement(name="PeriodType")
	@ManyToOne
	@JoinColumn(name="periodTypeId")
	private PeriodType periodType;
	
	@XmlElement
	private Integer boundary;
	
	public WalkingBoundary() { }

	public BaselineType getBaselineType() {
		return baselineType;
	}

	public void setBaselineType(BaselineType baselineType) {
		this.baselineType = baselineType;
	}

	public ActivityTrackerType getActivityTrackerType() {
		return activityTrackerType;
	}

	public void setActivityTrackerType(ActivityTrackerType activityTrackerType) {
		this.activityTrackerType = activityTrackerType;
	}

	public PeriodType getPeriodType() {
		return periodType;
	}

	public void setPeriodType(PeriodType periodType) {
		this.periodType = periodType;
	}

	public Integer getBoundary() {
		return boundary;
	}

	public void setBoundary(Integer boundary) {
		this.boundary = boundary;
	}
}