package com.cremy.firebucket.domain.models;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class UserModelTest {

  private static final String FAKE_USER_USERNAME = "Robert@mail.com";
  private static final String FAKE_USER_USERNAME_KO = "Robertmail.com";

  private UserModel userModel;

  @Before
  public void setUp() {
    userModel = new UserModel(FAKE_USER_USERNAME);
  }

  @Test
  public void testUserConstructorOK() {
    final String username = userModel.getUsername();

    assertThat(username).isEqualTo(FAKE_USER_USERNAME);
  }

  @Test
  public void testTransactionConstructorKO() {
    final String username = userModel.getUsername();

    assertThat(username).isNotEqualTo(FAKE_USER_USERNAME_KO);
  }
}