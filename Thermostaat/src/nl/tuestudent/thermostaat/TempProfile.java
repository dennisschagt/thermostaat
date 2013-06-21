package nl.tuestudent.thermostaat;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;


public class TempProfile extends Activity implements CommunicationClass.SubmitResult {
	NumberPicker t1, t2, t3, t4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_temp_profile);
		
		// TODO: Set the numberpickers to the current day/night temperature
		t1 = (NumberPicker) this.findViewById(R.id.numberPicker1);
		t1.setMinValue(5);
		t1.setMaxValue(30);
		t1.setValue(19);
		t1.setWrapSelectorWheel(false);
		t2 = (NumberPicker) this.findViewById(R.id.numberPicker2);
		t2.setMinValue(0);
		t2.setMaxValue(9);
		t2.setValue(5);
		t3 = (NumberPicker) this.findViewById(R.id.numberPicker3);
		t3.setMinValue(5);
		t3.setMaxValue(30);
		t3.setValue(16);
		t3.setWrapSelectorWheel(false);
		t4 = (NumberPicker) this.findViewById(R.id.numberPicker4);
		t4.setMinValue(0);
		t4.setMaxValue(9);
		t4.setValue(5);
	}

	public void onSaveAndReturn(View view) {
		// TODO: Maybe disable the submitButton and/or the temperaturePickers
		String dayTemperature = t1.getValue() + "." + t2.getValue();
		new CommunicationClass(this, "dayTemperature", "PUT", "<day_temperature>"+dayTemperature+"</day_temperature>");
		String nightTemperature = t3.getValue() + "." + t4.getValue();
		new CommunicationClass(this, "nightTemperature", "PUT", "<night_temperature>"+nightTemperature+"</night_temperature>");
		Toast.makeText(this, "Submitting day and night temperatures", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void submitResult(String function, String method, String contents) {
		// TODO: Check if both day and night temperature are succesfully submitted
		finish();
	}
}