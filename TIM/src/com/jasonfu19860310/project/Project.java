package com.jasonfu19860310.project;

import java.util.Calendar;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

public class Project {
	private String name = "project";
	private Calendar startDate;
	private Calendar endDate;
	private int hours;
	private int minitues;
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
	
	public void showOn(Context context, ViewGroup parent) {
		Button projectButton = new Button(context);
        projectButton.setText(getName());
    	int parentWidth = parent.getWidth();
    	LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    	layoutParams.setMargins((int)(0.2*parentWidth), 3, (int)(0.2*parentWidth), 0);
    	parent.addView(projectButton, layoutParams);
	}
}
