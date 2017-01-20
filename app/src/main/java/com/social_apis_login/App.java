package com.social_apis_login;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Ramesh on 1/20/17.
 */

public class App extends Application {
    private GoogleApiClient mGoogleApiClient;
    private static App mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mInstance = this;
        getKeyHash();
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public void setClient(GoogleApiClient client){
        mGoogleApiClient = client;
    }

    public GoogleApiClient getClient(){
        return mGoogleApiClient;
    }

    public void getKeyHash() {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.social_apis_login", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash Key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }
}
