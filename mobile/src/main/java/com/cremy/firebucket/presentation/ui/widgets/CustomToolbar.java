package com.cremy.firebucket.presentation.ui.widgets;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.cremy.firebucket.R;


public class CustomToolbar extends Toolbar {

    protected TextView toolbarTitle;

    public CustomToolbar(Context context) {
        super(context);
        init();
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.widget_toolbar_title, this);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
    }

    public void setTitle(@StringRes int resId) {
        toolbarTitle.setText(resId);
    }

    public void setTitle(CharSequence title) {
        toolbarTitle.setText(title);
    }

    public void setTitleColor(@ColorRes int textColorId) {
        toolbarTitle.setTextColor(ContextCompat.getColor(getContext(),textColorId));
    }

    @SuppressWarnings("deprecation")
    public void setToolbarTitleTextAppearance(@StyleRes int styleRes) {
        if (Build.VERSION.SDK_INT < 23) {
            toolbarTitle.setTextAppearance(getContext(), styleRes);
        } else {
            toolbarTitle.setTextAppearance(styleRes);
        }
    }

    public TextView getToolbarTitle() {
        return toolbarTitle;
    }
}
