package com.jasonfu19860310.habit.view;

import android.view.View;

import com.jasonfu19860310.habit.model.Project;

public class CreateProjectActivity extends BaseActivity {

	public CreateProjectActivity() {
		super();
		project = new Project();
	}

	@Override
	public void onSaveProject(View v) {
		if (isValidProject()) {
			getProjectManager().saveNewProject(project);
			finish();
		}
	}

}
