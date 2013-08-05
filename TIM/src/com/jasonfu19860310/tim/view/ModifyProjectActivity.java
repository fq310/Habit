package com.jasonfu19860310.tim.view;

import java.util.Calendar;

import com.jasonfu19860310.project.ProjectManager;
import com.jasonfu19860310.tim.R;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ModifyProjectActivity extends ProjectInfo {

	public ModifyProjectActivity() {
		super();
		int id = getIntent().getIntExtra("id", 0);
		project = ProjectManager.getInstance().getProject(id);
		initialInfo();
	}

	private void initialInfo() {
		initialText(project.getName(), R.id.text_create_project_hours);
		initialDate(project.getStartDate(), R.id.button_create_project_start_date);
		initialDate(project.getEndDate(), R.id.button_create_project_end_date);
		initialText(project.getMinitues(), R.id.text_create_project_minutues);
		initialText(project.getHours(), R.id.text_create_project_hours);
		initialWeekdays();
	}

	private void initialWeekdays() {
		int[] days = project.getWeekdays();
		setCheckBox(days[0], R.id.checkBox_create_project1);
		setCheckBox(days[1], R.id.checkBox_create_project2);
		setCheckBox(days[2], R.id.checkBox_create_project3);
		setCheckBox(days[3], R.id.checkBox_create_project4);
		setCheckBox(days[4], R.id.checkBox_create_project5);
		setCheckBox(days[5], R.id.checkBox_create_project6);
		setCheckBox(days[6], R.id.checkBox_create_project7);
	}

	private void setCheckBox(int data, int boxID) {
		CheckBox box = getCheckBox(boxID);
		box.setChecked(data == 1);
	}
	

	private CheckBox getCheckBox(int id) {
		return (CheckBox) findViewById(id);
	}

	private void initialText(int data, int textCreateProjectHours) {
		initialText(String.valueOf(data), textCreateProjectHours);
	}

	private void initialText(String data, int id) {
		EditText text = getEditText(id);
		text.setText(data);
	}

	private void initialDate(Calendar date, int buttonId) {
		Button dateButton = getButton(buttonId);
		dateButton.setText(date.get(Calendar.YEAR) + "/" + 
							date.get(Calendar.MONTH) + "/" +
							date.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public void onSaveProject(View v) {
		if (isValidProject()) {
			ProjectManager.getInstance().updateProject(project);
			finish();
		}
	}

}
