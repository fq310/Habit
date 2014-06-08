package com.jasonfu19860310.habit.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.jasonfu19860310.habit.DirectoryPicker;
import com.jasonfu19860310.tim.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.widget.Toast;

public class DBExportImport {
	public static final String PACKAGE_NAME = "com.jasonfu19860310.tim";
	public static final String DATA_DIRECTORY_DATABASE =
			Environment.getDataDirectory() +
			"/data/" + PACKAGE_NAME +
			"/databases/" + DBHelper.DATABASE_NAME; 
	public static final String PREFS_NAME = "MyPrefsFile";
	private Context context;

	public DBExportImport(Context context) {
		this.context = context;
	}
	
	public void importData(String source) {
		copyFile(source, DBExportImport.DATA_DIRECTORY_DATABASE);
	}
	
	public void exportData(String target) {
		copyFile(DBExportImport.DATA_DIRECTORY_DATABASE, target);
		
	}

	public void copyFile(String source, String target) {
		OutputStream targetFile = null;
		FileInputStream sourceFile = null;
		createIfNotExist(target);
		try {
			sourceFile = new FileInputStream(new File(source));
			targetFile = new FileOutputStream(target);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = sourceFile.read(buffer))>0){
			    targetFile.write(buffer, 0, length);
			}
			targetFile.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (targetFile != null) {
				try {
					targetFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (sourceFile != null) {
				try {
					sourceFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void createIfNotExist(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void exportDataAuto() {
		SharedPreferences settings = context.getSharedPreferences(DBExportImport.PREFS_NAME, 0);
		String initialDir = context.getResources().getString(R.string.backup_set_dir);
		String savedDir = settings.getString(
				DirectoryPicker.CHOSEN_DIRECTORY, 
				initialDir);
		if (savedDir.equals(initialDir))
			return;
		exportData(savedDir + "/" + DBHelper.DATABASE_NAME);
		Toast.makeText(context, R.string.backup_sync_finish, Toast.LENGTH_LONG).show();
	}

}
