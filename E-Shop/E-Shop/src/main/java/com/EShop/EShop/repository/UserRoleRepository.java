package com.EShop.EShop.repository;

import com.EShop.EShop.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<Role, Long> {

    Role findByAuthority(String roleUser);
}
