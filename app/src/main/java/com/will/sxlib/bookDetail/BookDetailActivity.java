package com.will.sxlib.bookDetail;

import android.os.Bundle;

import com.will.sxlib.R;
import com.will.sxlib.base.BaseActivity;
import com.will.sxlib.bookDetail.bean.BookDescription;
import com.will.sxlib.constant.Urls;
import com.will.sxlib.decode.JsonDecoder;
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
        //init();
        //getFragmentManager().beginTransaction().add(R.id.book_detail_container,new BookStateFragment()).commit();
        bookDescriptionTest();
        bookStateTest();
    }

    private void bookDescriptionTest(){
        OkHttpUtils.getInstance().requestFromUrl(Urls.DOUBAN_ISBN_SEARCH_URL+"9787121181085", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BookDescription description = new BookDescription(response.body().string());
                final BookDescriptionFragment fragment = new BookDescriptionFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("description",description);
                fragment.setArguments(bundle);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getFragmentManager().beginTransaction().add(R.id.book_detail_description_container,fragment).commit();
                    }
                });
                }
        });
    }
    private void bookStateTest(){
        OkHttpUtils.getInstance().requestFromUrl(Urls.SXLIB_REQUEST_HOLDING_URL + "617503", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final BookStateFragment fragment = new BookStateFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("book_state", JsonDecoder.getBookStateFromJsonString(response.body().string()));
                fragment.setArguments(bundle);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getFragmentManager().beginTransaction().add(R.id.book_detail_state_container,fragment).commit();
                    }
                });
            }
        });
    }
}
