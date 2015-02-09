package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ntou.cs.lab505.hearingaid.R;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void showDatabase(View view) {
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }
}
