package com.github.fq310.habit.view.create.timing;

import android.view.View;

import com.github.fq310.habit.model.TimingHabit;

public class CreateTimingHabitActivity extends TimingHabitActivity {
	public CreateTimingHabitActivity() {
		setTimingHabit(new TimingHabit());
	}

	@Override
	public void onSaveProject(View v) {
		if (isValidProject()) {
			getProjectManager().saveNewProject(getTimingHabit());
			finish();
		}
	}
}
