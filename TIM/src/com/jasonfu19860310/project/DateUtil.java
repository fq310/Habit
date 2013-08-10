package com.jasonfu19860310.project;

import java.util.Calendar;

public class DateUtil {
	public static int getDaysBwtween(Calendar startDate, Calendar currentDate) {
		int days = currentDate.get(Calendar.DAY_OF_YEAR) - 
				startDate.get(Calendar.DAY_OF_YEAR);
		if (dateYearNotEqual(startDate, currentDate)) {
			startDate = (Calendar) startDate.clone();
			do {
				days += startDate.getActualMaximum(Calendar.DAY_OF_YEAR);
				startDate.add(Calendar.YEAR, 1);
			} while (dateYearNotEqual(currentDate, startDate));
		}
		return days;
	}
	
	private static boolean dateYearNotEqual(Calendar startDate, Calendar currentDate) {
		return currentDate.get(Calendar.YEAR) != startDate.get(Calendar.YEAR);
	}
}
