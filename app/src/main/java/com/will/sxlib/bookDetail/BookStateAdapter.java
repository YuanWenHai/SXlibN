package com.will.sxlib.bookDetail;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.will.sxlib.R;
import com.will.sxlib.bookDetail.bean.BookState;
import com.will.sxlib.utils.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by will on 2017/3/26.<br/>
 * 注,这里目前有个bug，因为state table本质上是一个HorizontalScrollView中嵌套了一个纵向的RecycleView,<br/>
 * 而HorizontalScrollView的外部又是一个纵向的ScrollView，此处为了解决滑动冲突，直接在纵向RecyclerView的OnTouchListenr当中返回了true.<br/>
 * 这就导致了RecyclerView无法完整的接收到触摸事件，具体表现就是，当我们纵向滑动RecyclerView后，其Item将变得不可点击(原因暂时还未知).<br/>
 */

public class BookStateAdapter extends RecyclerView.Adapter<BookStateAdapter.BookStateViewHolder> {

    private List<BookState> mData = new ArrayList<>();

    public BookStateAdapter(List<BookState> data){
        if(data.size() != 0){
            mData.add(BookState.getTitleItem());
        }
        mData.addAll(data);
    }

    @Override
    public BookStateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookStateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_book_state_item,parent,false));
    }

    @Override
    public void onBindViewHolder(BookStateViewHolder holder, int position) {
        BookState mState = mData.get(position);
        //首项加粗
        if(position == 0){
            int boldRes = R.style.BookStateTableTextItemAppearanceBold;
            if(Build.VERSION.SDK_INT <23){
                Context context = holder.callno.getContext();
                for(TextView t : holder.mTextViews){
                    t.setTextAppearance(context,boldRes);
                }
            }else{
                for(TextView t : holder.mTextViews){
                    t.setTextAppearance(boldRes);
                }
            }
        }else{
            int res = R.style.BookStateTableTextItemAppearance;
            if(Build.VERSION.SDK_INT <23){
                Context context = holder.callno.getContext();
                for(TextView t : holder.mTextViews){
                    t.setTextAppearance(context,res);
                }
            }else{
                for(TextView t : holder.mTextViews){
                    t.setTextAppearance(res);
                }
            }
        }

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

    class BookStateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView callno,state,loanDate,returnDate,local,type,loanNumber,renewNumber;
        ArrayList<TextView> mTextViews = new ArrayList<>();
        public BookStateViewHolder(View view){
            super(view);
            callno = (TextView) view.findViewById(R.id.fragment_book_state_item_callno);
            mTextViews.add(callno);
            state = (TextView) view.findViewById(R.id.fragment_book_state_item_state);
            mTextViews.add(state);
            loanDate = (TextView) view.findViewById(R.id.fragment_book_state_item_loan_date);
            mTextViews.add(loanDate);
            returnDate = (TextView) view.findViewById(R.id.fragment_book_state_item_return_date);
            mTextViews.add(returnDate);
            local = (TextView) view.findViewById(R.id.fragment_book_state_item_local);
            mTextViews.add(local);
            type = (TextView) view.findViewById(R.id.fragment_book_state_item_type);
            mTextViews.add(type);
            loanNumber = (TextView) view.findViewById(R.id.fragment_book_state_item_loan_number);
            mTextViews.add(loanNumber);
            renewNumber = (TextView) view.findViewById(R.id.fragment_book_state_item_renew_number);
            mTextViews.add(renewNumber);
            for(View v : mTextViews){
                v.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            TextView textView = (TextView)v;
            Common.makeToast(textView.getText().toString());
        }
    }
}
