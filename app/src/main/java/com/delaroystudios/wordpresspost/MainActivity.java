package com.delaroystudios.wordpresspost;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.delaroystudios.wordpresspost.data.PostsContract;
import com.delaroystudios.wordpresspost.data.PostsDbHelper;
import com.delaroystudios.wordpresspost.sync.PostsSyncAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    SQLiteDatabase mDb;
    RecyclerView recyclerView;
    PostsAdapter postsAdapter;
    PostsDbHelper dbHelper;

    private static final int POSTS_LOADER = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new PostsDbHelper(this);

        PostsSyncAdapter.initializeSyncAdapter(this);

        //Toast.makeText(this, "On point", Toast.LENGTH_SHORT).show();
        PostsDbHelper dbHelper = new PostsDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        postsAdapter = new PostsAdapter(this, null);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(postsAdapter);

        getSupportLoaderManager().initLoader(POSTS_LOADER, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] PROJECTION = new String[]{
                PostsContract.AppEntry._ID,
                PostsContract.AppEntry.COLUMN_DATE,
                PostsContract.AppEntry.COLUMN_TITLE,
                PostsContract.AppEntry.COLUMN_LINK,
                PostsContract.AppEntry.COLUMN_EXCERPT,
        };

        return new CursorLoader(this,   // Parent activity context
                PostsContract.AppEntry.CONTENT_URI,   // Provider content URI to query
                PROJECTION,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        postsAdapter.swapCursor(data);

        //Toast.makeText(this, "going on fine " + data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        postsAdapter.swapCursor(null);
    }
}
