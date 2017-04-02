package com.cremy.firebucket.data.entities.mappers;

import com.cremy.firebucket.data.entities.UserEntity;
import com.cremy.firebucket.domain.models.UserModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

  private static final String FAKE_USER_USERNAME = "Robert@mail.com";
  private static final String FAKE_USER_UID = "2345345234xhusuhf";
  @Test
  public void testTransformUserEntity() {
    UserEntity userEntity = createUserEntity();
    UserModel userModel = UserMapper.transform(userEntity);

    assertThat(userModel, is(instanceOf(UserModel.class)));
    assertThat(userModel.getEmail(), is(FAKE_USER_USERNAME));
    assertThat(userModel.getUid(), is(FAKE_USER_UID));
    assertThat(userModel.getUsername(), is(FAKE_USER_USERNAME));
  }

  private UserEntity createUserEntity() {
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(FAKE_USER_USERNAME);
    userEntity.setUid(FAKE_USER_UID);
    userEntity.setEmail(FAKE_USER_USERNAME);
    return userEntity;
  }
}