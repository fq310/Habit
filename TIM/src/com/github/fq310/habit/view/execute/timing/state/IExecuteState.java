package com.github.fq310.habit.view.execute.timing.state;

public interface IExecuteState {
	public void start();
	public void clear();
	public void save();
	public void input();
	public void exit();
	public void onCreate();
}
