package com.jasonfu19860310.habit.controller;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jasonfu19860310.habit.db.DBContract.CountHabitEntry;
import com.jasonfu19860310.habit.db.DBExportImport;
import com.jasonfu19860310.habit.db.DBHelper;
import com.jasonfu19860310.habit.model.CountHabit;
import com.jasonfu19860310.habit.model.HabitListItem;

public class CountHabitManager {
	private DBHelper databaseHelper;
	private DBExportImport dbExportImport;
	public CountHabitManager(Context context) {
		databaseHelper = new DBHelper(context);
		dbExportImport = new DBExportImport(context);
	}
	
	public List<HabitListItem> getAllHabits() {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		String sortOrder =
				CountHabitEntry.COLUMN_NAME_NAME + " DESC";
		Cursor cursor = database.query(CountHabitEntry.TABLE_NAME, null, null, null, null, null, sortOrder);
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

	private CountHabit readHabitFrom(Cursor cursor) {
		long id = cursor.getLong(
				cursor.getColumnIndexOrThrow(CountHabitEntry._ID)
		);
		String name = cursor.getString(
				cursor.getColumnIndexOrThrow(CountHabitEntry.COLUMN_NAME_NAME)
		);
		long startDate = cursor.getLong(
				cursor.getColumnIndexOrThrow(CountHabitEntry.COLUMN_NAME_START_DATE)
		);
		long endDate = cursor.getLong(
				cursor.getColumnIndexOrThrow(CountHabitEntry.COLUMN_NAME_END_DATE)
		);
		long timesPerDay = cursor.getLong(
				cursor.getColumnIndexOrThrow(CountHabitEntry.COLUMN_NAME_TIMES_PER_DAY)
		);
		long todayChecked = cursor.getLong(
				cursor.getColumnIndexOrThrow(CountHabitEntry.COLUMN_NAME_TODAY_CHECKED)
		);
		long totalChecked = cursor.getLong(
				cursor.getColumnIndexOrThrow(CountHabitEntry.COLUMN_NAME_TOTAL_CHECKED)
		);
		CountHabit habit = new CountHabit(id, name, startDate, endDate, timesPerDay, totalChecked, todayChecked);
		return habit;
	}
	
	public void createNewHabit(CountHabit project) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		ContentValues values = getUpdateValues(project);
		database.insert(CountHabitEntry.TABLE_NAME, null, values);
		database.close();
		dbExportImport.exportDataAuto();
	}

	private ContentValues getUpdateValues(CountHabit project) {
		ContentValues values = new ContentValues();
		values.put(CountHabitEntry.COLUMN_NAME_NAME, project.getName());
		values.put(CountHabitEntry.COLUMN_NAME_START_DATE, project.getStartDate().getTimeInMillis());
		values.put(CountHabitEntry.COLUMN_NAME_END_DATE, project.getEndDate().getTimeInMillis());
		values.put(CountHabitEntry.COLUMN_NAME_TIMES_PER_DAY, project.getTimesPerDay());
		values.put(CountHabitEntry.COLUMN_NAME_TODAY_CHECKED, project.getTodayChecked());
		values.put(CountHabitEntry.COLUMN_NAME_TOTAL_CHECKED, project.getTotalChecked());
		return values;
	}
	
	public void updateHabit(CountHabit habit) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		ContentValues values = getUpdateValues(habit);
		updateTable(habit, database, values);
		database.close();
		dbExportImport.exportDataAuto();
	}
	
	private void updateTable(CountHabit habit, SQLiteDatabase database,
			ContentValues values) {
		String selection = CountHabitEntry._ID + "=?";
		String id = String.valueOf(habit.getId());
		String[] selectionArgs = {id};
		database.update(CountHabitEntry.TABLE_NAME, values, selection, selectionArgs);
	}
	
	public void deleteHabit(CountHabit habit) {
		long projectID = habit.getId();
		deleteHabitByID(projectID);
	}
	
	public void deleteHabitByID(long habitID) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		String selection = CountHabitEntry._ID + "=?";
		String id = String.valueOf(habitID);
		String[] selectionArgs = {id};
		database.delete(CountHabitEntry.TABLE_NAME, selection, selectionArgs);
		database.close();
		dbExportImport.exportDataAuto();
	}
	
	public CountHabit getHabit(long habitID) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		String selection = CountHabitEntry._ID + "=?";
		String id = String.valueOf(habitID);
		String[] selectionArgs = {id};
		Cursor cursor = database.query(CountHabitEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
		if (cursor.moveToFirst()) {
			CountHabit project = readHabitFrom(cursor);
			database.close();
			return project;
		}
		database.close();
		return null;
	}
}
