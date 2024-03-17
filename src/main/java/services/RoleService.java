package services;

import entities.Role;
import org.springframework.stereotype.Service;
import repositories.RoleRepository;

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

  public Role getRoleByName(String roleName) { // check if role exist
    return ROLE_REPOSITORY.findRoleByName(roleName);
  }
}