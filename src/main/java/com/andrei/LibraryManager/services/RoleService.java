package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.entities.Role;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.andrei.LibraryManager.repositories.RoleRepository;

@Service
public class RoleService {

  private final RoleRepository ROLE_REPOSITORY;

  public RoleService(RoleRepository roleRepository) {
    ROLE_REPOSITORY = roleRepository;
  }

  public Role addRole(Role role) {
    return ROLE_REPOSITORY.save(role);
  }

  public Role updateRole(Role role) {
    return ROLE_REPOSITORY.save(role);
  }

  public void deleteRole(Role role) {
    ROLE_REPOSITORY.delete(role);
  }

  public Optional<Role> getRoleByName(String roleName) {
    return ROLE_REPOSITORY.findRoleByRoleName(roleName);
  }
}
