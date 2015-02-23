package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import ntou.cs.lab505.hearingaid.R;
import ntou.cs.lab505.hearingaid.sqlite.DoSqlite;
import ntou.cs.lab505.hearingaid.sqlite.TableContract;

public class SettingSoundAddActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_sound_add);

        // ...

        SeekBar sb = (SeekBar) findViewById(R.id.setting_freq_band_seekBar);
        sb.setOnSeekBarChangeListener(this);
    }

    /**
     * Reload data to view from database
     */
    @Override
    public void onResume() {
        super.onResume();

        // ...
    }

    /**
     * save data to database
     */
    @Override
    public void onPause() {

        // ...

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
