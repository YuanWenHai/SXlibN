package com.will.sxlib.bookDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.will.sxlib.R;
import com.will.sxlib.base.BaseFragment;

/**
 * Created by will on 2017/3/5.
 */

public class BookCatalogFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail_description,container,false);
        ExpandableTextView summary = (ExpandableTextView) view.findViewById(R.id.fragment_book_catalog_summary);
        ExpandableTextView catalog = (ExpandableTextView) view.findViewById(R.id.fragment_book_catalog_catalog);
        summary.setText(getResources().getText(R.string.test_summary));
        catalog.setText(getResources().getText(R.string.test_catalog));
        return view;
    }
}
