package net.zfp.entity;

import java.util.Date;

public class DateNow {
	private Date currentDate = null;
	private boolean weekend = false;
	private int hourValue;
	
	public boolean isWeekend() {
		return weekend;
	}

	public void setWeekend(boolean weekend) {
		this.weekend = weekend;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public int getHourValue() {
		return hourValue;
	}

	public void setHourValue(int hourValue) {
		this.hourValue = hourValue;
	}
	
	
}
