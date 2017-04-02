package com.cremy.firebucket.data.repositories;

import com.cremy.firebucket.data.entities.TaskEntity;
import com.cremy.firebucket.data.entities.TaskPriorityEntity;
import com.cremy.firebucket.data.repositories.datasource.TaskDataSourceRemote;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * The repository is in charge of getting the data from the DataSource,
 * It will also map data from entity to model
 * Created by remychantenay on 23/02/2017.
 */
@Singleton
public class TaskRepository {

    private final TaskDataSourceRemote taskDataSourceRemote;

    @Inject
    public TaskRepository(TaskDataSourceRemote taskDataSourceRemote) {
        this.taskDataSourceRemote = taskDataSourceRemote;
    }

    public Observable createTask(String title,
                                         String deadline,
                                         long deadlineMs,
                                         String tag,
                                         int priorityId,
                                         String priorityLabel) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(title);
        taskEntity.setTag(tag);
        taskEntity.setDeadlineMs(deadlineMs);
        taskEntity.setDeadline(deadline);
        taskEntity.setPriority(new TaskPriorityEntity(priorityId, priorityLabel));
        return taskDataSourceRemote.createTask(taskEntity);
    }

    public Observable<TaskEntity> deleteTask(String taskId) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(taskId);
        return taskDataSourceRemote.deleteTask(taskEntity);
    }
}
