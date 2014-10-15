package com.github.fq310.habit.view.execute.timing;

import android.widget.TextView;

public class TimeText {
	private static final String COLON = ":";
	private TextView timeText;
	public TimeText(TextView timeText) {
		this.timeText = timeText;
	}

	
	private String[] getTimeStringArray() {
		return timeText.getText().toString().split(COLON);
	}
	
	private int[] getTimeIntArray() {
		String[] times = getTimeStringArray();
		int[] result = new int[times.length];
		for (int i = 0; i < times.length; ++i) {
			result [i] = Integer.valueOf(times[i]);
		}
		return result;
	}
	public int getIntMinute() {
		return getTimeIntArray()[1];
	}
	public int getIntSecond() {
		return getTimeIntArray()[2];
	}
	public int getIntHour() {
		return getTimeIntArray()[0];
	}
	
	public void setTime(int hours, int minutes, int seconds) {
		timeText.setText(
				formatTime(hours) + COLON + 
				formatTime(minutes) + COLON + 
				formatTime(seconds)
			);
	}

	private String formatTime(int time) {
		String timeString = String.valueOf(time);
		return time < 10 ? ("0" + timeString) : timeString;
	}
	
	public long getTotalSeconds() {
		int[] time = getTimeIntArray();
		long seconds = time[0] * 3600 +
				time[1] * 60 + time[2];
		return seconds;
	}
	
	public void setTime(long totalSeconds) {
		timeText.setText(getTimeStringFromSeconds(totalSeconds)); 
	}
	
	public static String getTimeStringFromSeconds(long totalSeconds) {
		String hours = stringOf(totalSeconds / (60*60));
		String minutes = stringOf((totalSeconds % 3600) / 60);
		String seconds = stringOf((totalSeconds % 3600) % 60);
		return hours + COLON + minutes + COLON + seconds;
	}
 	
	private static String stringOf(long time) {
		String result = Long.toString(time);
		if (result.length() < 2) return "0" + result;
		return result;
	}
	public void updateHourOnly(String hour) {
		int intHour = Integer.valueOf(hour);
		timeText.setText(formatTime(intHour) + COLON + 
				formatTime(getIntMinute()) + COLON + 
				formatTime(getIntSecond()));
	}

	public void updateMinutesOnly(String minute) {
		int intMinute = Integer.valueOf(minute);
		timeText.setText(formatTime(getIntHour()) + COLON + 
				formatTime(intMinute) + COLON + 
				formatTime(getIntSecond()));
	}

	public void updateSecondsOnly(String seconds) {
		int intSeconds = Integer.valueOf(seconds);
		timeText.setText(formatTime(getIntHour()) + COLON + 
				formatTime(getIntMinute()) + COLON + 
				formatTime(intSeconds));		
	}
	
	public String getTime() {
		return timeText.getText().toString();
	}
	public void setTime(String time) {
		timeText.setText(time);
	}
	
	public boolean isValidValue() {
		return isValidHour(getIntValue(getTimeStringArray()[0])) &&
				isValidMinute(getIntValue(getTimeStringArray()[1])) &&
				isValidSecond(getIntValue(getTimeStringArray()[2]));
	}

	private int getIntValue(String value) {
		if (value == null || value.length() == 0) {
			return 0;
		} else {
			return Integer.valueOf(value);
		}
	}
	
	private boolean isValidMinute(int value) {
		return 0 <= value && value <= 59;
	}
	
	private boolean isValidHour(int value) {
		return 0 <= value;
	}
	private boolean isValidSecond(int value) {
		return isValidMinute(value);
	}
	
	public boolean isZeroTime() {
		int hour = getIntHour();
		int minute = getIntMinute();
		int seconds = getIntSecond();
		if (hour == 0 && minute == 0 && seconds == 0) return true;
		return false;
	}


	public void increaseOneSecond() {
		int hours = getIntHour();
		int minutes = getIntMinute();
		int seconds = getIntSecond();
		if (0 <= seconds && seconds < 59) {
			++ seconds;
		} else if (seconds == 59) {
			seconds = 0;
			++ minutes;
		}
		if (minutes == 59){
			minutes = 0;
			++hours;
		}
		setTime(hours, minutes, seconds);
	}
}
