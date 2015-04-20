package ntou.cs.lab505.hearingaid.device;


import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import ntou.cs.lab505.hearingaid.sound.SoundParameter;

/**
 * Created by alan on 3/12/15.
 */
public class Microphone extends Thread {

    private OnMicrophoneListener onMicrophoneListener;

    private int recBufSize;
    private AudioRecord audioRecord;	//錄音類別
    private boolean isRecording = false;


    public Microphone() {
        recBufSize = AudioRecord.getMinBufferSize(SoundParameter.frequency,
                                                  SoundParameter.channelConfiguration,
                                                  SoundParameter.audioEncoding);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                                      SoundParameter.frequency,
                                      SoundParameter.channelConfiguration,
                                      SoundParameter.audioEncoding,
                                      recBufSize);
    }

    /**
     * set listener interface
     */
    public interface OnMicrophoneListener {
        public void OnRec(short [] data);
    }

    public void setOnMicrophoneListener(OnMicrophoneListener onMicrophoneListener) {
        this.onMicrophoneListener = onMicrophoneListener;
    }

    /**
     * control thread state.  start thread.
     */
    public void open() {
        isRecording = true;
        this.start();
    }

    /**
     * control thread state.  stop thread.
     */
    public void close() {
        isRecording = false;
        this.interrupt();
    }

    /**
     * thread content.
     */
    public void run() {
        try {
            Log.d("Microphone", "process start");
            short [] buffer = new short[recBufSize];
            int dataSum = 0;
            audioRecord.startRecording();

            // function loop
            while (isRecording) {
                //Log.d("Microphone", "function start");
                int bufferReadResult = audioRecord.read(buffer, 0, recBufSize);

                // check buffer contains data or not.
                if (bufferReadResult > 0) {
                    // skip empty data
                    dataSum = 0;
                    for (int i = 0; i < bufferReadResult; i++) {
                        dataSum += buffer[i];
                    }
                    Log.d("Microphone", "dataSum: " + dataSum);
                    //if (dataSum < 10000) {
                        //Log.d("Microphone", "data is empty");
                    //    continue;
                    //}

                    Log.d("Microphone", "get data " + bufferReadResult);
                    short[] tempBuf = new short[bufferReadResult];
                    //Log.d("length", "length: " + tempBuf.length);
                    System.arraycopy(buffer, 0, tempBuf, 0, bufferReadResult);
                    // send data back.
                    //Log.d("Microphone", buffer.toString());
                    onMicrophoneListener.OnRec(tempBuf);
                } else {
                    Log.d("Microphone", "record no data");
                }
            }
        } catch (Throwable e) {
            //
        }

        Log.d("Microphone", "process stop");
        audioRecord.release();
    }

}
