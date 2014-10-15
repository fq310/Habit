package com.github.fq310.habit.view.execute.count;

import com.github.fq310.habit.R;
import com.github.fq310.habit.controller.CountHabitManager;
import com.github.fq310.habit.model.CountHabit;
import com.github.fq310.habit.view.create.count.ModifyCountHabitActivity;
import com.github.fq310.habit.view.execute.timing.WarningDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ExecuteCountHabitActivity extends Activity {
	private CountHabitManager habitManager;
	private CountHabit habit;
	private final static int MODIFY = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_execute_count_habit);
		habitManager = new CountHabitManager(this);
		initialHabit();
		updateUIData();
	}
	
	private void updateUIData() {
		setTotal();
		setToday();
	}

	private void setToday() {
		TextView today = (TextView) findViewById(R.id.execute_count_habit_textView_today);
		String str = habit.getTodayChecked() + "/" + habit.getTimesPerDay();
		today.setText(str);
	}

	private void setTotal() {
		TextView total = (TextView) findViewById(R.id.execute_count_habit_progress);
		String str = getString(R.string.count_habit_total);
		str += " " + habit.getFinishRate();
		total.setText(str);
	}

	private void initialHabit() {
		long habitID = getIntent().getLongExtra("id", -1);
		habit = habitManager.getHabit(habitID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.execute_count_habit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case MODIFY:
			initialHabit();
			updateUIData();
		}
	}
	
	public void onModifyHabit(MenuItem i) {
		Intent intent = new Intent(this, ModifyCountHabitActivity.class);
		intent.putExtra("id", habit.getId());
		startActivityForResult(intent, MODIFY);
	}
	
	public void onDeleteHabit(MenuItem i) {
		WarningDialog.OKDialogWithListener(R.string.execute_warning, 
			R.string.execute_warning_msg, this, 
			new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int button) {
					habitManager.deleteHabit(habit);
					finish();
				}
		});
	}
	
	public void onCheckClicked(View view) {
		habit.addCheck();
		updateUIData();
	}
	
	public void onUnCheckClicked(View view) {
		habit.unCheck();
		updateUIData();
	}

	public void onOKClicked(View view) {
		habitManager.updateHabit(habit);
		Toast.makeText(this, R.string.execute_record_saved, Toast.LENGTH_SHORT).show();
	}
}
