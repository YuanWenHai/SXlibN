package com.will.sxlib.net;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by will on 2017/2/3.
 */

public class OkHttpUtils {
    private static OkHttpUtils mInstance;

    private final OkHttpClient mClient;

    private OkHttpUtils(){
         mClient = new OkHttpClient.Builder().followRedirects(false).followSslRedirects(false).build();
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

    public Call wrapUrlToCall(String url){
        Request request = new Request.Builder().url(url).build();
        return getInstance().getClient().newCall(request);
    }
    public void requestFromUrl(final String url, final Callback callback){
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onResponse(call,response);
            }
        });
    }
    public OkHttpClient getClient(){
        return mClient;
    }
    public void requestFromUrl(Request request,Callback callback){
        mClient.newCall(request).enqueue(callback);
    }
}
