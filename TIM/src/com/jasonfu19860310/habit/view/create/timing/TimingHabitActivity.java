package com.jasonfu19860310.habit.view.create.timing;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.jasonfu19860310.habit.controller.TimingHabitManager;
import com.jasonfu19860310.habit.model.TimingHabit;
import com.jasonfu19860310.habit.view.create.HabitBaseActivity;
import com.jasonfu19860310.tim.R;

public abstract class TimingHabitActivity extends HabitBaseActivity {
	protected TimingHabit timingHabit;
	private TimingHabitManager projectManager;
	public TimingHabitActivity(TimingHabit habit) {
		super(habit);
		timingHabit = habit;
		projectManager = new TimingHabitManager(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout createHabitLayout = (LinearLayout) findViewById(R.id.layout_create_habit);
		LinearLayout inputCountLayout = (LinearLayout) findViewById(R.id.count_habit_input);
		createHabitLayout.removeView(inputCountLayout);
	}
	
	@Override
	protected boolean isValidProject() {
		return verifyTimePerDay() && super.isValidProject();
	}

	private boolean verifyTimePerDay() {
		String hours = getDateFromText(R.id.text_create_project_hours);
		String minutes = getDateFromText(R.id.text_create_project_minutues);
		if (hours == null && minutes == null) {
			showWarningMessage(R.string.warning, R.string.warning_input_hours);
			return false;
		}
		if (hours != null) {
			timingHabit.setHours(Integer.valueOf(hours)); } 
		else {
			timingHabit.setHours(0); }
		if (minutes != null) {
			timingHabit.setMinitues(Integer.valueOf(minutes)); } 
		else {
			timingHabit.setMinitues(0); }
		return true;
	}
	
	protected TimingHabitManager getProjectManager() {
		return projectManager;
	}
}
