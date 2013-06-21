package nl.tuestudent.thermostaat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class PickTemperature extends DialogFragment implements NumberPicker.OnValueChangeListener {
	
	View view;
	
	public interface OnTemperatureSelected {
		void submitTemperature(String temperature);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String settingTemperature = getArguments().getString("settingTemperature");
		if(settingTemperature==null || settingTemperature=="") {
			settingTemperature = "15.0";
		}
		
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    final View view = inflater.inflate(R.layout.dialog_pick_temperature, null);
	    this.view = view;
	    
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view)
	    
	    // Add action buttons
	           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   // pass temperature to main activity
	            	   OnTemperatureSelected tempSubmitter = (OnTemperatureSelected) getActivity();
	            	   NumberPicker t1 = (NumberPicker) view.findViewById(R.id.tempNumberPicker);
	            	   NumberPicker t2 = (NumberPicker) view.findViewById(R.id.tempDigitNumberPicker);
	            	   String temperature = ((Integer)t1.getValue()).toString() + "." + ((Integer)t2.getValue()).toString();
	            	   tempSubmitter.submitTemperature(temperature);
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                  //do shit
	               }
	           });      
	    
	    NumberPicker t1 = (NumberPicker) view.findViewById(R.id.tempNumberPicker);
	    t1.setMinValue(5);
	    t1.setMaxValue(30);
	    t1.setValue(Integer.parseInt(settingTemperature.substring(0, settingTemperature.indexOf("."))));
	    t1.setWrapSelectorWheel(false);
	    t1.setOnValueChangedListener(this);
	    
	    NumberPicker t2 = (NumberPicker) view.findViewById(R.id.tempDigitNumberPicker);
	    t2.setMinValue(0);
	    t2.setMaxValue(9);
	    t2.setValue(Integer.parseInt(settingTemperature.substring(settingTemperature.indexOf(".")+1)));
	    t2.setOnValueChangedListener(this);
		
	    
		return builder.create();
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		NumberPicker t1 = (NumberPicker) view.findViewById(R.id.tempNumberPicker);
		NumberPicker t2 = (NumberPicker) view.findViewById(R.id.tempDigitNumberPicker);
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
	}
	
}
