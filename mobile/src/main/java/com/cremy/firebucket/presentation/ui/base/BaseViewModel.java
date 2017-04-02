package com.cremy.firebucket.presentation.ui.base;

import android.databinding.BaseObservable;

import com.cremy.firebucket.utils.rx.RxEventBus;

import javax.inject.Inject;

public abstract class BaseViewModel
        extends BaseObservable {

    @Inject
    public RxEventBus<Object> rxEventBus;

    //region Constructor
    public BaseViewModel() {
        // To complete
    }
    //endregion

}