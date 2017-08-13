package com.will.sxlib.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.will.sxlib.base.BaseApplication;

/**
 * Created by Will on 2017/2/6.
 */

public class ConfigManager {
    private static ConfigManager instance;
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    public static ConfigManager getInstance(){
        if(instance == null){
            synchronized (ConfigManager.class){
                if(instance == null){
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }
    private ConfigManager(){
        sp = BaseApplication.getGlobalContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void setSearchSettingSearchWay(int which){
        spEditor.putInt("search_setting_search_way",which).apply();
    }
    public int getSearchSettingSearchWay(){
        return sp.getInt("search_setting_search_way",0);
    }

    public void setSearchSettingSortWay(int which){
        spEditor.putInt("search_setting_sort_way",which).apply();
    }
    public int getSearchSettingSortWay(){
        return sp.getInt("search_setting_sort_way",0);
    }

    public void setSearchSettingSortOrder(int which){
        spEditor.putInt("search_setting_sort_order",which).apply();
    }
    public int getSearchSettingSortOrder(){
        return sp.getInt("search_setting_sort_order",0);
    }

    public  String getUserAccount(){
        return sp.getString("user_account","");
    };
    public  void setUserAccount(String account){
        spEditor.putString("user_account",account).apply();
    }
    public  String getUserPassword(){
        return sp.getString("user_password","");
    }
    public  void setUserPassword(String password){
        spEditor.putString("user_password", password).apply();
    }

}
