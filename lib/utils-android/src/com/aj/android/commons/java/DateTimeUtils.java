package com.aj.android.commons.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * Collection of Date and Time Utils
 * 
 * @author Akhil Jain
 *
 */
public class DateTimeUtils {

	public static final long MILLIS_PER_SECOND = 1000;
	public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
	public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
	public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

	/**
	 * Days of the week.
	 *
	 */
	public static enum Weekday{
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY,
		SUNDAY
	};

	/**
	 * Months of the year.
	 *
	 */
	public static enum Month{
		JANUARY,
		FEBRUARY,
		MARCH,
		APRIL,
		MAY,
		JUNE,
		JULY,
		AUGUST,
		SEPTEMBER,
		OCTOBER,
		NOVEMBER,
		DECEMBER
	};

	/**
	 * Gets Calendar Day int value for {@link Weekday}.
	 * @param weekday
	 * @return int -calendar value for day.
	 */
	private static int getCalendarWeek(Weekday weekday){
		int calendarWeekday=-1;
		switch (weekday) {
		case MONDAY:calendarWeekday=Calendar.MONDAY;break;
		case TUESDAY:calendarWeekday=Calendar.TUESDAY;break;
		case WEDNESDAY:calendarWeekday=Calendar.WEDNESDAY;break;
		case THURSDAY:calendarWeekday=Calendar.THURSDAY;break;
		case FRIDAY:calendarWeekday=Calendar.FRIDAY;break;
		case SATURDAY:calendarWeekday=Calendar.SATURDAY;break;
		case SUNDAY:calendarWeekday=Calendar.SUNDAY;break;
		}
		return calendarWeekday;
	}

	/**
	 * Gets Calendar Month value for {@link Month}.
	 * @param month
	 * @return int -calendar value for month.
	 */
	private static int getCalendarMonth(Month month){
		int calendarMonth=-1;
		switch (month) {
		case JANUARY:calendarMonth=Calendar.JANUARY;break;
		case FEBRUARY:calendarMonth=Calendar.FEBRUARY;break;
		case MARCH:calendarMonth=Calendar.MARCH;break;
		case APRIL:calendarMonth=Calendar.APRIL;break;
		case MAY:calendarMonth=Calendar.MAY;break;
		case JUNE:calendarMonth=Calendar.JUNE;break;
		case JULY:calendarMonth=Calendar.JULY;break;
		case AUGUST:calendarMonth=Calendar.AUGUST;break;
		case SEPTEMBER:calendarMonth=Calendar.SEPTEMBER;break;
		case OCTOBER:calendarMonth=Calendar.OCTOBER;break;
		case NOVEMBER:calendarMonth=Calendar.NOVEMBER;break;
		case DECEMBER:calendarMonth=Calendar.DECEMBER;break;
		}
		return calendarMonth;
	}

	/***
	 * Parse {@link String} to {@link Date} based on given date format using the provided Locale.
	 * 
	 * @param dateString -{@link String} which has to be parsed.
	 * @param sourceDateFormat -{@link String} format of the date.
	 * @param locale -{@link Locale} to be used.
	 * @return -{@link Date} parsed date.
	 * @throws ParseException exception when parsing the date.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if string parameter are blank or 0 - length.
	 */
	public static Date parseStringToDate(String dateString,String sourceDateFormat,Locale locale) throws ParseException{
		if(StringUtils.isNull(dateString)){
			throw new NullPointerException("dateString cannot be null");
		}
		if(StringUtils.isEmpty(dateString) || StringUtils.isBlank(dateString)){
			throw new IllegalArgumentException("dateString cannot be empty");
		}
		if(StringUtils.isNull(sourceDateFormat)){
			throw new NullPointerException("sourceDateFormat cannot be null");
		}
		if(StringUtils.isEmpty(sourceDateFormat) || StringUtils.isBlank(sourceDateFormat)){
			throw new IllegalArgumentException("sourceDateFormat cannot be empty");
		}
		if(StringUtils.isNull(locale)){
			throw new NullPointerException("locale cannot be null");
		}

		Date parsedDate=null;
		SimpleDateFormat originalFormat = new SimpleDateFormat(sourceDateFormat,locale);
		parsedDate = originalFormat.parse(dateString);
		return parsedDate;
	}

