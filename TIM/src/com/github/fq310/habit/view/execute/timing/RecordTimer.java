package com.github.fq310.habit.view.execute.timing;

import java.util.Timer;
import java.util.TimerTask;

public class RecordTimer extends TimerTask {
	private Timer timer = new Timer();
	private RecordTimer timerTask;
	private ExecuteHabitActivity executeHabitActivity;

	public RecordTimer(ExecuteHabitActivity executeHabitActivity) {
		this.executeHabitActivity = executeHabitActivity;
	}

	@Override
	public void run() {
		executeHabitActivity.increaseOneSecond();
	}
	
	public void startTimer() {
		timerTask = new RecordTimer(executeHabitActivity);
		timer = new Timer();
		timer.schedule(timerTask, 500, 1000); 
	}
	
	public void cancelTimer() {
		timer.cancel();
	}
}
