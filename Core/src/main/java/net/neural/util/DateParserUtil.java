package net.zfp.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateParserUtil {
	
	public static Date setDateByDayOffset(Date date, Integer offset) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + offset);
		
		return calendar.getTime();
	}
	
	public static Date setMaximumDate(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMaximum(Calendar.MILLISECOND));

		return new Date(calendar.getTimeInMillis());
	}

	public static Date setMinimumDate(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMinimum(Calendar.MILLISECOND));

		return new Date(calendar.getTimeInMillis());
	}

	public static Date getBeginningOfDay(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMinimum(Calendar.MILLISECOND));

		return new Date(calendar.getTimeInMillis());
	}

	public static Date getEndOfDay(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMaximum(Calendar.MILLISECOND));

		return new Date(calendar.getTimeInMillis());
	}

	public static Date getBeginningOfHour(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE,
				calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMinimum(Calendar.MILLISECOND));

		return new Date(calendar.getTimeInMillis());
	}

	public static Date getEndOfHour(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE,
				calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMaximum(Calendar.MILLISECOND));

		return new Date(calendar.getTimeInMillis());
	}

	public static Date getBeginningOfMonth(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMinimum(Calendar.MILLISECOND));

		return new Date(calendar.getTimeInMillis());
	}

	public static Date getEndOfMonth(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMaximum(Calendar.MILLISECOND));

		return new Date(calendar.getTimeInMillis());
	}
	
	public static Date getBeginningOfYear(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH,
				calendar.getActualMinimum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMinimum(Calendar.MILLISECOND));

		return new Date(calendar.getTimeInMillis());
	}

	public static Date getEndOfYear(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH,
				calendar.getActualMaximum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMaximum(Calendar.MILLISECOND));

		return new Date(calendar.getTimeInMillis());
	}
	
	public static int getHourValue(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		if (calendar.get(Calendar.HOUR) == 0) return 12;
		else return calendar.get(Calendar.HOUR);
	}
	
	public static int getHourOfDayValue(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int getDayValue(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public static String getAMPMValue(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		if (calendar.get(Calendar.AM_PM) == 1) return "pm";
		else return "am";
	}
	
	public static Date getFirstDayOfWeek(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		return new Date(calendar.getTimeInMillis() - (calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek()) * AppConstants.DAY_IN_MILLISECONDS);
	}
	
	public static Date getEndOfWeek(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		calendar.add(Calendar.DATE, (7-calendar.get(Calendar.DAY_OF_WEEK)));
		
		return getEndOfDay(calendar.getTime());
	}
	
	public static int whichDaysOfWeek(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	public static int getDaysOfWeek(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		return calendar.getActualMaximum(Calendar.DAY_OF_WEEK);
	}
	
	public static int getDaysOfMonth(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static int getCurrentDate(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		return calendar.get(Calendar.DATE);
	}
	
}
