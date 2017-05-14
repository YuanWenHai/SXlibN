package com.will.sxlib.search;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.will.recyclerviewloadingadapter.AsyncLoadingAdapter;
import com.will.recyclerviewloadingadapter.BaseRecyclerViewHolder;
import com.will.sxlib.R;
import com.will.sxlib.bean.BookSearchResult;
import com.will.sxlib.decode.HtmlUtils;
import com.will.sxlib.net.OkHttpUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by will on 2017/5/14.
 */

public class SearchAdapterN extends AsyncLoadingAdapter<BookSearchResult> {
    private boolean hasMoreData = true;
    private SearchUrlBuilder mUrlBuilder;

    public SearchAdapterN(){
        super(R.layout.fragment_search_item);
    }

    public void start(SearchUrlBuilder builder,OnLoadingListener listener){
        mUrlBuilder = builder;
        load(true,listener);
    }


    @Override
    public Call obtainTargetCall(int page) {
        return OkHttpUtils.getInstance().wrapUrlToCall(mUrlBuilder.pageNumber(page).build());
    }

    @Override
    public List<BookSearchResult> getCorrespondingData(Response response) {
        String content = "";
        try{
            content = response.body().string();
        }catch (IOException i){
            i.printStackTrace();
        }finally {
            response.close();
        }
        List<BookSearchResult> list = HtmlUtils.getBookSearchResultFromHtml(content);
        hasMoreData = list.size() == 10;
        return list;
    }

    @Override
    public boolean hasMoreData() {
        return hasMoreData;
    }

    @Override
    public void convert(BaseRecyclerViewHolder baseRecyclerViewHolder, BookSearchResult bookSearchResult) {
        baseRecyclerViewHolder.setText(R.id.search_item_title,bookSearchResult.getTitle());
        baseRecyclerViewHolder.setText(R.id.search_item_author,"作者："  + bookSearchResult.getAuthor());
        baseRecyclerViewHolder.setText(R.id.search_item_isbn,"ISBN：" + bookSearchResult.getIsbn());
        baseRecyclerViewHolder.setText(R.id.search_item_press,"出版社：" + bookSearchResult.getPress());
        baseRecyclerViewHolder.setText(R.id.search_item_publish_date,"出版日期：" + bookSearchResult.getPublishDate());

        if(bookSearchResult.getCoverUrl() != null){
            Picasso.with(getRecyclerView().getContext()).load(bookSearchResult.getCoverUrl()).placeholder(R.drawable.loading_image).into((ImageView)baseRecyclerViewHolder.getView(R.id.search_item_cover));
        }else{
            Picasso.with(getRecyclerView().getContext()).load(R.drawable.no_image_available).into((ImageView)baseRecyclerViewHolder.getView(R.id.search_item_cover));

        }
    }
}
