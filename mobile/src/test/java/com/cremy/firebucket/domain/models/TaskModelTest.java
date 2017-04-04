package com.cremy.firebucket.domain.models;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TaskModelTest {

  private static final String FAKE_TASK_ID = "xIUHifhosdOI67";
  private static final String FAKE_TASK_ID_KO = "";

  private static final String FAKE_TASK_TITLE = "Buy some milk";
  private static final String FAKE_TASK_TITLE_KO = "";

  private static final long FAKE_TASK_DEADLINE_MS = 999999999;
  private static final long FAKE_TASK_DEADLINE_MS_KO = 0;

  private static final String FAKE_TASK_TAG = "Work";
  private static final String FAKE_TASK_TAG_KO = "";

  private TaskModel taskModel;

  @Before
  public void setUp() {
    taskModel = new TaskModel(FAKE_TASK_TITLE);
    taskModel.setId(FAKE_TASK_ID);
    taskModel.setDeadlineMs(FAKE_TASK_DEADLINE_MS);
    taskModel.setTag(FAKE_TASK_TAG);
  }

  @Test
  public void testConstructorOK() {
    final String id = taskModel.getId();
    final String title = taskModel.getTitle();
    final long deadlineMs = taskModel.getDeadlineMs();
    final String tag = taskModel.getTag();

    assertThat(id).isEqualTo(FAKE_TASK_ID);
    assertThat(title).isEqualTo(FAKE_TASK_TITLE);
    assertThat(deadlineMs).isEqualTo(FAKE_TASK_DEADLINE_MS);
    assertThat(tag).isEqualTo(FAKE_TASK_TAG);
  }

  @Test
  public void testConstructorKO() {
    final String id = taskModel.getId();
    final String title = taskModel.getTitle();
    final long deadlineMs = taskModel.getDeadlineMs();
    final String tag = taskModel.getTag();

    assertThat(id).isNotEqualTo(FAKE_TASK_ID_KO);
    assertThat(title).isNotEqualTo(FAKE_TASK_TITLE_KO);
    assertThat(deadlineMs).isNotEqualTo(FAKE_TASK_DEADLINE_MS_KO);
    assertThat(tag).isNotEqualTo(FAKE_TASK_TAG_KO);
  }

  @Test
  public void testPriorityOK() {
    taskModel.setPriority(new TaskPriorityModel());

    assertThat(taskModel.getPriority()).isNotNull();
    assertThat(taskModel.getPriority().getLabel()).isEqualTo(TaskPriorityModel.PRIORITY_NORMAL_LABEL);
    assertThat(taskModel.getPriority().getId()).isEqualTo(TaskPriorityModel.PRIORITY_NORMAL_ID);
  }

  @Test
  public void testPriorityKO() {
    taskModel.setPriority(null);

    assertThat(taskModel.getPriority()).isNull();
  }
}