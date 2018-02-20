package com.delaroystudios.wordpresspost.data;

import android.database.Cursor;

/**
 * Created by delaroy on 2/20/18.
 */

public class Posts {
    public int id;

    public String date;
    public String title;
    public String link;
    public String excerpt;
    public String urltoimage;



    public Posts(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex(PostsContract.AppEntry._ID));
        this.date = cursor.getString(cursor.getColumnIndex(PostsContract.AppEntry.COLUMN_DATE));
        this.title = cursor.getString(cursor.getColumnIndex(PostsContract.AppEntry.COLUMN_TITLE));
        this.link = cursor.getString(cursor.getColumnIndex(PostsContract.AppEntry.COLUMN_LINK));
        this.excerpt = cursor.getString(cursor.getColumnIndex(PostsContract.AppEntry.COLUMN_EXCERPT));
    }

}
