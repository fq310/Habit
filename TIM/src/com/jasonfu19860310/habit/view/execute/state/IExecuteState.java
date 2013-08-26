package com.jasonfu19860310.habit.view.execute.state;

public interface IExecuteState {
	public void start();
	public void clear();
	public void save();
	public void pause();
	public void input();
	public void exit();
	public void onCreate();
}
