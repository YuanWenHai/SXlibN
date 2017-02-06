package com.will.sxlib.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will.sxlib.R;
import com.will.sxlib.bean.BookSearchResult;
import com.will.sxlib.utils.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by will on 2017/2/5.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements SearchHelper.RequestCallback {
    private OnItemClickListener mListener;
    private List<BookSearchResult> mResults;
    private Context mContext;
    private SearchHelper mHelper;

    public SearchAdapter(Context context){
        mContext = context;
        mResults = new ArrayList<>();
        mHelper = new SearchHelper();
    }

    public void search(SearchUrlBuilder builder){
        mResults.clear();
        mHelper.search(builder,this);
    }

    public void nextPage(){
        mHelper.nextPage(this);
    }


    @Override
    public void onSuccess(List<BookSearchResult> list) {
        if(list.size() < 10){
            //已至末尾
        }
        mResults.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onFailure() {
        Common.makeToast("加载失败");
    }



    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mListener = onItemClickListener;
    }


    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_item,parent,false));
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        BookSearchResult result = mResults.get(position);
        holder.title.setText(result.getTitle());
        holder.author.setText(result.getAuthor());
        holder.press.setText(result.getPress());
        holder.publishDate.setText(result.getPublishDate());
        holder.isbn.setText(result.getIsbn());

        if(result.getCoverUrl() != null){
            Picasso.with(mContext).load(result.getCoverUrl()).into(holder.cover);
        }
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }



    class SearchViewHolder extends RecyclerView.ViewHolder{
        public ImageView cover;
        public TextView title,author,press,publishDate,isbn;
        public SearchViewHolder(View view){
            super(view);
            cover = (ImageView) view.findViewById(R.id.search_item_cover);
            title = (TextView) view.findViewById(R.id.search_item_title);
            author = (TextView) view.findViewById(R.id.search_item_author);
            press = (TextView) view.findViewById(R.id.search_item_press);
            publishDate = (TextView) view.findViewById(R.id.search_item_publish_date);
            isbn = (TextView) view.findViewById(R.id.search_item_isbn);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        mListener.onClick(mResults.get(getAdapterPosition()));
                    }
                }
            });
        }

    }



    interface OnItemClickListener{
        void onClick(BookSearchResult book);
    }
}
