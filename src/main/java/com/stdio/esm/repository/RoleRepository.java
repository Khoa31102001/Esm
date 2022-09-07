package com.stdio.esm.repository;

import com.stdio.esm.model.Account;
import com.stdio.esm.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findById(Long roleId);
    Optional<Role> findByName(String name);

    boolean existsRoleById(Long roleId);
}