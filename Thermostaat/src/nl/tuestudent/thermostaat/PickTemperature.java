package nl.tuestudent.thermostaat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

public class PickTemperature extends DialogFragment {
	
	static private 	String[] digits = {"0","1","2","3","4","5","6","7","8","9"};
	static private 	String[] tempRange = null;
	
	// I'm lazy
	private static void createTempRange() {
		tempRange = new String[15];
		for(int i = 15; i <30; i++) {
			tempRange[i-15] = Integer.toString(i);
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.dialog_pick_temperature, null);
	    
	    //be lazy
	    createTempRange();
	    
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.dialog_pick_temperature, null))
	    
	    // Add action buttons
	           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   // pass temp to main activity and set it etc
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                  //do shit
	               }
	           });      
	    

	    //TODO make this work WHY IS IT NOT WORKING
		NumberPicker t = (NumberPicker) view.findViewById(R.id.tempNumberPicker);
		t.setMinValue(0);
		t.setMaxValue(14);
		t.setDisplayedValues(tempRange);
		t = (NumberPicker) view.findViewById(R.id.tempNumberPicker);
		t.setMinValue(0);
		t.setMaxValue(9);
		t.setDisplayedValues(digits);
		
		return builder.create();
	}
	
}