	/**
	 * Parse the {@link Date} to {@link String} based on given source and destination format using the provided Locale.
	 * 
	 * @param date -{@link Date} object which needs to be parsed.
	 * @param originalDateFormat -{@link String} original format of the date. 
	 * @param newDateFormat -{@link String} target date format.
	 * @param locale -{@link Locale} locale of the object.
	 * @return -{@link String} parsed date.
	 * @throws ParseException exception when parsing the date.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if string parameter are blank or 0 - length.
	 */
	public static String parseDateToString(Date date,String originalDateFormat,String newDateFormat,Locale locale) throws ParseException{
		if(StringUtils.isNull(date)){
			throw new NullPointerException("date cannot be null");
		}
		if(StringUtils.isNull(originalDateFormat)){
			throw new NullPointerException("originalDateFormat cannot be null");
		}
		if(StringUtils.isEmpty(originalDateFormat) || StringUtils.isBlank(originalDateFormat)){
			throw new IllegalArgumentException("originalDateFormat cannot be empty");
		}
		if(StringUtils.isNull(newDateFormat)){
			throw new NullPointerException("newDateFormat cannot be null");
		}
		if(StringUtils.isEmpty(newDateFormat) || StringUtils.isBlank(newDateFormat)){
			throw new IllegalArgumentException("newDateFormat cannot be empty");
		}
		if(StringUtils.isNull(locale)){
			throw new NullPointerException("locale cannot be null");
		}
		Date parsedDate=null;
		SimpleDateFormat originalFormat = new SimpleDateFormat(originalDateFormat,locale);
		parsedDate = originalFormat.parse(date.toString());
		SimpleDateFormat  targetFormat = new SimpleDateFormat(newDateFormat,locale);
		String formattedDate = targetFormat.format(parsedDate);
		return formattedDate;
	}

	/**
	 * Format given {@link String} to {@link Date} based on given source and destination format using the provided Locale.
	 *  
	 * @param dateString -Date in {@link String} form which has to be formatted.
	 * @param originalDateFormat -{@link String} original format of the date.
	 * @param newDateFormat -{@link String} target date format.
	 * @param locale -{@link Locale} locale of the object.
	 * @return - parsed {@link Date} object.
	 * @throws ParseException exception when parsing the date.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if string parameter are blank or 0 - length.
	 */
	public static Date formatStringToDate(String dateString,String originalDateFormat,String newDateFormat,Locale locale) throws ParseException{
		if(StringUtils.isNull(dateString)){
			throw new NullPointerException("dateString cannot be null");
		}
		if(StringUtils.isEmpty(dateString) || StringUtils.isBlank(dateString)){
			throw new IllegalArgumentException("dateString cannot be empty");
		}
		if(StringUtils.isNull(originalDateFormat)){
			throw new NullPointerException("originalDateFormat cannot be null");
		}
		if(StringUtils.isEmpty(originalDateFormat) || StringUtils.isBlank(originalDateFormat)){
			throw new IllegalArgumentException("originalDateFormat cannot be empty");
		}
		if(StringUtils.isNull(newDateFormat)){
			throw new NullPointerException("newDateFormat cannot be null");
		}
		if(StringUtils.isEmpty(newDateFormat) || StringUtils.isBlank(newDateFormat)){
			throw new IllegalArgumentException("newDateFormat cannot be empty");
		}
		if(StringUtils.isNull(locale)){
			throw new NullPointerException("locale cannot be null");
		}
		Date parsedDate=null;
		String formattedDate=null;;
		SimpleDateFormat originalFormat = new SimpleDateFormat(originalDateFormat,locale);
		parsedDate = originalFormat.parse(dateString);

		SimpleDateFormat  targetFormat = new SimpleDateFormat(newDateFormat,locale);
		formattedDate = targetFormat.format(parsedDate);
		return parseStringToDate(formattedDate, newDateFormat, locale);
	}

	/**
	 * Format one date to another date based on given source and destination format using the provided Locale.
	 * <br>
	 * Internally calls {@link #formatDatetoString(Date, String, String, Locale)}
	 * 
	 * @param date -{@link Date} object which has to be formatted.
	 * @param originalDateFormat -{@link String} original format of the date.
	 * @param newDateFormat -{@link String} target date format.
	 * @param locale -{@link Locale} locale of the object.
	 * @return -formated {@link Date} object.
	 * @throws ParseException exception when parsing the date.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if string parameter are blank or 0 - length.
	 */
	public static Date formatDate(Date date,String originalDateFormat,String newDateFormat,Locale locale) throws ParseException{
		if(StringUtils.isNull(date)){
			throw new NullPointerException("date cannot be null");
		}
		if(StringUtils.isNull(originalDateFormat)){
			throw new NullPointerException("originalDateFormat cannot be null");
		}
		if(StringUtils.isEmpty(originalDateFormat) || StringUtils.isBlank(originalDateFormat)){
			throw new IllegalArgumentException("originalDateFormat cannot be empty");
		}
		if(StringUtils.isNull(newDateFormat)){
			throw new NullPointerException("newDateFormat cannot be null");
		}
		if(StringUtils.isEmpty(newDateFormat) || StringUtils.isBlank(newDateFormat)){
			throw new IllegalArgumentException("newDateFormat cannot be empty");
		}
		if(StringUtils.isNull(locale)){
			throw new NullPointerException("locale cannot be null");
		}
		return formatStringToDate(date.toString(),originalDateFormat, newDateFormat, locale);
	}

