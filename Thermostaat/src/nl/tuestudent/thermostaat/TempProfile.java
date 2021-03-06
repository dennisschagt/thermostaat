package nl.tuestudent.thermostaat;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;


public class TempProfile extends Activity implements CommunicationClass.SubmitResult, NumberPicker.OnValueChangeListener {
	NumberPicker t1, t2, t3, t4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_temp_profile);
		
		// Get the message from the intent
	    Intent intent = getIntent();
	    String dayTempXML = intent.getStringExtra(MainActivity.DAY_DATA);
	    String nightTempXML = intent.getStringExtra(MainActivity.NIGHT_DATA);
	    String dayTemp = dayTempXML.replaceAll("</?day_temperature>", "");
	    String nightTemp = nightTempXML.replaceAll("</?night_temperature>", "");
	    
		// TODO: Set the numberpickers to the current day/night temperature
		t1 = (NumberPicker) this.findViewById(R.id.numberPicker1);
		t1.setMinValue(5);
		t1.setMaxValue(30);
		t1.setValue(Integer.parseInt(dayTemp.substring(0, dayTemp.indexOf("."))));
		t1.setWrapSelectorWheel(false);
		t1.setOnValueChangedListener(this);
		t2 = (NumberPicker) this.findViewById(R.id.numberPicker2);
		t2.setMinValue(0);
		t2.setMaxValue(9);
		t2.setValue(Integer.parseInt(dayTemp.substring(dayTemp.indexOf(".")+1)));
		t2.setOnValueChangedListener(this);
		t3 = (NumberPicker) this.findViewById(R.id.numberPicker3);
		t3.setMinValue(5);
		t3.setMaxValue(30);
		t3.setValue(Integer.parseInt(nightTemp.substring(0, nightTemp.indexOf("."))));
		t3.setWrapSelectorWheel(false);
		t3.setOnValueChangedListener(this);
		t4 = (NumberPicker) this.findViewById(R.id.numberPicker4);
		t4.setMinValue(0);
		t4.setMaxValue(9);
		t4.setValue(Integer.parseInt(nightTemp.substring(nightTemp.indexOf(".")+1)));
		t4.setOnValueChangedListener(this);
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

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		NumberPicker t1 = (NumberPicker) this.findViewById(R.id.numberPicker1);
		NumberPicker t2 = (NumberPicker) this.findViewById(R.id.numberPicker2);
		NumberPicker t3 = (NumberPicker) this.findViewById(R.id.numberPicker3);
		NumberPicker t4 = (NumberPicker) this.findViewById(R.id.numberPicker4);
		if(picker==t1 && newVal==30) {
			t2.setValue(0);
		}
		if(picker==t2) {
			if(oldVal==9 && newVal==0) {
				int t1Value = t1.getValue();
				if(t1Value<30) {
					t1.setValue(t1Value+1);
				}
			}
			if(oldVal==0 && newVal==9) {
				int t1Value = t1.getValue();
				if(t1Value>5) {
					t1.setValue(t1Value-1);
				}
			}
			if(t1.getValue()==30) {
				picker.setValue(0);
			}
		}
		if(picker==t3 && newVal==30) {
			t4.setValue(0);
		}
		if(picker==t4) {;
			if(oldVal==9 && newVal==0) {
				int t3Value = t3.getValue();
				if(t3Value<30) {
					t3.setValue(t3Value+1);
				}
			}
			if(oldVal==0 && newVal==9) {
				int t3Value = t3.getValue();
				if(t3Value>5) {
					t3.setValue(t3Value-1);
				}
			}
			if(t3.getValue()==30) {
				picker.setValue(0);
			}
		}
	}
}