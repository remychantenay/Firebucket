package com.cremy.firebucket.presentation.presenters.impl;

import android.content.Context;

import com.cremy.firebucket.analytics.AnalyticsHelper;
import com.cremy.firebucket.domain.interactors.Params;
import com.cremy.firebucket.domain.interactors.user.CheckUserUseCase;
import com.cremy.firebucket.firebase.FirebaseRemoteConfigHelper;
import com.cremy.firebucket.presentation.presenters.OnBoardingMVP;
import com.cremy.firebucket.presentation.presenters.base.BasePresenter;
import com.cremy.firebucket.presentation.ui.activities.BucketActivity;
import com.cremy.firebucket.rx.DefaultObserver;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import javax.inject.Inject;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class OnBoardingPresenter extends BasePresenter<OnBoardingMVP.View>
        implements OnBoardingMVP.Presenter {
    private final static String TAG = OnBoardingPresenter.class.getName();

    private final CheckUserUseCase checkUserUseCase;
    private final AnalyticsHelper analyticsHelper;
    private final FirebaseRemoteConfig firebaseRemoteConfig;

    @Inject
    public OnBoardingPresenter(CheckUserUseCase checkUserUseCase,
                               AnalyticsHelper analyticsHelper,
                               FirebaseRemoteConfig firebaseRemoteConfig) {
        this.checkUserUseCase = checkUserUseCase;
        this.analyticsHelper = analyticsHelper;
        this.firebaseRemoteConfig = firebaseRemoteConfig;
    }

    @Override
    public void attachView(OnBoardingMVP.View view) {
        super.attachView(view);
        analyticsHelper.trackPageView(AnalyticsHelper.VIEW_ONBOARDING);
    }

    @Override
    public void detachView() {
        checkUserUseCase.dispose();
        super.detachView();
    }

    @Override
    public void goToBucket(Context context) {
        BucketActivity.startMe(context);
    }

    @Override
    public void checkIfUserIsLogged() {
        checkUserUseCase.execute(new CheckUserObserver(), Params.EMPTY);
    }

    @Override
    public boolean isInMaintenance() {
        final String value = FirebaseRemoteConfigHelper.getString(firebaseRemoteConfig,
                FirebaseRemoteConfigHelper.FirebucketRemoteConfig.MAINTENANCE);
        return (!value.equals(FirebaseRemoteConfigHelper.FirebucketRemoteConfig.MAINTENANCE.getDefaultValue()));
    }

    private final class CheckUserObserver extends DefaultObserver<Boolean> {

        @Override public void onComplete() {
            super.onComplete();
        }

        @Override public void onError(Throwable e) {
            super.onError(e);
        }

        @Override public void onNext(Boolean value) {
            super.onNext(value);
            if (value) {
                view.userLogged();
            }
        }
    }
}