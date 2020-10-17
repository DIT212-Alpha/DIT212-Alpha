package cse.dit012.lost.service;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class FirebaseAuthenticatedUserServiceTest {
    @Test
    public void getUserInfoService() {
        assertNotNull(AuthenticatedUserService.get());
    }
}