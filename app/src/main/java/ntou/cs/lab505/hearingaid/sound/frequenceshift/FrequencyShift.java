package ntou.cs.lab505.hearingaid.sound.frequenceshift;

import android.provider.Telephony;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by alan on 3/16/15.
 */
public class FrequencyShift extends Thread {

    private boolean threadState;  // denote thread state.
    private LinkedBlockingQueue<short[]> inputDataQueue;
    private LinkedBlockingQueue<short[]> outputDataQueue;

    private JNISoundTouch soundtouch = new JNISoundTouch();  // sound process object
    private int sampleRate;
    private int channels;
    private int pitchSemiTones;
    private float rateChange;
    private float tempoChange;


    /**
     * constructor
     */
    public FrequencyShift() {
        sampleRate = 16000;
        channels = 1;
        pitchSemiTones = 0;
        rateChange = 0.0f;
        tempoChange = 0.0f;
    }

    /**
     * set data queue.  this queue is the source for processing.
     * @param inputDataQueue
     */
    public void setInputDataQueue(LinkedBlockingQueue<short[]> inputDataQueue) {
        this.inputDataQueue = inputDataQueue;
    }

    /**
     * set data queue.  this queue used to sve processed data.
     * @param outputDataQueue
     */
    public void setOutputDataQueue(LinkedBlockingQueue<short[]> outputDataQueue) {
        this.outputDataQueue = outputDataQueue;
    }

    public void setSoundParameters(int sampleRate, int channels, int pitchSemiTones, float rateChange, float tempoChange) {
        this.sampleRate = sampleRate;
        this.channels = channels;
        this.pitchSemiTones = pitchSemiTones;
        this.rateChange = rateChange;
        this.tempoChange = tempoChange;
    }

    /**
     * control thread state.  start thread.
     */
    public void threadStart() {
        this.threadState = true;
        this.start();
    }

    /**
     * control thread state.  stop thread.
     */
    public void threadStop() {
        this.threadState = false;
        this.interrupt();
    }

    /**
     * thread content.
     */
    public void run() {
        // set sound parameters
        soundtouch.setSampleRate(sampleRate);
        soundtouch.setChannels(channels);
        soundtouch.setPitchSemiTones(pitchSemiTones);
        soundtouch.setRateChange(rateChange);
        soundtouch.setTempoChange(tempoChange);

        short[] tempBuff;
        short[] tempBuff2;

        try {
            Log.d("FrequencyShift", "process start");
            while (threadState) {
                long startTime = System.currentTimeMillis();
                tempBuff = inputDataQueue.take();

                if (tempBuff != null) {
                    // put data to soundtouch library
                    soundtouch.putSamples(tempBuff, tempBuff.length);

                    // receive data from soundtouch library
                    do {
                        tempBuff2 = soundtouch.receiveSamples();
                        //Log.d("FrequencyShift", "data length: " + tempBuff2.length);
                        outputDataQueue.add(tempBuff2);
                    } while (tempBuff2.length > 0);
                }
                long stopTime = System.currentTimeMillis();
                //Log.d("FrequencyShift", "delay: " + (stopTime - startTime));
            }
        } catch (Throwable e) {
            // do nothing
        }

        Log.d("FrequencyShift", "process stop");
    }
}
