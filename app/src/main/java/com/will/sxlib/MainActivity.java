package com.will.sxlib;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;

import com.will.sxlib.base.BaseActivity;
import com.will.sxlib.base.NavigationFragment;
import com.will.sxlib.db.DBUtil;
import com.will.sxlib.navigation.NavigationFragmentController;
import com.will.sxlib.navigation.NavigationItem;

public class MainActivity extends BaseActivity {
    private NavigationFragmentController mController;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    @Override
    public void onBackPressed() {
        NavigationFragment mFragment = mController.getCurrentFragment(this);
        if(mFragment != null && mFragment.onBackPressed()){
            return;
        }
        super.onBackPressed();
    }

    private void initialize(){
        mDrawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        mController = new NavigationFragmentController();
        mController.setCurrentFragment(this,R.id.fragment_container, NavigationItem.SEARCH);
        mNavigationView = (NavigationView) findViewById(R.id.activity_main_navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                NavigationItem navigationItem;
                switch (item.getItemId()){
                    case R.id.main_menu_search:
                        navigationItem = NavigationItem.SEARCH;
                        break;
                    case R.id.main_menu_my_book:
                        navigationItem = NavigationItem.MY_BOOK;
                        break;
                    default:
                        navigationItem = NavigationItem.SEARCH;
                }
                mDrawer.closeDrawer(Gravity.START);
                mController.setCurrentFragment(MainActivity.this,R.id.fragment_container,navigationItem);
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBUtil.onDestroy();
    }
    public void showNavigationDrawer(){
        if(!mDrawer.isDrawerOpen(Gravity.START)){
            mDrawer.openDrawer(Gravity.START);
        }

    }
}
