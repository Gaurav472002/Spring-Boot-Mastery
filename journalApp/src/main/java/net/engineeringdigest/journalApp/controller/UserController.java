package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.PostRequest;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(
        name = "User APIs",
        description = "APIs available to authenticated users"
)
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;


    @Operation(
            summary = "Get all users",
            description = "Returns a list of all users"
    )
    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = userService.getAll();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @Operation(
            summary = "Update current user",
            description = "Updates the username and password of the currently authenticated user"
    )
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody User user) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        User userInDb =
                userService.findByUserName(userName);

        if (userInDb != null) {

            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());

            userService.updateUser(userInDb);

            return new ResponseEntity<>(
                    userInDb,
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );
    }


    @Operation(
            summary = "Delete current user",
            description = "Deletes the currently authenticated user"
    )
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        User user =
                userService.findByUserName(userName);

        if (user != null) {

            userService.deleteById(user.getId());

            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT
            );
        }

        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );
    }


    @Operation(
            summary = "Get weather",
            description = "Returns weather information for the requested city"
    )
    @GetMapping("/greeting")
    public ResponseEntity<?> greeting(

            @Parameter(
                    description = "Name of the city",
                    example = "Bhubaneswar",
                    required = true
            )
            @RequestParam String city) {

        WeatherResponse weatherResponse =
                weatherService.getWeather(city);

        if (weatherResponse == null) {

            return new ResponseEntity<>(
                    "Weather unavailable for city: " + city,
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                weatherResponse,
                HttpStatus.OK
        );
    }


    @Operation(
            summary = "Send POST request",
            description = "Sends a sample POST request using RestTemplate"
    )
    @PostMapping("/sendPost")
    public ResponseEntity<?> sendPost(
            @RequestBody PostRequest postRequest) {

        String response =
                weatherService.sendPost(postRequest);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }
}