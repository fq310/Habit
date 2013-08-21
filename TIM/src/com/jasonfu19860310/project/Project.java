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
	private long totalSeconds = 0;
	private long totalFinishedSeconds = 0;
	private int totalPassedDays = 0;
	private boolean timer_started;
	private boolean timer_paused;
	private long timer_seconds;
	private Calendar timerDestroyDate = Calendar.getInstance();
	
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
	
	public long getTotalSeconds() {
		return totalSeconds;
	}

	public void setTotalSeconds(long totalSeconds) {
		this.totalSeconds = totalSeconds;
	}

	public long getTotalFinishedSeconds() {
		return totalFinishedSeconds;
	}

	public void setTotalFinishedSeconds(long totalFinishedSeconds) {
		this.totalFinishedSeconds = totalFinishedSeconds;
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
	
	public Calendar getTimerDestroyDate() {
		return timerDestroyDate;
	}

	public void setTimerDestroyDate(Calendar date) {
		this.timerDestroyDate = date;
	}
}
