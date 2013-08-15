package com.jasonfu19860310.tim.view.execute;

import com.jasonfu19860310.tim.R;
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

	private String formatTime(int hours) {
		return (String) (hours < 10 ? ("0" + hours) : hours);
	}
	
	public long getTotalSeconds() {
		int[] time = getTimeIntArray();
		long seconds = time[0] * 3600 +
				time[1] * 60 + time[2];
		return seconds;
	}
	public void setTime(int initalTime) {
		timeText.setText(R.string.inital_time);
	}
	
	public void setTime(long totalSeconds) {
		String hours = stringOf(totalSeconds / 60*60);
		String minutes = stringOf((totalSeconds % 3600) / 60);
		String seconds = stringOf((totalSeconds % 3600) % 60);
		timeText.setText(hours + COLON + minutes + COLON + seconds); 
	}
	
	private String stringOf(long time) {
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
}
