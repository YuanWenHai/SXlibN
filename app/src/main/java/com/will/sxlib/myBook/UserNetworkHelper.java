package com.will.sxlib.myBook;

import com.will.sxlib.config.ConfigManager;
import com.will.sxlib.net.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Will on 2017/6/10.
 */

public class UserNetworkHelper {
    private static final String LOGIN_URL = "http://opac.lib.sx.cn/opac/reader/doLogin";
    private static final String HISTORY_LOAN_LIST = "http://opac.lib.sx.cn/opac/loan/historyLoanList";
    private static final String CURRENT_LOAN_LIST = "http://opac.lib.sx.cn/opac/loan/currentLoanList";
    private String loginSession = "";

    public void getHistoryLoanList(MyBookNetWorkCallback callback){
        executeUrlRequest(HISTORY_LOAN_LIST,callback);
    }
    public void getCurrentLoanList(MyBookNetWorkCallback callback){
        executeUrlRequest(CURRENT_LOAN_LIST,callback);
    }
    private void executeUrlRequest(final String url, final MyBookNetWorkCallback callback){
        if(loginSession.isEmpty()){
            getLoginSession(new LoginCallback() {
                @Override
                public void onGetLoginSession(String session) {
                    loginSession = session;
                    executeUrlRequestWithLoginSession(url,callback);
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(call,e);
                }

                @Override
                public void onPasswordIncorrect() {
                    callback.onPasswordIncorrect();
                }
            });
        }else{
            executeUrlRequestWithLoginSession(url,callback);
        }
    }
    private void executeUrlRequestWithLoginSession(final String url,final MyBookNetWorkCallback callback){
        Request request = new Request.Builder().url(url).header("cookie",loginSession).build();
        OkHttpUtils.getInstance().requestFromUrl(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 302){
                    loginSession = "";
                    executeUrlRequest(url,callback);
                }else if(response.code() == 200){
                    callback.onResponse(call,response);
                }
            }
        });
    }
    private void getLoginSession(final LoginCallback callback){
       getLoginSession(ConfigManager.getInstance().getUserAccount(),ConfigManager.getInstance().getUserPassword(),callback);
    }
    public void getLoginSession(String account,String password,final LoginCallback callback){
        RequestBody formBoty = new FormBody.Builder().add("rdid",account)
                .add("rdPasswd",password).build();
        Request request = new Request.Builder().url(LOGIN_URL).post(formBoty).build();
        OkHttpUtils.getInstance().requestFromUrl(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 302){
                    callback.onGetLoginSession(response.header("Set-Cookie").split(";")[0]);
                }else{
                    callback.onPasswordIncorrect();
                }
            }
        });
    }

     static abstract class MyBookNetWorkCallback implements Callback{
        abstract void onPasswordIncorrect();
    }
    interface LoginCallback {
        void onGetLoginSession(String session);
        void onFailure(Call call,IOException e);
        void onPasswordIncorrect();
    }

}
