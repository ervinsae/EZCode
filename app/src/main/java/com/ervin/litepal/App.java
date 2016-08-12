package com.ervin.litepal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.leakcanary.LeakCanary;

import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

/**
 * Created by Ervin on 2015/11/11.
 */
public class App extends LitePalApplication {

    private static App instance = null;
    public App getInstance(){
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SQLiteDatabase db = Connector.getDatabase();//建表

        LeakCanary.install(this);
    }
}
