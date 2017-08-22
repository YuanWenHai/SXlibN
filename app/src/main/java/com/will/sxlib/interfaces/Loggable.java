package com.will.sxlib.interfaces;

/**
 * Created by Will on 2017/8/22.
 */

public interface Loggable {
    int STATE_LOGGED = 0;
    int STATE_LOGOUT =  1;
    void onLoginChanged(int state);
}
