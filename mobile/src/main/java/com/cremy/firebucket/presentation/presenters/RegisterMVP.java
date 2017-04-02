package com.cremy.firebucket.presentation.presenters;

import android.content.Context;

import com.cremy.firebucket.domain.models.UserModel;
import com.cremy.firebucket.presentation.ui.base.BaseMvpView;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface RegisterMVP {

    interface View extends BaseMvpView {
        void setErrorEmailField();
        void setErrorPasswordField();
        void onSuccess();
        void onFailure();
    }

    interface Presenter {
        void register(String email, String password);
        void writeUser(String uid, String username);

        void onRegisterSuccess(UserModel userModel);
        void onRegisterFailure(Throwable e);
        void onRegisterSuccessTracking(UserModel userModel);
        void onRegisterFailureTracking(Throwable e);

        void onWriteUserSuccess();
        void onWriteUserFailure(Throwable e);

        void goToBucket(Context context);
    }
}
