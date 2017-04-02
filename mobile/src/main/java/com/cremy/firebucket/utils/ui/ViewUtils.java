package com.cremy.firebucket.utils.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Created by remychantenay on 27/02/2017.
 */

public final class ViewUtils {
    private static final int BACKGROUND_TRANSITION_DURATION = 250; // in ms

    public static void getColor(Context context, int resId) {
        ContextCompat.getColor(context, resId);
    }

    public static void setBackground(Drawable drawable, View view) {
        view.setBackground(drawable);
    }

    public static void setBackgroundWithTransition(View view, int startColor, int endColor) {
        if (view!=null) {
            ColorDrawable[] color = {new ColorDrawable(startColor), new ColorDrawable(endColor)};
            TransitionDrawable trans = new TransitionDrawable(color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(trans);
            } else {
                view.setBackgroundDrawable(trans);
            }
            trans.startTransition(BACKGROUND_TRANSITION_DURATION);
        }
    }

    public static void setGradientBackgroundWithTransition(View view,
                                                           final GradientDrawable start,
                                                           final GradientDrawable end) {
        if (view != null) {
            GradientDrawable[] gradientDrawables = {start, end};
            TransitionDrawable trans = new TransitionDrawable(gradientDrawables);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(trans);
            } else {
                view.setBackgroundDrawable(trans);
            }
            trans.startTransition(BACKGROUND_TRANSITION_DURATION);
        }
    }
}
