package com.cremy.firebucket.di.task;

import com.cremy.firebucket.data.repositories.TagListRepository;
import com.cremy.firebucket.data.repositories.TaskRepository;
import com.cremy.firebucket.data.repositories.datasource.TagListDataSourceRemote;
import com.cremy.firebucket.data.repositories.datasource.TaskDataSourceRemote;
import com.cremy.firebucket.di.app.component.AppComponent;
import com.cremy.firebucket.di.scope.ActivityScope;
import com.cremy.firebucket.domain.interactors.taglist.GetTagListUseCase;
import com.cremy.firebucket.domain.interactors.task.CreateTaskUseCase;
import com.cremy.firebucket.domain.interactors.task.DeleteTaskUseCase;
import com.cremy.firebucket.presentation.presenters.impl.CreateTaskPresenter;
import com.cremy.firebucket.presentation.ui.fragments.CreateTaskFragment;

import dagger.Component;

/**
 * The role of the component is to inject the dependencies in the specified targets
 * Targets must ALL be added here
 */
@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = TaskModule.class
)
public interface TaskComponent {

    //Fragments
    void inject(CreateTaskFragment view);

    // Presenters
    void inject(CreateTaskPresenter presenter);

    // UseCases/Interactors
    void inject(GetTagListUseCase useCase);
    void inject(CreateTaskUseCase useCase);
    void inject(DeleteTaskUseCase useCase);

    // Repositories
    void inject(TaskRepository repository);
    void inject(TagListRepository repository);

    // DataSources
    void inject(TaskDataSourceRemote dataSource);
    void inject(TagListDataSourceRemote dataSource);
}