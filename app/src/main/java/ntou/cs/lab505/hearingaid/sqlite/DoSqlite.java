package ntou.cs.lab505.hearingaid.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ntou.cs.lab505.hearingaid.sqlite.TableContract;

/**
 * This class used to process sqlite database.
 *
 * @author Alan Titor
 * @version 1.0
 * @since 2015-01-20
 */
public class DoSqlite extends SQLiteOpenHelper {

    private static final String CS = ", ";  // comma sep
    private static final String WS = " ";  // white space

    public DoSqlite(Context context) {
        super(context, TableContract.DB_NAME + ".db", null, TableContract.DATABASE_VERSION);
    }

    /**
     *
     * @param db The database.
     */
    public void onCreate(SQLiteDatabase db) {
        /*
         * be careful of sql create table command.
         * don't add "CS" at end of command.
         */

        // create sqlite "user_account" table command
        String SQL_CREATE_TABLE_UA =
                "CREATE TABLE " + TableContract.TABLE_USER_ACCOUNT + " (" +
                TableContract._ID + WS + TableContract.SQLITE_TYPE_INTEGER + WS + "PRIMARY KEY" + CS +
                TableContract.T_UA_USERNAME + WS + TableContract.SQLITE_TYPE_TEXT + CS +
                TableContract.T_UA_USERACCOUNT + WS + TableContract.SQLITE_TYPE_TEXT + CS +
                TableContract.T_UA_USERPASSWORD + WS + TableContract.SQLITE_TYPE_TEXT + CS +
                TableContract.T_UA_STATE + WS + TableContract.SQLITE_TYPE_INTEGER +
                " )";
        // create sqlite "setting" table command
        String SQL_CREATE_TABLE_S =
                "CREATE TABLE " + TableContract.TABLE_SETTING + " (" +
                TableContract._ID + WS + TableContract.SQLITE_TYPE_INTEGER + WS + "PRIMARY KEY" + CS +
                TableContract.T_S_BELONG + WS + TableContract.SQLITE_TYPE_INTEGER + CS +
                TableContract.T_S_ALIAS + WS + TableContract.SQLITE_TYPE_TEXT + CS +
                TableContract.T_S_DEVICEINTYPE + WS + TableContract.SQLITE_TYPE_INTEGER + CS +
                TableContract.T_S_DEVICEOUTTYPE + WS + TableContract.SQLITE_TYPE_INTEGER + CS +
                TableContract.T_S_DEFAULTMODE + WS + TableContract.SQLITE_TYPE_INTEGER + CS +
                TableContract.T_S_USEFB + WS + TableContract.SQLITE_TYPE_INTEGER + CS +
                TableContract.T_S_USEFS + WS + TableContract.SQLITE_TYPE_INTEGER + CS +
                TableContract.T_S_USESA + WS + TableContract.SQLITE_TYPE_INTEGER + CS +
                TableContract.T_S_FREQBANDGID + WS + TableContract.SQLITE_TYPE_INTEGER + CS +
                TableContract.T_S_FREQSHIFTGID + WS + TableContract.SQLITE_TYPE_INTEGER + CS +
                TableContract.T_S_SOUNDADDGID + WS + TableContract.SQLITE_TYPE_INTEGER + CS +
                TableContract.T_S_STATE + WS + TableContract.SQLITE_TYPE_INTEGER +
                " )";
        // create sqlite "freq_shift_model" table command
        String SQL_CREATE_TABLE_FSM =
                "CREATE TABLE " + TableContract.TABLE_FREQ_SHIFT_MODEL + " (" +
                TableContract._ID + WS + TableContract.SQLITE_TYPE_INTEGER + WS + "PRIMARY KEY" + CS +
                TableContract.T_FSM_USERID + WS + TableContract.SQLITE_TYPE_INTEGER + CS +
                TableContract.T_FSM_GROUPID + WS + TableContract.SQLITE_TYPE_INTEGER + CS +
                TableContract.T_FSM_SHIFTPARA + WS + TableContract.SQLITE_TYPE_REAL + CS +
                TableContract.T_FSM_STATE + WS + TableContract.SQLITE_TYPE_INTEGER +
                " )";
        // create sqlite "freq_band_model" table command
        String SQL_CREATE_TABLE_FBM = "";
        // create sqlite "sound_add_model" table command
        String SQL_CREATE_TABLE_SAM = "";

        // exclude sqlite commands
        db.execSQL(SQL_CREATE_TABLE_UA);
        db.execSQL(SQL_CREATE_TABLE_S);
        db.execSQL(SQL_CREATE_TABLE_FSM);
        //db.execSQL(SQL_CREATE_TABLE_FBM);
        //db.execSQL(SQL_CREATE_TABLE_SAM);


        // initial db table columns
        // initial user_account table columns
        ContentValues values = new ContentValues();
        values.put(TableContract.T_UA_USERNAME, "user_001");
        values.put(TableContract.T_UA_USERACCOUNT, "ha_account_001");
        values.put(TableContract.T_UA_USERPASSWORD, "ha_pw_001");
        values.put(TableContract.T_UA_STATE, 1);
        db.insert(TableContract.TABLE_USER_ACCOUNT, null, values);
        // get user ID
        long userId = -1 ;
        Cursor cursor = db.rawQuery("SELECT _ID FROM user_account WHERE userAccount='ha_account_001'", null);
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            userId = Integer.parseInt(cursor.getString(0));
        }
        //Log.d("DebugTag", "user ID in \"user_account\": " + userId);
        // initial setting table columns
        values = new ContentValues();
        values.put(TableContract.T_S_BELONG, userId);
        values.put(TableContract.T_S_ALIAS, "setting_001");
        values.put(TableContract.T_S_DEVICEINTYPE, 0);
        values.put(TableContract.T_S_DEVICEOUTTYPE, 0);
        values.put(TableContract.T_S_DEFAULTMODE, 0);
        values.put(TableContract.T_S_USEFB, 0);
        values.put(TableContract.T_S_USEFS, 0);
        values.put(TableContract.T_S_USESA, 0);
        values.put(TableContract.T_S_FREQBANDGID, 0);
        values.put(TableContract.T_S_FREQSHIFTGID, 0);
        values.put(TableContract.T_S_SOUNDADDGID, 0);
        values.put(TableContract.T_S_STATE, 1);
        db.insert(TableContract.TABLE_SETTING, null, values);
        // initial freq_band_model table columns

