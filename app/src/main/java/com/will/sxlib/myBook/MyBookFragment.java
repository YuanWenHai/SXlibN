package com.will.sxlib.myBook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.sxlib.R;
import com.will.sxlib.base.NavigationFragment;

/**
 * Created by Will on 2017/6/10.
 */

public class MyBookFragment extends NavigationFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_book,container,false);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
