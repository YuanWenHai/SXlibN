package com.will.sxlib.navigation;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by will on 2017/4/9.
 * Navigation Fragment Controller.
 */

public class NavigationController {

    public void setCurrentFragment(AppCompatActivity activity, int containerId,NavigationItem item){
        Fragment fragment = getAttachedFragment(activity,item);
        if(fragment == null){
            fragment = item.getFragment();
            activity.getFragmentManager().beginTransaction().add(containerId,fragment,item.name()).commit();
        }else{
            activity.getFragmentManager().beginTransaction().show(fragment).commit();
        }
        hideOtherFragments(activity,item);
    }

   private Fragment getAttachedFragment(AppCompatActivity activity, NavigationItem item){
       return activity.getFragmentManager().findFragmentByTag(item.name());
   }
   private void hideOtherFragments(AppCompatActivity activity,NavigationItem item){
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
