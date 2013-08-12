package com.jasonfu19860310.project;

import java.util.Calendar;

public class Project {
	public static final String ID = "id";
	private long id;
	private String name = "project";
	private Calendar startDate = Calendar.getInstance();
	private Calendar endDate = Calendar.getInstance();
	private int hours;
	private int minitues;
	private long totalMinitues = 0;
	private long totalFinishedMinitues = 0;
	private int totalPassedDays = 0;
	private boolean timer_started;
	private boolean timer_paused;
	private long timer_seconds;
	private Calendar timerStartDate = Calendar.getInstance();
	private String workdays = "0000000";
	
	public Calendar getEndDate() {
		return endDate;
	}
	
	public int getHours() {
		return hours;
	}

	public long getId() {
		return id;
	}

	public int getMinitues() {
		return minitues;
	}

	public String getName() {
		return name;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public int[] getWorkdays() {
		int length = workdays.length();
		int[] result = new int[length];
		for (int i = 0; i < length; ++i) {
			result[i] = Integer.valueOf(String.valueOf(workdays.charAt(i)));
		}
		return result;
	}
	
	public String getWorkdaysString() {
		return workdays;
	}
	
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setMinitues(int minitues) {
		this.minitues = minitues;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
	public void setWorkdays(String workdays) {
		this.workdays = workdays;
	}
	
	public void setWorkdays(int[] workdays) {
		StringBuilder result = new StringBuilder();
		for (int i : workdays) {
			result.append(i);
		}
		this.workdays = result.toString();
	}
	
	public long getTotalMinitues() {
		return totalMinitues;
	}

	public void setTotalMinitues(long totalMinitues) {
		this.totalMinitues = totalMinitues;
	}

	public long getTotalFinishedMinitues() {
		return totalFinishedMinitues;
	}

	public void setTotalFinishedMinitues(long totalFinishedMinitues) {
		this.totalFinishedMinitues = totalFinishedMinitues;
	}
	
	public int getTotalPassedDays() {
		return totalPassedDays;
	}

	public void setTotalPassedDays(int totalPassedDays) {
		this.totalPassedDays = totalPassedDays;
	}
	
	public boolean isTimer_started() {
		return timer_started;
	}

	public void setTimer_started(boolean timer_started) {
		this.timer_started = timer_started;
	}

	public boolean isTimer_paused() {
		return timer_paused;
	}

	public void setTimer_paused(boolean timer_paused) {
		this.timer_paused = timer_paused;
	}

	public long getTimer_seconds() {
		return timer_seconds;
	}

	public void setTimer_seconds(long timer_seconds) {
		this.timer_seconds = timer_seconds;
	}
	
	public Calendar getTimerStartDate() {
		return timerStartDate;
	}

	public void setTimerStartDate(Calendar timerStartDate) {
		this.timerStartDate = timerStartDate;
	}
}
