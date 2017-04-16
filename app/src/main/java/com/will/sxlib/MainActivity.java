package com.will.sxlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.will.sxlib.base.NavigationFragment;
import com.will.sxlib.db.DBUtil;
import com.will.sxlib.navigation.NavigationController;
import com.will.sxlib.navigation.NavigationItem;

public class MainActivity extends AppCompatActivity {
    private NavigationController mController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mController = new NavigationController();
        mController.setCurrentFragment(this,R.id.fragment_container, NavigationItem.SEARCH);
    }

    @Override
    public void onBackPressed() {
        NavigationFragment mFragment = mController.getCurrentFragment(this);
        if(mFragment != null && mFragment.onBackPressed()){
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
