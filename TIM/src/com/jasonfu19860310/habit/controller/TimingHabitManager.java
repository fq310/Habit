package com.jasonfu19860310.habit.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jasonfu19860310.habit.adt.HabitDate;
import com.jasonfu19860310.habit.db.DBExportImport;
import com.jasonfu19860310.habit.db.DBHelper;
import com.jasonfu19860310.habit.db.DBContract.HabitEntry;

import com.jasonfu19860310.habit.model.HabitListItem;
import com.jasonfu19860310.habit.model.TimingHabit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TimingHabitManager {
	private DBHelper databaseHelper;
	private RecordManager recordManager;
	private DBExportImport dbExportImport;
	public TimingHabitManager(Context context) {
		databaseHelper = new DBHelper(context);
		recordManager = new RecordManager(context);
		dbExportImport = new DBExportImport(context);
	}
	
	public List<HabitListItem> getAllHabits() {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		String sortOrder =
				HabitEntry.COLUMN_NAME_NAME + " DESC";
		Cursor cursor = database.query(HabitEntry.TABLE_NAME, null, null, null, null, null, sortOrder);
		List<HabitListItem> allProjects = new ArrayList<HabitListItem>();
		if (cursor.moveToFirst()) {
			allProjects.add(readHabitFrom(cursor));
			while (cursor.moveToNext()) {
				allProjects.add(readHabitFrom(cursor));
			}
		}
		database.close();
		return allProjects;
	}

	private TimingHabit readHabitFrom(Cursor cursor) {
		long id = cursor.getLong(
				cursor.getColumnIndexOrThrow(HabitEntry._ID)
		);
		String name = cursor.getString(
				cursor.getColumnIndexOrThrow(HabitEntry.COLUMN_NAME_NAME)
		);
		long startDate = cursor.getLong(
				cursor.getColumnIndexOrThrow(HabitEntry.COLUMN_NAME_START_DATE)
		);
		long endDate = cursor.getLong(
				cursor.getColumnIndexOrThrow(HabitEntry.COLUMN_NAME_END_DATE)
		);
		int hours = cursor.getInt(
				cursor.getColumnIndexOrThrow(HabitEntry.COLUMN_NAME_HOURS)
		);
		int minitues = cursor.getInt(
				cursor.getColumnIndexOrThrow(HabitEntry.COLUMN_NAME_MINITUES)
		);
		long totalSeconds = cursor.getLong(
				cursor.getColumnIndexOrThrow(HabitEntry.COLUMN_NAME_TOTAL_SECONDS)
		);
		long totalFinishedSeconds = cursor.getLong(
				cursor.getColumnIndexOrThrow(HabitEntry.COLUMN_NAME_TOTAL_FINISHED_SECONDS)
		);
		int totalPassedDays = cursor.getInt(
				cursor.getColumnIndexOrThrow(HabitEntry.COLUMN_NAME_TOTAL_PASSED_DAYS)
		);
		String timerStarted = cursor.getString(
				cursor.getColumnIndexOrThrow(HabitEntry.COLUMN_NAME_TIMER_STARTED)
		);
		String timerPaused = cursor.getString(
				cursor.getColumnIndexOrThrow(HabitEntry.COLUMN_NAME_TIMER_PAUSED)
		);
		long timerMinutes = cursor.getLong(
				cursor.getColumnIndexOrThrow(HabitEntry.COLUMN_NAME_TIMER_SECONDS)
		);
		long timerDestroyDate = cursor.getLong(
				cursor.getColumnIndexOrThrow(HabitEntry.COLUMN_NAME_TIMER_DESTORY_DATE)
		);
		TimingHabit project = new TimingHabit();
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
	
	public void saveNewProject(TimingHabit project) {
		project.setTotalSeconds(getTotalTime(project));
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		ContentValues values = getUpdateValues(project);
		database.insert(HabitEntry.TABLE_NAME, null, values);
		database.close();
		dbExportImport.exportDataAuto();
	}

	private ContentValues getUpdateValues(TimingHabit project) {
		ContentValues values = new ContentValues();
		values.put(HabitEntry.COLUMN_NAME_NAME, project.getName());
		values.put(HabitEntry.COLUMN_NAME_START_DATE, project.getStartDate().getTimeInMillis());
		values.put(HabitEntry.COLUMN_NAME_END_DATE, project.getEndDate().getTimeInMillis());
		values.put(HabitEntry.COLUMN_NAME_HOURS, project.getHours());
		values.put(HabitEntry.COLUMN_NAME_MINITUES, project.getMinitues());
		values.put(HabitEntry.COLUMN_NAME_TOTAL_FINISHED_SECONDS, project.getTotalFinishedSeconds());
		values.put(HabitEntry.COLUMN_NAME_TOTAL_SECONDS, project.getTotalSeconds());
		values.put(HabitEntry.COLUMN_NAME_TOTAL_PASSED_DAYS, project.getTotalPassedDays());
		values.put(HabitEntry.COLUMN_NAME_TIMER_STARTED,  String.valueOf(project.isTimer_started()));
		values.put(HabitEntry.COLUMN_NAME_TIMER_PAUSED,  String.valueOf(project.isTimer_paused()));
		values.put(HabitEntry.COLUMN_NAME_TIMER_SECONDS, project.getTimer_seconds());
		values.put(HabitEntry.COLUMN_NAME_TIMER_DESTORY_DATE, project.getTimerDestroyDate().getTimeInMillis());
		return values;
	}
	
	public TimingHabit getProject(long projectID) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		String selection = HabitEntry._ID + "=?";
		String id = String.valueOf(projectID);
		String[] selectionArgs = {id};
		Cursor cursor = database.query(HabitEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
		if (cursor.moveToFirst()) {
			TimingHabit project = readHabitFrom(cursor);
			database.close();
			return project;
		}
		database.close();
		return null;
	}
	
	public void updateProject(TimingHabit project) {
		project.setTotalSeconds(getTotalTime(project));
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		ContentValues values = getUpdateValues(project);
		updateTable(project, database, values);
		database.close();
		dbExportImport.exportDataAuto();
	}
	
	public int getTimeOn(Calendar date, long id) {
		return 60;
	}
	public int getTotalFinishedTime(int id) {
		return 600;
	}
	
	private long getTotalTime(TimingHabit project) {
		int totalDays = project.getStartDate().daysFrom(project.getEndDate());
		return totalDays * ((project.getHours() * 60 + project.getMinitues()) * 60);
	}
	
	public DBHelper getDb() {
		return databaseHelper;
	}

	public int getUnfinishedMinitesToday(long id) {
		return 60;
	}

	public void updateProjectAfterSave(TimingHabit project) {
		project.setTotalFinishedSeconds(project.getTotalFinishedSeconds() + project.getTimer_seconds());
		project.setTotalPassedDays(project.getStartDate().daysFrom(new HabitDate()));
		updateProject(project);
	}

	private void updateTable(TimingHabit project, SQLiteDatabase database,
			ContentValues values) {
		String selection = HabitEntry._ID + "=?";
		String id = String.valueOf(project.getId());
		String[] selectionArgs = {id};
		database.update(HabitEntry.TABLE_NAME, values, selection, selectionArgs);
	}

	public void updateProjectAfterExitActivity(TimingHabit project) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(HabitEntry.COLUMN_NAME_TIMER_STARTED, String.valueOf(project.isTimer_started()));
		values.put(HabitEntry.COLUMN_NAME_TIMER_PAUSED, String.valueOf(project.isTimer_paused()));
		values.put(HabitEntry.COLUMN_NAME_TIMER_SECONDS, project.getTimer_seconds());
		values.put(HabitEntry.COLUMN_NAME_TIMER_DESTORY_DATE, project.getTimerDestroyDate().getTimeInMillis());
		updateTable(project, database, values);
		database.close();
		dbExportImport.exportDataAuto();
	}

	public void deleteProject(TimingHabit currentProject) {
		long projectID = currentProject.getId();
		deleteProjectByID(projectID);
	}

	public void deleteProjectByID(long projectID) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		String selection = HabitEntry._ID + "=?";
		String id = String.valueOf(projectID);
		String[] selectionArgs = {id};
		database.delete(HabitEntry.TABLE_NAME, selection, selectionArgs);
		database.close();
		recordManager.deleteRecords(projectID);
		dbExportImport.exportDataAuto();
	}

	public void addNewRecord(TimingHabit habit, long totalSeconds) {
		recordManager.addNewRecord(habit.getId(), totalSeconds);
		updateProjectAfterSave(habit);
	}

	public long getFinishedSecondsToday(long id) {
		return recordManager.getFinishedSecondsToday(id);
	}
}
