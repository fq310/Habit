package com.jasonfu19860310.project;

import java.util.Calendar;

public class Project {
	private int id;
	private String name = "project";
	private Calendar startDate = Calendar.getInstance();
	private Calendar endDate = Calendar.getInstance();
	private int hours;
	private int minitues;
	private int totalMinitues = 0;
	private int totalFinishedMinitues = 0;
	private int totalPassedDays = 0;
	
	private int[] weekdays = new int[7];
	{
		for (int i = 0; i < weekdays.length; ++i) {
			weekdays[i] = 0;
		}
	}
	public Calendar getEndDate() {
		return endDate;
	}
	
	public int getHours() {
		return hours;
	}

	public int getId() {
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

	public int[] getWeekdays() {
		return weekdays;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public void setId(int id) {
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
	
	public void setWeekdays(int[] weekdays) {
		this.weekdays = weekdays;
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
