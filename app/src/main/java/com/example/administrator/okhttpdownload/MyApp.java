package com.example.administrator.okhttpdownload;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class MyApp extends Application {
    /**
     *  李
     */
    public static Context sContext;//全局的Context对象
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
