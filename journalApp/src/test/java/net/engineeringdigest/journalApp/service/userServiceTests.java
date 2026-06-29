package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.repository.userRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// Without this the application context won't be created and the beans will not be created
@SpringBootTest
public class userServiceTests {

    @Autowired
    userRepo userRepository;


    // To disable a test we can use this annotation others test will run except this one
//    @Disabled
//    @Test
    @ParameterizedTest
    @ValueSource(strings = {
            "admin2",
            "gaurav",
            "admin3",
            "admin4"
    })
    public void findUser(String name){

        // There are various assert methods than can be tried to test various scenarios
//        assertEquals(4,2+2);
//        assertNotNull(userRepository.findByUserName("admin2"));
        assertNotNull(userRepository.findByUserName(name));
//        assertTrue(5<650);
    }

    // Paramerterized test with multiple arguments

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "10,2,12",
            "20,30,50",
            "5,12,17"
    })
    public void  paramerterizedTest(int a ,int b, int expected){
        assertEquals(expected,a+b);
    }
}


//@BeforeAll @BeforeEach @AfterEach @AfterAll for doing something before and after tests