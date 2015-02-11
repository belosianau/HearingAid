package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ntou.cs.lab505.hearingaid.R;

public class SettingFreqBandActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    private int bandNumber;

    /**
     * Initial activity.
     * If it had be set parameters, reload data from database.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_freq_band);

        SeekBar sb = (SeekBar) findViewById(R.id.setting_freq_band_seekBar);
        sb.setOnSeekBarChangeListener(this);
    }

    /**
     * Reload data to view from database
     */
    @Override
    public void onResume() {
        super.onResume();

        //

    }

    /**
     * save data to database
     */
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
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout border = (LinearLayout) findViewById(R.id.setting_freq_band_draw);
        ArrayList<View> views = new ArrayList<View>(progress);

        // clear view
        border.removeAllViews();

        // add view
        for (int count = 0; count < progress; count++) {
            View view = layoutInflater.inflate(R.layout.filter_view, null);
            TextView viewLabel = (TextView) view.findViewById(R.id.filter_view_text1);
            viewLabel.setText(String.valueOf("頻帶" + ( count + 1)));
            views.add(view);
        }

        for (int count = 0; count < views.size(); count++) {
            border.addView(views.get(count));
        }
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
