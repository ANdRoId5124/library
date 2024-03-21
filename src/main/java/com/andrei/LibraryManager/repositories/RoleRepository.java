package com.andrei.LibraryManager.repositories;

import com.andrei.LibraryManager.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findRoleByRoleName(String roleName);
}
