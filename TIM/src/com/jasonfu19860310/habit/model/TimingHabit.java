package com.jasonfu19860310.habit.model;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;

import com.jasonfu19860310.habit.adt.HabitDate;
import com.jasonfu19860310.habit.view.execute.ExecuteHabitActivity;
import com.jasonfu19860310.tim.R;

public class TimingHabit implements HabitListItem {
	private long id;
	private String name = "project";
	private HabitDate startDate = new HabitDate();
	private HabitDate endDate = new HabitDate();
	private int hours;
	private int minitues;
	private long totalSeconds = 0;
	private long totalFinishedSeconds = 0;
	private int totalPassedDays = 0;
	private boolean timer_started;
	private boolean timer_paused;
	private long timer_seconds;
	private HabitDate timerDestroyDate = new HabitDate();
	
	public HabitDate getEndDate() {
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

	public HabitDate getStartDate() {
		return startDate;
	}

	public void setEndDate(HabitDate endDate) {
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
	
	public void setStartDate(HabitDate startDate) {
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
	
	public HabitDate getTimerDestroyDate() {
		return timerDestroyDate;
	}

	public void setTimerDestroyDate(HabitDate date) {
		this.timerDestroyDate = date;
	}

	public long getTimeSpentPerDay() {
		return this.getHours()*3600 + this.getMinitues()*60;
	}

	@Override
	public Intent getExecuteIntent(Context context) {
		return new Intent(context, ExecuteHabitActivity.class);
	}

	@Override
	public String getTipString(Context context) {
		String str = "";
		String paused = context.getString(R.string.paused);
		String started = context.getString(R.string.started);
		if (isTimer_paused()) {
			str = " [" + paused + "]";
		}
		if (isTimer_started()) {
			str = " [" + started + "]"; 
		}
		return str;
	}

	@Override
	public int getBackgroundClolor() {
		if (isTimer_paused()) {
			return COLOR_YELLOW;
		}
		if (isTimer_started()) {
			return COLOR_GREEN;
		}
		return COLOR_BLACK;
	}

	@Override
	public String getFinishRate() {
		float finishRate = ((float)getTotalFinishedSeconds()/getTotalSeconds()) * 100;
		DecimalFormat df = new DecimalFormat("###.##");
		return df.format(finishRate) + "%";
	}

}
