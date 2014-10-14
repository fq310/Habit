package com.jasonfu19860310.habit;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.jasonfu19860310.tim.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
Copyright (C) 2011 by Brad Greco <brad@bgreco.net>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

public class DirectoryPicker extends ListActivity {

	public static final String START_DIR = "startDir";
	public static final String ONLY_DIRS = "onlyDirs";
	public static final String SHOW_HIDDEN = "showHidden";
	public static final String CHOSEN_DIRECTORY = "chosenDir";
	public static final int PICK_DIRECTORY = 43522432;
	private File dir;
	private boolean showHidden = false;
	private boolean onlyDirs = true ;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSDDirectory();
        setContentView(R.layout.chooser_list);
        setTitle(dir.getAbsolutePath());
        initialButtonChoose();
        initialDirList();
    }

	private void getSDDirectory() {
		Bundle extras = getIntent().getExtras();
        dir = Environment.getExternalStorageDirectory();
        if (extras != null) {
        	String preferredStartDir = extras.getString(START_DIR);
        	showHidden = extras.getBoolean(SHOW_HIDDEN, false);
        	onlyDirs = extras.getBoolean(ONLY_DIRS, true);
        	if(preferredStartDir != null) {
            	File startDir = new File(preferredStartDir);
            	if(startDir.isDirectory()) {
            		dir = startDir;
            	}
            } 
        }
	}

	private void initialDirList() {
		ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        if(!dir.canRead()) {
        	Context context = getApplicationContext();
        	Toast toast = Toast.makeText(context, R.string.backup_chose_file_readErr, Toast.LENGTH_LONG);
        	toast.show();
        	return;
        }
        
        final ArrayList<File> files = filter(dir.listFiles(), onlyDirs, showHidden);
        String[] names = names(files);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, names));        	
        

        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		if(!files.get(position).isDirectory())
        			return;
        		String path = files.get(position).getAbsolutePath();
                Intent intent = new Intent(DirectoryPicker.this, DirectoryPicker.class);
                intent.putExtra(DirectoryPicker.START_DIR, path);
                intent.putExtra(DirectoryPicker.SHOW_HIDDEN, showHidden);
                intent.putExtra(DirectoryPicker.ONLY_DIRS, onlyDirs);
                startActivityForResult(intent, PICK_DIRECTORY);
        	}
        });
	}

	private void initialButtonChoose() {
		int green = Color.parseColor(getString(R.color.green));
		((Button)findViewById(R.id.button_backup_choose_dir))
		.setBackgroundColor(green);
        Button btnChoose = (Button) findViewById(R.id.button_backup_choose_dir);
        String name = dir.getName();
        if(name.length() == 0)
        	name = "/";
        btnChoose.setText(getResources().getString(R.string.backup_chose_file) + " " + "'" + name + "'");
        btnChoose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	returnDir(dir.getAbsolutePath());
            }
        });
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == PICK_DIRECTORY && resultCode == RESULT_OK) {
	    	Bundle extras = data.getExtras();
	    	String path = (String) extras.get(DirectoryPicker.CHOSEN_DIRECTORY);
	        returnDir(path);
    	}
    }
	
    private void returnDir(String path) {
    	Intent result = new Intent();
    	result.putExtra(CHOSEN_DIRECTORY, path);
        setResult(RESULT_OK, result);
    	finish();    	
    }

	public ArrayList<File> filter(File[] file_list, boolean onlyDirs, boolean showHidden) {
		ArrayList<File> files = new ArrayList<File>();
		for(File file: file_list) {
			if(onlyDirs && !file.isDirectory())
				continue;
			if(!showHidden && file.isHidden())
				continue;
			files.add(file);
		}
		Collections.sort(files, new Comparator<File>() {
			@Override
			public int compare(File arg0, File arg1) {
				String name0 = arg0.getName();
				String name1 = arg1.getName();
				if (name0 != null && name1 != null)
					return name0.compareToIgnoreCase(name1);
				return arg0.compareTo(arg1);
			}
		});
		return files;
	}
	
	public String[] names(ArrayList<File> files) {
		String[] names = new String[files.size()];
		int i = 0;
		for(File file: files) {
			names[i] = file.getName();
			i++;
		}
		return names;
	}
}

