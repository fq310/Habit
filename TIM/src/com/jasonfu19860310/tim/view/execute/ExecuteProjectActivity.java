package com.jasonfu19860310.tim.view.execute;


import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.jasonfu19860310.project.DateUtil;
import com.jasonfu19860310.project.Project;
import com.jasonfu19860310.project.ProjectManager;
import com.jasonfu19860310.project.RecordManager;
import com.jasonfu19860310.tim.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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
	private Handler handler;  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_execute_project);
		handler = new Handler();
		currentTime = (TextView) findViewById(R.id.execute_project_textView_time);
		startButton = (Button)findViewById(R.id.button_execute_start);
		projectID = getIntent().getLongExtra("id", -1);
		recordManager = new RecordManager(this);
		projectManager = new ProjectManager(this);
		project = projectManager.getProject(projectID);
		timerTask = new RecordTimer(currentTime, project, handler);
		initialTimer();
	}

	private void initialTimer() {
		boolean projectPaused = project.isTimer_paused();
		boolean projectStarted = project.isTimer_started();
		if (projectPaused) {
			long totalSeconds = project.getTimer_seconds();
			updateTimeLabel(totalSeconds);
			changeStartButtonTo(START);
		}
		if (projectStarted) {
			Calendar startDate = project.getTimerStartDate();
			Calendar currentDate = Calendar.getInstance();
			long totalTime = currentDate.getTimeInMillis() - startDate.getTimeInMillis();
			project.setTimer_seconds(totalTime);
			updateTimeLabel(totalTime);
			changeStartButtonTo(PAUSE);
			timer.schedule(timerTask, 500, 1000); 
		}
	}

	private void updateTimeLabel(long totalSeconds) {
		String hours = stringOf(totalSeconds / 60*60);
		String minutes = stringOf((totalSeconds % 3600) / 60);
		String seconds = stringOf((totalSeconds % 3600) % 60);
		currentTime.setText(hours + COLON + minutes + COLON + seconds);
	}

	private String stringOf(long time) {
		String result = Long.toString(time);
		if (result.length() < 2) return "0" + result;
		return result;
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
			timer = new Timer();
			timerTask = new RecordTimer(currentTime, project, handler);
			timer.schedule(timerTask, 500, 1000); 
		} else if (startStatus.equals(PAUSE)) {
			timer.cancel();
			changeStartButtonTo(START);
			project.setTimer_started(false);
			project.setTimer_paused(true);
			project.setTimer_seconds(getSecondsFromTextView());
		}
	}
	
	private long getSecondsFromTextView() {
		String timeString = currentTime.getText().toString();
		String[] time = timeString.split(COLON);
		int seconds = Integer.valueOf(time[0]) * 3600 +
			Integer.valueOf(time[1]) * 60 + 
			Integer.valueOf(time[2]);
		return seconds;
	}

	private String getStartButton() {
		return startButton.getText().toString();
	}

	public void onClearClicked(View view) {
		currentTime.setText(R.string.inital_time);
		initialRecordStatus();
	}

	private void initialRecordStatus() {
		project.setTimer_seconds(0);
		project.setTimer_started(false);
		project.setTimer_paused(false);
	}
	
	public void onSaveClicked(View view) {
		timer.cancel();
		String[] time = DateUtil.getHourAndMinute(((TextView) currentTime).getText().toString());
		int hour = Integer.valueOf(time[0]);
		int minute = Integer.valueOf(time[1]);
		int seconds = Integer.valueOf(time[2]);
		if (hour == 0 && minute == 0 && seconds == 0) {
			createWarningDialog(R.string.execute_error_msg_title, 
					R.string.execute_error_msg);
			return;
		}
		recordManager.addNewRecord(project, getSecondsFromTextView());
		projectManager.updateProject(project);
		changeStartButtonTo(START);
		initialRecordStatus();
		updateTimeLabel(0);
		createWarningDialog(R.string.execute_error_msg_success, 
				R.string.execute_error_msg_ok);
	}

	private void createWarningDialog(int title, int message) {
		WarningDialog.open(title, message, this);
	}
	
	public void onInputManuallyClicked(View view) {
		InputTimeDialog input = new InputTimeDialog(currentTime, this);
		input.openDialog();
	}

	@Override
	protected void onDestroy() {
		project.setTimer_seconds(getSecondsFromTextView());
		projectManager.updateProject(project);
		super.onDestroy();
	}

}
