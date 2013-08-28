package com.jasonfu19860310.habit.view.execute.state;

import com.jasonfu19860310.habit.adt.HabitDate;
import com.jasonfu19860310.habit.view.execute.ExecuteHabitActivity;

public class StartState extends ExecuteState {

	public StartState(ExecuteHabitActivity activity) {
		super(activity);
	}

	@Override
	public void start() {
		recordTimer.cancelTimer();
		changeStartButtonTo(START);
		currentProject.setTimer_started(false);
		currentProject.setTimer_paused(true);
		currentProject.setTimer_seconds(timeText.getTotalSeconds());
		activity.setCurrentState(activity.getPauseState());
	}

	@Override
	public void onCreate() {
		HabitDate timerDestroyDate = currentProject.getTimerDestroyDate();
		HabitDate currentDate = new HabitDate();
		long totalSeconds = (currentDate.getTimeInMillis() - timerDestroyDate.getTimeInMillis()) / 1000 + currentProject.getTimer_seconds();
		currentProject.setTimer_seconds(totalSeconds);
		timeText.setTime(totalSeconds);
		changeStartButtonTo(PAUSE);
		recordTimer.startTimer();
	}
	
	@Override
	public String toString() {
		return "start";
	}

}
