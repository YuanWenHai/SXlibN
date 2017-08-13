package com.will.sxlib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.util.TypedValue;
import android.widget.Toast;

import com.will.sxlib.base.BaseApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public static final String md5(final String s) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    @ColorInt
    public static  int getThemeColor(Context context,int colorId){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(colorId,typedValue,true);
        return typedValue.data;
    }

}
