package com.will.sxlib.navigation;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.will.sxlib.base.NavigationFragment;

/**
 * Created by will on 2017/4/9.
 * Navigation Fragment Controller.
 */

public class NavigationFragmentController {
    private String  currentFragment;

    public void setCurrentFragment(AppCompatActivity activity, int containerId,NavigationItem item){
        if(currentFragment.equals(item.name())){
            return;
        }
        currentFragment = item.name();
        Fragment fragment = getAttachedFragment(activity,item);
        if(fragment == null){
            fragment = item.getFragment();
            activity.getFragmentManager().beginTransaction().add(containerId,fragment,item.name()).commit();
        }else{
            activity.getFragmentManager().beginTransaction().show(fragment).commit();
        }
        hideOthers(activity,item);
    }
    public NavigationFragment getCurrentFragment(AppCompatActivity activity){
        return (NavigationFragment) getAttachedFragment(activity,NavigationItem.valueOf(currentFragment));
    }

   private Fragment getAttachedFragment(AppCompatActivity activity, NavigationItem item){
       return activity.getFragmentManager().findFragmentByTag(item.name());
   }
   private void hideOthers(AppCompatActivity activity, NavigationItem item){
       for (NavigationItem n : NavigationItem.values()){
           if(n.name().equals(item.name())){
               continue;
           }
           Fragment attachedFragment = getAttachedFragment(activity,n);
           if(attachedFragment != null){
               activity.getFragmentManager().beginTransaction().hide(attachedFragment).commit();
           }
       }
   }


}
