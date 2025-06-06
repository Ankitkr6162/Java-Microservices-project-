package com.user.service.UserService.service;

import com.user.service.UserService.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();

    User getUserByUserId(String userId);
}
