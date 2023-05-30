package com.google.privatesharingapp;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.google.privatesharingapp.mmkv.KVConfigImpl;

import org.litepal.LitePal;

public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize LitePal
        LitePal.initialize(this);
        KVConfigImpl.init(this, "demo");
        //Initialize Baidu Map
        SDKInitializer.setAgreePrivacy(this, true);
        SDKInitializer.initialize(this);
        //Use BD09LL coordinates
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
