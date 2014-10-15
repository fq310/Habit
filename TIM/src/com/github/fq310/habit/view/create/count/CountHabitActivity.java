package com.github.fq310.habit.view.create.count;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.github.fq310.habit.R;
import com.github.fq310.habit.controller.CountHabitManager;
import com.github.fq310.habit.model.CountHabit;
import com.github.fq310.habit.view.create.HabitBaseActivity;

public abstract class CountHabitActivity extends HabitBaseActivity {
	private CountHabit countHabit;
	private CountHabitManager manager;
	public CountHabitActivity() {
		manager = new CountHabitManager(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout createHabitLayout = (LinearLayout) findViewById(R.id.layout_create_habit);
		LinearLayout inputTimingLayout = (LinearLayout) findViewById(R.id.timing_habit_input);
		createHabitLayout.removeView(inputTimingLayout);
	}
	
	@Override
	protected boolean isValidProject() {
		return isValidTimesPerDay() && super.isValidProject();
	}
	private boolean isValidTimesPerDay() {
		String timesPerDay = getDateFromText(R.id.text_countHabit_timesPerDay);
		if (timesPerDay == null || Integer.parseInt(timesPerDay) < 1) {
			showWarningMessage(R.string.warning, R.string.warning_times_per_day);
			return false;
		}
		getCountHabit().setTimesPerDay(Integer.parseInt(timesPerDay));
		return true;
	}
	
	protected CountHabit getCountHabit() {
		return countHabit;
	}

	protected CountHabitManager getManager() {
		return manager;
	}
	
	public void setCountHabit(CountHabit countHabit) {
		this.countHabit = countHabit;
		habit = countHabit;
	}
}
