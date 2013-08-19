package com.jasonfu19860310.tim.view.execute;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import com.jasonfu19860310.project.Project;

public class RecordTimer extends TimerTask {
	private TimeText timeText;
	private Project project;
	private Handler handler;
	private Timer timer = new Timer();
	private RecordTimer timerTask;

	public RecordTimer(TimeText timeText, Project project, Handler handler) {
		super();
		this.timeText = timeText;
		this.project = project;
		this.handler = handler;
	}

	@Override
	public void run() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				int hours = timeText.getIntHour();
				int minutes = timeText.getIntMinute();
				int seconds = timeText.getIntSecond();
				if (0 <= seconds && seconds < 59) {
					++ seconds;
				} else if (seconds == 59) {
					seconds = 0;
					++ minutes;
				}
				if (minutes == 59){
					minutes = 0;
					++hours;
				}
				timeText.setTime(hours, minutes, seconds);
				project.setTimer_seconds(project.getTimer_seconds() + 1);
			}
		});
	}
	
	public void startNewTimer() {
		timerTask = new RecordTimer(timeText, project, handler);
		timer.schedule(timerTask, 500, 1000); 
	}
}
