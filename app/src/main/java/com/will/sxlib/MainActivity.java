package com.will.sxlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.will.sxlib.base.MainBaseFragment;
import com.will.sxlib.db.DBUtil;
import com.will.sxlib.search.SearchPageFragmentMain;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<WeakReference<MainBaseFragment>> fragments = new ArrayList<WeakReference<MainBaseFragment>>();
    private WeakReference<MainBaseFragment> currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchPageFragmentMain fragment = new SearchPageFragmentMain();
        getFragmentManager().beginTransaction().add(R.id.fragment_container,fragment).commit();
        currentFragment = new WeakReference<MainBaseFragment>(fragment);
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
