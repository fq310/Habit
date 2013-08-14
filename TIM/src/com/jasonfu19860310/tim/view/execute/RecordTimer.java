package com.jasonfu19860310.tim.view.execute;

import java.util.TimerTask;

import android.os.Handler;
import android.widget.TextView;

import com.jasonfu19860310.project.DateUtil;
import com.jasonfu19860310.project.Project;

public class RecordTimer extends TimerTask {
	private TextView currentTime;
	private Project project;
	private Handler handler;

	public RecordTimer(TextView currentTime, Project project, Handler handler) {
		super();
		this.currentTime = currentTime;
		this.project = project;
		this.handler = handler;
	}

	@Override
	public void run() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				String[] oldTime = DateUtil.getHourAndMinute(currentTime.getText().toString());
				int hours = Integer.valueOf(oldTime[0]);
				int minutes = Integer.valueOf(oldTime[1]);
				int seconds = Integer.valueOf(oldTime[2]);
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
				currentTime.setText(
					(hours < 10 ? ("0" + hours) : hours) + ":" + 
					(minutes < 10 ? ("0" + minutes) : minutes) + ":" + 
					(seconds < 10 ? ("0" + seconds) : seconds)
				);		
				project.setTimer_seconds(project.getTimer_seconds() + 1);
			}
		});
	}
}
