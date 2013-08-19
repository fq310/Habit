package com.jasonfu19860310.tim.view.execute.state;

import android.os.Handler;

import com.jasonfu19860310.tim.view.execute.ExecuteProjectActivity;

public class StopState extends ExecuteState {
	
	public StopState(ExecuteProjectActivity activity, Handler handler) {
		super(activity, handler);
	}

	@Override
	public void start() {
		activity.setCurrentState(activity.getStartState());
		activity.getCurrentState().start();
	}

	@Override
	public void onCreate() {
	}

}
