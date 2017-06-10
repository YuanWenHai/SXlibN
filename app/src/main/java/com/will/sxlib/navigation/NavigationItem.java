package com.will.sxlib.navigation;

import com.will.sxlib.base.NavigationFragment;
import com.will.sxlib.myBook.MyBookFragment;
import com.will.sxlib.search.SearchFragment;

/**
 * Created by will on 2017/4/9.
 */

public enum NavigationItem {

    SEARCH(){
        @Override
        public NavigationFragment getFragment() {
            return new SearchFragment();
        }
    },
    MY_BOOK(){
        @Override
        public NavigationFragment getFragment() {
            return new MyBookFragment();
        }
    };

    public abstract NavigationFragment getFragment();

}
