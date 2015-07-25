package es.jorider.rssreader.constants;

/**
 * Created by jorge
 */
public class ConstantsDB {

    public static final String DATABASE_NAME = "RSS_READER_DB";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_RSS_NAME = "RssTable";
    public static final String _TABLE_RSS_ID = "id";
    public static final String _TABLE_RSS_TITLE = "title";
    public static final String _TABLE_RSS_DATE = "date";
    public static final String _TABLE_RSS_DESCRIPTION = "description";
    public static final String _TABLE_RSS_IMAGE = "image";

    public static final String CREATE_TABLE_RSS_NEWS = "CREATE TABLE IF NOT EXISTS "+ TABLE_RSS_NAME + " (" +
            _TABLE_RSS_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
            _TABLE_RSS_TITLE +" TEXT," +
            _TABLE_RSS_DATE +" TEXT," +
            _TABLE_RSS_DESCRIPTION +" TEXT," +
            _TABLE_RSS_IMAGE +" BLOB);";
}
