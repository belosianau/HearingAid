/*
 * Copyright (c) 2015, NTOU CS LAB505 and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is
 */

package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ntou.cs.lab505.hearingaid.R;
import ntou.cs.lab505.hearingaid.sqlite.DoSqlite;
import ntou.cs.lab505.hearingaid.sqlite.TableContract;

/**
 * This class implements the activity of Menu view.
 * It loads the layout and adds the listener of touch actions.
 *
 * @author Alan Titor
 * @version 1.0
 * @since 2015-01-19
 */
public class MenuActivity extends Activity {

    /**
     * Load the layout of the LogoActivity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());


        //---------------------------------------------------
        // sqlite test code

        // write data to db
        SQLiteDatabase db = sqliteEntry.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableContract.T_UA_USERNAME, "user1");
        values.put(TableContract.T_UA_USERACCOUNT, "ha_001");
        values.put(TableContract.T_UA_USERPASSWORD, "ha_001");
        values.put(TableContract.T_UA_STATE, 1);
        //long newRowId = db.insert(TableContract.TABLE_USER_ACCOUNT, null, values);
    }

    /**
     * If the parameters of sound process models are set, the method allows flowchart go to
     * PlayActivity page.
     * @param view
     */
    public void playFunction(View view) {
        // check state from sql
        boolean state = false;

        // call sqlite method
        state = checkPlayState();

        // go to next activity
        if (state == true) {
            Intent intent = new Intent(this, PlayActivity.class);
            startActivity(intent);
        } else {  // notate message
            // do nothing
        }
    }

    /**
     * Go to SettingActivity.
     * @param view
     */
    public void settingFunction(View view) {
         // go to next activity
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    /**
     * Go to TestActivity.
     * @param view
     */
    public void testFunction(View view) {
        // go to next activity
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    /**
     * Go to InfoActivity page that show the document about this application.
     * @param view
     */
    public void infoFunction(View view) {
        // goto next activity
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    // Algorithms

    /*
     * sqlite action flowchart.
     * @return
     */
    private boolean checkPlayState() {
        boolean state = false;

        /*
        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());

        SQLiteDatabase db = sqliteEntry.getWritableDatabase();

        String[] projection = {
                TableContract._ID,
                TableContract.T_UA_USERNAME,
                TableContract.T_UA_USERACCOUNT,
                TableContract.T_UA_USERPASSWORD,
                TableContract.T_UA_STATE
        };

        Cursor c = db.query(
          TableContract.TABLE_USER_ACCOUNT,
          projection,
          null,
          null,
          null,
          null,
          null
        );

        c.moveToFirst();
        String data = c.getString(1);

        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
        */

        return state;
    }

    /**
     * Check icons state.
     * @param view
     */
    public void alertMessage(View view) {
        Context context = getApplicationContext();
        CharSequence text = "OK!";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}