package com.jasonfu19860310.tim.view.execute;

import com.jasonfu19860310.project.Project;
import com.jasonfu19860310.project.ProjectManager;
import com.jasonfu19860310.tim.R;
import com.jasonfu19860310.tim.view.execute.state.IExecuteState;
import com.jasonfu19860310.tim.view.execute.state.PausedState;
import com.jasonfu19860310.tim.view.execute.state.StartState;
import com.jasonfu19860310.tim.view.execute.state.StopState;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class ExecuteProjectActivity extends Activity {
	private Handler handler;
	private IExecuteState startState;
	private IExecuteState stopState;
	private IExecuteState pauseState;
	private IExecuteState currentState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_execute_project);
		handler = new Handler();
		startState = new StartState(this, handler);
		stopState = new StopState(this, handler);
		pauseState = new PausedState(this, handler);
		initialState();
	}

	private void initialState() {
		Project project = getCurrentProject();
		boolean projectPaused = project .isTimer_paused();
		boolean projectStarted = project.isTimer_started();
		if (projectPaused) {
			currentState = pauseState;
		} else if (projectStarted) {
			currentState = startState;
		} else {
			currentState = stopState;
		}
		currentState.onCreate();
	}

	private Project getCurrentProject() {
		long projectID = getIntent().getLongExtra("id", -1);
		ProjectManager projectManager = new ProjectManager(this);
		return projectManager.getProject(projectID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.execute_project, menu);
		return true;
	}
	
	public void onStartClicked(View view) {
		currentState.start();
	}
	
	public void onClearClicked(View view) {
		currentState.clear();
	}

	public void onSaveClicked(View view) {
		currentState.save();
	}
	
	public void onInputManuallyClicked(View view) {
		currentState.input();
	}

	@Override
	protected void onDestroy() {
		currentState.destroy();
		super.onDestroy();
	}
	
	public IExecuteState getStartState() {
		return startState;
	}

	public IExecuteState getStopState() {
		return stopState;
	}

	public IExecuteState getPauseState() {
		return pauseState;
	}

	public void setCurrentState(IExecuteState currentState) {
		this.currentState = currentState;
	}
	
	public IExecuteState getCurrentState() {
		return currentState;
	}
}
