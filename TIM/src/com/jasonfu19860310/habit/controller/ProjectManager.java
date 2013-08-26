package com.jasonfu19860310.habit.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jasonfu19860310.habit.db.DBHelper;
import com.jasonfu19860310.habit.db.ProjectDBHelper;
import com.jasonfu19860310.habit.db.DBContract.ProjectEntry;
import com.jasonfu19860310.habit.model.DateUtil;
import com.jasonfu19860310.habit.model.Project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProjectManager {
	private DBHelper databaseHelper;
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
		long totalSeconds = cursor.getLong(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_TOTAL_SECONDS)
		);
		long totalFinishedSeconds = cursor.getLong(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_TOTAL_FINISHED_SECONDS)
		);
		int totalPassedDays = cursor.getInt(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_TOTAL_PASSED_DAYS)
		);
		String timerStarted = cursor.getString(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_TIMER_STARTED)
		);
		String timerPaused = cursor.getString(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_TIMER_PAUSED)
		);
		long timerMinutes = cursor.getLong(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_TIMER_SECONDS)
		);
		long timerDestroyDate = cursor.getLong(
				cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_NAME_TIMER_DESTORY_DATE)
		);
		Project project = new Project();
		project.setId(id);
		project.setName(name);
		project.getStartDate().setTimeInMillis(startDate);
		project.getEndDate().setTimeInMillis(endDate);
		project.setHours(hours);
		project.setMinitues(minitues);
		project.setTotalFinishedSeconds(totalFinishedSeconds);
		project.setTotalSeconds(totalSeconds);
		project.setTotalPassedDays(totalPassedDays);
		project.setTimer_started(Boolean.valueOf(timerStarted));
		project.setTimer_paused(Boolean.valueOf(timerPaused));
		project.setTimer_seconds(timerMinutes);
		project.getTimerDestroyDate().setTimeInMillis(timerDestroyDate);
		return project;
	}
	
	public void saveNewProject(Project project) {
		project.setTotalSeconds(getTotalTime(project));
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		ContentValues values = getUpdateValues(project);
		database.insert(ProjectEntry.TABLE_NAME, null, values);
	}

	private ContentValues getUpdateValues(Project project) {
		ContentValues values = new ContentValues();
		values.put(ProjectEntry.COLUMN_NAME_NAME, project.getName());
		values.put(ProjectEntry.COLUMN_NAME_START_DATE, project.getStartDate().getTimeInMillis());
		values.put(ProjectEntry.COLUMN_NAME_END_DATE, project.getEndDate().getTimeInMillis());
		values.put(ProjectEntry.COLUMN_NAME_HOURS, project.getHours());
		values.put(ProjectEntry.COLUMN_NAME_MINITUES, project.getMinitues());
		values.put(ProjectEntry.COLUMN_NAME_TOTAL_FINISHED_SECONDS, project.getTotalFinishedSeconds());
		values.put(ProjectEntry.COLUMN_NAME_TOTAL_SECONDS, project.getTotalSeconds());
		values.put(ProjectEntry.COLUMN_NAME_TOTAL_PASSED_DAYS, project.getTotalPassedDays());
		values.put(ProjectEntry.COLUMN_NAME_TIMER_STARTED, project.isTimer_started());
		values.put(ProjectEntry.COLUMN_NAME_TIMER_PAUSED, project.isTimer_paused());
		values.put(ProjectEntry.COLUMN_NAME_TIMER_SECONDS, project.getTimer_seconds());
		values.put(ProjectEntry.COLUMN_NAME_TIMER_DESTORY_DATE, project.getTimerDestroyDate().getTimeInMillis());
		return values;
	}
	
	public Project getProject(long projectID) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		String selection = ProjectEntry._ID + "=?";
		String id = String.valueOf(projectID);
		String[] selectionArgs = {id};
		Cursor cursor = database.query(ProjectEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
		if (cursor.moveToFirst()) {
			return readProjectFrom(cursor);
		}
		return null;
	}
	
	public void updateProject(Project project) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		ContentValues values = getUpdateValues(project);
		updateTable(project, database, values);
	}
	
	public int getTimeOn(Calendar date, long id) {
		return 60;
	}
	public int getTotalFinishedTime(int id) {
		return 600;
	}
	
	private long getTotalTime(Project project) {
		int days = DateUtil.getDaysBwtween(project.getStartDate(), project.getEndDate()) + 1;
		return days * ((project.getHours() * 60 + project.getMinitues()) * 60);
	}
	
	public DBHelper getDb() {
		return databaseHelper;
	}

	public int getUnfinishedMinitesToday(long id) {
		return 60;
	}

	public void updateProjectAfterSave(Project project) {
		project.setTotalFinishedSeconds(project.getTotalFinishedSeconds() + project.getTimer_seconds());
		project.setTotalPassedDays(DateUtil.getDaysBwtween(project.getStartDate(), Calendar.getInstance()) + 1);
		updateProject(project);
	}

	private void updateTable(Project project, SQLiteDatabase database,
			ContentValues values) {
		String selection = ProjectEntry._ID + "=?";
		String id = String.valueOf(project.getId());
		String[] selectionArgs = {id};
		database.update(ProjectEntry.TABLE_NAME, values, selection, selectionArgs);
	}

	public void updateProjectAfterExitActivity(Project project) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ProjectEntry.COLUMN_NAME_TIMER_STARTED, String.valueOf(project.isTimer_started()));
		values.put(ProjectEntry.COLUMN_NAME_TIMER_PAUSED, String.valueOf(project.isTimer_paused()));
		values.put(ProjectEntry.COLUMN_NAME_TIMER_SECONDS, project.getTimer_seconds());
		values.put(ProjectEntry.COLUMN_NAME_TIMER_DESTORY_DATE, project.getTimerDestroyDate().getTimeInMillis());
		updateTable(project, database, values);
	}
}
