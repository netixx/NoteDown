package fr.enst.infsi351.notedown.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import fr.enst.infsi351.notedown.R;

/**
 * Created by francois on 13/04/15.
 */
public class RecentListAdapter extends BaseAdapter {


    private final Context context;
    private final List<RecentItem> values;


    public RecentListAdapter(Context context, List<RecentItem> values) {
        this.context = context;
        this.values = values;
    }


    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.recent_item, parent, false);
        } else {
            rowView = convertView;
        }
        TextView session = (TextView) rowView.findViewById(R.id.session);
        TextView date = (TextView) rowView.findViewById(R.id.date);

        session.setText(values.get(position).session);
        date.setText(DateFormat.getDateInstance().format(values.get(position).date));

        return rowView;
    }
}
