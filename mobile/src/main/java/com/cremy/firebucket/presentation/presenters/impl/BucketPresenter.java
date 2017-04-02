package com.cremy.firebucket.presentation.presenters.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.cremy.firebucket.data.entities.TaskEntity;
import com.cremy.firebucket.domain.interactors.Params;
import com.cremy.firebucket.domain.interactors.bucket.GetBucketUseCase;
import com.cremy.firebucket.domain.interactors.task.DeleteTaskUseCase;
import com.cremy.firebucket.domain.interactors.user.LoginUserUseCase;
import com.cremy.firebucket.domain.models.BucketModel;
import com.cremy.firebucket.domain.models.UserModel;
import com.cremy.firebucket.firebase.FirebaseAnalyticsHelper;
import com.cremy.firebucket.presentation.presenters.BucketMVP;
import com.cremy.firebucket.presentation.presenters.CreateTaskMVP;
import com.cremy.firebucket.presentation.presenters.LoginMVP;
import com.cremy.firebucket.presentation.presenters.base.BasePresenter;
import com.cremy.firebucket.rx.DefaultObserver;
import com.cremy.firebucket.utils.helpers.SharedPreferencesHelper;
import com.cremy.greenrobotutils.library.util.NetworkUtils;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class BucketPresenter extends BasePresenter<BucketMVP.View>
        implements BucketMVP.Presenter {
    private final static String TAG = BucketPresenter.class.getName();

    private final FirebaseAnalytics firebaseAnalytics;
    private final SharedPreferences sharedPreferences;
    private final GetBucketUseCase getBucketUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    @Inject
    public BucketPresenter(GetBucketUseCase getBucketUseCase,
                           DeleteTaskUseCase deleteTaskUseCase,
                           FirebaseAnalytics firebaseAnalytics,
                           SharedPreferences sharedPreferences) {
        this.getBucketUseCase = getBucketUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.firebaseAnalytics = firebaseAnalytics;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void attachView(BucketMVP.View view) {
        super.attachView(view);
        FirebaseAnalyticsHelper.trackPageView(firebaseAnalytics,
                FirebaseAnalyticsHelper.VIEW_BUCKET);
    }

    @Override
    public void detachView() {
        getBucketUseCase.dispose();
        deleteTaskUseCase.dispose();
        super.detachView();
    }

    @Override
    public void getBucket() {
        view.showLoading();
        getBucketUseCase.execute(new GetBucketObserver(), Params.EMPTY);
    }

    @Override
    public void onGetBucketSuccess(BucketModel bucketModel) {
        checkViewAttached();
        view.hideLoading();
        if (bucketModel.isEmpty()) {
            if (!SharedPreferencesHelper.getBoolean(sharedPreferences,
                    SharedPreferencesHelper.KEY_USER_TASK_AT_LEAST_ONCE)) {
                view.showBucketEmptyFirstTime();
            } else {
                view.showBucketEmpty();
            }
        } else {
            SharedPreferencesHelper.putBoolean(sharedPreferences,
                    SharedPreferencesHelper.KEY_USER_TASK_AT_LEAST_ONCE,
                    true);
            view.showBucket(bucketModel.toDisplayedList());
        }
    }

    @Override
    public void onGetBucketFailure(Throwable e) {
        e.printStackTrace();
        checkViewAttached();
        view.hideLoading();
        view.showBucketError();
    }

    @Override
    public void onGetBucketSuccessTracking() {
        FirebaseAnalyticsHelper.trackGetBucketSuccess(firebaseAnalytics, null);
    }

    @Override
    public void onGetBucketFailureTracking(Throwable e) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalyticsHelper.PARAM_MESSAGE, e.getMessage());
        FirebaseAnalyticsHelper.trackGetBucketFailure(firebaseAnalytics, bundle);
    }

    @Override
    public void deleteTask(String taskId) {
        Params params = Params.create();
        params.putString(DeleteTaskUseCase.PARAMS_KEY_TASK_ID, taskId);
        deleteTaskUseCase.execute(new DeleteTaskObserver(), params);
    }

    @Override
    public void onDeleteTaskSuccess() {
        checkViewAttached();
        view.showTaskDeleted();
    }

    @Override
    public void onDeleteTaskFailure(Throwable e) {
        checkViewAttached();
        view.showTaskDeletedError(e.getMessage());
    }

    @Override
    public void onDeleteTaskSuccessTracking() {
        FirebaseAnalyticsHelper.trackDeleteTaskSuccess(firebaseAnalytics, null);
    }

    @Override
    public void onDeleteTaskFailureTracking(Throwable e) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalyticsHelper.PARAM_MESSAGE, e.getMessage());
        FirebaseAnalyticsHelper.trackDeleteFailure(firebaseAnalytics, bundle);
    }

    private final class GetBucketObserver extends DefaultObserver<BucketModel> {

        @Override public void onComplete() {
            super.onComplete();
        }

        @Override public void onError(Throwable e) {
            super.onError(e);
            onGetBucketFailureTracking(e);
            onGetBucketFailure(e);
        }

        @Override public void onNext(BucketModel bucketModel) {
            super.onNext(bucketModel);
            onGetBucketSuccessTracking();
            onGetBucketSuccess(bucketModel);
        }
    }

    private final class DeleteTaskObserver extends DefaultObserver<TaskEntity> {

        @Override public void onComplete() {
            super.onComplete();
        }

        @Override public void onError(Throwable e) {
            super.onError(e);
            onDeleteTaskFailureTracking(e);
            onDeleteTaskFailure(e);
        }

        @Override public void onNext(TaskEntity taskEntity) {
            super.onNext(taskEntity);
            onDeleteTaskSuccessTracking();
            onDeleteTaskSuccess();
        }
    }
}