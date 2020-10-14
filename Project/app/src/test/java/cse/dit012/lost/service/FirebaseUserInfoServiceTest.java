package cse.dit012.lost.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class FirebaseUserInfoServiceTest {

    @Test
    public void getUserInfoService() {
        assertNotNull(UserInfoService.getUserInfoService());
    }
}