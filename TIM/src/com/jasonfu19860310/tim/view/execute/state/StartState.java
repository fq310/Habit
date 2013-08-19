package com.jasonfu19860310.tim.view.execute.state;

import java.util.Calendar;

import android.os.Handler;

import com.jasonfu19860310.tim.view.execute.ExecuteProjectActivity;

public class StartState extends ExecuteState {

	public StartState(ExecuteProjectActivity activity, Handler handler) {
		super(activity, handler);
	}

	@Override
	public void start() {
		timerTask.cancel();
		changeStartButtonTo(PAUSE);
		project.setTimer_started(false);
		project.setTimer_paused(true);
		project.setTimer_seconds(timeText.getTotalSeconds());
	}

	@Override
	public void onCreate() {
		Calendar startDate = project.getTimerStartDate();
		Calendar currentDate = Calendar.getInstance();
		long totalSeconds = currentDate.getTimeInMillis() - startDate.getTimeInMillis();
		project.setTimer_seconds(totalSeconds);
		timeText.setTime(totalSeconds);
		changeStartButtonTo(PAUSE);
		timerTask.startNewTimer();
	}

}
