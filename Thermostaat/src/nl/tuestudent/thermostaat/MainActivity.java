package nl.tuestudent.thermostaat;

import nl.tuestudent.thermostaat.data.WeekProgram;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements CommunicationClass.SubmitResult, PickTemperature.OnTemperatureSelected{
	
	public static WeekProgram weekProg = null;
	
	String weekProgram = null; // XML
	String currentTemperature = null; // XML
	String currentTime = "<time>00:00</time>"; // XML
	Boolean weekProgramState = false; // true=on, false=off
	Boolean activityInFront = false;
	String settingTemperature = "14.0";
	
	TextView statusTV;
	TextView currentTempTV;
	TextView tempSettingTV;
	TextView timeTV;
	
	private Handler mHandler = new Handler();
	
	@Override
	public void submitTemperature(String temperature) {
		settingTemperature = temperature;
		tempSettingTV.setText("Setting: "+temperature);
		new CommunicationClass(this, "currentTemperature", "PUT", "<current_temperature>" + temperature +  "</current_temperature>");
	}
	
	// TODO move all networking code to WeekProgram if possible
	
	@Override
	public void submitResult(String function, String method, String contents) {
		if(function.equals("weekProgram")) {
			if(method.equals("GET")) {
				if(contents.equals("Error")) {
					statusTV.setText("Error obtaining the weekprogram");
				} else {
					weekProgram = contents;
					statusTV.setText("Weekprogram obtained");
				}
			}
		}
		if(function.equals("weekProgramState")) {
			if(method.equals("GET")) {
				if(contents.equals("Error")) {
					statusTV.setText("Error obtaining the weekprogram state");
				} else {
					if(contents.equals("<week_program_state>on</week_program_state>")) {
						weekProgramState = true;
					} else {
						weekProgramState = false;
					}
					final Switch s = (Switch)findViewById(R.id.switch1);
					s.setChecked(weekProgramState);
					// Request for weekProgram
					statusTV.setText("Retrieving week program");
		            new CommunicationClass(this, "weekProgram", "GET");
				}
			}
		}
		if(function.equals("currentTemperature")) {
			if(method.equals("GET")) {
				if(contents.equals("Error")) {
					//statusTV.setText("Error obtaining the current temperature");
				} else {
					currentTemperature = contents;
					// TODO: Should use an xml parser instead
					int begin = 21;
					int end = currentTemperature.length()-22;
					String temperature = currentTemperature.substring(begin, end);
					currentTempTV.setText(temperature + "\u2103");
				}
				if(activityInFront) {
					mHandler.removeCallbacks(mUpdateTempRequest);
					mHandler.postDelayed(mUpdateTempRequest, 1000);
				}
			}
		}
		if(function.equals("time")) {
			if(method.equals("GET")) {
				if(contents.equals("Error")) {
					statusTV.setText("Error obtaining the current time");
				} else {
					currentTime = contents;
					timeTV.setText(currentTime);
				}
				if(activityInFront) {
					mHandler.removeCallbacks(mUpdateTimeRequest);
					mHandler.postDelayed(mUpdateTimeRequest, 1000);
				}
			}
		}
	}
	
	private Runnable mUpdateTempRequest = new Runnable() {
		public void run() {
			new CommunicationClass(MainActivity.this, "currentTemperature", "GET");
		}
	};

	private Runnable mUpdateTimeRequest = new Runnable() {
		public void run() {
			new CommunicationClass(MainActivity.this, "time", "GET");
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Switch s = (Switch)findViewById(R.id.switch1);
		s.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				switchSetWeekProgram(s);
			}
		});
			
		statusTV = (TextView) findViewById(R.id.statusTextView);
		currentTempTV = (TextView) findViewById(R.id.day_spacer);
		tempSettingTV = (TextView) findViewById(R.id.textView2);
		timeTV = (TextView) findViewById(R.id.textViewTime);
		timeTV.setText(currentTime);
		
		//get all the week program info
		weekProg = new WeekProgram();
		
		
		// Check network status
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            statusTV.setText("Retrieving week program state");
            // Request for weekProgramState
            new CommunicationClass(this, "weekProgramState", "GET");
        } else {
        	statusTV.setText("No network connection available");
        }
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mHandler.removeCallbacks(mUpdateTempRequest);
		mHandler.removeCallbacks(mUpdateTimeRequest);
		activityInFront = false;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Request for currentTemperature
        new CommunicationClass(this, "currentTemperature", "GET");
        // Request for current time
        new CommunicationClass(this, "time", "GET");
        activityInFront = true;
	}
	
	public void btnChangeWeekProgram(View view) {
		Intent intent = new Intent(this, ChangeWeekprogram.class);
		startActivity(intent);
	}
	
	public void switchSetWeekProgram(View view) {
		boolean on = ((Switch) view).isChecked();
		
		Toast t = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		t.setGravity(Gravity.CENTER, 0, 0);
		if(on) {
			t.setText(R.string.toggle_weekprogram_on);
		} else {
			t.setText(R.string.toggle_weekprogram_off);
		}
		t.show();
		
		String xmlCommand;
		if(on) {
			xmlCommand = "<week_program_state>on</week_program_state>";
		} else {
			xmlCommand = "<week_program_state>off</week_program_state>";
		}
		new CommunicationClass(MainActivity.this, "weekProgramState", "PUT", xmlCommand);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void tempChange(View view) {
		    DialogFragment tempFragment = new PickTemperature();
		    Bundle args = new Bundle();
		    args.putString("settingTemperature", settingTemperature);
		    tempFragment.setArguments(args);
		    tempFragment.show(getSupportFragmentManager(), "thermostaat");
	}
	
	public void setTempProfile(View view) {
		Intent intent = new Intent (this, TempProfile.class);
		startActivity(intent);
	}

}
