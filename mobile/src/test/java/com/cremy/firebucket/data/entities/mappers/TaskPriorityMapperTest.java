package com.cremy.firebucket.data.entities.mappers;

import com.cremy.firebucket.data.entities.TaskPriorityEntity;
import com.cremy.firebucket.domain.models.TaskPriorityModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TaskPriorityMapperTest {

  private static final int FAKE_PRIORITY_ID = TaskPriorityModel.PRIORITY_NORMAL_ID;
  private static final String FAKE_PRIORITY_LABEL = TaskPriorityModel.PRIORITY_NORMAL_LABEL;
  @Test
  public void testTransformEntity() {
    TaskPriorityEntity taskPriorityEntity = createEntity();
    TaskPriorityModel taskPriorityModel = TaskPriorityMapper.transform(taskPriorityEntity);

    assertThat(taskPriorityModel, is(instanceOf(TaskPriorityModel.class)));
    assertThat(taskPriorityModel.getId(), is(FAKE_PRIORITY_ID));
    assertThat(taskPriorityModel.getLabel(), is(FAKE_PRIORITY_LABEL));
  }

  private TaskPriorityEntity createEntity() {
    TaskPriorityEntity taskPriorityEntity = new TaskPriorityEntity();
    taskPriorityEntity.setId(FAKE_PRIORITY_ID);
    taskPriorityEntity.setLabel(FAKE_PRIORITY_LABEL);
    return taskPriorityEntity;
  }
}