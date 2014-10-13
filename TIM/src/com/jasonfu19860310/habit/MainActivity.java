package com.jasonfu19860310.habit;

import com.jasonfu19860310.habit.controller.TimingHabitManager;
import com.jasonfu19860310.habit.model.HabitListItem;
import com.jasonfu19860310.habit.view.CreateHabitActivity;
import com.jasonfu19860310.tim.R;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainActivity extends ListActivity {
	private long selectedHabitID;
	public static final int ADD = 1;
	public static final int EXECUTE = 2;
	public static final int SYNC = 3;
	private PorjectListAdapter listAdapter;
	private TimingHabitManager habitManager;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		getActionBar().setDisplayShowHomeEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        habitManager = new TimingHabitManager(this);
        listAdapter = new PorjectListAdapter(this); 
        initialHabitList();
    }

	private void initialHabitList() {
		setListAdapter(listAdapter);
        getListView().setOnItemClickListener(new ItemClickListener(this));
        registerForContextMenu(getListView());
        getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long id) {
				selectedHabitID = id;
				return false;
		}});
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }
	
	public void onOptionAddHabit(MenuItem i) {
		Intent intent = new Intent(this, CreateHabitActivity.class);
		this.startActivityForResult(intent, ADD);
	}
	
	public void onAbout(MenuItem i) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.about_msg)
		       .setTitle(R.string.about);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	           }
	       });
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void onSync(MenuItem i) {
		Intent intent = new Intent(this, Sync.class);
		this.startActivityForResult(intent, SYNC);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ADD:
			listAdapter.reloadData();
			break;
		case EXECUTE:
			listAdapter.reloadData();
			break;
		case SYNC:
			listAdapter.reloadData();
			break;
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		habitManager.deleteProjectByID(selectedHabitID);
		listAdapter.reloadData();
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add(R.string.deleteHabit);
	}
}

class ItemClickListener implements OnItemClickListener {
	public ItemClickListener(ListActivity context) {
		this.context = context;
	}
	private ListActivity context;
	@Override
	public void onItemClick(AdapterView<?> list, View view, int position, long id) {
		HabitListItem item = (HabitListItem) list.getItemAtPosition(position);
		Intent intent = item.getExecuteIntent(context);
		intent.putExtra("id", id);
		context.startActivityForResult(intent, MainActivity.EXECUTE);
	}
	
}

