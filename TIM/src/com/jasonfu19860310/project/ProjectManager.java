package com.jasonfu19860310.project;

import java.util.ArrayList;
import java.util.List;

public class ProjectManager {
	private static ProjectManager projectManager = new ProjectManager();
	private ProjectManager() {}
	public static ProjectManager getInstance() {
		return projectManager;
	}
	
	public List<Project> getAllProjects() {
		ArrayList<Project> projects = new ArrayList<Project>();
		projects.add(new Project());
		return projects;
	}
}
