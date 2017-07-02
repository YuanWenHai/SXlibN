package com.will.sxlib.myBook.loanList;

import android.os.Handler;
import android.os.Looper;

import com.will.recyclerviewloadingadapter.BaseLoadingAdapter;
import com.will.recyclerviewloadingadapter.BaseRecyclerViewHolder;
import com.will.sxlib.R;
import com.will.sxlib.decode.HtmlUtils;
import com.will.sxlib.myBook.UserNetworkHelper;
import com.will.sxlib.myBook.bean.MyHistoricalBookItem;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Will on 2017/7/2.
 */

public class LoanListAdapter extends BaseLoadingAdapter<MyHistoricalBookItem> {

    private Handler mHandler = new Handler(Looper.myLooper());
    private boolean hasMoreData = true;

    public LoanListAdapter(){
        super(R.layout.item_fragment_loan_list);
    }

    @Override
    public boolean hasMoreData() {
        return hasMoreData;
    }

    @Override
    public void loadData(int page) {
        UserNetworkHelper.getInstance().getHistoryLoanList(page, new UserNetworkHelper.MyBookNetworkCallback() {
            @Override
            public void onPasswordIncorrect() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        update(false);
                    }
                },500);

            }

            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        update(false);
                    }
                },500);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String html = response.body().string();
                response.close();
                final List<MyHistoricalBookItem> data =  HtmlUtils.getMyHistoricalItemFromHtml(html);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        update(data);
                        hasMoreData = getData().size() > 0 && getData().size() < getData().get(0).getTotalCount();
                    }
                });

            }
        });
    }

    @Override
    public void convert(BaseRecyclerViewHolder holder, MyHistoricalBookItem item) {
        if(item.getOperationType().contains("借")){
            holder.setText(R.id.item_fragment_loan_list_icon, "借");
            holder.setBackgroundRes(R.id.item_fragment_loan_list_icon,R.drawable.circle_light_blue);
        }else{
            holder.setText(R.id.item_fragment_loan_list_icon, "还");
            holder.setBackgroundRes(R.id.item_fragment_loan_list_icon,R.drawable.circle_deep_orange);
        }
        holder.setText(R.id.item_fragment_loan_list_title,item.getTitle());
        holder.setText(R.id.item_fragment_loan_list_location,item.getLocation());
        holder.setText(R.id.item_fragment_loan_list_date,item.getOperationDate());

    }
}
