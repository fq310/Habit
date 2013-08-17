package com.jasonfu19860310.tim.view.execute;


import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

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
	private TimeText timeText;
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
		TextView timeView = (TextView) findViewById(R.id.execute_project_textView_time);
		timeText = new TimeText(timeView);
		startButton = (Button)findViewById(R.id.button_execute_start);
		projectID = getIntent().getLongExtra("id", -1);
		recordManager = new RecordManager(this);
		projectManager = new ProjectManager(this);
		project = projectManager.getProject(projectID);
		timerTask = new RecordTimer(timeText, project, handler);
		initialTimer();
	}

	private void initialTimer() {
		boolean projectPaused = project.isTimer_paused();
		boolean projectStarted = project.isTimer_started();
		if (projectPaused) {
			long totalSeconds = project.getTimer_seconds();
			timeText.setTime(totalSeconds);
			changeStartButtonTo(START);
		}
		if (projectStarted) {
			Calendar startDate = project.getTimerStartDate();
			Calendar currentDate = Calendar.getInstance();
			long totalSeconds = currentDate.getTimeInMillis() - startDate.getTimeInMillis();
			project.setTimer_seconds(totalSeconds);
			timeText.setTime(totalSeconds);
			changeStartButtonTo(PAUSE);
			timer.schedule(timerTask, 500, 1000); 
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
			timer = new Timer();
			timerTask = new RecordTimer(timeText, project, handler);
			timer.schedule(timerTask, 500, 1000); 
		} else if (startStatus.equals(PAUSE)) {
			timer.cancel();
			changeStartButtonTo(START);
			project.setTimer_started(false);
			project.setTimer_paused(true);
			project.setTimer_seconds(timeText.getTotalSeconds());
		}
	}
	
	private String getStartButton() {
		return startButton.getText().toString();
	}

	public void onClearClicked(View view) {
		timeText.setTime(R.string.inital_time);
		initialRecordStatus();
	}

	private void initialRecordStatus() {
		project.setTimer_seconds(0);
		project.setTimer_started(false);
		project.setTimer_paused(false);
	}
	
	public void onSaveClicked(View view) {
		timer.cancel();
		if (timeText.isZeroTime()) {
			createWarningDialog(R.string.execute_error_msg_title, 
					R.string.execute_error_msg);
			return;
		}
		recordManager.addNewRecord(project.getId(), timeText.getTotalSeconds());
		projectManager.updateProjectAfterSave(project);
		changeStartButtonTo(START);
		initialRecordStatus();
		timeText.setTime(0);
		createWarningDialog(R.string.execute_error_msg_success, 
				R.string.execute_error_msg_ok);
	}

	private void createWarningDialog(int title, int message) {
		WarningDialog.open(title, message, this);
	}
	
	public void onInputManuallyClicked(View view) {
		InputTimeDialog input = new InputTimeDialog(timeText, this);
		input.openDialog();
	}

	@Override
	protected void onDestroy() {
		project.setTimer_seconds(timeText.getTotalSeconds());
		projectManager.updateProjectAfterExitActivity(project);
		super.onDestroy();
	}

}
