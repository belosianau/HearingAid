package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import ntou.cs.lab505.hearingaid.R;

public class SettingFreqBandActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    private int bandNumber;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_freq_band);

        loadData();
        loadView();

        SeekBar sb = (SeekBar) findViewById(R.id.setting_freq_band_seekBar);
        sb.setOnSeekBarChangeListener(this);
    }

    /**
     *
     */
    @Override
    public void onResume() {
        super.onResume();

        //

    }

    @Override
    public void onPause() {
        //


        super.onPause();
    }

    /**
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // change text
        TextView tv = (TextView) findViewById(R.id.setting_freq_band_show_text_2);
        tv.setText(String.valueOf(progress));
        bandNumber = progress;

        // clear view
        ListView lv = (ListView) findViewById(R.id.setting_freq_band_listView);
        lv.setAdapter(null);

        // add list
        FilterView[] FVData = new FilterView[2];

        FVData[0] = new FilterView(10, 10);
        FVData[1] = new FilterView(33, 44);

        FilterViewAdapter FVAdapter = new FilterViewAdapter(this, R.layout.filter_view, FVData);
        lv.setAdapter(FVAdapter);
    }

    /**
     *
     * @param seekBar
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //
    }

    /**
     *
     * @param seekBar
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //
    }

    /**
     *
     */
    private void loadData() {


    }

    /**
     *
     */
    private void loadView() {


    }
}
