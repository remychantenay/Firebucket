package com.cremy.firebucket.presentation.presenters;

import android.content.Context;

import com.cremy.firebucket.domain.models.UserModel;
import com.cremy.firebucket.presentation.ui.base.BaseMvpView;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface LoginMVP {

    interface View extends BaseMvpView {
        void setErrorEmailField();
        void setErrorPasswordField();
        void onSuccess();
        void onFailure();
    }

    interface Presenter {
        void login(String email, String password);
        void onLoginSuccess(UserModel userModel);
        void onLoginFailure(Throwable e);

        void onLoginSuccessTracking(UserModel userModel);
        void onLoginFailureTracking(Throwable e);

        void goToBucket(Context context);
    }
}
