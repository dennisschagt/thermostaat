package nl.tuestudent.thermostaat;

import nl.tuestudent.thermostaat.R.color;
import nl.tuestudent.thermostaat.data.DayProgram.ProgramSwitch;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RowAdapter extends ArrayAdapter<String> {
  private final Activity context;
  private final ProgramSwitch[] switches;

  static class ViewHolder {
    public TextView text;
    public ImageView image;
  }

  public RowAdapter(Activity context, ProgramSwitch[] switches, String[] names) {
    super(context, R.layout.rowlayout,  names);
    this.context = context;
    this.switches = switches;
  }

  @SuppressWarnings("deprecation")
@Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View rowView = convertView;
    if (rowView == null) {
      LayoutInflater inflater = context.getLayoutInflater();
      rowView = inflater.inflate(R.layout.rowlayout, null);
      ViewHolder viewHolder = new ViewHolder();
      viewHolder.text = (TextView) rowView.findViewById(R.id.label);
      viewHolder.image = (ImageView) rowView
          .findViewById(R.id.sunMoon);
      rowView.setTag(viewHolder);
    }

    ViewHolder holder = (ViewHolder) rowView.getTag();
    String s = String.format("%02d:%02d", switches[position].getHour(), switches[position].getMin());
    holder.text.setText(s);
    
    if(switches[position].getType().equals("day")) {
    	rowView.setBackgroundDrawable(rowView.getResources().getDrawable((R.drawable.background_day)));
    	holder.image.setImageResource(R.drawable.dag);
    } else {
    	rowView.setBackgroundDrawable(rowView.getResources().getDrawable((R.drawable.background_night)));
    	holder.image.setImageResource(R.drawable.nacht);
    	holder.text.setTextColor(ColorStateList.valueOf(0xFFF5F5F5));//0xF5F5F5 = WhiteSmoke
    }

    if(switches[position].getState().equals("off")) {
    	rowView.setBackgroundDrawable(rowView.getResources().getDrawable((R.drawable.background_disabled)));
    	holder.text.setTextColor(ColorStateList.valueOf(0xFFF5F5F5));//0xF5F5F5 = WhiteSmoke
    }
    
    return rowView;
  }
  
  
 @Override
 public void notifyDataSetChanged() {
	 //TODO make this quicksort
	 for(int i=0; i < switches.length ;i++) {
		 for(int x=i; x < switches.length; x++) {
			 if(switches[i].getHour() >= switches[x].getHour()
				&& switches[i].getMin() >= switches[x].getMin()) {
				 ProgramSwitch y = switches[i];
				 switches[i] = switches[x];
				 switches[x] = y;
			 }
		 }
	 }
	 for(ProgramSwitch s : switches) {
		 Log.d("asdf", "" + s.getHour() + ":"+ s.getMin());
	 }
	 super.notifyDataSetChanged();
 }
} 