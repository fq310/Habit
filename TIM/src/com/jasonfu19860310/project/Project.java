package com.jasonfu19860310.project;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

public class Project {
	private String title = "project";
	
	public String getTitle() {
		return title;
	}

	public void showOn(Context context, ViewGroup parent) {
		Button projectButton = new Button(context);
        projectButton.setText(getTitle());
    	int parentWidth = parent.getWidth();
    	LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    	layoutParams.setMargins((int)(0.2*parentWidth), 3, (int)(0.2*parentWidth), 0);
    	parent.addView(projectButton, layoutParams);
	}
}
