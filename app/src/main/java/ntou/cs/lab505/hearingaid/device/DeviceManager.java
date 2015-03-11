package ntou.cs.lab505.hearingaid.device;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by alan on 3/3/15.
 */
public class DeviceManager {

    private AudioManager audioManager = null;
    private int soundInputType = 0;  // o: default mic input. 1: bluetooth mic input.
    // device state
    private boolean isWiredHeadsetOn = false;
    private boolean isBluetoothA2DPOn = false;


    /**
     *
     * @param audioManager
     */
    public DeviceManager (AudioManager audioManager) {
        this.audioManager = audioManager;
        audioManager.setMode(AudioManager.MODE_NORMAL);
        isWiredHeadsetOn = audioManager.isWiredHeadsetOn();
        isBluetoothA2DPOn = audioManager.isBluetoothA2dpOn();
    }

    /**
     * check device state is on go or not.
     * @param type
     * @return device state
     */
    public boolean getDeviceState(int type) {

        boolean state = false;

        switch (type) {
            case 0:
                if (audioManager.isWiredHeadsetOn()) {
                    state = true;
                }
                break;
            case 1:
                if (audioManager.isBluetoothA2dpOn()) {
                    state = true;
                }
                break;
            default:
                break;
        }

        return state;
    }

    public boolean setSoundInput(int type) {
        boolean state = false;

        if (type == 0) {  // default mic
            soundInputType = type;
            audioManager.setBluetoothScoOn(false);
            audioManager.stopBluetoothSco();
            state = true;
        } else if (type == 1) {  // bluetooth mic
            if (audioManager.isBluetoothA2dpOn()) {
                soundInputType = type;
                audioManager.setBluetoothScoOn(true);
                audioManager.startBluetoothSco();
                state = true;
            }
        } else {
            //
        }

        return state;
    }

    public int getSoundInput() {
        return soundInputType;
    }

    public void closeDeviceManager() {
        audioManager.setBluetoothA2dpOn(isBluetoothA2DPOn);
        audioManager.setWiredHeadsetOn(isWiredHeadsetOn);
        audioManager.setBluetoothScoOn(false);
        audioManager.stopBluetoothSco();
    }
}
