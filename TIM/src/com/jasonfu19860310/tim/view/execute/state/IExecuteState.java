package com.jasonfu19860310.tim.view.execute.state;

public interface IExecuteState {
	public void start();
	public void clear();
	public void stop();
	public void save();
	public void pause();
	public void input();
	public void destroy();
	public void onCreate();
}
