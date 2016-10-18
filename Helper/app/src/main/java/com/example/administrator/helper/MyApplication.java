package com.example.administrator.helper;

import android.app.Application;

import com.example.administrator.helper.entity.User;

import org.xutils.x;

import c.b.BP;

/**
 * Created by Administrator on 2016/9/25.
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false); //输出debug日志，开启会影响性能
        BP.init(this, "381e8949cca2851afa738898139f924a");

    }
}
