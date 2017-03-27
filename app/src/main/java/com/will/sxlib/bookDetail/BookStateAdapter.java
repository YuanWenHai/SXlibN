package com.will.sxlib.bookDetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.will.sxlib.R;
import com.will.sxlib.bookDetail.bean.BookState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by will on 2017/3/26.
 */

public class BookStateAdapter extends RecyclerView.Adapter<BookStateAdapter.BookStateViewHolder> {

    private List<BookState> mData = new ArrayList<>();

    public BookStateAdapter(List<BookState> data){
        mData.add(BookState.getTitleItem());
        mData.addAll(data);
    }

    @Override
    public BookStateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookStateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_book_state_item,parent,false));
    }

    @Override
    public void onBindViewHolder(BookStateViewHolder holder, int position) {
        BookState mState = mData.get(position);
        holder.renewNumber.setText(mState.getRenewNumber());
        holder.loanNumber.setText(mState.getLoanNumber());
        holder.callno.setText(mState.getCallno());
        holder.loanDate.setText(mState.getLoanDate());
        holder.returnDate.setText(mState.getReturnDate());
        holder.type.setText(mState.getType());
        holder.local.setText(mState.getLocal());
        holder.state.setText(mState.getState());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class BookStateViewHolder extends RecyclerView.ViewHolder{
        TextView callno,state,loanDate,returnDate,local,type,loanNumber,renewNumber;
        public BookStateViewHolder(View view){
            super(view);
            callno = (TextView) view.findViewById(R.id.fragment_book_state_item_callno);
            state = (TextView) view.findViewById(R.id.fragment_book_state_item_state);
            loanDate = (TextView) view.findViewById(R.id.fragment_book_state_item_loan_date);
            returnDate = (TextView) view.findViewById(R.id.fragment_book_state_item_return_date);
            local = (TextView) view.findViewById(R.id.fragment_book_state_item_local);
            type = (TextView) view.findViewById(R.id.fragment_book_state_item_type);
            loanNumber = (TextView) view.findViewById(R.id.fragment_book_state_item_loan_number);
            renewNumber = (TextView) view.findViewById(R.id.fragment_book_state_item_renew_number);
        }
    }
}
