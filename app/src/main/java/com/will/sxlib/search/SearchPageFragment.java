package com.will.sxlib.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.will.sxlib.R;
import com.will.sxlib.base.BaseFragment;
import com.will.sxlib.config.ConfigManager;
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
        searchBar.inflateMenu(R.menu.search_bar_menu);

        searchBar.getMenu().setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search_bar_menu_search_way:

                        new MaterialDialog.Builder(getActivity())
                                .title(R.string.search_way)
                                .items(R.array.search_setting_search_ways)
                                .itemsCallbackSingleChoice(ConfigManager.getInstance().getSearchSettingSearchWay(), new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                        ConfigManager.getInstance().setSearchSettingSearchWay(which);
                                        return true;
                                    }
                                })
                                .positiveText("确定")
                                .show();
                        break;
                    case R.id.search_bar_menu_sort_way:

                        new MaterialDialog.Builder(getActivity())
                                .title(R.string.sort_way)
                                .items(R.array.search_setting_sort_ways)
                                .itemsCallbackSingleChoice(ConfigManager.getInstance().getSearchSettingSortWay(), new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                        ConfigManager.getInstance().setSearchSettingSortWay(which);
                                        return true;
                                    }
                                })
                                .positiveText("确定")
                                .show();

                        break;
                    case R.id.search_bar_menu_sort_order:
                        new MaterialDialog.Builder(getActivity())
                                .title(R.string.sort_order)
                                .items(R.array.search_setting_sort_orders)
                                .itemsCallbackSingleChoice(ConfigManager.getInstance().getSearchSettingSortOrder(), new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                        ConfigManager.getInstance().setSearchSettingSortOrder(which);
                                        return true;
                                    }
                                })
                                .positiveText("确定")
                                .show();

                        break;
                }
                return true;
            }
        });

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
        SearchUrlBuilder builder = new SearchUrlBuilder().searchKey(keyword);
        ConfigManager configManager = ConfigManager.getInstance();
        builder.searchWay(configManager.getSearchSettingSearchWay())
                .sortWay(configManager.getSearchSettingSortWay())
                .sortOrder(configManager.getSearchSettingSortOrder());
        mAdapter.setSearchBuilder(builder);
        mAdapter.disallowLoading(false);
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
    }

}
