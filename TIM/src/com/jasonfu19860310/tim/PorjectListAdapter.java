package com.jasonfu19860310.tim;

import java.util.List;
import java.util.Map;


import android.content.Context;
import android.widget.SimpleAdapter;

public class PorjectListAdapter extends SimpleAdapter {

	public static final String FINISHED_PERCENT = "percent";
	public static final String DAYS_PASSED = "days";
	public static final String UNFINISHED_TIME_OF_TODAY = "time";
	public static final String PROJECT_NAME = "name";

	public PorjectListAdapter(Context context, List<? extends Map<String, ?>> data) {
		super(context, data, R.layout.project_list_item, 
				new String[]{PROJECT_NAME, UNFINISHED_TIME_OF_TODAY, DAYS_PASSED, FINISHED_PERCENT}, 
				new int[]{R.id.projectlist_name, 
						  R.id.projectlist_unfinishedtime,
						  R.id.projectlist_days,
						  R.id.project_list_percent});
	}

}
