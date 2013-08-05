package com.jasonfu19860310.tim;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jasonfu19860310.project.Project;
import com.jasonfu19860310.project.ProjectManager;
import com.jasonfu19860310.tim.view.CreateProjectActivity;
import com.jasonfu19860310.tim.view.ExecuteProjectActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showAllProjects();
    }


    private void showAllProjects() {
    	ListView list = (ListView) findViewById(R.id.project_list_main);
    	PorjectListAdapter listAdapter = new PorjectListAdapter(this, getData());
    	list.setAdapter(listAdapter);
    	list.setOnItemClickListener(new ItemClickListener(this));
	}


	private List<? extends Map<String, ?>> getData() {
		ProjectManager projectManager = new ProjectManager(this);
		List<Project> projects = projectManager.getAllProjects();
		List<Map<String, ?>> projectList = new ArrayList<Map<String, ?>>();
		for (Project project : projects) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(PorjectListAdapter.PROJECT_NAME, project.getName());
			map.put(PorjectListAdapter.DAYS_PASSED, 
					"Day-[" + String.valueOf(project.getTotalPassedDays()) + "]");
			int unfinishedTimeOfToday = 
					projectManager.getTimeOn(Calendar.getInstance(), project.getId());
			map.put(PorjectListAdapter.UNFINISHED_TIME_OF_TODAY, 
					"Today remain: [" + String.valueOf(unfinishedTimeOfToday) + "] minutes");
			int totalFinishedTime = project.getTotalFinishedMinitues();
			int totalTime = project.getTotalMinitues();
			map.put(PorjectListAdapter.FINISHED_PERCENT, String.valueOf(totalFinishedTime/totalTime) + "%");
			projectList.add(map);
		}
		return projectList;
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	public void onOptionAddProject(MenuItem i) {
		Intent intent = new Intent(this, CreateProjectActivity.class);
		startActivity(intent);
	}
}

class ItemClickListener implements OnItemClickListener {
	public ItemClickListener(Context context) {
		this.context = context;
	}
	private Context context;
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(context, ExecuteProjectActivity.class);
		context.startActivity(intent);
	}
	
}
