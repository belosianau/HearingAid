package ntou.cs.lab505.hearingaid.sqlite;

import android.provider.BaseColumns;

/**
 * Created by alan on 1/22/15.
 */
public final class TableContract implements BaseColumns {

    //  sqlite db information
    public static final int DATABASE_VERSION = 1;
    public static final String DB_NAME = "ha";
    public static final String SQLITE_TYPE_NULL = "NULL";
    public static final String SQLITE_TYPE_INTEGER = "INTEGER";
    public static final String SQLITE_TYPE_REAL = "REAL";
    public static final String SQLITE_TYPE_TEXT = "TEXT";
    public static final String SQLITE_TYPE_BLOB = "BLOB";
    // sqlite db table name
    public static final String TABLE_USER_ACCOUNT = "user_account";
    public static final String TABLE_SETTING = "setting";
    public static final String TABLE_FREQ_BAND_MODEL = "freq_band_model";
    public static final String TABLE_FREQ_SHIFT_MODEL = "freq_shift_model";
    public static final String TABLE_SOUND_ADD_MODEL = "sound_add_model";
    // sqlite db columns name
    // user_account table
    public static final String T_UA_USERNAME = "userName";
    public static final String T_UA_USERACCOUNT = "userAccount";
    public static final String T_UA_USERPASSWORD = "userPassword";
    public static final String T_UA_STATE = "state";
    // setting table
    public static final String T_S_BELONG = "belong";
    public static final String T_S_ALIAS = "alias";
    public static final String T_S_DEVICEINTYPE = "deviceInType";
    public static final String T_S_DEVICEOUTTYPE = "deviceOutType";
    public static final String T_S_DEFAULTMODE = "defaultMode";
    public static final String T_S_USEFB = "useFB";
    public static final String T_S_USEFS = "useFS";
    public static final String T_S_USESA = "useSA";
    public static final String T_S_FREQBANDGID = "freqBandGId";
    public static final String T_S_FREQSHIFTGID = "freqShiftGId";
    public static final String T_S_SOUNDADDGID = "soundAddGId";
    public static final String T_S_STATE = "state";
    // freq_band_model table
    public static final String T_FBM_USERID = "userId";
    public static final String T_FBM_GROUPID = "groupId";
    public static final String T_FBM_LOWBAND = "lowBand";
    public static final String T_FBM_HIGHBAND = "highBand";
    public static final String T_FBM_STATE =  "state";
    // freq_shift_model table
    public static final String T_FSM_USERID = "userId";
    public static final String T_FSM_GROUPID = "groupId";
    public static final String T_FSM_SHIFTPARA = "shiftPara";
    public static final String T_FSM_STATE = "state";

    // sound_add_model table
    public static final String T_SAM_USERID = "userId";
    public static final String T_SAM_GROUPID = "groupId";
    public static final String T_SAM_L40 = "l40";
    public static final String T_SAM_L60 = "l60";
    public static final String T_SAM_L80 = "l80";
    public static final String T_SAM_R40 = "r40";
    public static final String T_SAM_R60 = "r60";
    public static final String T_SAM_R80 = "r80";
    public static final String T_SAM_STATE = "state";

    /**
     *
     */
    public TableContract() {}
}
