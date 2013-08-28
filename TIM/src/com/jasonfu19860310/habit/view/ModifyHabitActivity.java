package com.jasonfu19860310.habit.view;

import com.jasonfu19860310.habit.adt.HabitDate;
import com.jasonfu19860310.tim.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModifyHabitActivity extends HabitBaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		long id = getIntent().getLongExtra("id", -1);
		project = getProjectManager().getProject(id);
		initialInfo();
	}

	private void initialInfo() {
		initialText(project.getName(), R.id.text_create_project_name);
		initialDate(project.getStartDate(), R.id.button_create_project_start_date);
		initialDate(project.getEndDate(), R.id.button_create_project_end_date);
		initialText(project.getMinitues(), R.id.text_create_project_minutues);
		initialText(project.getHours(), R.id.text_create_project_hours);
	}

	private void initialText(int data, int textCreateProjectHours) {
		initialText(String.valueOf(data), textCreateProjectHours);
	}

	private void initialText(String data, int id) {
		EditText text = getEditText(id);
		text.setText(data);
	}

	private void initialDate(HabitDate date, int buttonId) {
		Button dateButton = getButton(buttonId);
		dateButton.setText(date.toString());
	}

	@Override
	public void onSaveProject(View v) {
		if (isValidProject()) {
			getProjectManager().updateProject(project);
			finish();
		}
	}
	
}
