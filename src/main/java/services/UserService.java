package services;

import entities.User;
import org.springframework.stereotype.Service;
import repositories.UserRepository;

@Service
public class UserService {

  private final UserRepository USER_REPOSITORY;

  public UserService(UserRepository userRepository) {
    this.USER_REPOSITORY = userRepository;
  }

  public User createUser(User user) {
    return USER_REPOSITORY.save(user);
  }

  public User updateUser(User user){
    return USER_REPOSITORY.save(user);
  }

  public void deleteUser(User user){
    USER_REPOSITORY.delete(user);
  }

  public User getUserByEmail(String email){ // check if user exist
    return USER_REPOSITORY.findUserByEmail(email);
  }

}
