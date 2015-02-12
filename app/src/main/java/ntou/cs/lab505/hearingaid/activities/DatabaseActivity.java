package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ntou.cs.lab505.hearingaid.R;
import ntou.cs.lab505.hearingaid.sqlite.DoSqlite;

public class DatabaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        String data = showDatabase();
        TextView tv = (TextView) findViewById(R.id.database_show_info);
        Resources res = getResources();
        tv.setText(data);
    }

    private String showDatabase() {
        String data = "";
        String tableName = "";

        DoSqlite sqliteEntry = new DoSqlite(this.getApplicationContext());
        SQLiteDatabase db = sqliteEntry.getReadableDatabase();
        Cursor cTable = db.rawQuery("SELECT NAME FROM sqlite_master WHERE TYPE='table'", null);

        if (cTable.moveToFirst()) {
            while (!cTable.isAfterLast()) {
                // table name
                tableName = cTable.getString(0);
                data += tableName + "\n==========\n";

                // column name
                List<String> columnName = new ArrayList<String>();
                Cursor cColumn = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
                if (cColumn.moveToFirst()) {
                    while (!cColumn.isAfterLast()) {
                        columnName.add(cColumn.getString(1));
                        cColumn.moveToNext();
                    }

                    for (int i = 0; i < columnName.size(); i++) {
                        data += columnName.get(i) + ", ";
                    }
                }
                data += "\n";

                // column data
                Cursor cData = db.rawQuery("SELECT * FROM " + tableName, null);
                if (cData.moveToFirst()) {
                    while (!cData.isAfterLast()) {
                        for (int i = 0; i < cData.getColumnCount(); i++) {
                            data += cData.getString(i) + ", ";
                        }
                        data += "\n";
                        cData.moveToNext();
                    }
                }

                data += "\n\n";  //
                cTable.moveToNext();
            }
        }

        return data;
    }
}
