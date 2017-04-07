package com.cremy.firebucket.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.cremy.firebucket.external.AnalyticsInterface;
import com.cremy.firebucket.domain.interactors.Params;
import com.cremy.firebucket.domain.interactors.user.LoginUserUseCase;
import com.cremy.firebucket.domain.interactors.user.RegisterUserUseCase;
import com.cremy.firebucket.domain.interactors.user.WriteUserUseCase;
import com.cremy.firebucket.domain.models.UserModel;
import com.cremy.firebucket.firebase.RxFirebase;
import com.cremy.firebucket.presentation.presenters.RegisterMVP;
import com.cremy.firebucket.presentation.presenters.base.BasePresenter;
import com.cremy.firebucket.presentation.ui.activities.BucketActivity;
import com.cremy.firebucket.rx.DefaultObserver;

import javax.inject.Inject;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class RegisterPresenter extends BasePresenter<RegisterMVP.View>
        implements RegisterMVP.Presenter {
    private final static String TAG = RegisterPresenter.class.getName();

    private final static int MIN_CHARS_PASSWORD = 5;

    private final AnalyticsInterface analyticsInterface;
    private final RegisterUserUseCase registerUserUseCase;
    private final WriteUserUseCase writeUserUseCase;

    @Inject
    public RegisterPresenter(RegisterUserUseCase registerUserUseCase,
                             WriteUserUseCase writeUserUseCase,
                             AnalyticsInterface analyticsInterface) {
        this.registerUserUseCase = registerUserUseCase;
        this.writeUserUseCase = writeUserUseCase;
        this.analyticsInterface = analyticsInterface;
    }

    @Override
    public void attachView(RegisterMVP.View view) {
        super.attachView(view);
        analyticsInterface.trackPageView(AnalyticsInterface.VIEW_REGISTRATION);
    }

    @Override
    public void detachView() {
        registerUserUseCase.dispose();
        writeUserUseCase.dispose();
        super.detachView();
    }

    @Override
    public void register(String email, String password) {
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
        registerUserUseCase.execute(new RegisterUserObserver(), params);
    }

    @Override
    public void writeUser(String uid, String username) {
        Params params = Params.create();
        params.putString(WriteUserUseCase.PARAMS_KEY_UID, uid);
        params.putString(WriteUserUseCase.PARAMS_KEY_USERNAME, username);
        writeUserUseCase.execute(new WriteUserObserver(), params);
    }

    @Override
    public void onRegisterSuccess(UserModel userModel) {
        writeUser(userModel.getUid(), userModel.getUsername());
    }

    @Override
    public void onWriteUserSuccess() {
        checkViewAttached();
        view.hideLoading();
        view.onSuccess();
    }

    @Override
    public void onRegisterFailure(Throwable e) {
        e.printStackTrace();
        checkViewAttached();
        view.hideLoading();
        view.onFailure();
    }

    @Override
    public void onWriteUserFailure(Throwable e) {
        e.printStackTrace();
        checkViewAttached();
        view.hideLoading();
        view.onFailure();
    }

    @Override
    public void onRegisterSuccessTracking(UserModel userModel) {
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsInterface.PARAM_USER_UID, userModel.getUid());
        analyticsInterface.trackRegisterSuccess(bundle);
    }

    @Override
    public void onRegisterFailureTracking(Throwable e) {
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsInterface.PARAM_MESSAGE, e.getMessage());
        analyticsInterface.trackRegisterFailure(bundle);
    }

    @Override
    public void goToBucket(Context context) {
        BucketActivity.startMe(context);
    }

    private final class RegisterUserObserver extends DefaultObserver<UserModel> {

        @Override public void onComplete() {
            super.onComplete();
        }

        @Override public void onError(Throwable e) {
            super.onError(e);
            onRegisterFailureTracking(e);
            onRegisterFailure(e);
        }

        @Override public void onNext(UserModel userModel) {
            super.onNext(userModel);
            onRegisterSuccessTracking(userModel);
            onRegisterSuccess(userModel);
        }
    }

    private final class WriteUserObserver extends DefaultObserver<RxFirebase.FirebaseTaskResponseSuccess> {

        @Override public void onComplete() {
            super.onComplete();
        }

        @Override public void onError(Throwable e) {
            super.onError(e);
            onWriteUserFailure(e);
        }

        @Override public void onNext(RxFirebase.FirebaseTaskResponseSuccess firebaseTaskResponseSuccess) {
            super.onNext(firebaseTaskResponseSuccess);
            onWriteUserSuccess();
        }
    }
}