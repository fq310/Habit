package com.jasonfu19860310.tim.view.execute.state;

import java.util.Calendar;
import com.jasonfu19860310.tim.view.execute.ExecuteProjectActivity;

public class StartState extends ExecuteState {

	public StartState(ExecuteProjectActivity activity) {
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
		Calendar timerDestroyDate = currentProject.getTimerDestroyDate();
		Calendar currentDate = Calendar.getInstance();
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
