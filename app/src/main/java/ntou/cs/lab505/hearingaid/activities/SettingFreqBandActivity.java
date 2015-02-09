package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
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
        //bandNumber = progress;

        // change layout

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
