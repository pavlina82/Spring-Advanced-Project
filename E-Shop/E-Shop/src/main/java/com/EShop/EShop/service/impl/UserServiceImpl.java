package com.EShop.EShop.service.impl;

import com.EShop.EShop.model.entity.Role;
import com.EShop.EShop.model.entity.User;
import com.EShop.EShop.repository.ProductRepository;
import com.EShop.EShop.repository.UserRepository;
import com.EShop.EShop.repository.UserRoleRepository;
import com.EShop.EShop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.EShop.EShop.constants.ValidationMessage.*;


@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ProductRepository productRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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

}
