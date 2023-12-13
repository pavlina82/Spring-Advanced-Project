package com.EShop.EShop.service;

import com.EShop.EShop.model.service.UserServiceModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    UserDetails loadUserByUsername(String username);

    void updateRole(Long id, String role);

    Object register(UserServiceModel map);

    List<UserServiceModel> findAllUsers();

    UserServiceModel findByUsername(String username);

    UserServiceModel findById(Long id);

    Authentication login(String name);

    UserServiceModel findUserByUserName(String name);
}
