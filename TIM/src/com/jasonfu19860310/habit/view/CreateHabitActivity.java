package com.jasonfu19860310.habit.view;

import android.view.View;

import com.jasonfu19860310.habit.model.Habit;

public class CreateHabitActivity extends HabitBaseActivity {

	public CreateHabitActivity() {
		super();
		project = new Habit();
	}

	@Override
	public void onSaveProject(View v) {
		if (isValidProject()) {
			getProjectManager().saveNewProject(project);
			finish();
		}
	}

}
