package com.github.fq310.habit.view.execute.timing.state;

import android.graphics.Color;
import android.widget.Button;
import android.widget.Toast;

import com.github.fq310.habit.R;
import com.github.fq310.habit.adt.HabitDate;
import com.github.fq310.habit.controller.TimingHabitManager;
import com.github.fq310.habit.model.TimingHabit;
import com.github.fq310.habit.view.execute.timing.ExecuteHabitActivity;
import com.github.fq310.habit.view.execute.timing.InputTimeDialog;
import com.github.fq310.habit.view.execute.timing.RecordTimer;
import com.github.fq310.habit.view.execute.timing.TimeText;
import com.github.fq310.habit.view.execute.timing.WarningDialog;

abstract public class ExecuteState implements IExecuteState{
	protected enum START_STATUS {
		PAUSE, START
	}
	protected ExecuteHabitActivity activity;
	private TimingHabitManager habitManager;
	protected TimingHabit currentHabit;
	
	private Button startButton;
	protected TimeText timeText;
	protected RecordTimer recordTimer;
	private int yellow = Color.parseColor("#FF9900");
	private int green = Color.parseColor("#339933");
	
	public ExecuteState(ExecuteHabitActivity activity) {
		this.activity = activity;
		startButton = (Button)activity.findViewById(R.id.button_execute_start);
		habitManager = activity.getProjectManager();
		timeText = activity.getTimeText();
		recordTimer = activity.getRecordTimer();
		currentHabit = activity.getProject();
		yellow = Color.parseColor(activity.getString(R.color.yellow));
		green = Color.parseColor(activity.getString(R.color.green));
	}
	
	protected void changeStartButtonTo(START_STATUS status) {
		startButton.setText(status.name());
		if (status == START_STATUS.PAUSE) 
			setStartButtonColor(yellow);
		if (status == START_STATUS.START) 
			setStartButtonColor(green);
	}
	
	@Override
	public void clear() {
		recordTimer.cancelTimer();
		changeStartButtonTo(START_STATUS.START);
		timeText.setTime(0);
		initialRecordStatus();
		activity.setCurrentState(activity.getStopState());
		setStartButtonColor(green);
	}

	@Override
	public void save() {
		recordTimer.cancelTimer();
		if (timeText.isZeroTime()) {
			createWarningDialog(R.string.execute_error_msg_title, 
					R.string.execute_error_msg);
			return;
		}
		habitManager.addNewRecord(currentHabit, timeText.getTotalSeconds());
		changeStartButtonTo(START_STATUS.START);
		initialRecordStatus();
		timeText.setTime(0);
		activity.setCurrentState(activity.getStopState());
		Toast.makeText(activity, R.string.execute_record_saved, Toast.LENGTH_SHORT).show();
	}
	
	private void initialRecordStatus() {
		currentHabit.setTimer_seconds(0);
		currentHabit.setTimer_started(false);
		currentHabit.setTimer_paused(false);
	}
	
	private void createWarningDialog(int title, int message) {
		WarningDialog.open(title, message, activity);
	}
	

	@Override
	public void exit() {
		recordTimer.cancelTimer();
		currentHabit.setTimer_seconds(timeText.getTotalSeconds());
		currentHabit.setTimerDestroyDate(new HabitDate());
		habitManager.updateProjectAfterExitActivity(currentHabit);
	}
	
	@Override
	public void input() {
		recordTimer.cancelTimer();
		currentHabit.setTimer_started(false);
		currentHabit.setTimer_paused(true);
		changeStartButtonTo(START_STATUS.START);
		InputTimeDialog input = new InputTimeDialog(timeText, activity);
		input.openDialog();
		activity.setCurrentState(activity.getPauseState());
	}
	
	private void setStartButtonColor(int color) {
		((Button)activity.findViewById(R.id.button_execute_start))
			.setBackgroundColor(color);
	}
	
}
