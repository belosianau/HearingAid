package ntou.cs.lab505.hearingaid.device;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by alan on 3/12/15.
 */
public class Speaker extends Service {

    public Speaker() {

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

        super.onCreate();
    }

    @Override
    public void onDestroy() {

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

        return super.onStartCommand(intent, flags, startId);
    }
}
