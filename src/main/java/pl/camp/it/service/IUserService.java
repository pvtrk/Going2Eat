package pl.camp.it.service;

import pl.camp.it.model.Register;
import pl.camp.it.model.User;

import java.util.List;

public interface IUserService {
    void persistUser (User user);
    User getUserById(int id);
    List<User> getAllUsers();
    boolean authenticateUser(String login, String pass);
    User getUserByLogin(String login);
    boolean checkRegister(Register register);
    void registerUser(Register register);
}
