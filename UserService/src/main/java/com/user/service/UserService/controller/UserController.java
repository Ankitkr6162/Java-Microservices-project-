package com.user.service.UserService.controller;

import com.user.service.UserService.entity.User;
import com.user.service.UserService.service.UserService;
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

    @GetMapping("/user-id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = this.userService.getUserByUserId(userId);
        return ResponseEntity.ok(user);
    }


}
