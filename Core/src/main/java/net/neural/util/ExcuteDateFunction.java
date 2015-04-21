package net.zfp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ExcuteDateFunction {
	private String strTodayDate;
	private String strHourlyDate;
	private String strMonthlyStartDate;
	private String strMonthlyEndDate;
	private String strYearlyStartDate;
	private String strYearlyEndDate;
//	private String strTodayDateTime;
	
	public ExcuteDateFunction(GregorianCalendar c){
		
		// get Hourly Date
		Calendar calendar =new GregorianCalendar(
				c.get(Calendar.YEAR),
				c.get(Calendar.MONTH),
				c.get(Calendar.DATE),
				c.get(Calendar.HOUR_OF_DAY),
				c.get(Calendar.MINUTE),
				c.get(Calendar.SECOND)); // Calendar.getInstance();
		DateFormat format= new SimpleDateFormat("yyyy-MM-dd");
		@SuppressWarnings("unused")
		DateFormat todayDateFormat= new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//		System.out.println("c.get(Calendar.DATE):"+c.get(Calendar.DATE));
//		System.out.println("c.get(Calendar.hour):"+c.get(Calendar.HOUR));
		
		String strTodayDate= new String();
		Date datetime = calendar.getTime();
		strTodayDate = format.format(datetime);
		System.out.println("Today Report Date: "+strTodayDate+" ");
		setStrTodayDate(strTodayDate);
		
//		String strTodayDateTime= new String();
//		datetime = calendar.getTime();
//		strTodayDateTime = todayDateFormat.format(datetime);
//		System.out.println("Today  Report DateTime: "+strTodayDateTime+" ");
//		setStrTodayDateTime(strTodayDateTime);
//		
		
		calendar.add((Calendar.DATE), -1);
		String strHourlyDate = new String();
		datetime = calendar.getTime();
	    strHourlyDate = format.format(datetime);
		System.out.println("Hourly Report Date: "+strHourlyDate+" ");
		setStrHourlyDate(strHourlyDate);
		
		//get Monthly Start & End Date
		calendar = new GregorianCalendar(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE)); // Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		datetime = calendar.getTime();
		String strMonthlyStartDate = new String();
		String strMonthlyEndDate = new String();
		String strTempDate= new String();
		System.out.println("Date time 33:" + datetime);
		strTempDate = format.format(datetime);
		strMonthlyStartDate=strTempDate.substring(0,8)+"01";
		strMonthlyEndDate = strTempDate.substring(0,8)+ calendar.getActualMaximum(Calendar.DATE);
		System.out.println("Monthly Report Start Date: "+strMonthlyStartDate +" End Date: "+ strMonthlyEndDate);
		setStrMonthlyStartDate(strMonthlyStartDate);
		setStrMonthlyEndDate(strMonthlyEndDate);
		
		//get Yearly Start & End Date
		calendar = new GregorianCalendar(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE)); // Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		datetime = calendar.getTime();
		String strYearlyStartDate = new String();
		String strYearlyEndDate = new String();
		strTempDate= new String();
		System.out.println("Date time 46:" + datetime);
		strTempDate = format.format(datetime);
		strYearlyStartDate=strTempDate.substring(0,5)+"01-01";
		strYearlyEndDate = strTempDate.substring(0,8)+ calendar.getActualMaximum(Calendar.DATE);
		System.out.println("Yearly Report Start Date: "+strYearlyStartDate +" End Date: "+ strYearlyEndDate);
		setStrYearlyStartDate(strYearlyStartDate);
		setStrYearlyEndDate(strYearlyEndDate);
		
	}
	
	public String getStrHourlyDate() {
		return strHourlyDate;
	}
	public void setStrHourlyDate(String strHourlyDate) {
		this.strHourlyDate = strHourlyDate;
	}
	public String getStrMonthlyStartDate() {
		return strMonthlyStartDate;
	}
	public void setStrMonthlyStartDate(String strMonthlyStartDate) {
		this.strMonthlyStartDate = strMonthlyStartDate;
	}
	public String getStrMonthlyEndDate() {
		return strMonthlyEndDate;
	}
	public void setStrMonthlyEndDate(String strMonthlyEndDate) {
		this.strMonthlyEndDate = strMonthlyEndDate;
	}
	public String getStrYearlyStartDate() {
		return strYearlyStartDate;
	}
	public void setStrYearlyStartDate(String strYearlyStartDate) {
		this.strYearlyStartDate = strYearlyStartDate;
	}
	public String getStrYearlyEndDate() {
		return strYearlyEndDate;
	}
	public void setStrYearlyEndDate(String strYearlyEndDate) {
		this.strYearlyEndDate = strYearlyEndDate;
	}

	public void setStrTodayDate(String strTodayDate) {
		this.strTodayDate = strTodayDate;
	}

	public String getStrTodayDate() {
		return strTodayDate;
	}

//	public void setStrTodayDateTime(String strTodayDateTime) {
//		this.strTodayDateTime = strTodayDateTime;
//	}
//
//	public String getStrTodayDateTime() {
//		return strTodayDateTime;
//	}
	
}
