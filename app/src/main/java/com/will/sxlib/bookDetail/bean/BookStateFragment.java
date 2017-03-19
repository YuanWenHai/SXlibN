package com.will.sxlib.bookDetail.bean;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.sxlib.R;
import com.will.sxlib.base.BaseFragment;

/**
 * Created by will on 2017/3/19.
 */

public class BookStateFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_state,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_book_state_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new StateAdapter());
        return view;
    }

    class StateAdapter extends RecyclerView.Adapter{
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_book_state_item,parent,false);
            return new StateViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }
    static class StateViewHolder extends RecyclerView.ViewHolder{
        public StateViewHolder(View view){
            super(view);
        }
    }

}
