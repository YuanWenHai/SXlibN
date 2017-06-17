package com.will.sxlib.myBook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.sxlib.R;
import com.will.sxlib.base.NavigationFragment;
import com.will.sxlib.decode.HtmlUtils;
import com.will.sxlib.myBook.bean.MyRenewBookItem;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Will on 2017/6/10.
 */

public class MyBookFragment extends NavigationFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

     /*   new UserNetworkHelper().getHistoryLoanList(2, new UserNetworkHelper.MyBookNetWorkCallback() {
            @Override
            void onPasswordIncorrect() {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = response.body().string();
                response.close();
                List<MyHistoricalBookItem> list =  HtmlUtils.getMyHistoricalItemFromHtml(content);
                Log.e("operation date is",list.get(0).getOperationDate());
            }
        });*/
     new UserNetworkHelper().getRenewList(new UserNetworkHelper.MyBookNetWorkCallback() {
         @Override
         void onPasswordIncorrect() {

         }

         @Override
         public void onFailure(Call call, IOException e) {

         }

         @Override
         public void onResponse(Call call, Response response) throws IOException {
             String html = response.body().string();
             response.close();
             List<MyRenewBookItem> list = HtmlUtils.getMyRenewBookItemFromHtml(html);
             Log.e("return date",list.get(0).getReturnDate());
         }
     });
        return inflater.inflate(R.layout.fragment_my_book,container,false);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
