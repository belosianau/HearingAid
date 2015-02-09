package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ntou.cs.lab505.hearingaid.R;
import ntou.cs.lab505.hearingaid.sqlite.DoSqlite;
import ntou.cs.lab505.hearingaid.sqlite.TableContract;

public class SettingActivity extends Activity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        loadData();
    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    /**
     *
     */
    private void loadData() {
        // check buttons attribute.
        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());
        SQLiteDatabase db = sqliteEntry.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT defaultMode FROM setting WHERE alias='setting_001'", null);
        cursor.moveToFirst();

        int defaultMode = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TableContract.T_S_DEFAULTMODE)));
        TextView tvfs = (TextView) findViewById(R.id.list_item_3);
        TextView tvfb = (TextView) findViewById(R.id.list_item_4);
        TextView tvsa = (TextView) findViewById(R.id.list_item_5);

        if (defaultMode == 3) {
            // enable buttons
            tvfs.setClickable(true);
            tvfb.setClickable(true);
            tvsa.setClickable(true);

            // draw color
            tvfs.setTextColor(Color.parseColor("#222222"));
            tvfb.setTextColor(Color.parseColor("#222222"));
            tvsa.setTextColor(Color.parseColor("#222222"));
        } else {
            // enable buttons
            tvfs.setClickable(false);
            tvfb.setClickable(false);
            tvsa.setClickable(false);

            // draw color
            tvfs.setTextColor(Color.parseColor("#9e9e9e"));
            tvfb.setTextColor(Color.parseColor("#9e9e9e"));
            tvsa.setTextColor(Color.parseColor("#9e9e9e"));
        }
    }

    /**
     *
     * @param view
     */
    public void hit_1(View view) {
        TextView tv = (TextView) findViewById(R.id.setting_show_info);
        Resources res = getResources();
        String hit = res.getString(R.string.setting_ques_1);
        tv.setText(hit);
    }

    /**
     *
     * @param view
     */
    public void hit_2(View view) {
        TextView tv = (TextView) findViewById(R.id.setting_show_info);
        Resources res = getResources();
        String hit = res.getString(R.string.setting_ques_2);
        tv.setText(hit);
    }

    /**
     *
     * @param view
     */
    public void hit_3(View view) {
        TextView tv = (TextView) findViewById(R.id.setting_show_info);
        Resources res = getResources();
        String hit = res.getString(R.string.setting_ques_3);
        tv.setText(hit);
    }

    /**
     *
     * @param view
     */
    public void hit_4(View view) {
        TextView tv = (TextView) findViewById(R.id.setting_show_info);
        Resources res = getResources();
        String hit = res.getString(R.string.setting_ques_4);
        tv.setText(hit);
    }

    /**
     *
     * @param view
     */
    public void hit_5(View view) {
        TextView tv = (TextView) findViewById(R.id.setting_show_info);
        Resources res = getResources();
        String hit = res.getString(R.string.setting_ques_5);
        tv.setText(hit);
    }

    /**
     *
     * @param view
     */
    public void funt_1(View view) {
        Intent intent = new Intent(this, SettingDeviceActivity.class);
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void funt_2(View view) {
        Intent intent = new Intent(this, SettingDefaultModeActivity.class);
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void funt_3(View view) {
        Intent intent = new Intent(this, SettingFreqShiftActivity.class);
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void funt_4(View view) {
        Intent intent = new Intent(this, SettingFreqBandActivity.class);
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void funt_5(View view) {
        Intent intent = new Intent(this, SettingSoundAddActivity.class);
        startActivity(intent);
    }
}
