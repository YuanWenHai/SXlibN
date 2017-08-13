package com.will.sxlib.login;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.will.sxlib.R;
import com.will.sxlib.base.BaseActivity;
import com.will.sxlib.myBook.UserNetworkHelper;
import com.will.sxlib.utils.Common;

import java.io.IOException;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Will on 2017/8/8.
 */

public class LoginActivity extends BaseActivity {
    public static final int START_LOGIN_ACTIVITY = 6666;

    CircularProgressButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeView();
    }
    private void initializeView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_login_toolbar);
        final TextInputLayout accountInput = (TextInputLayout) findViewById(R.id.activity_login_input_account);
        final TextInputLayout passwordInput = (TextInputLayout) findViewById(R.id.activity_login_input_password);
        loginButton = (CircularProgressButton) findViewById(R.id.activity_login_button_login);

        toolbar.setNavigationIcon(R.drawable.md_nav_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountInput.getEditText().getText().toString();
                final String password = passwordInput.getEditText().getText().toString();
                if(account.isEmpty() || account.contains(" ") ){
                    accountInput.setError("账号不能为空或包含空格");
                    accountInput.requestFocus();
                    passwordInput.setError(null);
                    return;
                }else if(password.replaceAll(" ","").isEmpty()){
                    passwordInput.setError("密码不能为空");
                    passwordInput.requestFocus();
                    accountInput.setError(null);
                    return;
                }else{
                    accountInput.setError(null);
                    passwordInput.setError(null);
                }
                loginButton.startAnimation();
                UserNetworkHelper.getInstance().login(account, password, new UserNetworkHelper.MyBookNetworkCallback() {
                    @Override
                    public void onPasswordIncorrect() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                passwordInput.setError("账号与密码不符");
                                passwordInput.requestFocus();
                                loginButton.revertAnimation();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              Common.makeToast("网络连接失败，请稍后重试");
                              loginButton.revertAnimation();
                          }
                      });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loginButton.doneLoadingAnimation(Common.getThemeColor(LoginActivity.this,R.attr.colorAccent), BitmapFactory.decodeResource(getResources(),R.drawable.ic_login_holo_dark));
                            }
                        });
                    }
                });
            }
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

    }
}
