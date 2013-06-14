package nl.tuestudent.thermostaat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.NumberPicker;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		NumberPicker temperatureDigits = (NumberPicker) findViewById(R.id.temperatureDigits);
		temperatureDigits.setMaxValue(30);
		temperatureDigits.setMinValue(5);
		temperatureDigits.setWrapSelectorWheel(false);
		temperatureDigits.setValue(22);
		
		String decimals[] = {",0", ",1", ",2", ",3", ",4", ",5", ",6", ",7", ",8", ",9"};
		NumberPicker temperatureDecimals = (NumberPicker) findViewById(R.id.temperatureDecimals);
		temperatureDecimals.setMaxValue(9);
		temperatureDecimals.setMinValue(0);
		temperatureDecimals.setWrapSelectorWheel(true);
		temperatureDecimals.setDisplayedValues(decimals);
	}
	
	public void btnChangeWeekProgram(View view) {
		Intent intent = new Intent(this, ChangeWeekprogram.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
