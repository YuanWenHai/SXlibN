package com.will.sxlib.navigation;

import com.will.sxlib.base.NavigationFragment;
import com.will.sxlib.search.SearchPageFragmentMain;

/**
 * Created by will on 2017/4/9.
 */

public enum NavigationItem {

    SEARCH(){
        @Override
        public NavigationFragment getFragment() {
            return new SearchPageFragmentMain();
        }
    };
    public abstract NavigationFragment getFragment();

}
