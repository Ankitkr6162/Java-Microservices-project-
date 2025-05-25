package com.user.service.UserService.controller;

import com.user.service.UserService.entity.User;
import com.user.service.UserService.service.UserService;
import com.user.service.UserService.service.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/save")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = this.userService.saveUser(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/all-user")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUsers = this.userService.getAllUser();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    int retryCount = 1;

    @GetMapping("/user-id/{userId}")
  //  @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
   // @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallBack")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        logger.info("Retry Count {}",retryCount);
        retryCount++;
        User user = this.userService.getUserByUserId(userId);
        return ResponseEntity.ok(user);
    }



    // Creating fallback method for ratingHotelBreaker (Circuit Breaker)
    // fallBackMethod name should be same as it used above as well as the return type  and parameters
    public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex) {
        logger.info("Fallback message because service is down" + ex.getMessage());
        User user = new User();
        user.setEmail("dummy@gmail.com");
        user.setName("Dummy");
        user.setAbout("The created user is dummy because the service is down");
        user.setUserId("0000");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    //http://192.168.214.218:8081/actuator/health
    //This will help to check the state of the circuit breaker and number of calls etc.

    //http://192.168.214.218:8081/users/user-id/4813764b-6552-4ec9-8d18-876e082f7b69
    //This API is dependent on Rating and Hotel Service

}
