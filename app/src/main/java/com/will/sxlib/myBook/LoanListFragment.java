package com.will.sxlib.myBook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.sxlib.base.BaseFragment;

/**
 * Created by Will on 2017/6/18.
 */

public class LoanListFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(container.getContext());

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
