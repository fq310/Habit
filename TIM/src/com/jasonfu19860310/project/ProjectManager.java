package com.jasonfu19860310.project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jasonfu19860310.db.ProjectDBContract.ProjectEntry;
import com.jasonfu19860310.db.ProjectDBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProjectManager {
	private ProjectDBHelper databaseHelper;
	public ProjectManager(Context context) {
		 databaseHelper = new ProjectDBHelper(context);
	}
	
	public List<Project> getAllProjects() {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		String sortOrder =
				ProjectEntry.COLUMN_NAME_NAME + " DESC";
		Cursor cursor = database.query(ProjectEntry.TABLE_NAME, null, null, null, null, null, sortOrder);
		ArrayList<Project> allProjects = new ArrayList<Project>();
		if (cursor.moveToFirst()) {
			allProjects.add(readProjectFrom(cursor));
			while (cursor.moveToNext()) {
				allProjects.add(readProjectFrom(cursor));
			}
		}
		return allProjects;
	}

	private Project readProjectFrom(Cursor cursor) {
		long id = cursor.getLong(
				cursor.getColumnIndexOrThrow(ProjectEntry._ID)
		);
		String name = cursor.getString(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_NAME)
		);
		long startDate = cursor.getLong(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_START_DATE)
		);
		long endDate = cursor.getLong(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_END_DATE)
		);
		int hours = cursor.getInt(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_HOURS)
		);
		int minitues = cursor.getInt(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_MINITUES)
		);
		int totalMinitues = cursor.getInt(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_TOTAL_MINITUES)
		);
		int totalFinishedMinitues = cursor.getInt(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_TOTAL_FINISHED_MINITUES)
		);
		int totalPassedDays = cursor.getInt(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_TOTAL_PASSED_DAYS)
		);
		String workdays = cursor.getString(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_WORKDAYS)
		);
		Project project = new Project();
		project.setId(id);
		project.setName(name);
		project.getStartDate().setTimeInMillis(startDate);
		project.getEndDate().setTimeInMillis(endDate);
		project.setHours(hours);
		project.setMinitues(minitues);
		project.setTotalFinishedMinitues(totalFinishedMinitues);
		project.setTotalMinitues(totalMinitues);
		project.setTotalPassedDays(totalPassedDays);
		project.setWorkdays(workdays);
		return project;
	}
	
	public void saveNewProject(Project project) {
		project.setTotalMinitues(getTotalTime(project));
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ProjectEntry.COLUMN_NAME_NAME, project.getName());
		values.put(ProjectEntry.COLUMN_NAME_START_DATE, project.getStartDate().getTimeInMillis());
		values.put(ProjectEntry.COLUMN_NAME_END_DATE, project.getEndDate().getTimeInMillis());
		values.put(ProjectEntry.COLUMN_NAME_HOURS, project.getHours());
		values.put(ProjectEntry.COLUMN_NAME_MINITUES, project.getMinitues());
		values.put(ProjectEntry.COLUMN_NAME_WORKDAYS, project.getWorkdaysString());
		values.put(ProjectEntry.COLUMN_NAME_TOTAL_FINISHED_MINITUES, project.getTotalFinishedMinitues());
		values.put(ProjectEntry.COLUMN_NAME_TOTAL_MINITUES, project.getTotalMinitues());
		values.put(ProjectEntry.COLUMN_NAME_TOTAL_PASSED_DAYS, project.getTotalPassedDays());
		database.insert(ProjectEntry.TABLE_NAME, null, values);
	}
	
	public Project getProject(int id) {
		return new Project();
	}
	
	public void updateProject(Project project) {
		project.setTotalMinitues(getTotalTime(project));
		project.setTotalPassedDays(getDaysBwtween(project.getStartDate(), Calendar.getInstance()));
		
	}
	
	public int getTimeOn(Calendar date, long id) {
		return 60;
	}
	public int getTotalFinishedTime(int id) {
		return 600;
	}
	
	private int getTotalTime(Project project) {
		int days = getDaysBwtween(project.getStartDate(), project.getEndDate()) + 1;
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
	
	public ProjectDBHelper getDb() {
		return databaseHelper;
	}

	public int getUnfinishedMinitesToday(long id) {
		return 60;
	}
}
