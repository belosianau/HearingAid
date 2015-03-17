package ntou.cs.lab505.hearingaid.sound;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

import ntou.cs.lab505.hearingaid.device.Microphone;
import ntou.cs.lab505.hearingaid.device.Microphone2;
import ntou.cs.lab505.hearingaid.device.Speaker;
import ntou.cs.lab505.hearingaid.device.Speaker2;
import ntou.cs.lab505.hearingaid.sound.frequenceshift.FrequencyShift;

/**
 * Created by alan on 3/11/15.
 */
public class SoundService extends Service{

    // device state parameters
    private static boolean isPauseByHeadsetUnplug = false;
    private static boolean serviceState = false;
    // model data queues
    private LinkedBlockingQueue<short[]> microphoneQueue = new LinkedBlockingQueue<short[]>();  // queue that save
    private LinkedBlockingQueue<short[]> frequencyShiftQueue = new LinkedBlockingQueue<short[]>();
    // model objects
    private Microphone2 microphone;
    private FrequencyShift frequencyShift;
    private Speaker2 speaker;

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
        //serviceState = true;  // move to "onStartCommand()".
        // create microphone object
        microphone = new Microphone2();  // new microphone class
        // create frequency shift object
        frequencyShift = new FrequencyShift();
        // create speaker object
        if (SoundParameter.frequency == 8000) {
            speaker = new Speaker2();
        } else {
            speaker = new Speaker2(SoundParameter.frequency);
        }
        // 設定資料傳輸步驟
        microphone.setOutputQueue(microphoneQueue);
        frequencyShift.setInputDataQueue(microphoneQueue);
        frequencyShift.setOutputDataQueue(frequencyShiftQueue);
        speaker.setInputDataQueue(frequencyShiftQueue);

        //frequencyShift.setSoundParameters();

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
        // change service state
        serviceState = true;
        // start thread;
        microphone.threadStart();
        frequencyShift.threadStart();
        speaker.threadStart();

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * close service
     */
    @Override
    public void onDestroy() {
        serviceState = false;
        microphone.threadStop();
        frequencyShift.threadStop();
        speaker.threadStop();
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