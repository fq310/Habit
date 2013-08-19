package com.jasonfu19860310.tim.view.execute.state;

import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

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
	
	protected TimeText timeText;
	private long projectID;
	private RecordManager recordManager;
	private ProjectManager projectManager;
	protected Project project;
	
	protected RecordTimer timerTask;
	private Button startButton;
	
	public ExecuteState(ExecuteProjectActivity activity, Handler handler) {
		this.activity = activity;
		TextView timeTextView = (TextView) activity.findViewById(R.id.execute_project_textView_time);
		timeText = new TimeText(timeTextView);        
		startButton = (Button)activity.findViewById(R.id.button_execute_start);
		projectID = activity.getIntent().getLongExtra("id", -1);
		recordManager = new RecordManager(activity);
		projectManager = new ProjectManager(activity);
		project = projectManager.getProject(projectID);
		timerTask = new RecordTimer(timeText, project, handler);
	}
	
	protected void changeStartButtonTo(String status) {
		startButton.setText(status);
	}
	
	@Override
	public void clear() {
		timeText.setTime(R.string.inital_time);
		initialRecordStatus();
		activity.setCurrentState(activity.getStopState());
	}

	@Override
	public void save() {
		activity.setCurrentState(activity.getStopState());
		timerTask.cancel();
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
	
	private void initialRecordStatus() {
		project.setTimer_seconds(0);
		project.setTimer_started(false);
		project.setTimer_paused(false);
	}
	
	private void createWarningDialog(int title, int message) {
		WarningDialog.open(title, message, activity);
	}
	

	@Override
	public void destroy() {
		project.setTimer_seconds(timeText.getTotalSeconds());
		projectManager.updateProjectAfterExitActivity(project);
	}
	
	@Override
	public void input() {
		activity.getCurrentState().pause();
		activity.setCurrentState(activity.getPauseState());
		InputTimeDialog input = new InputTimeDialog(timeText, activity);
		input.openDialog();
	}
	
	@Override
	public void pause() {
		timerTask.cancel();
		changeStartButtonTo(PAUSE);
		project.setTimer_started(false);
		project.setTimer_paused(true);
		activity.setCurrentState(activity.getPauseState());		
	}

}
