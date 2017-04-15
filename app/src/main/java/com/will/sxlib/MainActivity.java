package com.will.sxlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.will.sxlib.base.NavigationFragment;
import com.will.sxlib.db.DBUtil;
import com.will.sxlib.search.SearchPageFragmentMain;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<WeakReference<NavigationFragment>> fragments = new ArrayList<WeakReference<NavigationFragment>>();
    private WeakReference<NavigationFragment> currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchPageFragmentMain fragment = new SearchPageFragmentMain();
        getFragmentManager().beginTransaction().add(R.id.fragment_container,fragment).commit();
        currentFragment = new WeakReference<NavigationFragment>(fragment);
    }

    @Override
    public void onBackPressed() {
        if(currentFragment.get() != null && currentFragment.get().onBackPressed()){
           return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBUtil.onDestroy();
    }
}
