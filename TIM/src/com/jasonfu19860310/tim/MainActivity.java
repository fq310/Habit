package com.jasonfu19860310.tim;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jasonfu19860310.project.Project;
import com.jasonfu19860310.project.ProjectManager;
import com.jasonfu19860310.tim.view.CreateProjectActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
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
	}


	private List<? extends Map<String, ?>> getData() {
		List<Project> projects = ProjectManager.getInstance().getAllProjects();
		List<Map<String, ?>> projectList = new ArrayList<Map<String, ?>>();
		for (Project project : projects) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(PorjectListAdapter.PROJECT_NAME, project.getName());
			map.put(PorjectListAdapter.DAYS_PASSED, 
					"Day-[" + String.valueOf(project.getTotalPassedDays()) + "]");
			int unfinishedTimeOfToday = 
					ProjectManager.getInstance().getTimeOn(Calendar.getInstance(), project.getId());
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
