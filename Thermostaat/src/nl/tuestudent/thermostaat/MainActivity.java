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
import android.widget.ToggleButton;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements CommunicationClass.SubmitResult{
	
	public static WeekProgram weekProg = null;
	String weekProgram = null; // XML
	String currentTemperature = null; // XML
	Boolean weekProgramState = false; // true=on, false=off
	Boolean activityInFront = false;
	
	TextView statusTV;
	TextView currentTempTV;
	Switch weekProgramSW;
	
	private Handler mHandler = new Handler();
	
	//TODO move all networking code to WeekProgram if possible
	
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
					weekProgramSW.setChecked(weekProgramState);
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
	}
	
	private Runnable mUpdateTempRequest = new Runnable() {
		public void run() {
			new CommunicationClass(MainActivity.this, "currentTemperature", "GET");
		}
	};

	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
		statusTV = (TextView) findViewById(R.id.statusTextView);
		currentTempTV = (TextView) findViewById(R.id.day_spacer);
		weekProgramSW = (Switch) findViewById(R.id.switch1);
		
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
        
        weekProgramSW.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String command;
                if(isChecked) {
                	command = "<week_program_state>on</week_program_state>";
                } else {
                	command = "<week_program_state>off</week_program_state>";
                }
                new CommunicationClass(MainActivity.this, "weekProgramState", "PUT", command);
                switchSetWeekProgram(weekProgramSW);
            }
        });
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mHandler.removeCallbacks(mUpdateTempRequest);
		activityInFront = false;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Request for currentTemperature
        new CommunicationClass(this, "currentTemperature", "GET");
        activityInFront = true;
	}
	
	public void btnChangeWeekProgram(View view) {
		Intent intent = new Intent(this, ChangeWeekprogram.class);
		startActivity(intent);
	}
	
	public void switchSetWeekProgram(View view) {
		boolean on = ((Switch) view).isChecked();
		
		Toast t = Toast.makeText(this, "", 10);
		t.setGravity(Gravity.CENTER, 0, 0);
		if(on) {
			t.setText(R.string.toggle_weekprogram_on);
		} else {
			t.setText(R.string.toggle_weekprogram_off);
		}
		t.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void tempChange(View view) {
		    DialogFragment tempFragment = new PickTemperature();
		    tempFragment.show(getSupportFragmentManager(), "thermostaat");
	}
	
	public void setTempProfile(View view) {
		Intent intent = new Intent (this, TempProfile.class);
		startActivity(intent);
	}

}
