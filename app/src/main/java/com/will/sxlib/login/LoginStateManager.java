package com.will.sxlib.login;

import com.will.sxlib.interfaces.Loggable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 2017/8/22.
 * 一个简单的工具，用于通知注册者登陆状态已改变
 */

public class LoginStateManager {

    private static LoginStateManager mInstance;
    private List<Loggable> registeredList = new ArrayList<>();

    private LoginStateManager(){}
    public static LoginStateManager getInstance(){
        if(mInstance == null){
            synchronized(LoginStateManager.class){
                if (mInstance == null){
                    mInstance = new LoginStateManager();
                }
            }
        }
        return mInstance;
    }
    public void register(Loggable instance){
        if(!registeredList.contains(instance)){
            registeredList.add(instance);
        }
    }
    public void notifyLoginStateChanged(int state){
        for(Loggable l : registeredList){
            l.onLoginChanged(state);
        }
    }
}
