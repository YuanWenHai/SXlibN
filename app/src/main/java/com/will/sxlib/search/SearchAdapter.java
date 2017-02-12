package com.will.sxlib.search;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.will.recyclerviewloadingadapter.BaseRecyclerViewHolder;
import com.will.recyclerviewloadingadapter.LoadingAdapter;
import com.will.sxlib.R;
import com.will.sxlib.bean.BookSearchResult;

import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by will on 2017/2/5.
 * <p>一些说明</p>
 * <p>基于LoadingAdapter拓展，增加了一些需要的特性</p>
 * <p>因为有中断当前加载并进行新加载的需求，故使用{@link #updateWithVerification(List, int)}方法,意图防止异步加载导致的错误</p>
 *
 */

public class SearchAdapter extends LoadingAdapter<BookSearchResult> {
    private Context mContext;
    private boolean hasMore = true;
    private SearchHelper mHelper;
    private SearchUrlBuilder mSearchBuilder;
    private WeakHashMap<Integer,SearchHelper.RequestCallback> mCallbacks;

    public SearchAdapter(Context context){
        super(R.layout.fragment_search_item);
        mContext = context;
        mHelper = new SearchHelper();
        mCallbacks = new WeakHashMap<>();
    }

    public void setSearchBuilder(SearchUrlBuilder builder){
        mSearchBuilder = builder;
    }

    @Override
    public boolean hasMoreData() {
        return hasMore;
    }

    @Override
    public void loadData(int page,int requestID) {
        SearchHelper.RequestCallback callback;
        callback = mCallbacks.get(requestID);
        if(callback == null){
            callback = new MyLoadingCallback(requestID);
            mCallbacks.put(requestID,callback);
        }
        mHelper.searchWithUrl(mSearchBuilder.pageNumber(page).build(),callback);
        Log.e("search url",mSearchBuilder.pageNumber(page).build());
    }

    @Override
    public void convert(BaseRecyclerViewHolder baseRecyclerViewHolder, BookSearchResult bookSearchResult) {
        baseRecyclerViewHolder.setText(R.id.search_item_title,bookSearchResult.getTitle());
        baseRecyclerViewHolder.setText(R.id.search_item_author,"作者："  + bookSearchResult.getAuthor());
        baseRecyclerViewHolder.setText(R.id.search_item_isbn,"ISBN：" + bookSearchResult.getIsbn());
        baseRecyclerViewHolder.setText(R.id.search_item_press,"出版社：" + bookSearchResult.getPress());
        baseRecyclerViewHolder.setText(R.id.search_item_publish_date,"出版日期：" + bookSearchResult.getPublishDate());

        if(bookSearchResult.getCoverUrl() != null){
            Picasso.with(mContext).load(bookSearchResult.getCoverUrl()).placeholder(R.drawable.loading_image).into((ImageView)baseRecyclerViewHolder.getView(R.id.search_item_cover));
        }else{
            Picasso.with(mContext).load(R.drawable.no_image_available).into((ImageView)baseRecyclerViewHolder.getView(R.id.search_item_cover));

        }
    }


    @Override
    protected Animation getItemAnimation() {
        return AnimationUtils.loadAnimation(mContext,R.anim.adapter_item_enter_animation);
    }
     private class MyLoadingCallback implements SearchHelper.RequestCallback{
        private int requestID;
         MyLoadingCallback(int requestID){
            this.requestID = requestID;
        }
         @Override
         public void onSuccess(List<BookSearchResult> list) {
             hasMore = list.size() == 10;
             updateWithVerification(list,requestID);
         }

         @Override
         public void onFailure() {
            updateWithVerification(false,requestID);
         }
     }
}
