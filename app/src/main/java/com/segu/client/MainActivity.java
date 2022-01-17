package com.segu.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.segu.aidl.RandomNumberGenerator;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final MyCoolServiceConnectionImpl serviceConnection = new MyCoolServiceConnectionImpl();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViewById(R.id.bindBtn).setOnClickListener((btn) -> onBindBtnClicked());
        findViewById(R.id.unbindBtn).setOnClickListener((btn) -> onUnbindBtnClicked());
        EditText editText = (EditText) findViewById(R.id.editText);
        findViewById(R.id.generateNumberBtn).setOnClickListener((btn) -> onGenerateNumberBtnClicked(editText));

    }

    private void onGenerateNumberBtnClicked(EditText editText) {
        RandomNumberGenerator calculator = serviceConnection.getRandomNumberGenerator();
        try {
            int number = calculator.nextRandomInt();
            editText.setText(Integer.toString(number));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onBindBtnClicked() {
        try {
            Intent explicitIntent = createExplicitIntent();
            bindService(explicitIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            Log.e("Binding", "Failed to bind service", e);
        }
    }

    private void onUnbindBtnClicked() {
        unbindService(serviceConnection);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }

    private Intent createExplicitIntent() throws IllegalAccessException {
        Intent intent = new Intent("com.example.aidl.REMOTE_CONNECTION");
        List<ResolveInfo> resolvedServices = getPackageManager().queryIntentServices(intent, 0);
        if (resolvedServices.isEmpty()) {
            throw new IllegalAccessException("Failed to get any services");
        }
        ResolveInfo firstResolvedInfo = resolvedServices.get(0);
        ComponentName componentName = new ComponentName(firstResolvedInfo.serviceInfo.packageName, firstResolvedInfo.serviceInfo.name);
        intent.setComponent(componentName);
        return intent;
    }
}