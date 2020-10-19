package cse.dit012.lost.service.logIn;


import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.service.broadcast.BroadcastService;
import cse.dit012.lost.service.login.LoginService;
import cse.dit012.lost.service.login.LoginServiceFactory;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class LoginServiceTest {
  LoginServiceFactory loginServiceFactory;
  LoginService loginService;

    @Test
    public void doesUserExist() throws ExecutionException, InterruptedException {

      loginService =  LoginServiceFactory.createEmailAndPasswordService("test@test.com","test123");

       loginService.login().get();

    }

    @Test(expected = ExecutionException.class)
    public void userDoesntExist() throws ExecutionException, InterruptedException {

      loginService =  LoginServiceFactory.createEmailAndPasswordService("test@test.com","test1234");

      loginService.login().get();

    }

}
