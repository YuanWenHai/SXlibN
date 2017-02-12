package com.will.recyclerviewloadingadapter;

/**
 * Created by Will on 2016/7/17.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;


public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> views = new SparseArray<>();
    public View convertView;
    Object associatedObject;

    protected BaseRecyclerViewHolder(View view) {
        super(view);
        this.convertView = view;
    }
    public View getConvertView() {
        return this.convertView;
    }

    public BaseRecyclerViewHolder setText(int viewId, CharSequence value) {
        TextView view = (TextView)this.getView(viewId);
        view.setText(value);
        return this;
    }

    public BaseRecyclerViewHolder setImageResource(int viewId, int imageResId) {
        ImageView view = (ImageView)this.getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public BaseRecyclerViewHolder setBackgroundColor(int viewId, int color) {
        View view = this.getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public BaseRecyclerViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = this.getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public BaseRecyclerViewHolder setTextColor(int viewId, int textColor) {
        TextView view = (TextView)this.getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public BaseRecyclerViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = (ImageView)this.getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public BaseRecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = (ImageView)this.getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public BaseRecyclerViewHolder setAlpha(int viewId, float value) {
        if(Build.VERSION.SDK_INT >= 11) {
            this.getView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0L);
            alpha.setFillAfter(true);
            this.getView(viewId).startAnimation(alpha);
        }

        return this;
    }

    public BaseRecyclerViewHolder setVisible(int viewId, boolean visible) {
        View view = this.getView(viewId);
        view.setVisibility(visible ? View.VISIBLE: View.INVISIBLE);
        return this;
    }

    public BaseRecyclerViewHolder linkify(int viewId) {
        TextView view = (TextView)this.getView(viewId);
        Linkify.addLinks(view, 15);
        return this;
    }

    public BaseRecyclerViewHolder setTypeface(int viewId, Typeface typeface) {
        TextView view = (TextView)this.getView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | 128);
        return this;
    }

    public BaseRecyclerViewHolder setTypeface(Typeface typeface, int... viewIds) {
        int[] var3 = viewIds;
        int var4 = viewIds.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int viewId = var3[var5];
            TextView view = (TextView)this.getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | 128);
        }

        return this;
    }

    public BaseRecyclerViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = (ProgressBar)this.getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public BaseRecyclerViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = (ProgressBar)this.getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public BaseRecyclerViewHolder setMax(int viewId, int max) {
        ProgressBar view = (ProgressBar)this.getView(viewId);
        view.setMax(max);
        return this;
    }

    public BaseRecyclerViewHolder setRating(int viewId, float rating) {
        RatingBar view = (RatingBar)this.getView(viewId);
        view.setRating(rating);
        return this;
    }

    public BaseRecyclerViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = (RatingBar)this.getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public BaseRecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = this.getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }



    public BaseRecyclerViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = this.getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public BaseRecyclerViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = this.getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public BaseRecyclerViewHolder setOnItemClickListener(int viewId, AdapterView.OnItemClickListener listener) {
        AdapterView view = (AdapterView)this.getView(viewId);
        view.setOnItemClickListener(listener);
        return this;
    }

    public BaseRecyclerViewHolder setOnItemLongClickListener(int viewId, AdapterView.OnItemLongClickListener listener) {
        AdapterView view = (AdapterView)this.getView(viewId);
        view.setOnItemLongClickListener(listener);
        return this;
    }

    public BaseRecyclerViewHolder setOnItemSelectedClickListener(int viewId, AdapterView.OnItemSelectedListener listener) {
        AdapterView view = (AdapterView)this.getView(viewId);
        view.setOnItemSelectedListener(listener);
        return this;
    }

    public BaseRecyclerViewHolder setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        CompoundButton view = (CompoundButton)this.getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    public BaseRecyclerViewHolder setTag(int viewId, Object tag) {
        View view = this.getView(viewId);
        view.setTag(tag);
        return this;
    }

    public BaseRecyclerViewHolder setTag(int viewId, int key, Object tag) {
        View view = this.getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public BaseRecyclerViewHolder setChecked(int viewId, boolean checked) {
        View view = this.getView(viewId);
        if(view instanceof CompoundButton) {
            ((CompoundButton)view).setChecked(checked);
        } else if(view instanceof CheckedTextView) {
            ((CheckedTextView)view).setChecked(checked);
        }

        return this;
    }

    public BaseRecyclerViewHolder setAdapter(int viewId, Adapter adapter) {
        AdapterView view = (AdapterView)this.getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    public View getView(int viewId) {
        View view = this.views.get(viewId);
        if(view == null) {
            view = this.convertView.findViewById(viewId);
            this.views.put(viewId, view);
        }

        return view;
    }

    public Object getAssociatedObject() {
        return this.associatedObject;
    }

    public void setAssociatedObject(Object associatedObject) {
        this.associatedObject = associatedObject;
    }

}

