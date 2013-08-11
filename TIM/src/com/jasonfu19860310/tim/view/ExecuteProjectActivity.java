package com.jasonfu19860310.tim.view;


import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.jasonfu19860310.project.DateUtil;
import com.jasonfu19860310.project.Project;
import com.jasonfu19860310.project.ProjectManager;
import com.jasonfu19860310.project.RecordManager;
import com.jasonfu19860310.tim.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ExecuteProjectActivity extends Activity {

	private static final String PAUSE = "Pause";
	private static final String START = "Start";
	private static final String COLON = ":";
	private TextView currentTime;
	private long projectID;
	private RecordManager recordManager;
	private ProjectManager projectManager;
	private Project project;
	private Timer timer = new Timer();
	private TimerTask timerTask;
	private Button startButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_execute_project);
		currentTime = (TextView) findViewById(R.id.execute_project_textView_time);
		startButton = (Button)findViewById(R.id.button_execute_start);
		projectID = getIntent().getLongExtra("id", -1);
		recordManager = new RecordManager(this);
		projectManager = new ProjectManager(this);
		project = projectManager.getProject(projectID);
		timerTask = new RecordTimer(currentTime, project);
		initialTimer();
	}

	private void initialTimer() {
		boolean projectPaused = project.isTimer_paused();
		if (projectPaused || project.getTimer_seconds() > 0) {
			changeStartButtonTo(START);
			long totalSeconds = project.getTimer_seconds();
			long tempHour = totalSeconds / 60*60;
			long tempMinute = (totalSeconds % 3600) / 60;
			long tempSecond = (totalSeconds % 3600) % 60;
			String hours = (String) (tempHour == 0 ? "00" : tempHour);
			String minutes = (String) (tempMinute == 0 ? "00" : tempMinute);
			String seconds = (String) (tempSecond == 0 ? "00" : tempSecond);
			currentTime.setText(hours + COLON + minutes + COLON + seconds);
		}
	}

	private void changeStartButtonTo(String status) {
		startButton.setText(status);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.execute_project, menu);
		return true;
	}
	
	public void onStartClicked(View view) {
		String startStatus = getStartButton();
		if (startStatus.equals(START)) {
			changeStartButtonTo(PAUSE);
			project.setTimer_started(true);
			project.setTimer_paused(false);
			project.setTimerStartDate(Calendar.getInstance());
			timer.schedule(timerTask, 0, 1000); 
		} else if (startStatus.equals(PAUSE)) {
			changeStartButtonTo(START);
			project.setTimer_started(false);
			project.setTimer_paused(true);
			project.setTimer_seconds(getCurrentSeconds());
			timer.cancel();
		}
	}
	
	private long getCurrentSeconds() {
		String timeString = currentTime.getText().toString();
		String[] time = timeString.split(COLON);
		int seconds = Integer.valueOf(time[0]) * 3600 +
			Integer.valueOf(time[1]) * 60 + 
			Integer.valueOf(time[0]);
		return seconds;
	}

	private String getStartButton() {
		return startButton.getText().toString();
	}

	public void onClearClicked(View view) {
		currentTime.setText(R.string.inital_time);
		project.setTimer_seconds(0);
		project.setTimer_started(false);
		project.setTimer_paused(false);
	}
	
	public void onSaveClicked(View view) {
		String[] time = DateUtil.getHourAndMinute(((TextView) currentTime).getText().toString());
		int hour = Integer.valueOf(time[0]);
		int minute = Integer.valueOf(time[1]);
		if (hour == 0 && minute == 0) {
			createWarningDialog(R.string.execute_error_msg_title, 
					R.string.execute_error_msg);
			return;
		}
		if (invalidMinute(minute)) {
			createWarningDialog(R.string.execute_error_msg_title, 
					R.string.execute_error_msg_minute);
			return;
		}

		projectManager.stopTimer();
		recordManager.addNewRecord(project.getId(), getCurrentSeconds());
		project.setTimer_seconds(0);
		project.setTimer_started(false);
		project.setTimer_paused(false);
		changeStartButtonTo(START);
		projectManager.updateProject(project);
		createWarningDialog(R.string.execute_error_msg_success, 
				R.string.execute_error_msg_ok);
	}

	private boolean invalidMinute(int minute) {
		return minute < 0 || minute > 60;
	}

	private void createWarningDialog(int title, int message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		       .setTitle(title);
		builder.setPositiveButton(R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void onInputManuallyClicked(View view) {
		AlertDialog.Builder builder = initialDialogBuilder();
		AlertDialog dialog = builder.create();
		dialog.show();
		addMinutesChangeListener(currentTime, dialog);
		addHoursChangeListener(currentTime, dialog);
		
	}

	private AlertDialog.Builder initialDialogBuilder() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.input_time)
			.setView(getLayoutInflater().inflate(R.layout.input_time, null));
		final String oldTime = currentTime.getText().toString();
		addButtonClickListener(builder, currentTime, oldTime);
		return builder;
	}

	private void addButtonClickListener(AlertDialog.Builder builder,
			final TextView currentTime, final String oldTime) {
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	           }
	       });
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   currentTime.setText(oldTime);
	           }
	       });
	}

	private void addHoursChangeListener(final TextView currentTime,
			AlertDialog dialog) {
		final EditText hours = (EditText) dialog.findViewById(R.id.input_time_editText_hour);
		hours.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String[] times = DateUtil.getHourAndMinute(currentTime.getText().toString());
				currentTime.setText(hours.getText().toString().trim() + COLON + times[1]);
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
			}});
	}

	private void addMinutesChangeListener(final TextView currentTime,
			AlertDialog dialog) {
		final EditText minutes = 
				(EditText) dialog.findViewById(R.id.input_time_editText_minitue);
		minutes.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String[] times = DateUtil.getHourAndMinute(currentTime.getText().toString());
				currentTime.setText(times[0] + COLON + 
						minutes.getText().toString().trim());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
			}});
	}
	
	@Override
	protected void onDestroy() {
		project.setTimer_seconds(getCurrentSeconds());
		projectManager.updateProject(project);
		super.onDestroy();
	}

}

class RecordTimer extends TimerTask {
	private TextView currentTime;
	private Project project;

	public RecordTimer(TextView currentTime, Project project) {
		super();
		this.currentTime = currentTime;
		this.project = project;
	}

	@Override
	public void run() {
		String[] oldTime = DateUtil.getHourAndMinute(currentTime.getText().toString());
		int hour = Integer.valueOf(oldTime[0]);
		int minute = Integer.valueOf(oldTime[1]);
		if (0 <= minute && minute < 59) {
			++minute;
		} else if (minute == 59){
			minute = 0;
			++hour;
		}
		currentTime.setText(hour + ":" + minute);
		project.setTimer_seconds(project.getTimer_seconds() + 1);
	}
	
}
