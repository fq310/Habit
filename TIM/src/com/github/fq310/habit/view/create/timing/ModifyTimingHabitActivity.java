package com.github.fq310.habit.view.create.timing;

import com.github.fq310.habit.R;
import com.github.fq310.habit.model.TimingHabit;

import android.os.Bundle;
import android.view.View;

public class ModifyTimingHabitActivity extends TimingHabitActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		long id = getIntent().getLongExtra("id", -1);
		TimingHabit habitInDB = getProjectManager().getProject(id);
		setTimingHabit(habitInDB);
		initialInfo();
	}

	private void initialInfo() {
		initialText(getTimingHabit().getName(), R.id.text_create_project_name);
		initialDateButton(getTimingHabit().getStartDate(), R.id.button_create_project_start_date);
		initialDateButton(getTimingHabit().getEndDate(), R.id.button_create_project_end_date);
		initialText(getTimingHabit().getMinitues(), R.id.text_create_project_minutues);
		initialText(getTimingHabit().getHours(), R.id.text_create_project_hours);
	}

	@Override
	public void onSaveProject(View v) {
		if (isValidProject()) {
			getProjectManager().updateProject(getTimingHabit());
			finish();
		}
	}
	
}
