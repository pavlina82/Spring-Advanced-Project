package com.EShop.EShop.service.impl;


import com.EShop.EShop.model.entity.Role;
import com.EShop.EShop.model.entity.User;
import com.EShop.EShop.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;


public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public ShopUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        return userRepository.findByUsername(userName)
                .map(ShopUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + userName + " not found!"));
    }

    private static UserDetails map(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities().stream().map(ShopUserDetailsService::map).collect(Collectors.toList()))
                .build();
    }
    private static GrantedAuthority map(Role role){
        return new SimpleGrantedAuthority("ROLE_" + role.getAuthority());
    }
}
