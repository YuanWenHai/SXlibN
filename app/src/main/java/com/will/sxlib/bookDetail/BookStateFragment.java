package com.will.sxlib.bookDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.sxlib.R;
import com.will.sxlib.base.BaseFragment;
import com.will.sxlib.bookDetail.bean.BookState;

import java.util.ArrayList;

/**
 * Created by will on 2017/3/19.
 */

public class BookStateFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_state,container,false);
        ArrayList<BookState> data = (ArrayList<BookState>) getArguments().getSerializable("book_state");

        if(data == null || data.size() == 0) {
            view.findViewById(R.id.fragment_book_state_horizontal_scroll_view).setVisibility(View.GONE);
            view.findViewById(R.id.fragment_book_state_empty_text).setVisibility(View.VISIBLE);
        }else{
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_book_state_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new BookStateAdapter(data));
        }
        return view;
    }


}
