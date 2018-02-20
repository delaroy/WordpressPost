package com.delaroystudios.wordpresspost.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by delaroy on 8/21/17.
 */

public class PostsSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static PostsSyncAdapter eMovieSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("MovieSyncService", "onCreate - MovieSyncService");
        synchronized (sSyncAdapterLock) {
            if (eMovieSyncAdapter == null) {
                eMovieSyncAdapter = new PostsSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return eMovieSyncAdapter.getSyncAdapterBinder();
    }
}
