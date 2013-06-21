package nl.tuestudent.thermostaat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ChangeWeekprogram extends Activity {
	public final static String EXTRA_MESSAGE = "nl.tuestudent.thermostaat.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_weekprogram);
	}
	
	public void changeDay(View view) {
		Intent intent = new Intent(this, ChangeDay2.class);
	    String message = "";
	    switch(view.getId()) {
	    case R.id.button_change_sunday:
	    	message += "Sunday";
	    	break;
	    case R.id.button_change_monday:
	    	message += "Monday";
	    	break;
	    case R.id.button_change_tuesday:
	    	message += "Tuesday";
	    	break;
	    case R.id.button_change_wednesday:
	    	message += "Wednesday";
	    	break;
	    case R.id.button_change_thursday:
	    	message += "Thursday";
	    	break;
	    case R.id.button_change_friday:
	    	message += "Friday";
	    	break;
	    case R.id.button_change_saturday:
	    	message += "Saturday";
	    	break;
	    }
	    intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_weekprogram, menu);
		return true;
	}

}