        // initial freq_shift_model table columns
        values = new ContentValues();
        values.put(TableContract.T_FSM_USERID, userId);
        values.put(TableContract.T_FSM_GROUPID, 1);
        values.put(TableContract.T_FSM_SHIFTPARA, -1.5);
        values.put(TableContract.T_FSM_STATE, 1);
        db.insert(TableContract.TABLE_FREQ_SHIFT_MODEL, null, values);
        // initial sound_add_model table columns


        /*
         *  sqlite SELECT command:
         *
         *  String[] myArguments = new String[] {first, last};
         *  cursor = mDb.query("SELECT * FROM contacts WHERE first_name = ? AND last_name = ?;", myArguments);
         */
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ";
        db.execSQL(SQL_DELETE_ENTRIES + TableContract.TABLE_USER_ACCOUNT);
        db.execSQL(SQL_DELETE_ENTRIES + TableContract.TABLE_SETTING);
        db.execSQL(SQL_DELETE_ENTRIES + TableContract.TABLE_FREQ_BAND_MODEL);
        db.execSQL(SQL_DELETE_ENTRIES + TableContract.TABLE_FREQ_SHIFT_MODEL);
        db.execSQL(SQL_DELETE_ENTRIES + TableContract.TABLE_SOUND_ADD_MODEL);
        onCreate(db);
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }



    // create table


    // insert table


    // search table


    // delete table


    // sql command to string


    // parameters to sql command


    // other relatively methods



}
