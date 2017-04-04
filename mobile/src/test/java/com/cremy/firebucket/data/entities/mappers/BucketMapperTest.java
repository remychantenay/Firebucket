package com.cremy.firebucket.data.entities.mappers;

import com.cremy.firebucket.data.entities.BucketEntity;
import com.cremy.firebucket.data.entities.TaskEntity;
import com.cremy.firebucket.domain.models.BucketModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BucketMapperTest {

  private static final String FAKE_TASK_ID = "xIUHifhosdOI67";
  private static final String FAKE_TASK_ID_2 = "xIojsdfsdsf87667x";

  HashMap<String, TaskEntity> tasks;

  @Before
  public void setUp() {
    tasks = new HashMap<>();
  }

  @Test
  public void testTransformEntity() {
    BucketEntity bucketEntity = createEntity();

    TaskEntity taskEntity = new TaskEntity();
    taskEntity.setId(FAKE_TASK_ID);
    TaskEntity taskEntity2 = new TaskEntity();
    taskEntity2.setId(FAKE_TASK_ID_2);

    tasks.put(FAKE_TASK_ID, taskEntity);
    tasks.put(FAKE_TASK_ID_2, taskEntity2);

    BucketModel bucketModel = BucketMapper.transform(bucketEntity);
    assertThat(bucketModel, is(instanceOf(BucketModel.class)));
    assertThat(bucketModel.getTasks()).hasSize(2);
  }

  @Test
  public void testTransformEmptyEntity() {
    BucketEntity bucketEntity = createEntity();

    BucketModel bucketModel = BucketMapper.transform(bucketEntity);
    assertThat(bucketModel, is(instanceOf(BucketModel.class)));
    assertThat(bucketModel.getTasks()).isEmpty();
    assertThat(bucketModel.getTasks()).hasSize(0);
  }

  private BucketEntity createEntity() {
    BucketEntity BucketEntity = new BucketEntity();
    BucketEntity.setTasks(tasks);
    return BucketEntity;
  }
}