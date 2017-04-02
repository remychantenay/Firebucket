package com.cremy.firebucket.data.entities.mappers;

import com.cremy.firebucket.data.entities.TaskPriorityEntity;
import com.cremy.firebucket.domain.models.TaskPriorityModel;

/**
 * Created by remychantenay on 23/02/2017.
 */
public class TaskPriorityMapper {

    public static TaskPriorityModel transform(TaskPriorityEntity taskPriorityEntity) {
        TaskPriorityModel taskPriorityModel = null;
        if (taskPriorityEntity != null) {
            taskPriorityModel = new TaskPriorityModel(taskPriorityEntity.getId(),
                    taskPriorityEntity.getLabel());
            taskPriorityModel.setId(taskPriorityEntity.getId());
            taskPriorityModel.setLabel(taskPriorityEntity.getLabel());
        }

        return taskPriorityModel;
    }
}
