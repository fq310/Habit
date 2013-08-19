package com.jasonfu19860310.tim.view.execute.state;

import java.util.Calendar;

import android.os.Handler;

import com.jasonfu19860310.tim.view.execute.ExecuteProjectActivity;

public class PausedState extends ExecuteState {
	
	public PausedState(ExecuteProjectActivity activity, Handler handler) {
		super(activity, handler);
	}

	@Override
	public void start() {
		changeStartButtonTo(START);
		project.setTimer_started(true);
		project.setTimer_paused(false);
		project.setTimerStartDate(Calendar.getInstance());
		timerTask.startNewTimer();
	}

	@Override
	public void onCreate() {
		long totalSeconds = project.getTimer_seconds();
		timeText.setTime(totalSeconds);
		changeStartButtonTo(START);
	}

}
