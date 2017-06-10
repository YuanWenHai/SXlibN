package com.will.sxlib.net;

import java.util.WeakHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by will on 2017/4/16.
 * Url Request管理对象，便于在页面关闭时终止所有任务。
 */

public class UrlTaskExecutor {
    private WeakHashMap<String,Call> mCalls = new WeakHashMap<>();

    public void requestFromUrl(String url, Callback callback){
        Request request = new Request.Builder().url(url).build();
        OkHttpClient mClient = OkHttpUtils.getInstance().getClient();
        Call call = mClient.newCall(request);
        mCalls.put(url,call);
        call.enqueue(callback);
    }
    public void removeAllTask(){
        for(Call call : mCalls.values()){
            if(call != null ){
                call.cancel();
            }
        }
    }
    public void RemoveTask(String url){
        Call call = mCalls.get(url);
        if(call != null){
            call.cancel();
        }
    }
}
