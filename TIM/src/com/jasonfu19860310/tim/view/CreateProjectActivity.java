package com.jasonfu19860310.tim.view;

import android.view.View;

import com.jasonfu19860310.project.Project;
import com.jasonfu19860310.project.ProjectManager;

public class CreateProjectActivity extends ProjectInfo {

	public CreateProjectActivity() {
		super();
		project = new Project();
	}

	@Override
	public void onSaveProject(View v) {
		if (isValidProject()) {
			ProjectManager.getInstance().saveNewProject(project);
		}
	}

}
