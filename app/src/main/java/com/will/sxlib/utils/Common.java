package com.will.sxlib.utils;

import android.widget.Toast;

import com.will.sxlib.base.BaseApplication;

/**
 * Created by will on 2017/2/4.
 */

public class Common {
    private static Toast mToast;
    public static void makeToast(String message,int length){
        if(mToast == null){
            mToast = Toast.makeText(BaseApplication.getGlobalContext(),"",length);
        }
        mToast.setText(message);
        mToast.show();
    }
    public static void makeToast(String message){
        makeToast(message,Toast.LENGTH_SHORT);
    }
}
