package net.zfp.entity.rule;

public class RuleResult {
	
	private boolean checkWeather = false;
	private boolean weatherCondition = false;
	private boolean checkBoundaries = false;
	private boolean boundariesCondition = false;
	private boolean checkStartTime = false;
	private boolean startTimeCondition = false;
	private boolean checkEndTime = false;
	private boolean endTimeCondition = false;
	
	
	public boolean isWeatherCondition() {
		return weatherCondition;
	}
	public void setWeatherCondition(boolean weatherCondition) {
		this.weatherCondition = weatherCondition;
	}
	public boolean isCheckWeather() {
		return checkWeather;
	}
	public void setCheckWeather(boolean checkWeather) {
		this.checkWeather = checkWeather;
	}
	public boolean isCheckBoundaries() {
		return checkBoundaries;
	}
	public void setCheckBoundaries(boolean checkBoundaries) {
		this.checkBoundaries = checkBoundaries;
	}
	public boolean isBoundariesCondition() {
		return boundariesCondition;
	}
	public void setBoundariesCondition(boolean boundariesCondition) {
		this.boundariesCondition = boundariesCondition;
	}
	public boolean isCheckStartTime() {
		return checkStartTime;
	}
	public void setCheckStartTime(boolean checkStartTime) {
		this.checkStartTime = checkStartTime;
	}
	public boolean isStartTimeCondition() {
		return startTimeCondition;
	}
	public void setStartTimeCondition(boolean startTimeCondition) {
		this.startTimeCondition = startTimeCondition;
	}
	public boolean isCheckEndTime() {
		return checkEndTime;
	}
	public void setCheckEndTime(boolean checkEndTime) {
		this.checkEndTime = checkEndTime;
	}
	public boolean isEndTimeCondition() {
		return endTimeCondition;
	}
	public void setEndTimeCondition(boolean endTimeCondition) {
		this.endTimeCondition = endTimeCondition;
	}
	
}
