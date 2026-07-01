package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.userRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private userRepo userRepository;

    // We will use slf4j which is an abstraction for logback framework
//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /* Instead of creatintg a logger again and again use can use @Slf4J annotation and log */
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    public void saveNewUser(User user) {

        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        }catch(Exception e){
//            logger.info("Duplicate user found");
//            // we can add dynamic arguments as well
//            logger.error("Duplicate user found {}",user.getUserName(), e);
//            logger.warn("Duplicate user found");
//
//            // By default the above three are enabled
//            logger.debug("Duplicate user found");
//            logger.trace("Duplicate user found");


            log.info("Duplicate user found");
            // we can add dynamic arguments as well
            log.error("Duplicate user found {}",user.getUserName(), e);
            log.warn("Duplicate user found");

            // By default the above three are enabled
            log.debug("Duplicate user found");
            log.trace("Duplicate user found");
        }

    }

    public void saveAdmin(User admin){

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRoles(Arrays.asList("USER","ADMIN"));

        userRepository.save(admin);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void updateUser(User user){

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        userRepository.save(user);
    }
}