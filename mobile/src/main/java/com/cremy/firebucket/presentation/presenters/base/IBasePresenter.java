package com.cremy.firebucket.presentation.presenters.base;


import com.cremy.firebucket.presentation.ui.base.BaseMvpView;

/**
 * This interface must be inherited from  {@link BasePresenter}
 * Created by remychantenay on 26/04/2016.
 */
public interface IBasePresenter<V extends BaseMvpView> {

    //region View
    void attachView(V _view);
    void detachView();
    boolean isViewAttached();
    void checkViewAttached();
    //endregion
}