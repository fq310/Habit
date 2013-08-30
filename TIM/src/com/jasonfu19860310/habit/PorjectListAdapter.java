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
	public static final String FINISHED_PERCENT = "percent";
	public static final String DAYS_PASSED = "days";
	public static final String UNFINISHED_TIME_OF_TODAY = "time";
	public static final String PROJECT_NAME = "name";
	private List<Habit> habits;
	private LayoutInflater layoutInflater;  
	private HabitManager habitManager;
	private HaibtListColorAnimator animator = new HaibtListColorAnimator();
	
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
		itemView.setBackgroundColor(Color.rgb(0, 0, 0));
		
		habits = habitManager.getAllHabits();
		Habit habit = habits.get(position);
		String habitName = habit.getName();
		TextView nameTextView = (TextView) itemView.findViewById(R.id.projectlist_name);
		boolean timerPaused = habit.isTimer_paused();
		boolean timerStarted = habit.isTimer_started();
		if (timerPaused) {
			habitName = habitName + " [PAUSED]";  
			animator.pauseAnimate(itemView, habit.getId());
		}
		if (timerStarted) {
			habitName = habitName + " [STARTED]"; 
			animator.startAnimate(itemView, habit.getId());
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
		animator.removeOutdatedAnimator(habits);
		notifyDataSetChanged();
	}
	
}
