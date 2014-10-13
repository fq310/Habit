package com.jasonfu19860310.habit;

import java.util.ArrayList;
import java.util.List;

import com.jasonfu19860310.habit.controller.CountHabitManager;
import com.jasonfu19860310.habit.controller.TimingHabitManager;
import com.jasonfu19860310.habit.model.HabitListItem;
import com.jasonfu19860310.tim.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PorjectListAdapter extends BaseAdapter {
	private List<HabitListItem> habits = new ArrayList<HabitListItem>();
	private LayoutInflater layoutInflater;  
	private TimingHabitManager timingHabitManager;
	private Context context;
	private CountHabitManager countHabitManager;
	
	public PorjectListAdapter(Context context) {
		this.context = context;
		layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		timingHabitManager = new TimingHabitManager(context);
		countHabitManager = new CountHabitManager(context);
		habits.addAll(timingHabitManager.getAllHabits());
		habits.addAll(countHabitManager.getAllHabits());
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
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
		
		HabitListItem habit = habits.get(position);
		itemView.setBackgroundColor(habit.getBackgroundClolor());
		
		TextView nameTextView = (TextView) itemView.findViewById(R.id.projectlist_name);
		nameTextView.setText(habit.getName() + habit.getTipString(context));
		
		TextView finishedPercent = (TextView) itemView.findViewById(R.id.project_list_percent);
		finishedPercent.setText(habit.getFinishRate());
		
		return itemView;
	}

	public void reloadData() {
		habits.clear();
		habits.addAll(timingHabitManager.getAllHabits());
		habits.addAll(countHabitManager.getAllHabits());
		notifyDataSetChanged();
	}
	
}
