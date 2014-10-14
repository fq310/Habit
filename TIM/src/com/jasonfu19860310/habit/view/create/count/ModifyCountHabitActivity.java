package com.jasonfu19860310.habit.view.create.count;

import com.jasonfu19860310.habit.model.CountHabit;
import com.jasonfu19860310.tim.R;
import android.os.Bundle;
import android.view.View;

public class ModifyCountHabitActivity extends CountHabitActivity {
	public ModifyCountHabitActivity() {
		super(new CountHabit());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialInfo();
	}

	private void initialInfo() {
		long id = getIntent().getLongExtra("id", -1);
		CountHabit habitInDB = getManager().getHabit(id);
		initialText(habitInDB.getName(), R.id.text_create_project_name);
		initialDateButton(habitInDB.getStartDate(), R.id.button_create_project_start_date);
		initialDateButton(habitInDB.getEndDate(), R.id.button_create_project_end_date);
	}
	
	@Override
	public void onSaveProject(View v) {
		if (isValidProject()) {
			getManager().updateHabit(getCountHabit());
			finish();
		}
	}

}
