package com.will.sxlib.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.will.recyclerviewloadingadapter.BaseRecyclerViewHolder;
import com.will.recyclerviewloadingadapter.LoadingAdapter;
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_search_recycler_view);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_search_refresh_layout);
        mRefreshLayout.setEnabled(false);

        mAdapter = new SearchAdapter(getActivity());
        mAdapter.setAllowInterrupt(true);
        mAdapter.setOnItemClickListener(new LoadingAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Object item, BaseRecyclerViewHolder holder) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("result",(BookSearchResult)item);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), holder.itemView, getString(R.string.book_card_transition_name));
                startActivity(intent, options.toBundle());

                //startActivity(new Intent(getActivity(), BookDetailActivity.class));
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnStartLoadingListener(new LoadingAdapter.OnLoadingListener() {
            @Override
            public void onSuccess() {
                mRefreshLayout.setRefreshing(false);
                updateSubtitleText();
            }

            @Override
            public void onFailure() {
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onLoading() {
                mRefreshLayout.setRefreshing(true);
            }
        });


        mSearchBar = (MaterialSearchBar) view.findViewById(R.id.fragment_search_search_view);
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
        return view;
    }
    private void search(String keyword){
        mTitle.setText(keyword);
        mSubtitle.setText("");

        SearchUrlBuilder builder = new SearchUrlBuilder().searchKey(keyword);
        ConfigManager configManager = ConfigManager.getInstance();
        builder.searchWay(configManager.getSearchSettingSearchWay())
                .sortWay(configManager.getSearchSettingSortWay())
                .sortOrder(configManager.getSearchSettingSortOrder());
        mAdapter.setSearchBuilder(builder);
        mAdapter.startLoading();
        mSearchBar.disableSearch();
        revealToolbar(true);
    }
    private void revealToolbar(boolean whether){
        int x = mToolbar.getWidth();
        int y = mToolbar.getHeight() / 2;
        float radius = (float) Math.hypot(x,y);
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

    private void initToolbar(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.fragment_search_toolbar);
        mTitle = (TextView)view.findViewById(R.id.fragment_search_toolbar_title);
        mSubtitle = (TextView) view.findViewById(R.id.fragment_search_toolbar_subtitle);
        setHasOptionsMenu(true);
        mToolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_result_toobar_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        mSearchBar.enableSearch();
        return true;
    }

    @Override
    public boolean onBackPressed() {
        if(mToolbar.getVisibility() == View.VISIBLE){
            mAdapter.discardAllLoadingTaskResult();
            mRefreshLayout.setRefreshing(false);
            revealToolbar(false);
            mAdapter.reset();
            mAdapter.notifyDataSetChanged();
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
