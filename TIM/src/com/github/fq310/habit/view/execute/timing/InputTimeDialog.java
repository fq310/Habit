package com.github.fq310.habit.view.execute.timing;

import com.github.fq310.habit.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class InputTimeDialog {
	public InputTimeDialog(TimeText currentTime, ExecuteHabitActivity context) {
		this.timeText = currentTime;
		this.context = context;
	}

	protected static final String COLON = ":";
	private TimeText timeText;
	private ExecuteHabitActivity context;
	private EditText hours;
	private EditText minutes;
	private EditText seconds;

	public void openDialog() {
		AlertDialog.Builder builder = initialDialogBuilder();
		AlertDialog dialog = builder.create();
		dialog.show();
		addMinutesChangeListener(dialog);
		addHoursChangeListener(dialog);
		addSecoondsChangeListener(dialog);
	}
	
	private AlertDialog.Builder initialDialogBuilder() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.input_time)
			.setView(context.getLayoutInflater().inflate(R.layout.input_time, null));
		final String oldTime = timeText.getTime();
		addButtonClickListener(builder, oldTime);
		return builder;
	}

	private void addButtonClickListener(AlertDialog.Builder builder, final String oldTime) {
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   if (!timeText.isValidValue()) {
	        		   timeText.setTime(oldTime);
	        		   WarningDialog.open(R.string.execute_error_msg_title, 
	        				   R.string.execute_error_msg_time, context);
	        	   }
	        	   context.saveCurrentState();
	           }
	    });
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   timeText.setTime(oldTime);
	        	   context.saveCurrentState();
	           }
	       });
	}

	private void addHoursChangeListener(AlertDialog dialog) {
		hours = (EditText) dialog.findViewById(R.id.input_time_editText_hour);
		hours.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String hour = getTime(hours);
				if (hour.length() == 0) return;
				timeText.updateHourOnly(hour);
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

	private void addMinutesChangeListener(AlertDialog dialog) {
		minutes = (EditText) dialog.findViewById(R.id.input_time_editText_minitue);
		minutes.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String minute = getTime(minutes);
				timeText.updateMinutesOnly(minute);
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
	
	private String getTime(EditText timeText) {
		String timeString = timeText.getText().toString().trim();
		if (timeString.length() == 0) {
			timeString = "0";
		}
		return timeString;
	}
	
	private void addSecoondsChangeListener(AlertDialog dialog) {
		seconds = (EditText) dialog.findViewById(R.id.input_time_editText_seconds);
		seconds.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String second = getTime(seconds);
				timeText.updateSecondsOnly(second);
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
}
