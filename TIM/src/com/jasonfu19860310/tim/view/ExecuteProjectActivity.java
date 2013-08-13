package com.jasonfu19860310.tim.view;


import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.jasonfu19860310.project.DateUtil;
import com.jasonfu19860310.project.Project;
import com.jasonfu19860310.project.ProjectManager;
import com.jasonfu19860310.project.RecordManager;
import com.jasonfu19860310.tim.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ExecuteProjectActivity extends Activity {

	private static final String PAUSE = "Pause";
	private static final String START = "Start";
	private static final String COLON = ":";
	private TextView currentTime;
	private long projectID;
	private RecordManager recordManager;
	private ProjectManager projectManager;
	private Project project;
	private Timer timer = new Timer();
	private TimerTask timerTask;
	private Button startButton;
	private Handler handler;  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_execute_project);
		handler = new Handler();
		currentTime = (TextView) findViewById(R.id.execute_project_textView_time);
		startButton = (Button)findViewById(R.id.button_execute_start);
		projectID = getIntent().getLongExtra("id", -1);
		recordManager = new RecordManager(this);
		projectManager = new ProjectManager(this);
		project = projectManager.getProject(projectID);
		timerTask = new RecordTimer(currentTime, project, handler);
		initialTimer();
	}

	private void initialTimer() {
		boolean projectPaused = project.isTimer_paused();
		boolean projectStarted = project.isTimer_started();
		if (projectPaused) {
			long totalSeconds = project.getTimer_seconds();
			updateTime(totalSeconds);
			changeStartButtonTo(START);
		}
		if (projectStarted) {
			Calendar startDate = project.getTimerStartDate();
			Calendar currentDate = Calendar.getInstance();
			long totalTime = currentDate.getTimeInMillis() - startDate.getTimeInMillis();
			project.setTimer_seconds(totalTime);
			updateTime(totalTime);
			changeStartButtonTo(PAUSE);
			timer.schedule(timerTask, 500, 1000); 
		}
	}

	private void updateTime(long totalSeconds) {
		String hours = stringOf(totalSeconds / 60*60);
		String minutes = stringOf((totalSeconds % 3600) / 60);
		String seconds = stringOf((totalSeconds % 3600) % 60);
		currentTime.setText(hours + COLON + minutes + COLON + seconds);
	}

	private String stringOf(long time) {
		String result = Long.toString(time);
		if (result.length() < 2) return "0" + result;
		return result;
	}

	private void changeStartButtonTo(String status) {
		startButton.setText(status);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.execute_project, menu);
		return true;
	}
	
	public void onStartClicked(View view) {
		String startStatus = getStartButton();
		if (startStatus.equals(START)) {
			changeStartButtonTo(PAUSE);
			project.setTimer_started(true);
			project.setTimer_paused(false);
			project.setTimerStartDate(Calendar.getInstance());
			timer = new Timer();
			timerTask = new RecordTimer(currentTime, project, handler);
			timer.schedule(timerTask, 500, 1000); 
		} else if (startStatus.equals(PAUSE)) {
			timer.cancel();
			changeStartButtonTo(START);
			project.setTimer_started(false);
			project.setTimer_paused(true);
			project.setTimer_seconds(getSecondsFromTextView());
		}
	}
	
	private long getSecondsFromTextView() {
		String timeString = currentTime.getText().toString();
		String[] time = timeString.split(COLON);
		int seconds = Integer.valueOf(time[0]) * 3600 +
			Integer.valueOf(time[1]) * 60 + 
			Integer.valueOf(time[2]);
		return seconds;
	}

	private String getStartButton() {
		return startButton.getText().toString();
	}

	public void onClearClicked(View view) {
		currentTime.setText(R.string.inital_time);
		initialRecordStatus();
	}

	private void initialRecordStatus() {
		project.setTimer_seconds(0);
		project.setTimer_started(false);
		project.setTimer_paused(false);
	}
	
	public void onSaveClicked(View view) {
		timer.cancel();
		String[] time = DateUtil.getHourAndMinute(((TextView) currentTime).getText().toString());
		int hour = Integer.valueOf(time[0]);
		int minute = Integer.valueOf(time[1]);
		int seconds = Integer.valueOf(time[2]);
		if (hour == 0 && minute == 0 && seconds == 0) {
			createWarningDialog(R.string.execute_error_msg_title, 
					R.string.execute_error_msg);
			return;
		}
		recordManager.addNewRecord(project, getSecondsFromTextView());
		changeStartButtonTo(START);
		projectManager.updateProject(project);
		initialRecordStatus();
		createWarningDialog(R.string.execute_error_msg_success, 
				R.string.execute_error_msg_ok);
	}

	private void createWarningDialog(int title, int message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		       .setTitle(title);
		builder.setPositiveButton(R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void onInputManuallyClicked(View view) {
		AlertDialog.Builder builder = initialDialogBuilder();
		AlertDialog dialog = builder.create();
		dialog.show();
		addMinutesChangeListener(currentTime, dialog);
		addHoursChangeListener(currentTime, dialog);
		
	}

	private AlertDialog.Builder initialDialogBuilder() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.input_time)
			.setView(getLayoutInflater().inflate(R.layout.input_time, null));
		final String oldTime = currentTime.getText().toString();
		addButtonClickListener(builder, currentTime, oldTime);
		return builder;
	}

	private void addButtonClickListener(AlertDialog.Builder builder,
			final TextView currentTime, final String oldTime) {
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	           }
	       });
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   currentTime.setText(oldTime);
	           }
	       });
	}

	private void addHoursChangeListener(final TextView currentTime,
			AlertDialog dialog) {
		final EditText hours = (EditText) dialog.findViewById(R.id.input_time_editText_hour);
		hours.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String[] times = DateUtil.getHourAndMinute(currentTime.getText().toString());
				currentTime.setText(hours.getText().toString().trim() + COLON + times[1]);
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
			}});
	}

	private void addMinutesChangeListener(final TextView currentTime,
			AlertDialog dialog) {
		final EditText minutes = 
				(EditText) dialog.findViewById(R.id.input_time_editText_minitue);
		minutes.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String[] times = DateUtil.getHourAndMinute(currentTime.getText().toString());
				currentTime.setText(times[0] + COLON + 
						minutes.getText().toString().trim());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
			}});
	}
	
	@Override
	protected void onDestroy() {
		project.setTimer_seconds(getSecondsFromTextView());
		projectManager.updateProject(project);
		super.onDestroy();
	}

}

class RecordTimer extends TimerTask {
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
