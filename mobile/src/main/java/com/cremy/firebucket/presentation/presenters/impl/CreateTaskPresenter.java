package com.cremy.firebucket.presentation.presenters.impl;

import android.net.Network;
import android.os.Bundle;

import com.cremy.firebucket.R;
import com.cremy.firebucket.data.exceptions.NetworkConnectionException;
import com.cremy.firebucket.domain.interactors.Params;
import com.cremy.firebucket.domain.interactors.taglist.GetTagListUseCase;
import com.cremy.firebucket.domain.interactors.task.CreateTaskUseCase;
import com.cremy.firebucket.domain.models.TagListModel;
import com.cremy.firebucket.domain.models.TaskModel;
import com.cremy.firebucket.domain.models.TaskPriorityModel;
import com.cremy.firebucket.firebase.FirebaseAnalyticsHelper;
import com.cremy.firebucket.presentation.presenters.CreateTaskMVP;
import com.cremy.firebucket.presentation.presenters.base.BasePresenter;
import com.cremy.firebucket.rx.DefaultObserver;
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

        if (title.isEmpty() || deadline == null || tag.isEmpty()) {
            checkViewAttached();
            view.showMessageInvalidTaskTitle();
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
    }

    @Override
    public void onCreateTaskSuccessTracking(TaskModel taskModel) {
        Bundle bundle = new Bundle();
        bundle.putString(CreateTaskUseCase.PARAMS_KEY_TAG, taskModel.getTag());
        bundle.putString(CreateTaskUseCase.PARAMS_KEY_PRIORITY_LABEL, taskModel.getPriority().getLabel());
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

    private final class GetCreateTaskObserver extends DefaultObserver<TaskModel> {

        @Override public void onComplete() {
            super.onComplete();
        }

        @Override public void onError(Throwable e) {
            super.onError(e);
            onCreateTaskFailureTracking(e);
            onCreateTaskFailure(e);
        }

        @Override public void onNext(TaskModel taskModel) {
            super.onNext(taskModel);
            onCreateTaskSuccessTracking(taskModel);
            onCreateTaskSuccess();
        }
    }
}