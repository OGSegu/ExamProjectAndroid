package com.segu.aidl;

import static org.junit.Assert.assertTrue;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.segu.client.MyCoolServiceConnectionImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private final MyCoolServiceConnectionImpl serviceConnection = new MyCoolServiceConnectionImpl();

    @Before
    public void init() throws IllegalAccessException, InterruptedException {
        Intent intent = createExplicitIntent();
        appContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Thread.sleep(3000);
    }

    @Test
    public void receiveNumber() throws IllegalAccessException, RemoteException, InterruptedException {
        int receivedNumber = serviceConnection.getRandomNumberGenerator().nextRandomInt();
        Log.i("ServiceTest", "Received number " + receivedNumber);
        assertTrue(receivedNumber > 0 && receivedNumber < Integer.MAX_VALUE);
    }

    public Intent createExplicitIntent() throws IllegalAccessException {
        Intent intent = new Intent("com.example.aidl.REMOTE_CONNECTION");
        List<ResolveInfo> resolvedServices = appContext.getPackageManager().queryIntentServices(intent, 0);
        if (resolvedServices.isEmpty()) {
            throw new IllegalAccessException("Failed to get any services");
        }
        ResolveInfo firstResolvedInfo = resolvedServices.get(0);
        ComponentName componentName = new ComponentName(firstResolvedInfo.serviceInfo.packageName, firstResolvedInfo.serviceInfo.name);
        intent.setComponent(componentName);
        return intent;
    }
}