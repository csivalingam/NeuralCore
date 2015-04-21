package net.zfp.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.personalmotion.PersonalMotion;
import net.zfp.entity.personalmotion.WalkingBoundary;


@XmlRootElement(name="PersonalMotionView")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class PersonalMotionDailyView {
	
	@XmlElement
	private Date date;
	
	@XmlElement
	private String stringDate;
	
	@XmlElement
	private Integer fuelGoal;
	
	@XmlElement
	private Double distance;
	
	@XmlElement
	private String activityId;
	
	@XmlElement
	private String duration;
	
	@XmlElement
	private Integer fuel;
	
	@XmlElement
	private Integer calories;
	
	@XmlElement
	private Integer steps;
	
	@XmlElement
	private Long sourceId;
	
	@XmlElement(name="boundaryViews")
	private List<WalkingBoundary> walkingBaselineView;
	
	@XmlElement(name="baselineView")
	private BaselineView baselineView;
	
	@XmlElement(name="timeViews")
	private List<PersonalMotionView> personalMotionViews;
	
	@XmlElement
	private ResultView result;
	
	public PersonalMotionDailyView() {
	}

	public PersonalMotionDailyView(PersonalMotion pm) {
		this.date = pm.getStartTime();
		this.stringDate = getStringDate(pm.getStartTime());
		this.fuelGoal = pm.getFuelGoal();
		this.distance = Math.round(pm.getDistance() * 100.0) / 100.0 ;
		this.activityId = pm.getActivityId();
		this.duration = getStringDuration(pm.getDuration());
		this.calories = pm.getCalories();
		this.steps =pm.getSteps();
		this.fuel =pm.getFuel();
	}
	
	public String getStringDuration(String duration){
		String[] testing = duration.split(":");
		
		return testing[0] + ":" + testing[1];
	}
	
	public String getStringDate(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
		return formatter.format(date);
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStringDate() {
		return stringDate;
	}

	public void setStringDate(String stringDate) {
		this.stringDate = stringDate;
	}

	public Integer getFuelGoal() {
		return fuelGoal;
	}

	public void setFuelGoal(Integer fuelGoal) {
		this.fuelGoal = fuelGoal;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
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

	public List<PersonalMotionView> getPersonalMotionViews() {
		return personalMotionViews;
	}

	public void setPersonalMotionViews(List<PersonalMotionView> personalMotionViews) {
		this.personalMotionViews = personalMotionViews;
	}

	public List<WalkingBoundary> getWalkingBaselineView() {
		return walkingBaselineView;
	}

	public void setWalkingBaselineView(List<WalkingBoundary> walkingBaselineView) {
		this.walkingBaselineView = walkingBaselineView;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}

	public BaselineView getBaselineView() {
		return baselineView;
	}

	public void setBaselineView(BaselineView baselineView) {
		this.baselineView = baselineView;
	}
	
	
}
