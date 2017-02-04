package com.will.sxlib.search;

import com.will.recyclerviewloadingadapter.BaseRecyclerViewHolder;
import com.will.recyclerviewloadingadapter.LoadingAdapter;
import com.will.sxlib.R;
import com.will.sxlib.bean.BookSearchResult;

/**
 * Created by Will on 2017/2/4.
 */

public class SearchAdapter extends LoadingAdapter<BookSearchResult> {

    public SearchAdapter(){
        super(R.layout.fragment_search_item);
    }

    @Override
    public boolean hasMoreData() {
        return false;
    }

    @Override
    public void loadData(int i) {

    }

    @Override
    public void convert(BaseRecyclerViewHolder baseRecyclerViewHolder, BookSearchResult bookSearchResult) {

    }
}
