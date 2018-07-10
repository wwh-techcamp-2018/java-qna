package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUserId("sheep1500");
        user.setPassword("1234");
        user.setName("ksy");
        user.setEmail("ksy@woowahan.com");
    }

    @Test
    public void updateSucceed() {
        User userTest = new User("sheep1500", "1234", "ksy_test", "ksy_test@woowahan.com");
        userTest.setId(1L);
        user.update(userTest);
        assertEquals(user, userTest);
    }

    @Test
    public void updateFailById() {
        User userTest = new User("sheep", "1233", "ksy_test", "ksy_test@woowahan.com");
        user.update(userTest);
        assertNotEquals(user, userTest);
    }

    @Test
    public void updateFailByPassword() {
        User userTest = new User("sheep1500", "1233_fail", "ksy_test", "ksy_test@woowahan.com");
        user.update(userTest);
        assertNotEquals(user, userTest);
    }

    @Test
    public void matchId() {
        assertEquals(true, user.matchId(1L));
        assertEquals(false, user.matchId(-1L));
    }

    @Test
    public void matchUserId() {
        assertEquals(true, user.matchUserId("sheep1500"));
        assertEquals(false, user.matchUserId("abc"));
    }

    @Test
    public void matchPassword() {
        assertEquals(true, user.matchPassword("1234"));
        assertEquals(false, user.matchPassword("abc"));
    }
}