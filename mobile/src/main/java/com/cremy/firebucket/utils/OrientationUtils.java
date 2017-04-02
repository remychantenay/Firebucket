package com.cremy.firebucket.utils;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cremy.firebucket.presentation.ui.base.BaseView;

/**
 * Created by remychantenay on 02/05/2016.
 */
public final class OrientationUtils {
    private final static String TAG = "OrientationUtils";


    /**
     * Allows to get the configuration change, will trigger:
     * BaseActivityView.onPortrait() or BaseView.onLandscape()
     * @param config
     * @param view
     */
    public static void setUpOrientation(@NonNull Configuration config,
                                        @NonNull BaseView view) {
        Log.d(TAG, "onConfigurationChanged");
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view.onLandscape();
        } else if (config.orientation == Configuration.ORIENTATION_PORTRAIT){
            view.onPortrait();
        }
    }

}