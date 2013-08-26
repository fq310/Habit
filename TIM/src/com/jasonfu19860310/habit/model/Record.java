package com.jasonfu19860310.habit.model;

import java.util.Calendar;

public class Record {
	private long id;
	private long projectId;
	private Calendar recordDate = Calendar.getInstance();
	private int timeConsuming;
	public long getId() {
		return id;
	}
	public long getProjectId() {
		return projectId;
	}
	public Calendar getRecordDate() {
		return recordDate;
	}
	public int getTimeConsuming() {
		return timeConsuming;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public void setRecordDate(Calendar recordDate) {
		this.recordDate = recordDate;
	}
	public void setTimeConsuming(int timeConsuming) {
		this.timeConsuming = timeConsuming;
	}
}
