package com.jasonfu19860310.tim.view.execute;

import com.jasonfu19860310.project.Project;
import com.jasonfu19860310.project.ProjectManager;
import com.jasonfu19860310.project.RecordManager;
import com.jasonfu19860310.tim.R;
import com.jasonfu19860310.tim.view.execute.state.IExecuteState;
import com.jasonfu19860310.tim.view.execute.state.PausedState;
import com.jasonfu19860310.tim.view.execute.state.StartState;
import com.jasonfu19860310.tim.view.execute.state.StopState;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ExecuteProjectActivity extends Activity {
	private Handler handler;
	protected TimeText timeText;
	protected RecordTimer recordTimer;
	private Project currentProject;
	private ProjectManager projectManager;
	private RecordManager recordManager;
	
	private IExecuteState startState;
	private IExecuteState stopState;
	private IExecuteState pauseState;
	private IExecuteState currentState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_execute_project);
		initialUtilityObject();
		initialState();
	}

	private void initialUtilityObject() {
		handler = new Handler();
		long projectID = getIntent().getLongExtra("id", -1);
		projectManager = new ProjectManager(this);
		recordManager = new RecordManager(this);
		currentProject = projectManager.getProject(projectID);
		TextView timeTextView = (TextView) findViewById(R.id.execute_project_textView_time);
		timeText = new TimeText(timeTextView);
		recordTimer = new RecordTimer(timeText, currentProject, handler);
	}

	private void initialState() {
		startState = new StartState(this);
		stopState = new StopState(this);
		pauseState = new PausedState(this);
		
		boolean projectPaused = currentProject .isTimer_paused();
		boolean projectStarted = currentProject.isTimer_started();
		if (projectPaused) {
			currentState = pauseState;
		} else if (projectStarted) {
			currentState = startState;
		} else {
			currentState = stopState;
		}
		currentState.onCreate();
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
		Log.i("change to state: ", currentState.toString());
	}
	
	public IExecuteState getCurrentState() {
		return currentState;
	}
	public Handler getHandler() {
		return handler;
	}

	public Project getProject() {
		return currentProject;
	}

	public ProjectManager getProjectManager() {
		return projectManager;
	}

	public RecordManager getRecordManager() {
		return recordManager;
	}
	
	public TimeText getTimeText() {
		return timeText;
	}

	public RecordTimer getRecordTimer() {
		return recordTimer;
	}
}
