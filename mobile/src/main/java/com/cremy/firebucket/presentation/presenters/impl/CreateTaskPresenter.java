package com.cremy.firebucket.presentation.presenters.impl;

import android.os.Bundle;

import com.cremy.firebucket.domain.interactors.Params;
import com.cremy.firebucket.domain.interactors.taglist.GetTagListUseCase;
import com.cremy.firebucket.domain.interactors.task.CreateTaskUseCase;
import com.cremy.firebucket.domain.models.TagListModel;
import com.cremy.firebucket.domain.models.TaskModel;
import com.cremy.firebucket.domain.models.TaskPriorityModel;
import com.cremy.firebucket.external.AnalyticsInterface;
import com.cremy.firebucket.external.TaskReminderInterface;
import com.cremy.firebucket.firebase.services.ReminderService;
import com.cremy.firebucket.presentation.presenters.CreateTaskMVP;
import com.cremy.firebucket.presentation.presenters.base.BasePresenter;
import com.cremy.firebucket.rx.DefaultObserver;
import com.cremy.greenrobotutils.library.util.NetworkUtils;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class CreateTaskPresenter extends BasePresenter<CreateTaskMVP.View>
        implements CreateTaskMVP.Presenter {
    private final static String TAG = CreateTaskPresenter.class.getName();

    private final GetTagListUseCase getTagListUseCase;
    private final CreateTaskUseCase createTaskUseCase;
    private final AnalyticsInterface analyticsInterface;
    private final TaskReminderInterface taskReminderInterface;

    @Inject
    public CreateTaskPresenter(GetTagListUseCase getTagListUseCase,
                               CreateTaskUseCase createTaskUseCase,
                               AnalyticsInterface analyticsInterface,
                               TaskReminderInterface taskReminderInterface) {
        this.getTagListUseCase = getTagListUseCase;
        this.createTaskUseCase = createTaskUseCase;
        this.analyticsInterface = analyticsInterface;
        this.taskReminderInterface = taskReminderInterface;
    }

    @Override
    public void attachView(CreateTaskMVP.View view) {
        super.attachView(view);
        analyticsInterface.trackPageView(AnalyticsInterface.VIEW_CREATE_TASK);
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
        analyticsInterface.trackGetTagListSuccess(null);
    }

    @Override
    public void onGetTagListFailureTracking(Throwable e) {
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsInterface.PARAM_MESSAGE, e.getMessage());
        analyticsInterface.trackGetTagListFailure(bundle);
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

        createTaskUseCase.execute(new CreateTaskPresenter.CreateTaskObserver(), params);
    }

    @Override
    public void onCreateTaskSuccess(TaskModel taskModel) {
        setReminder(taskModel);
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
        analyticsInterface.trackCreateTaskSuccess(bundle);
    }

    @Override
    public void onCreateTaskFailureTracking(Throwable e) {
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsInterface.PARAM_MESSAGE, e.getMessage());
        analyticsInterface.trackCreateTaskFailure(bundle);
    }

    @Override
    public void setReminder(TaskModel taskModel) {
        checkViewAttached();
        if (view.isReminderSet()) {
            long reminderMs = view.getReminderDate().getTime();
            long nowMs = Calendar.getInstance().getTime().getTime();
            int diffSecs = (int) ((reminderMs - nowMs) / 1000);

            Bundle extras = new Bundle();
            extras.putString(ReminderService.BUNDLE_KEY_TASK_TITLE, taskModel.getTitle());
            extras.putString(ReminderService.BUNDLE_KEY_TASK_PRIORITY, taskModel.getPriority().getLabel());
            extras.putString(ReminderService.BUNDLE_KEY_TASK_TAG, taskModel.getTag());

            taskReminderInterface.setTaskReminder(view.getContext(),
                    taskModel.getId(),
                    diffSecs,
                    extras);
        }
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

    private final class CreateTaskObserver extends DefaultObserver<TaskModel> {

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
            onCreateTaskSuccess(taskModel);
        }
    }
}