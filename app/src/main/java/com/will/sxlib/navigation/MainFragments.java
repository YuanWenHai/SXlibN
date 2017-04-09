package com.will.sxlib.navigation;

import android.os.Handler;
import android.os.Looper;

import com.will.sxlib.base.BaseFragment;


/**
 * Created by will on 2017/4/9.
 * 未完成，预期其负责首页fragment的初始化，实例与相关数据的保存、获取. 2017/4/9
 */

public class MainFragments {
    private static MainFragments mInstance;
    private Handler mHandler;
    private Class<?extends BaseFragment>
    private MainFragments(){
        mHandler = new Handler(Looper.getMainLooper());
    }
    public static MainFragments getInstance(){
        if(mInstance == null){
            synchronized(MainFragments.class){
                if(mInstance == null){
                    mInstance = new MainFragments();
                }
            }
        }
        return mInstance;
    }

    public void initialize(){
        //do initialization and others synchronize
    }

    public void initializeAsync(final NavigationInitializeCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // do initialization and others
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFinish();
                    }
                });
            }
        }).start();
    }

    interface NavigationInitializeCallback {
        void onFinish();
    }
}
