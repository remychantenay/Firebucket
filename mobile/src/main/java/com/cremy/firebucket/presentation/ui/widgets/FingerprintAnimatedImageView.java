package com.cremy.firebucket.presentation.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.cremy.firebucket.R;
import com.cremy.firebucket.utils.ui.ViewUtils;

/**
 * This widget extends {@link ImageView} and allows to handle easily the VectorDrawables
 * as well as AnimatedVectorDrawables
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FingerprintAnimatedImageView extends ImageView {

    private final static int DELAY_SHOWING_FINGERPRINT_AFTER_FAIL = 1500; // in ms

    protected Drawable showFingerprint;
    protected Drawable fingerprintToTick;
    protected Drawable fingerprintToCross;

    private GradientDrawable currentBackground;
    private Handler handler;

    public FingerprintAnimatedImageView(Context context) {
        super(context);
        init();
    }

    public FingerprintAnimatedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FingerprintAnimatedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FingerprintAnimatedImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        loadAnimatedVectorDrawables();
    }

    protected void loadAnimatedVectorDrawables() {
        showFingerprint = getResources().getDrawable(R.drawable.animated_vector_fingerprint_show, null);
        fingerprintToTick = getResources().getDrawable(R.drawable.animated_vector_fingerprint_to_tick, null);
        fingerprintToCross = getResources().getDrawable(R.drawable.animated_vector_fingerprint_to_cross, null);
    }

    public void showFingerprint() {
        if (showFingerprint!=null) {
            setImageDrawable(showFingerprint);
            if (getDrawable() instanceof Animatable2) {
                ((Animatable2) getDrawable()).start();

                currentBackground = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[] {ContextCompat.getColor(getContext(), R.color.fingerprint_icon_color_background_top),
                                ContextCompat.getColor(getContext(), R.color.fingerprint_icon_color_background_bottom)});
                currentBackground.setShape(GradientDrawable.OVAL);
                setBackground(currentBackground);
            }
        }
    }

    public void showAuthenticationSucceed() {
        if (fingerprintToTick!=null) {
            setImageDrawable(fingerprintToTick);
            if (getDrawable() instanceof Animatable2) {
                ((Animatable2) getDrawable()).start();

                GradientDrawable gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[] {ContextCompat.getColor(getContext(), R.color.fingerprint_icon_color_success),
                                ContextCompat.getColor(getContext(), R.color.fingerprint_icon_color_success)});
                gradientDrawable.setShape(GradientDrawable.OVAL);
                refreshBackground(gradientDrawable);
            }
        }
    }

    /**
     *
     * @param shouldShowRidgesAfter if true, the animation will display the fingerprint ridges
     *                              after DELAY_SHOWING_FINGERPRINT_AFTER_FAIL
     */
    public void showAuthenticationFailed(boolean shouldShowRidgesAfter) {
        if (fingerprintToCross != null) {
            setImageDrawable(fingerprintToCross);
            if (getDrawable() instanceof Animatable2) {
                ((Animatable2) getDrawable()).start();

                GradientDrawable gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[] {ContextCompat.getColor(getContext(), R.color.fingerprint_icon_color_fail),
                                ContextCompat.getColor(getContext(), R.color.fingerprint_icon_color_fail)});
                gradientDrawable.setShape(GradientDrawable.OVAL);
                refreshBackground(gradientDrawable);

                if (shouldShowRidgesAfter) {
                    if (handler == null) {
                        handler = new Handler();
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showFingerprint();
                        }
                    }, DELAY_SHOWING_FINGERPRINT_AFTER_FAIL);
                }
            }
        }
    }

    private void refreshBackground(GradientDrawable newBackground) {
        ViewUtils.setGradientBackgroundWithTransition(this, currentBackground, newBackground);
        currentBackground = newBackground;
    }
}