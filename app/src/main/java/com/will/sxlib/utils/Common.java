package com.will.sxlib.utils;

import android.widget.Toast;

import com.will.sxlib.base.BaseApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static String convertTimeWithPattern(long timeMillis,String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }

}
