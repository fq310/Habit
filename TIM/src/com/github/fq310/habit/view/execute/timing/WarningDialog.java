package com.github.fq310.habit.view.execute.timing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

import com.github.fq310.habit.R;

public class WarningDialog {
	
	public static void open(int title, int message, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
		       .setTitle(title);
		builder.setPositiveButton(R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public static void OKDialogWithListener(int title, int message, Context context, OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
		       .setTitle(title);
		builder.setPositiveButton(R.string.ok, listener);
		builder.setNegativeButton(R.string.cancel, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
