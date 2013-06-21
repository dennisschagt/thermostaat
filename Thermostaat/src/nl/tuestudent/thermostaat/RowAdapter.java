package nl.tuestudent.thermostaat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RowAdapter extends ArrayAdapter<String> {
  private final Activity context;
  private final String[] names;

  static class ViewHolder {
    public TextView text;
    public ImageView image;
  }

  public RowAdapter(Activity context, String[] names) {
    super(context, R.layout.rowlayout, names);
    this.context = context;
    this.names = names;
  }

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
    String s = names[position];
    holder.text.setText(s);

    return rowView;
  }
} 