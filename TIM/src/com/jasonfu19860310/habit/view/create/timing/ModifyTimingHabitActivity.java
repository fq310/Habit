package com.jasonfu19860310.habit.view.create.timing;

import com.jasonfu19860310.habit.model.TimingHabit;
import com.jasonfu19860310.tim.R;
import android.os.Bundle;
import android.view.View;

public class ModifyTimingHabitActivity extends TimingHabitActivity {
	public ModifyTimingHabitActivity(TimingHabit habit) {
		super(new TimingHabit());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialInfo();
	}

	private void initialInfo() {
		long id = getIntent().getLongExtra("id", -1);
		TimingHabit habitInDB = getProjectManager().getProject(id);
		initialText(habitInDB.getName(), R.id.text_create_project_name);
		initialDateButton(habitInDB.getStartDate(), R.id.button_create_project_start_date);
		initialDateButton(habitInDB.getEndDate(), R.id.button_create_project_end_date);
		initialText(habitInDB.getMinitues(), R.id.text_create_project_minutues);
		initialText(habitInDB.getHours(), R.id.text_create_project_hours);
	}

	@Override
	public void onSaveProject(View v) {
		if (isValidProject()) {
			getProjectManager().updateProject(timingHabit);
			finish();
		}
	}
	
}
