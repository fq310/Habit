package com.jasonfu19860310.habit.view;


import com.jasonfu19860310.habit.adt.HabitDate;
import com.jasonfu19860310.habit.controller.TimingHabitManager;
import com.jasonfu19860310.habit.helper.ColorHelper;
import com.jasonfu19860310.habit.model.TimingHabit;
import com.jasonfu19860310.tim.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Base class for create and modify activity
 * @author qiangf
 *
 */
public abstract class HabitBaseActivity extends Activity {
	public static final String CREATE = "create";
	public static final String MODIFY = "modify";
	public static final String OPERATION = "operation";
	protected TimingHabit project;
	private TimingHabitManager projectManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_habit_info);
		getActionBar().setDisplayShowHomeEnabled(false);
		projectManager = new TimingHabitManager(this);
		initialButton();
	}

	private void initialButton() {
		((Button)findViewById(R.id.button_create_project_done))
			.setBackgroundColor(ColorHelper.color_green);
		((Button)findViewById(R.id.button_create_project_cancel))
			.setBackgroundColor(ColorHelper.color_blue);
		
		((Button)findViewById(R.id.button_create_project_start_date))
			.setBackgroundColor(ColorHelper.color_blue);
		((Button)findViewById(R.id.button_create_project_end_date))
			.setBackgroundColor(ColorHelper.color_blue);
	}

	public void onSetStartDate(View v) {
		showDatePickerDialog(R.id.button_create_project_start_date);
	}
	
	public void onSetEndDate(View v) {
		showDatePickerDialog(R.id.button_create_project_end_date);
	}

	private void showDatePickerDialog(int button2update) {
		DateSetListener dateListener = 
				new DateSetListener(this, button2update);
		HabitDate date = new HabitDate();
		DatePickerDialog dialog = new DatePickerDialog(this,
				dateListener,
				date.getYear(),
				date.getMonth(),
				date.getDayOfMonth());
		dialog.show();
	}
	
	public abstract void onSaveProject(View v);
	public void onCancelProject(View v) {
		this.finish();
	}

	protected boolean isValidProject() {
		return verifyProjectName() 
				&& verifyStartDate()
				&& verifyEndDate()
				&& verifyTimePerDay() 
				&& verifyDates();
	}

	private boolean verifyDates() {
		HabitDate start = project.getStartDate();
		HabitDate end = project.getEndDate();
		if (start.after(end)) {
			showWarningMessage(R.string.warning, R.string.warning_input_dateCompare);
			return false;
		}
		return true;
	}

	private void showWarningMessage(int title, int message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		       .setTitle(title);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		       public void onClick(DialogInterface dialog, int id) {
		       }
		   });
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private boolean verifyProjectName() {
		String projectName = getDateFromText(R.id.text_create_project_name);
		if (projectName == null) {
			showWarningMessage(R.string.warning, R.string.warning_input_name);
			return false;
		}
		project.setName(projectName);
		return true;
	}
	
	private boolean verifyStartDate() {
		String dateText = getDateText(R.id.button_create_project_start_date);
		if (HabitDate.isValidTimeString(dateText)) {
			project.setStartDate(new HabitDate(dateText));
			return true;
		}
		showWarningMessage(R.string.warning, R.string.warning_input_startdate);
		return false;
	}
	
	private boolean verifyEndDate() {
		String dateText = getDateText(R.id.button_create_project_end_date);
		if (HabitDate.isValidTimeString(dateText)) {
			project.setEndDate(new HabitDate(dateText));
			return true;
		}
		showWarningMessage(R.string.warning, R.string.warning_input_enddate);
		return false;
	}

	private String getDateText(int dateButton) {
		Button button = getButton(dateButton);
		String date = button.getText().toString();
		return date;
	}
	
	protected Button getButton(int id) {
		return  (Button) findViewById(id);
	}
	
	protected EditText getEditText(int id) {
		return  (EditText) findViewById(id);
	}
	
	private boolean verifyTimePerDay() {
		String hours = getDateFromText(R.id.text_create_project_hours);
		String minutes = getDateFromText(R.id.text_create_project_minutues);
		if (hours == null && minutes == null) {
			showWarningMessage(R.string.warning, R.string.warning_input_hours);
			return false;
		}
		if (hours != null) {
			project.setHours(Integer.valueOf(hours)); } 
		else {
			project.setHours(0); }
		if (minutes != null) {
			project.setMinitues(Integer.valueOf(minutes)); } 
		else {
			project.setMinitues(0); }
		return true;
	}

	private String getDateFromText(int editID) {
		EditText editor = getEditText(editID);
		Editable text = editor.getText();
		if (text == null || text.length() == 0) {
			return null;
		}
		return text.toString();
	}
	
	protected TimingHabitManager getProjectManager() {
		return projectManager;
	}
	
}

class DateSetListener implements OnDateSetListener {
	public DateSetListener(Activity activity, int buttonId) {
		this.activity = activity;
		this.buttonId = buttonId;
	}

	private Activity activity;
	private int buttonId;

	@Override
	public void onDateSet(DatePicker arg0, int year, int month, int day) {
		Button button = (Button) activity.findViewById(buttonId);
		button.setText(year + "/" + (month+1) + "/" + day);
	}

}
