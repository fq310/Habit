package com.github.fq310.habit.view.execute.timing;

import com.github.fq310.habit.R;
import com.github.fq310.habit.adt.HabitDate;
import com.github.fq310.habit.controller.TimingHabitManager;
import com.github.fq310.habit.model.TimingHabit;
import com.github.fq310.habit.view.create.timing.ModifyTimingHabitActivity;
import com.github.fq310.habit.view.execute.timing.state.IExecuteState;
import com.github.fq310.habit.view.execute.timing.state.PausedState;
import com.github.fq310.habit.view.execute.timing.state.StartState;
import com.github.fq310.habit.view.execute.timing.state.StopState;

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
	private TimingHabit currentHabit;
	private TimingHabitManager habitManager;
	private boolean isNotifiedTodayFinished = false;
	private long todayFinishedTime;
	private final static int MODIFY = 1;
	
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
		initialProgressBars();
		initialState();
	}

	private void initialActionBar() {
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle(currentHabit.getName());
	}
	
	private void initialUtilityObject() {
		handler = new Handler();
		habitManager = new TimingHabitManager(this);
		
		TextView timeTextView = (TextView) findViewById(R.id.execute_project_textView_time);
		timeText = new TimeText(timeTextView);
		recordTimer = new RecordTimer(this);
		
		initialHabit();
	}

	private void initialHabit() {
		long habitID = getIntent().getLongExtra("id", -1);
		currentHabit = habitManager.getProject(habitID);
	}

	private void initialProgressBars() {
		intialTodayFinishedTimeBar();
		initialTotalFinishedTimeBar();
		initialTotalPassedDaysBar();
	}

	private void intialTodayFinishedTimeBar() {
		todayFinishedTime = habitManager.getFinishedSecondsToday(currentHabit.getId());
		long totalTimePerDay = currentHabit.getTimeSpentPerDay();
		int finishedRate = (int) (((float)todayFinishedTime/totalTimePerDay)*100);
		ProgressBar todayFinishedBar = (ProgressBar) findViewById(R.id.execute_project_progressBar_today_finished);
		todayFinishedBar.setMax(100);
		todayFinishedBar.setProgress(finishedRate);
		
		TextView passedDayText = (TextView) findViewById(R.id.execute_project_text_today_fnished);
		passedDayText.setText(R.string.today_Finished_Time);
		passedDayText.append(" [" + finishedRate + " %]");
	}

	private void initialTotalPassedDaysBar() {
		ProgressBar passedDayBar = (ProgressBar) findViewById(R.id.execute_project_progressBar2);
		HabitDate startDate = currentHabit.getStartDate();
		HabitDate endDate = currentHabit.getEndDate();
		passedDayBar.setMax(startDate.daysFrom(endDate));
		int passedDays = startDate.daysFrom(new HabitDate());
		if (passedDays < 0) passedDays = 0;
		passedDayBar.setProgress(passedDays);
		
		TextView passedDayText = (TextView) findViewById(R.id.execute_project_text2);
		passedDayText.setText(startDate.toString() + " - " + endDate.toString());
		passedDayText.append(" [ Day - " + passedDays +" ]");
	}

	private void initialTotalFinishedTimeBar() {
		ProgressBar finishedTimeBar = (ProgressBar) findViewById(R.id.execute_project_progressBar1);
		float rate = (float)currentHabit.getTotalFinishedSeconds()/currentHabit.getTotalSeconds() * 100;
		finishedTimeBar.setMax(100);
		finishedTimeBar.setProgress((int)rate);
		
		TextView finishedTimeText = (TextView) findViewById(R.id.execute_project_text1);
		finishedTimeText.setText(R.string.Total_Finished_Time);
		long totalSeconds = currentHabit.getTotalFinishedSeconds();
		finishedTimeText.append(" [ " + TimeText.getTimeStringFromSeconds(totalSeconds) + " ]");
	}

	private void initialState() {
		startState = new StartState(this);
		stopState = new StopState(this);
		pauseState = new PausedState(this);
		
		boolean projectPaused = currentHabit .isTimer_paused();
		boolean projectStarted = currentHabit.isTimer_started();
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
	
	public void onModifyHabit(MenuItem i) {
		if (inValidStatus()) return;
		Intent intent = new Intent(this, ModifyTimingHabitActivity.class);
		intent.putExtra("id", currentHabit.getId());
		startActivityForResult(intent, MODIFY);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case MODIFY:
			initialHabit();
			initialActionBar();
			initialProgressBars();
		}
	}
	
	public void onDeleteHabit(MenuItem i) {
		WarningDialog.OKDialogWithListener(R.string.execute_warning, 
			R.string.execute_warning_msg, this, 
			new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int button) {
					habitManager.deleteProject(currentHabit);
					finish();
				}
		});
	}
	
	private boolean inValidStatus() {
		if (currentState == stopState) return false;
		WarningDialog.open(R.string.execute_warning, 
				R.string.execute_warning_save, this);
		return true;
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
		initialHabit();
		initialProgressBars();
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

	public TimingHabit getProject() {
		return currentHabit;
	}

	public TimingHabitManager getProjectManager() {
		return habitManager;
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
		currentHabit.setTimer_seconds(timeText.getTotalSeconds());
		currentHabit.setTimerDestroyDate(new HabitDate());
		habitManager.updateProjectAfterExitActivity(currentHabit);
	}
	
	protected void increaseOneSecond() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				timeText.increaseOneSecond();
				currentHabit.setTimer_seconds(currentHabit.getTimer_seconds() + 1);
				long targetTimeToday = currentHabit.getTimeSpentPerDay();
				long finishedTime = timeText.getTotalSeconds() + todayFinishedTime;
				Log.i("fqtime", "current : " + timeText.getTotalSeconds() + ". total finished: " + finishedTime + ". Target: " + targetTimeToday);
				if (isNotifiedTodayFinished == false &&  finishedTime >= targetTimeToday) {
					Log.i("fqtime", "target meet");
					isNotifiedTodayFinished = true;
				}
			}
		});
	}
}
