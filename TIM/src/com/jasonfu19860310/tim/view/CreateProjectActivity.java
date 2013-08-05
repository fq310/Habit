package com.jasonfu19860310.tim.view;

import android.view.View;

import com.jasonfu19860310.project.Project;

public class CreateProjectActivity extends ProjectInfo {

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
