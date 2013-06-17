package nl.tuestudent.thermostaat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class LazyAdapter extends ArrayAdapter<String>{
	HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	public LazyAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
		super(context,R.layout.row_change_day, R.id.label, objects);
		
		for (int i = 0; i < objects.size(); ++i) {
			mIdMap.put(objects.get(i), i);
		}
	}

	@Override
	public long getItemId(int position) {
		String item = getItem(position);
		return mIdMap.get(item);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

}

