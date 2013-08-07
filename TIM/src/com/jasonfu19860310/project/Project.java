package com.jasonfu19860310.project;

import java.util.Calendar;

public class Project {
	private long id;
	private String name = "project";
	private Calendar startDate = Calendar.getInstance();
	private Calendar endDate = Calendar.getInstance();
	private int hours;
	private int minitues;
	private int totalMinitues = 0;
	private int totalFinishedMinitues = 0;
	private int totalPassedDays = 0;
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
			result[i] = Integer.valueOf(workdays.charAt(i));
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
	
	public int getTotalMinitues() {
		return totalMinitues;
	}

	public void setTotalMinitues(int totalMinitues) {
		this.totalMinitues = totalMinitues;
	}

	public int getTotalFinishedMinitues() {
		return totalFinishedMinitues;
	}

	public void setTotalFinishedMinitues(int totalFinishedMinitues) {
		this.totalFinishedMinitues = totalFinishedMinitues;
	}
	
	public int getTotalPassedDays() {
		return totalPassedDays;
	}

	public void setTotalPassedDays(int totalPassedDays) {
		this.totalPassedDays = totalPassedDays;
	}
}
