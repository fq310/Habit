package com.jasonfu19860310.tim.view.execute.state;

import java.util.Calendar;

import android.widget.Button;
import com.jasonfu19860310.project.Project;
import com.jasonfu19860310.project.ProjectManager;
import com.jasonfu19860310.project.RecordManager;
import com.jasonfu19860310.tim.R;
import com.jasonfu19860310.tim.view.execute.ExecuteProjectActivity;
import com.jasonfu19860310.tim.view.execute.InputTimeDialog;
import com.jasonfu19860310.tim.view.execute.RecordTimer;
import com.jasonfu19860310.tim.view.execute.TimeText;
import com.jasonfu19860310.tim.view.execute.WarningDialog;

abstract public class ExecuteState implements IExecuteState{
	protected static final String PAUSE = "Pause";
	protected static final String START = "Start";
	protected ExecuteProjectActivity activity;
	private RecordManager recordManager;
	private ProjectManager projectManager;
	protected Project currentProject;
	
	private Button startButton;
	protected TimeText timeText;
	protected RecordTimer recordTimer;
	
	public ExecuteState(ExecuteProjectActivity activity) {
		this.activity = activity;
		startButton = (Button)activity.findViewById(R.id.button_execute_start);
		recordManager = activity.getRecordManager();
		projectManager = activity.getProjectManager();
		timeText = activity.getTimeText();
		recordTimer = activity.getRecordTimer();
		currentProject = activity.getProject();
	}
	
	protected void changeStartButtonTo(String status) {
		startButton.setText(status);
	}
	
	@Override
	public void clear() {
		recordTimer.cancelTimer();
		changeStartButtonTo(START);
		timeText.setTime(0);
		initialRecordStatus();
		activity.setCurrentState(activity.getStopState());
	}

	@Override
	public void save() {
		activity.setCurrentState(activity.getStopState());
		recordTimer.cancelTimer();
		if (timeText.isZeroTime()) {
			createWarningDialog(R.string.execute_error_msg_title, 
					R.string.execute_error_msg);
			return;
		}
		recordManager.addNewRecord(currentProject.getId(), timeText.getTotalSeconds());
		projectManager.updateProjectAfterSave(currentProject);
		changeStartButtonTo(START);
		initialRecordStatus();
		timeText.setTime(0);
		activity.setCurrentState(activity.getStopState());
		createWarningDialog(R.string.execute_error_msg_success, 
				R.string.execute_error_msg_ok);
	}
	
	private void initialRecordStatus() {
		currentProject.setTimer_seconds(0);
		currentProject.setTimer_started(false);
		currentProject.setTimer_paused(false);
	}
	
	private void createWarningDialog(int title, int message) {
		WarningDialog.open(title, message, activity);
	}
	

	@Override
	public void exit() {
		recordTimer.cancelTimer();
		currentProject.setTimer_seconds(timeText.getTotalSeconds());
		currentProject.setTimerDestroyDate(Calendar.getInstance());
		projectManager.updateProjectAfterExitActivity(currentProject);
	}
	
	@Override
	public void input() {
		recordTimer.cancelTimer();
		activity.setCurrentState(activity.getPauseState());
		changeStartButtonTo(START);
		InputTimeDialog input = new InputTimeDialog(timeText, activity);
		input.openDialog();
	}
	
	@Override
	public void pause() {
		recordTimer.cancelTimer();
		changeStartButtonTo(PAUSE);
		currentProject.setTimer_started(false);
		currentProject.setTimer_paused(true);
		activity.setCurrentState(activity.getPauseState());		
	}

}