	/**
	 * Format {@link String} date to {@link String} date based on given source and destination format using the provided Locale.
	 * 
	 * @param dateString -Date in {@link String} form which has to be formatted.
	 * @param originalDateFormat -{@link String} original format of the date.
	 * @param newDateFormat -{@link String} target date format.
	 * @param locale -{@link Locale} locale of the object.
	 * @return -{@link String} formated date.
	 * @throws ParseException exception when parsing the date.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if string parameter are blank or 0 - length.
	 */
	public static String formatStringDateToString(String dateString,String originalDateFormat,String newDateFormat,Locale locale) throws ParseException{
		if(StringUtils.isNull(dateString)){
			throw new NullPointerException("dateString cannot be null");
		}
		if(StringUtils.isEmpty(dateString) || StringUtils.isBlank(dateString)){
			throw new IllegalArgumentException("dateString cannot be empty");
		}
		if(StringUtils.isNull(originalDateFormat)){
			throw new NullPointerException("originalDateFormat cannot be null");
		}
		if(StringUtils.isEmpty(originalDateFormat) || StringUtils.isBlank(originalDateFormat)){
			throw new IllegalArgumentException("originalDateFormat cannot be empty");
		}
		if(StringUtils.isNull(newDateFormat)){
			throw new NullPointerException("newDateFormat cannot be null");
		}
		if(StringUtils.isEmpty(newDateFormat) || StringUtils.isBlank(newDateFormat)){
			throw new IllegalArgumentException("newDateFormat cannot be empty");
		}
		if(StringUtils.isNull(locale)){
			throw new NullPointerException("locale cannot be null");
		}
		Date parsedDate=null;
		String formattedDate=null;;
		SimpleDateFormat originalFormat = new SimpleDateFormat(originalDateFormat,locale);
		parsedDate = originalFormat.parse(dateString);

		SimpleDateFormat  targetFormat = new SimpleDateFormat(newDateFormat,locale);
		formattedDate = targetFormat.format(parsedDate);
		return formattedDate;
	}

	/**
	 * Format {@link String} to {@link Date} object based on given source and destination format using the provided Locale.
	 * <br>
	 * Internally calls {@link #formatStringDateToString(String, String, String, Locale)}.
	 * 
	 * @param date -{@link Date} which has to be formatted.
	 * @param originalDateFormat -{@link String} original format of the date.
	 * @param newDateFormat -{@link String} target date format.
	 * @param locale -{@link Locale} locale of the object.
	 * @return -{@link String} formated date.
	 * @throws ParseException exception when parsing the date.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if string parameter are blank or 0 - length.
	 */
	public static String formatDatetoString(Date date,String originalDateFormat,String newDateFormat,Locale locale) throws ParseException{
		if(StringUtils.isNull(date)){
			throw new NullPointerException("date cannot be null");
		}
		if(StringUtils.isNull(originalDateFormat)){
			throw new NullPointerException("originalDateFormat cannot be null");
		}
		if(StringUtils.isEmpty(originalDateFormat) || StringUtils.isBlank(originalDateFormat)){
			throw new IllegalArgumentException("originalDateFormat cannot be empty");
		}
		if(StringUtils.isNull(newDateFormat)){
			throw new NullPointerException("newDateFormat cannot be null");
		}
		if(StringUtils.isEmpty(newDateFormat) || StringUtils.isBlank(newDateFormat)){
			throw new IllegalArgumentException("newDateFormat cannot be empty");
		}
		if(StringUtils.isNull(locale)){
			throw new NullPointerException("locale cannot be null");
		}
		return formatStringDateToString(date.toString(),originalDateFormat, newDateFormat, locale);
	}

