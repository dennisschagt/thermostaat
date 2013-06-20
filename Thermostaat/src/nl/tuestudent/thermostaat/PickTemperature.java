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
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    final View view = inflater.inflate(R.layout.dialog_pick_temperature, null);
	    
	    
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view)
	    
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
	    
	    NumberPicker t1 = (NumberPicker) view.findViewById(R.id.tempNumberPicker);
		t1.setMinValue(15);
		t1.setMaxValue(30);
	    NumberPicker t2 = (NumberPicker) view.findViewById(R.id.tempDigitNumberPicker);
		t2.setMinValue(0);
		t2.setMaxValue(9);
		
		return builder.create();
	}
	
}
