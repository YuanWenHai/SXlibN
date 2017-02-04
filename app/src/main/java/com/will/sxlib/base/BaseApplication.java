package com.will.sxlib.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by will on 2017/2/4.
 */

public class BaseApplication extends Application {
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }
    public static Context getGlobalContext(){
        return applicationContext;
    }
}
