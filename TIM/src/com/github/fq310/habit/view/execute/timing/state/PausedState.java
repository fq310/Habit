package com.github.fq310.habit.view.execute.timing.state;

import com.github.fq310.habit.view.execute.timing.ExecuteHabitActivity;

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
		changeStartButtonTo(START_STATUS.PAUSE);
		currentHabit.setTimer_started(true);
		currentHabit.setTimer_paused(false);
		recordTimer.startTimer();
		activity.setCurrentState(activity.getStartState());
	}

	@Override
	public void onCreate() {
		long totalSeconds = currentHabit.getTimer_seconds();
		timeText.setTime(totalSeconds);
		changeStartButtonTo(START_STATUS.START);
	}

}
