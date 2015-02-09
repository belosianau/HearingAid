/*
 * Copyright (c) 2015, NTOU CS LAB505 and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is
 */

package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ntou.cs.lab505.hearingaid.R;

/**
 * This class implements the activity of Logo view.
 * It loads the layout and adds the listener of touch actions.
 *
 * @author Alan Titor
 * @version 1.0
 * @since 2015-01-19
 */
public class LogoActivity extends Activity {

    /**
     * Load the layout of the LogoActivity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
    }

    /**
     * The listener of touch actions.
     * When user touch the screen, application will change to next activity(menu activity) showing
     * new view.
     * @param view
     */
    public void changeView(View view) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
    }
}