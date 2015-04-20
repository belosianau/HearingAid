package ntou.cs.lab505.hearingaid.sound.puretone;

import java.util.Map;

import ntou.cs.lab505.hearingaid.device.Microphone;

/**
 * Created by alan on 3/26/15.
 */
public class PureToneGeneration {

    private int sampleRate;
    private double db;

    /**
     * initial samplerate
     * @param sampleRate
     */
    public PureToneGeneration(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    /**
     * Generate pure tone.
     * @param frequency
     * @param second
     * @param db
     * @return
     */
    public short[] generate(int frequency, int second, double db) {

        this.db =Math.pow(10, db / 20);
        // create sin wave
        short[] sin = new short[second * sampleRate];
        double samplingInterval = (double) (sampleRate / frequency);

        for (int i = 0; i < sin.length; i++) {
            double angle = (2.0 * Math.PI * i) / samplingInterval;

            int temp = (int) (Math.sin(angle) * this.db);

            // avoid vector overflow
            if (temp > Short.MAX_VALUE) {
                sin[i] = Short.MAX_VALUE;
            } else if (temp < Short.MIN_VALUE) {
                sin[i] = Short.MIN_VALUE;
            } else {
                sin[i] = (short) (Math.sin(angle) * this.db);
            }
        }

        return sin;
    }
}
