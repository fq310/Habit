package com.jasonfu19860310.habit.model;

import android.content.Context;
import android.content.Intent;

import com.jasonfu19860310.habit.adt.HabitDate;

public class CountHabit implements HabitListItem {
	private long id;
	private String name = "project";
	private HabitDate startDate = new HabitDate();
	private HabitDate endDate = new HabitDate();
	private long timesPerDay = 0;
	private long totalChecked = 0;
	private long todayChecked = 0;
	
	public CountHabit(long id, String name, long startDate,
			long endDate, long timesPerDay, long totalChecked,
			long todayChecked) {
		this.id = id;
		this.name = name;
		this.startDate.setTimeInMillis(startDate);
		this.endDate.setTimeInMillis(endDate);
		this.timesPerDay = timesPerDay;
		this.totalChecked = totalChecked;
		this.todayChecked = todayChecked;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HabitDate getStartDate() {
		return startDate;
	}
	public void setStartDate(HabitDate startDate) {
		this.startDate = startDate;
	}
	public HabitDate getEndDate() {
		return endDate;
	}
	public void setEndDate(HabitDate endDate) {
		this.endDate = endDate;
	}
	public long getTimesPerDay() {
		return timesPerDay;
	}
	public void setTimesPerDay(long timesPerDay) {
		this.timesPerDay = timesPerDay;
	}
	public long getTotalChecked() {
		return totalChecked;
	}
	public long getTodayChecked() {
		return todayChecked;
	}
	public void addCheck() {
		++todayChecked;
		++totalChecked;
	}
	public void modifyTodayChecked(int value) {
		todayChecked = value;
		totalChecked += (value - todayChecked);
	}
	public void modifyTotalChecked(int value) {
		totalChecked = value;
	}
	@Override
	public Intent getExecuteIntent(Context context) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getTipString(Context context) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getBackgroundClolor() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getFinishRate() {
		// TODO Auto-generated method stub
		return "";
	}
}
