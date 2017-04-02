package com.cremy.firebucket.presentation.ui.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * This class must implement {@link BaseView}
 * Created by remychantenay on 18/05/2016.
 */
public abstract class BaseActivity
        extends AppCompatActivity {

    public abstract void getExtras(Intent _intent);
    public abstract void closeActivity();
    public abstract void setUpToolbar();
    public abstract Fragment getAttachedFragment(int id);
    public abstract Fragment getAttachedFragment(String tag);

    public Context getContext() {
        return this;
    }
}