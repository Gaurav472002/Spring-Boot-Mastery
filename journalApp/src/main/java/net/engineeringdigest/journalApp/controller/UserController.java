package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = userService.getAll();

        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user){

        userService.saveEntry(user);

        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{userName}")
    public ResponseEntity<?> updateUser(
            @RequestBody User user,
            @PathVariable String userName){

        User userInDb = userService.findByUserName(userName);

        if(userInDb != null){

            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());

            userService.saveEntry(userInDb);

            return new ResponseEntity<>(userInDb,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/deleteUser/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName){

        User user = userService.findByUserName(userName);

        if(user != null){
            userService.deleteById(user.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
