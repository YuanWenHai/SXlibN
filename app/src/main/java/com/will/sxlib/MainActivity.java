package com.will.sxlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.will.sxlib.search.SearchPageFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().add(R.id.fragment_container,new SearchPageFragment()).commit();
        /*
        String url = new SearchUrlBuilder().searchKey("java").sortWay(SearchUrlBuilder.SORT_WAY_DATE).build();
        Log.e("url ",url);
        OkHttpUtils.getInstance().requestFromUrl(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<BookSearchResult> results = HtmlUtils.getBookSearchResultFromHtml(response.body().string());

                new SearchHelper().requestCoversWithISBNs(results, new SearchHelper.RequestCoverCallback() {
                    @Override
                    public void onSuccess(List<BookSearchResult> list) {
                        BookSearchResult result = list.get(0);
                        Log.e("title",result.getTitle());
                        Log.e("author",result.getAuthor());
                        Log.e("publish date",result.getPublishDate());
                        Log.e("press",result.getPress());
                        Log.e("isbn",result.getIsbn());
                        Log.e("cover url",result.getCoverUrl());
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });
         */
    }


}
