package org.devio.simple;

import android.app.Application;

/**
 * Created by dwl on 2016/2/26.
 */
public class SmmApplication extends Application {

    private static SmmApplication instance = null;

    public static SmmApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Thread.setDefaultUncaughtExceptionHandler(this);
        instance = this;
    }
}
