package com.cremy.firebucket.domain.models;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class UserModelTest {

  private static final String FAKE_USER_USERNAME = "Robert@mail.com";
  private static final String FAKE_USER_USERNAME_KO = "Robertmail.com";

  private static final String FAKE_USER_UID = "NJtezZhXfOMNK39ZWt8AIJ9WMbx2";
  private static final String FAKE_USER_UID_KO = "NJtezZhXfOMNK39ZWt8AIJ9WMb__";

  private UserModel userModel;

  @Before
  public void setUp() {
    userModel = new UserModel(FAKE_USER_USERNAME);
    userModel.setUid(FAKE_USER_UID);
  }

  @Test
  public void testConstructorOK() {
    final String username = userModel.getUsername();
    final String uid = userModel.getUid();

    assertThat(username).isEqualTo(FAKE_USER_USERNAME);
    assertThat(uid).isEqualTo(FAKE_USER_UID);
  }

  @Test
  public void testConstructorKO() {
    final String username = userModel.getUsername();
    final String uid = userModel.getUid();

    assertThat(username).isNotEqualTo(FAKE_USER_USERNAME_KO);
    assertThat(uid).isNotEqualTo(FAKE_USER_UID_KO);
  }
}