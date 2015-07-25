package es.jorider.rssreader.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.jorider.rssreader.constants.ConstantsDB;
import es.jorider.rssreader.model.RssItem;
import es.jorider.rssreader.util.ImageUtils;

/**
 * Created by jorge
 */
public class DBRssItem {

    public final String TAG = DBRssItem.class.getName();

    private SQLiteDatabase db = null;
    private DatabaseHelper openHelper = null;

    /**
     * Constuctor
     *
     * @param context
     */
    public DBRssItem(Context context) {
        openHelper = new DatabaseHelper(context);
    }

    /**
     * Insert new new in Database
     *
     * @param rssItem RssItem
     * @return
     */
    public int insert(RssItem rssItem) {

        long valor = -1;

        try {
            ContentValues values = new ContentValues();
            values.put(ConstantsDB._TABLE_RSS_TITLE, rssItem.getTitle());
            values.put(ConstantsDB._TABLE_RSS_DATE, rssItem.getDate());
            values.put(ConstantsDB._TABLE_RSS_DESCRIPTION, rssItem.getDescription());
            try {
                values.put(ConstantsDB._TABLE_RSS_IMAGE, ImageUtils.getBitmapAsByteArray(rssItem.getImage()));
            } catch (NullPointerException e) {
                Log.e(TAG, "Image null");
            }

            db = openHelper.getWritableDatabase();
            valor = db.insert(ConstantsDB.TABLE_RSS_NAME, null, values);

        } catch (NullPointerException e) {
            Log.wtf(TAG, "" + ((e != null) ? e.getMessage() : "NullPointerException"));

        } catch (Exception e) {
            Log.wtf(TAG, "" + ((e != null) ? e.getMessage() : "Exception"));

        } finally {
            closeConnector();
        }
        return (int) valor;
    }

    /**
     * Get all RssNews from Database
     *
     * @return
     */
    public ArrayList<RssItem> getAll() {
        ArrayList<RssItem> list = new ArrayList<RssItem>();

        Cursor c = null;

        try {
            db = openHelper.getWritableDatabase();
            c = db.query(ConstantsDB.TABLE_RSS_NAME, null, null, null, null, null, ConstantsDB._TABLE_RSS_DATE + " DESC");

            if (c.moveToFirst()) {
                // Recorremos el cursor hasta que no haya mas registros
                do {
                    RssItem rssItem = new RssItem();
                    rssItem.setId(c.getInt(0));
                    rssItem.setTitle(c.getString(1));
                    rssItem.setDate(c.getString(2));
                    rssItem.setDescription(c.getString(3));
                    rssItem.setImage(ImageUtils.getImageFromByteArray(c.getBlob(4)));

                    list.add(rssItem);

                } while (c.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.wtf(TAG, "" + ((e != null) ? e.getMessage() : "SQLiteException"));
        } catch (NullPointerException e) {
            Log.wtf(TAG, "" + ((e != null) ? e.getMessage() : "NullPointerException"));
        } catch (Exception e) {
            Log.wtf(TAG, "" + ((e != null) ? e.getMessage() : "Exception"));
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
            closeConnector();
        }

        return list;
    }

    /**
     * Delete all current data stored
     *
     * @return
     */
    public int deleteAll() {
        int resp = 0;

        try {
            db = openHelper.getWritableDatabase();
            resp = db.delete(ConstantsDB.TABLE_RSS_NAME, null, null);
        }catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            closeConnector();
        }
        return resp;
    }


    /**
     * Close connector
     */
    private void closeConnector() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
