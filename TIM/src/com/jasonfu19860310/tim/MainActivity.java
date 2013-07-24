package com.jasonfu19860310.tim;

import java.util.List;

import com.jasonfu19860310.project.Project;
import com.jasonfu19860310.project.ProjectManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showAllProjects();
    }


    private void showAllProjects() {
    	List<Project> projects = ProjectManager.getInstance().getAllProjects();
    	for (Project project : projects) {
    		project.showOn(this, (ViewGroup) findViewById(R.id.project_list));
    	}
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	public void onOptionAddProject(MenuItem i) {
		Intent intent = new Intent(this, CreateProjectActivity.class);
		startActivity(intent);
	}
    
}