	/**
	 * Get {@link Date} based on given {@link Calendar} instance.
	 * 
	 * @param calendar -{@link Calendar} object.
	 * @return -parsed {@link Date} from calendar. 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Date getDateFromCalendar(Calendar calendar){
		if(StringUtils.isNull(calendar)){
			throw new NullPointerException("callendar cannot ne null");
		}
		return new Date(calendar.getTimeInMillis());
	}

	/**
	 * Get {@link Calendar} instance based on given {@link Date} object.  
	 * @param date -{@link Date} object.
	 * @return -parsed {@link Calendar} object.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Calendar getCalendarFromDate(Date date){
		if(StringUtils.isNull(date)){
			throw new NullPointerException("date cannot be null");
		}
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * Used internally to add/subtract amount to specific calendar field.
	 * 
	 * @param date -{@link Date} object.
	 * @param calendarField -calendar specific field.
	 * @param amount -int, value by which field should be changed.
	 * @return -new {@link Date} object with updated time.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	private static Date add(Date date, int calendarField, int amount) {
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	/**
	 * Add / Subtract given no of minutes to {@link Date}.
	 * 
	 * @param date -{@link Date} object.
	 * @param minutesToBeAdded -amount by which to be changed.
	 * @return -new {@link Date} object with updated time. 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Date addMinuteToDate(Date date,int minutesToBeAdded){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		return add(date, Calendar.MINUTE, minutesToBeAdded);
	}

	/***
	 *  Add / Subtract given no of hours to {@link Date}.
	 *  
	 * @param date -{@link Date} object.
	 * @param hoursToBeAdded -amount by which to be changed.
	 * @return -new {@link Date} object with updated time.
	 * @throws NullPointerException if any of the parameters is null. 
	 */
	public static Date addHoursToDate(Date date,int hoursToBeAdded){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		return add(date, Calendar.HOUR, hoursToBeAdded);
	}

	/***
	 *  Add / Subtract given no of days to {@link Date}.
	 *  
	 * @param date -{@link Date} object.
	 * @param daysToBeAdded -amount by which to be changed.
	 * @return -new {@link Date} object with updated time.
	 * @throws NullPointerException if any of the parameters is null. 
	 */
	public static Date addDaysToDate(Date date,int daysToBeAdded){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		return add(date, Calendar.DATE, daysToBeAdded);
	}

	/***
	 *  Add / Subtract given no of months to {@link Date}.
	 *  
	 * @param date -{@link Date} object.
	 * @param monthsToBeAdded -amount by which to be changed.
	 * @return -new {@link Date} object with updated time.
	 * @throws NullPointerException if any of the parameters is null. 
	 */
	public static Date addMonthsToDate(Date date,int monthsToBeAdded){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		return add(date, Calendar.MONTH, monthsToBeAdded);
	}

	/***
	 *  Add / Subtract given no of years to {@link Date}.
	 *  
	 * @param date -{@link Date} object.
	 * @param yearsToBeAdded -amount by which to be changed.
	 * @return -new {@link Date} object with updated time.
	 * @throws NullPointerException if any of the parameters is null. 
	 */
	public static Date addYearToDate(Date date,int yearsToBeAdded){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		return add(date, Calendar.YEAR, yearsToBeAdded);
	}

