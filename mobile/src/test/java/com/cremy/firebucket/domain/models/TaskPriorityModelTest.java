package com.cremy.firebucket.domain.models;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TaskPriorityModelTest {

  private static final int FAKE_TASK_PRIORITY_ID = 1;
  private static final int FAKE_TASK_PRIORITY_ID_KO = -1;

  private static final String FAKE_TASK_PRIORITY_LABEL = "Normal";
  private static final String FAKE_TASK_PRIORITY_LABEL_KO = "FakeValue";

  private TaskPriorityModel taskPriorityModel;

  @Before
  public void setUp() {
    taskPriorityModel = new TaskPriorityModel(FAKE_TASK_PRIORITY_ID, FAKE_TASK_PRIORITY_LABEL);
  }

  @Test
  public void testConstructorOK() {
    final int id = taskPriorityModel.getId();
    final String label = taskPriorityModel.getLabel();

    assertThat(id).isEqualTo(FAKE_TASK_PRIORITY_ID);
    assertThat(label).isEqualTo(FAKE_TASK_PRIORITY_LABEL);
  }

  @Test
  public void testConstructorKO() {
    final int id = taskPriorityModel.getId();
    final String label = taskPriorityModel.getLabel();

    assertThat(id).isNotEqualTo(FAKE_TASK_PRIORITY_ID_KO);
    assertThat(label).isNotEqualTo(FAKE_TASK_PRIORITY_LABEL_KO);
  }
}