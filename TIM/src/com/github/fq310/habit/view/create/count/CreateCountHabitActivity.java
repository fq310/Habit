package com.github.fq310.habit.view.create.count;

import android.view.View;

import com.github.fq310.habit.model.CountHabit;

public class CreateCountHabitActivity extends CountHabitActivity {

	public CreateCountHabitActivity() {
		setCountHabit(new CountHabit());
	}

	@Override
	public void onSaveProject(View v) {
		if (isValidProject()) {
			getManager().createNewHabit(getCountHabit());
			finish();
		}
	}
}
