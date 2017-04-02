package com.cremy.firebucket.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.cremy.firebucket.R;
import com.cremy.firebucket.data.entities.TaskEntity;
import com.cremy.firebucket.domain.interactors.Params;
import com.cremy.firebucket.domain.interactors.taglist.GetTagListUseCase;
import com.cremy.firebucket.domain.interactors.task.CreateTaskUseCase;
import com.cremy.firebucket.domain.interactors.task.DeleteTaskUseCase;
import com.cremy.firebucket.domain.interactors.user.LoginUserUseCase;
import com.cremy.firebucket.domain.models.TagListModel;
import com.cremy.firebucket.domain.models.TaskPriorityModel;
import com.cremy.firebucket.domain.models.UserModel;
import com.cremy.firebucket.firebase.FirebaseAnalyticsHelper;
import com.cremy.firebucket.presentation.presenters.CreateTaskMVP;
import com.cremy.firebucket.presentation.presenters.LoginMVP;
import com.cremy.firebucket.presentation.presenters.RegisterMVP;
import com.cremy.firebucket.presentation.presenters.base.BasePresenter;
import com.cremy.firebucket.rx.DefaultObserver;
import com.cremy.firebucket.utils.CustomDateUtils;
import com.cremy.greenrobotutils.library.util.NetworkUtils;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class CreateTaskPresenter extends BasePresenter<CreateTaskMVP.View>
        implements CreateTaskMVP.Presenter {
    private final static String TAG = CreateTaskPresenter.class.getName();

    private final FirebaseAnalytics firebaseAnalytics;
    private final GetTagListUseCase getTagListUseCase;
    private final CreateTaskUseCase createTaskUseCase;

    @Inject
    public CreateTaskPresenter(GetTagListUseCase getTagListUseCase,
                               CreateTaskUseCase createTaskUseCase,
                               FirebaseAnalytics firebaseAnalytics) {
        this.getTagListUseCase = getTagListUseCase;
        this.createTaskUseCase = createTaskUseCase;
        this.firebaseAnalytics = firebaseAnalytics;
    }

    @Override
    public void attachView(CreateTaskMVP.View view) {
        super.attachView(view);
        FirebaseAnalyticsHelper.trackPageView(firebaseAnalytics,
                FirebaseAnalyticsHelper.VIEW_CREATE_TASK);
    }

    @Override
    public void detachView() {
        getTagListUseCase.dispose();
        createTaskUseCase.dispose();
        super.detachView();
    }

    @Override
    public void getTagList() {
        if (!NetworkUtils.isNetworkEnabled(view.getContext())) {
            view.showNoNetwork();
            return;
        }

        getTagListUseCase.execute(new CreateTaskPresenter.GetTagListObserver(), Params.EMPTY);
    }

    @Override
    public void onGetTagListSuccess(TagListModel tagListModel) {
        checkViewAttached();
        view.hideLoading();
        view.showTagList(tagListModel.toArray());
    }

    @Override
    public void onGetTagListFailure(Throwable e) {
        e.printStackTrace();
        checkViewAttached();
        view.hideLoading();
        view.showTagListError();
    }

    @Override
    public void onGetTagListSuccessTracking() {
        FirebaseAnalyticsHelper.trackGetTagListSuccess(firebaseAnalytics, null);
    }

    @Override
    public void onGetTagListFailureTracking(Throwable e) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalyticsHelper.PARAM_MESSAGE, e.getMessage());
        FirebaseAnalyticsHelper.trackGetTagListFailure(firebaseAnalytics, bundle);
    }

    @Override
    public void createTask(String title,
                           Calendar deadline,
                           String tag,
                           int idPriority) {

        if (!NetworkUtils.isNetworkEnabled(view.getContext())) {
            view.showNoNetwork();
            return;
        }

        if (title.isEmpty() || deadline == null || tag.isEmpty()) {
            checkViewAttached();
            view.showMessage(view.getContext().getResources().getString(R.string.error_create_task_invalid_title));
            return;
        }

        Params params = Params.create();
        params.putString(CreateTaskUseCase.PARAMS_KEY_TITLE, title);
        params.putLong(CreateTaskUseCase.PARAMS_KEY_DEADLINE_MS, deadline.getTimeInMillis());
        params.putString(CreateTaskUseCase.PARAMS_KEY_DEADLINE, deadline.getTime().toString());

        params.putString(CreateTaskUseCase.PARAMS_KEY_TAG, tag);
        params.putInt(CreateTaskUseCase.PARAMS_KEY_PRIORITY_ID, idPriority);
        params.putString(CreateTaskUseCase.PARAMS_KEY_PRIORITY_LABEL, TaskPriorityModel.getResourceLabel(view.getContext(), idPriority));

        createTaskUseCase.execute(new CreateTaskPresenter.GetCreateTaskObserver(), params);
    }

    @Override
    public void onCreateTaskSuccess() {
        checkViewAttached();
        view.hideLoading();
        view.onSuccess();
    }

    @Override
    public void onCreateTaskFailure(Throwable e) {
        e.printStackTrace();
        checkViewAttached();
        view.hideLoading();
        view.onFailure();
    }

    @Override
    public void onCreateTaskSuccessTracking(TaskEntity taskEntity) {
        Bundle bundle = new Bundle();
        bundle.putString(CreateTaskUseCase.PARAMS_KEY_TAG, taskEntity.getTag());
        bundle.putString(CreateTaskUseCase.PARAMS_KEY_PRIORITY_LABEL, taskEntity.getPriority().getLabel());
        FirebaseAnalyticsHelper.trackCreateTaskSuccess(firebaseAnalytics, bundle);
    }

    @Override
    public void onCreateTaskFailureTracking(Throwable e) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalyticsHelper.PARAM_MESSAGE, e.getMessage());
        FirebaseAnalyticsHelper.trackCreateTaskFailure(firebaseAnalytics, bundle);
    }

    private final class GetTagListObserver extends DefaultObserver<TagListModel> {

        @Override public void onComplete() {
            super.onComplete();
        }

        @Override public void onError(Throwable e) {
            super.onError(e);
            onGetTagListFailureTracking(e);
            onGetTagListFailure(e);
        }

        @Override public void onNext(TagListModel tagListModel) {
            super.onNext(tagListModel);
            onGetTagListSuccessTracking();
            onGetTagListSuccess(tagListModel);
        }
    }

    private final class GetCreateTaskObserver extends DefaultObserver<TaskEntity> {

        @Override public void onComplete() {
            super.onComplete();
        }

        @Override public void onError(Throwable e) {
            super.onError(e);
            onCreateTaskFailureTracking(e);
            onCreateTaskFailure(e);
        }

        @Override public void onNext(TaskEntity taskEntity) {
            super.onNext(taskEntity);
            onCreateTaskSuccessTracking(taskEntity);
            onCreateTaskSuccess();
        }
    }
}