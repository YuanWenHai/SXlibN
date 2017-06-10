package com.will.sxlib.bookDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.will.sxlib.R;
import com.will.sxlib.base.BaseFragment;
import com.will.sxlib.bookDetail.bean.BookDescription;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by will on 2017/3/5.
 */

public class BookDescriptionFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_description,container,false);
        BookDescription description = null;
        try{
            description = (BookDescription) getArguments().getSerializable("description");
        }catch (ClassCastException c){
            c.printStackTrace();
        }
        if(description == null){
            return null;
        }
        initializeView(view,description);
        return view;
    }
    private void initializeView(View view, BookDescription description){
        //部件layout，用于在无数据时隐藏对应组件
        View ratingLayout = view.findViewById(R.id.fragment_book_description_layout_rating);
        View summaryLayout = view.findViewById(R.id.fragment_book_description_layout_summary);
        View authorIntroLayout = view.findViewById(R.id.fragment_book_description_layout_author_intro);
        View catalogLayout = view.findViewById(R.id.fragment_book_description_layout_catalog);
        //rating bar
        if(!description.getRating().isEmpty()){
            BookDescription.Rating rating = description.getRating();
            MaterialRatingBar ratingBar = (MaterialRatingBar) view.findViewById(R.id.fragment_book_description_rating_bar);
            TextView ratingText = (TextView) view.findViewById(R.id.fragment_book_description_text_rating);
            TextView ratersNumber = (TextView) view.findViewById(R.id.fragment_book_description_text_raters_number);
            float ratingValue =(Float.valueOf(rating.average)/(float)rating.max)*5;
            ratingBar.setNumStars(5);
            ratingBar.setRating(ratingValue);
            ratingText.setText(rating.average);
            ratersNumber.setText((rating.numRaters+"人评价"));
        }else{
            ratingLayout.setVisibility(View.GONE);
        }
        //summary
        ExpandableTextView summary = (ExpandableTextView) view.findViewById(R.id.fragment_book_description_text_summary);
        if(!description.getSummary().isEmpty()){

            summary.setText(description.getSummary());
        }else{
            //summaryLayout.setVisibility(View.GONE);
            summary.setText("暂无书籍简介");
        }
        //author introduction
        ExpandableTextView authorIntro = (ExpandableTextView) view.findViewById(R.id.fragment_book_description_text_author_intro);
        if(!description.getAuthorIntro().isEmpty()){
            authorIntro.setText(description.getAuthorIntro());
        }else{
            authorIntroLayout.setVisibility(View.GONE);
        }
        //catalog
        ExpandableTextView catalog = (ExpandableTextView) view.findViewById(R.id.fragment_book_description_text_catalog);
        if(!description.getCatalog().isEmpty()){
            catalog.setText(description.getCatalog());
        }else{
            //catalogLayout.setVisibility(View.GONE);
            catalog.setText("暂无书籍目录");
        }
    }
}
