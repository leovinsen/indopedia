package com.example.indopedia;

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by asus on 05/12/2017.
 */

public class Database {

    private static final String TAG = "Database";

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "Indopedia.db";

    //Table Name
    private static final String TABLE_NAME = "articles";

    //The columns we'll include in the dictionary table
    public static final String ARTICLE_TITLE = SearchManager.SUGGEST_COLUMN_TEXT_1;
    public static final String ARTICLE_CONTENT = SearchManager.SUGGEST_COLUMN_TEXT_2;
    private static final String ARTICLE_IMAGE = "article_image";

    private final DatabaseHelper mDatabaseOpenHelper;
    //private static final HashMap<String,String> mColumnMap = buildColumnMap();

    /**
     * Constructor
     * @param context The Context within which to work, used to create the DB
     */
    public Database(Context context) {
        Log.e(TAG, "Init database");
        mDatabaseOpenHelper = new DatabaseHelper(context);
        System.out.println("after init?");
    }

    public String getDBA(String articleName){
        SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                ARTICLE_TITLE,
                ARTICLE_CONTENT,
                ARTICLE_IMAGE
        };

// Filter results WHERE "title" = 'My Title'
        String selection = ARTICLE_TITLE + " = ?";
        String[] selectionArgs = {articleName};

// How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                // The sort order
        );

        String articleTitle = null;
        String articleContent = null;


        if (cursor != null) {
            cursor.moveToNext();
            articleTitle = cursor.getString(0);
        }


        return articleTitle;
    }

    public SQLiteDatabase getDb(){
        return mDatabaseOpenHelper.getReadableDatabase();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private final Context mHelperContext;
        private SQLiteDatabase mDatabase;

        // Table create statement
        private static final String CREATE_TABLE_ARTICLES = "CREATE TABLE " + TABLE_NAME + "("+
                ARTICLE_TITLE + " TEXT," +
                ARTICLE_CONTENT + " TEXT," +
                ARTICLE_IMAGE + " INT);";

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.e(TAG, "CREATING DatabaseHelper");
            mHelperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.e(TAG, "exec SQL");
            mDatabase = db;
            mDatabase.execSQL(CREATE_TABLE_ARTICLES);
            loadDatabase();

        }



        private void loadDatabase(){

            new Thread(new Runnable() {
                public void run(){
                    try {
                        loadArticles();
                    } catch (IOException e){
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }

        private void loadArticles() throws IOException {
            Log.d(TAG, "Loading articles...");
            final Resources resources = mHelperContext.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.articles);
            String packageName = mHelperContext.getPackageName();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            try{
                String line;
                //read until end of file
                while ((line = reader.readLine()) != null){
                    //split by -
                    String[] strings = TextUtils.split(line, "|");

                    //terminate current iteration if article is not in the form of <TITLE - IMAGEID - CONTENT>
                    //although form is <TITLE, IMAGEID, CONTENT>, they are inserted as <TITLE, CONTENT, IMAGEID>
                   Log.e(TAG, " reached here");
                    if (strings.length < 3) continue;
                    System.out.println("yeay");
                    String articleTitle = strings[0].trim();
                    String articleContent = strings[2].trim();
                    int photoId = resources.getIdentifier(strings[1], "drawable", packageName);

                    long id = addArticle(articleTitle, articleContent, photoId);
                    if (id < 0) {
                        Log.e(TAG, "unable to article: " + strings[0].trim());
                    }
                }
            } finally {
                reader.close();
            }
            Log.d(TAG, "Finished loading articles.");
        }

        public long addArticle (String title, String content, int photoId){
            ContentValues initialValues = new ContentValues();
            initialValues.put(ARTICLE_TITLE, title);
            initialValues.put(ARTICLE_CONTENT, content);
            initialValues.put(ARTICLE_IMAGE, photoId);

            return mDatabase.insert(TABLE_NAME, null, initialValues);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data" );
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}
