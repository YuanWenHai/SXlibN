package com.will.sxlib.myBook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.sxlib.R;
import com.will.sxlib.base.NavigationFragment;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Will on 2017/6/10.
 */

public class MyBookFragment extends NavigationFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        new UserNetworkHelper().getHistoryLoanList(new UserNetworkHelper.MyBookNetWorkCallback() {
            @Override
            void onPasswordIncorrect() {
                Log.e("error","password incorrect");
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error","network error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("succeed,content is",response.body().string());
            }
        });
        return inflater.inflate(R.layout.fragment_my_book,container,false);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
