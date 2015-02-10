package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import ntou.cs.lab505.hearingaid.R;

/**
 * Created by alan on 2/9/15.
 */
public class FilterViewAdapter extends ArrayAdapter<FilterView> {

    Context context;
    int layoutResourceId;
    FilterView data[] = null;

    public FilterViewAdapter(Context context, int layoutResourceId, FilterView[] data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        } else {
            //
        }

        FilterView filterView = data[position];
        EditText let = (EditText) convertView.findViewById(R.id.filter_view_lowBand);
        EditText het = (EditText) convertView.findViewById(R.id.filter_view_highBand);
        let.setText(String.valueOf(filterView.lowBand));
        het.setText(String.valueOf(filterView.highBand));

        return convertView;
    }
}
