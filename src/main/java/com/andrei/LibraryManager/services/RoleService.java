package com.andrei.LibraryManager.services;

import com.andrei.LibraryManager.entities.Role;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.andrei.LibraryManager.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;

  public Role addRole(Role role) {
    return roleRepository.save(role);
  }

  public Role updateRole(Role role) {
    return roleRepository.save(role);
  }

  public void deleteRole(Role role) {
    roleRepository.delete(role);
  }

  public Optional<Role> getRoleByName(String roleName) {
    return roleRepository.findRoleByRoleName(roleName);
  }
}
