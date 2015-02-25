package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import ntou.cs.lab505.hearingaid.R;
import ntou.cs.lab505.hearingaid.sqlite.DoSqlite;
import ntou.cs.lab505.hearingaid.sqlite.TableContract;

public class SettingSoundAddActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar[] sbl40;
    private SeekBar[] sbl60;
    private SeekBar[] sbl80;
    private SeekBar[] sbr40;
    private SeekBar[] sbr60;
    private SeekBar[] sbr80;


     /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_sound_add);

        loadData();
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

        LinearLayout border = (LinearLayout) findViewById(R.id.setting_sound_add_draw);
        int itemCount = border.getChildCount();
        View view;  // used to reference gain_view layout
        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());
        SQLiteDatabase db = sqliteEntry.getWritableDatabase();
        ContentValues values;  // used to save data to database.
        TextView l40, l60, l80, r40, r60, r80;  // get data from textview.  get data from seekbar is "多此一舉".


        // delete old data
        String[] selectionArgs = {"22"};  // self definite number.
        db.delete(TableContract.TABLE_SOUND_ADD_MODEL, TableContract.T_SAM_GROUPID + " =?", selectionArgs);


        // insert new data
        for (int count = 0; count < itemCount; count++) {
            // values from view fields.
            view = border.getChildAt(count);
            l40 = (TextView) view.findViewById(R.id.gain_view_num_l40);
            l60 = (TextView) view.findViewById(R.id.gain_view_num_l60);
            l80 = (TextView) view.findViewById(R.id.gain_view_num_l80);
            r40 = (TextView) view.findViewById(R.id.gain_view_num_r40);
            r60 = (TextView) view.findViewById(R.id.gain_view_num_r60);
            r80 = (TextView) view.findViewById(R.id.gain_view_num_r80);

            // generate object to save values.
            values = new ContentValues();

            // save values to database.
            values.put(TableContract.T_SAM_USERID, 1);
            values.put(TableContract.T_SAM_GROUPID, 22);
            values.put(TableContract.T_SAM_STATE, 1);

            if (l40.getText().toString().trim().length() == 0) {
                values.put(TableContract.T_SAM_L40, 0);
            } else {
                values.put(TableContract.T_SAM_L40, l40.getText().toString());
            }
            if (l60.getText().toString().trim().length() == 0) {
                values.put(TableContract.T_SAM_L60, 0);
            } else {
                values.put(TableContract.T_SAM_L60, l60.getText().toString());
            }
            if (l80.getText().toString().trim().length() == 0) {
                values.put(TableContract.T_SAM_L80, 0);
            } else {
                values.put(TableContract.T_SAM_L80, l80.getText().toString());
            }
            if (r40.getText().toString().trim().length() == 0) {
                values.put(TableContract.T_SAM_R40, 0);
            } else {
                values.put(TableContract.T_SAM_R40, r40.getText().toString());
            }
            if (r60.getText().toString().trim().length() == 0) {
                values.put(TableContract.T_SAM_R60, 0);
            } else {
                values.put(TableContract.T_SAM_R60, r60.getText().toString());
            }
            if (r80.getText().toString().trim().length() == 0) {
                values.put(TableContract.T_SAM_R80, 0);
            } else {
                values.put(TableContract.T_SAM_R80, r80.getText().toString());
            }

            // write data to db.
            db.insert(TableContract.TABLE_SOUND_ADD_MODEL, null, values);
        }


        // onPause method.
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

        // count views number
        LinearLayout border = (LinearLayout) findViewById(R.id.setting_sound_add_draw);
        int viewCount = border.getChildCount();
        View childView;
        SeekBar sb;
        TextView tv;


        for (int i = 0; i < viewCount; i++) {
            // get child view
            childView = border.getChildAt(i);

            // change db value on layout
            if (seekBar.equals(sbl40[i])) {
                sb = (SeekBar) childView.findViewById(R.id.gain_view_seekBar_l40);
                tv = (TextView) childView.findViewById(R.id.gain_view_num_l40);
                tv.setText(sb.getProgress() - 60 + "");
            } else if (seekBar.equals(sbl60[i])) {
                sb = (SeekBar) childView.findViewById(R.id.gain_view_seekBar_l60);
                tv = (TextView) childView.findViewById(R.id.gain_view_num_l60);
                tv.setText(sb.getProgress() - 60 + "");
            } else if (seekBar.equals(sbl80[i])) {
                sb = (SeekBar) childView.findViewById(R.id.gain_view_seekBar_l80);
                tv = (TextView) childView.findViewById(R.id.gain_view_num_l80);
                tv.setText(sb.getProgress() - 60 + "");
            } else if (seekBar.equals(sbr40[i])) {
                sb = (SeekBar) childView.findViewById(R.id.gain_view_seekBar_r40);
                tv = (TextView) childView.findViewById(R.id.gain_view_num_r40);
                tv.setText(sb.getProgress() - 60 + "");
            } else if (seekBar.equals(sbr60[i])) {
                sb = (SeekBar) childView.findViewById(R.id.gain_view_seekBar_r60);
                tv = (TextView) childView.findViewById(R.id.gain_view_num_r60);
                tv.setText(sb.getProgress() - 60 + "");
            } else if (seekBar.equals(sbr80[i])) {
                sb = (SeekBar) childView.findViewById(R.id.gain_view_seekBar_r80);
                tv = (TextView) childView.findViewById(R.id.gain_view_num_r80);
                tv.setText(sb.getProgress() - 60 + "");
            } else {
                //
            }
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

    private void loadData() {

        // connect sqlite
        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());
        SQLiteDatabase db = sqliteEntry.getWritableDatabase();

        // count data at 'freq_band_model' table
        Cursor c = db.rawQuery("SELECT " + TableContract.T_FBM_USERID + " FROM " + TableContract.TABLE_FREQ_BAND_MODEL, null);
        int FBMNums = c.getCount();

        // count data at 'sound_add_model' table
        c = db.rawQuery("SELECT " + TableContract.T_SAM_USERID + " FROM " + TableContract.TABLE_SOUND_ADD_MODEL, null);
        int SAMNums = c.getCount();


        // create array save data
        int[] L40 = new int[FBMNums];
        int[] L60 = new int[FBMNums];
        int[] L80 = new int[FBMNums];
        int[] R40 = new int[FBMNums];
        int[] R60 = new int[FBMNums];
        int[] R80 = new int[FBMNums];

        // initial seekbar
        sbl40 = new SeekBar[FBMNums];
        sbl60 = new SeekBar[FBMNums];
        sbl80 = new SeekBar[FBMNums];
        sbr40 = new SeekBar[FBMNums];
        sbr60 = new SeekBar[FBMNums];
        sbr80 = new SeekBar[FBMNums];


        // check data record
        if (FBMNums == SAMNums) { // load old data
            String command = "SELECT " + TableContract.T_SAM_L40 + " , " +
                    TableContract.T_SAM_L60 + " , " +
                    TableContract.T_SAM_L80 + " , " +
                    TableContract.T_SAM_R40 + " , " +
                    TableContract.T_SAM_R60 + " , " +
                    TableContract.T_SAM_R80 +
                    " FROM " + TableContract.TABLE_SOUND_ADD_MODEL +
                    " WHERE " + TableContract.T_SAM_GROUPID + " = ?";
            String [] selectArgs = {"22"};
            c = db.rawQuery(command, selectArgs);

            // save data to arrays
            c.moveToFirst();
            int count = 0;
            while (!c.isAfterLast()) {
                L40[count] = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_SAM_L40)));
                L60[count] = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_SAM_L60)));
                L80[count] = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_SAM_L80)));
                R40[count] = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_SAM_R40)));
                R60[count] = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_SAM_R60)));
                R80[count] = Integer.parseInt(c.getString(c.getColumnIndex(TableContract.T_SAM_R80)));
                c.moveToNext();
                count++;
            }
        } else {  // clear all data
            String [] selectArgs = {"22"};
            db.delete(TableContract.TABLE_SOUND_ADD_MODEL, TableContract.T_SAM_GROUPID + " = ?" , selectArgs);
        }


        // initial layout
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout border = (LinearLayout) findViewById(R.id.setting_sound_add_draw);
        border.removeAllViews();  // clear border

        // generate layout and insert data to field
        ArrayList<View> views = new ArrayList<View>(FBMNums);

        for (int i = 0; i < FBMNums; i++) {
            View view = layoutInflater.inflate(R.layout.gain_view, null);

            // change label
            TextView viewLabel = (TextView) view.findViewById(R.id.gain_view_title);
            viewLabel.setText(String.valueOf("頻帶" + (i + 1)));

            // handle gain_view layout
            if (SAMNums > 0) {  // reload data to layout
                // handle seekbar
                sbl40[i] = (SeekBar) view.findViewById(R.id.gain_view_seekBar_l40);
                sbl40[i].setOnSeekBarChangeListener(this);
                sbl40[i].setProgress(L40[i] + 60);
                // handle textview
                TextView l40tv = (TextView) view.findViewById(R.id.gain_view_num_l40);
                l40tv.setText(L40[i] + "");

                // handle seekbar
                sbl60[i] = (SeekBar) view.findViewById(R.id.gain_view_seekBar_l60);
                sbl60[i].setOnSeekBarChangeListener(this);
                sbl60[i].setProgress(L60[i] + 60);
                // handle textview
                TextView l60tv = (TextView) view.findViewById(R.id.gain_view_num_l60);
                l60tv.setText(L60[i] + "");

                // handle seekbar
                sbl80[i] = (SeekBar) view.findViewById(R.id.gain_view_seekBar_l80);
                sbl80[i].setOnSeekBarChangeListener(this);
                sbl80[i].setProgress(L80[i] + 60);
                // handle textview
                TextView l80tv = (TextView) view.findViewById(R.id.gain_view_num_l80);
                l80tv.setText(L80[i] + "");

                // handle seekbar
                sbr40[i] = (SeekBar) view.findViewById(R.id.gain_view_seekBar_r40);
                sbr40[i].setOnSeekBarChangeListener(this);
                sbr40[i].setProgress(R40[i] + 60);
                // handle textview
                TextView r40tv = (TextView) view.findViewById(R.id.gain_view_num_r40);
                r40tv.setText(R40[i] + "");

                // handle seekbar
                sbr60[i] = (SeekBar) view.findViewById(R.id.gain_view_seekBar_r60);
                sbr60[i].setOnSeekBarChangeListener(this);
                sbr60[i].setProgress(R60[i] + 60);
                // handle textview
                TextView r60tv = (TextView) view.findViewById(R.id.gain_view_num_r60);
                r60tv.setText(R60[i] + "");

                // handle seekbar
                sbr80[i] = (SeekBar) view.findViewById(R.id.gain_view_seekBar_r80);
                sbr80[i].setOnSeekBarChangeListener(this);
                sbr80[i].setProgress(R80[i] + 60);
                // handle textview
                TextView r80tv = (TextView) view.findViewById(R.id.gain_view_num_r80);
                r80tv.setText(R80[i] + "");
            } else {  // empty layout
                sbl40[i] = (SeekBar) view.findViewById(R.id.gain_view_seekBar_l40);
                sbl40[i].setOnSeekBarChangeListener(this);
                sbl60[i] = (SeekBar) view.findViewById(R.id.gain_view_seekBar_l60);
                sbl60[i].setOnSeekBarChangeListener(this);
                sbl80[i] = (SeekBar) view.findViewById(R.id.gain_view_seekBar_l80);
                sbl80[i].setOnSeekBarChangeListener(this);
                sbr40[i] = (SeekBar) view.findViewById(R.id.gain_view_seekBar_r40);
                sbr40[i].setOnSeekBarChangeListener(this);
                sbr60[i] = (SeekBar) view.findViewById(R.id.gain_view_seekBar_r60);
                sbr60[i].setOnSeekBarChangeListener(this);
                sbr80[i] = (SeekBar) view.findViewById(R.id.gain_view_seekBar_r80);
                sbr80[i].setOnSeekBarChangeListener(this);
            }

            // collect views to array
            views.add(view);
        }

        // put view to activity
        for (int i = 0; i < views.size(); i++) {
            border.addView(views.get(i));
        }
    }

    /**
     *
     * @param message
     */
    private void alertMessage(String message) {
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
