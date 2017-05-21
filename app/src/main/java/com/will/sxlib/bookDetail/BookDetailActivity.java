package com.will.sxlib.bookDetail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will.sxlib.R;
import com.will.sxlib.base.BaseActivity;
import com.will.sxlib.bean.BookSearchResult;
import com.will.sxlib.bookDetail.bean.BookDescription;
import com.will.sxlib.constant.Urls;
import com.will.sxlib.decode.JsonDecoder;
import com.will.sxlib.net.RequestHelper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by will on 2017/2/19.
 */

public class BookDetailActivity extends BaseActivity {

    private RequestHelper mRequestHelper;
    private android.support.v7.widget.Toolbar mToolbar;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        mToolbar = (Toolbar) findViewById(R.id.book_detail_toolbar);
        mToolbar.setNavigationIcon(R.drawable.md_nav_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        BookSearchResult result  = (BookSearchResult) getIntent().getSerializableExtra("result");
        if(result != null){
            mRequestHelper = new RequestHelper();
            initDescription(result.getIsbn());
            initState(result.getRecno());
            initBaseInfo(result);
        }

    }

    private void initBaseInfo(BookSearchResult result){
        mToolbar.setTitle(result.getTitle());
        ImageView cover = (ImageView) findViewById(R.id.book_detail_cover);
        TextView title = (TextView) findViewById(R.id.book_detail_title);
        TextView author = (TextView) findViewById(R.id.book_detail_author);
        TextView isbn = (TextView) findViewById(R.id.book_detail_isbn);
        TextView publishDate = (TextView) findViewById(R.id.book_detail_publish_date);
        TextView press = (TextView) findViewById(R.id.book_detail_press);
        Picasso.with(this).load(result.getCoverUrl()).into(cover);
        title.setText(result.getTitle());
        author.setText(("作者："+result.getAuthor()));
        isbn.setText(("ISBN："+result.getIsbn()));
        publishDate.setText(("出版日期："+result.getPublishDate()));
        press.setText(("出版社："+result.getPress()));
    }

    private void initDescription(String isbn){
        mRequestHelper.requestFromUrl(Urls.DOUBAN_ISBN_SEARCH_URL+isbn, new Callback() {
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
                        if(!BookDetailActivity.this.isDestroyed()){
                            getFragmentManager().beginTransaction().add(R.id.book_detail_description_container,fragment).commit();
                        }
                    }
                });
                }
        });
    }
    private void initState(String recNo){
        mRequestHelper.requestFromUrl(Urls.SXLIB_REQUEST_HOLDING_URL + recNo, new Callback() {
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
                        if(!BookDetailActivity.this.isDestroyed()){
                            getFragmentManager().beginTransaction().add(R.id.book_detail_state_container,fragment).commit();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
       mRequestHelper.removeAllTask();
        super.onDestroy();
    }
}
