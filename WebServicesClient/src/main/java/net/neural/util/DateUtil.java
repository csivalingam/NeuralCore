package net.zfp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

	static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	static final SimpleDateFormat df2 = new SimpleDateFormat("MMM-dd yyyy");
	static final SimpleDateFormat df3 = new SimpleDateFormat("dd MMMMM, yyyy", Locale.FRENCH);
	static final SimpleDateFormat df4 = new SimpleDateFormat("MMM dd, yyyy");
	
	static public String printCalendar(Date d) {
		return df.format(d);
	}

	static public Date parseDate(String c) throws ParseException {
		Date d = df.parse(c);
		return d;
	}
	
	static public String printCalendar4(Date d){
		return df4.format(d);
	}
	
	static public String printCalendar2(Date d) {
		return df2.format(d);
	}
	
	static public String printCalendar2French(Date d) {
		return df3.format(d);
	}
	
	static public Date parseDate2(String c) throws ParseException {
		Date d = df2.parse(c);
		return d;
	}
	
	private static boolean isWeekend(Calendar calendar) {

		int dow = calendar.get (Calendar.DAY_OF_WEEK);

		return (dow == Calendar.SUNDAY) || (dow == Calendar.SATURDAY);
	}
	
	static public Date getContinousEndDate(Date startDate, String periodType, Integer currentStage, Integer period){
		periodType = periodType.toUpperCase();
		Date date = startDate;
		if (periodType.equals("MONTH")){
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(startDate);
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + (currentStage-1));
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
			calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
			
			date = calendar.getTime();
		}else if (periodType.equals("WEEK")){
			date = new Date(startDate.getTime() + (currentStage) * period * 7 * 24 * 60 * 60 * 1000l - 24 * 60 * 60 * 1000l);
		}else if (periodType.equals("DAY")){
			date = new Date(startDate.getTime() + (currentStage) * period * 24 * 60 * 60 * 1000l - 24 * 60 * 60 * 1000l);
		}else if (periodType.equals("WEEKEND") || periodType.equals("WEEKDAY")){
			int counter = 0;
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(startDate);
			
			while(true){
				if (periodType.equals("WEEKDAY")){
					if (!isWeekend(calendar)) break;
				}else if (periodType.equals("WEEKEND")){
					if (isWeekend(calendar)) break;
				}else{
					//Weried value get out of the loop
					break;
				}
				
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
			}
			if (periodType.equals("WEEKDAY")){
				period *= 5;
			}else if (periodType.equals("WEEKEND")){
				period *= 2;
			}
			
			for(int i = 1; i <= currentStage; i++){
				counter = 0;
				
				while( counter < period){
					calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
					
					if (periodType.equals("WEEKDAY")){
						if (!isWeekend(calendar)) counter++;
					}else if (periodType.equals("WEEKEND")){
						if (isWeekend(calendar)) counter++;
					}	
					//System.out.println("calendar : " + calendar.getTime() + " : " + counter + " : " + period);
				}
			}
			
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
			
			Date endDate = new Date(calendar.getTime().getTime() - 1000l);
			
			if (periodType.equals("WEEKEND")){
				calendar.setTime(endDate);
				while(true){
					if (periodType.equals("WEEKEND")){
						if (isWeekend(calendar)) break;
					}
					
					calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
				}
				endDate = calendar.getTime();
			}
			
			date = endDate;
		}
		
		return date;
	}
	
	static public Date getContinousStartDate(Date startDate, String periodType, Integer currentStage, Integer period){
		periodType = periodType.toUpperCase();
		Date date = startDate;
		if (periodType.equals("MONTH")){
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(startDate);
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + (currentStage-1));
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
			date = calendar.getTime();
		}else if (periodType.equals("WEEK")){
			date = new Date(startDate.getTime() + (currentStage-1) * period * 7 * 24 * 60 * 60 * 1000l);
		}else if (periodType.equals("DAY")){
			date = new Date(startDate.getTime() + (currentStage-1) * period * 24 * 60 * 60 * 1000l);
		}else if (periodType.equals("WEEKEND") || periodType.equals("WEEKDAY")){
			int counter = 0;
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(startDate);
			
			while(true){
				if (periodType.equals("WEEKDAY")){
					if (!isWeekend(calendar)) break;
				}else if (periodType.equals("WEEKEND")){
					if (isWeekend(calendar)) break;
				}else{
					//Weried value get out of the loop
					break;
				}
				
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
			}
			
			if (periodType.equals("WEEKDAY")){
				period *= 5;
			}else if (periodType.equals("WEEKEND")){
				period *= 2;
			}
			
			for(int i = 1; i < currentStage; i++){
				counter = 0;
				while( counter < period){
					calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
					
					if (periodType.equals("WEEKDAY")){
						if (!isWeekend(calendar)) counter++;
					}else if (periodType.equals("WEEKEND")){
						if (isWeekend(calendar)) counter++;
					}	
					//System.out.println("calendar : " + calendar.getTime() + " : " + counter + " : " + period);
				}
			}
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
			
			date = calendar.getTime();
		}
		
		return date;
	}
}
