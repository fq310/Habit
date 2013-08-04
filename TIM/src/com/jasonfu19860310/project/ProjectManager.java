package com.jasonfu19860310.project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProjectManager {
	private static ProjectManager projectManager = new ProjectManager();
	private ProjectManager() {}
	public static ProjectManager getInstance() {
		return projectManager;
	}
	
	public List<Project> getAllProjects() {
		ArrayList<Project> projects = new ArrayList<Project>();
		projects.add(getExampleProject());
		projects.add(getExampleProject());
		return projects;
	}
	private Project getExampleProject() {
		Project example = new Project();
		example.setName("aaa");
		Calendar start = example.getStartDate();
		start.set(2013, 1, 1);
		Calendar end = example.getEndDate();
		end.set(2014, 4, 15);
		example.setHours(1);
		example.setMinitues(0);
		example.setTotalFinishedMinitues(1300);
		example.setTotalMinitues(getTotalTime(example));
		example.setTotalPassedDays(getDaysBwtween(example.getStartDate(), Calendar.getInstance()));
		return example;
	}
	
	public void saveNewProject(Project project) {
		project.setTotalMinitues(getTotalTime(project));
		
	}
	
	public Project getProject(int id) {
		return new Project();
	}
	
	public void updateProject(Project project) {
		project.setTotalMinitues(getTotalTime(project));
		project.setTotalPassedDays(getDaysBwtween(project.getStartDate(), Calendar.getInstance()));
		
	}
	
	public int getTimeOn(Calendar date, int id) {
		return 60;
	}
	public int getTotalFinishedTime(int id) {
		return 600;
	}
	
	private int getTotalTime(Project project) {
		int days = getDaysBwtween(project.getStartDate(), project.getEndDate());
		return days * (project.getHours() * 60 + project.getMinitues());
	}
	
	private int getDaysBwtween(Calendar startDate, Calendar currentDate) {
		int days = currentDate.get(Calendar.DAY_OF_YEAR) - 
				startDate.get(Calendar.DAY_OF_YEAR);
		if (dateYearNotEqual(startDate, currentDate)) {
			startDate = (Calendar) startDate.clone();
			do {
				days += startDate.getActualMaximum(Calendar.DAY_OF_YEAR);
				startDate.add(Calendar.YEAR, 1);
			} while (dateYearNotEqual(currentDate, startDate));
		}
		return days;
	}
	
	private boolean dateYearNotEqual(Calendar startDate, Calendar currentDate) {
		return currentDate.get(Calendar.YEAR) != startDate.get(Calendar.YEAR);
	}
}
