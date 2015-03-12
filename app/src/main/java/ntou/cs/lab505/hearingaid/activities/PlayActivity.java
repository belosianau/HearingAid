package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import ntou.cs.lab505.hearingaid.R;
import ntou.cs.lab505.hearingaid.device.DeviceManager;
import ntou.cs.lab505.hearingaid.device.HeadsetPlugReceiver;
import ntou.cs.lab505.hearingaid.sound.SoundParameter;
import ntou.cs.lab505.hearingaid.sound.SoundService;
import ntou.cs.lab505.hearingaid.sqlite.DoSqlite;
import ntou.cs.lab505.hearingaid.sqlite.TableContract;

public class PlayActivity extends Activity {

    private AudioManager audioManager;
    private DeviceManager deviceManager;
    private HeadsetPlugReceiver headsetPlugReceiver;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(headsetPlugReceiver);
    }

    /**
     *
     * @param view
     */
    public void playSoundFunction (View view)
    {
        if (SoundService.getServiceState() == false) {
            DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());
            SQLiteDatabase db = sqliteEntry.getWritableDatabase();

            Cursor cursor = db.rawQuery("SELECT deviceInType, deviceOutType" +
                    " FROM setting WHERE alias='setting_001'", null);
            cursor.moveToFirst();

            int deviceIn = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TableContract.T_S_DEVICEINTYPE)));
            int deviceOut = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TableContract.T_S_DEVICEOUTTYPE)));
            //Toast.makeText(this, deviceIn + "_" + deviceOut, 5).show();


            // check device state.  set device broadcast.
            headsetPlugReceiver = new HeadsetPlugReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.HEADSET_PLUG");
            intentFilter.addAction("android.bluetooth.headset.action.STATE_CHANGED");
            registerReceiver(headsetPlugReceiver, intentFilter);


            audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            deviceManager = new DeviceManager(audioManager);
            /*
            if (deviceManager.getDeviceState(0) == true) {
                Toast.makeText(this, "default OK", 5).show();
            } else {
                Toast.makeText(this, "default not OK", 5).show();
            }

            if (deviceManager.getDeviceState(1) == true) {
                Toast.makeText(this, "bluetooth OK", 5).show();
            } else {
                Toast.makeText(this, "bluetooth not OK", 5).show();
            }
            */


            // check state
            if (deviceManager.getDeviceState(deviceIn) == false) {
                if (deviceIn == 0) {
                    Toast.makeText(getApplicationContext(), "內建麥克風無法使用", Toast.LENGTH_LONG).show();
                } else if (deviceIn == 1) {
                    Toast.makeText(getApplicationContext(), "藍芽裝置無法使用", Toast.LENGTH_LONG).show();
                } else {
                    //
                }

                // ************************************************************************
                return;  // stop program.  please check device state and system parameters.
            }


            audioManager.setMode(AudioManager.MODE_NORMAL);

            if (deviceIn == 0) {  // use default mic
                SoundParameter.frequency = 16000;
            } else if (deviceIn == 1) {  // use bluetooth mic
                SoundParameter.frequency = 8000;
            } else {  // waiting!
                //
            }


            // change activity image
            ImageView img= (ImageView) findViewById(R.id.play_sound_bottom);
            img.setImageResource(R.drawable.ic_pause_orange);


            // start sound process
            Intent service = new Intent(PlayActivity.this, SoundService.class);
            startService(service);


        } else {
            ImageView img= (ImageView) findViewById(R.id.play_sound_bottom);
            img.setImageResource(R.drawable.ic_play_orange);

            Intent service = new Intent(PlayActivity.this, SoundService.class);
            stopService(service);
        }
    }

    /**
     *
     * @param view
     */
    public void soundAddFunction (View view) {

    }

    /**
     *
     * @param view
     */
    public void soundMinFunction (View view) {

    }

    /**
     *
     * @param view
     */
    public void soundSettingFunction (View view) {

    }
}
