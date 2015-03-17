package ntou.cs.lab505.hearingaid.device;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import ntou.cs.lab505.hearingaid.sound.SoundParameter;

/**
 * Created by alan on 3/17/15.
 */
public class Microphone2 extends Thread {

    private boolean threadState;  // denote thread state.
    private int recordBufSize;
    private LinkedBlockingQueue<short[]> outputDataQueue;
    private AudioRecord audioRecord;  // record function object.


    public Microphone2() {
        recordBufSize = AudioRecord.getMinBufferSize(SoundParameter.frequency,
                                                     SoundParameter.channelConfiguration,
                                                     SoundParameter.audioEncoding);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                                      SoundParameter.frequency,
                                      SoundParameter.channelConfiguration,
                                      SoundParameter.audioEncoding,
                                      recordBufSize);
    }

    /**
     * set data queue.  this queue used to save processed data.
     * @param outputDataQueue
     */
    public void setOutputQueue(LinkedBlockingQueue<short[]> outputDataQueue) {
        this.outputDataQueue = outputDataQueue;
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
        try {
            Log.d("Microphone", "process start");
            short[] microphoneBuff = new short[recordBufSize];
            // start record sound
            audioRecord.startRecording();


            // function loop
            while(threadState) {
                int buffReadResult = audioRecord.read(microphoneBuff, 0, recordBufSize);

                // check buffer contains data or not.
                if (buffReadResult > 0) {
                    // temp buffer used to resize microphoneBuffer
                    short[] tempBuff = new short[buffReadResult];
                    // copy data to temp buffer
                    System.arraycopy(microphoneBuff, 0, tempBuff, 0, buffReadResult);
                    // output data
                    outputDataQueue.add(tempBuff);
                }
            }
        } catch (Throwable e) {
            // do nothing
        }

        Log.d("Microphone", "process stop");
        // stoop microphone record
        audioRecord.release();
    }
}
