package com.segu.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.segu.aidl.RandomNumberGenerator;

import java.util.Random;

public class MySuperBoundService extends Service {

    private final Random random = new Random();

    private final String LOG_TAG = "BoundService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "Service onCreated");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "Service onBind");
        return new RandomNumberGenerator.Stub() {
            @Override
            public int nextRandomInt() {
                return Math.abs(random.nextInt());
            }
        };
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(LOG_TAG, "Service onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "Service onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "Service onDestroy");
    }
}
