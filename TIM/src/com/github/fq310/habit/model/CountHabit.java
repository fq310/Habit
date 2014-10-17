package com.github.fq310.habit.model;

import android.content.Context;
import android.content.Intent;

import com.github.fq310.habit.R;
import com.github.fq310.habit.adt.HabitDate;
import com.github.fq310.habit.view.execute.count.ExecuteCountHabitActivity;

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
	public CountHabit() {
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
	@Override
	public HabitDate getStartDate() {
		return startDate;
	}
	public void setStartDate(HabitDate startDate) {
		this.startDate = startDate;
	}
	@Override
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
	public void unCheck() {
		if (todayChecked > 0) {
			--todayChecked;
			--totalChecked;
		}
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
		return new Intent(context, ExecuteCountHabitActivity.class);
	}
	@Override
	public String getTipString(Context context) {
		String today = context.getString(R.string.count_habit_today);
		return " [" + today + ": " + getTodayChecked() + "/" + getTimesPerDay() + "] ";
	}
	private int getTotalTarget() {
		int days = getStartDate().daysFrom(getEndDate());
		return (int) (days * getTimesPerDay());
	}
	@Override
	public int getBackgroundClolor(Context context) {
		if (isTodayFinished() || isTotalFinished()) return COLOR_GREEN;
		return COLOR_TRANSPARENT;
	}
	private boolean isTotalFinished() {
		return getTotalChecked() >= getTotalTarget();
	}
	@Override
	public String getFinishRate() {
		return getTotalChecked() + "/" + getTotalTarget() ;
	}
	public boolean isTodayFinished() {
		return getTodayChecked() >= getTimesPerDay();
	}
}
