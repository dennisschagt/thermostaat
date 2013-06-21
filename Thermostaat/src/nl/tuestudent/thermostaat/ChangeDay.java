package nl.tuestudent.thermostaat;

import java.util.ArrayList;

import nl.tuestudent.thermostaat.data.DayProgram;
import nl.tuestudent.thermostaat.data.DayProgram.ProgramSwitch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChangeDay extends FragmentActivity {
	
	String dayName;
	DayProgram dayProgram;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_day);
		
		// Get the message from the intent
	    Intent intent = getIntent();
	    dayName = intent.getStringExtra(ChangeWeekprogram.EXTRA_MESSAGE);

	    // Create the text view
	    TextView textView = (TextView) findViewById(R.id.t_day_name);
	    textView.setText(dayName);
	    
	    dayProgram = MainActivity.weekProg.findDayProgram(dayName);
	    populateTimeList();
	}

	private void populateTimeList() {
		String[] timeArray = getResources().getStringArray(R.array.time_array);
		
		ListView timeList = (ListView) findViewById(R.id.timeList);
		if(timeList == null) {
			Log.d("thermostaat", "Oh no! timeList seems to be null!");
		}
		
		ArrayList<String> timeAL = new ArrayList<String>();
		for(String i: timeArray) {
			timeAL.add(i);
		}
		
		LazyAdapter timeAdapter = new LazyAdapter(this,
				android.R.layout.simple_list_item_1, timeAL);
		
		timeList.setAdapter(timeAdapter);
		
		//set day/night colors
		ProgramSwitch[] switches = dayProgram.getSwitches();
		for(int i=0; i < switches.length; i++) {
			ProgramSwitch ps = switches[i];
			if(ps.getState().equals("off")) {
				continue;
			}
			if(ps.getType().equals("day")) {
				timeAdapter.getItem(ps.getHour());
			}
		}
		
		
		//item click handler	
		timeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//show add time menu
				Log.d("thermostaat", "JE HEBT OP EEN ITEM GECLICKED: " + position);
				DialogFragment tempFrag = new PickDayNight();
				tempFrag.show(getSupportFragmentManager(), "kjljnjkjhjftfuhjvjuyg");
				
				//TODO send the time (position of item you clicked on) to the dialog so it can use that as default dialog
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_day, menu);
		return true;
	}

}
