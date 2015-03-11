package ntou.cs.lab505.hearingaid.sound;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by alan on 3/11/15.
 */
public class SoundService extends Service{

    public static boolean isPauseByHeadsetUnplug = false;
    private static boolean serviceState = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void setHeadsetUnPlug(boolean state) {
        isPauseByHeadsetUnplug = state;
    }

    public static boolean isService() {
        return serviceState;
    }
}
