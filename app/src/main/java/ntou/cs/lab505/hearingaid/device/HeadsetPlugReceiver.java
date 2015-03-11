package ntou.cs.lab505.hearingaid.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import ntou.cs.lab505.hearingaid.sound.SoundService;

/**
 * Created by alan on 3/11/15.
 */
public class HeadsetPlugReceiver extends BroadcastReceiver {

    private static final String EXTRA_STATE = "android.bluetooth.profile.extra.STATE";

    public static final int STATE_CONNECTED = 0x00000002;
    public static final int STATE_CONNECTING = 0x00000001;
    public static final int STATE_DISCONNECTED = 0x00000000;
    public static final int STATE_DISCONNECTING = 0x00000003;

    @Override
    public void onReceive(Context context, Intent intent) {

        //廣播判斷耳機移除需先判斷是否在服務狀態 在服務狀態時需將isPauseByHeadsetUnplug改變 以利接回耳機後啟用
        if (intent.getAction().equals("android.intent.action.HEADSET_PLUG")) {  // Broadcast Action: Wired Headset plugged in or unplugged.


        } else if (intent.getAction().equals("android.bluetooth.headset.action.STATE_CHANGED")) {  	//判斷藍芽狀態是否改變.  // Intent used to broadcast the change in the Audio Connection state of the A2DP profile.
            if (intent.getIntExtra(EXTRA_STATE, STATE_DISCONNECTED) == STATE_DISCONNECTED && SoundService.isService()) {  // 如果還在執行，則停止程式
                SoundService.setHeadsetUnPlug(true);	//做為判斷是否為廣播中斷
                context.stopService(new Intent("      "));	 // stop program
            }



        } else {
            //
        }
    }
}

/*
 * Reference:
 *     http://developer.android.com/reference/android/content/BroadcastReceiver.html
 *
 *
 */