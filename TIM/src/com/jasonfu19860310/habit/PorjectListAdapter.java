package com.jasonfu19860310.habit;

import java.text.DecimalFormat;
import java.util.List;

import com.jasonfu19860310.habit.controller.HabitManager;
import com.jasonfu19860310.habit.model.Habit;
import com.jasonfu19860310.tim.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PorjectListAdapter extends BaseAdapter {

	private static final int COLOR_BLACK = Color.rgb(0, 0, 0);
	private static final int COLOR_YELLOW = Color.rgb(184, 133, 10);
	private static final int COLOR_GREEN = Color.rgb(64, 116, 52);

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public static final String FINISHED_PERCENT = "percent";
	public static final String DAYS_PASSED = "days";
	public static final String UNFINISHED_TIME_OF_TODAY = "time";
	public static final String PROJECT_NAME = "name";
	private List<Habit> habits;
	private LayoutInflater layoutInflater;  
	private HabitManager habitManager;
	
	public PorjectListAdapter(Context context) {
		layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		habitManager = new HabitManager(context);
		habits = habitManager.getAllHabits();
	}

	@Override
	public int getCount() {
		return habits.size();
	}

	@Override
	public Object getItem(int position) {
		return habits.get(position);
	}

	@Override
	public long getItemId(int position) {
		return habits.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView;
		if ((convertView == null)) {
			itemView = layoutInflater.inflate(
	                R.layout.habit_list_item, parent, false);
		} else {
			itemView = convertView;
		}
		itemView.clearAnimation();
		itemView.setBackgroundColor(COLOR_BLACK);
		
		habits = habitManager.getAllHabits();
		Habit habit = habits.get(position);
		String habitName = habit.getName();
		TextView nameTextView = (TextView) itemView.findViewById(R.id.projectlist_name);
		boolean timerPaused = habit.isTimer_paused();
		boolean timerStarted = habit.isTimer_started();
		if (timerPaused) {
			habitName = habitName + " [PAUSED]";
			itemView.setBackgroundColor(COLOR_YELLOW);
		}
		if (timerStarted) {
			habitName = habitName + " [STARTED]"; 
			itemView.setBackgroundColor(COLOR_GREEN);
		}
		nameTextView.setText(habitName);
		
		TextView finishedPercent = (TextView) itemView.findViewById(R.id.project_list_percent);
		finishedPercent.setText(getFinishedRate(habit));
		
		return itemView;
	}

	private String getFinishedRate(Habit project) {
		float finishRate = ((float)project.getTotalFinishedSeconds()/project.getTotalSeconds()) * 100;
		DecimalFormat df = new DecimalFormat("###.##");
		return df.format(finishRate) + "%";
	}

	public void reloadData() {
		habits = habitManager.getAllHabits();
		notifyDataSetChanged();
	}
	
}
