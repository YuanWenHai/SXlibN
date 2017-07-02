package com.will.sxlib.dialog;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.afollestad.materialdialogs.MaterialDialog;
import com.will.sxlib.R;
import com.will.sxlib.config.ConfigManager;

/**
 * Created by Will on 2017/5/30.
 */

public class DialogUtils {

    public static MaterialDialog showSearchSetting(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_search_setting,null);
        AppCompatSpinner searchWaySpinner = (AppCompatSpinner) view.findViewById(R.id.dialog_search_setting_spinner_search_way);
        AppCompatSpinner sortWaySpinner = (AppCompatSpinner) view.findViewById(R.id.dialog_search_setting_spinner_sort_way);
        AppCompatSpinner sortOrderSpinner = (AppCompatSpinner) view.findViewById(R.id.dialog_search_setting_spinner_sort_order);

        searchWaySpinner.setAdapter(getSpinnerAdapter(context,R.array.search_setting_search_ways));
        sortWaySpinner.setAdapter(getSpinnerAdapter(context,R.array.search_setting_sort_ways));
        sortOrderSpinner.setAdapter(getSpinnerAdapter(context,R.array.search_setting_sort_orders));

        searchWaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ConfigManager.getInstance().setSearchSettingSearchWay(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sortWaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ConfigManager.getInstance().setSearchSettingSortWay(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sortOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ConfigManager.getInstance().setSearchSettingSortOrder(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchWaySpinner.setSelection(ConfigManager.getInstance().getSearchSettingSearchWay());
        sortWaySpinner.setSelection(ConfigManager.getInstance().getSearchSettingSortWay());
        sortOrderSpinner.setSelection(ConfigManager.getInstance().getSearchSettingSortOrder());

        return new MaterialDialog.Builder(context)
                .customView(view,false).show();
    }
    private static ArrayAdapter<CharSequence> getSpinnerAdapter(Context context,@ArrayRes int res){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,res,R.layout.item_dialog_search_setting_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
