package com.will.sxlib.bookDetail;

import android.os.Bundle;
import android.util.Log;

import com.will.sxlib.R;
import com.will.sxlib.base.BaseActivity;
import com.will.sxlib.bookDetail.bean.BookDescription;
import com.will.sxlib.constant.Urls;
import com.will.sxlib.net.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by will on 2017/2/19.
 */

public class BookDetailActivity extends BaseActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        init();
        OkHttpUtils.getInstance().requestFromUrl(Urls.DOUBAN_ISBN_SEARCH_URL+"978-7-5083-5393-7", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("catalog",new BookDescription(response.body().string()).getCatalog())
;            }
        });
    }

    private void init(){

    }
}
