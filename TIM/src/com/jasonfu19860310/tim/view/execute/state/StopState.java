package com.jasonfu19860310.tim.view.execute.state;

import com.jasonfu19860310.tim.view.execute.ExecuteProjectActivity;
import com.jasonfu19860310.tim.view.execute.InputTimeDialog;

public class StopState extends ExecuteState {
	
	public StopState(ExecuteProjectActivity activity) {
		super(activity);
	}

	@Override
	public void start() {
		changeStartButtonTo(PAUSE);
		currentProject.setTimer_started(true);
		currentProject.setTimer_paused(false);
		currentProject.setTimer_seconds(timeText.getTotalSeconds());
		recordTimer.startTimer();
		activity.setCurrentState(activity.getStartState());
	}
	
	@Override
	public void input() {
		InputTimeDialog input = new InputTimeDialog(timeText, activity);
		input.openDialog();
	}

	@Override
	public void onCreate() {
	}
	
	@Override
	public String toString() {
		return "stop";
	}

}
