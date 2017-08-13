package com.will.sxlib.myBook.loanList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.recyclerviewloadingadapter.BaseLoadingAdapter;
import com.will.sxlib.R;
import com.will.sxlib.base.BaseFragment;

/**
 * Created by Will on 2017/6/18.
 */

public class LoanListFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.recycler_with_refresh,container,false);
        refreshLayout.setEnabled(false);
        RecyclerView recyclerView = (RecyclerView) refreshLayout.findViewById(R.id.recycler_with_refresh_recycler_view);
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);
        LoanListAdapter adapter = new LoanListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(adapter);
        refreshLayout.setRefreshing(true);
        adapter.load(false, new BaseLoadingAdapter.OnLoadingListener() {
            @Override
            public void onResult(boolean which) {
                refreshLayout.setRefreshing(false);
            }
        });
        return refreshLayout;
    }
}
