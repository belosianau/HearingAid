package ntou.cs.lab505.hearingaid.sound.frequenceshift;

import android.provider.Telephony;

import java.util.ArrayList;

/**
 * Created by alan on 3/16/15.
 */
public class FrequencyShift extends Thread {

    private OnFrequencyShiftListener onFrequencyShiftListener;
    private boolean isStrat = false;  // control thread state
    private ArrayList<short[]> signals = new ArrayList<short[]>();

    private JNISoundTouch soundtouch = new JNISoundTouch();


    public FrequencyShift () {
        //
    }

    /**
     * set listener interface.  put data to next model.
     */
    public interface OnFrequencyShiftListener {
        public void OnWrite(short[] data);
    }

    public void  setOnFrequencyShift(OnFrequencyShiftListener onFrequencyShiftListener) {
        this.onFrequencyShiftListener = onFrequencyShiftListener;
    }

    public void addData(short[] data) {
        synchronized (signals) {
            signals.add(data);
        }
    }

    /**
     * control thread state.  start thread.
     */
    public void open() {
        isStrat = true;
        this.start();
    }

    /**
     * control thread state.  stop thread.
     */
    public void close() {
        isStrat = false;
        this.interrupt();
    }

    public void run() {



    }
}
