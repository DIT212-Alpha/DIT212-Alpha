package cse.dit012.lost.service.authenticateduser;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FirebaseAuthenticatedUserServiceTest {
    @Test
    public void getUserInfoService() {
        assertNotNull(AuthenticatedUserService.userService);
    }
}