package com.will.sxlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;

import com.will.sxlib.base.BaseActivity;
import com.will.sxlib.base.NavigationFragment;
import com.will.sxlib.db.DBUtil;
import com.will.sxlib.interfaces.Drawer;
import com.will.sxlib.login.LoginActivity;
import com.will.sxlib.navigation.NavigationFragmentController;
import com.will.sxlib.navigation.NavigationItem;
import com.will.sxlib.utils.Common;

public class MainActivity extends BaseActivity implements Drawer{
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
        mNavigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
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
                    case R.id.main_menu_login:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        return true;
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
    private void triggerNavigationDrawer(){
        if(mDrawer.isDrawerOpen(Gravity.START)){
            mDrawer.closeDrawer(Gravity.START);
        }else{
            mDrawer.openDrawer(Gravity.START);
        }

    }

    @Override
    public void triggerDrawer() {
        triggerNavigationDrawer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LoginActivity.START_LOGIN_ACTIVITY && resultCode == RESULT_OK){
            Common.makeToast("登陆成功");
        }
    }
}
