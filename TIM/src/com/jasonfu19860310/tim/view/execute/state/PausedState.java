package com.jasonfu19860310.tim.view.execute.state;

import com.jasonfu19860310.tim.view.execute.ExecuteProjectActivity;

public class PausedState extends ExecuteState {
	
	@Override
	public String toString() {
		return "pause";
	}

	public PausedState(ExecuteProjectActivity activity) {
		super(activity);
	}

	@Override
	public void start() {
		changeStartButtonTo(PAUSE);
		currentProject.setTimer_started(true);
		currentProject.setTimer_paused(false);
		recordTimer.startTimer();
		activity.setCurrentState(activity.getStartState());
	}

	@Override
	public void onCreate() {
		long totalSeconds = currentProject.getTimer_seconds();
		timeText.setTime(totalSeconds);
		changeStartButtonTo(START);
	}

}
