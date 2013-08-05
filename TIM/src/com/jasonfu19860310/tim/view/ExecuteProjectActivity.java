package com.jasonfu19860310.tim.view;

import com.jasonfu19860310.tim.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ExecuteProjectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_execute_project);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.execute_project, menu);
		return true;
	}

}
