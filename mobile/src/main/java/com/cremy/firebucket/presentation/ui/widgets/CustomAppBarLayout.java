package com.cremy.firebucket.presentation.ui.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cremy.firebucket.R;

public class CustomAppBarLayout extends AppBarLayout implements AppBarLayout.OnOffsetChangedListener {

    private final static int DURATION_ALPHA_ANIMATION = 300; // in ms

    private CustomToolbar toolbar;
    private ImageView collapsingToolbarBackgroundImageView;
    private FrameLayout collapsingToolbarContent;
    private boolean appBarCollapsed = false;

    private OnAppBarOffsetChangedListener onAppBarOffsetChangedListener = null;

    /**
     * This interface must be implemented by any host wanting to be notified of the
     * OffsetChangedListener
     */
    public interface OnAppBarOffsetChangedListener {
        void onAppBarOffsetChanged(boolean isCollapsed);
    }

    public CustomAppBarLayout(Context context) {
        super(context);
        init();
    }

    public CustomAppBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.widget_collapsible_toolbar, this);
        toolbar = (CustomToolbar) findViewById(R.id.toolbar);
        toolbar.getToolbarTitle().setAlpha(0);
        collapsingToolbarBackgroundImageView = (ImageView) findViewById(R.id.collapsing_toolbar_background_imageview);
        collapsingToolbarContent = (FrameLayout) findViewById(R.id.collapsing_toolbar_content);
    }

    /**
     * Allows to set the offsetChangedListener to hide/display the toolbar title or the content
     */
    private void setUpOnOffsetChangedListener() {
        addOnOffsetChangedListener(this);
    }

    public void setTitle(@StringRes int resId) {
        if (toolbar != null && toolbar.getToolbarTitle() != null) {
            toolbar.getToolbarTitle().setText(resId);
        }
    }

    public void setTitle(CharSequence title) {
        if (toolbar != null && toolbar.getToolbarTitle() != null) {
            toolbar.getToolbarTitle().setText(title);
        }
    }

    /**
     * @param drawable, can be null, in that case, won't be visible
     */
    public void setCollapsingToolbarBackgroundImageViewDrawable(@Nullable Drawable drawable) {
        if (collapsingToolbarBackgroundImageView != null) {
            collapsingToolbarBackgroundImageView.setImageDrawable(drawable);
        }
    }

    public void setContentView(View view) {
        if (collapsingToolbarContent != null) {
            collapsingToolbarContent.removeAllViews();
            collapsingToolbarContent.addView(view);
        }
    }

    public void setHeightLarge(Context context) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.toolbar_collapsing_height_large);
        setLayoutParams(layoutParams);

        // We update the offsetChangedListener as the height changed
        invalidateOffsetChangedListener();
    }

    public void setHeightNormal(Context context) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.toolbar_collapsing_height_normal);
        setLayoutParams(layoutParams);

        // We update the offsetChangedListener as the height changed
        invalidateOffsetChangedListener();
    }

    private void invalidateOffsetChangedListener() {
        removeOnOffsetChangedListener(this);
        setUpOnOffsetChangedListener();
    }

    /**
     * Allows to know if the AppBarLayout is currently collapsed
     * @return
     */
    public boolean isAppBarCollapsed() {
        return appBarCollapsed;
    }

    public CustomToolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        final float yAxis = Math.abs(verticalOffset);
        if(yAxis == 0 || yAxis <= toolbar.getHeight()){
            if (appBarCollapsed) {
                collapsingToolbarContent.animate().alpha(1).setDuration(DURATION_ALPHA_ANIMATION);
                toolbar.getToolbarTitle().animate().alpha(0).setDuration(DURATION_ALPHA_ANIMATION);
                appBarCollapsed = false;
                if (onAppBarOffsetChangedListener != null) {
                    onAppBarOffsetChangedListener.onAppBarOffsetChanged(appBarCollapsed);
                }
            }
        }else {
            if (!appBarCollapsed) {
                collapsingToolbarContent.animate().alpha(0).setDuration(DURATION_ALPHA_ANIMATION);
                toolbar.getToolbarTitle().animate().alpha(1).setDuration(DURATION_ALPHA_ANIMATION);
                appBarCollapsed = true;
                if (onAppBarOffsetChangedListener != null) {
                    onAppBarOffsetChangedListener.onAppBarOffsetChanged(appBarCollapsed);
                }
            }

        }

    }

    public void setOnAppBarOffsetChangedListener(OnAppBarOffsetChangedListener onAppBarOffsetChangedListener) {
        this.onAppBarOffsetChangedListener = onAppBarOffsetChangedListener;
    }
}
