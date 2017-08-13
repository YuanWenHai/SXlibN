package com.will.sxlib.myBook;

import com.will.sxlib.config.ConfigManager;
import com.will.sxlib.net.OkHttpUtils;
import com.will.sxlib.utils.Common;

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
    private static final String HISTORY_LOAN_LIST_URL = "http://opac.lib.sx.cn/opac/loan/historyLoanList";
    private static final String RENEW_LIST_URL = "http://opac.lib.sx.cn/opac/loan/renewList";
    private static final String DO_RENEW_URL = "http://opac.lib.sx.cn/opac/loan/doRenew";
    private static final String CHANGE_PASSWORD_URL = "http://opac.lib.sx.cn/opac/reader/updatePassword";
    private static final String READER_SPACE_URL = "http://opac.lib.sx.cn/opac/reader/space";

    private static UserNetworkHelper mInstance;

    private String loginSession = "";
    private UserNetworkHelper(){}

    public static UserNetworkHelper getInstance(){
        if(mInstance == null){
            synchronized(UserNetworkHelper.class){
                if(mInstance == null){
                    mInstance = new UserNetworkHelper();
                }
            }
        }
        return mInstance;
    }


    /**
     * 获取历史借阅记录，每次获取十条
     * @param pageIndex 页数，从1开始
     * @param callback callback
     */
    public void getHistoryLoanList(int pageIndex,MyBookNetworkCallback callback){
        RequestBody body = new FormBody.Builder().add("page",String.valueOf(pageIndex))
                .add("rows","10").build();
        Request.Builder builder = new Request.Builder().url(HISTORY_LOAN_LIST_URL).post(body);
        executeRequest(builder,callback);
    }
    public void getRenewList(MyBookNetworkCallback callback){
        executeRequest(new Request.Builder().url(RENEW_LIST_URL),callback);
    }
    public void executeRenew(String barCode, MyBookNetworkCallback callback){
        RequestBody formBody = new FormBody.Builder().add("barcodeList",barCode).build();
        Request.Builder builder = new Request.Builder().url(DO_RENEW_URL).post(formBody);
        executeRequest(builder,callback);
    }
    private void executeRequest(final Request.Builder builder, final MyBookNetworkCallback callback){
        if(loginSession.isEmpty()){
            getLoginSession(new LoginCallback() {
                @Override
                public void onGetLoginSession(String session) {
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
    private void executeRequestWithLoginSession(final Request.Builder builder, final MyBookNetworkCallback callback){
        Request request = builder.header("cookie",loginSession).build();
        OkHttpUtils.getInstance().makeRequest(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 302){
                    loginSession = "";
                    executeRequest(builder,callback);
                }else if(response.code() == 200){
                    callback.onResponse(call,response);
                }
            }
        });
    }
    private void getLoginSession(final LoginCallback callback){
       getLoginSession(ConfigManager.getInstance().getUserAccount(),ConfigManager.getInstance().getUserPassword(),callback);
    }
    private void getLoginSession(String account,String password,final LoginCallback callback){
        RequestBody formBody = new FormBody.Builder().add("rdid",account)
                .add("rdPasswd",password).build();
        Request request = new Request.Builder().url(LOGIN_URL).post(formBody).build();
        OkHttpUtils.getInstance().makeRequest(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //如果登陆成功，这里将收到一个重定向请求，通过重定向的Url判定是否登陆成功
                if(response.code() == 302 && response.header("Location").contains("http://opac.lib.sx.cn/opac/reader/space")){
                    loginSession = response.header("Set-Cookie").split(";")[0];
                    callback.onGetLoginSession(loginSession);
                    //200为密码错误的返回码
                }else if (response.code() == 200){
                    callback.onPasswordIncorrect();
                    //这里处理因为手机网络状态导致的404,500之类的错误码，也将其判定为网络错误
                }else{
                    callback.onFailure(call,new IOException());
                }
            }
        });
    }
    public void changePassword(String account,String newPassword,MyBookNetworkCallback callback){
        RequestBody formBody = new FormBody.Builder().add("rdid",account).add("newPassword",newPassword).build();
        Request.Builder builder = new Request.Builder().url(CHANGE_PASSWORD_URL).post(formBody);
        executeRequest(builder,callback);
    }

    /**
     * 登陆账户<br/>
     * 这里手动调用了登陆方法并传入了账号密码，而非通过SharedPreference设置再读出，是出于SharedPreference.Editor的apply()方法的异步性考虑
     * @param account
     * @param password
     * @param callback
     */
    public void login(final String account, String password, final MyBookNetworkCallback callback){
        final String md5Password = Common.md5(password);
        getLoginSession(account, md5Password, new LoginCallback() {
            @Override
            public void onGetLoginSession(String session) {
                ConfigManager.getInstance().setUserAccount(account);
                ConfigManager.getInstance().setUserPassword(md5Password);
                Request.Builder builder = new Request.Builder().url(READER_SPACE_URL);
                executeRequest(builder,callback);
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
    }

     public static abstract class MyBookNetworkCallback implements Callback{
        public abstract void onPasswordIncorrect();
    }
    interface LoginCallback {
        void onGetLoginSession(String session);
        void onFailure(Call call,IOException e);
        void onPasswordIncorrect();
    }

}
