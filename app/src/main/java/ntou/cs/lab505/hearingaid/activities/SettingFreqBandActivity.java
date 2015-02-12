package ntou.cs.lab505.hearingaid.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import ntou.cs.lab505.hearingaid.R;
import ntou.cs.lab505.hearingaid.sqlite.DoSqlite;
import ntou.cs.lab505.hearingaid.sqlite.TableContract;

public class SettingFreqBandActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    //private int bandNumber;

    /**
     * Initial activity.
     * If it had be set parameters, reload data from database.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_freq_band);

        loadData();

        SeekBar sb = (SeekBar) findViewById(R.id.setting_freq_band_seekBar);
        sb.setOnSeekBarChangeListener(this);
    }

    /**
     * Reload data to view from database
     */
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * save data to database
     */
    @Override
    public void onPause() {

        LinearLayout ll = (LinearLayout) findViewById(R.id.setting_freq_band_draw);
        int itemCount = ll.getChildCount();
        View v;  // used to reference item view
        EditText etlb, ethb;  // used to reference lowband and highband input field.
        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());
        SQLiteDatabase db = sqliteEntry.getWritableDatabase();
        ContentValues values;  // used to save data to database.

        // delete old data
        String[] selectionArgs = {"22"};
        db.delete(TableContract.TABLE_FREQ_BAND_MODEL, TableContract.T_FBM_GROUPID + " = ?", selectionArgs);

        // insert new data
        for (int count = 0; count < itemCount; count++) {
            // get values from view fields.
            v = ll.getChildAt(count);
            etlb = (EditText) v.findViewById(R.id.filter_view_lowBand);
            ethb = (EditText) v.findViewById(R.id.filter_view_highBand);
            // generate object to save values.
            values = new ContentValues();
            // save values to database.
            values.put(TableContract.T_FBM_USERID, 1);
            values.put(TableContract.T_FBM_GROUPID, 22);
            if (etlb.getText().toString().trim().length() == 0) {
                values.put(TableContract.T_FBM_LOWBAND, 0);
            } else {
                values.put(TableContract.T_FBM_LOWBAND, etlb.getText().toString());
            }
            if (ethb.getText().toString().trim().length() == 0) {
                values.put(TableContract.T_FBM_HIGHBAND, 0);
            } else {
                values.put(TableContract.T_FBM_HIGHBAND, ethb.getText().toString());
            }

            values.put(TableContract.T_FBM_STATE, 1);
            db.insert(TableContract.TABLE_FREQ_BAND_MODEL, null, values);
        }

        // check parameters



        super.onPause();
    }

    /**
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        // change text
        TextView tv = (TextView) findViewById(R.id.setting_freq_band_show_text_2);
        tv.setText(String.valueOf(progress));
        int bandNumber = progress;
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout border = (LinearLayout) findViewById(R.id.setting_freq_band_draw);
        ArrayList<View> views = new ArrayList<View>(progress);

        // clear view
        border.removeAllViews();

        // add view
        for (int count = 0; count < progress; count++) {
            View view = layoutInflater.inflate(R.layout.filter_view, null);
            TextView viewLabel = (TextView) view.findViewById(R.id.filter_view_text1);
            viewLabel.setText(String.valueOf("頻帶" + ( count + 1)));
            views.add(view);
        }

        for (int count = 0; count < views.size(); count++) {
            border.addView(views.get(count));
        }
    }

    /**
     *
     * @param seekBar
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //
    }

    /**
     *
     * @param seekBar
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //
    }

    /**
     * select data from database.
     * generate layout for displaying input field.
     */
    public void loadData() {

        // load parameters from database.
        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());
        SQLiteDatabase db = sqliteEntry.getReadableDatabase();
        String selectCommand = "SELECT " + TableContract.T_FBM_LOWBAND + " , " + TableContract.T_FBM_HIGHBAND +
                               " FROM " + TableContract.TABLE_FREQ_BAND_MODEL +
                               " WHERE " + TableContract.T_FBM_GROUPID + " = ?";
        String [] selectArgs = {"22"};
        Cursor cursor = db.rawQuery(selectCommand, selectArgs);
        //alertMessage(String.valueOf(cursor.getCount()));

        // select data from database.
        int bandNumber = 0;
        int[] lowBands = new int[cursor.getCount()];
        int[] highBands = new int[cursor.getCount()];

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            lowBands[bandNumber] = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TableContract.T_FBM_LOWBAND)));
            highBands[bandNumber] = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TableContract.T_FBM_HIGHBAND)));
            cursor.moveToNext();
            bandNumber++;
        }

        // initial layout
        TextView tv = (TextView) findViewById(R.id.setting_freq_band_show_text_2);
        tv.setText(String.valueOf(bandNumber));

        SeekBar sb = (SeekBar) findViewById(R.id.setting_freq_band_seekBar);
        sb.setProgress(bandNumber);

        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout border = (LinearLayout) findViewById(R.id.setting_freq_band_draw);
        border.removeAllViews();

        // generate layout and insert data to field
        ArrayList<View> views = new ArrayList<View>(bandNumber);

        for (int count = 0; count < bandNumber; count++) {
            View view = layoutInflater.inflate(R.layout.filter_view, null);
            TextView viewLabel = (TextView) view.findViewById(R.id.filter_view_text1);
            viewLabel.setText(String.valueOf("頻帶" + ( count + 1)));
            EditText etlb = (EditText) view.findViewById(R.id.filter_view_lowBand);
            etlb.setText(String.valueOf(lowBands[count]));
            EditText ethb = (EditText) view.findViewById(R.id.filter_view_highBand);
            ethb.setText(String.valueOf(highBands[count]));
            views.add(view);
        }

        for (int count = 0; count < views.size(); count++) {
            border.addView(views.get(count));
        }

    }

    /**
     *
     * @param message
     */
    public void alertMessage(String message) {
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}