package com.example.wanve4k.wanve.base;

import android.app.Application;

import com.example.wanve4k.wanve.bean.UserBean;

import net.arraynetworks.vpn.VPNManager;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhou on 2017/11/28.
 */

public class App extends Application {

    private UserBean user;
    private static App app;

    public static App getInstence() {
        return app;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //VPNManager.initialize(this);
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        app = this;
    }
}
