package com.jasonfu19860310.habit.view.execute.state;

import android.widget.Button;

import com.jasonfu19860310.habit.adt.HabitDate;
import com.jasonfu19860310.habit.controller.HabitManager;
import com.jasonfu19860310.habit.controller.RecordManager;
import com.jasonfu19860310.habit.model.Habit;
import com.jasonfu19860310.habit.view.execute.ExecuteHabitActivity;
import com.jasonfu19860310.habit.view.execute.InputTimeDialog;
import com.jasonfu19860310.habit.view.execute.RecordTimer;
import com.jasonfu19860310.habit.view.execute.TimeText;
import com.jasonfu19860310.habit.view.execute.WarningDialog;
import com.jasonfu19860310.tim.R;

abstract public class ExecuteState implements IExecuteState{
	protected static final String PAUSE = "Pause";
	protected static final String START = "Start";
	protected ExecuteHabitActivity activity;
	private RecordManager recordManager;
	private HabitManager projectManager;
	protected Habit currentProject;
	
	private Button startButton;
	protected TimeText timeText;
	protected RecordTimer recordTimer;
	
	public ExecuteState(ExecuteHabitActivity activity) {
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
		currentProject.setTimerDestroyDate(new HabitDate());
		projectManager.updateProjectAfterExitActivity(currentProject);
	}
	
	@Override
	public void input() {
		recordTimer.cancelTimer();
		currentProject.setTimer_started(false);
		currentProject.setTimer_paused(true);
		changeStartButtonTo(START);
		InputTimeDialog input = new InputTimeDialog(timeText, activity);
		input.openDialog();
		activity.setCurrentState(activity.getPauseState());
	}
	
}
