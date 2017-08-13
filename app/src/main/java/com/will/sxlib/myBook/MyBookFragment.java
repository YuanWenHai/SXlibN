package com.will.sxlib.myBook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.sxlib.R;
import com.will.sxlib.base.NavigationFragment;
import com.will.sxlib.interfaces.Drawer;

/**
 * Created by Will on 2017/6/10.
 */

public class MyBookFragment extends NavigationFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_book,container,false);
        initializeView(view);
        return view;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void initializeView(View root){
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.fragment_my_book_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_holo_dark_padding_10);

        AppCompatActivity parent = (AppCompatActivity) getActivity();
        parent.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Drawer)getActivity()).triggerDrawer();
            }
        });
        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.fragment_my_book_tab_layout);
        ViewPager viewPager = (ViewPager) root.findViewById(R.id.fragment_my_book_view_pager);
        tabLayout.addTab(tabLayout.newTab().setText("借阅列表"));
        tabLayout.addTab(tabLayout.newTab().setText("历史借阅"));
        LoanListViewPagerAdapter pagerAdapter = new LoanListViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
