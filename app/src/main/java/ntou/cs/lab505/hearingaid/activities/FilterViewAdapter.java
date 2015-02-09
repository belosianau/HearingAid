package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.logging.Filter;

import ntou.cs.lab505.hearingaid.R;

/**
 * Created by alan on 2/9/15.
 */
public class FilterViewAdapter extends ArrayAdapter<FilterView> {

    public Context context;
    public int layoutResourceId;
    public FilterView data[] = null;

    public FilterViewAdapter(Context context, int layoutResourceId, FilterView[] data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FilterViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new FilterViewHolder();
            holder.lowBand = (EditText) row.findViewById(R.id.filter_view_lowBand);
            holder.highBand = (EditText) row.findViewById(R.id.filter_view_highBand);

            row.setTag(holder);
        } else {
            holder = (FilterViewHolder) row.getTag();
        }

        FilterView filterView = data[position];
        holder.lowBand.setText(filterView.lowBand);
        holder.highBand.setText(filterView.highBand);

        return row;
    }

    static class FilterViewHolder {
        EditText lowBand;
        EditText highBand;
    }

}
