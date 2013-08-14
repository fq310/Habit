package com.jasonfu19860310.tim.view.execute;

import com.jasonfu19860310.project.DateUtil;
import com.jasonfu19860310.tim.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class InputTimeDialog {
	public InputTimeDialog(TextView currentTime, Activity context) {
		this.currentTime = currentTime;
		this.context = context;
	}

	protected static final String COLON = ":";
	private TextView currentTime;
	private Activity context;
	private EditText hours;
	private EditText minutes;
	private EditText seconds;

	public void openDialog() {
		AlertDialog.Builder builder = initialDialogBuilder();
		AlertDialog dialog = builder.create();
		dialog.show();
		addMinutesChangeListener(currentTime, dialog);
		addHoursChangeListener(currentTime, dialog);
		addSecoondsChangeListener(currentTime, dialog);
	}
	
	private AlertDialog.Builder initialDialogBuilder() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.input_time)
			.setView(context.getLayoutInflater().inflate(R.layout.input_time, null));
		final String oldTime = currentTime.getText().toString();
		addButtonClickListener(builder, currentTime, oldTime);
		return builder;
	}

	private void addButtonClickListener(AlertDialog.Builder builder,
			final TextView currentTime, final String oldTime) {
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   if (!validInput()) {
	        		   currentTime.setText(oldTime);
	        		   WarningDialog.open(R.string.execute_error_msg_title, 
	        				   R.string.execute_error_msg_time, context);
	        	   }
	           }

			private boolean validInput() {
				return isValidHour(getIntValue(hours)) &&
	        			   isValidTime(getIntValue(minutes)) &&
	        			   isValidTime(getIntValue(seconds));
			}

			private int getIntValue(EditText text) {
				String value = text.getText().toString();
				if (value == null || value.length() == 0) {
					return 0;
				} else {
					return Integer.valueOf(value);
				}
			}
			
			private boolean isValidTime(int value) {
				return 0 <= value && value <= 59;
			}
			
			private boolean isValidHour(int value) {
				return 0 <= value;
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
		hours = (EditText) dialog.findViewById(R.id.input_time_editText_hour);
		hours.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String[] times = DateUtil.getHourAndMinute(currentTime.getText().toString());
				currentTime.setText(hours.getText().toString().trim()
						+ COLON + times[1] + COLON + times[2]);
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
		minutes = (EditText) dialog.findViewById(R.id.input_time_editText_minitue);
		minutes.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String[] times = DateUtil.getHourAndMinute(currentTime.getText().toString());
				currentTime.setText(times[0] + COLON + 
						minutes.getText().toString().trim() +  COLON + times[2]);
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
	
	private void addSecoondsChangeListener(final TextView currentTime,
			AlertDialog dialog) {
		seconds = (EditText) dialog.findViewById(R.id.input_time_editText_seconds);
		seconds.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String[] times = DateUtil.getHourAndMinute(currentTime.getText().toString());
				currentTime.setText(times[0] + COLON + times[1] + COLON + 
						seconds.getText().toString().trim());
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
