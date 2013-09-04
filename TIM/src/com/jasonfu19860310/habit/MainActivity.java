package com.jasonfu19860310.habit;

import com.jasonfu19860310.habit.controller.HabitManager;
import com.jasonfu19860310.habit.view.CreateHabitActivity;
import com.jasonfu19860310.habit.view.execute.ExecuteHabitActivity;
import com.jasonfu19860310.tim.R;

import android.os.Bundle;
import android.app.ListActivity;
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
	private PorjectListAdapter listAdapter;
	private HabitManager habitManager;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		getActionBar().setDisplayShowHomeEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        habitManager = new HabitManager(this);
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
        getMenuInflater().inflate(R.menu.add_habit, menu);
        return true;
    }
	
	public void onOptionAddHabit(MenuItem i) {
		Intent intent = new Intent(this, CreateHabitActivity.class);
		this.startActivityForResult(intent, ADD);
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
		Intent intent = new Intent(context, ExecuteHabitActivity.class);
		intent.putExtra("id", id);
		context.startActivityForResult(intent, MainActivity.EXECUTE);
	}
	
}

