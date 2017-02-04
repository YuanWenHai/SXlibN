package com.will.sxlib.net;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by will on 2017/2/3.
 */

public class OkHttpUtils {
    private static OkHttpUtils mInstance;

    private final OkHttpClient mClient = new OkHttpClient();

    private OkHttpUtils(){

    }

    public static OkHttpUtils getInstance(){
        if(mInstance == null){
            synchronized(OkHttpUtils.class){
                if(mInstance == null){
                    mInstance = new OkHttpUtils();
                }
            }
        }
        return mInstance;
    }

    public void requestFromUrl(String url, Callback callback){
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(callback);
    }
    public void requestFromUrl(Request request,Callback callback){
        mClient.newCall(request).enqueue(callback);
    }

}
