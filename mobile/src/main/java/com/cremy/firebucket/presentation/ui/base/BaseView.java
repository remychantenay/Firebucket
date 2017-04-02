package com.cremy.firebucket.presentation.ui.base;

import android.content.Context;

public interface BaseView {

    void injectDependencies();

    void attachToPresenter();
    void detachFromPresenter();

    void onLandscape();
    void onPortrait();

    void showLoading();
    void hideLoading();

    void showMessage(String message);
    void showNoNetwork();

    Context getContext();
}