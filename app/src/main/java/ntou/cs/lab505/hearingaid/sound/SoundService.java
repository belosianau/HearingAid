package ntou.cs.lab505.hearingaid.sound;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import ntou.cs.lab505.hearingaid.device.Microphone;
import ntou.cs.lab505.hearingaid.device.Speaker;

/**
 * Created by alan on 3/11/15.
 */
public class SoundService extends Service{

    private static boolean isPauseByHeadsetUnplug = false;
    private static boolean serviceState = false;

    private Microphone microphone;
    private Speaker speaker;

    public SoundService() {
        //
    }

    /**
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    /**
     * initial service
     * create service object
     */
    @Override
    public void onCreate() {
        // change service state
        serviceState = true;

        // crete object

        // create microphone object
        microphone = new Microphone();
        // create speaker object
        if (SoundParameter.frequency == 8000) {
            speaker = new Speaker();
        } else {
            speaker = new Speaker(SoundParameter.frequency);
        }
        //



        // set listener

        // microphone listener
        microphone.setOnMicrophoneListener(
                new Microphone.OnMicrophoneListener() {
                    @Override
                    public void OnRec(short[] data) {
                        speaker.AddSignals(data);
                    }
                }
        );



        super.onCreate();
    }

    /**
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // start thread;
        microphone.open();
        speaker.open();

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * close service
     */
    @Override
    public void onDestroy() {
        serviceState = false;
        microphone.close();
        speaker.close();
        super.onDestroy();
    }

    private static void setServiceState(boolean state) {
        serviceState = state;
    }

    public static boolean getServiceState() {
        return serviceState;
    }

    public static void setHeadsetUnPlugState(boolean state) {
        isPauseByHeadsetUnplug = state;
    }

    public static boolean pauseByHeadsetUnplug() {
        return isPauseByHeadsetUnplug;
    }



    /* call method to change db while service is not running?  */

    // test method

    /*
    public void writeDbDeviceState(int type, int state) {
        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());
        SQLiteDatabase db = sqliteEntry.getReadableDatabase();



    }

    public boolean getDbDeviceState(int type) {
        boolean state = false;

        return state;
    }
    */
}

 /*
  * If you use MediaRecorder (the example, above) it will save compressed audio to a file.
  * If you use AudioRecord, you can get audio samples directly.
  * Get the raw data
  *
  *
  *
  *
  */