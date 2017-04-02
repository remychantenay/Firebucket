package com.cremy.firebucket.presentation.presenters.base;


import com.cremy.firebucket.presentation.ui.base.BaseMvpView;

/**
 * Mother of all presenters.
 * Note: must implement {@link IBasePresenter}
 * Created by remychantenay on 14/05/2016.
 */
public class BasePresenter<T extends BaseMvpView> implements IBasePresenter<T> {

    protected T view;

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public boolean isViewAttached() {
        return view!=null;
    }

    @Override
    public void checkViewAttached() throws ViewNotAttachedException {
        if (!isViewAttached()) throw new ViewNotAttachedException();
    }

    public static class ViewNotAttachedException extends RuntimeException {
        public ViewNotAttachedException() {
            super("Call Presenter.attachView(BaseView) before asking for data");
        }
    }
}