package com.cremy.firebucket.di.bucket;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.cremy.firebucket.data.repositories.BucketRepository;
import com.cremy.firebucket.data.repositories.TaskRepository;
import com.cremy.firebucket.data.repositories.datasource.BucketDataSourceRemote;
import com.cremy.firebucket.data.repositories.datasource.TaskDataSourceRemote;
import com.cremy.firebucket.domain.interactors.bucket.GetBucketUseCase;
import com.cremy.firebucket.domain.interactors.taglist.GetTagListUseCase;
import com.cremy.firebucket.domain.interactors.task.DeleteTaskUseCase;
import com.cremy.firebucket.presentation.presenters.impl.BucketPresenter;
import com.cremy.firebucket.presentation.presenters.impl.CreateTaskPresenter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class BucketModule {

    private Fragment fragment;

    public BucketModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return fragment;
    }

    @Provides
    BucketPresenter provideBucketPresenter(GetBucketUseCase getBucketUseCase,
                                           DeleteTaskUseCase deleteTaskUseCase,
                                           FirebaseAnalytics firebaseAnalytics,
                                           SharedPreferences sharedPreferences) {
        return new BucketPresenter(getBucketUseCase,
                deleteTaskUseCase,
                firebaseAnalytics,
                sharedPreferences);
    }

    @Provides
    GetBucketUseCase provideGetBucketUseCase(BucketRepository repository) {
        return new GetBucketUseCase(repository);
    }

    @Provides
    BucketDataSourceRemote provideBucketDataSource(FirebaseAuth firebaseAuth,
                                             FirebaseDatabase firebaseDatabase) {
        return new BucketDataSourceRemote(firebaseAuth, firebaseDatabase);
    }

    @Provides
    BucketRepository provideBucketRepository(BucketDataSourceRemote dataSource) {
        return new BucketRepository(dataSource);
    }

    @Provides
    DeleteTaskUseCase provideDeleteTaskUseCase(TaskRepository repository) {
        return new DeleteTaskUseCase(repository);
    }

    @Provides
    TaskDataSourceRemote provideTaskDataSource(FirebaseAuth firebaseAuth,
                                             FirebaseDatabase firebaseDatabase) {
        return new TaskDataSourceRemote(firebaseAuth, firebaseDatabase);
    }

    @Provides
    TaskRepository provideTaskRepository(TaskDataSourceRemote dataSource) {
        return new TaskRepository(dataSource);
    }

}