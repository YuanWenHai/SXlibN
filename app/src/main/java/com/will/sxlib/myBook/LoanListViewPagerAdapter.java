package com.will.sxlib.myBook;

import android.app.FragmentManager;

import com.will.sxlib.myBook.loanList.LoanListFragment;
import com.will.sxlib.myBook.renewList.RenewListFragment;

/**
 * Created by Will on 2017/6/18.
 */

public class LoanListViewPagerAdapter extends android.support.v13.app.FragmentStatePagerAdapter {
    private String[] titles = new String[]{"续借列表","历史借阅"};

    public LoanListViewPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public android.app.Fragment getItem(int position) {
        if(position == 1){
            return new LoanListFragment();
        }
        return new RenewListFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
