package com.will.sxlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.will.sxlib.base.BaseFragment;
import com.will.sxlib.search.SearchPageFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<WeakReference<BaseFragment>> fragments = new ArrayList<WeakReference<BaseFragment>>();
    private WeakReference<BaseFragment> currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchPageFragment fragment = new SearchPageFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container,fragment).commit();
        currentFragment = new WeakReference<BaseFragment>(fragment);
    }

    @Override
    public void onBackPressed() {
        if(currentFragment.get() != null && currentFragment.get().onBackPressed()){
           return;
        }
        super.onBackPressed();
    }
}
