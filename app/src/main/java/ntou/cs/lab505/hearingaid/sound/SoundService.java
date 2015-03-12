package ntou.cs.lab505.hearingaid.sound;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import ntou.cs.lab505.hearingaid.sqlite.DoSqlite;

/**
 * Created by alan on 3/11/15.
 */
public class SoundService extends Service{

    private static boolean isPauseByHeadsetUnplug = false;
    private static boolean serviceState = false;


    public SoundService() {

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

    @Override
    public void onCreate() {
        Log.d("create", "go");
        serviceState = true;
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d("destroy", "go");
        serviceState = false;
        super.onDestroy();
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
        Log.d("service", "start: " + startId );




        return super.onStartCommand(intent, flags, startId);
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
