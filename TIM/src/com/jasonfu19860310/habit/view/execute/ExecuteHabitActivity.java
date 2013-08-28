package com.jasonfu19860310.habit.view.execute;

import com.jasonfu19860310.habit.adt.HabitDate;
import com.jasonfu19860310.habit.controller.HabitManager;
import com.jasonfu19860310.habit.controller.RecordManager;
import com.jasonfu19860310.habit.model.Habit;
import com.jasonfu19860310.habit.view.ModifyHabitActivity;
import com.jasonfu19860310.habit.view.execute.state.IExecuteState;
import com.jasonfu19860310.habit.view.execute.state.PausedState;
import com.jasonfu19860310.habit.view.execute.state.StartState;
import com.jasonfu19860310.habit.view.execute.state.StopState;
import com.jasonfu19860310.tim.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExecuteHabitActivity extends Activity {

	private Handler handler;
	protected TimeText timeText;
	protected RecordTimer recordTimer;
	private Habit currentProject;
	private HabitManager projectManager;
	private RecordManager recordManager;
	
	private IExecuteState startState;
	private IExecuteState stopState;
	private IExecuteState pauseState;
	private IExecuteState currentState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_execute_habit);
		initialUtilityObject();
		initialActionBar();
		initialProgressBar();
		initialState();
	}

	private void initialActionBar() {
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle(currentProject.getName());
	}
	
	private void initialUtilityObject() {
		handler = new Handler();
		long projectID = getIntent().getLongExtra("id", -1);
		projectManager = new HabitManager(this);
		recordManager = new RecordManager(this);
		currentProject = projectManager.getProject(projectID);
		TextView timeTextView = (TextView) findViewById(R.id.execute_project_textView_time);
		timeText = new TimeText(timeTextView);
		recordTimer = new RecordTimer(timeText, currentProject, handler);
	}

	private void initialProgressBar() {
		ProgressBar finishedTimeBar = (ProgressBar) findViewById(R.id.execute_project_progressBar1);
		float rate = (float)currentProject.getTotalFinishedSeconds()/currentProject.getTotalSeconds() * 100;
		finishedTimeBar.setMax(100);
		finishedTimeBar.setProgress((int)rate);
		
		TextView finishedTimeText = (TextView) findViewById(R.id.execute_project_text1);
		finishedTimeText.setText(R.string.Total_Finished_Time);
		long totalSeconds = currentProject.getTotalFinishedSeconds();
		finishedTimeText.append(" [" + TimeText.getTimeStringFromSeconds(totalSeconds) + "]");
		
		ProgressBar passedDayBar = (ProgressBar) findViewById(R.id.execute_project_progressBar2);
		HabitDate startDate = currentProject.getStartDate();
		HabitDate endDate = currentProject.getEndDate();
		passedDayBar.setMax(startDate.daysFrom(endDate));
		int passedDays = startDate.daysFrom(new HabitDate());
		passedDayBar.setProgress(passedDays);
		
		TextView passedDayText = (TextView) findViewById(R.id.execute_project_text2);
		passedDayText.setText(startDate.toString() + " - " + endDate.toString());
		passedDayText.append(" [ Day - " + passedDays + 1 +"]");
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
		getMenuInflater().inflate(R.menu.modify_habit, menu);
		return true;
	}
	
	public void onModifyProject(MenuItem i) {
		currentState.pause();
		Intent intent = new Intent(this, ModifyHabitActivity.class);
		intent.putExtra("id", currentProject.getId());
		this.startActivity(intent);
	}
	
	public void onDeleteProject(MenuItem i) {
		currentState.pause();
		WarningDialog.OKDialogWithListener(R.string.execute_warning, 
			R.string.execute_warning_msg, this, 
			new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int button) {
					projectManager.deleteProject(currentProject);
					finish();
				}
		});
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
		initialProgressBar();
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

	public Habit getProject() {
		return currentProject;
	}

	public HabitManager getProjectManager() {
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
	 * When the App is closed by unexpected user actions, we can restore 
	 * the state.
	 */
	public void saveCurrentState() {
		currentProject.setTimer_seconds(timeText.getTotalSeconds());
		currentProject.setTimerDestroyDate(new HabitDate());
		projectManager.updateProjectAfterExitActivity(currentProject);
	}
	
}
