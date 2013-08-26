package com.jasonfu19860310.habit.view.execute;

import java.util.Calendar;

import com.jasonfu19860310.habit.controller.ProjectManager;
import com.jasonfu19860310.habit.controller.RecordManager;
import com.jasonfu19860310.habit.model.DateUtil;
import com.jasonfu19860310.habit.model.Project;
import com.jasonfu19860310.habit.view.ModifyProjectActivity;
import com.jasonfu19860310.habit.view.execute.state.IExecuteState;
import com.jasonfu19860310.habit.view.execute.state.PausedState;
import com.jasonfu19860310.habit.view.execute.state.StartState;
import com.jasonfu19860310.habit.view.execute.state.StopState;
import com.jasonfu19860310.tim.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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
		getActionBar().setDisplayShowHomeEnabled(false);
		initialUtilityObject();
		initialProgressBar();
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

	private void initialProgressBar() {
		ProgressBar finishedTime = (ProgressBar) findViewById(R.id.execute_project_progressBar1);
		finishedTime.setMax((int) (currentProject.getTotalSeconds()/60));
		finishedTime.setProgress((int) (currentProject.getTotalFinishedSeconds()/60));
		
		ProgressBar passedDays = (ProgressBar) findViewById(R.id.execute_project_progressBar2);
		passedDays.setMax(DateUtil.getDaysBwtween(currentProject.getStartDate(), currentProject.getEndDate()) + 1);
		passedDays.setProgress(currentProject.getTotalPassedDays());
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
		getMenuInflater().inflate(R.menu.modify_project, menu);
		return true;
	}
	
	public void onModifyProject(MenuItem i) {
		Intent intent = new Intent(this, ModifyProjectActivity.class);
		intent.putExtra("id", currentProject.getId());
		this.startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
		currentState.exit();
		finish();
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

	public IExecuteState getStartState() {
		return startState;
	}

	public IExecuteState getStopState() {
		return stopState;
	}

	public IExecuteState getPauseState() {
		return pauseState;
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
	
	public void setCurrentState(IExecuteState currentState) {
		this.currentState = currentState;
		saveCurrentState();
		Log.i("change to state: ", currentState.toString());
	}

	/*
	 * After changing the state, the current state will be saved into the DB.
	 * In case when the App is closed by unexpected user actions, we can restore 
	 * the state.
	 * 
	 */
	public void saveCurrentState() {
		currentProject.setTimer_seconds(timeText.getTotalSeconds());
		currentProject.setTimerDestroyDate(Calendar.getInstance());
		projectManager.updateProjectAfterExitActivity(currentProject);
	}
	
}
