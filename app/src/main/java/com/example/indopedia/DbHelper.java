//package com.example.indopedia;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
///**
// * Created by asus on 13/11/2017.
// */
//
//public class DbHelper extends SQLiteOpenHelper {
//
//    // If you change the database schema, you must increment the database version.
//    public static final int DATABASE_VERSION = 1;
//
//    //Database Name
//    private static final String DATABASE_NAME = "Indopedia.db";
//
//    //Table Name
//    private static final String TABLE_NAME = "articles";
//
//    // column names
//    private static final String ARTICLE_TITLE = "article_title";
//    private static final String ARTICLE_CONTENT = "article_content";
//    private static final String ARTICLE_IMAGE = "article_image";
//
//    // Table create statement
//    private static final String CREATE_TABLE_ARTICLES = "CREATE TABLE " + TABLE_NAME + "("+
//            ARTICLE_TITLE + " TEXT," +
//            ARTICLE_CONTENT + " TEXT" +
//            ARTICLE_IMAGE + " INT);";
//
//    //Table delete statement
//    private static final String DELETE_TABLE_ARTICLES = "DROP TABLE IF EXISTS " + TABLE_NAME;
//
//    public DbHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_TABLE_ARTICLES);
//    }
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // This database is only a cache for online data, so its upgrade policy is
//        // to simply to discard the data and start over
//        db.execSQL(DELETE_TABLE_ARTICLES);
//        onCreate(db);
//    }
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        onUpgrade(db, oldVersion, newVersion);
//    }
//
//    public void insertArticle(Article article){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(ARTICLE_TITLE, article.getTitle()); // Article Title
//        values.put(ARTICLE_CONTENT, article.getContent());
//        values.put(ARTICLE_IMAGE, article.getPhotoId()); // Article Image
//
//        // Inserting Row
//        long newRowId = db.insert(TABLE_NAME, null, values);
//    }
//
//
//
//
//    public void deleteArticle(Article article){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, ARTICLE_TITLE + " = ?",
//                new String[] { String.valueOf(article.getTitle()) });
//    }
//
//
//
//    //not done
//    public Article getArticle(String articleName) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//// Define a projection that specifies which columns from the database
//// you will actually use after this query.
//        String[] projection = {
//                ARTICLE_TITLE,
//                ARTICLE_CONTENT,
//                ARTICLE_IMAGE
//        };
//
//// Filter results WHERE "title" = 'My Title'
//        String selection = ARTICLE_TITLE + " = ?";
//        String[] selectionArgs = {articleName};
//
//// How you want the results sorted in the resulting Cursor
////        String sortOrder =
////                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";
//
//        Cursor cursor = db.query(
//                TABLE_NAME,                     // The table to query
//                projection,                               // The columns to return
//                selection,                                // The columns for the WHERE clause
//                selectionArgs,                            // The values for the WHERE clause
//                null,                                     // don't group the rows
//                null,                                     // don't filter by row groups
//                null                                // The sort order
//        );
//
//        String articleTitle = null;
//        String articleContent = null;
//
//
//        if (cursor != null) {
//            cursor.moveToNext();
//
//        }
//
//
//        Article article = null;
//
//        return article;
//    }
//
//}
