package ntou.cs.lab505.hearingaid.sound.frequenceshift;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.LinkedBlockingQueue;

import ntou.cs.lab505.hearingaid.data.Record;

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

    File myF_data1 = new File(Environment.getExternalStorageDirectory().toString() + "/sound_process/" + "data1.txt");
    File myF_datal1 = new File(Environment.getExternalStorageDirectory().toString() + "/sound_process/" + "datal1.txt");
    File myF_data2 = new File(Environment.getExternalStorageDirectory().toString() + "/sound_process/" + "data2.txt");
    File myF_datal2 = new File(Environment.getExternalStorageDirectory().toString() + "/sound_process/" + "datal2.txt");


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
        Log.d("FrequencyShift", "process start");

        // set sound parameters
        soundtouch.setSampleRate(sampleRate);
        soundtouch.setChannels(channels);
        soundtouch.setPitchSemiTones(pitchSemiTones);  // Changes the sound pitch or key while keeping the original tempo (speed).
        soundtouch.setRateChange(rateChange);  // Changes both tempo and pitch together as if a vinyl disc was played at different RPM rate.
        soundtouch.setTempoChange(tempoChange);  // Changes the sound to play at faster or slower tempo than originally without affecting the sound pitch.

        short[] tempBuff;
        short[] tempBuff2;

        FileOutputStream fOut1 = null;
        FileOutputStream fOutl1 = null;
        FileOutputStream fOut2 = null;
        FileOutputStream fOutl2 = null;
        OutputStreamWriter w1 = null;
        OutputStreamWriter wl1 = null;
        OutputStreamWriter w2 = null;
        OutputStreamWriter wl2 = null;
        try {
            myF_data1.createNewFile();
            fOut1 = new FileOutputStream(myF_data1);
            myF_datal1.createNewFile();
            fOutl1 = new FileOutputStream(myF_datal1);
            myF_data2.createNewFile();
            fOut2 = new FileOutputStream(myF_data2);
            myF_datal2.createNewFile();
            fOutl2 = new FileOutputStream(myF_datal2);

            w1 = new OutputStreamWriter(fOut1);
            wl1 = new OutputStreamWriter(fOutl1);
            w2 = new OutputStreamWriter(fOut2);
            wl2 = new OutputStreamWriter(fOutl2);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            while (threadState) {
                tempBuff = inputDataQueue.take();

                if (tempBuff.length != 0) {
                    Log.d("FrequencyShift", "data 1 length: " + tempBuff.length);
                    //Record.FREQSHIFT_DATA1 += "(";
                    //for (int i = 0; i < tempBuff.length; i++) {
                    //    Record.FREQSHIFT_DATA1 += tempBuff[i] + ",";
                    //}
                    //Record.FREQSHIFT_DATA1 += ")\n";
                    //Record.FREQSHIFT_DATAL1 += tempBuff.length + ",";

                    // put data to soundtouch library
                    //long startTime = System.currentTimeMillis();



                    try {
                        wl1.append(tempBuff.length + ",");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        w1.append("(");
                        w1.append(tempBuff[0] + "");
                        for (int i = 1; i < tempBuff.length; i++) {
                            w1.append("," + tempBuff[i]);
                        }
                        w1.append(")\n\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    long startTime = System.nanoTime();  // start time.
                    soundtouch.putSamples(tempBuff, tempBuff.length);

                    // receive data from soundtouch library
                    /*
                    do {
                        tempBuff2 = soundtouch.receiveSamples();
                        Log.d("FrequencyShift", "data 2 length: " + tempBuff2.length);
                        Record.FREQSHIFT_DATA2 += "(";
                        for (int i = 0; i < tempBuff2.length; i++) {
                            Record.FREQSHIFT_DATA2 += tempBuff2[i] + ",";
                        }
                        Record.FREQSHIFT_DATA2 += ")\n";
                        Record.FREQSHIFT_DATAL2 += tempBuff2.length + ",";
                        outputDataQueue.add(tempBuff2);
                    } while (tempBuff2.length > 0);
                    */
                    while ((tempBuff2 = soundtouch.receiveSamples()).length != 0) {
                        Log.d("FrequencyShift", "data 2 length: " + tempBuff2.length);
                        //Record.FREQSHIFT_DATA2 += "(";
                        //for (int i = 0; i < tempBuff2.length; i++) {
                        //    Record.FREQSHIFT_DATA2 += tempBuff2[i] + ",";
                        //}
                        //Record.FREQSHIFT_DATA2 += ")\n";
                        //Record.FREQSHIFT_DATAL2 += tempBuff2.length + ",";



                        try {
                            wl2.append(tempBuff2.length + ",");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            w2.append("(");
                            w2.append(tempBuff2[0] + "");
                            for (int i = 1; i < tempBuff2.length; i++) {
                                w2.append("," + tempBuff2[i]);
                            }
                            w2.append(")\n\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                        outputDataQueue.add(tempBuff2);
                    }

                    //long stopTime = System.currentTimeMillis();
                    long stopTime = System.nanoTime();  // stop time.
                    Log.d("FrequencyShift", "delay: " + (stopTime - startTime));
                    Record.FREQSHIFT_TIME += (stopTime - startTime) + ",";
                }
            }
        } catch (Throwable e) {
            // do nothing
        }



        try {
            w1.close();
            wl1.close();
            w2.close();
            wl2.close();
            fOut1.close();
            fOutl1.close();
            fOut2.close();
            fOutl2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Log.d("FrequencyShift", "process stop");
    }
}


/**
 * How to use NDK:
 *
 * (1)
 *      just include library from exist package.
 *
 * (2)
 *      add ndk compile command at 'build.gradle'
 *              ndk {
 *                   moduleName "soundtouch"
 *               }
 *      write the correct package path in c++ interface code
 *              extern "C" JNIEXPORT void JNICALL Java_ntou_cs_lab505_hearingaid_sound_frequenceshift_JNISoundTouch_setSampleRate() {}
 *
 *              add extern "C"
 *              denote JNICALL_MY_PACKAGE_PATH_......() {}
 */
