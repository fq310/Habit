package com.jasonfu19860310.tim;

import java.util.List;

import com.jasonfu19860310.project.Project;
import com.jasonfu19860310.project.ProjectManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PorjectListAdapter extends BaseAdapter {

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public static final String FINISHED_PERCENT = "percent";
	public static final String DAYS_PASSED = "days";
	public static final String UNFINISHED_TIME_OF_TODAY = "time";
	public static final String PROJECT_NAME = "name";
	private List<Project> projects;
	private LayoutInflater layoutInflater;  
	private ProjectManager projectManager;
	
	public PorjectListAdapter(Context context) {
		layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		projectManager = new ProjectManager(context);
		projects = projectManager.getAllProjects();
	}

	@Override
	public int getCount() {
		return projects.size();
	}

	@Override
	public Object getItem(int position) {
		return projects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return projects.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView;
		if ((convertView == null)) {
			itemView = layoutInflater.inflate(
	                R.layout.project_list_item, parent, false);
		} else {
			itemView = convertView;
		}
		
		projects = projectManager.getAllProjects();
		Project project = projects.get(position);
		String projectName = project.getName();
		TextView nameTextView = (TextView) itemView.findViewById(R.id.projectlist_name);
		boolean timerPaused = project.isTimer_paused();
		boolean timerStarted = project.isTimer_started();
		if (timerPaused) {
			projectName = projectName + " [PAUSED]";  
		}
		if (timerStarted) {
			projectName = projectName + " [STARTED]"; 
		}
		nameTextView.setText(projectName);
		
		TextView finishedPercent = (TextView) itemView.findViewById(R.id.project_list_percent);
		finishedPercent.setText(project.getTotalFinishedSeconds()/project.getTotalSeconds() * 100 + "%");
		
		return itemView;
	}

	public void reloadData() {
		projects = projectManager.getAllProjects();
		notifyDataSetChanged();
	}

}
