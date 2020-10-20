package cse.dit012.lost.service.logIn;


import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import cse.dit012.lost.service.login.LoginService;
import cse.dit012.lost.service.login.LoginServiceFactory;

@RunWith(AndroidJUnit4.class)
public class LoginServiceTest {
    LoginServiceFactory loginServiceFactory;
    LoginService loginService;

    @Test
    public void doesUserExist() throws ExecutionException, InterruptedException {

        loginService = LoginServiceFactory.createEmailAndPasswordService("test@test.com", "test123");

        loginService.login().get();

    }

    @Test(expected = ExecutionException.class)
    public void userDoesntExist() throws ExecutionException, InterruptedException {

        loginService = LoginServiceFactory.createEmailAndPasswordService("test@test.com", "test1234");

        loginService.login().get();

    }

}
