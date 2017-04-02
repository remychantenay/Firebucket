package com.cremy.firebucket.presentation.ui.widgets;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.cremy.firebucket.R;

/**
 * Smaller version of {@link FingerprintAnimatedImageView}
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FingerprintSmallAnimatedImageView extends FingerprintAnimatedImageView {

    public FingerprintSmallAnimatedImageView(Context context) {
        super(context);
    }

    public FingerprintSmallAnimatedImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FingerprintSmallAnimatedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FingerprintSmallAnimatedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void loadAnimatedVectorDrawables() {
        showFingerprint = getResources().getDrawable(R.drawable.animated_vector_fingerprint_show_small, null);
        fingerprintToTick = getResources().getDrawable(R.drawable.animated_vector_fingerprint_to_tick_small, null);
        fingerprintToCross = getResources().getDrawable(R.drawable.animated_vector_fingerprint_to_cross_small, null);
    }
}