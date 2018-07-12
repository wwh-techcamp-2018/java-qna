package codesquad.domain;

import codesquad.exception.UserFailureException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;


public class UserTest {

    User user;
    User updateUser;
    User differentSessionUser;
    User sameSessionUser;

    @Before
    public void setUp(){
        user = new User((long)1, "kookooku", "1234", "koo", "abc@abc");
        updateUser = new User((long)1, "kookooku", "1234", "updateName", "abc@abc");
        differentSessionUser =  new User((long)1, "kookooku2", "12345", "koo", "abc@abc");
        sameSessionUser =  new User((long)1, "kookooku", "1234", "koo", "abc@abc");
    }

    @Test
    public void isSamePassword(){
        assertThat(true, is(user.isSamePassword("1234")));
        assertThat(false, is(user.isSamePassword("23345")));
    }

    @Test
    public void updateUser(){
        user.update(updateUser, sameSessionUser);
        assertThat(updateUser).isEqualTo(user);
    }

    @Test(expected = UserFailureException.class)
    public void updateFailure() {
        user.update(updateUser, differentSessionUser);
    }
}