package es.jorider.rssreader.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import es.jorider.rssreader.constants.ConstantsDB;

/**
 * Created by jorge
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = DatabaseHelper.class.getName();

    public DatabaseHelper(Context context) {
        super(context, ConstantsDB.DATABASE_NAME, null, ConstantsDB.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(ConstantsDB.CREATE_TABLE_RSS_NEWS);
            Log.i(TAG, "TABLE CREATED CORRECTLY!");
        } catch (SQLiteException e) {
            Log.e(TAG, "Error creating Table->" + ((e!=null)? e.getMessage():"Exception"));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
