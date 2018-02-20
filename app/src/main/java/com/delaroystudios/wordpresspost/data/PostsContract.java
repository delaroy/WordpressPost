package com.delaroystudios.wordpresspost.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by delaroy on 2/20/18.
 */

public class PostsContract {

    public static final String CONTENT_AUTHORITY = "com.delaroystudios.wordpresspost";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_POST= "posts-path";


    public static final class AppEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_POST);

        public final static String TABLE_POSTS = "poststable";


        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_LINK = "link";
        public final static String COLUMN_EXCERPT = "excerpt";

    }
}

