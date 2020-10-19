package cse.dit012.lost.service.authenticateduser;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cse.dit012.lost.model.user.UserId;
import cse.dit012.lost.service.login.LoginService;
import cse.dit012.lost.service.login.LoginServiceFactory;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AuthenticatedUserServiceTest {
    @Before
    public void setUp() throws InterruptedException, ExecutionException, TimeoutException {
        LoginService login = LoginServiceFactory.createEmailAndPasswordService("test@test.com", "test123");
        login.login().get(5, TimeUnit.SECONDS);
    }

    @Test
    public void getEmailTest() {
        assertEquals(AuthenticatedUserService.userService.getEmail(), "test@test.com");
    }

    @Test
    public void getIDTest() {
        assertEquals(AuthenticatedUserService.userService.getID(), new UserId("zqy2GeW5GNNHSLUCEbl53dYe5zD2"));
    }
}
