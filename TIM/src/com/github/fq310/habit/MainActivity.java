package com.github.fq310.habit;

import com.github.fq310.habit.R;
import com.github.fq310.habit.model.HabitListItem;
import com.github.fq310.habit.view.create.count.CreateCountHabitActivity;
import com.github.fq310.habit.view.create.timing.CreateTimingHabitActivity;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity {
	public static final int ADD_TIMING = 1;
	public static final int ADD_COUNT = 4;
	public static final int EXECUTE = 2;
	public static final int SYNC = 3;
	private PorjectListAdapter listAdapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		getActionBar().setDisplayShowHomeEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listAdapter = new PorjectListAdapter(this); 
        initialHabitList();
    }

	private void initialHabitList() {
		setListAdapter(listAdapter);
        getListView().setOnItemClickListener(new ItemClickListener(this));
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }
	
	public void onOptionAddTimingHabit(MenuItem i) {
		Intent intent = new Intent(this, CreateTimingHabitActivity.class);
		this.startActivityForResult(intent, ADD_TIMING);
	}
	
	public void onOptionAddCountHabit(MenuItem i) {
		Intent intent = new Intent(this, CreateCountHabitActivity.class);
		this.startActivityForResult(intent, ADD_COUNT);
	}
	
	public void onAbout(MenuItem i) {
		Intent intent = new Intent(this, AboutActivity.class);
		this.startActivity(intent);
	}
	
	public void onSync(MenuItem i) {
		Intent intent = new Intent(this, Sync.class);
		this.startActivityForResult(intent, SYNC);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		listAdapter.reloadData();
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
		intent.putExtra("id", item.getId());
		context.startActivityForResult(intent, MainActivity.EXECUTE);
	}
	
}