	/**
	 * Calculates the difference between the given {@link Date} in milliseconds.
	 * 
	 * @param date1 -{@link Date} object.
	 * @param date2 -{@link Date} object.
	 * @return -time difference in long milliseconds.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static long calculateDiffInMilliSeconds(Date date1,Date date2){
		if (StringUtils.isNull(date1)) {
			throw new NullPointerException("date1 cannot be null");
		}
		if (StringUtils.isNull(date2)) {
			throw new NullPointerException("date2 cannot be null");
		}
		long milliseconds1 = date1.getTime();
		long milliseconds2 =date2.getTime();
		long differenceInMilliSeconds = milliseconds2 - milliseconds1;  
		return differenceInMilliSeconds;
	}

	/**
	 * Calculates the difference between the given {@link Date} in seconds.
	 * <br>
	 * Internally calls {@link #calculateDiffInMilliSeconds(Date, Date)}. 
	 * @param date1 -{@link Date} object.
	 * @param date2 -{@link Date} object.
	 * @return -time difference in long seconds.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static long calculateDiffInSeconds(Date date1,Date date2){
		if (StringUtils.isNull(date1)) {
			throw new NullPointerException("date1 cannot be null");
		}
		if (StringUtils.isNull(date2)) {
			throw new NullPointerException("date2 cannot be null");
		}
		long differenceInSeconds = (calculateDiffInMilliSeconds(date1, date2)) / MILLIS_PER_SECOND;
		return differenceInSeconds;
	}

	/**
	 * Calculates the difference between the given {@link Date} in minutes.
	 * <br>
	 * Internally calls {@link #calculateDiffInMilliSeconds(Date, Date)}. 
	 * @param date1 -{@link Date} object.
	 * @param date2 -{@link Date} object.
	 * @return -time difference in long minutes.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static long calculateDiffInMinutes(Date date1,Date date2){
		if (StringUtils.isNull(date1)) {
			throw new NullPointerException("date1 cannot be null");
		}
		if (StringUtils.isNull(date2)) {
			throw new NullPointerException("date2 cannot be null");
		}
		long differenceInMinutes = (calculateDiffInMilliSeconds(date1, date2)) / MILLIS_PER_MINUTE;
		return differenceInMinutes;
	}

	/**
	 * Calculates the difference between the given {@link Date} in hours.
	 * <br>
	 * Internally calls {@link #calculateDiffInMilliSeconds(Date, Date)}. 
	 * @param date1 -{@link Date} object.
	 * @param date2 -{@link Date} object.
	 * @return -time difference in long hours.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static long calculateDiffInHours(Date date1,Date date2){
		if (StringUtils.isNull(date1)) {
			throw new NullPointerException("date1 cannot be null");
		}
		if (StringUtils.isNull(date2)) {
			throw new NullPointerException("date2 cannot be null");
		}
		long differenceInHours = (calculateDiffInMilliSeconds(date1, date2)) / MILLIS_PER_HOUR;
		return differenceInHours;
	}

	/**
	 * Calculates the difference between the given {@link Date} in days.
	 * <br>
	 * Internally calls {@link #calculateDiffInHours(Date, Date)}. 
	 * @param date1 -{@link Date} object.
	 * @param date2 -{@link Date} object.
	 * @return -time difference in long days.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static long calculateDiffInDays(Date date1,Date date2){
		if (StringUtils.isNull(date1)) {
			throw new NullPointerException("date1 cannot be null");
		}
		if (StringUtils.isNull(date2)) {
			throw new NullPointerException("date2 cannot be null");
		}
		long differenceInDays = (calculateDiffInHours(date1, date2)) / 24;
		return differenceInDays;
	}

	/**
	 * Calculates the difference between the given {@link Date} in weeks.
	 * <br>
	 * Internally calls {@link #calculateDiffInDays(Date, Date)}. 
	 * @param date1 -{@link Date} object.
	 * @param date2 -{@link Date} object.
	 * @return -time difference in long weeks.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static long calculateDiffInWeeks(Date date1,Date date2){
		if (StringUtils.isNull(date1)) {
			throw new NullPointerException("date1 cannot be null");
		}
		if (StringUtils.isNull(date2)) {
			throw new NullPointerException("date2 cannot be null");
		}
		long differenceInWeeks = (calculateDiffInDays(date1, date2)) / 7;
		return differenceInWeeks;
	}

	/**
	 * Calculates the difference between the given {@link Date} in months.
	 * @param date1 -{@link Date} object.
	 * @param date2 -{@link Date} object.
	 * @return -time difference in long months.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static long calculateDiffInMonths(Date date1,Date date2){
		if (StringUtils.isNull(date1)) {
			throw new NullPointerException("date1 cannot be null");
		}
		if (StringUtils.isNull(date2)) {
			throw new NullPointerException("date2 cannot be null");
		}
		Calendar cal1=Calendar.getInstance();
		Calendar cal2=Calendar.getInstance();

		cal1.setTime(date1);
		cal2.setTime(date2);
		long m1 = cal1.get(Calendar.YEAR) * 12 + cal1.get(Calendar.MONTH);
		long m2 = cal2.get(Calendar.YEAR) * 12 + cal2.get(Calendar.MONTH);
		return (m2 - m1) ;
	}

	/**
	 * Calculates the difference between the given {@link Date} in years.
	 * <br>
	 * Internally calls {@link #calculateDiffInMonths(Date, Date)}.
	 * @param date1 -{@link Date} object.
	 * @param date2 -{@link Date} object.
	 * @return -time difference in long years.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static long calculateDiffInYears(Date date1,Date date2){
		if (StringUtils.isNull(date1)) {
			throw new NullPointerException("date1 cannot be null");
		}
		if (StringUtils.isNull(date2)) {
			throw new NullPointerException("date2 cannot be null");
		}
		
		long differenceInYears = (calculateDiffInMonths(date1, date2)) / 12;
		return differenceInYears;
	}

	/**
	 * Get current system time in milliseconds.<br> 
	 * For more read here {@link System#currentTimeMillis()}.
	 * 
	 * @return -time in long milliseconds.
	 */
	public static long getCurrentTimeInMilliSeconds(){
		return System.currentTimeMillis();
	}

