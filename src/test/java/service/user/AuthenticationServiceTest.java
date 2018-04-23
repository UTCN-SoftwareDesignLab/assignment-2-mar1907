package service.user;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import repository.user.UserRepository;

import static org.junit.Assert.*;

public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;

    @Before
    public void setUp() throws Exception {
        authenticationService = new AuthenticationServiceImplementation(Mockito.mock(UserRepository.class));
    }

    @Test
    public void register() {
        assertNotNull(authenticationService.register("admin","parola1!",1));
    }

    @Test
    public void login() {
        assertNotNull(authenticationService.login("admin","parola1!"));
    }
}