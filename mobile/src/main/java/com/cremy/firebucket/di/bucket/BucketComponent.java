package com.cremy.firebucket.di.bucket;

import com.cremy.firebucket.data.repositories.BucketRepository;
import com.cremy.firebucket.data.repositories.TaskRepository;
import com.cremy.firebucket.data.repositories.datasource.BucketDataSourceRemote;
import com.cremy.firebucket.data.repositories.datasource.TaskDataSourceRemote;
import com.cremy.firebucket.di.app.component.AppComponent;
import com.cremy.firebucket.di.scope.ActivityScope;
import com.cremy.firebucket.domain.interactors.bucket.GetBucketUseCase;
import com.cremy.firebucket.domain.interactors.task.DeleteTaskUseCase;
import com.cremy.firebucket.presentation.presenters.impl.BucketPresenter;
import com.cremy.firebucket.presentation.presenters.impl.CreateTaskPresenter;
import com.cremy.firebucket.presentation.ui.fragments.BucketFragment;
import com.cremy.firebucket.presentation.ui.fragments.CreateTaskFragment;

import dagger.Component;

/**
 * The role of the component is to inject the dependencies in the specified targets
 * Targets must ALL be added here
 */
@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = BucketModule.class
)
public interface BucketComponent {

    //Fragments
    void inject(BucketFragment view);

    // Presenters
    void inject(BucketPresenter presenter);

    // UseCases/Interactors
    void inject(GetBucketUseCase useCase);
    void inject(DeleteTaskUseCase useCase);

    // Repositories
    void inject(BucketRepository repository);
    void inject(TaskRepository repository);

    // DataSources
    void inject(BucketDataSourceRemote dataSource);
    void inject(TaskDataSourceRemote dataSource);
}