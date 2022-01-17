package com.segu.client;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.segu.aidl.RandomNumberGenerator;


public class MyCoolServiceConnectionImpl implements ServiceConnection {

    private volatile RandomNumberGenerator randomNumberGenerator;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder binder) {
        randomNumberGenerator = RandomNumberGenerator.Stub.asInterface(binder);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        randomNumberGenerator = null;
    }

    public RandomNumberGenerator getRandomNumberGenerator() {
        return randomNumberGenerator;
    }
}
