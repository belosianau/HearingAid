package ntou.cs.lab505.hearingaid.device;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

import ntou.cs.lab505.hearingaid.sound.SoundParameter;

/**
 * Created by alan on 3/17/15.
 */
public class Speaker2 extends Thread {

    private boolean threadState;
    private int speakerBufSize;
    private AudioTrack audioTrack;
    private LinkedBlockingQueue<short[]> inputDataQueue;

    private int sampleRate = SoundParameter.frequency;


    /**
     * constructor
     */
    public Speaker2() {
        speakerBufSize = AudioTrack.getMinBufferSize(sampleRate,
                SoundParameter.channelConfiguration,
                SoundParameter.audioEncoding);
        audioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL,
                sampleRate,
                SoundParameter.channelConfiguration,
                SoundParameter.audioEncoding,
                speakerBufSize,
                AudioTrack.MODE_STATIC);
    }

    /**
     * constructor
     * @param sampleRate
     */
    public Speaker2(int sampleRate) {
        this.sampleRate = sampleRate;
        speakerBufSize = AudioTrack.getMinBufferSize(sampleRate,
                AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                SoundParameter.audioEncoding);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                SoundParameter.audioEncoding,
                speakerBufSize,
                AudioTrack.MODE_STREAM);
    }

    /**
     * set data queue.  this queue used to save processed data.
     * @param inputDataQueue
     */
    public void setInputDataQueue(LinkedBlockingQueue<short[]> inputDataQueue) {
        this.inputDataQueue = inputDataQueue;
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
     * thread function.
     */
    public void run() {
        Log.d("Speaker", "process start");
        short[] tempBuff;
        // start audio function
        audioTrack.play();


        while (threadState) {
            try {
                // take data from queue
                //tempBuff = inputDataQueue.take();
                tempBuff = inputDataQueue.poll();
                if (tempBuff != null) {
                    audioTrack.write(tempBuff, 0, tempBuff.length);
                }
            } catch (Exception e) {
                // do nothing.
            }
        }

        // stop audio function
        audioTrack.stop();
        audioTrack.release();
        Log.d("Speaker", "process stop");
    }

    /**
     * calculate db values
     * @param data: the data want be analysed
     * @return db values
     */
    public int calculateDb(short[] data) {
        short min = data[0];
        double sum = 0;

        for (int i = 0; i < data.length; i++) {
            sum = sum + Math.pow(data[i], 2);
        }

        sum = 10 * Math.log10(sum / data.length);

        return (int) sum;
    }
}
