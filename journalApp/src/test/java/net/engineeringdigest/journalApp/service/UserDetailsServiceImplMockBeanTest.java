package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.userRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.mockito.Mockito.when;

/*
 * @ExtendWith(MockitoExtension.class)
 * Enables Mockito support in JUnit 5.
 * It automatically initializes all @Mock and @InjectMocks objects.
 */
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplMockBeanTest {

    /*
     * @InjectMocks
     * Creates an object of UserDetailsServiceImpl and injects all the
     * mocked dependencies (@Mock) into it.
     */
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    /*
     * @Mock
     * Creates a mock object of userRepo.
     * No actual database call will be made.
     */
    @Mock
    private userRepo userRepository;

    /*
     * @Test
     * Marks this method as a JUnit test case.
     */
    @Test
    void loadUserByUsernameTest() {

        // Creating a dummy user object
        User mockUser = new User();
        mockUser.setUserName("admin2");
        mockUser.setPassword("abcde");
        mockUser.setRoles(java.util.Arrays.asList("USER"));

        /*
         * when(...).thenReturn(...)
         * Mock the repository behaviour.
         *
         * Whenever findByUserName() is called with ANY string,
         * return the dummy user instead of querying MongoDB.
         */
        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(mockUser);

        // Call the actual service method
        UserDetails user =
                userDetailsService.loadUserByUsername("admin2");

        // Verify the returned object is not null
        Assertions.assertNotNull(user);

        // Verify username
        Assertions.assertEquals("admin2", user.getUsername());

        // Verify password
        Assertions.assertEquals("abcde", user.getPassword());

        // Verify role
        Assertions.assertTrue(
                user.getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_USER"))
        );
    }
}