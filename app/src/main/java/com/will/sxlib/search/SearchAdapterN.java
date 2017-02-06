package com.will.sxlib.search;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.will.recyclerviewloadingadapter.BaseRecyclerViewHolder;
import com.will.recyclerviewloadingadapter.LoadingAdapter;
import com.will.sxlib.R;
import com.will.sxlib.bean.BookSearchResult;

import java.util.List;

/**
 * Created by will on 2017/2/5.
 */

public class SearchAdapterN extends LoadingAdapter<BookSearchResult> implements SearchHelper.RequestCallback {
    private Context mContext;
    private boolean hasMore = true;
    private SearchHelper mHelper;
    private SearchUrlBuilder mSearchBuilder;

    public SearchAdapterN(Context context){
        super(R.layout.fragment_search_item);
        mContext = context;
        mHelper = new SearchHelper();
    }

    public void setSearchBuilder(SearchUrlBuilder builder){
        mSearchBuilder = builder;
    }

    public void disallowLoading(boolean which){
        hasMore = !which;
    }

    @Override
    public boolean hasMoreData() {
        return hasMore;
    }

    @Override
    public void loadData(int i) {
        mHelper.searchWithUrl(mSearchBuilder.pageNumber(i).build(),this);
        Log.e("search url",mSearchBuilder.pageNumber(i).build());
    }

    @Override
    public void convert(BaseRecyclerViewHolder baseRecyclerViewHolder, BookSearchResult bookSearchResult) {
        baseRecyclerViewHolder.setText(R.id.search_item_title,bookSearchResult.getTitle());
        baseRecyclerViewHolder.setText(R.id.search_item_author,"作者："  + bookSearchResult.getAuthor());
        baseRecyclerViewHolder.setText(R.id.search_item_isbn,"ISBN：" + bookSearchResult.getIsbn());
        baseRecyclerViewHolder.setText(R.id.search_item_press,"出版社：" + bookSearchResult.getPress());
        baseRecyclerViewHolder.setText(R.id.search_item_publish_date,"出版日期：" + bookSearchResult.getPublishDate());

        if(bookSearchResult.getCoverUrl() != null){
            Picasso.with(mContext).load(bookSearchResult.getCoverUrl()).into((ImageView)baseRecyclerViewHolder.getView(R.id.search_item_cover));
        }else{
            Picasso.with(mContext).load(R.drawable.no_image_available).into((ImageView)baseRecyclerViewHolder.getView(R.id.search_item_cover));

        }
    }

    @Override
    public void onSuccess(List<BookSearchResult> list) {
        hasMore = list.size() == 10;
        update(list);
    }

    @Override
    public void onFailure() {
        update(false);
    }
}
