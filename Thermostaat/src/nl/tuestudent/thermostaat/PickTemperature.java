package nl.tuestudent.thermostaat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class PickTemperature extends DialogFragment {
	
	static private 	String[] digits = {"0","1","2","3","4","5","6","7","8","9"};
	static private 	String[] tempRange = null;
	
	// I'm lazy
	private static void createTempRange() {
		tempRange = new String[25];
		for(int i = 5; i < 30; i++) {
			tempRange[i-5] = Integer.toString(i);
		}
	}
	
	public interface OnTemperatureSelected {
		void submitTemperature(String temperature);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final String settingTemperature = getArguments().getString("settingTemperature");
		
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    final View view = inflater.inflate(R.layout.dialog_pick_temperature, null);
	    
	    //be lazy
	    createTempRange();
	    
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
	    
	    NumberPicker t2 = (NumberPicker) view.findViewById(R.id.tempDigitNumberPicker);
	    t2.setMinValue(0);
	    t2.setMaxValue(9);
	    t2.setValue(Integer.parseInt(settingTemperature.substring(settingTemperature.indexOf(".")+1)));

	    /*//TODO make this work WHY IS IT NOT WORKING
		NumberPicker t = (NumberPicker) view.findViewById(R.id.tempNumberPicker);
		t.setMinValue(0);
		t.setMaxValue(14);
		t.setDisplayedValues(tempRange);
		t = (NumberPicker) view.findViewById(R.id.tempNumberPicker);
		t.setMinValue(0);
		t.setMaxValue(9);
		t.setDisplayedValues(digits);*/
		
		return builder.create();
	}
	
}
