package com.will.sxlib.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.will.recyclerviewloadingadapter.BaseLoadingAdapter;
import com.will.recyclerviewloadingadapter.BaseRecyclerViewHolder;
import com.will.sxlib.R;
import com.will.sxlib.base.NavigationFragment;
import com.will.sxlib.bean.BookSearchResult;
import com.will.sxlib.bookDetail.BookDetailActivity;
import com.will.sxlib.config.ConfigManager;
import com.will.sxlib.db.DBUtil;

/**
 * Created by will on 2017/2/4.
 */

public class SearchPageFragmentMain extends NavigationFragment {
    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private Toolbar mToolbar;
    private MaterialSearchBar mSearchBar;
    private SwipeRefreshLayout mRefreshLayout;
    private TextView mTitle;
    private TextView mSubtitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        initToolbar(view);
        initSearchBar(view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_search_recycler_view);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_search_refresh_layout);
        mRefreshLayout.setEnabled(false);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mAdapter = new SearchAdapter();
        mAdapter.setOnItemClickListener(new BaseLoadingAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Object item, BaseRecyclerViewHolder holder) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("result",(BookSearchResult)item);
               /* ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), holder.itemView, getString(R.string.book_card_transition_name));
                startActivity(intent, options.toBundle());*/
                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }
    private void search(String keyword){
        mTitle.setText(keyword);
        mSubtitle.setText("搜索中..");

        SearchUrlBuilder builder = new SearchUrlBuilder().searchKey(keyword);
        ConfigManager configManager = ConfigManager.getInstance();
        builder.searchWay(configManager.getSearchSettingSearchWay())
                .sortWay(configManager.getSearchSettingSortWay())
                .sortOrder(configManager.getSearchSettingSortOrder());
        mRefreshLayout.setRefreshing(true);
        mAdapter.start(builder, new BaseLoadingAdapter.OnLoadingListener() {
            @Override
            public void onResult(boolean which) {
                updateSubtitleText();
                mRefreshLayout.setRefreshing(false);
            }
        });
        mSearchBar.disableSearch();
        revealToolbar(true);
    }
    private void revealToolbar(boolean whether){
        int x = mToolbar.getWidth();
        int y = mToolbar.getHeight() / 2;
        float radius = (float) Math.hypot(x,y);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            mToolbar.setVisibility(whether ? View.VISIBLE : View.INVISIBLE);
        }else{
            if(whether){
                Animator animator = ViewAnimationUtils.createCircularReveal(mToolbar,x,y,0,radius);
                mToolbar.setVisibility(View.VISIBLE);
                animator.start();
            }else{
                Animator animator = ViewAnimationUtils.createCircularReveal(mToolbar,x,y,radius,0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mToolbar.setVisibility(View.INVISIBLE);
                    }
                });
                animator.start();
            }
        }

    }

    private void initToolbar(View rootView){
        mToolbar = (Toolbar) rootView.findViewById(R.id.fragment_search_toolbar);
        mToolbar.setNavigationIcon(R.drawable.md_nav_back);
        mTitle = (TextView)rootView.findViewById(R.id.fragment_search_toolbar_title);
        mSubtitle = (TextView) rootView.findViewById(R.id.fragment_search_toolbar_subtitle);
        setHasOptionsMenu(true);
        mToolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
    }

    private void initSearchBar(View rootView){
        View maskView = rootView.findViewById(R.id.fragment_search_search_view_mask);
        maskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSearchBar.isSearchEnabled()){
                    mSearchBar.disableSearch();
                }
            }
        });
        mSearchBar = (MaterialSearchBar) rootView.findViewById(R.id.fragment_search_search_view);
        mSearchBar.setMaskView(maskView);
        mSearchBar.inflateMenu(R.menu.search_bar_menu);
        mSearchBar.setLastSuggestions(DBUtil.getInstance(getActivity()).getSearchHistoryFromDB());
        mSearchBar.getMenu().setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
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

        mSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                search(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_result_toobar_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        if(item.getItemId() == R.id.search_result_toolbar_menu_search){
            mSearchBar.enableSearch();
        }
       return true;
    }

    @Override
    public boolean onBackPressed() {
        if(mToolbar.getVisibility() == View.VISIBLE){
            mAdapter.clear(true);
            mRefreshLayout.setRefreshing(false);
            revealToolbar(false);
            return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        DBUtil.getInstance(getActivity()).insertSearchHistoryToDB(mSearchBar.getLastSuggestions());
        super.onPause();
    }

    private void updateSubtitleText(){
        int resultNumber;
        if(mAdapter.getData().size() == 0){
            resultNumber = 0;
        }else{
            resultNumber = mAdapter.getData().get(0).getResultNumber();
        }
        String text = "共发现相关条目"+resultNumber+"条";
        mSubtitle.setText(text);
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.adapter_item_enter_animation);
        mSubtitle.startAnimation(animation);
    }
}
