package com.github.fq310.habit;

import com.github.fq310.habit.R;
import com.github.fq310.habit.db.DBExportImport;
import com.github.fq310.habit.db.DBHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Sync extends Activity {
	public static final int CHOOSE_DIR = 1;
	private String directory;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync);
		getActionBar().setDisplayShowHomeEnabled(false);
		initialButton();
	}

	private void initialButton() {
		SharedPreferences settings = getSharedPreferences(DBExportImport.PREFS_NAME, 0);
		String savedDir = settings.getString(
				DirectoryPicker.CHOSEN_DIRECTORY, 
				getResources().getString(R.string.backup_set_dir));
		Button setDirbutton = getDirButton();
		setDirbutton.setText(savedDir);
	}

	private Button getDirButton() {
		Button setDirbutton = (Button)findViewById(R.id.button_backup_set_dir);
		return setDirbutton;
	}

	public void onImportDataNow(View v) {
		if (isValidDir() == false) {
			waning();
			return;
		}
		new DBExportImport(this).importData(getDir() + "/" + DBHelper.DATABASE_NAME);
		Toast.makeText(this, R.string.backup_sync_importOK, Toast.LENGTH_SHORT).show();
	}

	public void onExportDataNow(View v) {
		if (isValidDir() == false) {
			waning();
			return;
		}
		new DBExportImport(this).exportData(getDir() + "/" + DBHelper.DATABASE_NAME);
		Toast.makeText(this, R.string.backup_sync_importOK, Toast.LENGTH_SHORT).show();
	}
	
	private void waning() {
		Toast.makeText(this, R.string.backup_sync_exportOK, Toast.LENGTH_LONG).show();
	}

	private boolean isValidDir() {
		String dir = getDir();
		if (dir.equals(getResources().getString(R.string.backup_set_dir)))
			return false;
		return true;
	}

	private String getDir() {
		Button setDirbutton = getDirButton();
		String dir = setDirbutton.getText().toString();
		return dir;
	}
	
	public void onCancel(View v) {
		this.finish();
	}
	
	public void onOk(View v) {
		saveDir();
		this.finish();
	}

	private void saveDir() {
		if (directory != null) {
			SharedPreferences settings = getSharedPreferences(DBExportImport.PREFS_NAME, 0);
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putString(DirectoryPicker.CHOSEN_DIRECTORY, directory);
		    editor.commit();
		}
	}
	
	public void onSetDir(View v) {
		Intent intent = new Intent(this, DirectoryPicker.class);
		this.startActivityForResult(intent, CHOOSE_DIR);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) return;
		switch (requestCode) {
		case CHOOSE_DIR:
			directory = data.getStringExtra(DirectoryPicker.CHOSEN_DIRECTORY);
			Button dirButton = ((Button)findViewById(R.id.button_backup_set_dir));
			dirButton.setText(directory);
			saveDir();
			break;
		}
	}

}
