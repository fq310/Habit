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
		changeStartButtonTo(START_STATUS.START);
		currentHabit.setTimer_started(false);
		currentHabit.setTimer_paused(true);
		currentHabit.setTimer_seconds(timeText.getTotalSeconds());
		activity.setCurrentState(activity.getPauseState());
	}

	@Override
	public void onCreate() {
		HabitDate timerDestroyDate = currentHabit.getTimerDestroyDate();
		HabitDate currentDate = new HabitDate();
		long totalSeconds = (currentDate.getTimeInMillis() - timerDestroyDate.getTimeInMillis()) / 1000 + currentHabit.getTimer_seconds();
		currentHabit.setTimer_seconds(totalSeconds);
		timeText.setTime(totalSeconds);
		changeStartButtonTo(START_STATUS.PAUSE);
		recordTimer.startTimer();
	}
	
	@Override
	public String toString() {
		return "start";
	}

}
