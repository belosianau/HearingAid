package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.LinkedBlockingQueue;

import ntou.cs.lab505.hearingaid.R;
import ntou.cs.lab505.hearingaid.data.Record;
import ntou.cs.lab505.hearingaid.device.Speaker2;
import ntou.cs.lab505.hearingaid.sound.SoundParameter;
import ntou.cs.lab505.hearingaid.sound.frequenceshift.FrequencyShift;
import ntou.cs.lab505.hearingaid.sound.puretone.PureToneGeneration;

public class TestActivity extends Activity {

    PureToneGeneration pureToneGeneration;
    FrequencyShift frequencyShift;
    Speaker2 speaker;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        context = getApplicationContext();
    }

    public void showDatabase(View view) {
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }

    public void showPureTone(View view) throws IOException {

        EditText input1 = (EditText) findViewById(R.id.input1_activity_test);
        EditText input2 = (EditText) findViewById(R.id.input2_activity_test);

        // function models
        //PureToneGeneration pureToneGeneration;
        //FrequencyShift frequencyShift;
        //Speaker2 speaker;
        // data queue
        LinkedBlockingQueue<short[]> pureToneQueue = new LinkedBlockingQueue<short[]>();  //
        LinkedBlockingQueue<short[]> frequencyShiftQueue = new LinkedBlockingQueue<short[]>();


        // initial objects
        pureToneGeneration = new PureToneGeneration(SoundParameter.frequency);
        frequencyShift = new FrequencyShift();

        if (SoundParameter.frequency == 8000) {
            speaker = new Speaker2();
        } else {
            speaker = new Speaker2(SoundParameter.frequency);
        }

        // set data queue
        frequencyShift.setInputDataQueue(pureToneQueue);
        frequencyShift.setOutputDataQueue(frequencyShiftQueue);
        speaker.setInputDataQueue(frequencyShiftQueue);
        //
        frequencyShift.setSoundParameters(SoundParameter.frequency, 1, Integer.parseInt(input2.getText().toString()), 0, 0);
        //
        frequencyShift.threadStart();
        speaker.threadStart();

        // ---
        int freq = Integer.parseInt(input1.getText().toString());  // 注意數值限制
        int sec = 1;
        double db = 80;  // default is 60db
        short[] pureToneVector = pureToneGeneration.generate(freq, sec, db);


        //----------
        /*
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){

            File yzsPath = new File(Environment.getExternalStorageDirectory().toString() + "/sound_process/");
            if(!yzsPath.isDirectory()){
                yzsPath.mkdir();
            }

        }

        File myFile = new File(Environment.getExternalStorageDirectory().toString() + "/sound_process/" + "test.txt");
        myFile.createNewFile();
        FileOutputStream fOut = new FileOutputStream(myFile);
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
        myOutWriter.append("321321");
        myOutWriter.close();
        fOut.close();
        */
        //------------------


        int unit = 4096 * 2;
        short[] tempBuff = new short[unit];

        int count = pureToneVector.length / unit;
        if (pureToneVector.length % unit != 0) {
            count += 1;
        }

        //Log.d("unit", "" + pureToneVector.length);

        int head = 0;
        int size = 0;
        for (int i = 0; i < count; i++) {
            size = tempBuff.length;
            if (i == count - 1) {
                size = pureToneVector.length % unit;
            }

            System.arraycopy(pureToneVector, head, tempBuff, 0, size);
            pureToneQueue.add(tempBuff);

            head += unit;
        }
    }

    @Override
    public void onPause() {

        frequencyShift.threadStop();
        speaker.threadStop();


        // save data to file.
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            File yzsPath = new File(Environment.getExternalStorageDirectory().toString() + "/sound_process/");
            if(!yzsPath.isDirectory()){
                yzsPath.mkdir();
            }
        }

        File myFile = new File(Environment.getExternalStorageDirectory().toString() + "/sound_process/" + "data.txt");
        try {
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append("Time: " + Record.FREQSHIFT_TIME);
            //myOutWriter.append("\nData 1: " + Record.FREQSHIFT_DATA1);
            //myOutWriter.append("\nData length 1: " + Record.FREQSHIFT_DATAL1);
            //myOutWriter.append("\nData 2: " + Record.FREQSHIFT_DATA2);
            //myOutWriter.append("\nData length 2: " + Record.FREQSHIFT_DATAL2);
            myOutWriter.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onPause();
    }
}
