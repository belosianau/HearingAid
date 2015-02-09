package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import ntou.cs.lab505.hearingaid.R;
import ntou.cs.lab505.hearingaid.sqlite.DoSqlite;
import ntou.cs.lab505.hearingaid.sqlite.TableContract;

public class SettingDeviceActivity extends Activity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_device);

        // load data from database.
        //loadData();
    }

    /**
     *
     * @param view
     */
    public void onRadioButtonClicked (View view) {
        boolean checked = ((RadioButton) view).isChecked();

        RadioButton b1 = (RadioButton) findViewById(R.id.setting_device_radioButton1);
        RadioButton b2 = (RadioButton) findViewById(R.id.setting_device_radioButton2);
        RadioButton b3 = (RadioButton) findViewById(R.id.setting_device_radioButton3);
        RadioButton b4 = (RadioButton) findViewById(R.id.setting_device_radioButton4);
        RadioButton b5 = (RadioButton) findViewById(R.id.setting_device_radioButton5);
        RadioButton b6 = (RadioButton) findViewById(R.id.setting_device_radioButton6);


        switch (view.getId()) {
            case R.id.setting_device_radioButton1:
                if (checked) {
                    b2.setChecked(false);
                    b3.setChecked(false);
                }
                break;
            case R.id.setting_device_radioButton2:
                if (checked) {
                    b1.setChecked(false);
                    b3.setChecked(false);
                }
                break;
            case R.id.setting_device_radioButton3:
                if (checked) {
                    b1.setChecked(false);
                    b2.setChecked(false);
                }
                break;
            case R.id.setting_device_radioButton4:
                if (checked) {
                    b5.setChecked(false);
                    b6.setChecked(false);
                }
                break;
            case R.id.setting_device_radioButton5:
                if (checked) {
                    b4.setChecked(false);
                    b6.setChecked(false);
                }
                break;
            case R.id.setting_device_radioButton6:
                if (checked) {
                    b4.setChecked(false);
                    b5.setChecked(false);
                }
                break;
            default:
                b1.setChecked(false);
                b2.setChecked(false);
                b3.setChecked(false);
                b4.setChecked(false);
                b5.setChecked(false);
                b6.setChecked(false);
                break;
        }
    }

    /**
     * Save device control parameters to database.
     */
    @Override
    public void onPause() {
        // get button value
        View view = new View(this);
        RadioButton b1 = (RadioButton) findViewById(R.id.setting_device_radioButton1);
        RadioButton b2 = (RadioButton) findViewById(R.id.setting_device_radioButton2);
        RadioButton b3 = (RadioButton) findViewById(R.id.setting_device_radioButton3);
        RadioButton b4 = (RadioButton) findViewById(R.id.setting_device_radioButton4);
        RadioButton b5 = (RadioButton) findViewById(R.id.setting_device_radioButton5);
        RadioButton b6 = (RadioButton) findViewById(R.id.setting_device_radioButton6);

        int buttonValue1 = -1, buttonValue2 = -1;

        if (b1.isChecked()) {
            buttonValue1 = 0;
        } else if (b2.isChecked()) {
            buttonValue1 = 1;
        } else if (b3.isChecked()) {
            buttonValue1 = 2;
        } else {
            buttonValue1 = -1;
        }

        if (b4.isChecked()) {
            buttonValue2 = 0;
        } else if (b5.isChecked()) {
            buttonValue2 = 1;
        } else if (b6.isChecked()) {
            buttonValue2 = 2;
        } else {
            buttonValue2 = -1;
        }


        // save the data to database.
        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());
        SQLiteDatabase db = sqliteEntry.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableContract.T_S_DEVICEINTYPE, buttonValue1);
        values.put(TableContract.T_S_DEVICEOUTTYPE, buttonValue2);

        String selection = TableContract.T_S_ALIAS + "= 'setting_001'";
        db.update(TableContract.TABLE_SETTING, values, selection, null);

        super.onPause();
    }

    /**
     * load data from database when activity start.
     */
    private void loadData() {
        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());
        SQLiteDatabase db = sqliteEntry.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT deviceInType, deviceOutType FROM setting WHERE alias='setting_001'", null);
        cursor.moveToFirst();

        int deviceIn = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TableContract.T_S_DEVICEINTYPE)));
        int deviceOut = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TableContract.T_S_DEVICEOUTTYPE)));

        if (deviceIn == 0) {
            RadioButton b = (RadioButton) findViewById(R.id.setting_device_radioButton1);
            b.setChecked(true);
        } else if (deviceIn == 1) {
            RadioButton b = (RadioButton) findViewById(R.id.setting_device_radioButton2);
            b.setChecked(true);
        } else if (deviceIn == 2) {
            RadioButton b = (RadioButton) findViewById(R.id.setting_device_radioButton3);
            b.setChecked(true);
        } else {
            //
        }

        if (deviceOut == 0) {
            RadioButton b = (RadioButton) findViewById(R.id.setting_device_radioButton4);
            b.setChecked(true);
        } else if (deviceOut == 1) {
            RadioButton b = (RadioButton) findViewById(R.id.setting_device_radioButton5);
            b.setChecked(true);
        } else if (deviceOut == 2) {
            RadioButton b = (RadioButton) findViewById(R.id.setting_device_radioButton6);
            b.setChecked(true);
        } else {
            //
        }
    }

    /**
     *
     */
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
