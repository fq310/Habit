package com.jasonfu19860310.tim.createproject;


import java.util.Calendar;

import com.jasonfu19860310.project.Project;
import com.jasonfu19860310.project.ProjectManager;
import com.jasonfu19860310.tim.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

public class CreateProjectActivity extends Activity {
	private Project newProject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		newProject = new Project();
		setContentView(R.layout.activity_create_project);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.create_project, menu);
		return true;
	}
	
	public void onDeleteAddProject(MenuItem i) {
		
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
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog dialog = new DatePickerDialog(this,
				dateListener,
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		dialog.show();
	}
	
	public void onSaveProject(View v) {
		if (saveProjectName() 
				&& saveStartDate()
				&& saveEndDate()
				&& saveTimePerDay()
				&& compareDates()
				&& saveWeekdays()
				) {
			ProjectManager.getInstance().saveNewProject(newProject);
			return;
		}
	}

	private boolean compareDates() {
		Calendar start = newProject.getStartDate();
		Calendar end = newProject.getEndDate();
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

	private boolean saveProjectName() {
		String projectName = getDateFromText(R.id.text_create_project_name);
		if (projectName == null) {
			showWarningMessage(R.string.warning, R.string.warning_input_name);
			return false;
		}
		newProject.setName(projectName);
		return true;
	}
	
	private boolean saveStartDate() {
		Calendar newDate = getDate(R.id.button_create_project_start_date);
		if (newDate != null) {
			newProject.setStartDate(newDate);
			return true;
		}
		showWarningMessage(R.string.warning, R.string.warning_input_startdate);
		return false;
	}
	
	private boolean saveEndDate() {
		Calendar newDate = getDate(R.id.button_create_project_end_date);
		if (newDate != null) {
			newProject.setEndDate(newDate);
			return true;
		}
		showWarningMessage(R.string.warning, R.string.warning_input_enddate);
		return false;
	}

	private Calendar getDate(int dateButton) {
		Button button = (Button) findViewById(dateButton);
		String date = button.getText().toString();
		if (date.matches("\\d+/\\d+/\\d+")) {
			String[] dates = date.split("/");
			Calendar newDate = Calendar.getInstance();
			newDate.set(Integer.valueOf(dates[0]), 
					Integer.valueOf(dates[1]), 
					Integer.valueOf(dates[2]));
			return newDate;
		} else {
			return null;
		}
	}
	
	private boolean saveWeekdays() {
		int[] weekDays = newProject.getWeekdays();
		LinearLayout weeks1 = (LinearLayout) findViewById(R.id.layout_create_project_week1);
		for (int i = 0; i < weeks1.getChildCount(); ++i) {
			CheckBox check = (CheckBox) weeks1.getChildAt(i);
			if (check.isChecked()) weekDays[i] = 1;
		}
		
		LinearLayout weeks2 = (LinearLayout) findViewById(R.id.layout_create_project_week2);
		for (int i = 0; i < weeks2.getChildCount(); ++i) {
			CheckBox check = (CheckBox) weeks2.getChildAt(i);
			if (check.isChecked()) weekDays[i + 3] = 1;
		}
		
		if (noneDaysSelected(weekDays)) {
			showWarningMessage(R.string.warning, R.string.warning_input_weekDays);
			return false;
		}
		return true;
	}

	private boolean noneDaysSelected(int[] weekDays) {
		int sum = 0;
		for (int i : weekDays) {
			sum += i;
		}
		if (sum == 0) return true;
		return false;
	}

	private boolean saveTimePerDay() {
		String hours = getDateFromText(R.id.text_create_project_hours);
		if (hours == null) {
			showWarningMessage(R.string.warning, R.string.warning_input_hours);
			return false;
		}
		newProject.setHours(Integer.valueOf(hours));
		String minutes = getDateFromText(R.id.text_create_project_minutues);
		if (minutes == null) {
			showWarningMessage(R.string.warning, R.string.warning_input_minutes);
			return false;
		}
		newProject.setMinitues(Integer.valueOf(minutes));
		return true;
	}

	private String getDateFromText(int editID) {
		EditText editor = (EditText) findViewById(editID);
		Editable text = editor.getText();
		if (text == null || text.length() == 0) {
			return null;
		}
		return text.toString();
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
		button.setText(year + "/" + month + "/" + day);
	}
	
}
