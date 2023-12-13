package com.EShop.EShop.service.impl;

import com.EShop.EShop.model.entity.Role;
import com.EShop.EShop.model.entity.User;
import com.EShop.EShop.model.service.UserServiceModel;
import com.EShop.EShop.repository.UserRepository;
import com.EShop.EShop.repository.UserRoleRepository;
import com.EShop.EShop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.EShop.EShop.constants.ValidationMessage.*;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService shopUserDetailsService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService shopUserDetailsService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.shopUserDetailsService = shopUserDetailsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDetails result = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));

        return result;


    }

    @Override
    public void updateRole(Long id, String role) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));

        boolean isRootAdmin = user.getAuthorities()
                .stream()
                .map(Role::getAuthority)
                .toList()
                .contains(ROOT_ADMIN);

        if (isRootAdmin) {
            throw new IllegalArgumentException("Username not found.");
        }
        updateUserRole(user, role);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);

        String encodedPassword = bCryptPasswordEncoder.encode(userServiceModel.getPassword());
        user.setPassword(encodedPassword);
        user.setAuthorities(getRolesForRegistration());

        return modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {

        return this.userRepository.findAll()
                .stream()
                .map(uEntity -> this.modelMapper.map(uEntity, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel findByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        return user == null ? null
                : this.modelMapper.map(user, UserServiceModel.class);

    }

    @Override
    public UserServiceModel findById(Long id) {
        User user = this.userRepository.findById(id).orElse(null);
        return user == null ? null
                : this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public org.springframework.security.core.Authentication login(String name) {

        UserDetails userDetails = shopUserDetailsService.loadUserByUsername(name);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }

    @Override
    public UserServiceModel findUserByUserName(String username) {
        return this.userRepository.findByUsername(username)
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }

    private void updateUserRole(User user, String role) {
        Set<Role> roles = new HashSet<>();

        switch (role) {
            case ROLE_USER:
                roles.add(this.userRoleRepository.findByAuthority(ROLE_USER));
                break;


            case ROLE_MODERATOR:
                roles.add(this.userRoleRepository.findByAuthority(ROLE_MODERATOR));
                roles.add(this.userRoleRepository.findByAuthority(ROLE_USER));
                break;
            case ROLE_ADMIN:
                roles.add(this.userRoleRepository.findByAuthority(ROLE_ADMIN));
                roles.add(this.userRoleRepository.findByAuthority(ROLE_MODERATOR));
                roles.add(this.userRoleRepository.findByAuthority(ROLE_USER));
                break;

        }
        user.setAuthorities(roles);
    }

    private Set<Role> getRolesForRegistration() {
        Set<Role> roles = new HashSet<>();
        if (this.userRepository.count() == 0) {
            roles.add(this.userRoleRepository.findByAuthority("ROOT_ADMIN"));
            roles.add(this.userRoleRepository.findByAuthority(ROLE_ADMIN));
            roles.add(this.userRoleRepository.findByAuthority(ROLE_ADMIN));
            roles.add(this.userRoleRepository.findByAuthority(ROLE_MODERATOR));
            roles.add(this.userRoleRepository.findByAuthority(ROLE_USER));
        } else {
            roles.add(this.userRoleRepository.findByAuthority(ROLE_USER));
        }
        return roles;
    }
}
