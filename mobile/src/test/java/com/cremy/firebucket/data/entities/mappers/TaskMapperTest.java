package com.cremy.firebucket.data.entities.mappers;

import com.cremy.firebucket.data.entities.TaskEntity;
import com.cremy.firebucket.domain.models.TaskModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTest {

  private static final String FAKE_TASK_ID = "xIUHifhosdOI67";
  private static final String FAKE_TASK_TITLE = "Buy some milk";
  private static final long FAKE_TASK_DEADLINE_MS = 999999999;
  private static final String FAKE_TASK_TAG = "Work";

  @Test
  public void testTransformEntity() {
    TaskEntity taskEntity = createEntity();
    TaskModel taskModel = TaskMapper.transform(taskEntity);

    assertThat(taskModel, is(instanceOf(TaskModel.class)));
    assertThat(taskModel.getId(), is(FAKE_TASK_ID));
    assertThat(taskModel.getTitle(), is(FAKE_TASK_TITLE));
    assertThat(taskModel.getDeadlineMs()).isEqualTo(FAKE_TASK_DEADLINE_MS);
    assertThat(taskModel.getTag(), is(FAKE_TASK_TAG));
  }

  private TaskEntity createEntity() {
    TaskEntity taskEntity = new TaskEntity();
    taskEntity.setId(FAKE_TASK_ID);
    taskEntity.setTitle(FAKE_TASK_TITLE);
    taskEntity.setDeadlineMs(FAKE_TASK_DEADLINE_MS);
    taskEntity.setTag(FAKE_TASK_TAG);
    return taskEntity;
  }
}