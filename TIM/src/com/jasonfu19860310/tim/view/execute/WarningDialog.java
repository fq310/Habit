package com.jasonfu19860310.tim.view.execute;

import android.app.AlertDialog;
import android.content.Context;

import com.jasonfu19860310.tim.R;

public class WarningDialog {
	
	public static void open(int title, int message, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
		       .setTitle(title);
		builder.setPositiveButton(R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
