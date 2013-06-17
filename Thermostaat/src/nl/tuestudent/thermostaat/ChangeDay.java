package nl.tuestudent.thermostaat;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChangeDay extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_day);
		
		// Get the message from the intent
	    Intent intent = getIntent();
	    String message = intent.getStringExtra(ChangeWeekprogram.EXTRA_MESSAGE);

	    // Create the text view
	    TextView textView = (TextView) findViewById(R.id.t_day_name);
	    textView.setText(message);

	    // Set the text view as the activity layout
	    //setContentView(textView);
	    

	    
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_day, menu);
		return true;
	}

}
