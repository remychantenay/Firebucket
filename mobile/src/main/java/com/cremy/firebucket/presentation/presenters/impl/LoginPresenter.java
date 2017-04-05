package com.cremy.firebucket.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.cremy.firebucket.analytics.AnalyticsInterface;
import com.cremy.firebucket.domain.interactors.Params;
import com.cremy.firebucket.domain.interactors.user.LoginUserUseCase;
import com.cremy.firebucket.domain.models.UserModel;
import com.cremy.firebucket.presentation.presenters.LoginMVP;
import com.cremy.firebucket.presentation.presenters.base.BasePresenter;
import com.cremy.firebucket.presentation.ui.activities.BucketActivity;
import com.cremy.firebucket.rx.DefaultObserver;

import javax.inject.Inject;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class LoginPresenter extends BasePresenter<LoginMVP.View>
        implements LoginMVP.Presenter {
    private final static String TAG = LoginPresenter.class.getName();

    private final static int MIN_CHARS_PASSWORD = 5;

    private final AnalyticsInterface analyticsInterface;
    private final LoginUserUseCase loginUserUseCase;

    @Inject
    public LoginPresenter(LoginUserUseCase loginUserUseCase,
                          AnalyticsInterface analyticsInterface) {
        this.loginUserUseCase = loginUserUseCase;
        this.analyticsInterface = analyticsInterface;
    }

    @Override
    public void attachView(LoginMVP.View view) {
        super.attachView(view);
        analyticsInterface.trackPageView(AnalyticsInterface.VIEW_LOGIN);
    }

    @Override
    public void detachView() {
        loginUserUseCase.dispose();
        super.detachView();
    }

    @Override
    public void login(String email, String password) {
        email = email.trim();
        password = password.trim();

        if (email.isEmpty()) {
            view.setErrorEmailField();
            return;
        }
        if (password.isEmpty() || password.length() < MIN_CHARS_PASSWORD) {
            view.setErrorPasswordField();
            return;
        }

        view.showLoading();

        Params params = Params.create();
        params.putString(LoginUserUseCase.PARAMS_KEY_EMAIL, email);
        params.putString(LoginUserUseCase.PARAMS_KEY_PASSWORD, password);
        loginUserUseCase.execute(new LoginUserObserver(), params);
    }

    @Override
    public void onLoginSuccess(UserModel userModel) {
        checkViewAttached();
        view.hideLoading();
        view.onSuccess();
    }

    @Override
    public void onLoginFailure(Throwable e) {
        e.printStackTrace();
        checkViewAttached();
        view.hideLoading();
        view.onFailure();
    }

    @Override
    public void onLoginSuccessTracking(UserModel userModel) {
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsInterface.PARAM_USER_UID, userModel.getUid());
        analyticsInterface.trackLoginSuccess(bundle);
    }

    @Override
    public void onLoginFailureTracking(Throwable e) {
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsInterface.PARAM_MESSAGE, e.getMessage());
        analyticsInterface.trackLoginFailure(bundle);
    }

    @Override
    public void goToBucket(Context context) {
        BucketActivity.startMe(context);
    }

    private final class LoginUserObserver extends DefaultObserver<UserModel> {

        @Override public void onComplete() {
            super.onComplete();
        }

        @Override public void onError(Throwable e) {
            super.onError(e);
            onLoginFailureTracking(e);
            onLoginFailure(e);
        }

        @Override public void onNext(UserModel userModel) {
            super.onNext(userModel);
            onLoginSuccessTracking(userModel);
            onLoginSuccess(userModel);
        }
    }
}