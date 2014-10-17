package com.github.fq310.habit;

import android.app.Activity;
import android.os.Bundle;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle(R.string.about);
	}
}
