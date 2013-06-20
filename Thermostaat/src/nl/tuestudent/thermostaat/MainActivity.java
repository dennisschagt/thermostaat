package nl.tuestudent.thermostaat;

import android.os.Bundle;
import android.app.Activity;
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

public class MainActivity extends FragmentActivity{

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
