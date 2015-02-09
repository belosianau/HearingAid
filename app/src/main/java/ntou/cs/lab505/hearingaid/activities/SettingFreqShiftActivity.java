package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import ntou.cs.lab505.hearingaid.R;

public class SettingFreqShiftActivity extends Activity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_freq_shift);
    }

    /**
     *
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        RadioButton b1 = (RadioButton) findViewById(R.id.setting_freq_shift_button_1);
        RadioButton b2 = (RadioButton) findViewById(R.id.setting_freq_shift_button_2);

        switch (view.getId()) {
            case R.id.setting_freq_shift_button_1:
                if (b1.isChecked()) {
                    b2.setChecked(false);
                }
                break;
            case R.id.setting_freq_shift_button_2:
                if (b2.isChecked()) {
                    b1.setChecked(false);
                }
                break;
            default:
                b1.setChecked(false);
                b2.setChecked(false);
                break;
        }
    }

}
