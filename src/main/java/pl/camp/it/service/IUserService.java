package pl.camp.it.service;

import pl.camp.it.model.User;

import java.util.List;

public interface IUserService {
    void persistUser (User user);
    User getUserById(int id);
    List<User> getAllUsers();
}
