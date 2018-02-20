package com.delaroystudios.wordpresspost.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by delaroy on 2/20/18.
 */

public class PostsProvider extends ContentProvider {

    public static final String LOG_TAG = PostsProvider.class.getSimpleName();


    private static final int POSTS = 100;

    private static final int POST_ID = 101;


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(PostsContract.CONTENT_AUTHORITY, PostsContract.PATH_POST, POSTS);

        sUriMatcher.addURI(PostsContract.CONTENT_AUTHORITY, PostsContract.PATH_POST + "/#", POST_ID);
    }



    /** Database helper object */
    private PostsDbHelper mDbHelper;


    @Override
    public boolean onCreate() {
        mDbHelper = new PostsDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case POSTS:

                cursor = database.query(PostsContract.AppEntry.TABLE_POSTS, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case POST_ID:

                selection = PostsContract.AppEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the movies table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(PostsContract.AppEntry.TABLE_POSTS, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case POSTS:
                return insertPost(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertPost(Uri uri, ContentValues values) {

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new movie with the given values
        long id = database.insert(PostsContract.AppEntry.TABLE_POSTS, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the movie content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        switch (sUriMatcher.match(uri)) {
            case POSTS:
                //Rows aren't counted with null selection
                s = (s == null) ? "1" : s;
                break;
            case POST_ID:
                long id = ContentUris.parseId(uri);
                s = String.format("%s = ?", PostsContract.AppEntry._ID);
                strings = new String[]{String.valueOf(id)};
                break;
            default:
                throw new IllegalArgumentException("Illegal delete URI");
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int count = db.delete(PostsContract.AppEntry.TABLE_POSTS, s, strings);

        if (count > 0) {
            //Notify observers of the change
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

