package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/* The rest controller not only makes this class a component but also makes sure that
all the end points we define here will returnn the response in JSON format */

@RestController

@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @GetMapping("/HealthCheck")

    public String healthCheck(){
        return "OK";
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user){

        userService.saveNewUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
