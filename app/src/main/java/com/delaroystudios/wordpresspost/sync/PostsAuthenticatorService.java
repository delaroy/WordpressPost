package com.delaroystudios.wordpresspost.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by delaroy on 8/16/17.
 */


public class PostsAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private PostsAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new PostsAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
