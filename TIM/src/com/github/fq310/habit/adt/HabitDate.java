package com.github.fq310.habit.adt;

import java.util.Calendar;

public class HabitDate {
	private static final String TIME_STRING_FORMAT = "\\d+/\\d+/\\d+";
	private Calendar date = Calendar.getInstance();
	public HabitDate() {
	}
	
	public HabitDate(String textString) {
		setTimeFromString(textString);
	}
	
	public long getTimeInMillis() {
		return date.getTimeInMillis();
	}

	public void setTimeInMillis(long milliseconds) {
		date.setTimeInMillis(milliseconds);
	}
	
	public int daysFrom(HabitDate date) {
		if (date.getTimeInMillis() > getTimeInMillis()) {
			return getDaysBwtween(this.date, date.getCalendar());
		} else {
			return -getDaysBwtween(date.getCalendar(), this.date);
		}
	}
	
	public Calendar getCalendar() {
		Calendar newDate = Calendar.getInstance();
		newDate.setTimeInMillis(date.getTimeInMillis());
		return newDate;
	}
	/*
	 * [] : contains the start and end day
	 */
	private int getDaysBwtween(Calendar startDate, Calendar currentDate) {
		int days = currentDate.get(Calendar.DAY_OF_YEAR) - 
				startDate.get(Calendar.DAY_OF_YEAR);
		if (dateYearNotEqual(startDate, currentDate)) {
			startDate = (Calendar) startDate.clone();
			do {
				days += startDate.getActualMaximum(Calendar.DAY_OF_YEAR);
				startDate.add(Calendar.YEAR, 1);
			} while (dateYearNotEqual(currentDate, startDate));
		}
		return days + 1;
	}
	
	private boolean dateYearNotEqual(Calendar startDate, Calendar currentDate) {
		return currentDate.get(Calendar.YEAR) != startDate.get(Calendar.YEAR);
	}

	public boolean after(HabitDate end) {
		return date.after(end.getCalendar());
	}
	
	public void setTimeFromString(String timeText) {
		if (timeText == null) return;
		if (timeText.matches(TIME_STRING_FORMAT)) {
			String[] dates = timeText.split("/");
			date.set(Integer.valueOf(dates[0]), 
					Integer.valueOf(dates[1]) - 1, 
					Integer.valueOf(dates[2]));
		}
	}
	public static boolean isValidTimeString(String timeText) {
		return timeText.matches(TIME_STRING_FORMAT);
	}
	
	public int getYear() {
		return date.get(Calendar.YEAR);
	}

	public int getMonth() {
		return date.get(Calendar.MONTH);
	}

	public int getDayOfMonth() {
		return date.get(Calendar.DAY_OF_MONTH);
	}
	
	@Override
	public String toString() {
		return date.get(Calendar.YEAR) + "/" + 
				(date.get(Calendar.MONTH) + 1) + "/" +
				date.get(Calendar.DAY_OF_MONTH);
	}
}
