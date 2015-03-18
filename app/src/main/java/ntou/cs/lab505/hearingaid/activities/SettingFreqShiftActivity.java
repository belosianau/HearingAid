package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import ntou.cs.lab505.hearingaid.R;
import ntou.cs.lab505.hearingaid.sqlite.DoSqlite;
import ntou.cs.lab505.hearingaid.sqlite.TableContract;

public class SettingFreqShiftActivity extends Activity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_freq_shift);
    }

    /**
     *
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        RadioButton b1 = (RadioButton) findViewById(R.id.setting_freq_shift_button_1);
        RadioButton b2 = (RadioButton) findViewById(R.id.setting_freq_shift_button_2);

        switch (view.getId()) {
            case R.id.setting_freq_shift_button_1:
                if (b1.isChecked()) {
                    b2.setChecked(false);
                }
                break;
            case R.id.setting_freq_shift_button_2:
                if (b2.isChecked()) {
                    b1.setChecked(false);
                }
                break;
            default:
                b1.setChecked(false);
                b2.setChecked(false);
                break;
        }
    }

    /**
     * Save frequency shift parameter to database
     */
    @Override
    public void onPause() {
        View view = new View(this);
        RadioButton b1 = (RadioButton) findViewById(R.id.setting_freq_shift_button_1);
        RadioButton b2 = (RadioButton) findViewById(R.id.setting_freq_shift_button_2);

        int buttonValue = -1;

        if (b1.isChecked()) {
            buttonValue = 0;
        } else if (b2.isChecked()) {
            buttonValue = 1;
        } else {
            buttonValue = -1;
        }

        // create db instance
        DoSqlite sqliteEntry = new DoSqlite((this.getApplicationContext()));
        SQLiteDatabase db = sqliteEntry.getWritableDatabase();


        // delete old data
        String[] selectionArgs = {"22"};
        db.delete(TableContract.TABLE_FREQ_SHIFT_MODEL, TableContract.T_FSM_GROUPID + " = ?", selectionArgs);


        // save the data to database
        ContentValues values = new ContentValues();
        values.put(TableContract.T_FSM_USERID, 1);
        values.put(TableContract.T_FSM_GROUPID, 22);
        values.put(TableContract.T_FSM_SHIFTPARA, buttonValue);
        values.put(TableContract.T_FSM_STATE, 1);

        db.insert(TableContract.TABLE_FREQ_SHIFT_MODEL, null, values);


        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();


        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());
        SQLiteDatabase db = sqliteEntry.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT shiftPara FROM freq_shift_model WHERE groupId='22'", null);
        cursor.moveToFirst();

        int buttonValue = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TableContract.T_FSM_SHIFTPARA)));

        if (buttonValue == 0) {
            RadioButton b = (RadioButton) findViewById(R.id.setting_freq_shift_button_1);
            b.setChecked(true);
        } else if (buttonValue == 1) {
            RadioButton b = (RadioButton) findViewById(R.id.setting_freq_shift_button_2);
            b.setChecked(true);
        } else {
            //
        }
    }
}
