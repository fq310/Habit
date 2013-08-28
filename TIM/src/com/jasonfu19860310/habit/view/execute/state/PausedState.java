package com.jasonfu19860310.habit.view.execute.state;

import com.jasonfu19860310.habit.view.execute.ExecuteHabitActivity;

public class PausedState extends ExecuteState {
	
	@Override
	public String toString() {
		return "pause";
	}

	public PausedState(ExecuteHabitActivity activity) {
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
