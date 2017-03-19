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
        /*BookDescription description = new BookDescription("");
        description.setAuthorIntro(getResources().getText(R.string.test_author_intro).toString());
        description.setCatalog(getResources().getText(R.string.test_catalog).toString());
        description.setSummary(getResources().getText(R.string.test_summary).toString());
        description.getRating().numRaters = 2345;
        description.getRating().average = "9.2";
        description.getRating().max = 10;
        initializeView(view,description);*/
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
        if(!description.getSummary().isEmpty()){
            ExpandableTextView summary = (ExpandableTextView) view.findViewById(R.id.fragment_book_description_text_summary);
            summary.setText(description.getSummary());
        }else{
            summaryLayout.setVisibility(View.GONE);
        }
        //author introduction
        if(!description.getAuthorIntro().isEmpty()){
            ExpandableTextView authorIntro = (ExpandableTextView) view.findViewById(R.id.fragment_book_description_text_author_intro);
            authorIntro.setText(description.getAuthorIntro());
        }else{
            authorIntroLayout.setVisibility(View.GONE);
        }
        //catalog
        if(!description.getCatalog().isEmpty()){
            ExpandableTextView catalog = (ExpandableTextView) view.findViewById(R.id.fragment_book_description_text_catalog);
            catalog.setText(description.getCatalog());
        }else{
            catalogLayout.setVisibility(View.GONE);
        }
    }
}
