package com.cremy.firebucket.domain.interactors.task;

import com.cremy.firebucket.data.repositories.TaskRepository;
import com.cremy.firebucket.domain.interactors.Params;
import com.cremy.firebucket.domain.interactors.base.BaseUseCase;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Implementation of {@link BaseUseCase} that represents a UseCase/Interactor
 */
public class DeleteTaskUseCase extends BaseUseCase {

  public final static String PARAMS_KEY_TASK_ID = "task_id";

  private final TaskRepository taskRepository;

  @Inject
  public DeleteTaskUseCase(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Override public Observable getObservable(Params params) {
    return this.taskRepository.deleteTask(params.getString(PARAMS_KEY_TASK_ID, "0"));
  }
}