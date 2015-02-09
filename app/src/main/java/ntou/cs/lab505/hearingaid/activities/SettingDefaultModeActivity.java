package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import ntou.cs.lab505.hearingaid.R;
import ntou.cs.lab505.hearingaid.sqlite.DoSqlite;
import ntou.cs.lab505.hearingaid.sqlite.TableContract;

public class SettingDefaultModeActivity extends Activity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_default_mode);
    }

    /**
     *
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        RadioButton b1 = (RadioButton) findViewById(R.id.setting_default_mode_button_1);
        RadioButton b2 = (RadioButton) findViewById(R.id.setting_default_mode_button_2);
        RadioButton b3 = (RadioButton) findViewById(R.id.setting_default_mode_button_3);
        RadioButton b4 = (RadioButton) findViewById(R.id.setting_default_mode_button_4);

        switch (view.getId()) {
            case R.id.setting_default_mode_button_1:
                if (b1.isChecked()) {
                    b2.setChecked(false);
                    b3.setChecked(false);
                    b4.setChecked(false);
                }
                break;
            case R.id.setting_default_mode_button_2:
                if (b2.isChecked()) {
                    b1.setChecked(false);
                    b3.setChecked(false);
                    b4.setChecked(false);
                }
                break;
            case R.id.setting_default_mode_button_3:
                if (b3.isChecked()) {
                    b1.setChecked(false);
                    b2.setChecked(false);
                    b4.setChecked(false);
                }
                break;
            case R.id.setting_default_mode_button_4:
                if (b4.isChecked()) {
                    b1.setChecked(false);
                    b2.setChecked(false);
                    b3.setChecked(false);
                }
                break;
            default:
                b1.setChecked(false);
                b2.setChecked(false);
                b3.setChecked(false);
                b4.setChecked(false);
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
        RadioButton b1 = (RadioButton) findViewById(R.id.setting_default_mode_button_1);
        RadioButton b2 = (RadioButton) findViewById(R.id.setting_default_mode_button_2);
        RadioButton b3 = (RadioButton) findViewById(R.id.setting_default_mode_button_3);
        RadioButton b4 = (RadioButton) findViewById(R.id.setting_default_mode_button_4);

        int buttonValue = -1;

        if (b1.isChecked()) {
            buttonValue = 0;
        } else if (b2.isChecked()) {
            buttonValue = 1;
        } else if (b3.isChecked()) {
            buttonValue = 2;
        } else if (b4.isChecked()) {
            buttonValue = 3;
        } else {
            buttonValue = -1;
        }


        // save the data to database
        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());
        SQLiteDatabase db = sqliteEntry.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableContract.T_S_DEFAULTMODE, buttonValue);

        String selection = TableContract.T_S_ALIAS + "= 'setting_001'";
        db.update(TableContract.TABLE_SETTING, values, selection, null);


        super.onPause();
    }

    /**
     *
     */
    private void loadData() {
        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());
        SQLiteDatabase db = sqliteEntry.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT defaultMode FROM setting WHERE alias='setting_001'", null);
        cursor.moveToFirst();

        int defaultMode = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TableContract.T_S_DEFAULTMODE)));

        if (defaultMode == 0) {
            RadioButton b = (RadioButton) findViewById(R.id.setting_default_mode_button_1);
            b.setChecked(true);
        } else if (defaultMode == 1) {
            RadioButton b = (RadioButton) findViewById(R.id.setting_default_mode_button_2);
            b.setChecked(true);
        } else if (defaultMode == 2) {
            RadioButton b = (RadioButton) findViewById(R.id.setting_default_mode_button_3);
            b.setChecked(true);
        } else if (defaultMode == 3) {
            RadioButton b = (RadioButton) findViewById(R.id.setting_default_mode_button_4);
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
