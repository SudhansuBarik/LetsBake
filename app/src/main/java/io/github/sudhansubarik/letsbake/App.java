package io.github.sudhansubarik.letsbake;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App mInstance;

    public static Context getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = (App) getApplicationContext();
    }
}
