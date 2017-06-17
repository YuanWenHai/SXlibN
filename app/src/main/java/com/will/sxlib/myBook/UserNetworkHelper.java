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
    private static final String RENEW_LIST = "http://opac.lib.sx.cn/opac/loan/renewList";

    private String loginSession = "";

    public void getHistoryLoanList(int pageIndex,MyBookNetWorkCallback callback){
        RequestBody body = new FormBody.Builder().add("page",String.valueOf(pageIndex))
                .add("rows","10").build();
        Request.Builder builder = new Request.Builder().url(HISTORY_LOAN_LIST).post(body);
        executeUrlRequest(builder,callback);
    }
    public void getRenewList(MyBookNetWorkCallback callback){
        executeUrlRequest(new Request.Builder().url(RENEW_LIST),callback);
    }
    private void executeUrlRequest(final Request.Builder builder, final MyBookNetWorkCallback callback){
        if(loginSession.isEmpty()){
            getLoginSession(new LoginCallback() {
                @Override
                public void onGetLoginSession(String session) {
                    loginSession = session;
                    executeRequestWithLoginSession(builder,callback);
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
            executeRequestWithLoginSession(builder,callback);
        }
    }
    private void executeRequestWithLoginSession(final Request.Builder builder, final MyBookNetWorkCallback callback){
        Request request = builder.header("cookie",loginSession).build();
        OkHttpUtils.getInstance().requestFromUrl(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 302){
                    loginSession = "";
                    executeUrlRequest(builder,callback);
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
        RequestBody formBody = new FormBody.Builder().add("rdid",account)
                .add("rdPasswd",password).build();
        Request request = new Request.Builder().url(LOGIN_URL).post(formBody).build();
        OkHttpUtils.getInstance().requestFromUrl(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //如果登陆成功，这里将收到一个重定向请求，通过重定向的Url判定是否登陆成功
                if(response.code() == 302 && response.header("Location").contains("http://opac.lib.sx.cn/opac/reader/space")){
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
