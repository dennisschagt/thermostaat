package nl.tuestudent.thermostaat;

import nl.tuestudent.thermostaat.data.DayProgram.ProgramSwitch;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.Switch;
import android.widget.TimePicker;

public class PickDayNight extends DialogFragment {
	
	public interface PickDayNightFinishListener {
		public void onFinish();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    Bundle args = getArguments();
	    int position = args.getInt("item_position");
	    String dayName = args.getString("day_name");
	    
	    final ProgramSwitch ps = MainActivity.weekProg.findDayProgram(dayName).getSwitches()[position];

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    View view = inflater.inflate(R.layout.dialog_pick_day_night, null);
	    
	    final TimePicker tp = (TimePicker)view.findViewById(R.id.timePicker2);
	    
	    tp.setIs24HourView(true);
	    tp.setCurrentHour(ps.getHour());
	    tp.setCurrentMinute(ps.getMin());
	    
	    final Switch s = (Switch)view.findViewById(R.id.switch1);
	    s.setChecked(ps.getState().equals("on"));
	    
	    builder.setView(view)
	    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                ps.setHour(tp.getCurrentHour());
                ps.setMin(tp.getCurrentMinute());
                ps.setState(s.isChecked()? "on" : "off");
                ((PickDayNightFinishListener)getActivity()).onFinish();
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	// do nothing
            }
        });  
	    return builder.create();
	}
	
}
