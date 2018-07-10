package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class UserTest {

    User user;
    User updateUser;

    @Before
    public void setUp(){
        user = new User((long)1, "kookooku", "1234", "koo", "abc@abc");
        updateUser = new User((long)1, "kookooku", "1234", "updateName", "abc@abc");
    }

    @Test
    public void isSamePassword(){

        assertThat(true, is(user.isSamePassword("1234")));
        assertThat(false, is(user.isSamePassword("23345")));
    }

    @Test
    public void updateUser(){
        user.updateUser(updateUser);
        assertUser(updateUser,user);
    }

    private void assertUser(User expected , User valid){
        assertThat(expected.getId(), is(valid.getId()));
        assertThat(expected.getUserId(), is(valid.getUserId()));
        assertThat(expected.getPassword(), is(valid.getPassword()));
        assertThat(expected.getName(), is(valid.getName()));
        assertThat(expected.getEmail(), is(valid.getEmail()));
    }
}