package ntou.cs.lab505.hearingaid.device;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

import java.util.ArrayList;

import ntou.cs.lab505.hearingaid.sound.SoundParameter;

/**
 * Created by alan on 3/12/15.
 */
public class Speaker extends Thread {

    private int speakerBufSize;
    private AudioTrack audioTrack;
    private boolean isPlaying = false;
    private ArrayList<short[]> signals = new ArrayList<short[]>();

    private int sampleRate = SoundParameter.frequency;


    /**
     * constructor
     */
    public Speaker() {
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
     * con
     * @param sampleRate
     */
    public Speaker(int sampleRate) {
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
     * control thread state.  start thread.
     */
    public void open() {
        isPlaying = true;
        this.start();
    }

    /**
     * control thread state.  stop thread.
     */
    public void close() {
        isPlaying = false;
        this.interrupt();
    }

    /**
     * get data from other models.
     * @param data: data is sound resource that be process by other models
     */
    public void AddSignals(short[] data) {
        // lock array when insert data.
        synchronized (signals) {
            signals.add(data);
        }
    }

    /**
     * thread function
     */
    public void run() {
        Log.d("Speaker", "process start");
        //audioTrack.setPlaybackRate(88200);
        short[] buff;
        audioTrack.play();

        while (isPlaying) {
            //Log.d("Speaker", "function start");
            //buff = null;
            yield();

            // lock array when output data.
            synchronized (signals) {
                // get data
                if (signals.size() == 0) {
                    continue;
                } else {
                    buff = signals.get(0);
                    signals.remove(0);
                }

                // write data to speaker device
                if (buff.length > 0) {
                    Log.d("Speaker", "write data");
                    audioTrack.write(buff, 0, buff.length);
                }
            }
        }

        // stop audio
        audioTrack.stop();
        audioTrack.release();
        signals.clear();
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
