package com.delaroystudios.wordpresspost.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by delaroy on 2/20/18.
 */

public class PostsDbHelper extends SQLiteOpenHelper {
    private static final String TAG = PostsDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "wordpresspost.db";
    private static final int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase db;
    ContentResolver mContentResolver;

    public PostsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mContentResolver = context.getContentResolver();

        db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_POSTS_TABLE = "CREATE TABLE " + PostsContract.AppEntry.TABLE_POSTS + " (" +
                PostsContract.AppEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PostsContract.AppEntry.COLUMN_DATE + " TEXT NOT NULL," +
                PostsContract.AppEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                PostsContract.AppEntry.COLUMN_LINK + " TEXT NOT NULL, " +
                PostsContract.AppEntry.COLUMN_EXCERPT + " TEXT NOT NULL" + " );";

        sqLiteDatabase.execSQL(SQL_CREATE_POSTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PostsContract.AppEntry.TABLE_POSTS);

    }

}

