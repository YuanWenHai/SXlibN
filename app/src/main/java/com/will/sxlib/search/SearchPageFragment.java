package com.will.sxlib.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        MaterialSearchBar searchBar = (MaterialSearchBar) view.findViewById(R.id.fragment_search_search_view);
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                Common.makeToast("on search state changed");
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Common.makeToast(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                Common.makeToast("on button clicked");
            }
        });
        return view;
    }
}
