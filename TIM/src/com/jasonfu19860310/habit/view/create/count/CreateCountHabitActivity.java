package com.jasonfu19860310.habit.view.create.count;

import android.view.View;
import com.jasonfu19860310.habit.model.CountHabit;

public class CreateCountHabitActivity extends CountHabitActivity {

	public CreateCountHabitActivity() {
		super(new CountHabit());
	}

	@Override
	public void onSaveProject(View v) {
		if (isValidProject()) {
			getManager().createNewHabit(getCountHabit());
			finish();
		}
	}
}
