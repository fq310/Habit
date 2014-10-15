package com.github.fq310.habit.view.create.count;

import com.github.fq310.habit.R;
import android.os.Bundle;
import android.view.View;

public class ModifyCountHabitActivity extends CountHabitActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		long id = getIntent().getLongExtra("id", -1);
		setCountHabit(getManager().getHabit(id));
		initialInfo();
	}

	private void initialInfo() {
		initialText(getCountHabit().getName(), R.id.text_create_project_name);
		initialDateButton(getCountHabit().getStartDate(), R.id.button_create_project_start_date);
		initialDateButton(getCountHabit().getEndDate(), R.id.button_create_project_end_date);
		initialText("" + getCountHabit().getTimesPerDay(), R.id.text_countHabit_timesPerDay);
	}
	
	@Override
	public void onSaveProject(View v) {
		if (isValidProject()) {
			getManager().updateHabit(getCountHabit());
			finish();
		}
	}

}
