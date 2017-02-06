package com.will.sxlib.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.will.sxlib.R;
import com.will.sxlib.base.BaseFragment;
import com.will.sxlib.utils.Common;

/**
 * Created by will on 2017/2/4.
 */

public class SearchPageFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private SearchAdapterN mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_search_recycler_view);
        mAdapter = new SearchAdapterN(getActivity());
        mAdapter.disallowLoading(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        final MaterialSearchBar searchBar = (MaterialSearchBar) view.findViewById(R.id.fragment_search_search_view);
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                Common.makeToast("on search state changed");
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
               search(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        return view;
    }
    private void search(String keyword){
        mAdapter.setSearchBuilder(new SearchUrlBuilder().searchKey(keyword));
        mAdapter.disallowLoading(false);
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
    }
}
