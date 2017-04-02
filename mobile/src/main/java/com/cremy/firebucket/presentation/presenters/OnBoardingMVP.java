package com.cremy.firebucket.presentation.presenters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;

import com.cremy.firebucket.presentation.ui.base.BaseMvpView;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface OnBoardingMVP {

    interface View extends BaseMvpView {
        void userLogged();
    }

    interface Presenter {
        void goToBucket(Context context);
        void checkIfUserIsLogged();

        boolean isInMaintenance();
    }
}
