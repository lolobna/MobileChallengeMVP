package com.example.mobilechallengemvp;

import android.app.Application;

import com.example.mobilechallengemvp.di.AppComponent;
import com.example.mobilechallengemvp.di.DaggerAppComponent;

public class MyApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}