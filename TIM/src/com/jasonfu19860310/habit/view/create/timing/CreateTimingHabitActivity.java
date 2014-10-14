package com.jasonfu19860310.habit.view.create.timing;

import android.view.View;

import com.jasonfu19860310.habit.model.TimingHabit;

public class CreateTimingHabitActivity extends TimingHabitActivity {
	public CreateTimingHabitActivity() {
		super(new TimingHabit());
	}

	@Override
	public void onSaveProject(View v) {
		if (isValidProject()) {
			getProjectManager().saveNewProject(timingHabit);
			finish();
		}
	}
}