	/**
	 * Get current system time in seconds.<br> 
	 * For more read here {@link System#currentTimeMillis()}.
	 * 
	 * @return -time in long seconds.
	 */
	public static long getCurrentTimeInSeconds(){
		return (System.currentTimeMillis()/MILLIS_PER_SECOND);
	}

	/**
	 * Gets the number of days present in the given year and month.
	 * <br>
	 * If <b>blank, empty or null </b>argument is passed,<code> -1</code> will be returned.
	 * @param year -year in {@link String} form. 
	 * @param month -month in {@link String} form.
	 * @return int number of days in the given month in given year. 
	 */
	public static int getDaysInMonth(String year, String month) {
		int days = 0;
		if(StringUtils.isEmpty(year)||StringUtils.isBlank(year)||StringUtils.isEmpty(month)||StringUtils.isBlank(month)){
			days=-1;
		}
		else{
			if (month.equals("1") || month.equals("3") || month.equals("5")
					|| month.equals("7") || month.equals("8") || month.equals("10")
					|| month.equals("12")) {
				days = 31;
			} else if (month.equals("4") || month.equals("6") || month.equals("9")
					|| month.equals("11")) {
				days = 30;
			} else {
				if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
						|| Integer.parseInt(year) % 400 == 0) {
					days = 29;
				} else {
					days = 28;
				}
			}
		}
		return days;
	}

	/**
	 * Gets the number of days present in the given year and month.
	 * 
	 * @param year -year. 
	 * @param month -month.
	 * @return int number of days in the given month in given year.
	 */
	public static int getDaysinTheMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Gets the number of days present in month the given {@link Date}.
	 * 
	 * @param date -{@link Date}. 
	 * @return int number of days in the given date.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static int getDaysinTheMonth(Date date) {
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}


	/** 
	 * Gets the date / day element from today date.
	 * <br>
	 * Internally calls {@link #getDayFromDate(Date)}
	 * <br>
	 *  E.g.,
	 *  <pre>
	 *      getTodayDate() = 9
	 *      //where today date is Oct 09 12:04:47
	 *  </pre> 
	 *   
	 * @return int -the date/day from today.
	 * 
	 */
	public static int getTodayDate() {
		return getDayFromDate(new Date());
	}

	/** 
	 * Gets the month element from today date.
	 * <br>
	 * Internally calls {@link #getMonthFromDate(Date)}
	 * <br>
	 *  E.g.,
	 *  <pre>
	 *      getCurrentMonth() = 10
	 *      //where today date is Oct 09 12:04:47
	 *  </pre> 
	 *   
	 * @return int -the date/day from today.
	 * 
	 */
	public static int getCurrentMonth() {
		return getMonthFromDate(new Date());
	}

	/** 
	 * Gets the year element from today date.
	 * <br>
	 * Internally calls {@link #getYearFromDate(Date)}
	 * <br>
	 *  E.g.,
	 *  <pre>
	 *      getCurrentYear() = 2014
	 *      //where today date is Oct 09 12:04:47 2014
	 *  </pre> 
	 *   
	 * @return int -the year from today.
	 * 
	 */
	public static int getCurrentYear() {
		return getYearFromDate(new Date());
	}

	/** 
	 * Gets the date / day element from given {@link Date} object.
	 * 
	 * @param date -{@link Date} object.
	 * @return int -the date/day from given date.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static int getDayFromDate(Date date) {
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		Calendar calendar = Calendar.getInstance();
		if(date!=null){
			calendar.setTime(date);
		}
		return calendar.get(Calendar.DATE);
	}

	/** 
	 * Gets the month element from given {@link Date} object.
	 * 
	 * @param date -{@link Date} object.
	 * @return int -the month from given date.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static int getMonthFromDate(Date date) {
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		Calendar calendar = Calendar.getInstance();
		if(date!=null){
			calendar.setTime(date);
		}
		return calendar.get(Calendar.MONTH)+1;
	}

	/** 
	 * Gets the year element from given {@link Date} object.
	 * 
	 * @param date -{@link Date} object.
	 * @return int -the year from given date.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static int getYearFromDate(Date date) {
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		Calendar calendar = Calendar.getInstance();
		if(date!=null){
			calendar.setTime(date);
		}
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * Gets the first of the current month, based on today date.
	 * <br>
	 * Internally calls {@link #getFirstDayOfMonth(Date)}.
	 * @return {@link Date} -a new object with date set to 1st date of the month.
	 */
	public static Date getFirstDayOfMonth() {
		return getFirstDayOfMonth(new Date());
	}

	/**
	 * Gets the first day of the month, based on given date.
	 * <br>
	 * Internally calls {@link #getFirstDayOfMonth(Date)}.
	 * @param date -{@link Date} object.
	 * @return {@link Date} -a new object with date set to 1st date of the given month.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Date getFirstDayOfMonth(Date date) {
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * Gets the last day of the current month, based on today date.
	 * <br>
	 * Internally calls {@link #getLastDayOfMonth(Date)}.
	 * @return {@link Date} -a new object with date set to last date of the month.
	 */
	public static Date getLastDayOfMonth() {
		return getLastDayOfMonth(new Date());
	}

	/**
	 * Gets the last day of the current month, based on given date.
	 * @param date -{@link Date} object.
	 * @return {@link Date} -a new object with date set to last date of the given month.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Date getLastDayOfMonth(Date date) {
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * Get previous week from today date.
	 *  <br>
	 *  Internally calls {@link #getPreviousWeek(Date)}.
	 * @return {@link Date} -a new object with same date / day as today in previous week. 
	 */
	public static Date getPreviousWeek(){
		return getPreviousWeek(new Date());
	}

	/**
	 * Get previous week from given date.
	 * @param date -{@link Date} object.
	 * @return {@link Date} -a new object with same date / day from given date in previous week.
	 * @throws NullPointerException if any of the parameters is null. 
	 */
	public static Date getPreviousWeek(Date date){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		return addDaysToDate(date, -7);
	}

	/**
	 * Get next week from today date.
	 *  <br>
	 *  Internally calls {@link #getNextWeek(Date)}.
	 * @return {@link Date} -a new object with same date / day as today in previous week.
	 */
	public static Date getNextWeek(){
		return getNextWeek(new Date());
	}

	/**
	 * Get next week from given date.
	 * @param date -{@link Date} object.
	 * @return {@link Date} -a new object with same date / day from given date in next week.
	 * @throws NullPointerException if any of the parameters is null. 
	 */
	public static Date getNextWeek(Date date){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		return addDaysToDate(date, 7);
	}


	/**
	 * Get previous month from today date.
	 *  <br>
	 *  Internally calls {@link #getPreviousMonth(Date)}.
	 * @return {@link Date} -a new object with same date / day as today in previous month. 
	 */
	public static Date getPreviousMonth(){
		return getPreviousMonth(new Date());
	}

	/**
	 * Get next month from given date.
	 * @param date -{@link Date} object.
	 * @return {@link Date} -a new object with same date / day from given date in previous month.
	 * @throws NullPointerException if any of the parameters is null. 
	 */
	public static Date getPreviousMonth(Date date){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		return addMonthsToDate(date, -1);
	}

	/**
	 * Get next month from today date.
	 *  <br>
	 *  Internally calls {@link #getNextMonth(Date)}.
	 * @return {@link Date} -a new object with same date / day as today in next month. 
	 */
	public static Date getNextMonth(){
		return getNextMonth(new Date());
	}

	/**
	 * Get next month from given date.
	 * @param date -{@link Date} object.
	 * @return {@link Date} -a new object with same date / day from given date in next month.
	 * @throws NullPointerException if any of the parameters is null. 
	 */
	public static Date getNextMonth(Date date){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		return addMonthsToDate(date, 1);
	}

	/**
	 * Gets 1st Sunday of the month in today date.
	 * <br>
	 * Internally calls {@link #getFirstSundayOfMonth(Date)}.
	 * @return {@link Date} -a new object with date / day as 1st Sunday from today date. 
	 */
	public static Date getFirstSundayOfMonth(){
		return getFirstSundayOfMonth(new Date());
	}

	/**
	 * Gets last Sunday of the month in today date.
	 * <br>
	 * Internally calls {@link #getLastSundayOfMonth(Date)}.
	 * @return {@link Date} -a new object with date / day as last Sunday from today date. 
	 */
	public static Date getLastSundayOfMonth(){
		return getLastSundayOfMonth(new Date());
	}

	/**
	 * Gets 1st Monday of the month in today date.
	 * <br>
	 * Internally calls {@link #getFirstMondayOfMonth(Date)}.
	 * @return {@link Date} -a new object with date / day as 1st Monday from today date. 
	 */
	public static Date getFirstMondayOfMonth(){
		return getFirstMondayOfMonth(new Date());
	}

	/**
	 * Gets last Monday of the month in today date.
	 * <br>
	 * Internally calls {@link #getLastMondayOfMonth(Date)}.
	 * @return {@link Date} -a new object with date / day as last Monday from today date. 
	 */
	public static Date getLastMondayOfMonth(){
		return getLastMondayOfMonth(new Date());
	}

	/**
	 * Gets 1st Sunday of the month in given date.
	 * @param date -{@link Date} object.
	 * @return {@link Date} -a new object with date / day as 1st Sunday from given date.
	 * @throws NullPointerException if any of the parameters is null. 
	 */
	public static Date getFirstSundayOfMonth(Date date){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		return getDateFromCalendar(getNthOfMonth(1,Calendar.SUNDAY,getMonthFromDate(date)-1,getYearFromDate(date)));
	}

	/**
	 * Gets last Sunday of the month in given date.
	 * @param date -{@link Date} object.
	 * @return {@link Date} -a new object with date / day as last Sunday from given date.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Date getLastSundayOfMonth(Date date){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		return getDateFromCalendar(getNthOfMonth(-1,Calendar.SUNDAY,getMonthFromDate(date)-1,getYearFromDate(date)));
	}

	/**
	 * Gets 1st Monday of the month in given date.
	 * @param date -{@link Date} object.
	 * @return {@link Date} -a new object with date / day as 1st Monday from given date. 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Date getFirstMondayOfMonth(Date date){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		return getDateFromCalendar(getNthOfMonth(1,Calendar.MONDAY,getMonthFromDate(date)-1,getYearFromDate(date)));
	}

	/**
	 * Gets last Monday of the month in given date.
	 * @param date -{@link Date} object.
	 * @return {@link Date} -a new object with date / day as last Monday from given date.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Date getLastMondayOfMonth(Date date){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		return getDateFromCalendar(getNthOfMonth(-1,Calendar.MONDAY,getMonthFromDate(date)-1,getYearFromDate(date)));
	}

	/**
	 * Gets the nth date from the given week, month and year.
	 *  
	 * @param nth -int value specifying nth value required.
	 * @param weekday -week from the {@link Weekday}.
	 * @param month -month from the {@link Month}.
	 * @param year -int year.
	 * @return {@link Date} -a new object date with nth date based on specified values. 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Date getNthOfMonth(int nth,Weekday weekday, Month month, int year){
		if (StringUtils.isNull(weekday)) {
			throw new NullPointerException("weekday cannot be null");
		}
		if (StringUtils.isNull(month)) {
			throw new NullPointerException("month cannot be null");
		}
		Calendar nthDate=getNthOfMonth(nth, getCalendarWeek(weekday), getCalendarMonth(month), year);
		return new Date(nthDate.getTimeInMillis());
	}

	/**
	 * Gets the nth date from the given week, month and year.
	 *  
	 * @param nth -int value specifying nth value required.
	 * @param dayOfWeek -int value.
	 * @param month -int value.
	 * @param year -int year.
	 * @return {@link Calendar} -a new calendar instance with nth date based on specified values. 
	 */
	private static Calendar getNthOfMonth(int n, int dayOfWeek, int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, n);
		return calendar;
	} 

	/**
	 * Returns the string representation of time difference based on today date.
	 *  
	 * @param date -{@link Date} object.
	 * @return -{@link String} object.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static String timeDifference(Date date){
		if (StringUtils.isNull(date)) {
			throw new NullPointerException("date cannot be null");
		}
		String returnTimediff;
		Date nowdate=new Date();
		int diffInDays = (int) ((nowdate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
		int diffInHours = (int) ((nowdate.getTime() - date.getTime()) / (1000 * 60 * 60))%24;
		int diffInMins = (int) ((nowdate.getTime() - date.getTime()) / (1000 * 60 ))%60;
//		int diffInSec = (int) ((nowdate.getTime() - date.getTime()) / (1000  ))%60;
		if((diffInDays>=1)&&(diffInDays<2)){
			returnTimediff=" yesterday";	
		}else if(diffInDays>=2){
			returnTimediff=diffInDays+" days ago";
		}else if((diffInHours<24)&&(diffInHours>=1)){
			returnTimediff=""+diffInHours +" hours ago";
		}else if((diffInMins<60)&&(diffInMins>1)){
			returnTimediff=""+diffInMins+" minutes ago";
		}else{
			returnTimediff="few seconds ago";
		}
		return returnTimediff;
	}
}
